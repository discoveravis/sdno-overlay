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

package org.openo.sdno.overlayvpn.model.v2.overlay;

import java.util.ArrayList;
import java.util.List;

import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;

/**
 * vpn gateway model
 */
/**
 * The vpn gateway model.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 3, 2017
 */
public class NbiVpnGateway extends UuidModel {

    private String name = null;

    private String tenantId = null;

    private String description = null;

    private String vpnId = null;

    private String siteId = null;

    private String vpcId = null;

    private String deployPosition = null;

    private String upstreamBandwidth = null;

    private String downstreamBandwidth = null;

    private String vlans = null;

    private List<String> ports = new ArrayList<String>();

    private List<String> portNames = new ArrayList<String>();

    private String regionId = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(String vpnId) {
        this.vpnId = vpnId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public String getDeployPosition() {
        return deployPosition;
    }

    public void setDeployPosition(String deployPosition) {
        this.deployPosition = deployPosition;
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

    public String getVlans() {
        return vlans;
    }

    public void setVlans(String vlans) {
        this.vlans = vlans;
    }

    public List<String> getPorts() {
        return ports;
    }

    public void setPorts(List<String> ports) {
        this.ports = ports;
    }

    public List<String> getPortNames() {
        return portNames;
    }

    public void setPortNames(List<String> portNames) {
        this.portNames = portNames;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

}
