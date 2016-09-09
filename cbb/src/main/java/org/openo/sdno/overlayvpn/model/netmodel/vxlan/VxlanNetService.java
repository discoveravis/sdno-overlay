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

package org.openo.sdno.overlayvpn.model.netmodel.vxlan;

import java.util.List;

import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of VxLanNetService.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public class VxlanNetService extends VxlanModelBase {

    /**
     * Tenant Id(NBI)
     */
    @AString(require = true, min = 1, max = 255)
    private String tenantVpnId;

    /**
     * Type of topology,including none-interconnect,hub-spoken and full-mesh
     */
    @AString(scope = "HUBSPOKEN,FULLMESH")
    private String topoType;

    @AString()
    private List<EvpnE2EService> evpnE2EServiceList;

    @AString()
    private List<EvpnNeService> evpnNeInstanceList;

    @AString()
    private List<L3vpnE2EService> l3vpnE2EServiceList;

    @AString()
    private List<L3vpnNeService> l3vpnNeInstanceList;

    public String getTenantVpnId() {
        return tenantVpnId;
    }

    public void setTenantVpnId(String tenantVpnId) {
        this.tenantVpnId = tenantVpnId;
    }

    public String getTopoType() {
        return topoType;
    }

    public void setTopoType(String topoType) {
        this.topoType = topoType;
    }

}
