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

package org.openo.sdno.overlayvpn.model.netmodel.vxlan;

import java.util.List;

import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of L3Vpn EndtoEnd Service.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class L3vpnE2EService extends VxlanModelBase {

    /**
     * Topology type:HUBSPOKEN,FULLMESH
     */
    @AString(scope = "HUBSPOKEN,FULLMESH")
    private String topoType;

    /**
     * Controller Id in NetModel.
     */
    @AString(require = true, min = 1, max = 255)
    private String controllerId;

    /**
     * Controller Id in Device
     */
    @AString(min = 1, max = 255)
    private String externalId;

    /**
     * {evpn|l3vpn}
     */
    @AString(scope = "EVPN,L3VPN")
    private String serviceType;

    /**
     * VxLan Service Id
     */
    @AString(min = 1, max = 255)
    private String vxlanNetServiceId;

    private List<L3Tp> tpIdList;

    /**
     * Service Topo Id
     */
    @AString(min = 1, max = 255)
    private String serviceTopoId;

    /**
     * VxLan Tunnel Id
     */
    @AString(min = 1, max = 255)
    private String vxlanTunnelId;

    public void setTopoType(String topoType) {
        this.topoType = topoType;
    }

    public String getTopoType() {
        return topoType;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setVxlanNetServiceId(String vxlanNetServiceId) {
        this.vxlanNetServiceId = vxlanNetServiceId;
    }

    public String getVxlanNetServiceId() {
        return vxlanNetServiceId;
    }

    public void setTpIdList(List<L3Tp> tpIdList) {
        this.tpIdList = tpIdList;
    }

    public List<L3Tp> getTpIdList() {
        return tpIdList;
    }

    public void setServiceTopoId(String serviceTopoId) {
        this.serviceTopoId = serviceTopoId;
    }

    public String getServiceTopoId() {
        return serviceTopoId;
    }

    public void setVxlanTunnelId(String vxlanTunnelId) {
        this.vxlanTunnelId = vxlanTunnelId;
    }

    public String getVxlanTunnelId() {
        return vxlanTunnelId;
    }

}
