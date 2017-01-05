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
@XmlType(name = "FailDataSbiNeVxlanTunnelStatus", propOrder = {"errcode", "errmsg", "data"})

@XmlRootElement(name = "FailDataSbiNeVxlanTunnelStatus")
@ApiModel(description = "Fail data model")
public class FailDataSbiNeVxlanTunnelStatus {

    @XmlElement(name = "errcode")
    @ApiModelProperty(example = "null", value = "result error code")
    private String errcode = null;

    @XmlElement(name = "errmsg")
    @ApiModelProperty(example = "null", value = "error message")
    private String errmsg = null;

    @XmlElement(name = "data")
    @ApiModelProperty(example = "null", value = "")
    private SbiNeVxlanTunnelStatus data = null;

    /**
     * result error code
     * 
     * @return errcode
     **/
    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    /**
     * error message
     * 
     * @return errmsg
     **/
    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    /**
     * Get data
     * 
     * @return data
     **/
    public SbiNeVxlanTunnelStatus getData() {
        return data;
    }

    public void setData(SbiNeVxlanTunnelStatus data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FailDataSbiNeVxlanTunnelStatus {\n");

        sb.append("    errcode: ").append(toIndentedString(errcode)).append("\n");
        sb.append("    errmsg: ").append(toIndentedString(errmsg)).append("\n");
        sb.append("    data: ").append(toIndentedString(data)).append("\n");
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
