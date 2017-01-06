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

package org.openo.sdno.overlayvpn.model.v2.vlan;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiIfVlan", propOrder = {"serviceVlanUuid", "ethInterfaceConfigId", "ifId", "ifName", "defaultVlan",
                "linkType", "vlans"})

public class SbiIfVlan extends UuidModel {

    @XmlElement(name = "serviceVlanUuid")
    @ApiModelProperty(example = "null", required = true, value = "The UUID of VLAN model")
    private String serviceVlanUuid = null;

    @XmlElement(name = "ethInterfaceConfigId")
    @ApiModelProperty(example = "null", value = "The interface UUID allocated by controller")
    private String ethInterfaceConfigId = null;

    @XmlElement(name = "ifId")
    @ApiModelProperty(example = "null", value = "The UUID of interface")
    private String ifId = null;

    @XmlElement(name = "ifName")
    @ApiModelProperty(example = "null", value = "interface name")
    private String ifName = null;

    @XmlElement(name = "defaultVlan")
    @ApiModelProperty(example = "null", value = "VLAN value, the range is 1 to 4094")
    private Integer defaultVlan = null;

    @XmlElement(name = "linkType")
    @ApiModelProperty(example = "null", required = true, value = "the scope is access,trunk,hybird,dot1q,qinq")
    private String linkType = null;

    @XmlElement(name = "vlans")
    @ApiModelProperty(example = "null", value = "vlan")
    private String vlans = null;

    /**
     * The UUID of VLAN model
     * 
     * @return serviceVlanUuid
     **/
    public String getServiceVlanUuid() {
        return serviceVlanUuid;
    }

    public void setServiceVlanUuid(String serviceVlanUuid) {
        this.serviceVlanUuid = serviceVlanUuid;
    }

    /**
     * The interface UUID allocated by controller
     * 
     * @return ethInterfaceConfigId
     **/
    public String getEthInterfaceConfigId() {
        return ethInterfaceConfigId;
    }

    public void setEthInterfaceConfigId(String ethInterfaceConfigId) {
        this.ethInterfaceConfigId = ethInterfaceConfigId;
    }

    /**
     * The UUID of interface
     * 
     * @return ifId
     **/
    public String getIfId() {
        return ifId;
    }

    public void setIfId(String ifId) {
        this.ifId = ifId;
    }

    /**
     * interface name
     * 
     * @return ifName
     **/
    public String getIfName() {
        return ifName;
    }

    public void setIfName(String ifName) {
        this.ifName = ifName;
    }

    /**
     * VLAN value, the range is 1 to 4094
     * 
     * @return defaultVlan
     **/
    public Integer getDefaultVlan() {
        return defaultVlan;
    }

    public void setDefaultVlan(Integer defaultVlan) {
        this.defaultVlan = defaultVlan;
    }

    /**
     * the scope is access,trunk,hybird,dot1q,qinq
     * 
     * @return linkType
     **/
    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    /**
     * vlan
     * 
     * @return vlans
     **/
    public String getVlans() {
        return vlans;
    }

    public void setVlans(String vlans) {
        this.vlans = vlans;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiIfVlan {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    serviceVlanUuid: ").append(toIndentedString(serviceVlanUuid)).append("\n");
        sb.append("    ethInterfaceConfigId: ").append(toIndentedString(ethInterfaceConfigId)).append("\n");
        sb.append("    ifId: ").append(toIndentedString(ifId)).append("\n");
        sb.append("    ifName: ").append(toIndentedString(ifName)).append("\n");
        sb.append("    defaultVlan: ").append(toIndentedString(defaultVlan)).append("\n");
        sb.append("    linkType: ").append(toIndentedString(linkType)).append("\n");
        sb.append("    vlans: ").append(toIndentedString(vlans)).append("\n");
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
