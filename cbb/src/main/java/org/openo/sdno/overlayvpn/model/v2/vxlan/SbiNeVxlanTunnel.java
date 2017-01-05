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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.SbiBaseNetModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiNeVxlanTunnel", propOrder = {"vni", "vxlanInstanceId", "sourceAddress", "destAddress", "sourceIfId",
                "tunnelIfId", "vxlanInstances", "vnis"})

public class SbiNeVxlanTunnel extends SbiBaseNetModel {

    @XmlElement(name = "vni")
    @ApiModelProperty(example = "null", value = "vlan id")
    private String vni = null;

    @XmlElement(name = "vxlanInstanceId")
    @ApiModelProperty(example = "null", required = true, value = "bound vxlan instance id")
    private String vxlanInstanceId = null;

    @XmlElement(name = "sourceAddress")
    @ApiModelProperty(example = "null", value = "source address")
    private String sourceAddress = null;

    @XmlElement(name = "destAddress")
    @ApiModelProperty(example = "null", value = "destination address")
    private String destAddress = null;

    @XmlElement(name = "sourceIfId")
    @ApiModelProperty(example = "null", value = "source ifid")
    private String sourceIfId = null;

    @XmlElement(name = "tunnelIfId")
    @ApiModelProperty(example = "null", value = "tunnel ifid")
    private String tunnelIfId = null;

    @XmlElement(name = "vxlanInstances")
    @ApiModelProperty(example = "null", value = "")
    private List<String> vxlanInstances = new ArrayList<String>();

    @XmlElement(name = "vnis")
    @ApiModelProperty(example = "null", value = "")
    private List<String> vnis = new ArrayList<String>();

    /**
     * vlan id
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
     * bound vxlan instance id
     * 
     * @return vxlanInstanceId
     **/
    public String getVxlanInstanceId() {
        return vxlanInstanceId;
    }

    public void setVxlanInstanceId(String vxlanInstanceId) {
        this.vxlanInstanceId = vxlanInstanceId;
    }

    /**
     * source address
     * 
     * @return sourceAddress
     **/
    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    /**
     * destination address
     * 
     * @return destAddress
     **/
    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * source ifid
     * 
     * @return sourceIfId
     **/
    public String getSourceIfId() {
        return sourceIfId;
    }

    public void setSourceIfId(String sourceIfId) {
        this.sourceIfId = sourceIfId;
    }

    /**
     * tunnel ifid
     * 
     * @return tunnelIfId
     **/
    public String getTunnelIfId() {
        return tunnelIfId;
    }

    public void setTunnelIfId(String tunnelIfId) {
        this.tunnelIfId = tunnelIfId;
    }

    /**
     * Get vxlanInstances
     * 
     * @return vxlanInstances
     **/
    public List<String> getVxlanInstances() {
        return vxlanInstances;
    }

    public void setVxlanInstances(List<String> vxlanInstances) {
        this.vxlanInstances = vxlanInstances;
    }

    /**
     * Get vnis
     * 
     * @return vnis
     **/
    public List<String> getVnis() {
        return vnis;
    }

    public void setVnis(List<String> vnis) {
        this.vnis = vnis;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiNeVxlanTunnel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    vni: ").append(toIndentedString(vni)).append("\n");
        sb.append("    vxlanInstanceId: ").append(toIndentedString(vxlanInstanceId)).append("\n");
        sb.append("    sourceAddress: ").append(toIndentedString(sourceAddress)).append("\n");
        sb.append("    destAddress: ").append(toIndentedString(destAddress)).append("\n");
        sb.append("    sourceIfId: ").append(toIndentedString(sourceIfId)).append("\n");
        sb.append("    tunnelIfId: ").append(toIndentedString(tunnelIfId)).append("\n");
        sb.append("    vxlanInstances: ").append(toIndentedString(vxlanInstances)).append("\n");
        sb.append("    vnis: ").append(toIndentedString(vnis)).append("\n");
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
