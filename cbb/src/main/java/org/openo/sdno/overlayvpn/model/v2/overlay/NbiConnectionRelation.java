/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.model.v2.overlay;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * The connection relation between NetConnection and Vpn/InternalVpn connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
@MOResType(infoModelName = "overlayvpn_connectionrelation")
public class NbiConnectionRelation extends BaseModel {

    @AUuid
    private String vpnConnectionId;

    @AString(scope = "VpnConnection,InternalVpnConnection")
    private String vpnConnectionType;

    @AUuid
    private String netConnectionId;

    @AString(scope = "vxlan,gre,ipsec")
    private String netConnectionType;

    private String ext;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public NbiConnectionRelation() {
        super();
    }

    public String getVpnConnectionId() {
        return vpnConnectionId;
    }

    public void setVpnConnectionId(String vpnConnectionId) {
        this.vpnConnectionId = vpnConnectionId;
    }

    public String getVpnConnectionType() {
        return vpnConnectionType;
    }

    public void setVpnConnectionType(String vpnConnectionType) {
        this.vpnConnectionType = vpnConnectionType;
    }

    public String getNetConnectionId() {
        return netConnectionId;
    }

    public void setNetConnectionId(String netConnectionId) {
        this.netConnectionId = netConnectionId;
    }

    public String getNetConnectionType() {
        return netConnectionType;
    }

    public void setNetConnectionType(String netConnectionType) {
        this.netConnectionType = netConnectionType;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * Build resource connection key.<br>
     * 
     * @return The vpnconnectionId related netConnectionId as string
     * @since SDNO 0.5
     */
    public String toResKey() {
        return new StringBuilder().append(vpnConnectionId).append(":").append(netConnectionId).toString();
    }
}
