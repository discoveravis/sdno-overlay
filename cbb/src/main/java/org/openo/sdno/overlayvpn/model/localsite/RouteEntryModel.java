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

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.BaseServiceModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AIp;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Model class of Site RouteEntry.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-11-17
 */
@MOResType(infoModelName = "localsite_routeentrymodel")
public class RouteEntryModel extends BaseServiceModel {

    /**
     * Site Uuid
     */
    @AUuid(require = true)
    private String siteId;

    /**
     * Subnet Uuid
     */
    @AUuid
    private String subnetId;

    /**
     * Subnet cidr
     */
    private String cidr;

    /**
     * Vpn Gateway Id
     */
    @AUuid
    private String vpnGatewayId;

    /**
     * Internet Gateway Id
     */
    @AUuid
    private String internetGatewayId;

    /**
     * Next hop
     */
    @AIp
    private String nextHop;

    /**
     * Route precedence
     */
    @AInt(require = false, min = 1, max = 254)
    private int precedence;

    /**
     * Route type
     */
    @AString(require = false, scope = "static-routing")
    private String routeType = "static-routing";

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public String getVpnGatewayId() {
        return vpnGatewayId;
    }

    public void setVpnGatewayId(String vpnGatewayId) {
        this.vpnGatewayId = vpnGatewayId;
    }

    public String getInternetGatewayId() {
        return internetGatewayId;
    }

    public void setInternetGatewayId(String internetGatewayId) {
        this.internetGatewayId = internetGatewayId;
    }

    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }

    public int getPrecedence() {
        return precedence;
    }

    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

}
