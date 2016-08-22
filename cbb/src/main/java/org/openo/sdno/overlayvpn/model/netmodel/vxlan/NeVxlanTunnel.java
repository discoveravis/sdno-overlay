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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.netmodel.BaseNetModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AIp;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of NeVxlanTunnel Model.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_nevxlantunnel")
public class NeVxlanTunnel extends BaseNetModel {

    @JsonIgnore
    @AUuid(require = true)
    private String vxlanInstanceId;

    @JsonIgnore
    @AInt(require = true, min = 1, max = 16777215)
    private String vni;

    @NONInvField
    private List<String> vxlanInstances;

    @NONInvField
    private List<String> vnis;

    private String sourceIfId;

    @AIp(require = true)
    private String sourceAddress;

    @AIp(require = true)
    private String destAddress;

    private String tunnelIfId;

    @AUuid(require = true)
    private String neId;

    @AUuid(require = true)
    private String peerNeId;

    @NONInvField
    @JsonIgnore
    private String localSiteType;

    public String getVxlanInstanceId() {
        return vxlanInstanceId;
    }

    public void setVxlanInstanceId(String vxlanInstanceId) {
        this.vxlanInstanceId = vxlanInstanceId;
    }

    public String getVni() {
        return vni;
    }

    public void setVni(String vni) {
        this.vni = vni;
    }

    public List<String> getVxlanInstances() {
        return vxlanInstances;
    }

    public void setVxlanInstances(List<String> vxlanInstances) {
        this.vxlanInstances = vxlanInstances;
    }

    public List<String> getVnis() {
        return vnis;
    }

    public void setVnis(List<String> vnis) {
        this.vnis = vnis;
    }

    public String getSourceIfId() {
        return sourceIfId;
    }

    public void setSourceIfId(String sourceIfId) {
        this.sourceIfId = sourceIfId;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public String getTunnelIfId() {
        return tunnelIfId;
    }

    public void setTunnelIfId(String tunnelIfId) {
        this.tunnelIfId = tunnelIfId;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getPeerNeId() {
        return peerNeId;
    }

    public void setPeerNeId(String peerNeId) {
        this.peerNeId = peerNeId;
    }

    public String getLocalSiteType() {
        return localSiteType;
    }

    public void setLocalSiteType(String localSiteType) {
        this.localSiteType = localSiteType;
    }

    /**
     * Generate Unique Service Id.<br/>
     * 
     * @param dbTunnel NeVxlanTunnel Object need to generate Id
     * @return unique service id generated
     * @since SDNO 0.5
     */
    public String generateUniqueServiceId(NeVxlanTunnel dbTunnel) {
        return dbTunnel.getConnectionServiceId() + dbTunnel.getVxlanInstanceId() + dbTunnel.getNeId()
                + dbTunnel.getSourceAddress() + dbTunnel.getPeerNeId() + dbTunnel.getDestAddress();
    }
}
