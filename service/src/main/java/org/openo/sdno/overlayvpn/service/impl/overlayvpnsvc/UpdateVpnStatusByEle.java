/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Overlay VPN status update.<br>
 * 
 * @author
 * @version SDNO 0.5 Jun 8, 2016
 */
public class UpdateVpnStatusByEle {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateVpnStatusByEle.class);

    private UpdateVpnStatusByEle() {

    }

    /**
     * After a successful connection to the deployment or deployment , refresh its connection and
     * vpn state<br>
     * 
     * @param deployResult - Overlay VPN deployment result
     * @param fullOverlayVpn - Original overlay VPN model
     * @param conn - Connection model
     * @param refreshedStatus - Admin status
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void updateVpnStatusByConnStatus(ResultRsp<Map<String, OverlayVpn>> deployResult,
            OverlayVpn fullOverlayVpn, Connection conn, AdminStatus refreshedStatus) throws ServiceException {
        if(!deployResult.isSuccess()) {
            LOGGER.info("updateVpnStatusByConnStatus: deployResult is fail.");
            return;
        }

        conn.setAdminStatus(refreshedStatus.getName());
        InventoryDao invDao = new InventoryDao<>();
        invDao.update(Connection.class, Arrays.asList(conn), "adminStatus");

        List<EndpointGroup> epgList = new ArrayList<EndpointGroup>();
        for(EndpointGroup epg : conn.getEndpointGroups()) {
            epg.setAdminStatus(refreshedStatus.getName());
            epgList.add(epg);
        }
        invDao.update(EndpointGroup.class, epgList, "adminStatus");

        if(refreshedStatus.getName().equals(fullOverlayVpn.getAdminStatus())) {
            return;
        }

        boolean isNeedRefreshVpn = true;

        for(Connection tempConn : fullOverlayVpn.getVpnConnections()) {
            if(tempConn.getUuid().equals(conn.getUuid())) {
                continue;
            }
            if(!refreshedStatus.getName().equals(tempConn.getAdminStatus())) {
                isNeedRefreshVpn = false;
                break;
            }
        }

        if(isNeedRefreshVpn) {
            fullOverlayVpn.setAdminStatus(refreshedStatus.getName());
            invDao.update(OverlayVpn.class, Arrays.asList(fullOverlayVpn), "adminStatus");
        }
    }

    /**
     * Update the VPN Staus<br>
     * 
     * @param deployResult Result response of the depoly operation
     * @param fullOverlayVpn Overlay VPN
     * @param epg End point group
     * @param refreshedStatus Admin Status to be updated to
     * @param isUpdateOper True if the operation is of update type, will early return if set to
     *            false
     * @param invDao Inventory Dao Object
     * @throws ServiceException throws exception if the operation fails.
     * @since SDNO 0.5
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void updateVpnStatusByEpgStatus(ResultRsp<Map<String, OverlayVpn>> deployResult,
            OverlayVpn fullOverlayVpn, EndpointGroup epg, AdminStatus refreshedStatus, boolean isUpdateOper,
            InventoryDao invDao) throws ServiceException {

        if(!deployResult.isSuccess()) {
            if(!isUpdateOper) {
                LOGGER.info("updateVpnStatusByEpgStatus: deployResult is fail.");
                return;
            }

            // Epg update process failed, need to refresh the connection status and vpn
            refreshConnAndVpn(fullOverlayVpn, epg, invDao, AdminStatus.INACTIVE);

            return;

        }

        epg.setAdminStatus(refreshedStatus.getName());

        invDao.update(EndpointGroup.class, Arrays.asList(epg), "adminStatus");

        refreshConnAndVpn(fullOverlayVpn, epg, invDao, refreshedStatus);
    }

    /**
     * Update connection status of connection and respective endpoint groups<br>
     * 
     * @param fullOverlayVpn - Original overlay VPN
     * @param epg - Endpoint group
     * @param invDao - Inventory Dao
     * @param refreshedStatus - New admin status
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void refreshConnAndVpn(OverlayVpn fullOverlayVpn, EndpointGroup epg, InventoryDao invDao,
            AdminStatus refreshedStatus) throws ServiceException {
        boolean isNeedRefreshVpnStatus = true;
        boolean isNeedRefreshConnStatus = true;

        if(fullOverlayVpn.getAdminStatus().equals(refreshedStatus.getName())) {
            isNeedRefreshVpnStatus = false;
        }

        Connection tempConn = null;

        for(Connection connection : fullOverlayVpn.getVpnConnections()) {
            if(connection.getUuid().equals(epg.getConnectionId())) {
                tempConn = connection;
                if(connection.getAdminStatus().equals(refreshedStatus.getName())) {
                    isNeedRefreshConnStatus = false;
                    continue;
                }

                // When you deploy and only two epg, another epg state should be updated with
                // deployment of state
                for(EndpointGroup tempEpg : connection.getEndpointGroups()) {
                    if(tempEpg.getUuid().equals(epg.getUuid())) {
                        continue;
                    }

                    if((connection.getEndpointGroups().size() == 2)
                            && (refreshedStatus.getName().equals(AdminStatus.ACTIVE.getName()))) {
                        tempEpg.setAdminStatus(refreshedStatus.getName());
                        invDao.update(EndpointGroup.class, Arrays.asList(tempEpg), "adminStatus");

                    }
                }
            }
        }

        if((null != tempConn) && isNeedRefreshConnStatus) {
            tempConn.setAdminStatus(refreshedStatus.getName());
            invDao.update(Connection.class, Arrays.asList(tempConn), "adminStatus");
        }
        if(isNeedRefreshVpnStatus) {
            fullOverlayVpn.setAdminStatus(refreshedStatus.getName());
            invDao.update(OverlayVpn.class, Arrays.asList(fullOverlayVpn), "adminStatus");
        }
    }
}
