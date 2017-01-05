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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiSubnetBdInfoModel", propOrder = {"controllerId", "neId", "deviceId", "vni", "subnetId", "bdId",
                "vbdifName"})

@XmlRootElement(name = "SbiSubnetBdInfoModel")
public class SbiSubnetBdInfoModel {

    @XmlElement(name = "controllerId")
    @ApiModelProperty(example = "null", value = "controller Id")
    private String controllerId = null;

    @XmlElement(name = "neId")
    @ApiModelProperty(example = "null", value = "NE Id")
    private String neId = null;

    @XmlElement(name = "deviceId")
    @ApiModelProperty(example = "null", value = "device Id")
    private String deviceId = null;

    @XmlElement(name = "vni")
    @ApiModelProperty(example = "null", value = "vni value")
    private String vni = null;

    @XmlElement(name = "subnetId")
    @ApiModelProperty(example = "null", value = "subnet Id")
    private String subnetId = null;

    @XmlElement(name = "bdId")
    @ApiModelProperty(example = "null", value = "broadcast domain Id")
    private String bdId = null;

    @XmlElement(name = "vbdifName")
    @ApiModelProperty(example = "null", value = "The interface name of broadcast domain")
    private String vbdifName = null;

    /**
     * controller Id
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
     * NE Id
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
     * device Id
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
     * vni value
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
     * subnet Id
     * 
     * @return subnetId
     **/
    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    /**
     * broadcast domain Id
     * 
     * @return bdId
     **/
    public String getBdId() {
        return bdId;
    }

    public void setBdId(String bdId) {
        this.bdId = bdId;
    }

    /**
     * The interface name of broadcast domain
     * 
     * @return vbdifName
     **/
    public String getVbdifName() {
        return vbdifName;
    }

    public void setVbdifName(String vbdifName) {
        this.vbdifName = vbdifName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiSubnetBdInfoModel {\n");

        sb.append("    controllerId: ").append(toIndentedString(controllerId)).append("\n");
        sb.append("    neId: ").append(toIndentedString(neId)).append("\n");
        sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
        sb.append("    vni: ").append(toIndentedString(vni)).append("\n");
        sb.append("    subnetId: ").append(toIndentedString(subnetId)).append("\n");
        sb.append("    bdId: ").append(toIndentedString(bdId)).append("\n");
        sb.append("    vbdifName: ").append(toIndentedString(vbdifName)).append("\n");
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
