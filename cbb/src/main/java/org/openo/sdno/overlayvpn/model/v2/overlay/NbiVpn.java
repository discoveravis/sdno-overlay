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

import java.util.List;
import java.util.Set;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOEditableField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOSubField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * The vpn model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
@MOResType(infoModelName = "overlayvpn_vpn")
public class NbiVpn extends ServiceModel {

    @NONInvField
    @MOSubField(parentId = "vpnId")
    private List<NbiVpnConnection> vpnConnections;

    @NONInvField
    @MOSubField(parentId = "vpnId")
    private List<NbiVpnGateway> vpnGateways;

    @NONInvField
    private Set<String> siteList;

    @NONInvField
    private Set<String> vpcList;

    @AString(min = 0, max = 255)
    @MOEditableField
    private String ext;

    @AString(min = 1, max = 255)
    private String vpnDescriptor;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public NbiVpn() {
        super();
    }

    public List<NbiVpnConnection> getVpnConnections() {
        return vpnConnections;
    }

    public void setVpnConnections(List<NbiVpnConnection> vpnConnections) {
        this.vpnConnections = vpnConnections;
    }

    public List<NbiVpnGateway> getVpnGateways() {
        return vpnGateways;
    }

    public void setVpnGateways(List<NbiVpnGateway> vpnGateways) {
        this.vpnGateways = vpnGateways;
    }

    public Set<String> getSiteList() {
        return siteList;
    }

    public void setSiteList(Set<String> siteList) {
        this.siteList = siteList;
    }

    public Set<String> getVpcList() {
        return vpcList;
    }

    public void setVpcList(Set<String> vpcList) {
        this.vpcList = vpcList;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getVpnDescriptor() {
        return vpnDescriptor;
    }

    public void setVpnDescriptor(String vpnDescriptor) {
        this.vpnDescriptor = vpnDescriptor;
    }
}
