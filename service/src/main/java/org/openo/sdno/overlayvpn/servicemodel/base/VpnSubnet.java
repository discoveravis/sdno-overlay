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

import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;

/**
 * The vpn subnet model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 10, 2017
 */
public class VpnSubnet extends ServiceModel {

    private String cidrBlock;

    private Integer cidrBlockSize;

    private String siteId;

    private String gatewayIp;

    private String enableDhcp;

    private String vlanId;

    private String vni;

    private VpnVlan vlan;

    private List<String> ports;

    private List<String> portNames;

    private String gatewayInterface;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public VpnSubnet() {
        super();
    }

    public String getCidrBlock() {
        return cidrBlock;
    }

    public void setCidrBlock(String cidrBlock) {
        this.cidrBlock = cidrBlock;
    }

    public Integer getCidrBlockSize() {
        return cidrBlockSize;
    }

    public void setCidrBlockSize(Integer cidrBlockSize) {
        this.cidrBlockSize = cidrBlockSize;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public String getEnableDhcp() {
        return enableDhcp;
    }

    public void setEnableDhcp(String enableDhcp) {
        this.enableDhcp = enableDhcp;
    }

    public String getVlanId() {
        return vlanId;
    }

    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    public String getVni() {
        return vni;
    }

    public void setVni(String vni) {
        this.vni = vni;
    }

    public VpnVlan getVlan() {
        return vlan;
    }

    public void setVlan(VpnVlan vlan) {
        this.vlan = vlan;
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

    public String getGatewayInterface() {
        return gatewayInterface;
    }

    public void setGatewayInterface(String gatewayInterface) {
        this.gatewayInterface = gatewayInterface;
    }

}
