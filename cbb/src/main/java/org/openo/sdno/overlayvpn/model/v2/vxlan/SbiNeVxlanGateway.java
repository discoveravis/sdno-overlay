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
@XmlType(name = "SbiNeVxlanGateway", propOrder = {"neRole", "vxlanInstanceId", "ipAddress", "ipMask"})

public class SbiNeVxlanGateway extends SbiBaseNetModel {

    @XmlElement(name = "neRole")
    @ApiModelProperty(example = "null", value = "role of Ne")
    private String neRole = null;

    @XmlElement(name = "vxlanInstanceId")
    @ApiModelProperty(example = "null", required = true, value = "bound vxlan instance id")
    private String vxlanInstanceId = null;

    @XmlElement(name = "ipAddress")
    @ApiModelProperty(example = "null", value = "ip address")
    private String ipAddress = null;

    @XmlElement(name = "ipMask")
    @ApiModelProperty(example = "null", value = "ip mask")
    private String ipMask = null;

    /**
     * role of Ne
     * 
     * @return neRole
     **/
    public String getNeRole() {
        return neRole;
    }

    public void setNeRole(String neRole) {
        this.neRole = neRole;
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
     * ip address
     * 
     * @return ipAddress
     **/
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiNeVxlanGateway {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    neRole: ").append(toIndentedString(neRole)).append("\n");
        sb.append("    vxlanInstanceId: ").append(toIndentedString(vxlanInstanceId)).append("\n");
        sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
        sb.append("    ipMask: ").append(toIndentedString(ipMask)).append("\n");
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
