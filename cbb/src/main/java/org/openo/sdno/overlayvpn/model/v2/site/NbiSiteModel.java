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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.UuidModel;
import org.openo.sdno.overlayvpn.model.v2.cpe.NbiNeMo;
import org.openo.sdno.overlayvpn.model.v2.internetgateway.NbiInternetGatewayModel;
import org.openo.sdno.overlayvpn.model.v2.routeentry.NbiRouteEntryModel;
import org.openo.sdno.overlayvpn.model.v2.subnet.NbiSubnetModel;
import org.openo.sdno.overlayvpn.model.v2.vlan.NbiVlanModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NbiSiteModel", propOrder = {"name", "tenantId", "description", "location", "actionState",
                "deployCloudCpeByDeafult", "popId", "localCpeType", "siteDescriptor", "reliability", "isEncrypt",
                "vpnUpstreamBandwidth", "vpnDownstreamBandwidth", "totalDownstreamBandwidth", "totalUpstreamBandwidth",
                "createtime", "updatetime", "localCpes", "cloudCpes", "subnets", "vlans", "routes", "internetGateway"})

public class NbiSiteModel extends UuidModel {

    @XmlElement(name = "name")
    @ApiModelProperty(example = "null", required = true, value = "the name of the model")
    private String name = null;

    @XmlElement(name = "tenantId")
    @ApiModelProperty(example = "null", required = true, value = "tenant ID")
    private String tenantId = null;

    @XmlElement(name = "description")
    @ApiModelProperty(example = "null", value = "description info")
    private String description = null;

    @XmlElement(name = "location")
    @ApiModelProperty(example = "null", value = "location")
    private String location = null;

    @XmlElement(name = "actionState")
    @ApiModelProperty(example = "null", value = "operation status(scope:none,normal,creating,deleting,updating,create_exception,update_exception,delete_exception,deploying,deploy_exception,undeploying,undeploy_exception,checking,check_exception)")
    private String actionState = null;

    @XmlElement(name = "deployCloudCpeByDeafult")
    @ApiModelProperty(example = "null", value = "default=true")
    private String deployCloudCpeByDeafult = null;

    @XmlElement(name = "popId")
    @ApiModelProperty(example = "null", value = "pop id")
    private String popId = null;

    @XmlElement(name = "localCpeType")
    @ApiModelProperty(example = "null", value = "local cpe type")
    private String localCpeType = null;

    @XmlElement(name = "siteDescriptor")
    @ApiModelProperty(example = "null", required = true, value = "site Descriptor")
    private String siteDescriptor = null;

    @XmlElement(name = "reliability")
    @ApiModelProperty(example = "null", required = true, value = "(scope:singleFixedNetwork,dualFixedNetwork,fixedAndWirelessNetwork,EthernetNetwork,VDSLNetwork,GSHDSLNetwork,EthernetAndLTENetwork,EthernetAndEthernetNetwork,EthernetAndVDSLNetwork,VDSLAndLTENetwork)")
    private String reliability = null;

    @XmlElement(name = "isEncrypt")
    @ApiModelProperty(example = "null", value = "is encrypt")
    private String isEncrypt = null;

    @XmlElement(name = "vpnUpstreamBandwidth")
    @ApiModelProperty(example = "null", value = "vpn Upstream Bandwidth default=-2L")
    private Long vpnUpstreamBandwidth = null;

    @XmlElement(name = "vpnDownstreamBandwidth")
    @ApiModelProperty(example = "null", value = "vpn Downstream Bandwidth default=-2L")
    private Long vpnDownstreamBandwidth = null;

    @XmlElement(name = "totalDownstreamBandwidth")
    @ApiModelProperty(example = "null", value = "total Downstream Bandwidth")
    private String totalDownstreamBandwidth = null;

    @XmlElement(name = "totalUpstreamBandwidth")
    @ApiModelProperty(example = "null", value = "total Upstream Bandwidth")
    private String totalUpstreamBandwidth = null;

    @XmlElement(name = "createtime")
    @ApiModelProperty(example = "null", value = "create time")
    private Long createtime = null;

    @XmlElement(name = "updatetime")
    @ApiModelProperty(example = "null", value = "update time")
    private Long updatetime = null;

    @XmlElement(name = "localCpes")
    @ApiModelProperty(example = "null", value = "")
    private List<NbiNeMo> localCpes = new ArrayList<NbiNeMo>();

    @XmlElement(name = "cloudCpes")
    @ApiModelProperty(example = "null", value = "")
    private List<NbiNeMo> cloudCpes = new ArrayList<NbiNeMo>();

    @XmlElement(name = "subnets")
    @ApiModelProperty(example = "null", value = "")
    private List<NbiSubnetModel> subnets = new ArrayList<NbiSubnetModel>();

    @XmlElement(name = "vlans")
    @ApiModelProperty(example = "null", value = "")
    private List<NbiVlanModel> vlans = new ArrayList<NbiVlanModel>();

    @XmlElement(name = "routes")
    @ApiModelProperty(example = "null", value = "")
    private List<NbiRouteEntryModel> routes = new ArrayList<NbiRouteEntryModel>();

    @XmlElement(name = "internetGateway")
    @ApiModelProperty(example = "null", value = "")
    private List<NbiInternetGatewayModel> internetGateway = new ArrayList<NbiInternetGatewayModel>();

    /**
     * the name of the model
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
     * tenant ID
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
     * description info
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
     * operation
     * status(scope:none,normal,creating,deleting,updating,create_exception,update_exception,delete_exception,deploying,deploy_exception,undeploying,undeploy_exception,checking,check_exception)
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
     * default=true
     * 
     * @return deployCloudCpeByDeafult
     **/
    public String getDeployCloudCpeByDeafult() {
        return deployCloudCpeByDeafult;
    }

    public void setDeployCloudCpeByDeafult(String deployCloudCpeByDeafult) {
        this.deployCloudCpeByDeafult = deployCloudCpeByDeafult;
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
     * local cpe type
     * 
     * @return localCpeType
     **/
    public String getLocalCpeType() {
        return localCpeType;
    }

    public void setLocalCpeType(String localCpeType) {
        this.localCpeType = localCpeType;
    }

    /**
     * site Descriptor
     * 
     * @return siteDescriptor
     **/
    public String getSiteDescriptor() {
        return siteDescriptor;
    }

    public void setSiteDescriptor(String siteDescriptor) {
        this.siteDescriptor = siteDescriptor;
    }

    /**
     * (scope:singleFixedNetwork,dualFixedNetwork,fixedAndWirelessNetwork,EthernetNetwork,VDSLNetwork,GSHDSLNetwork,EthernetAndLTENetwork,EthernetAndEthernetNetwork,EthernetAndVDSLNetwork,VDSLAndLTENetwork)
     * 
     * @return reliability
     **/
    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    /**
     * is encrypt
     * 
     * @return isEncrypt
     **/
    public String getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(String isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    /**
     * vpn Upstream Bandwidth default=-2L
     * 
     * @return vpnUpstreamBandwidth
     **/
    public Long getVpnUpstreamBandwidth() {
        return vpnUpstreamBandwidth;
    }

    public void setVpnUpstreamBandwidth(Long vpnUpstreamBandwidth) {
        this.vpnUpstreamBandwidth = vpnUpstreamBandwidth;
    }

    /**
     * vpn Downstream Bandwidth default=-2L
     * 
     * @return vpnDownstreamBandwidth
     **/
    public Long getVpnDownstreamBandwidth() {
        return vpnDownstreamBandwidth;
    }

    public void setVpnDownstreamBandwidth(Long vpnDownstreamBandwidth) {
        this.vpnDownstreamBandwidth = vpnDownstreamBandwidth;
    }

    /**
     * total Downstream Bandwidth
     * 
     * @return totalDownstreamBandwidth
     **/
    public String getTotalDownstreamBandwidth() {
        return totalDownstreamBandwidth;
    }

    public void setTotalDownstreamBandwidth(String totalDownstreamBandwidth) {
        this.totalDownstreamBandwidth = totalDownstreamBandwidth;
    }

    /**
     * total Upstream Bandwidth
     * 
     * @return totalUpstreamBandwidth
     **/
    public String getTotalUpstreamBandwidth() {
        return totalUpstreamBandwidth;
    }

    public void setTotalUpstreamBandwidth(String totalUpstreamBandwidth) {
        this.totalUpstreamBandwidth = totalUpstreamBandwidth;
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
     * Get localCpes
     * 
     * @return localCpes
     **/
    public List<NbiNeMo> getLocalCpes() {
        return localCpes;
    }

    public void setLocalCpes(List<NbiNeMo> localCpes) {
        this.localCpes = localCpes;
    }

    /**
     * Get cloudCpes
     * 
     * @return cloudCpes
     **/
    public List<NbiNeMo> getCloudCpes() {
        return cloudCpes;
    }

    public void setCloudCpes(List<NbiNeMo> cloudCpes) {
        this.cloudCpes = cloudCpes;
    }

    /**
     * Get subnets
     * 
     * @return subnets
     **/
    public List<NbiSubnetModel> getSubnets() {
        return subnets;
    }

    public void setSubnets(List<NbiSubnetModel> subnets) {
        this.subnets = subnets;
    }

    /**
     * Get vlans
     * 
     * @return vlans
     **/
    public List<NbiVlanModel> getVlans() {
        return vlans;
    }

    public void setVlans(List<NbiVlanModel> vlans) {
        this.vlans = vlans;
    }

    /**
     * Get routes
     * 
     * @return routes
     **/
    public List<NbiRouteEntryModel> getRoutes() {
        return routes;
    }

    public void setRoutes(List<NbiRouteEntryModel> routes) {
        this.routes = routes;
    }

    /**
     * Get internetGateway
     * 
     * @return internetGateway
     **/
    public List<NbiInternetGatewayModel> getInternetGateway() {
        return internetGateway;
    }

    public void setInternetGateway(List<NbiInternetGatewayModel> internetGateway) {
        this.internetGateway = internetGateway;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiSiteModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    location: ").append(toIndentedString(location)).append("\n");
        sb.append("    actionState: ").append(toIndentedString(actionState)).append("\n");
        sb.append("    deployCloudCpeByDeafult: ").append(toIndentedString(deployCloudCpeByDeafult)).append("\n");
        sb.append("    popId: ").append(toIndentedString(popId)).append("\n");
        sb.append("    localCpeType: ").append(toIndentedString(localCpeType)).append("\n");
        sb.append("    siteDescriptor: ").append(toIndentedString(siteDescriptor)).append("\n");
        sb.append("    reliability: ").append(toIndentedString(reliability)).append("\n");
        sb.append("    isEncrypt: ").append(toIndentedString(isEncrypt)).append("\n");
        sb.append("    vpnUpstreamBandwidth: ").append(toIndentedString(vpnUpstreamBandwidth)).append("\n");
        sb.append("    vpnDownstreamBandwidth: ").append(toIndentedString(vpnDownstreamBandwidth)).append("\n");
        sb.append("    totalDownstreamBandwidth: ").append(toIndentedString(totalDownstreamBandwidth)).append("\n");
        sb.append("    totalUpstreamBandwidth: ").append(toIndentedString(totalUpstreamBandwidth)).append("\n");
        sb.append("    createtime: ").append(toIndentedString(createtime)).append("\n");
        sb.append("    updatetime: ").append(toIndentedString(updatetime)).append("\n");
        sb.append("    localCpes: ").append(toIndentedString(localCpes)).append("\n");
        sb.append("    cloudCpes: ").append(toIndentedString(cloudCpes)).append("\n");
        sb.append("    subnets: ").append(toIndentedString(subnets)).append("\n");
        sb.append("    vlans: ").append(toIndentedString(vlans)).append("\n");
        sb.append("    routes: ").append(toIndentedString(routes)).append("\n");
        sb.append("    internetGateway: ").append(toIndentedString(internetGateway)).append("\n");
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
