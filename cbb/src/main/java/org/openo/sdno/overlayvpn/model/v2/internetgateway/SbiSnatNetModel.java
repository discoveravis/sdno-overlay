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

package org.openo.sdno.overlayvpn.model.v2.internetgateway;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseServiceModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiSnatNetModel", propOrder = {"neId", "deviceId", "controllerId", "natId", "internetGatewayId",
                "subnetId", "ifName", "privateCidr", "privatePrefix", "aclNumber", "aclId", "startPublicIpAddress",
                "endPublicIpAddress", "qosPreNat", "createtime", "updatetime", "type"})

public class SbiSnatNetModel extends BaseServiceModel {

    @XmlElement(name = "neId")
    @ApiModelProperty(example = "null", required = true, value = "NE Id")
    private String neId = null;

    @XmlElement(name = "deviceId")
    @ApiModelProperty(example = "null", required = true, value = "device Id")
    private String deviceId = null;

    @XmlElement(name = "controllerId")
    @ApiModelProperty(example = "null", required = true, value = "controller Id")
    private String controllerId = null;

    @XmlElement(name = "natId")
    @ApiModelProperty(example = "null", value = "nat Id")
    private String natId = null;

    @XmlElement(name = "internetGatewayId")
    @ApiModelProperty(example = "null", required = true, value = "internet gateway Id")
    private String internetGatewayId = null;

    @XmlElement(name = "subnetId")
    @ApiModelProperty(example = "null", value = "subnet Id")
    private String subnetId = null;

    @XmlElement(name = "ifName")
    @ApiModelProperty(example = "null", required = true, value = "interface name")
    private String ifName = null;

    @XmlElement(name = "privateCidr")
    @ApiModelProperty(example = "null", value = "private IP address")
    private String privateCidr = null;

    @XmlElement(name = "privatePrefix")
    @ApiModelProperty(example = "null", value = "private prefix of IP address")
    private String privatePrefix = null;

    @XmlElement(name = "aclNumber")
    @ApiModelProperty(example = "null", required = true, value = "acl number")
    private String aclNumber = null;

    @XmlElement(name = "aclId")
    @ApiModelProperty(example = "null", value = "acl Id")
    private String aclId = null;

    @XmlElement(name = "startPublicIpAddress")
    @ApiModelProperty(example = "null", value = "start public IP address")
    private String startPublicIpAddress = null;

    @XmlElement(name = "endPublicIpAddress")
    @ApiModelProperty(example = "null", value = "end public IP address")
    private String endPublicIpAddress = null;

    @XmlElement(name = "qosPreNat")
    @ApiModelProperty(example = "null", value = "default false, true when vcpe")
    private String qosPreNat = null;

    @XmlElement(name = "createtime")
    @ApiModelProperty(example = "null", value = "create time")
    private Long createtime = null;

    @XmlElement(name = "updatetime")
    @ApiModelProperty(example = "null", value = "update time")
    private Long updatetime = null;

    @XmlElement(name = "type")
    @ApiModelProperty(example = "null", value = "the scope is none,create,updateIp")
    private String type = null;

    /**
     * NE Id
     * 
     * @return neId
     **/
    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    /**
     * device Id
     * 
     * @return deviceId
     **/
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * controller Id
     * 
     * @return controllerId
     **/
    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    /**
     * nat Id
     * 
     * @return natId
     **/
    public String getNatId() {
        return natId;
    }

    public void setNatId(String natId) {
        this.natId = natId;
    }

    /**
     * internet gateway Id
     * 
     * @return internetGatewayId
     **/
    public String getInternetGatewayId() {
        return internetGatewayId;
    }

    public void setInternetGatewayId(String internetGatewayId) {
        this.internetGatewayId = internetGatewayId;
    }

    /**
     * subnet Id
     * 
     * @return subnetId
     **/
    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    /**
     * interface name
     * 
     * @return ifName
     **/
    public String getIfName() {
        return ifName;
    }

    public void setIfName(String ifName) {
        this.ifName = ifName;
    }

    /**
     * private IP address
     * 
     * @return privateCidr
     **/
    public String getPrivateCidr() {
        return privateCidr;
    }

    public void setPrivateCidr(String privateCidr) {
        this.privateCidr = privateCidr;
    }

    /**
     * private prefix of IP address
     * 
     * @return privatePrefix
     **/
    public String getPrivatePrefix() {
        return privatePrefix;
    }

    public void setPrivatePrefix(String privatePrefix) {
        this.privatePrefix = privatePrefix;
    }

    /**
     * acl number
     * 
     * @return aclNumber
     **/
    public String getAclNumber() {
        return aclNumber;
    }

    public void setAclNumber(String aclNumber) {
        this.aclNumber = aclNumber;
    }

    /**
     * acl Id
     * 
     * @return aclId
     **/
    public String getAclId() {
        return aclId;
    }

    public void setAclId(String aclId) {
        this.aclId = aclId;
    }

    /**
     * start public IP address
     * 
     * @return startPublicIpAddress
     **/
    public String getStartPublicIpAddress() {
        return startPublicIpAddress;
    }

    public void setStartPublicIpAddress(String startPublicIpAddress) {
        this.startPublicIpAddress = startPublicIpAddress;
    }

    /**
     * end public IP address
     * 
     * @return endPublicIpAddress
     **/
    public String getEndPublicIpAddress() {
        return endPublicIpAddress;
    }

    public void setEndPublicIpAddress(String endPublicIpAddress) {
        this.endPublicIpAddress = endPublicIpAddress;
    }

    /**
     * default false, true when vcpe
     * 
     * @return qosPreNat
     **/
    public String getQosPreNat() {
        return qosPreNat;
    }

    public void setQosPreNat(String qosPreNat) {
        this.qosPreNat = qosPreNat;
    }

    /**
     * create time
     * 
     * @return createtime
     **/
    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    /**
     * update time
     * 
     * @return updatetime
     **/
    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * the scope is none,create,updateIp
     * 
     * @return type
     **/
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiSnatNetModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    neId: ").append(toIndentedString(neId)).append("\n");
        sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
        sb.append("    controllerId: ").append(toIndentedString(controllerId)).append("\n");
        sb.append("    natId: ").append(toIndentedString(natId)).append("\n");
        sb.append("    internetGatewayId: ").append(toIndentedString(internetGatewayId)).append("\n");
        sb.append("    subnetId: ").append(toIndentedString(subnetId)).append("\n");
        sb.append("    ifName: ").append(toIndentedString(ifName)).append("\n");
        sb.append("    privateCidr: ").append(toIndentedString(privateCidr)).append("\n");
        sb.append("    privatePrefix: ").append(toIndentedString(privatePrefix)).append("\n");
        sb.append("    aclNumber: ").append(toIndentedString(aclNumber)).append("\n");
        sb.append("    aclId: ").append(toIndentedString(aclId)).append("\n");
        sb.append("    startPublicIpAddress: ").append(toIndentedString(startPublicIpAddress)).append("\n");
        sb.append("    endPublicIpAddress: ").append(toIndentedString(endPublicIpAddress)).append("\n");
        sb.append("    qosPreNat: ").append(toIndentedString(qosPreNat)).append("\n");
        sb.append("    createtime: ").append(toIndentedString(createtime)).append("\n");
        sb.append("    updatetime: ").append(toIndentedString(updatetime)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
