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
 * Class of L3vpnNeService.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public class L3vpnNeService extends VxlanModelBase {

    /**
     * Controller Id in NetModel
     */
    @AString(require = true, min = 1, max = 255)
    private String controllerId;

    /**
     * Controller Id in Device
     */
    @AString(min = 1, max = 255)
    private String externalId;

    /**
     * VxLan Service Id
     */
    @AString(min = 1, max = 255)
    private String vxlanNetServiceId;

    /**
     * L3vpnE2EService Id
     */
    @AString(min = 1, max = 255)
    private String l3vpnE2EServiceId;

    /**
     * Network Element Device Id
     */
    @AString(min = 1, max = 255)
    private String neId;

    /**
     * Topology Id
     */
    @AString(min = 1, max = 255)
    private String tpId;

    private List<VxlanTunnelconnection> tunnelConnectionList;

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

    public String getVxlanNetServiceId() {
        return vxlanNetServiceId;
    }

    public void setVxlanNetServiceId(String vxlanNetServiceId) {
        this.vxlanNetServiceId = vxlanNetServiceId;
    }

    public String getL3vpnE2EServiceId() {
        return l3vpnE2EServiceId;
    }

    public void setL3vpnE2EServiceId(String l3vpnE2EServiceId) {
        this.l3vpnE2EServiceId = l3vpnE2EServiceId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getNeId() {
        return neId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTunnelConnectionList(List<VxlanTunnelconnection> tunnelConnectionList) {
        this.tunnelConnectionList = tunnelConnectionList;
    }

    public List<VxlanTunnelconnection> getTunnelConnectionList() {
        return tunnelConnectionList;
    }
}
