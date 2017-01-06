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
import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NbiCloudCpeModel", propOrder = {"tenantId", "siteId", "popId", "vCpeId", "name", "esn", "mgrIp",
                "mgrMask", "mgrGatewayIp", "controllerId", "controllerIp", "controllerMask", "description", "vendor",
                "type", "vnfdVersion", "callbackUrl", "vnfdId", "vnfId", "vnfmId", "dcLocation", "rangeId",
                "referenceCount", "adminState", "actionState", "template", "additionalInfo"})

public class NbiCloudCpeModel extends UuidModel {

    @XmlElement(name = "tenantId")
    @ApiModelProperty(example = "null", required = true, value = "tenant id")
    private String tenantId = null;

    @XmlElement(name = "siteId")
    @ApiModelProperty(example = "null", required = true, value = "site id")
    private String siteId = null;

    @XmlElement(name = "popId")
    @ApiModelProperty(example = "null", value = "pop id")
    private String popId = null;

    @XmlElement(name = "vCpeId")
    @ApiModelProperty(example = "null", value = "vcpe id")
    private String vCpeId = null;

    @XmlElement(name = "name")
    @ApiModelProperty(example = "null", value = "name")
    private String name = null;

    @XmlElement(name = "esn")
    @ApiModelProperty(example = "null", value = "esn")
    private String esn = null;

    @XmlElement(name = "mgrIp")
    @ApiModelProperty(example = "null", value = "mgr ip")
    private String mgrIp = null;

    @XmlElement(name = "mgrMask")
    @ApiModelProperty(example = "null", value = "mask of mgr ip")
    private String mgrMask = null;

    @XmlElement(name = "mgrGatewayIp")
    @ApiModelProperty(example = "null", value = "mgr gateway ip")
    private String mgrGatewayIp = null;

    @XmlElement(name = "controllerId")
    @ApiModelProperty(example = "null", value = "controller id")
    private String controllerId = null;

    @XmlElement(name = "controllerIp")
    @ApiModelProperty(example = "null", value = "controller ip")
    private String controllerIp = null;

    @XmlElement(name = "controllerMask")
    @ApiModelProperty(example = "null", value = "controller mask")
    private String controllerMask = null;

    @XmlElement(name = "description")
    @ApiModelProperty(example = "null", value = "description")
    private String description = null;

    @XmlElement(name = "vendor")
    @ApiModelProperty(example = "null", value = "vendor")
    private String vendor = null;

    @XmlElement(name = "type")
    @ApiModelProperty(example = "null", value = "type")
    private String type = null;

    @XmlElement(name = "vnfdVersion")
    @ApiModelProperty(example = "null", value = "vnfd Version")
    private String vnfdVersion = null;

    @XmlElement(name = "callbackUrl")
    @ApiModelProperty(example = "null", value = "callback Url")
    private String callbackUrl = null;

    @XmlElement(name = "vnfdId")
    @ApiModelProperty(example = "null", value = "vnfd id")
    private String vnfdId = null;

    @XmlElement(name = "vnfId")
    @ApiModelProperty(example = "null", value = "vnf id")
    private String vnfId = null;

    @XmlElement(name = "vnfmId")
    @ApiModelProperty(example = "null", value = "vnfm id")
    private String vnfmId = null;

    @XmlElement(name = "dcLocation")
    @ApiModelProperty(example = "null", value = "dc location")
    private String dcLocation = null;

    @XmlElement(name = "rangeId")
    @ApiModelProperty(example = "null", value = "range id")
    private String rangeId = null;

    @XmlElement(name = "referenceCount")
    @ApiModelProperty(example = "null", value = "reference count")
    private Integer referenceCount = null;

    @XmlElement(name = "adminState")
    @ApiModelProperty(example = "null", value = "scope = Active, Stopped, Building")
    private String adminState = null;

    @XmlElement(name = "actionState")
    @ApiModelProperty(example = "null", value = "(scope = none, normal, creating, deleting, updating, create_exception, update_exception, delete_exception, deploying, deploy_exception, undeploying, undeploy_exception, checking, check_exception)")
    private String actionState = null;

    @XmlElement(name = "template")
    @ApiModelProperty(example = "null", value = "template")
    private String template = null;

    @XmlElement(name = "additionalInfo")
    @ApiModelProperty(example = "null", value = "additional info")
    private List<NvString> additionalInfo = new ArrayList<NvString>();

    /**
     * tenant id
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
     * site id
     * 
     * @return siteId
     **/
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    /**
     * pop id
     * 
     * @return popId
     **/
    public String getPopId() {
        return popId;
    }

    public void setPopId(String popId) {
        this.popId = popId;
    }

    /**
     * vcpe id
     * 
     * @return vCpeId
     **/
    public String getVCpeId() {
        return vCpeId;
    }

    public void setVCpeId(String vCpeId) {
        this.vCpeId = vCpeId;
    }

    /**
     * name
     * 
     * @return name
     **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * esn
     * 
     * @return esn
     **/
    public String getEsn() {
        return esn;
    }

    public void setEsn(String esn) {
        this.esn = esn;
    }

    /**
     * mgr ip
     * 
     * @return mgrIp
     **/
    public String getMgrIp() {
        return mgrIp;
    }

    public void setMgrIp(String mgrIp) {
        this.mgrIp = mgrIp;
    }

    /**
     * mask of mgr ip
     * 
     * @return mgrMask
     **/
    public String getMgrMask() {
        return mgrMask;
    }

    public void setMgrMask(String mgrMask) {
        this.mgrMask = mgrMask;
    }

    /**
     * mgr gateway ip
     * 
     * @return mgrGatewayIp
     **/
    public String getMgrGatewayIp() {
        return mgrGatewayIp;
    }

    public void setMgrGatewayIp(String mgrGatewayIp) {
        this.mgrGatewayIp = mgrGatewayIp;
    }

    /**
     * controller id
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
     * controller ip
     * 
     * @return controllerIp
     **/
    public String getControllerIp() {
        return controllerIp;
    }

    public void setControllerIp(String controllerIp) {
        this.controllerIp = controllerIp;
    }

    /**
     * controller mask
     * 
     * @return controllerMask
     **/
    public String getControllerMask() {
        return controllerMask;
    }

    public void setControllerMask(String controllerMask) {
        this.controllerMask = controllerMask;
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
     * vendor
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
     * type
     * 
     * @return type
     **/
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * vnfd Version
     * 
     * @return vnfdVersion
     **/
    public String getVnfdVersion() {
        return vnfdVersion;
    }

    public void setVnfdVersion(String vnfdVersion) {
        this.vnfdVersion = vnfdVersion;
    }

    /**
     * callback Url
     * 
     * @return callbackUrl
     **/
    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    /**
     * vnfd id
     * 
     * @return vnfdId
     **/
    public String getVnfdId() {
        return vnfdId;
    }

    public void setVnfdId(String vnfdId) {
        this.vnfdId = vnfdId;
    }

    /**
     * vnf id
     * 
     * @return vnfId
     **/
    public String getVnfId() {
        return vnfId;
    }

    public void setVnfId(String vnfId) {
        this.vnfId = vnfId;
    }

    /**
     * vnfm id
     * 
     * @return vnfmId
     **/
    public String getVnfmId() {
        return vnfmId;
    }

    public void setVnfmId(String vnfmId) {
        this.vnfmId = vnfmId;
    }

    /**
     * dc location
     * 
     * @return dcLocation
     **/
    public String getDcLocation() {
        return dcLocation;
    }

    public void setDcLocation(String dcLocation) {
        this.dcLocation = dcLocation;
    }

    /**
     * range id
     * 
     * @return rangeId
     **/
    public String getRangeId() {
        return rangeId;
    }

    public void setRangeId(String rangeId) {
        this.rangeId = rangeId;
    }

    /**
     * reference count
     * 
     * @return referenceCount
     **/
    public Integer getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(Integer referenceCount) {
        this.referenceCount = referenceCount;
    }

    /**
     * scope = Active, Stopped, Building
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
     * (scope = none, normal, creating, deleting, updating, create_exception, update_exception,
     * delete_exception, deploying, deploy_exception, undeploying, undeploy_exception, checking,
     * check_exception)
     * 
     * @return actionState
     **/
    public String getActionState() {
        return actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }

    /**
     * template
     * 
     * @return template
     **/
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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
        sb.append("class NbiCloudCpeModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
        sb.append("    siteId: ").append(toIndentedString(siteId)).append("\n");
        sb.append("    popId: ").append(toIndentedString(popId)).append("\n");
        sb.append("    vCpeId: ").append(toIndentedString(vCpeId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    esn: ").append(toIndentedString(esn)).append("\n");
        sb.append("    mgrIp: ").append(toIndentedString(mgrIp)).append("\n");
        sb.append("    mgrMask: ").append(toIndentedString(mgrMask)).append("\n");
        sb.append("    mgrGatewayIp: ").append(toIndentedString(mgrGatewayIp)).append("\n");
        sb.append("    controllerId: ").append(toIndentedString(controllerId)).append("\n");
        sb.append("    controllerIp: ").append(toIndentedString(controllerIp)).append("\n");
        sb.append("    controllerMask: ").append(toIndentedString(controllerMask)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    vendor: ").append(toIndentedString(vendor)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    vnfdVersion: ").append(toIndentedString(vnfdVersion)).append("\n");
        sb.append("    callbackUrl: ").append(toIndentedString(callbackUrl)).append("\n");
        sb.append("    vnfdId: ").append(toIndentedString(vnfdId)).append("\n");
        sb.append("    vnfId: ").append(toIndentedString(vnfId)).append("\n");
        sb.append("    vnfmId: ").append(toIndentedString(vnfmId)).append("\n");
        sb.append("    dcLocation: ").append(toIndentedString(dcLocation)).append("\n");
        sb.append("    rangeId: ").append(toIndentedString(rangeId)).append("\n");
        sb.append("    referenceCount: ").append(toIndentedString(referenceCount)).append("\n");
        sb.append("    adminState: ").append(toIndentedString(adminState)).append("\n");
        sb.append("    actionState: ").append(toIndentedString(actionState)).append("\n");
        sb.append("    template: ").append(toIndentedString(template)).append("\n");
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
