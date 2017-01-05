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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiDeviceInfo", propOrder = {"id", "showTenant", "serviceIp", "neType", "version", "status", "gisLon",
                "gisLat", "vendor", "tenantId", "tenantName", "orgnizationId", "creator", "createTime", "registerTime",
                "modifier", "modifyTime"})

public class SbiDeviceInfo extends SbiDeviceCreateBasicInfo {

    @XmlElement(name = "id")
    @ApiModelProperty(example = "null", value = "the Id allocated by controller")
    private String id = null;

    @XmlElement(name = "showTenant")
    @ApiModelProperty(example = "null", value = "Whether the tenant is displayed in the query result")
    private String showTenant = null;

    @XmlElement(name = "serviceIp")
    @ApiModelProperty(example = "null", value = "device IP")
    private String serviceIp = null;

    @XmlElement(name = "neType")
    @ApiModelProperty(example = "null", value = "device type")
    private String neType = null;

    @XmlElement(name = "version")
    @ApiModelProperty(example = "null", value = "software version of the device")
    private String version = null;

    @XmlElement(name = "status")
    @ApiModelProperty(example = "null", value = "device status")
    private String status = null;

    @XmlElement(name = "gisLon")
    @ApiModelProperty(example = "null", value = "longitude of the device")
    private Double gisLon = null;

    @XmlElement(name = "gisLat")
    @ApiModelProperty(example = "null", value = "latitude of the device")
    private Double gisLat = null;

    @XmlElement(name = "vendor")
    @ApiModelProperty(example = "null", value = "device vendor")
    private String vendor = null;

    @XmlElement(name = "tenantId")
    @ApiModelProperty(example = "null", value = "tenant Id")
    private String tenantId = null;

    @XmlElement(name = "tenantName")
    @ApiModelProperty(example = "null", value = "tenant name")
    private String tenantName = null;

    @XmlElement(name = "orgnizationId")
    @ApiModelProperty(example = "null", value = "orgnization Id")
    private String orgnizationId = null;

    @XmlElement(name = "creator")
    @ApiModelProperty(example = "null", value = "creator")
    private String creator = null;

    @XmlElement(name = "createTime")
    @ApiModelProperty(example = "null", value = "create time")
    private String createTime = null;

    @XmlElement(name = "registerTime")
    @ApiModelProperty(example = "null", value = "register time")
    private String registerTime = null;

    @XmlElement(name = "modifier")
    @ApiModelProperty(example = "null", value = "modifier")
    private String modifier = null;

    @XmlElement(name = "modifyTime")
    @ApiModelProperty(example = "null", value = "modify time")
    private String modifyTime = null;

    /**
     * the Id allocated by controller
     * 
     * @return id
     **/
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Whether the tenant is displayed in the query result
     * 
     * @return showTenant
     **/
    public String getShowTenant() {
        return showTenant;
    }

    public void setShowTenant(String showTenant) {
        this.showTenant = showTenant;
    }

    /**
     * device IP
     * 
     * @return serviceIp
     **/
    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    /**
     * device type
     * 
     * @return neType
     **/
    public String getNeType() {
        return neType;
    }

    public void setNeType(String neType) {
        this.neType = neType;
    }

    /**
     * software version of the device
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
     * device status
     * 
     * @return status
     **/
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * longitude of the device
     * 
     * @return gisLon
     **/
    public Double getGisLon() {
        return gisLon;
    }

    public void setGisLon(Double gisLon) {
        this.gisLon = gisLon;
    }

    /**
     * latitude of the device
     * 
     * @return gisLat
     **/
    public Double getGisLat() {
        return gisLat;
    }

    public void setGisLat(Double gisLat) {
        this.gisLat = gisLat;
    }

    /**
     * device vendor
     * 
     * @return vendor
     **/
    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * tenant Id
     * 
     * @return tenantId
     **/
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * tenant name
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
     * orgnization Id
     * 
     * @return orgnizationId
     **/
    public String getOrgnizationId() {
        return orgnizationId;
    }

    public void setOrgnizationId(String orgnizationId) {
        this.orgnizationId = orgnizationId;
    }

    /**
     * creator
     * 
     * @return creator
     **/
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * create time
     * 
     * @return createTime
     **/
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * register time
     * 
     * @return registerTime
     **/
    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * modifier
     * 
     * @return modifier
     **/
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * modify time
     * 
     * @return modifyTime
     **/
    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiDeviceInfo {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    showTenant: ").append(toIndentedString(showTenant)).append("\n");
        sb.append("    serviceIp: ").append(toIndentedString(serviceIp)).append("\n");
        sb.append("    neType: ").append(toIndentedString(neType)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("    status: ").append(toIndentedString(status)).append("\n");
        sb.append("    gisLon: ").append(toIndentedString(gisLon)).append("\n");
        sb.append("    gisLat: ").append(toIndentedString(gisLat)).append("\n");
        sb.append("    vendor: ").append(toIndentedString(vendor)).append("\n");
        sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
        sb.append("    tenantName: ").append(toIndentedString(tenantName)).append("\n");
        sb.append("    orgnizationId: ").append(toIndentedString(orgnizationId)).append("\n");
        sb.append("    creator: ").append(toIndentedString(creator)).append("\n");
        sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
        sb.append("    registerTime: ").append(toIndentedString(registerTime)).append("\n");
        sb.append("    modifier: ").append(toIndentedString(modifier)).append("\n");
        sb.append("    modifyTime: ").append(toIndentedString(modifyTime)).append("\n");
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
