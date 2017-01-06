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

package org.openo.sdno.overlayvpn.model.v2.cpe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiDeviceCreateBasicInfo", propOrder = {"name", "esn", "orgnizationName", "description"})

public class SbiDeviceCreateBasicInfo extends UuidModel {

    @XmlElement(name = "name")
    @ApiModelProperty(example = "null", value = "device name")
    private String name = null;

    @XmlElement(name = "esn")
    @ApiModelProperty(example = "null", required = true, value = "electronic serial number")
    private String esn = null;

    @XmlElement(name = "orgnizationName")
    @ApiModelProperty(example = "null", value = "orgnization name")
    private String orgnizationName = null;

    @XmlElement(name = "description")
    @ApiModelProperty(example = "null", value = "description info")
    private String description = null;

    /**
     * device name
     * 
     * @return name
     **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * electronic serial number
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
     * orgnization name
     * 
     * @return orgnizationName
     **/
    public String getOrgnizationName() {
        return orgnizationName;
    }

    public void setOrgnizationName(String orgnizationName) {
        this.orgnizationName = orgnizationName;
    }

    /**
     * description info
     * 
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiDeviceCreateBasicInfo {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    esn: ").append(toIndentedString(esn)).append("\n");
        sb.append("    orgnizationName: ").append(toIndentedString(orgnizationName)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
