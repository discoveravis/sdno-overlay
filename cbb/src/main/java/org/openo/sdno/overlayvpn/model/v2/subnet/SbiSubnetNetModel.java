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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseServiceModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiSubnetNetModel", propOrder = {"neId", "controllerId", "networkId", "serviceSubnetId", "vni",
                "vlanId", "cidrIpAddress", "cidrMask", "gatewayIp", "enableDhcp", "ipRangeStartIp", "ipRangeEndIp",
                "useMode", "changedMode", "dhcpMode", "dnsServerMode", "unlimit", "ipv6Address", "prefixLength",
                "dhcp6Enable", "dhcp6Mode", "priorDnsServer", "standbyDnsServer"})

public class SbiSubnetNetModel extends BaseServiceModel {

    @XmlElement(name = "neId")
    @ApiModelProperty(example = "null", required = true, value = "NE Id")
    private String neId = null;

    @XmlElement(name = "controllerId")
    @ApiModelProperty(example = "null", required = true, value = "controller Id")
    private String controllerId = null;

    @XmlElement(name = "networkId")
    @ApiModelProperty(example = "null", value = "network Id")
    private String networkId = null;

    @XmlElement(name = "serviceSubnetId")
    @ApiModelProperty(example = "null", required = true, value = "The UUID of subnet service")
    private String serviceSubnetId = null;

    @XmlElement(name = "vni")
    @ApiModelProperty(example = "null", value = "vni value")
    private String vni = null;

    @XmlElement(name = "vlanId")
    @ApiModelProperty(example = "null", value = "The UUID of VLAN service")
    private String vlanId = null;

    @XmlElement(name = "cidrIpAddress")
    @ApiModelProperty(example = "null", required = true, value = "The IP address of the subnet")
    private String cidrIpAddress = null;

    @XmlElement(name = "cidrMask")
    @ApiModelProperty(example = "null", required = true, value = "The mask of the subnet")
    private String cidrMask = null;

    @XmlElement(name = "gatewayIp")
    @ApiModelProperty(example = "null", value = "The gateway IP address")
    private String gatewayIp = null;

    @XmlElement(name = "enableDhcp")
    @ApiModelProperty(example = "null", value = "enable dhcp or not")
    private String enableDhcp = null;

    @XmlElement(name = "ipRangeStartIp")
    @ApiModelProperty(example = "null", value = "The start IP address of the subnet")
    private String ipRangeStartIp = null;

    @XmlElement(name = "ipRangeEndIp")
    @ApiModelProperty(example = "null", value = "The end IP address of the subnet")
    private String ipRangeEndIp = null;

    @XmlElement(name = "useMode")
    @ApiModelProperty(example = "null", required = true, value = "the scope is terminal,vm")
    private String useMode = null;

    @XmlElement(name = "changedMode")
    @ApiModelProperty(example = "null", value = "change mode")
    private String changedMode = null;

    @XmlElement(name = "dhcpMode")
    @ApiModelProperty(example = "null", required = true, value = "the scope is server,relay")
    private String dhcpMode = null;

    @XmlElement(name = "dnsServerMode")
    @ApiModelProperty(example = "null", required = true, value = "the scope is systemsetting,custom")
    private String dnsServerMode = null;

    @XmlElement(name = "unlimit")
    @ApiModelProperty(example = "null", value = "the scope is true,false")
    private String unlimit = null;

    @XmlElement(name = "ipv6Address")
    @ApiModelProperty(example = "null", value = "ipv6 address")
    private String ipv6Address = null;

    @XmlElement(name = "prefixLength")
    @ApiModelProperty(example = "null", value = "prefix length")
    private String prefixLength = null;

    @XmlElement(name = "dhcp6Enable")
    @ApiModelProperty(example = "null", value = "the scope is true,false")
    private String dhcp6Enable = null;

    @XmlElement(name = "dhcp6Mode")
    @ApiModelProperty(example = "null", value = "the scope is server,relay")
    private String dhcp6Mode = null;

    @XmlElement(name = "priorDnsServer")
    @ApiModelProperty(example = "null", value = "prior dns server")
    private String priorDnsServer = null;

    @XmlElement(name = "standbyDnsServer")
    @ApiModelProperty(example = "null", value = "standby dns server")
    private String standbyDnsServer = null;

    /**
     * NE Id
     * 
     * @return neId
     **/
    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    /**
     * controller Id
     * 
     * @return controllerId
     **/
    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    /**
     * network Id
     * 
     * @return networkId
     **/
    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    /**
     * The UUID of subnet service
     * 
     * @return serviceSubnetId
     **/
    public String getServiceSubnetId() {
        return serviceSubnetId;
    }

    public void setServiceSubnetId(String serviceSubnetId) {
        this.serviceSubnetId = serviceSubnetId;
    }

    /**
     * vni value
     * 
     * @return vni
     **/
    public String getVni() {
        return vni;
    }

    public void setVni(String vni) {
        this.vni = vni;
    }

    /**
     * The UUID of VLAN service
     * 
     * @return vlanId
     **/
    public String getVlanId() {
        return vlanId;
    }

    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    /**
     * The IP address of the subnet
     * 
     * @return cidrIpAddress
     **/
    public String getCidrIpAddress() {
        return cidrIpAddress;
    }

    public void setCidrIpAddress(String cidrIpAddress) {
        this.cidrIpAddress = cidrIpAddress;
    }

    /**
     * The mask of the subnet
     * 
     * @return cidrMask
     **/
    public String getCidrMask() {
        return cidrMask;
    }

    public void setCidrMask(String cidrMask) {
        this.cidrMask = cidrMask;
    }

    /**
     * The gateway IP address
     * 
     * @return gatewayIp
     **/
    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    /**
     * enable dhcp or not
     * 
     * @return enableDhcp
     **/
    public String getEnableDhcp() {
        return enableDhcp;
    }

    public void setEnableDhcp(String enableDhcp) {
        this.enableDhcp = enableDhcp;
    }

    /**
     * The start IP address of the subnet
     * 
     * @return ipRangeStartIp
     **/
    public String getIpRangeStartIp() {
        return ipRangeStartIp;
    }

    public void setIpRangeStartIp(String ipRangeStartIp) {
        this.ipRangeStartIp = ipRangeStartIp;
    }

    /**
     * The end IP address of the subnet
     * 
     * @return ipRangeEndIp
     **/
    public String getIpRangeEndIp() {
        return ipRangeEndIp;
    }

    public void setIpRangeEndIp(String ipRangeEndIp) {
        this.ipRangeEndIp = ipRangeEndIp;
    }

    /**
     * the scope is terminal,vm
     * 
     * @return useMode
     **/
    public String getUseMode() {
        return useMode;
    }

    public void setUseMode(String useMode) {
        this.useMode = useMode;
    }

    /**
     * change mode
     * 
     * @return changedMode
     **/
    public String getChangedMode() {
        return changedMode;
    }

    public void setChangedMode(String changedMode) {
        this.changedMode = changedMode;
    }

    /**
     * the scope is server,relay
     * 
     * @return dhcpMode
     **/
    public String getDhcpMode() {
        return dhcpMode;
    }

    public void setDhcpMode(String dhcpMode) {
        this.dhcpMode = dhcpMode;
    }

    /**
     * the scope is systemsetting,custom
     * 
     * @return dnsServerMode
     **/
    public String getDnsServerMode() {
        return dnsServerMode;
    }

    public void setDnsServerMode(String dnsServerMode) {
        this.dnsServerMode = dnsServerMode;
    }

    /**
     * the scope is true,false
     * 
     * @return unlimit
     **/
    public String getUnlimit() {
        return unlimit;
    }

    public void setUnlimit(String unlimit) {
        this.unlimit = unlimit;
    }

    /**
     * ipv6 address
     * 
     * @return ipv6Address
     **/
    public String getIpv6Address() {
        return ipv6Address;
    }

    public void setIpv6Address(String ipv6Address) {
        this.ipv6Address = ipv6Address;
    }

    /**
     * prefix length
     * 
     * @return prefixLength
     **/
    public String getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(String prefixLength) {
        this.prefixLength = prefixLength;
    }

    /**
     * the scope is true,false
     * 
     * @return dhcp6Enable
     **/
    public String getDhcp6Enable() {
        return dhcp6Enable;
    }

    public void setDhcp6Enable(String dhcp6Enable) {
        this.dhcp6Enable = dhcp6Enable;
    }

    /**
     * the scope is server,relay
     * 
     * @return dhcp6Mode
     **/
    public String getDhcp6Mode() {
        return dhcp6Mode;
    }

    public void setDhcp6Mode(String dhcp6Mode) {
        this.dhcp6Mode = dhcp6Mode;
    }

    /**
     * prior dns server
     * 
     * @return priorDnsServer
     **/
    public String getPriorDnsServer() {
        return priorDnsServer;
    }

    public void setPriorDnsServer(String priorDnsServer) {
        this.priorDnsServer = priorDnsServer;
    }

    /**
     * standby dns server
     * 
     * @return standbyDnsServer
     **/
    public String getStandbyDnsServer() {
        return standbyDnsServer;
    }

    public void setStandbyDnsServer(String standbyDnsServer) {
        this.standbyDnsServer = standbyDnsServer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiSubnetNetModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    neId: ").append(toIndentedString(neId)).append("\n");
        sb.append("    controllerId: ").append(toIndentedString(controllerId)).append("\n");
        sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
        sb.append("    serviceSubnetId: ").append(toIndentedString(serviceSubnetId)).append("\n");
        sb.append("    vni: ").append(toIndentedString(vni)).append("\n");
        sb.append("    vlanId: ").append(toIndentedString(vlanId)).append("\n");
        sb.append("    cidrIpAddress: ").append(toIndentedString(cidrIpAddress)).append("\n");
        sb.append("    cidrMask: ").append(toIndentedString(cidrMask)).append("\n");
        sb.append("    gatewayIp: ").append(toIndentedString(gatewayIp)).append("\n");
        sb.append("    enableDhcp: ").append(toIndentedString(enableDhcp)).append("\n");
        sb.append("    ipRangeStartIp: ").append(toIndentedString(ipRangeStartIp)).append("\n");
        sb.append("    ipRangeEndIp: ").append(toIndentedString(ipRangeEndIp)).append("\n");
        sb.append("    useMode: ").append(toIndentedString(useMode)).append("\n");
        sb.append("    changedMode: ").append(toIndentedString(changedMode)).append("\n");
        sb.append("    dhcpMode: ").append(toIndentedString(dhcpMode)).append("\n");
        sb.append("    dnsServerMode: ").append(toIndentedString(dnsServerMode)).append("\n");
        sb.append("    unlimit: ").append(toIndentedString(unlimit)).append("\n");
        sb.append("    ipv6Address: ").append(toIndentedString(ipv6Address)).append("\n");
        sb.append("    prefixLength: ").append(toIndentedString(prefixLength)).append("\n");
        sb.append("    dhcp6Enable: ").append(toIndentedString(dhcp6Enable)).append("\n");
        sb.append("    dhcp6Mode: ").append(toIndentedString(dhcp6Mode)).append("\n");
        sb.append("    priorDnsServer: ").append(toIndentedString(priorDnsServer)).append("\n");
        sb.append("    standbyDnsServer: ").append(toIndentedString(standbyDnsServer)).append("\n");
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
