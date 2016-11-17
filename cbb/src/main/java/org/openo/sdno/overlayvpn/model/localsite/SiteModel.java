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

package org.openo.sdno.overlayvpn.model.localsite;

import java.util.List;

import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOConvertField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Model class of Local Site.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-11-17
 */
@MOResType(infoModelName = "localsite_sitemodel")
public class SiteModel extends AbstUuidModel {

    /**
     * Tenant Uuid
     */
    @AString(require = true, min = 0, max = 36)
    private String tenantId;

    /**
     * Site Name
     */
    @AString(require = true, min = 0, max = 128)
    private String name;

    /**
     * Site Description
     */
    @AString(require = false, min = 0, max = 255)
    private String description;

    /**
     * Site Location
     */
    @AString(require = false, min = 0, max = 128)
    private String location;

    /**
     * Action State
     */
    @AString(scope = "None,Normal,Creating,Deleting,Updating,Create_Excepion,Delete_Exception,Update_Exception")
    private String actionState = ActionStatus.NORMAL.getName();

    /**
     * Whether start vCPE when site creation or not
     */
    @MOConvertField
    private boolean deployCloudCpeByDefault = true;

    /**
     * Pop Uuid
     */
    @AUuid(require = false)
    private String popId;

    /**
     * Local CPE type
     */
    @AString(require = false, min = 0, max = 255)
    private String localCpeType;

    /**
     * Name of Template
     */
    @AString(require = true, min = 0, max = 255)
    private String siteDescriptor;

    /**
     * Protection mode
     */
    @AString(require = true)
    private String reliability;

    /**
     * Whether Encrypt in IpSec connection
     */
    @AString(require = false, scope = "true,false")
    private String isEncrypt;

    /**
     * Create time
     */
    private Long createtime;

    /**
     * Update time
     */
    private Long updatetime;

    @NONInvField
    private List<NetworkElementMO> localCpes;

    @NONInvField
    private List<NetworkElementMO> cloudCpes;

    @NONInvField
    private List<SubnetModel> subnets;

    @NONInvField
    private List<VlanModel> vlans;

    @NONInvField
    private List<RouteEntryModel> routes;

    @NONInvField
    private InternetGatewayModel internetGateway;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActionState() {
        return actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }

    public boolean isDeployCloudCpeByDefault() {
        return deployCloudCpeByDefault;
    }

    public void setDeployCloudCpeByDefault(boolean deployCloudCpeByDefault) {
        this.deployCloudCpeByDefault = deployCloudCpeByDefault;
    }

    public String getPopId() {
        return popId;
    }

    public void setPopId(String popId) {
        this.popId = popId;
    }

    public String getLocalCpeType() {
        return localCpeType;
    }

    public void setLocalCpeType(String localCpeType) {
        this.localCpeType = localCpeType;
    }

    public String getSiteDescriptor() {
        return siteDescriptor;
    }

    public void setSiteDescriptor(String siteDescriptor) {
        this.siteDescriptor = siteDescriptor;
    }

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    public String getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(String isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    public List<NetworkElementMO> getLocalCpes() {
        return localCpes;
    }

    public void setLocalCpes(List<NetworkElementMO> localCpes) {
        this.localCpes = localCpes;
    }

    public List<NetworkElementMO> getCloudCpes() {
        return cloudCpes;
    }

    public void setCloudCpes(List<NetworkElementMO> cloudCpes) {
        this.cloudCpes = cloudCpes;
    }

    public List<SubnetModel> getSubnets() {
        return subnets;
    }

    public void setSubnets(List<SubnetModel> subnets) {
        this.subnets = subnets;
    }

    public List<VlanModel> getVlans() {
        return vlans;
    }

    public void setVlans(List<VlanModel> vlans) {
        this.vlans = vlans;
    }

    public List<RouteEntryModel> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteEntryModel> routes) {
        this.routes = routes;
    }

    public InternetGatewayModel getInternetGateway() {
        return internetGateway;
    }

    public void setInternetGateway(InternetGatewayModel internetGateway) {
        this.internetGateway = internetGateway;
    }

}
