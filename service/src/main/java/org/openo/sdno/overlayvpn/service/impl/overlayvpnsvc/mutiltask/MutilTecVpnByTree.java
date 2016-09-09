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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.enums.OperaMethType;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.TechnologyType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of Dividing OverlayVpn Connections by Technology Type.<br>
 * 
 * @author
 * @version SDNO 0.5 Jun 7, 2016
 */
public class MutilTecVpnByTree {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutilTecVpnByTree.class);

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    private MutilTecVpnByTree() {
        super();
    }

    /**
     * Get Map of Technology type to OverlayVpn.<br>
     * 
     * @param complexTreeVpn OverlayVpn need to divide
     * @param type operation type
     * @return Map of Technology type to OverlayVpn
     * @since SDNO 0.5
     */
    public static ResultRsp<Map<String, OverlayVpn>> getTechToCloudVpnMap(OverlayVpn complexTreeVpn,
            OperaMethType type) {
        Map<String, OverlayVpn> techToOverlayVpnMap = new HashMap<>();
        for(Connection tempConn : complexTreeVpn.getVpnConnections()) {
            if((OperaMethType.DEPLOY.getName().equals(type.getName())
                    || OperaMethType.CREATE.getName().equals(type.getName()))
                    && AdminStatus.ACTIVE.getName().equals(tempConn.getAdminStatus())) {
                continue;
            }

            // UnDeploy or update inactive OverlayVpn is permitted
            if((OperaMethType.UNDEPLOY.getName().equals(type.getName())
                    || OperaMethType.UPDATE.getName().equals(type.getName()))
                    && AdminStatus.INACTIVE.getName().equals(tempConn.getAdminStatus())) {
                continue;
            }

            String connTech = tempConn.getTechnology();

            // get connections based on VXLAN
            if(TechnologyType.isVxlanRelated(connTech)) {
                buildVxlanOverlayVpn(techToOverlayVpnMap, complexTreeVpn, tempConn);
            }

            // get connections based on IPSEC
            if(TechnologyType.isIpsec(connTech)) {
                buildIpsecOverlayVpn(techToOverlayVpnMap, complexTreeVpn, tempConn);
            }
        }

        LOGGER.info("get tecToVpn map OK, map = " + complexTreeVpn);
        return new ResultRsp<Map<String, OverlayVpn>>(ErrorCode.OVERLAYVPN_SUCCESS, techToOverlayVpnMap);
    }

    /**
     * Add OverlayVpn Connections based on VxLan to Map.<br>
     * 
     * @param techToCloudVpnMap Map of VxLan Technology type to OverlayVpn
     * @param complexOverlayVpn OverlayVpn need to divide
     * @param conn current Connection
     * @since SDNO 0.5
     */
    private static void buildVxlanOverlayVpn(Map<String, OverlayVpn> techToCloudVpnMap, OverlayVpn complexOverlayVpn,
            Connection conn) {
        if(!techToCloudVpnMap.containsKey(CommConst.VPN_TEC_VXLAN)) {
            // Create new OverlayVpn instance
            OverlayVpn newOverlayVpn = new OverlayVpn();
            newOverlayVpn.copyBasicData(complexOverlayVpn);
            newOverlayVpn.setAdditionalInfo(complexOverlayVpn.getAdditionalInfo());
            techToCloudVpnMap.put(CommConst.VPN_TEC_VXLAN, newOverlayVpn);
        }
        // Add Connection
        OverlayVpn overlayVpnInMap = techToCloudVpnMap.get(CommConst.VPN_TEC_VXLAN);
        if(null == overlayVpnInMap.getVpnConnections()) {
            overlayVpnInMap.setVpnConnections(new ArrayList<Connection>());
        }
        overlayVpnInMap.getVpnConnections().add(conn);
    }

    /**
     * Add OverlayVpn Connections based on IpSec to Map.<br>
     * 
     * @param tecToCloudVpnMap Map of IpSec Technology type to OverlayVpn
     * @param complexCloudVpn OverlayVpn need to divide
     * @param vpnConn current Connection
     * @since SDNO 0.5
     */
    private static void buildIpsecOverlayVpn(Map<String, OverlayVpn> tecToCloudVpnMap, OverlayVpn complexCloudVpn,
            Connection vpnConn) {
        if(!tecToCloudVpnMap.containsKey(CommConst.VPN_TEC_IPSEC)) {
            // Create new OverlayVpn instance
            OverlayVpn newOverlayVpn = new OverlayVpn();
            newOverlayVpn.copyBasicData(complexCloudVpn);
            newOverlayVpn.setAdditionalInfo(complexCloudVpn.getAdditionalInfo());
            tecToCloudVpnMap.put(CommConst.VPN_TEC_IPSEC, newOverlayVpn);
        }

        // add Connection
        OverlayVpn overlayVpnInMap = tecToCloudVpnMap.get(CommConst.VPN_TEC_IPSEC);
        if(null == overlayVpnInMap.getVpnConnections()) {
            overlayVpnInMap.setVpnConnections(new ArrayList<Connection>());
        }
        overlayVpnInMap.getVpnConnections().add(vpnConn);
    }

}
