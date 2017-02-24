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

package org.openo.sdno.overlayvpn.model.v2.ipsec;

import java.util.List;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * NBI IpSec model.<br/>
 * 
 * @author
 * @version SDNO 0.5 Jan 16, 2017
 */
@MOResType(infoModelName = "ipsec_nbi_ipsec")
public class NbiIpSec extends BaseModel {

    @AUuid(require = true)
    private String srcNeId;

    @AUuid(require = true)
    private String connectionId;

    @AString(require = true, scope = "localcpe,cloudcpe,vpc,dc-r")
    private String srcNeRole;

    @AString(require = true, scope = "localcpe,cloudcpe,vpc,dc-r")
    private String destNeRole;

    @AString(require = true, scope = "ipsec,ipv6_over_ipv4,ipv4_over_ipv6")
    private String type;

    @AUuid(require = true)
    private String destNeId;

    @AString(require = false, min = 1, max = 255)
    private String srcPortName;

    @AString(require = false, min = 1, max = 255)
    private String destPortName;

    @AString(require = true, scope = "work,protect")
    private String workType;

    @AString(require = true, scope = "nqa,none")
    private String protectionPolicy;

    private String nqa;

    @AString(require = true, min = 1, max = 1024)
    private String ikePolicy;

    @AString(require = true, min = 1, max = 255)
    private String ipsecPolicy;

    @AString(require = true, scope = "true,false")
    private String srcIsTemplateType;

    @AString(require = true, scope = "true,false")
    private String destIsTemplateType;

    @AString(require = false, min = 1, max = 255)
    private String ruleSrcPortName;

    @AString(require = false, min = 1, max = 255)
    private String ruleDestPortName;

    @AString(min = 0, max = 4096)
    private String sourceLanCidrs;

    @AString(min = 0, max = 4096)
    private String destLanCidrs;

    @AString(scope = "true,false")
    private String qosPreClassify;

    private String regionId;

    @NONInvField
    private String srcPortIp;

    @NONInvField
    private String destPortIp;

    // below parameters are not in yaml
    @JsonIgnore
    @NONInvField
    private SbiIp srcIp;

    @JsonIgnore
    @NONInvField
    private SbiIp destIp;

    @JsonIgnore
    @NONInvField
    @Valid
    private List<SbiIp> ruleSrcIp;

    @JsonIgnore
    @NONInvField
    @Valid
    private List<SbiIp> ruleDestIp;

    @JsonIgnore
    @NONInvField
    private String srcDeviceId;

    @JsonIgnore
    @NONInvField
    private String destDeviceId;

    @JsonIgnore
    @NONInvField
    @Valid
    private SbiIkePolicy ikePolicyData;

    @JsonIgnore
    @NONInvField
    @Valid
    private SbiIpSecPolicy ipSecPolicyData;

    public String getSrcNeId() {
        return srcNeId;
    }

    public void setSrcNeId(String srcNeId) {
        this.srcNeId = srcNeId;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getSrcNeRole() {
        return srcNeRole;
    }

    public void setSrcNeRole(String srcNeRole) {
        this.srcNeRole = srcNeRole;
    }

    public String getDestNeRole() {
        return destNeRole;
    }

    public void setDestNeRole(String destNeRole) {
        this.destNeRole = destNeRole;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDestNeId() {
        return destNeId;
    }

    public void setDestNeId(String destNeId) {
        this.destNeId = destNeId;
    }

    public String getSrcPortName() {
        return srcPortName;
    }

    public void setSrcPortName(String srcPortName) {
        this.srcPortName = srcPortName;
    }

    public String getDestPortName() {
        return destPortName;
    }

    public void setDestPortName(String destPortName) {
        this.destPortName = destPortName;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getProtectionPolicy() {
        return protectionPolicy;
    }

    public void setProtectionPolicy(String protectionPolicy) {
        this.protectionPolicy = protectionPolicy;
    }

    public String getNqa() {
        return nqa;
    }

    public void setNqa(String nqa) {
        this.nqa = nqa;
    }

    public String getIkePolicy() {
        return ikePolicy;
    }

    public void setIkePolicy(String ikePolicy) {
        this.ikePolicy = ikePolicy;
    }

    public String getIpsecPolicy() {
        return ipsecPolicy;
    }

    public void setIpsecPolicy(String ipsecPolicy) {
        this.ipsecPolicy = ipsecPolicy;
    }

    public String getSrcIsTemplateType() {
        return srcIsTemplateType;
    }

    public void setSrcIsTemplateType(String srcIsTemplateType) {
        this.srcIsTemplateType = srcIsTemplateType;
    }

    public String getDestIsTemplateType() {
        return destIsTemplateType;
    }

    public void setDestIsTemplateType(String destIsTemplateType) {
        this.destIsTemplateType = destIsTemplateType;
    }

    public String getRuleSrcPortName() {
        return ruleSrcPortName;
    }

    public void setRuleSrcPortName(String ruleSrcPortName) {
        this.ruleSrcPortName = ruleSrcPortName;
    }

    public String getRuleDestPortName() {
        return ruleDestPortName;
    }

    public void setRuleDestPortName(String ruleDestPortName) {
        this.ruleDestPortName = ruleDestPortName;
    }

    public String getSourceLanCidrs() {
        return sourceLanCidrs;
    }

    public void setSourceLanCidrs(String sourceLanCidrs) {
        this.sourceLanCidrs = sourceLanCidrs;
    }

    public String getDestLanCidrs() {
        return destLanCidrs;
    }

    public void setDestLanCidrs(String destLanCidrs) {
        this.destLanCidrs = destLanCidrs;
    }

    public String getQosPreClassify() {
        return qosPreClassify;
    }

    public void setQosPreClassify(String qosPreClassify) {
        this.qosPreClassify = qosPreClassify;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSrcPortIp() {
        return srcPortIp;
    }

    public void setSrcPortIp(String srcPortIp) {
        this.srcPortIp = srcPortIp;
    }

    public String getDestPortIp() {
        return destPortIp;
    }

    public void setDestPortIp(String destPortIp) {
        this.destPortIp = destPortIp;
    }

    public SbiIp getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(SbiIp srcIp) {
        this.srcIp = srcIp;
    }

    public SbiIp getDestIp() {
        return destIp;
    }

    public void setDestIp(SbiIp destIp) {
        this.destIp = destIp;
    }

    public List<SbiIp> getRuleSrcIp() {
        return ruleSrcIp;
    }

    public void setRuleSrcIp(List<SbiIp> ruleSrcIp) {
        this.ruleSrcIp = ruleSrcIp;
    }

    public List<SbiIp> getRuleDestIp() {
        return ruleDestIp;
    }

    public void setRuleDestIp(List<SbiIp> ruleDestIp) {
        this.ruleDestIp = ruleDestIp;
    }

    public String getSrcDeviceId() {
        return srcDeviceId;
    }

    public void setSrcDeviceId(String srcDeviceId) {
        this.srcDeviceId = srcDeviceId;
    }

    public String getDestDeviceId() {
        return destDeviceId;
    }

    public void setDestDeviceId(String destDeviceId) {
        this.destDeviceId = destDeviceId;
    }

    public SbiIkePolicy getIkePolicyData() {
        return ikePolicyData;
    }

    public void setIkePolicyData(SbiIkePolicy ikePolicyData) {
        this.ikePolicyData = ikePolicyData;
    }

    public SbiIpSecPolicy getIpSecPolicyData() {
        return ipSecPolicyData;
    }

    public void setIpSecPolicyData(SbiIpSecPolicy ipSecPolicyData) {
        this.ipSecPolicyData = ipSecPolicyData;
    }

}
