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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseServiceModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NbiSubnetModel", propOrder = {"siteId", "cidrBlock", "cidrBlockSize", "gatewayIp", "vlanId", "vni",
                "ports", "portNames", "ipv6Address", "prefixLength", "dhcp6Enable", "dhcp6Mode", "enableDhcp",
                "gatewayInterface", "createTime", "updateTime"})

public class NbiSubnetModel extends BaseServiceModel {

    @XmlElement(name = "siteId")
    @ApiModelProperty(example = "null", value = "site id")
    private String siteId = null;

    @XmlElement(name = "cidrBlock")
    @ApiModelProperty(example = "null", value = "cidr block")
    private String cidrBlock = null;

    @XmlElement(name = "cidrBlockSize")
    @ApiModelProperty(example = "null", value = "cidr block size 8~32")
    private Integer cidrBlockSize = null;

    @XmlElement(name = "gatewayIp")
    @ApiModelProperty(example = "null", value = "gateway ip")
    private String gatewayIp = null;

    @XmlElement(name = "vlanId")
    @ApiModelProperty(example = "null", value = "vlan id")
    private String vlanId = null;

    @XmlElement(name = "vni")
    @ApiModelProperty(example = "null", value = "vni")
    private String vni = null;

    @XmlElement(name = "ports")
    @ApiModelProperty(example = "null", value = "ports list")
    private List<String> ports = new ArrayList<String>();

    @XmlElement(name = "portNames")
    @ApiModelProperty(example = "null", value = "port name list")
    private List<String> portNames = new ArrayList<String>();

    @XmlElement(name = "ipv6Address")
    @ApiModelProperty(example = "null", value = "ipv6 address")
    private String ipv6Address = null;

    @XmlElement(name = "prefixLength")
    @ApiModelProperty(example = "null", value = "length of prifix")
    private String prefixLength = null;

    @XmlElement(name = "dhcp6Enable")
    @ApiModelProperty(example = "null", value = "whether enable dhcp6")
    private String dhcp6Enable = null;

    @XmlElement(name = "dhcp6Mode")
    @ApiModelProperty(example = "null", value = "dhcp mode scope =\"server, relay\"")
    private String dhcp6Mode = null;

    @XmlElement(name = "enableDhcp")
    @ApiModelProperty(example = "null", value = "whether enable dhcp")
    private String enableDhcp = null;

    @XmlElement(name = "gatewayInterface")
    @ApiModelProperty(example = "null", value = "BDIF interface name, when vni is not null and need to read from AC, pass this parameter to lower layer")
    private String gatewayInterface = null;

    @XmlElement(name = "createTime")
    @ApiModelProperty(example = "null", value = "create time")
    private String createTime = null;

    @XmlElement(name = "updateTime")
    @ApiModelProperty(example = "null", value = "update time")
    private String updateTime = null;

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
     * cidr block
     * 
     * @return cidrBlock
     **/
    public String getCidrBlock() {
        return cidrBlock;
    }

    public void setCidrBlock(String cidrBlock) {
        this.cidrBlock = cidrBlock;
    }

    /**
     * cidr block size 8~32
     * 
     * @return cidrBlockSize
     **/
    public Integer getCidrBlockSize() {
        return cidrBlockSize;
    }

    public void setCidrBlockSize(Integer cidrBlockSize) {
        this.cidrBlockSize = cidrBlockSize;
    }

    /**
     * gateway ip
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
     * vlan id
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
     * vni
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
     * ports list
     * 
     * @return ports
     **/
    public List<String> getPorts() {
        return ports;
    }

    public void setPorts(List<String> ports) {
        this.ports = ports;
    }

    /**
     * port name list
     * 
     * @return portNames
     **/
    public List<String> getPortNames() {
        return portNames;
    }

    public void setPortNames(List<String> portNames) {
        this.portNames = portNames;
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
     * length of prifix
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
     * whether enable dhcp6
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
     * dhcp mode scope =\"server, relay\"
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
     * whether enable dhcp
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
     * BDIF interface name, when vni is not null and need to read from AC, pass this parameter to
     * lower layer
     * 
     * @return gatewayInterface
     **/
    public String getGatewayInterface() {
        return gatewayInterface;
    }

    public void setGatewayInterface(String gatewayInterface) {
        this.gatewayInterface = gatewayInterface;
    }

    /**
     * create time
     * 
     * @return createTime
     **/
    @Override
    public String getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * update time
     * 
     * @return updateTime
     **/
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiSubnetModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    siteId: ").append(toIndentedString(siteId)).append("\n");
        sb.append("    cidrBlock: ").append(toIndentedString(cidrBlock)).append("\n");
        sb.append("    cidrBlockSize: ").append(toIndentedString(cidrBlockSize)).append("\n");
        sb.append("    gatewayIp: ").append(toIndentedString(gatewayIp)).append("\n");
        sb.append("    vlanId: ").append(toIndentedString(vlanId)).append("\n");
        sb.append("    vni: ").append(toIndentedString(vni)).append("\n");
        sb.append("    ports: ").append(toIndentedString(ports)).append("\n");
        sb.append("    portNames: ").append(toIndentedString(portNames)).append("\n");
        sb.append("    ipv6Address: ").append(toIndentedString(ipv6Address)).append("\n");
        sb.append("    prefixLength: ").append(toIndentedString(prefixLength)).append("\n");
        sb.append("    dhcp6Enable: ").append(toIndentedString(dhcp6Enable)).append("\n");
        sb.append("    dhcp6Mode: ").append(toIndentedString(dhcp6Mode)).append("\n");
        sb.append("    enableDhcp: ").append(toIndentedString(enableDhcp)).append("\n");
        sb.append("    gatewayInterface: ").append(toIndentedString(gatewayInterface)).append("\n");
        sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
        sb.append("    updateTime: ").append(toIndentedString(updateTime)).append("\n");
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
