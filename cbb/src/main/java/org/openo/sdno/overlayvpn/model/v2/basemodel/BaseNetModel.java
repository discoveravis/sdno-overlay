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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseNetModel", propOrder = {"controllerId", "externalId", "connectionServiceId", "neId", "peerNeId",
                "deviceId", "peerDeviceId"})

@ApiModel(description = "base net model")
public class BaseNetModel extends BaseModel {

    @XmlElement(name = "controllerId")
    @ApiModelProperty(example = "null", value = "")
    private String controllerId = null;

    @XmlElement(name = "externalId")
    @ApiModelProperty(example = "null", value = "")
    private String externalId = null;

    @XmlElement(name = "connectionServiceId")
    @ApiModelProperty(example = "null", required = true, value = "")
    private String connectionServiceId = null;

    @XmlElement(name = "neId")
    @ApiModelProperty(example = "null", required = true, value = "")
    private String neId = null;

    @XmlElement(name = "peerNeId")
    @ApiModelProperty(example = "null", value = "")
    private String peerNeId = null;

    @XmlElement(name = "deviceId")
    @ApiModelProperty(example = "null", required = true, value = "")
    private String deviceId = null;

    @XmlElement(name = "peerDeviceId")
    @ApiModelProperty(example = "null", value = "")
    private String peerDeviceId = null;

    /**
     * Get controllerId
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
     * Get externalId
     * 
     * @return externalId
     **/
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
     * Get connectionServiceId
     * 
     * @return connectionServiceId
     **/
    public String getConnectionServiceId() {
        return connectionServiceId;
    }

    public void setConnectionServiceId(String connectionServiceId) {
        this.connectionServiceId = connectionServiceId;
    }

    /**
     * Get neId
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
     * Get peerNeId
     * 
     * @return peerNeId
     **/
    public String getPeerNeId() {
        return peerNeId;
    }

    public void setPeerNeId(String peerNeId) {
        this.peerNeId = peerNeId;
    }

    /**
     * Get deviceId
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
     * Get peerDeviceId
     * 
     * @return peerDeviceId
     **/
    public String getPeerDeviceId() {
        return peerDeviceId;
    }

    public void setPeerDeviceId(String peerDeviceId) {
        this.peerDeviceId = peerDeviceId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BaseNetModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    controllerId: ").append(toIndentedString(controllerId)).append("\n");
        sb.append("    externalId: ").append(toIndentedString(externalId)).append("\n");
        sb.append("    connectionServiceId: ").append(toIndentedString(connectionServiceId)).append("\n");
        sb.append("    neId: ").append(toIndentedString(neId)).append("\n");
        sb.append("    peerNeId: ").append(toIndentedString(peerNeId)).append("\n");
        sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
        sb.append("    peerDeviceId: ").append(toIndentedString(peerDeviceId)).append("\n");
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
