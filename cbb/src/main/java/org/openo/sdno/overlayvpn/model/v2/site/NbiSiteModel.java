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

package org.openo.sdno.overlayvpn.model.v2.site;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOConvertField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.v2.cpe.NbiCloudCpeModel;
import org.openo.sdno.overlayvpn.model.v2.cpe.NbiLocalCpeModel;
import org.openo.sdno.overlayvpn.model.v2.internetgateway.NbiInternetGatewayModel;
import org.openo.sdno.overlayvpn.model.v2.routeentry.NbiRouteEntryModel;
import org.openo.sdno.overlayvpn.model.v2.subnet.NbiSubnetModel;
import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;
import org.openo.sdno.overlayvpn.model.v2.vlan.NbiVlanModel;
import org.openo.sdno.overlayvpn.verify.annotation.ALong;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Model class of Local Site.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-6
 */
@MOResType(infoModelName = "localsite_sitemodel")
public class NbiSiteModel extends UuidModel {

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
    @AString(scope = "None,Normal,Creating,Deleting,Updating,Create_Exception,Delete_Exception,Update_Exception")
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
    @AString(require = false, min = 0, max = 128)
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

    @ALong
    private Long vpnUpstreamBandwidth = -1L;

    @ALong
    private Long vpnDownstreamBandwidth = -1L;

    @AUuid
    private String qosPolicyId;

    @AString
    private String totalUpstreamBandwidth;

    @AString
    private String totalDownstreamBandwidth;

    @AString
    private String upstreamQosProfile;

    @AString
    private String downstreamQosProfile;

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
    @JsonIgnore
    private List<NbiLocalCpeModel> localCpeModels;

    @NONInvField
    @JsonIgnore
    private List<NbiCloudCpeModel> cloudCpeModels;

    @NONInvField
    private List<NbiSubnetModel> subnets;

    @NONInvField
    private List<NbiVlanModel> vlans;

    @NONInvField
    private List<NbiRouteEntryModel> routes;

    @NONInvField
    private NbiInternetGatewayModel internetGateway;

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

    public Long getVpnUpstreamBandwidth() {
        return vpnUpstreamBandwidth;
    }

    public void setVpnUpstreamBandwidth(Long vpnUpstreamBandwidth) {
        this.vpnUpstreamBandwidth = vpnUpstreamBandwidth;
    }

    public Long getVpnDownstreamBandwidth() {
        return vpnDownstreamBandwidth;
    }

    public void setVpnDownstreamBandwidth(Long vpnDownstreamBandwidth) {
        this.vpnDownstreamBandwidth = vpnDownstreamBandwidth;
    }

    public String getQosPolicyId() {
        return qosPolicyId;
    }

    public void setQosPolicyId(String qosPolicyId) {
        this.qosPolicyId = qosPolicyId;
    }

    public String getTotalUpstreamBandwidth() {
        return totalUpstreamBandwidth;
    }

    public void setTotalUpstreamBandwidth(String totalUpstreamBandwidth) {
        this.totalUpstreamBandwidth = totalUpstreamBandwidth;
    }

    public String getTotalDownstreamBandwidth() {
        return totalDownstreamBandwidth;
    }

    public void setTotalDownstreamBandwidth(String totalDownstreamBandwidth) {
        this.totalDownstreamBandwidth = totalDownstreamBandwidth;
    }

    public String getUpstreamQosProfile() {
        return upstreamQosProfile;
    }

    public void setUpstreamQosProfile(String upstreamQosProfile) {
        this.upstreamQosProfile = upstreamQosProfile;
    }

    public String getDownstreamQosProfile() {
        return downstreamQosProfile;
    }

    public void setDownstreamQosProfile(String downstreamQosProfile) {
        this.downstreamQosProfile = downstreamQosProfile;
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

    public List<NbiSubnetModel> getSubnets() {
        return subnets;
    }

    public void setSubnets(List<NbiSubnetModel> subnets) {
        this.subnets = subnets;
    }

    public List<NbiVlanModel> getVlans() {
        return vlans;
    }

    public void setVlans(List<NbiVlanModel> vlans) {
        this.vlans = vlans;
    }

    public List<NbiRouteEntryModel> getRoutes() {
        return routes;
    }

    public void setRoutes(List<NbiRouteEntryModel> routes) {
        this.routes = routes;
    }

    public NbiInternetGatewayModel getInternetGateway() {
        return internetGateway;
    }

    public void setInternetGateway(NbiInternetGatewayModel internetGateway) {
        this.internetGateway = internetGateway;
    }

    public List<NbiLocalCpeModel> getLocalCpeModels() {
        return localCpeModels;
    }

    public void setLocalCpeModels(List<NbiLocalCpeModel> localCpeModels) {
        this.localCpeModels = localCpeModels;
    }

    public List<NbiCloudCpeModel> getCloudCpeModels() {
        return cloudCpeModels;
    }

    public void setCloudCpeModels(List<NbiCloudCpeModel> cloudCpeModels) {
        this.cloudCpeModels = cloudCpeModels;
    }

    public void initBasicInfo(String name, String tenantId, String location, String description) {
        this.name = name;
        this.tenantId = tenantId;
        this.location = location;
        this.description = description;
    }

}
