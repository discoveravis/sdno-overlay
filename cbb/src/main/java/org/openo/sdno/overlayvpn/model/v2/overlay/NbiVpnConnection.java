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

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MORelatedField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * The vpn connection model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
@MOResType(infoModelName = "overlayvpn_vpnconnection")
public class NbiVpnConnection extends NbiServiceConnection {

    @AUuid
    private String vpnId;

    @AString
    private String routeType;

    @AUuid
    private String aEndVpnGatewayId;

    @AUuid
    private String zEndVpnGatewayId;

    @NONInvField
    @MORelatedField(relationId = "aEndVpnGatewayId")
    private NbiVpnGateway aEndVpnGateway;

    @NONInvField
    @MORelatedField(relationId = "zEndVpnGatewayId")
    private NbiVpnGateway zEndVpnGateway;

    @AInt(min = 1, max = 16777216)
    private String vni;

    @AString
    private String ext;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public NbiVpnConnection() {
        super();
    }

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(String vpnId) {
        this.vpnId = vpnId;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getaEndVpnGatewayId() {
        return aEndVpnGatewayId;
    }

    public void setaEndVpnGatewayId(String aEndVpnGatewayId) {
        this.aEndVpnGatewayId = aEndVpnGatewayId;
    }

    public String getzEndVpnGatewayId() {
        return zEndVpnGatewayId;
    }

    public void setzEndVpnGatewayId(String zEndVpnGatewayId) {
        this.zEndVpnGatewayId = zEndVpnGatewayId;
    }

    public NbiVpnGateway getaEndVpnGateway() {
        return aEndVpnGateway;
    }

    public void setaEndVpnGateway(NbiVpnGateway aEndVpnGateway) {
        this.aEndVpnGateway = aEndVpnGateway;
    }

    public NbiVpnGateway getzEndVpnGateway() {
        return zEndVpnGateway;
    }

    public void setzEndVpnGateway(NbiVpnGateway zEndVpnGateway) {
        this.zEndVpnGateway = zEndVpnGateway;
    }

    public String getVni() {
        return vni;
    }

    public void setVni(String vni) {
        this.vni = vni;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
