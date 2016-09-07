/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.overlayvpn;

import java.util.Arrays;

import org.openo.sdno.overlayvpn.model.common.enums.ModifyMaskType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.springframework.util.StringUtils;

/**
 * Util Class of OverlayVpn Service.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 7, 2016
 */
public class OverlayVpnSvcUtil {

    private OverlayVpnSvcUtil() {

    }

    /**
     * Mark ModifyMaskType to EndpointGroup of OverlayVpn.<br/>
     * 
     * @param overlayVpn OverlayVpn need to mark
     * @param epg EndpointGroup need to mark
     * @param modifyMask ModifyMask Type
     * @since SDNO 0.5
     */
    public static void markEpgToVpn(OverlayVpn overlayVpn, EndpointGroup epg, ModifyMaskType modifyMask) {
        for(Connection conn : overlayVpn.getVpnConnections()) {
            if(conn.getUuid().equals(epg.getConnectionId())) {
                for(EndpointGroup tempEpg : conn.getEndpointGroups()) {
                    if(tempEpg.getUuid().equals(epg.getUuid())) {
                        tempEpg.setModifyMask(modifyMask.getName());
                        return;
                    }
                }
            }
        }
    }

    /**
     * Set ModifyMaskType.<br/>
     * 
     * @param overlayVpn OverlayVpn need to mark
     * @param conn Connection Object
     * @param modifyMask ModifyMask Type
     * @since SDNO 0.5
     */
    public static void markConnToVpn(OverlayVpn overlayVpn, Connection conn, ModifyMaskType modifyMask) {
        for(Connection tempConn : overlayVpn.getVpnConnections()) {
            if(tempConn.getUuid().equals(conn.getUuid())) {
                tempConn.setModifyMask(modifyMask.getName());
                return;
            }
        }
    }

    /**
     * Build OverlayVpn by Connection.<br/>
     * 
     * @param fullVpn OverlayVpn Data
     * @param connectionId Connection Id
     * @return OverlayVpn created
     * @since SDNO 0.5
     */
    public static OverlayVpn buildVpnDataByConnId(OverlayVpn fullVpn, String connectionId) {
        OverlayVpn returnVpn = new OverlayVpn();
        returnVpn.copyBasicData(fullVpn);

        Connection conn = new Connection();

        if(StringUtils.hasLength(connectionId)) {
            conn = findConnInVpn(fullVpn, connectionId);
        }

        returnVpn.setVpnConnections(Arrays.asList(conn));
        return returnVpn;
    }

    private static Connection findConnInVpn(OverlayVpn vpn, String connectionId) {
        for(Connection conn : vpn.getVpnConnections()) {
            if(connectionId.equals(conn.getUuid())) {
                return conn;
            }
        }
        return new Connection();
    }
}
