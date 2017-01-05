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

package org.openo.sdno.overlayvpn.model.v2.cpe;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.NvString;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NbiNeMo", propOrder = {"version", "description", "logicId", "phyNeId", "managementDomainId",
                "controllerId", "siteId", "productName", "isVirtual", "ipAddress", "source", "owner", "location",
                "serialNumber", "manufacturer", "manufactureDate", "adminState", "operState", "nativeId",
                "accessIpVersion", "neRole", "additionalInfo"})

public class NbiNeMo extends BaseMo {

    @XmlElement(name = "version")
    @ApiModelProperty(example = "null", value = "version")
    private String version = null;

    @XmlElement(name = "description")
    @ApiModelProperty(example = "null", value = "description")
    private String description = null;

    @XmlElement(name = "logicId")
    @ApiModelProperty(example = "null", value = "logic id of ne")
    private String logicId = null;

    @XmlElement(name = "phyNeId")
    @ApiModelProperty(example = "null", value = "physical ne id")
    private String phyNeId = null;

    @XmlElement(name = "managementDomainId")
    @ApiModelProperty(example = "null", value = "management Domain Id")
    private List<String> managementDomainId = new ArrayList<String>();

    @XmlElement(name = "controllerId")
    @ApiModelProperty(example = "null", value = "controller Id list")
    private List<String> controllerId = new ArrayList<String>();

    @XmlElement(name = "siteId")
    @ApiModelProperty(example = "null", value = "site id list")
    private List<String> siteId = new ArrayList<String>();

    @XmlElement(name = "productName")
    @ApiModelProperty(example = "null", value = "product name")
    private String productName = null;

    @XmlElement(name = "isVirtual")
    @ApiModelProperty(example = "null", value = "whether is a virtual ne")
    private String isVirtual = null;

    @XmlElement(name = "ipAddress")
    @ApiModelProperty(example = "null", required = true, value = "ip Address")
    private String ipAddress = null;

    @XmlElement(name = "source")
    @ApiModelProperty(example = "null", value = "(scope=network_me,os,network_ems,user)")
    private String source = null;

    @XmlElement(name = "owner")
    @ApiModelProperty(example = "null", value = "owner")
    private String owner = null;

    @XmlElement(name = "location")
    @ApiModelProperty(example = "null", value = "location")
    private String location = null;

    @XmlElement(name = "serialNumber")
    @ApiModelProperty(example = "null", value = "serial Number")
    private String serialNumber = null;

    @XmlElement(name = "manufacturer")
    @ApiModelProperty(example = "null", value = "manufacturer")
    private String manufacturer = null;

    @XmlElement(name = "manufactureDate")
    @ApiModelProperty(example = "null", value = "manufacture Date")
    private String manufactureDate = null;

    @XmlElement(name = "adminState")
    @ApiModelProperty(example = "null", value = "(scope=active,inactive)")
    private String adminState = null;

    @XmlElement(name = "operState")
    @ApiModelProperty(example = "null", value = "(scope=up,down,unknown)")
    private String operState = null;

    @XmlElement(name = "nativeId")
    @ApiModelProperty(example = "null", value = "native id")
    private String nativeId = null;

    @XmlElement(name = "accessIpVersion")
    @ApiModelProperty(example = "null", value = "(scope=ipv4,ipv6,ipv4/ipv6)")
    private String accessIpVersion = null;

    @XmlElement(name = "neRole")
    @ApiModelProperty(example = "null", value = "ne role")
    private String neRole = null;

    @XmlElement(name = "additionalInfo")
    @ApiModelProperty(example = "null", value = "additional info")
    private List<NvString> additionalInfo = new ArrayList<NvString>();

    /**
     * version
     * 
     * @return version
     **/
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * description
     * 
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * logic id of ne
     * 
     * @return logicId
     **/
    public String getLogicId() {
        return logicId;
    }

    public void setLogicId(String logicId) {
        this.logicId = logicId;
    }

    /**
     * physical ne id
     * 
     * @return phyNeId
     **/
    public String getPhyNeId() {
        return phyNeId;
    }

    public void setPhyNeId(String phyNeId) {
        this.phyNeId = phyNeId;
    }

    /**
     * management Domain Id
     * 
     * @return managementDomainId
     **/
    public List<String> getManagementDomainId() {
        return managementDomainId;
    }

    public void setManagementDomainId(List<String> managementDomainId) {
        this.managementDomainId = managementDomainId;
    }

    /**
     * controller Id list
     * 
     * @return controllerId
     **/
    public List<String> getControllerId() {
        return controllerId;
    }

    public void setControllerId(List<String> controllerId) {
        this.controllerId = controllerId;
    }

    /**
     * site id list
     * 
     * @return siteId
     **/
    public List<String> getSiteId() {
        return siteId;
    }

    public void setSiteId(List<String> siteId) {
        this.siteId = siteId;
    }

    /**
     * product name
     * 
     * @return productName
     **/
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * whether is a virtual ne
     * 
     * @return isVirtual
     **/
    public String getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(String isVirtual) {
        this.isVirtual = isVirtual;
    }

    /**
     * ip Address
     * 
     * @return ipAddress
     **/
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * (scope=network_me,os,network_ems,user)
     * 
     * @return source
     **/
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * owner
     * 
     * @return owner
     **/
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * location
     * 
     * @return location
     **/
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * serial Number
     * 
     * @return serialNumber
     **/
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * manufacturer
     * 
     * @return manufacturer
     **/
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * manufacture Date
     * 
     * @return manufactureDate
     **/
    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    /**
     * (scope=active,inactive)
     * 
     * @return adminState
     **/
    public String getAdminState() {
        return adminState;
    }

    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    /**
     * (scope=up,down,unknown)
     * 
     * @return operState
     **/
    public String getOperState() {
        return operState;
    }

    public void setOperState(String operState) {
        this.operState = operState;
    }

    /**
     * native id
     * 
     * @return nativeId
     **/
    public String getNativeId() {
        return nativeId;
    }

    public void setNativeId(String nativeId) {
        this.nativeId = nativeId;
    }

    /**
     * (scope=ipv4,ipv6,ipv4/ipv6)
     * 
     * @return accessIpVersion
     **/
    public String getAccessIpVersion() {
        return accessIpVersion;
    }

    public void setAccessIpVersion(String accessIpVersion) {
        this.accessIpVersion = accessIpVersion;
    }

    /**
     * ne role
     * 
     * @return neRole
     **/
    public String getNeRole() {
        return neRole;
    }

    public void setNeRole(String neRole) {
        this.neRole = neRole;
    }

    /**
     * additional info
     * 
     * @return additionalInfo
     **/
    public List<NvString> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(List<NvString> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiNeMo {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    logicId: ").append(toIndentedString(logicId)).append("\n");
        sb.append("    phyNeId: ").append(toIndentedString(phyNeId)).append("\n");
        sb.append("    managementDomainId: ").append(toIndentedString(managementDomainId)).append("\n");
        sb.append("    controllerId: ").append(toIndentedString(controllerId)).append("\n");
        sb.append("    siteId: ").append(toIndentedString(siteId)).append("\n");
        sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
        sb.append("    isVirtual: ").append(toIndentedString(isVirtual)).append("\n");
        sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
        sb.append("    source: ").append(toIndentedString(source)).append("\n");
        sb.append("    owner: ").append(toIndentedString(owner)).append("\n");
        sb.append("    location: ").append(toIndentedString(location)).append("\n");
        sb.append("    serialNumber: ").append(toIndentedString(serialNumber)).append("\n");
        sb.append("    manufacturer: ").append(toIndentedString(manufacturer)).append("\n");
        sb.append("    manufactureDate: ").append(toIndentedString(manufactureDate)).append("\n");
        sb.append("    adminState: ").append(toIndentedString(adminState)).append("\n");
        sb.append("    operState: ").append(toIndentedString(operState)).append("\n");
        sb.append("    nativeId: ").append(toIndentedString(nativeId)).append("\n");
        sb.append("    accessIpVersion: ").append(toIndentedString(accessIpVersion)).append("\n");
        sb.append("    neRole: ").append(toIndentedString(neRole)).append("\n");
        sb.append("    additionalInfo: ").append(toIndentedString(additionalInfo)).append("\n");
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
