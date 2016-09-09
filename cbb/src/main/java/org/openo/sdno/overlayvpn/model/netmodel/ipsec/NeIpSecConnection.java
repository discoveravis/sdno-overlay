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

package org.openo.sdno.overlayvpn.model.netmodel.ipsec;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.AuthModeType;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.model.netmodel.BaseNetModel;
import org.openo.sdno.overlayvpn.verify.annotation.AIpMask;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of NeIpSecConnection Model Data.<br>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
@MOResType(infoModelName = "neipsecconnection")
public class NeIpSecConnection extends BaseNetModel {

    @AUuid(require = true)
    private String neId;

    @AIpMask(require = true)
    private String sourceAddress;

    @JsonProperty(value = "sourceIfId")
    @AString(require = true, min = 1, max = 36)
    private String soureIfName;

    @AString(require = true, scope = "none,hub,spoke")
    private String topoRole;

    @AString(require = true, scope = "PSK,certificate")
    private String authMode = AuthModeType.PSK.getName();

    @AString(require = true, min = 1, max = 127)
    private String psk;

    @AString(min = 0, max = 36)
    @JsonIgnore
    private String ikePolicyId;

    // Info of peer end
    @AIpMask(require = true)
    private String peerAddress;

    @JsonIgnore
    @AString(require = false, min = 0, max = 255)
    private String peerNeId;

    @AString(min = 0, max = 36)
    @JsonIgnore
    private String ipsecPolicyId;

    @NONInvField
    @NotNull
    private IkePolicy ikePolicy;

    @NONInvField
    @NotNull
    private IpSecPolicy ipSecPolicy;

    /**
     * @return Returns the neId.
     */
    public String getNeId() {
        return neId;
    }

    /**
     * @param neId The neId to set.
     */
    public void setNeId(String neId) {
        this.neId = neId;
    }

    /**
     * @param sourceAddress The sourceAddress to set.
     */
    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * @return Returns the sourceAddress.
     */
    public String getSourceAddress() {
        return sourceAddress;
    }

    /**
     * @param soureIfName The soureIfName to set.
     */
    public void setSoureIfName(String soureIfName) {
        this.soureIfName = soureIfName;
    }

    /**
     * @return Returns the soureIfName.
     */
    public String getSoureIfName() {
        return soureIfName;
    }

    /**
     * @return Returns the topoRole.
     */
    public String getTopoRole() {
        return topoRole;
    }

    /**
     * @param topoRole The topoRole to set.
     */
    public void setTopoRole(String topoRole) {
        this.topoRole = topoRole;
    }

    /**
     * @return Returns the peerAddress.
     */
    public String getPeerAddress() {
        return peerAddress;
    }

    /**
     * @param peerAddress The peerAddress to set.
     */
    public void setPeerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
    }

    /**
     * @param authMode The authMode to set.
     */
    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    /**
     * @return Returns the authMode.
     */
    public String getAuthMode() {
        return authMode;
    }

    /**
     * @param psk The psk to set.
     */
    public void setPsk(String psk) {
        this.psk = psk;
    }

    /**
     * @return Returns the psk.
     */
    public String getPsk() {
        return psk;
    }

    /**
     * @param ikePolicyId The ikePolicyId to set.
     */
    public void setIkePolicyId(String ikePolicyId) {
        this.ikePolicyId = ikePolicyId;
    }

    /**
     * @return Returns the ikePolicyId.
     */
    public String getIkePolicyId() {
        return ikePolicyId;
    }

    /**
     * @param ipsecPolicyId The ipsecPolicyId to set.
     */
    public void setIpsecPolicyId(String ipsecPolicyId) {
        this.ipsecPolicyId = ipsecPolicyId;
    }

    /**
     * @return Returns the ipsecPolicyId.
     */
    public String getIpsecPolicyId() {
        return ipsecPolicyId;
    }

    /**
     * @return Returns the ikePolicy.
     */
    public IkePolicy getIkePolicy() {
        return ikePolicy;
    }

    /**
     * @return Returns the peerNeId.
     */
    public String getPeerNeId() {
        return peerNeId;
    }

    /**
     * @param peerNeId The peerNeId to set.
     */
    public void setPeerNeId(String peerNeId) {
        this.peerNeId = peerNeId;
    }

    /**
     * @param ikePolicy The ikePolicy to set.
     */
    public void setIkePolicy(IkePolicy ikePolicy) {
        this.ikePolicy = ikePolicy;
    }

    /**
     * @return Returns the ipSecPolicy.
     */
    public IpSecPolicy getIpSecPolicy() {
        return ipSecPolicy;
    }

    /**
     * @param ipSecPolicy The ipSecPolicy to set.
     */
    public void setIpSecPolicy(IpSecPolicy ipSecPolicy) {
        this.ipSecPolicy = ipSecPolicy;
    }

}
