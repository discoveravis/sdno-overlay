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
@XmlType(name = "NbiBaseServiceModel", propOrder = {"srcNeId", "srcNeRole", "connectionId"})

public class NbiBaseServiceModel extends BaseModel {

    @XmlElement(name = "srcNeId")
    @ApiModelProperty(example = "null", required = true, value = "source NE Id")
    private String srcNeId = null;

    @XmlElement(name = "srcNeRole")
    @ApiModelProperty(example = "null", required = true, value = "source NE role, the scope is localcpe,cloudcpe,vpc,dc-r")
    private String srcNeRole = null;

    @XmlElement(name = "connectionId")
    @ApiModelProperty(example = "null", required = true, value = "connection Id")
    private String connectionId = null;

    /**
     * source NE Id
     * 
     * @return srcNeId
     **/
    public String getSrcNeId() {
        return srcNeId;
    }

    public void setSrcNeId(String srcNeId) {
        this.srcNeId = srcNeId;
    }

    /**
     * source NE role, the scope is localcpe,cloudcpe,vpc,dc-r
     * 
     * @return srcNeRole
     **/
    public String getSrcNeRole() {
        return srcNeRole;
    }

    public void setSrcNeRole(String srcNeRole) {
        this.srcNeRole = srcNeRole;
    }

    /**
     * connection Id
     * 
     * @return connectionId
     **/
    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiBaseServiceModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    srcNeId: ").append(toIndentedString(srcNeId)).append("\n");
        sb.append("    srcNeRole: ").append(toIndentedString(srcNeRole)).append("\n");
        sb.append("    connectionId: ").append(toIndentedString(connectionId)).append("\n");
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
