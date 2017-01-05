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

package org.openo.sdno.overlayvpn.model.v2.vxlan;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ip", propOrder = {"ipv4", "ipv6", "ipMask", "prefixLength", "neId", "deviceId", "vxlanTunnelId"})

@XmlRootElement(name = "Ip")
@ApiModel(description = "ip model")
public class Ip {

    @XmlElement(name = "ipv4")
    @ApiModelProperty(example = "null", required = true, value = "ipv4 ip")
    private String ipv4 = null;

    @XmlElement(name = "ipv6")
    @ApiModelProperty(example = "null", value = "ipv6 ip")
    private String ipv6 = null;

    @XmlElement(name = "ipMask")
    @ApiModelProperty(example = "null", required = true, value = "ip mask")
    private String ipMask = null;

    @XmlElement(name = "prefixLength")
    @ApiModelProperty(example = "null", value = "length of prifix")
    private String prefixLength = null;

    @XmlElement(name = "neId")
    @ApiModelProperty(example = "null", value = "id of ne")
    private String neId = null;

    @XmlElement(name = "deviceId")
    @ApiModelProperty(example = "null", value = "device id")
    private String deviceId = null;

    @XmlElement(name = "vxlanTunnelId")
    @ApiModelProperty(example = "null", value = "vxlan tunnel id")
    private String vxlanTunnelId = null;

    /**
     * ipv4 ip
     * 
     * @return ipv4
     **/
    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    /**
     * ipv6 ip
     * 
     * @return ipv6
     **/
    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    /**
     * ip mask
     * 
     * @return ipMask
     **/
    public String getIpMask() {
        return ipMask;
    }

    public void setIpMask(String ipMask) {
        this.ipMask = ipMask;
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
     * id of ne
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
     * device id
     * 
     * @return deviceId
     **/
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * vxlan tunnel id
     * 
     * @return vxlanTunnelId
     **/
    public String getVxlanTunnelId() {
        return vxlanTunnelId;
    }

    public void setVxlanTunnelId(String vxlanTunnelId) {
        this.vxlanTunnelId = vxlanTunnelId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Ip {\n");

        sb.append("    ipv4: ").append(toIndentedString(ipv4)).append("\n");
        sb.append("    ipv6: ").append(toIndentedString(ipv6)).append("\n");
        sb.append("    ipMask: ").append(toIndentedString(ipMask)).append("\n");
        sb.append("    prefixLength: ").append(toIndentedString(prefixLength)).append("\n");
        sb.append("    neId: ").append(toIndentedString(neId)).append("\n");
        sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
        sb.append("    vxlanTunnelId: ").append(toIndentedString(vxlanTunnelId)).append("\n");
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
