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

package org.openo.sdno.overlayvpn.model.v2.interfaceip;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiInterfaceIpConfig", propOrder = {"interfaceName", "mode", "ip", "netMask", "mode6", "ipv6Address",
                "prefixLength"})

@XmlRootElement(name = "SbiInterfaceIpConfig")
public class SbiInterfaceIpConfig {

    @XmlElement(name = "interfaceName")
    @ApiModelProperty(example = "null", value = "interface name")
    private String interfaceName = null;

    @XmlElement(name = "mode")
    @ApiModelProperty(example = "null", required = true, value = "IP address assignment mode, the scope is dhcp,manual")
    private String mode = null;

    @XmlElement(name = "ip")
    @ApiModelProperty(example = "null", value = "IP address")
    private String ip = null;

    @XmlElement(name = "netMask")
    @ApiModelProperty(example = "null", value = "the mask of IP address")
    private String netMask = null;

    @XmlElement(name = "mode6")
    @ApiModelProperty(example = "null", required = true, value = "ipv6 address assignment mode, the scope is dhcp,manual")
    private String mode6 = null;

    @XmlElement(name = "ipv6Address")
    @ApiModelProperty(example = "null", value = "ipv6 address")
    private String ipv6Address = null;

    @XmlElement(name = "prefixLength")
    @ApiModelProperty(example = "null", value = "prefix length")
    private Long prefixLength = null;

    /**
     * interface name
     * 
     * @return interfaceName
     **/
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * IP address assignment mode, the scope is dhcp,manual
     * 
     * @return mode
     **/
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * IP address
     * 
     * @return ip
     **/
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * the mask of IP address
     * 
     * @return netMask
     **/
    public String getNetMask() {
        return netMask;
    }

    public void setNetMask(String netMask) {
        this.netMask = netMask;
    }

    /**
     * ipv6 address assignment mode, the scope is dhcp,manual
     * 
     * @return mode6
     **/
    public String getMode6() {
        return mode6;
    }

    public void setMode6(String mode6) {
        this.mode6 = mode6;
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
    public Long getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(Long prefixLength) {
        this.prefixLength = prefixLength;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiInterfaceIpConfig {\n");

        sb.append("    interfaceName: ").append(toIndentedString(interfaceName)).append("\n");
        sb.append("    mode: ").append(toIndentedString(mode)).append("\n");
        sb.append("    ip: ").append(toIndentedString(ip)).append("\n");
        sb.append("    netMask: ").append(toIndentedString(netMask)).append("\n");
        sb.append("    mode6: ").append(toIndentedString(mode6)).append("\n");
        sb.append("    ipv6Address: ").append(toIndentedString(ipv6Address)).append("\n");
        sb.append("    prefixLength: ").append(toIndentedString(prefixLength)).append("\n");
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
