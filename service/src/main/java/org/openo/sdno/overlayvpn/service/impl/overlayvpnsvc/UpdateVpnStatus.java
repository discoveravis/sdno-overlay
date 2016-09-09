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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Overlay VPN status update.<br>
 * <p>
 * Methods to update the status of deployment for overlay vpn models
 * </p>
 * 
 * @author
 * @version SDNO 0.5 May 31, 2016
 */
@Service
public class UpdateVpnStatus {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateVpnStatus.class);

    private UpdateVpnStatus() {
    }

    /**
     * Update the status of Overlay VPN and associated service models.
     * 
     * @param deployResult - OverlayVpn deployment result
     * @param tecVpnToDeployedMap - Hash map to store the deployment status
     * @param operOverlayVpn - OverlayVpn for which status needs to be updated
     * @param operConns - Connection for which status needs to be updated
     * @param operEpgs - EndpointGroup for which status needs to be updated
     * @param inventoryDao - Inventory Data access object.
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void updateVpnStatus(ResultRsp<Map<String, OverlayVpn>> deployResult,
            Map<String, Boolean> tecVpnToDeployedMap, OverlayVpn operOverlayVpn, List<Connection> operConns,
            List<EndpointGroup> operEpgs, InventoryDao inventoryDao) throws ServiceException {
        if(MapUtils.isEmpty(deployResult.getData())) {
            return;
        }

        Map<String, String> connUuidToAdmStatMap = new HashMap<>();
        Map<String, String> epgUuidToAdmStatMap = new HashMap<>();

        // Deployment status update to Connection and EPG maps
        fillStatusToMap(tecVpnToDeployedMap, deployResult, connUuidToAdmStatMap, epgUuidToAdmStatMap);
        LOGGER.info("Status: Conn = " + connUuidToAdmStatMap + ", Epg = " + epgUuidToAdmStatMap);

        // Set overlay VPN status based on the status of connection
        if(connUuidToAdmStatMap.containsValue(AdminStatus.PARTIALLYINACTIVE.getName())) {
            operOverlayVpn.setAdminStatus(AdminStatus.PARTIALLYINACTIVE.getName());
        } else if(connUuidToAdmStatMap.containsValue(AdminStatus.ACTIVE.getName())) {
            operOverlayVpn.setAdminStatus(AdminStatus.ACTIVE.getName());
        } else if(connUuidToAdmStatMap.containsValue(AdminStatus.INACTIVE.getName())) {
            operOverlayVpn.setAdminStatus(AdminStatus.INACTIVE.getName());
        }

        // Filling deployment status
        for(Connection operConn : operConns) {
            if(connUuidToAdmStatMap.containsKey(operConn.getUuid())) {
                operConn.setAdminStatus(connUuidToAdmStatMap.get(operConn.getUuid()));
            }
        }
        for(EndpointGroup operEpg : operEpgs) {
            if(epgUuidToAdmStatMap.containsKey(operEpg.getUuid())) {
                operEpg.setAdminStatus(epgUuidToAdmStatMap.get(operEpg.getUuid()));
            }
        }

        // Update service status in DAO
        inventoryDao.update(OverlayVpn.class, Arrays.asList(operOverlayVpn), "adminStatus");
        inventoryDao.update(Connection.class, operConns, "adminStatus");
        inventoryDao.update(EndpointGroup.class, operEpgs, "adminStatus");
    }

    /**
     * Utility function to update the deployment status to map<br>
     * 
     * @param tecVpnToDeployedMap - Map to be updated with status
     * @param deployResult - Deployment result response
     * @param connUuidToAdmStatMap - Connection Map to be updated with status
     * @param epgUuidToAdmStatMap - EndpointGroup Map to be updated with status
     * @since SDNO 0.5
     */
    private void fillStatusToMap(Map<String, Boolean> tecVpnToDeployedMap,
            ResultRsp<Map<String, OverlayVpn>> deployResult, Map<String, String> connUuidToAdmStatMap,
            Map<String, String> epgUuidToAdmStatMap) {
        if(null == tecVpnToDeployedMap) {
            // Update the status of deployement of overlay VPN
            for(Entry<String, OverlayVpn> tempEntry : deployResult.getData().entrySet()) {
                OverlayVpn tempVpn = tempEntry.getValue();
                doFillStatusMap(deployResult.isSuccess(), connUuidToAdmStatMap, epgUuidToAdmStatMap, tempVpn);
            }
        } else {
            // Update overlay VPN on the basis of tecVpnToDeployedMap
            for(Entry<String, Boolean> tempEntry : tecVpnToDeployedMap.entrySet()) {
                String tempVpnTec = tempEntry.getKey();
                OverlayVpn tempVpn = deployResult.getData().get(tempVpnTec);
                doFillStatusMap(tempEntry.getValue(), connUuidToAdmStatMap, epgUuidToAdmStatMap, tempVpn);
            }
        }
    }

    /**
     * Based on deployment status fill the map<br>
     * 
     * @param isDepolyed - Boolean to check Deployment or Undeployment operation
     * @param connUuidToAdmStatMap - Connection Map to be updated with status
     * @param epgUuidToAdmStatMap - EndpointGroup Map to be updated with status
     * @param tempVpn - Overlay VPN Map whose connections and EPG's needs status update
     * @since SDNO 0.5
     */
    private void doFillStatusMap(boolean isDepolyed, Map<String, String> connUuidToAdmStatMap,
            Map<String, String> epgUuidToAdmStatMap, OverlayVpn tempVpn) {
        if(isDepolyed) {
            for(Connection tempConn : tempVpn.getVpnConnections()) {
                fillStatWhenDeployOk(connUuidToAdmStatMap, tempConn.getUuid());
                for(EndpointGroup tempEpg : tempConn.getEndpointGroups()) {
                    fillStatWhenDeployOk(epgUuidToAdmStatMap, tempEpg.getUuid());
                }
            }
        } else {
            for(Connection tempConn : tempVpn.getVpnConnections()) {
                fillStatWhenUnDeployOk(connUuidToAdmStatMap, tempConn.getUuid());
                for(EndpointGroup tempEpg : tempConn.getEndpointGroups()) {
                    fillStatWhenUnDeployOk(epgUuidToAdmStatMap, tempEpg.getUuid());
                }
            }
        }
    }

    /**
     * Utility to fill status onto map with key as uuid and status as value when deployment was
     * successful<br>
     * 
     * @param uuidToStatMap - Map to be updated with
     * @param uuid - Key
     * @since SDNO 0.5
     */
    private static void fillStatWhenDeployOk(Map<String, String> uuidToStatMap, String uuid) {
        if(AdminStatus.PARTIALLYINACTIVE.getName().equals(uuidToStatMap.get(uuid))) {
            return;
        }

        if(AdminStatus.INACTIVE.getName().equals(uuidToStatMap.get(uuid))) {
            uuidToStatMap.put(uuid, AdminStatus.PARTIALLYINACTIVE.getName());
        } else {
            uuidToStatMap.put(uuid, AdminStatus.ACTIVE.getName());
        }
    }

    /**
     * Utility to fill status onto map with key as uuid and status as value when undeploy was
     * successful<br>
     * 
     * @param uuidToStatMap - Map to be updated with
     * @param uuid - key
     * @since SDNO 0.5
     */
    private static void fillStatWhenUnDeployOk(Map<String, String> uuidToStatMap, String uuid) {
        if(AdminStatus.PARTIALLYINACTIVE.getName().equals(uuidToStatMap.get(uuid))) {
            return;
        }

        if(AdminStatus.ACTIVE.getName().equals(uuidToStatMap.get(uuid))) {
            uuidToStatMap.put(uuid, AdminStatus.PARTIALLYINACTIVE.getName());
        } else {
            uuidToStatMap.put(uuid, AdminStatus.INACTIVE.getName());
        }
    }

}
