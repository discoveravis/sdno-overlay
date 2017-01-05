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

package org.openo.sdno.overlayvpn.model.v2.routeentry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseServiceModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NbiRouteEntryModel", propOrder = {"siteId", "subnetId", "cidr", "vpnGatewayId", "internetGatewayId",
                "nextHop", "precedence", "routeType"})

public class NbiRouteEntryModel extends BaseServiceModel {

    @XmlElement(name = "siteId")
    @ApiModelProperty(example = "null", required = true, value = "site id")
    private String siteId = null;

    @XmlElement(name = "subnetId")
    @ApiModelProperty(example = "null", value = "subnet id")
    private String subnetId = null;

    @XmlElement(name = "cidr")
    @ApiModelProperty(example = "null", value = "cidr")
    private String cidr = null;

    @XmlElement(name = "vpnGatewayId")
    @ApiModelProperty(example = "null", value = "vpn gateway id")
    private String vpnGatewayId = null;

    @XmlElement(name = "internetGatewayId")
    @ApiModelProperty(example = "null", value = "internet Gateway Id")
    private String internetGatewayId = null;

    @XmlElement(name = "nextHop")
    @ApiModelProperty(example = "null", value = "next hop")
    private String nextHop = null;

    @XmlElement(name = "precedence")
    @ApiModelProperty(example = "null", value = "precedence")
    private String precedence = null;

    @XmlElement(name = "routeType")
    @ApiModelProperty(example = "null", value = "only support static-routing for now")
    private String routeType = null;

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
     * subnet id
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
     * cidr
     * 
     * @return cidr
     **/
    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    /**
     * vpn gateway id
     * 
     * @return vpnGatewayId
     **/
    public String getVpnGatewayId() {
        return vpnGatewayId;
    }

    public void setVpnGatewayId(String vpnGatewayId) {
        this.vpnGatewayId = vpnGatewayId;
    }

    /**
     * internet Gateway Id
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
     * next hop
     * 
     * @return nextHop
     **/
    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }

    /**
     * precedence
     * 
     * @return precedence
     **/
    public String getPrecedence() {
        return precedence;
    }

    public void setPrecedence(String precedence) {
        this.precedence = precedence;
    }

    /**
     * only support static-routing for now
     * 
     * @return routeType
     **/
    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiRouteEntryModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    siteId: ").append(toIndentedString(siteId)).append("\n");
        sb.append("    subnetId: ").append(toIndentedString(subnetId)).append("\n");
        sb.append("    cidr: ").append(toIndentedString(cidr)).append("\n");
        sb.append("    vpnGatewayId: ").append(toIndentedString(vpnGatewayId)).append("\n");
        sb.append("    internetGatewayId: ").append(toIndentedString(internetGatewayId)).append("\n");
        sb.append("    nextHop: ").append(toIndentedString(nextHop)).append("\n");
        sb.append("    precedence: ").append(toIndentedString(precedence)).append("\n");
        sb.append("    routeType: ").append(toIndentedString(routeType)).append("\n");
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
