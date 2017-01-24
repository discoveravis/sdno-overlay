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

package org.openo.sdno.overlayvpn.model.v2.subnet;

import java.util.List;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOConvertField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseServiceModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AIp;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Model class of Site Subnet.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-4
 */
@MOResType(infoModelName = "localsite_subnetmodel")
public class NbiSubnetModel extends BaseServiceModel {

    /**
     * Site Uuid
     */
    @AUuid
    private String siteId;

    /**
     * Subnet Ip Segment
     */
    private String cidrBlock;

    /**
     * Size of Cidr Block
     */
    @AInt(min = 8, max = 32)
    private Integer cidrBlockSize;

    /**
     * Gateway Ip
     */
    @AIp
    private String gatewayIp;

    /**
     * Vlan Id
     */
    private String vlanId;

    /**
     * Vni Id
     */
    @AInt(require = false)
    private String vni;

    /**
     * List of port name
     */
    @MOConvertField
    private List<String> portNames;

    /**
     * List of port Uuid
     */
    @MOConvertField
    private List<String> ports;

    /**
     * Ipv6 Address
     */
    @AString(require = false)
    @NONInvField
    private String ipv6Address;

    /**
     * Prefix Length
     */
    @AString(require = false)
    @NONInvField
    private String prefixLength;

    /**
     * Enable Ipv6 dhcp
     */
    @AString(require = false, scope = "true,false")
    @NONInvField
    private String dhcp6Enable = "false";

    /**
     * Ipv6 dhcp mode
     */
    @AString(require = false, scope = "server,relay")
    @NONInvField
    private String dhcp6Mode = "server";

    /**
     * Enable Ipv4 dhcp
     */
    @AString(require = false, scope = "true,false")
    private String enableDhcp = "false";

    /**
     * Name of BdIf Interface
     */
    @AString
    private String gatewayInterface;

    /**
     * Create time
     */
    @NONInvField
    @AString
    private long createtime;

    /**
     * Update time
     */
    @NONInvField
    @AString
    private long updatetime;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
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

    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
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

    public List<String> getPortNames() {
        return portNames;
    }

    public void setPortNames(List<String> portNames) {
        this.portNames = portNames;
    }

    public List<String> getPorts() {
        return ports;
    }

    public void setPorts(List<String> ports) {
        this.ports = ports;
    }

    public String getIpv6Address() {
        return ipv6Address;
    }

    public void setIpv6Address(String ipv6Address) {
        this.ipv6Address = ipv6Address;
    }

    public String getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(String prefixLength) {
        this.prefixLength = prefixLength;
    }

    public String getDhcp6Enable() {
        return dhcp6Enable;
    }

    public void setDhcp6Enable(String dhcp6Enable) {
        this.dhcp6Enable = dhcp6Enable;
    }

    public String getDhcp6Mode() {
        return dhcp6Mode;
    }

    public void setDhcp6Mode(String dhcp6Mode) {
        this.dhcp6Mode = dhcp6Mode;
    }

    public String getEnableDhcp() {
        return enableDhcp;
    }

    public void setEnableDhcp(String enableDhcp) {
        this.enableDhcp = enableDhcp;
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

    public String getGatewayInterface() {
        return gatewayInterface;
    }

    public void setGatewayInterface(String gatewayInterface) {
        this.gatewayInterface = gatewayInterface;
    }
}
