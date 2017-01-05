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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NbiActionModel", propOrder = {"deploy", "undeploy"})

@XmlRootElement(name = "NbiActionModel")
@ApiModel(description = "action model")
public class NbiActionModel {

    @XmlElement(name = "deploy")
    @ApiModelProperty(example = "null", value = "list of UUIDs")
    private List<String> deploy = new ArrayList<String>();

    @XmlElement(name = "undeploy")
    @ApiModelProperty(example = "null", value = "list of UUIDs")
    private List<String> undeploy = new ArrayList<String>();

    /**
     * list of UUIDs
     * 
     * @return deploy
     **/
    public List<String> getDeploy() {
        return deploy;
    }

    public void setDeploy(List<String> deploy) {
        this.deploy = deploy;
    }

    /**
     * list of UUIDs
     * 
     * @return undeploy
     **/
    public List<String> getUndeploy() {
        return undeploy;
    }

    public void setUndeploy(List<String> undeploy) {
        this.undeploy = undeploy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiActionModel {\n");

        sb.append("    deploy: ").append(toIndentedString(deploy)).append("\n");
        sb.append("    undeploy: ").append(toIndentedString(undeploy)).append("\n");
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
