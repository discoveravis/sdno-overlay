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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiSecurityPolicy", propOrder = {"pfs", "lifeTime", "externalId", "sbiServiceId"})

@ApiModel(description = "security policy model")
public class SbiSecurityPolicy extends UuidModel {

    @XmlElement(name = "pfs")
    @ApiModelProperty(example = "null", value = "perfect forward secrecy(Group2,Group5,Group14)")
    private String pfs = null;

    @XmlElement(name = "lifeTime")
    @ApiModelProperty(example = "null", value = "")
    private String lifeTime = null;

    @XmlElement(name = "externalId")
    @ApiModelProperty(example = "null", value = "")
    private String externalId = null;

    @XmlElement(name = "sbiServiceId")
    @ApiModelProperty(example = "null", value = "")
    private String sbiServiceId = null;

    /**
     * perfect forward secrecy(Group2,Group5,Group14)
     * 
     * @return pfs
     **/
    public String getPfs() {
        return pfs;
    }

    public void setPfs(String pfs) {
        this.pfs = pfs;
    }

    /**
     * Get lifeTime
     * 
     * @return lifeTime
     **/
    public String getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(String lifeTime) {
        this.lifeTime = lifeTime;
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
     * Get sbiServiceId
     * 
     * @return sbiServiceId
     **/
    public String getSbiServiceId() {
        return sbiServiceId;
    }

    public void setSbiServiceId(String sbiServiceId) {
        this.sbiServiceId = sbiServiceId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiSecurityPolicy {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    pfs: ").append(toIndentedString(pfs)).append("\n");
        sb.append("    lifeTime: ").append(toIndentedString(lifeTime)).append("\n");
        sb.append("    externalId: ").append(toIndentedString(externalId)).append("\n");
        sb.append("    sbiServiceId: ").append(toIndentedString(sbiServiceId)).append("\n");
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
