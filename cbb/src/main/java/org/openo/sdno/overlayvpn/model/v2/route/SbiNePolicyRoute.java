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

package org.openo.sdno.overlayvpn.model.v2.route;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.route.SbiRouteNetModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiNePolicyRoute", propOrder = {"trafficPolicyName", "interfaceName", "direction", "filterAction"})

public class SbiNePolicyRoute extends SbiRouteNetModel {

    @XmlElement(name = "trafficPolicyName")
    @ApiModelProperty(example = "null", required = true, value = "traffic policy name")
    private String trafficPolicyName = null;

    @XmlElement(name = "interfaceName")
    @ApiModelProperty(example = "null", required = true, value = "interface name")
    private String interfaceName = null;

    @XmlElement(name = "direction")
    @ApiModelProperty(example = "null", required = true, value = "the scope is inbound, outbound, all")
    private String direction = null;

    @XmlElement(name = "filterAction")
    @ApiModelProperty(example = "null", required = true, value = "filter action")
    private String filterAction = null;

    /**
     * traffic policy name
     * 
     * @return trafficPolicyName
     **/
    public String getTrafficPolicyName() {
        return trafficPolicyName;
    }

    public void setTrafficPolicyName(String trafficPolicyName) {
        this.trafficPolicyName = trafficPolicyName;
    }

    /**
     * interface name
     * 
     * @return interfaceName
     **/
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * the scope is inbound, outbound, all
     * 
     * @return direction
     **/
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * filter action
     * 
     * @return filterAction
     **/
    public String getFilterAction() {
        return filterAction;
    }

    public void setFilterAction(String filterAction) {
        this.filterAction = filterAction;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiNePolicyRoute {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    trafficPolicyName: ").append(toIndentedString(trafficPolicyName)).append("\n");
        sb.append("    interfaceName: ").append(toIndentedString(interfaceName)).append("\n");
        sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
        sb.append("    filterAction: ").append(toIndentedString(filterAction)).append("\n");
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
