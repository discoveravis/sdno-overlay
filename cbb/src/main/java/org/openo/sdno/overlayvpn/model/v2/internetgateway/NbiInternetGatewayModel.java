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

package org.openo.sdno.overlayvpn.model.v2.internetgateway;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOConvertField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseServiceModel;
import org.openo.sdno.overlayvpn.model.v2.site.NbiSiteModel;
import org.openo.sdno.overlayvpn.verify.annotation.AIp;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Model class of Site Internet Gateway.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-12
 */
@MOResType(infoModelName = "localsite_internetgatewaymodel")
public class NbiInternetGatewayModel extends BaseServiceModel {

    /**
     * Site Uuid
     */
    @AUuid(require = true)
    private String siteId;

    /**
     * Protection mode, just support non-protection mode
     */
    @AString(require = false, scope = "non-protection")
    private String protectionType = "non-protection";

    /**
     * Enable Snat or not
     */
    @AString(require = false, scope = "true,false")
    private String enableSnat = "true";

    /**
     * Bandwidth of UpStream
     */
    private Long upstreamBandwidth;

    /**
     * Bandwidth of DownStream
     */
    private Long downstreamBandwidth;

    /**
     * List of Subnet Uuid
     */
    @MOConvertField
    private List<String> sourceSubnets;

    /**
     * Vpn id inside Site
     */
    @AUuid(require = false)
    private String vpnId;

    /**
     * Public Ip Address
     */
    @AIp
    private String publicIP;

    /**
     * Gateway Ne Id
     */
    @AUuid
    private String gwNeId;

    /**
     * Name of wan interface used for internet
     */
    private String wanNames;

    /**
     * Deploy position
     */
    @AString(scope = "localFirst,cloudFirst")
    private String deployPosition;

    @JsonIgnore
    @NONInvField
    private List<NetworkElementMO> nes;

    @JsonIgnore
    @NONInvField
    private NbiSiteModel site;

    @NONInvField
    @AString
    private long createtime;

    @NONInvField
    @AString
    private long updatetime;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getProtectionType() {
        return protectionType;
    }

    public void setProtectionType(String protectionType) {
        this.protectionType = protectionType;
    }

    public String getEnableSnat() {
        return enableSnat;
    }

    public void setEnableSnat(String enableSnat) {
        this.enableSnat = enableSnat;
    }

    public Long getUpstreamBandwidth() {
        return upstreamBandwidth;
    }

    public void setUpstreamBandwidth(Long upstreamBandwidth) {
        this.upstreamBandwidth = upstreamBandwidth;
    }

    public Long getDownstreamBandwidth() {
        return downstreamBandwidth;
    }

    public void setDownstreamBandwidth(Long downstreamBandwidth) {
        this.downstreamBandwidth = downstreamBandwidth;
    }

    public List<String> getSourceSubnets() {
        return sourceSubnets;
    }

    public void setSourceSubnets(List<String> sourceSubnets) {
        this.sourceSubnets = sourceSubnets;
    }

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(String vpnId) {
        this.vpnId = vpnId;
    }

    public String getPublicIP() {
        return publicIP;
    }

    public void setPublicIP(String publicIP) {
        this.publicIP = publicIP;
    }

    public String getDeployPosition() {
        return deployPosition;
    }

    public void setDeployPosition(String deployPosition) {
        this.deployPosition = deployPosition;
    }

    public String getGwNeId() {
        return gwNeId;
    }

    public void setGwNeId(String gwNeId) {
        this.gwNeId = gwNeId;
    }

    public String getWanNames() {
        return wanNames;
    }

    public void setWanNames(String wanNames) {
        this.wanNames = wanNames;
    }

    public List<NetworkElementMO> getNes() {
        return nes;
    }

    public void setNes(List<NetworkElementMO> nes) {
        this.nes = nes;
    }

    public NbiSiteModel getSite() {
        return site;
    }

    public void setSite(NbiSiteModel site) {
        this.site = site;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

}
