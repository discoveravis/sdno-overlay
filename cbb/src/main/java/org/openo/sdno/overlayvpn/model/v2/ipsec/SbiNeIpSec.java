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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseNetModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiNeIpSec", propOrder = {"externalIpSecId", "sourceIfName", "destIfName", "sourceAddress",
                "peerAddress", "ikePolicy", "ipSecPolicy", "workType", "sourceLanCidrs", "peerLanCidrs",
                "isTemplateType", "nqa", "localNeRole", "tenantName", "protectionPolicy", "qosPreClassify", "regionId"})

@ApiModel(description = "Sbi Ipsec data model.")
public class SbiNeIpSec extends BaseNetModel {

    @XmlElement(name = "externalIpSecId")
    @ApiModelProperty(example = "null", value = "")
    private String externalIpSecId = null;

    @XmlElement(name = "sourceIfName")
    @ApiModelProperty(example = "null", required = true, value = "")
    private String sourceIfName = null;

    @XmlElement(name = "destIfName")
    @ApiModelProperty(example = "null", value = "")
    private String destIfName = null;

    @XmlElement(name = "sourceAddress")
    @ApiModelProperty(example = "null", required = true, value = "")
    private String sourceAddress = null;

    @XmlElement(name = "peerAddress")
    @ApiModelProperty(example = "null", required = true, value = "")
    private String peerAddress = null;

    @XmlElement(name = "ikePolicy")
    @ApiModelProperty(example = "null", value = "")
    private SbiIkePolicy ikePolicy = null;

    @XmlElement(name = "ipSecPolicy")
    @ApiModelProperty(example = "null", value = "")
    private SbiIpSecPolicy ipSecPolicy = null;

    @XmlElement(name = "workType")
    @ApiModelProperty(example = "null", required = true, value = "work type(work,protect)")
    private String workType = null;

    @XmlElement(name = "sourceLanCidrs")
    @ApiModelProperty(example = "null", value = "")
    private String sourceLanCidrs = null;

    @XmlElement(name = "peerLanCidrs")
    @ApiModelProperty(example = "null", value = "")
    private String peerLanCidrs = null;

    @XmlElement(name = "isTemplateType")
    @ApiModelProperty(example = "null", required = true, value = "boolean(true,false)")
    private String isTemplateType = null;

    @XmlElement(name = "nqa")
    @ApiModelProperty(example = "null", value = "")
    private String nqa = null;

    @XmlElement(name = "localNeRole")
    @ApiModelProperty(example = "null", required = true, value = "local ne role(thincpe,cloudcpe,vpc)")
    private String localNeRole = null;

    @XmlElement(name = "tenantName")
    @ApiModelProperty(example = "null", value = "")
    private String tenantName = null;

    @XmlElement(name = "protectionPolicy")
    @ApiModelProperty(example = "null", value = "")
    private String protectionPolicy = null;

    @XmlElement(name = "qosPreClassify")
    @ApiModelProperty(example = "null", value = "boolean(true,false)")
    private String qosPreClassify = null;

    @XmlElement(name = "regionId")
    @ApiModelProperty(example = "null", value = "")
    private String regionId = null;

    /**
     * Get externalIpSecId
     * 
     * @return externalIpSecId
     **/
    public String getExternalIpSecId() {
        return externalIpSecId;
    }

    public void setExternalIpSecId(String externalIpSecId) {
        this.externalIpSecId = externalIpSecId;
    }

    /**
     * Get sourceIfName
     * 
     * @return sourceIfName
     **/
    public String getSourceIfName() {
        return sourceIfName;
    }

    public void setSourceIfName(String sourceIfName) {
        this.sourceIfName = sourceIfName;
    }

    /**
     * Get destIfName
     * 
     * @return destIfName
     **/
    public String getDestIfName() {
        return destIfName;
    }

    public void setDestIfName(String destIfName) {
        this.destIfName = destIfName;
    }

    /**
     * Get sourceAddress
     * 
     * @return sourceAddress
     **/
    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * Get peerAddress
     * 
     * @return peerAddress
     **/
    public String getPeerAddress() {
        return peerAddress;
    }

    public void setPeerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
    }

    /**
     * Get ikePolicy
     * 
     * @return ikePolicy
     **/
    public SbiIkePolicy getIkePolicy() {
        return ikePolicy;
    }

    public void setIkePolicy(SbiIkePolicy ikePolicy) {
        this.ikePolicy = ikePolicy;
    }

    /**
     * Get ipSecPolicy
     * 
     * @return ipSecPolicy
     **/
    public SbiIpSecPolicy getIpSecPolicy() {
        return ipSecPolicy;
    }

    public void setIpSecPolicy(SbiIpSecPolicy ipSecPolicy) {
        this.ipSecPolicy = ipSecPolicy;
    }

    /**
     * work type(work,protect)
     * 
     * @return workType
     **/
    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    /**
     * Get sourceLanCidrs
     * 
     * @return sourceLanCidrs
     **/
    public String getSourceLanCidrs() {
        return sourceLanCidrs;
    }

    public void setSourceLanCidrs(String sourceLanCidrs) {
        this.sourceLanCidrs = sourceLanCidrs;
    }

    /**
     * Get peerLanCidrs
     * 
     * @return peerLanCidrs
     **/
    public String getPeerLanCidrs() {
        return peerLanCidrs;
    }

    public void setPeerLanCidrs(String peerLanCidrs) {
        this.peerLanCidrs = peerLanCidrs;
    }

    /**
     * boolean(true,false)
     * 
     * @return isTemplateType
     **/
    public String getIsTemplateType() {
        return isTemplateType;
    }

    public void setIsTemplateType(String isTemplateType) {
        this.isTemplateType = isTemplateType;
    }

    /**
     * Get nqa
     * 
     * @return nqa
     **/
    public String getNqa() {
        return nqa;
    }

    public void setNqa(String nqa) {
        this.nqa = nqa;
    }

    /**
     * local ne role(thincpe,cloudcpe,vpc)
     * 
     * @return localNeRole
     **/
    public String getLocalNeRole() {
        return localNeRole;
    }

    public void setLocalNeRole(String localNeRole) {
        this.localNeRole = localNeRole;
    }

    /**
     * Get tenantName
     * 
     * @return tenantName
     **/
    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    /**
     * Get protectionPolicy
     * 
     * @return protectionPolicy
     **/
    public String getProtectionPolicy() {
        return protectionPolicy;
    }

    public void setProtectionPolicy(String protectionPolicy) {
        this.protectionPolicy = protectionPolicy;
    }

    /**
     * boolean(true,false)
     * 
     * @return qosPreClassify
     **/
    public String getQosPreClassify() {
        return qosPreClassify;
    }

    public void setQosPreClassify(String qosPreClassify) {
        this.qosPreClassify = qosPreClassify;
    }

    /**
     * Get regionId
     * 
     * @return regionId
     **/
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiNeIpSec {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    externalIpSecId: ").append(toIndentedString(externalIpSecId)).append("\n");
        sb.append("    sourceIfName: ").append(toIndentedString(sourceIfName)).append("\n");
        sb.append("    destIfName: ").append(toIndentedString(destIfName)).append("\n");
        sb.append("    sourceAddress: ").append(toIndentedString(sourceAddress)).append("\n");
        sb.append("    peerAddress: ").append(toIndentedString(peerAddress)).append("\n");
        sb.append("    ikePolicy: ").append(toIndentedString(ikePolicy)).append("\n");
        sb.append("    ipSecPolicy: ").append(toIndentedString(ipSecPolicy)).append("\n");
        sb.append("    workType: ").append(toIndentedString(workType)).append("\n");
        sb.append("    sourceLanCidrs: ").append(toIndentedString(sourceLanCidrs)).append("\n");
        sb.append("    peerLanCidrs: ").append(toIndentedString(peerLanCidrs)).append("\n");
        sb.append("    isTemplateType: ").append(toIndentedString(isTemplateType)).append("\n");
        sb.append("    nqa: ").append(toIndentedString(nqa)).append("\n");
        sb.append("    localNeRole: ").append(toIndentedString(localNeRole)).append("\n");
        sb.append("    tenantName: ").append(toIndentedString(tenantName)).append("\n");
        sb.append("    protectionPolicy: ").append(toIndentedString(protectionPolicy)).append("\n");
        sb.append("    qosPreClassify: ").append(toIndentedString(qosPreClassify)).append("\n");
        sb.append("    regionId: ").append(toIndentedString(regionId)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(Object o) {
        if(o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
