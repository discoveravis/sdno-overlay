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
@XmlType(name = "CpeIpChange", propOrder = {"esn", "neId", "devId", "type"})

@XmlRootElement(name = "CpeIpChange")
@ApiModel(description = "deploy/undeploy model")
public class CpeIpChange {

    @XmlElement(name = "esn")
    @ApiModelProperty(example = "null", value = "esn")
    private String esn = null;

    @XmlElement(name = "neId")
    @ApiModelProperty(example = "null", required = true, value = "ne id")
    private String neId = null;

    @XmlElement(name = "devId")
    @ApiModelProperty(example = "null", required = true, value = "device id")
    private String devId = null;

    @XmlElement(name = "type")
    @ApiModelProperty(example = "null", value = "type")
    private String type = null;

    /**
     * esn
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
     * ne id
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
     * @return devId
     **/
    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    /**
     * type
     * 
     * @return type
     **/
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CpeIpChange {\n");

        sb.append("    esn: ").append(toIndentedString(esn)).append("\n");
        sb.append("    neId: ").append(toIndentedString(neId)).append("\n");
        sb.append("    devId: ").append(toIndentedString(devId)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
