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

import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;

/**
 * The vpn connection model.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 3, 2017
 */
public class NbiVpnConnection extends UuidModel {

    private String name = null;

    private String tenantId = null;

    private String description = null;

    private String deployStatus = null;

    private String vpnId = null;

    private String aEndVpnGatewayId = null;

    private String zEndVpnGatewayId = null;

    private String vni = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(String deployStatus) {
        this.deployStatus = deployStatus;
    }

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(String vpnId) {
        this.vpnId = vpnId;
    }

    public String getVni() {
        return vni;
    }

    public void setVni(String vni) {
        this.vni = vni;
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

}
