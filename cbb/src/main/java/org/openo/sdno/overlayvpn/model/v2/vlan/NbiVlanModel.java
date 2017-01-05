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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseServiceModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NbiVlanModel", propOrder = {"siteId", "vlanId", "ports", "portNames", "createtime", "updatetime"})

public class NbiVlanModel extends BaseServiceModel {

    @XmlElement(name = "siteId")
    @ApiModelProperty(example = "null", value = "site id")
    private String siteId = null;

    @XmlElement(name = "vlanId")
    @ApiModelProperty(example = "null", value = "vlan id")
    private Integer vlanId = null;

    @XmlElement(name = "ports")
    @ApiModelProperty(example = "null", value = "ports list")
    private List<String> ports = new ArrayList<String>();

    @XmlElement(name = "portNames")
    @ApiModelProperty(example = "null", value = "port name list")
    private List<String> portNames = new ArrayList<String>();

    @XmlElement(name = "createtime")
    @ApiModelProperty(example = "null", value = "create time")
    private Long createtime = null;

    @XmlElement(name = "updatetime")
    @ApiModelProperty(example = "null", value = "update time")
    private Long updatetime = null;

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
     * vlan id
     * 
     * @return vlanId
     **/
    public Integer getVlanId() {
        return vlanId;
    }

    public void setVlanId(Integer vlanId) {
        this.vlanId = vlanId;
    }

    /**
     * ports list
     * 
     * @return ports
     **/
    public List<String> getPorts() {
        return ports;
    }

    public void setPorts(List<String> ports) {
        this.ports = ports;
    }

    /**
     * port name list
     * 
     * @return portNames
     **/
    public List<String> getPortNames() {
        return portNames;
    }

    public void setPortNames(List<String> portNames) {
        this.portNames = portNames;
    }

    /**
     * create time
     * 
     * @return createtime
     **/
    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    /**
     * update time
     * 
     * @return updatetime
     **/
    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiVlanModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    siteId: ").append(toIndentedString(siteId)).append("\n");
        sb.append("    vlanId: ").append(toIndentedString(vlanId)).append("\n");
        sb.append("    ports: ").append(toIndentedString(ports)).append("\n");
        sb.append("    portNames: ").append(toIndentedString(portNames)).append("\n");
        sb.append("    createtime: ").append(toIndentedString(createtime)).append("\n");
        sb.append("    updatetime: ").append(toIndentedString(updatetime)).append("\n");
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
