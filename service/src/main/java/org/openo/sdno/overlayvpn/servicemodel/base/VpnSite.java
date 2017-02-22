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

package org.openo.sdno.overlayvpn.servicemodel.base;

import java.util.List;

import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * The vpn site model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class VpnSite extends BaseSite {

    private String popId;

    private String localCpeType;

    private String isEncrypt;

    private String upstreamBandwidth;

    private String downstreamBandwidth;

    private List<NetworkElementMO> localCpes;

    private List<NetworkElementMO> cloudCpes;

    private List<VpnSubnet> subnets;

    private List<VpnVlan> vlans;

    @AString(require = true)
    private String siteDescriptor;

    @AString(scope = "singleFixedNetwork,dualFixedNetwork,fixedAndWirselessNetwork,EthernetNetwork")
    private String reliability;

    private InternetGateway internetGateway;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public VpnSite() {
        super();
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

    public String getIsEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(String isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public String getUpstreamBandwidth() {
        return upstreamBandwidth;
    }

    public void setUpstreamBandwidth(String upstreamBandwidth) {
        this.upstreamBandwidth = upstreamBandwidth;
    }

    public String getDownstreamBandwidth() {
        return downstreamBandwidth;
    }

    public void setDownstreamBandwidth(String downstreamBandwidth) {
        this.downstreamBandwidth = downstreamBandwidth;
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

    public List<VpnSubnet> getSubnets() {
        return subnets;
    }

    public void setSubnets(List<VpnSubnet> subnets) {
        this.subnets = subnets;
    }

    public List<VpnVlan> getVlans() {
        return vlans;
    }

    public void setVlans(List<VpnVlan> vlans) {
        this.vlans = vlans;
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

    public InternetGateway getInternetGateway() {
        return internetGateway;
    }

    public void setInternetGateway(InternetGateway internetGateway) {
        this.internetGateway = internetGateway;
    }

}
