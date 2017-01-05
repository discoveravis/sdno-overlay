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

package org.openo.sdno.overlayvpn.model.v2.basemodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiBaseNetModel", propOrder = {"deviceId", "peerDeviceId", "controllerId", "connectionId",
                "externalId"})

public class SbiBaseNetModel extends BaseModel {

    @XmlElement(name = "deviceId")
    @ApiModelProperty(example = "null", required = true, value = "device ID")
    private String deviceId = null;

    @XmlElement(name = "peerDeviceId")
    @ApiModelProperty(example = "null", value = "peerDevice ID")
    private String peerDeviceId = null;

    @XmlElement(name = "controllerId")
    @ApiModelProperty(example = "null", value = "controller ID")
    private String controllerId = null;

    @XmlElement(name = "connectionId")
    @ApiModelProperty(example = "null", value = "connection ID")
    private String connectionId = null;

    @XmlElement(name = "externalId")
    @ApiModelProperty(example = "null", value = "external ID")
    private String externalId = null;

    /**
     * device ID
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
     * peerDevice ID
     * 
     * @return peerDeviceId
     **/
    public String getPeerDeviceId() {
        return peerDeviceId;
    }

    public void setPeerDeviceId(String peerDeviceId) {
        this.peerDeviceId = peerDeviceId;
    }

    /**
     * controller ID
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
     * connection ID
     * 
     * @return connectionId
     **/
    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    /**
     * external ID
     * 
     * @return externalId
     **/
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiBaseNetModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
        sb.append("    peerDeviceId: ").append(toIndentedString(peerDeviceId)).append("\n");
        sb.append("    controllerId: ").append(toIndentedString(controllerId)).append("\n");
        sb.append("    connectionId: ").append(toIndentedString(connectionId)).append("\n");
        sb.append("    externalId: ").append(toIndentedString(externalId)).append("\n");
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
