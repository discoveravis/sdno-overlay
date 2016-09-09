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
 * Class of EvpnNeService.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public class EvpnNeService extends VxlanModelBase {

    /**
     * Controller Id
     */
    @AString(require = true, min = 1, max = 255)
    private String controllerId;

    /**
     * UUID for Controller
     */
    @AString(min = 1, max = 255)
    private String externalId;

    /**
     * {evpn|l3vpn}
     */
    @AString(scope = "evpn,l3vpn")
    private String serviceType;

    /**
     * EvpnE2EService Id
     */
    @AString(min = 1, max = 255)
    private String evpnE2EServiceId;

    /**
     * VXLAN ID
     */
    @AString(require = true, min = 1, max = 255)
    private String vxlanNeServiceId;

    /**
     * Network Element Id
     */
    @AString(min = 1, max = 255)
    private String neId;

    /**
     * Topology Id
     */
    @AString(min = 1, max = 255)
    private String tpId;

    /**
     * List of VxlanTunnelconnection
     */
    @AString()
    private List<VxlanTunnelconnection> tunnelConnectionList;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getEvpnE2EServiceId() {
        return evpnE2EServiceId;
    }

    public void setEvpnE2EServiceId(String evpnE2EServiceId) {
        this.evpnE2EServiceId = evpnE2EServiceId;
    }

    public String getVxlanNeServiceId() {
        return vxlanNeServiceId;
    }

    public void setVxlanNeServiceId(String vxlanNeServiceId) {
        this.vxlanNeServiceId = vxlanNeServiceId;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public List<VxlanTunnelconnection> getTunnelConnectionList() {
        return tunnelConnectionList;
    }

    public void setTunnelConnectionList(List<VxlanTunnelconnection> tunnelConnectionList) {
        this.tunnelConnectionList = tunnelConnectionList;
    }

}
