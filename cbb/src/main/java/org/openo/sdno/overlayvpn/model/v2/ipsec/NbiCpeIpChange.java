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

package org.openo.sdno.overlayvpn.model.v2.ipsec;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NbiCpeIpChange", propOrder = {"esn", "neId", "devId", "type", "interfaces"})

@XmlRootElement(name = "NbiCpeIpChange")
public class NbiCpeIpChange {

    @XmlElement(name = "esn")
    @ApiModelProperty(example = "null", value = "")
    private String esn = null;

    @XmlElement(name = "neId")
    @ApiModelProperty(example = "null", required = true, value = "")
    private String neId = null;

    @XmlElement(name = "devId")
    @ApiModelProperty(example = "null", required = true, value = "")
    private String devId = null;

    @XmlElement(name = "type")
    @ApiModelProperty(example = "null", value = "")
    private String type = null;

    @XmlElement(name = "interfaces")
    @ApiModelProperty(example = "null", value = "")
    private List<NbiCpeIpChangeItem> interfaces = new ArrayList<NbiCpeIpChangeItem>();

    /**
     * Get esn
     * 
     * @return esn
     **/
    public String getEsn() {
        return esn;
    }

    public void setEsn(String esn) {
        this.esn = esn;
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
     * Get devId
     * 
     * @return devId
     **/
    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    /**
     * Get type
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
     * Get interfaces
     * 
     * @return interfaces
     **/
    public List<NbiCpeIpChangeItem> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<NbiCpeIpChangeItem> interfaces) {
        this.interfaces = interfaces;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiCpeIpChange {\n");

        sb.append("    esn: ").append(toIndentedString(esn)).append("\n");
        sb.append("    neId: ").append(toIndentedString(neId)).append("\n");
        sb.append("    devId: ").append(toIndentedString(devId)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    interfaces: ").append(toIndentedString(interfaces)).append("\n");
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
