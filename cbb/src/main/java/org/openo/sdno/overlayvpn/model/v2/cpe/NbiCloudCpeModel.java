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

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.NvString;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Model class of CloudCpe.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-4
 */
@MOResType(infoModelName = "localsite_cloudcpe")
public class NbiCloudCpeModel extends UuidModel {

    /**
     * Pop Uuid
     */
    @AUuid
    private String popId;

    /**
     * Tenant Uuid
     */
    @AUuid(require = true)
    private String tenantId;

    /**
     * Site Uuid
     */
    @AUuid(require = true)
    private String siteId;

    /**
     * vCPE Uuid
     */
    @AUuid
    private String vCpeId;

    /**
     * Name of CloudCpe
     */
    @AString
    private String name;

    /**
     * Esn of CloudCpe
     */
    @AString
    private String esn;

    /**
     * Manage Ip Address of CloudCpe
     */
    @AString
    private String mgrIp;

    /**
     * Ip Mask of Manage Ip Address
     */
    @AString
    private String mgrMask;

    /**
     * Manage Gateway Ip Address of CloudCpe
     */
    @AString
    private String mgrGateWayIp;

    /**
     * Controller Uuid
     */
    @AString
    private String controllerId;

    /**
     * Controller Ip Address
     */
    @AString
    @NONInvField
    private String controllerIp;

    /**
     * Controller Ip Address Mask
     */
    @AString
    @NONInvField
    private String controllerMask;

    /**
     * Description of CloudCpe
     */
    @AString
    @NONInvField
    private String description;

    /**
     * Vendor of CloudCpe
     */
    @AString
    @NONInvField
    private String vendor;

    /**
     * Type of CloudCpe
     */
    @AString
    @NONInvField
    private String type;

    /**
     * Version of Vnfd
     */
    @AString
    @NONInvField
    private String vnfdVersion;

    /**
     * Callback Url
     */
    @AString
    @NONInvField
    private String callbackUrl;

    /**
     * Vnfd Uuid
     */
    @AString
    private String vnfdId;

    /**
     * Vnf Id
     */
    @AString
    private String vnfId;

    /**
     * Vnfm Id
     */
    @AString
    private String vnfmId;

    /**
     * Dc Location
     */
    @AString
    private String dcLocation;

    /**
     * Range Id
     */
    @AString
    private String rangeId;

    /**
     * Reference Count
     */
    @AString
    private int referenceCount;

    /**
     * Administrative state
     */
    @AString(require = false, scope = "Active,Stopped,Building")
    private String adminState;

    /**
     * Action state
     */
    @AString(scope = "Normal,Creating,Deleting,Updating,Create_Exception,Delete_Exception,Update_Exception")
    private String actionState = ActionStatus.NORMAL.getName();

    /**
     * Template of CloudCpe
     */
    private String template;

    /**
     * Additional Information
     */
    @JsonIgnore
    @NONInvField
    private List<NvString> additionalInfo;

    public String getPopId() {
        return popId;
    }

    public void setPopId(String popId) {
        this.popId = popId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getvCpeId() {
        return vCpeId;
    }

    public void setvCpeId(String vCpeId) {
        this.vCpeId = vCpeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEsn() {
        return esn;
    }

    public void setEsn(String esn) {
        this.esn = esn;
    }

    public String getMgrIp() {
        return mgrIp;
    }

    public void setMgrIp(String mgrIp) {
        this.mgrIp = mgrIp;
    }

    public String getMgrMask() {
        return mgrMask;
    }

    public void setMgrMask(String mgrMask) {
        this.mgrMask = mgrMask;
    }

    public String getMgrGatewayIp() {
        return mgrGateWayIp;
    }

    public void setMgrGatewayIp(String mgrGatewayIp) {
        this.mgrGateWayIp = mgrGatewayIp;
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getControllerIp() {
        return controllerIp;
    }

    public void setControllerIp(String controllerIp) {
        this.controllerIp = controllerIp;
    }

    public String getControllerMask() {
        return controllerMask;
    }

    public void setControllerMask(String controllerMask) {
        this.controllerMask = controllerMask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVnfdVersion() {
        return vnfdVersion;
    }

    public void setVnfdVersion(String vnfdVersion) {
        this.vnfdVersion = vnfdVersion;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getVnfdId() {
        return vnfdId;
    }

    public void setVnfdId(String vnfdId) {
        this.vnfdId = vnfdId;
    }

    public String getVnfId() {
        return vnfId;
    }

    public void setVnfId(String vnfId) {
        this.vnfId = vnfId;
    }

    public String getVnfmId() {
        return vnfmId;
    }

    public void setVnfmId(String vnfmId) {
        this.vnfmId = vnfmId;
    }

    public String getDcLocation() {
        return dcLocation;
    }

    public void setDcLocation(String dcLocation) {
        this.dcLocation = dcLocation;
    }

    public String getRangeId() {
        return rangeId;
    }

    public void setRangeId(String rangeId) {
        this.rangeId = rangeId;
    }

    public int getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }

    public String getAdminState() {
        return adminState;
    }

    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    public String getActionState() {
        return actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<NvString> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(List<NvString> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

}
