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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.ResultResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiNeVxlanInstanceResultResponse", propOrder = {"data", "successed", "fail"})

@ApiModel(description = "SbiNeVxlanInstance result model")
public class SbiNeVxlanInstanceResultResponse extends ResultResponse {

    @XmlElement(name = "data")
    @ApiModelProperty(example = "null", value = "")
    private SbiNeVxlanInstance data = null;

    @XmlElement(name = "successed")
    @ApiModelProperty(example = "null", value = "the successed list.")
    private List<SbiNeVxlanInstance> successed = new ArrayList<SbiNeVxlanInstance>();

    @XmlElement(name = "fail")
    @ApiModelProperty(example = "null", value = "the failed list.")
    private List<FailDataSbiNeVxlanInstance> fail = new ArrayList<FailDataSbiNeVxlanInstance>();

    /**
     * Get data
     * 
     * @return data
     **/
    public SbiNeVxlanInstance getData() {
        return data;
    }

    public void setData(SbiNeVxlanInstance data) {
        this.data = data;
    }

    /**
     * the successed list.
     * 
     * @return successed
     **/
    public List<SbiNeVxlanInstance> getSuccessed() {
        return successed;
    }

    public void setSuccessed(List<SbiNeVxlanInstance> successed) {
        this.successed = successed;
    }

    /**
     * the failed list.
     * 
     * @return fail
     **/
    public List<FailDataSbiNeVxlanInstance> getFail() {
        return fail;
    }

    public void setFail(List<FailDataSbiNeVxlanInstance> fail) {
        this.fail = fail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiNeVxlanInstanceResultResponse {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    data: ").append(toIndentedString(data)).append("\n");
        sb.append("    successed: ").append(toIndentedString(successed)).append("\n");
        sb.append("    fail: ").append(toIndentedString(fail)).append("\n");
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
