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
@XmlType(name = "SbiNeVxlanTunnelStatus", propOrder = {"deviceId", "localIp", "peerIp"})

@XmlRootElement(name = "SbiNeVxlanTunnelStatus")
@ApiModel(description = "Sbi Ne Vxlan Tunnel Status model")
public class SbiNeVxlanTunnelStatus {

    @XmlElement(name = "deviceId")
    @ApiModelProperty(example = "null", required = true, value = "device id")
    private String deviceId = null;

    @XmlElement(name = "localIp")
    @ApiModelProperty(example = "null", value = "local ip")
    private String localIp = null;

    @XmlElement(name = "peerIp")
    @ApiModelProperty(example = "null", value = "peer ip")
    private String peerIp = null;

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
     * local ip
     * 
     * @return localIp
     **/
    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    /**
     * peer ip
     * 
     * @return peerIp
     **/
    public String getPeerIp() {
        return peerIp;
    }

    public void setPeerIp(String peerIp) {
        this.peerIp = peerIp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiNeVxlanTunnelStatus {\n");

        sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
        sb.append("    localIp: ").append(toIndentedString(localIp)).append("\n");
        sb.append("    peerIp: ").append(toIndentedString(peerIp)).append("\n");
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
