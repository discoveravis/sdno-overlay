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

import org.openo.sdno.overlayvpn.model.v2.basemodel.SbiBaseNetModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiNeVxlanInterface", propOrder = {"localName", "vxlanInstanceId", "accessType", "portNativeId",
                "dot1qVlanBitmap"})

public class SbiNeVxlanInterface extends SbiBaseNetModel {

    @XmlElement(name = "localName")
    @ApiModelProperty(example = "null", required = true, value = "vxlan's name on controller")
    private String localName = null;

    @XmlElement(name = "vxlanInstanceId")
    @ApiModelProperty(example = "null", required = true, value = "bound vxlan instance id")
    private String vxlanInstanceId = null;

    @XmlElement(name = "accessType")
    @ApiModelProperty(example = "null", value = "matching access type of message")
    private String accessType = null;

    @XmlElement(name = "portNativeId")
    @ApiModelProperty(example = "null", value = "physical id of associated access point")
    private String portNativeId = null;

    @XmlElement(name = "dot1qVlanBitmap")
    @ApiModelProperty(example = "null", value = "access vlan range, set when accessType is dot1q")
    private String dot1qVlanBitmap = null;

    /**
     * vxlan's name on controller
     * 
     * @return localName
     **/
    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
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
     * matching access type of message
     * 
     * @return accessType
     **/
    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    /**
     * physical id of associated access point
     * 
     * @return portNativeId
     **/
    public String getPortNativeId() {
        return portNativeId;
    }

    public void setPortNativeId(String portNativeId) {
        this.portNativeId = portNativeId;
    }

    /**
     * access vlan range, set when accessType is dot1q
     * 
     * @return dot1qVlanBitmap
     **/
    public String getDot1qVlanBitmap() {
        return dot1qVlanBitmap;
    }

    public void setDot1qVlanBitmap(String dot1qVlanBitmap) {
        this.dot1qVlanBitmap = dot1qVlanBitmap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiNeVxlanInterface {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    localName: ").append(toIndentedString(localName)).append("\n");
        sb.append("    vxlanInstanceId: ").append(toIndentedString(vxlanInstanceId)).append("\n");
        sb.append("    accessType: ").append(toIndentedString(accessType)).append("\n");
        sb.append("    portNativeId: ").append(toIndentedString(portNativeId)).append("\n");
        sb.append("    dot1qVlanBitmap: ").append(toIndentedString(dot1qVlanBitmap)).append("\n");
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
