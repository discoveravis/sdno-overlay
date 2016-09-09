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
 * Class of EVpn E2E Service.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public class EvpnE2EService extends VxlanModelBase {

    /**
     * Topology type:HUBSPOKEN,FULLMESH
     */
    @AString(scope = "HUBSPOKEN,FULLMESH")
    private String topoType;

    /**
     * Controller Id in NetModel
     */
    @AString(require = true, min = 1, max = 255)
    private String controllerId;

    /**
     * Controller id in Device
     */
    @AString(min = 1, max = 255)
    private String externalId;

    /**
     * {evpn|l3vpn}
     */
    @AString(scope = "EVPN,L3VPN")
    private String serviceType;

    /**
     * VxLAN Id
     */
    @AString(require = true, min = 1, max = 255)
    private String vxlanServiceId;

    /**
     * MacAddress LearningMode
     */
    @AString(require = true, scope = "data-plane,control-plane")
    private String macLearningMode;

    /**
     * Arp SuppressMode
     */
    @AString(require = true, min = 1, max = 255)
    private String arpSuppressMode;

    /**
     * List of L2Tp
     */
    @AString
    private List<L2Tp> tpList;

    /**
     * Service topology Id
     */
    @AString(min = 1, max = 255)
    private String serviceTopoId;

    /**
     * VxLan Tunnel Id
     */
    @AString(min = 1, max = 255)
    private String vxlanTunnelId;

    public String getTopoType() {
        return topoType;
    }

    public void setTopoType(String topoType) {
        this.topoType = topoType;
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getVxlanServiceId() {
        return vxlanServiceId;
    }

    public void setVxlanServiceId(String vxlanServiceId) {
        this.vxlanServiceId = vxlanServiceId;
    }

    public String getMacLearningMode() {
        return macLearningMode;
    }

    public void setMacLearningMode(String macLearningMode) {
        this.macLearningMode = macLearningMode;
    }

    public String getArpSuppressMode() {
        return arpSuppressMode;
    }

    public void setArpSuppressMode(String arpSuppressMode) {
        this.arpSuppressMode = arpSuppressMode;
    }

    public List<L2Tp> getTpList() {
        return tpList;
    }

    public void setTpList(List<L2Tp> tpList) {
        this.tpList = tpList;
    }

    public String getServiceTopoId() {
        return serviceTopoId;
    }

    public void setServiceTopoId(String serviceTopoId) {
        this.serviceTopoId = serviceTopoId;
    }

    public String getVxlanTunnelId() {
        return vxlanTunnelId;
    }

    public void setVxlanTunnelId(String vxlanTunnelId) {
        this.vxlanTunnelId = vxlanTunnelId;
    }

}
