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
@XmlType(name = "NbiLocalCpeModel", propOrder = {"tenantId", "siteId", "name", "esn", "oldEsn", "controllerId",
                "localCpeType", "description"})

public class NbiLocalCpeModel extends UuidModel {

    @XmlElement(name = "tenantId")
    @ApiModelProperty(example = "null", required = true, value = "tenant id")
    private String tenantId = null;

    @XmlElement(name = "siteId")
    @ApiModelProperty(example = "null", required = true, value = "site id")
    private String siteId = null;

    @XmlElement(name = "name")
    @ApiModelProperty(example = "null", value = "name")
    private String name = null;

    @XmlElement(name = "esn")
    @ApiModelProperty(example = "null", value = "esn")
    private String esn = null;

    @XmlElement(name = "oldEsn")
    @ApiModelProperty(example = "null", value = "old esn")
    private String oldEsn = null;

    @XmlElement(name = "controllerId")
    @ApiModelProperty(example = "null", value = "controller id")
    private String controllerId = null;

    @XmlElement(name = "localCpeType")
    @ApiModelProperty(example = "null", value = "local cpe type")
    private String localCpeType = null;

    @XmlElement(name = "description")
    @ApiModelProperty(example = "null", value = "description")
    private String description = null;

    /**
     * tenant id
     * 
     * @return tenantId
     **/
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * site id
     * 
     * @return siteId
     **/
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    /**
     * name
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
     * old esn
     * 
     * @return oldEsn
     **/
    public String getOldEsn() {
        return oldEsn;
    }

    public void setOldEsn(String oldEsn) {
        this.oldEsn = oldEsn;
    }

    /**
     * controller id
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
     * local cpe type
     * 
     * @return localCpeType
     **/
    public String getLocalCpeType() {
        return localCpeType;
    }

    public void setLocalCpeType(String localCpeType) {
        this.localCpeType = localCpeType;
    }

    /**
     * description
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
        sb.append("class NbiLocalCpeModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
        sb.append("    siteId: ").append(toIndentedString(siteId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    esn: ").append(toIndentedString(esn)).append("\n");
        sb.append("    oldEsn: ").append(toIndentedString(oldEsn)).append("\n");
        sb.append("    controllerId: ").append(toIndentedString(controllerId)).append("\n");
        sb.append("    localCpeType: ").append(toIndentedString(localCpeType)).append("\n");
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
