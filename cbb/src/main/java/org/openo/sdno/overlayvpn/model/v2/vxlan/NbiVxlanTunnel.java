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
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.NbiBaseServiceModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NbiVxlanTunnel", propOrder = {"type", "destNeId", "destNeRole", "srcPortName", "destPortName", "vni",
                "srcTunnelName", "destTunnelName", "portVlanList", "srcVbdifIp", "destVbdifIp"})

public class NbiVxlanTunnel extends NbiBaseServiceModel {

    @XmlElement(name = "type")
    @ApiModelProperty(example = "null", required = true, value = "vxlan's type(scope:vxlan,ipv6 over ipv4,ipv4 over ipv6,l3-gw-vxlan)")
    private String type = null;

    @XmlElement(name = "destNeId")
    @ApiModelProperty(example = "null", required = true, value = "destination ne id")
    private String destNeId = null;

    @XmlElement(name = "destNeRole")
    @ApiModelProperty(example = "null", required = true, value = "destination ne role(scope:localcpe,cloudcpe,vpc,dc-r)")
    private String destNeRole = null;

    @XmlElement(name = "srcPortName")
    @ApiModelProperty(example = "null", value = "source port name")
    private String srcPortName = null;

    @XmlElement(name = "destPortName")
    @ApiModelProperty(example = "null", value = "destination port name")
    private String destPortName = null;

    @XmlElement(name = "vni")
    @ApiModelProperty(example = "null", required = true, value = "vni")
    private String vni = null;

    @XmlElement(name = "srcTunnelName")
    @ApiModelProperty(example = "null", value = "source tunnel name")
    private String srcTunnelName = null;

    @XmlElement(name = "destTunnelName")
    @ApiModelProperty(example = "null", value = "destination tunnel name")
    private String destTunnelName = null;

    @XmlElement(name = "portVlanList")
    @ApiModelProperty(example = "null", value = "vxlan's name on controller")
    private String portVlanList = null;

    @XmlElement(name = "srcVbdifIp")
    @ApiModelProperty(example = "null", value = "L3 vxlan's vbdif ip, no need when creating,but need return after creating,return format is json of Ip.")
    private String srcVbdifIp = null;

    @XmlElement(name = "destVbdifIp")
    @ApiModelProperty(example = "null", value = "L3 vxlan's vbdif ip, no need when creating,but need return after creating,return format is json of Ip.")
    private String destVbdifIp = null;

    /**
     * vxlan's type(scope:vxlan,ipv6 over ipv4,ipv4 over ipv6,l3-gw-vxlan)
     * 
     * @return type
     **/
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * destination ne id
     * 
     * @return destNeId
     **/
    public String getDestNeId() {
        return destNeId;
    }

    public void setDestNeId(String destNeId) {
        this.destNeId = destNeId;
    }

    /**
     * destination ne role(scope:localcpe,cloudcpe,vpc,dc-r)
     * 
     * @return destNeRole
     **/
    public String getDestNeRole() {
        return destNeRole;
    }

    public void setDestNeRole(String destNeRole) {
        this.destNeRole = destNeRole;
    }

    /**
     * source port name
     * 
     * @return srcPortName
     **/
    public String getSrcPortName() {
        return srcPortName;
    }

    public void setSrcPortName(String srcPortName) {
        this.srcPortName = srcPortName;
    }

    /**
     * destination port name
     * 
     * @return destPortName
     **/
    public String getDestPortName() {
        return destPortName;
    }

    public void setDestPortName(String destPortName) {
        this.destPortName = destPortName;
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
     * source tunnel name
     * 
     * @return srcTunnelName
     **/
    public String getSrcTunnelName() {
        return srcTunnelName;
    }

    public void setSrcTunnelName(String srcTunnelName) {
        this.srcTunnelName = srcTunnelName;
    }

    /**
     * destination tunnel name
     * 
     * @return destTunnelName
     **/
    public String getDestTunnelName() {
        return destTunnelName;
    }

    public void setDestTunnelName(String destTunnelName) {
        this.destTunnelName = destTunnelName;
    }

    /**
     * vxlan's name on controller
     * 
     * @return portVlanList
     **/
    public String getPortVlanList() {
        return portVlanList;
    }

    public void setPortVlanList(String portVlanList) {
        this.portVlanList = portVlanList;
    }

    /**
     * L3 vxlan's vbdif ip, no need when creating,but need return after creating,return format is
     * json of Ip.
     * 
     * @return srcVbdifIp
     **/
    public String getSrcVbdifIp() {
        return srcVbdifIp;
    }

    public void setSrcVbdifIp(String srcVbdifIp) {
        this.srcVbdifIp = srcVbdifIp;
    }

    /**
     * L3 vxlan's vbdif ip, no need when creating,but need return after creating,return format is
     * json of Ip.
     * 
     * @return destVbdifIp
     **/
    public String getDestVbdifIp() {
        return destVbdifIp;
    }

    public void setDestVbdifIp(String destVbdifIp) {
        this.destVbdifIp = destVbdifIp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiVxlanTunnel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    destNeId: ").append(toIndentedString(destNeId)).append("\n");
        sb.append("    destNeRole: ").append(toIndentedString(destNeRole)).append("\n");
        sb.append("    srcPortName: ").append(toIndentedString(srcPortName)).append("\n");
        sb.append("    destPortName: ").append(toIndentedString(destPortName)).append("\n");
        sb.append("    vni: ").append(toIndentedString(vni)).append("\n");
        sb.append("    srcTunnelName: ").append(toIndentedString(srcTunnelName)).append("\n");
        sb.append("    destTunnelName: ").append(toIndentedString(destTunnelName)).append("\n");
        sb.append("    portVlanList: ").append(toIndentedString(portVlanList)).append("\n");
        sb.append("    srcVbdifIp: ").append(toIndentedString(srcVbdifIp)).append("\n");
        sb.append("    destVbdifIp: ").append(toIndentedString(destVbdifIp)).append("\n");
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
