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

package org.openo.sdno.overlayvpn.model.v2.internetgateway;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseServiceModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NbiInternetGatewayModel", propOrder = {"siteId", "protectionType", "enableSnat", "upstreamBandwidth",
                "downstreamBandwidth", "vpnId", "publicIp", "deployPosition", "sourceSubnets", "createtime",
                "updatetime"})

public class NbiInternetGatewayModel extends BaseServiceModel {

    @XmlElement(name = "siteId")
    @ApiModelProperty(example = "null", required = true, value = "site id")
    private String siteId = null;

    @XmlElement(name = "protectionType")
    @ApiModelProperty(example = "null", value = "not support now")
    private String protectionType = null;

    @XmlElement(name = "enableSnat")
    @ApiModelProperty(example = "null", value = "default:true")
    private String enableSnat = null;

    @XmlElement(name = "upstreamBandwidth")
    @ApiModelProperty(example = "null", value = "up stream Bandwidth")
    private String upstreamBandwidth = null;

    @XmlElement(name = "downstreamBandwidth")
    @ApiModelProperty(example = "null", value = "down stream Bandwidth")
    private String downstreamBandwidth = null;

    @XmlElement(name = "vpnId")
    @ApiModelProperty(example = "null", value = "vpn id")
    private String vpnId = null;

    @XmlElement(name = "publicIp")
    @ApiModelProperty(example = "null", value = "public ip")
    private String publicIp = null;

    @XmlElement(name = "deployPosition")
    @ApiModelProperty(example = "null", value = "(scope=localFirst, cloudFirst)")
    private String deployPosition = null;

    @XmlElement(name = "sourceSubnets")
    @ApiModelProperty(example = "null", value = "port name list")
    private List<String> sourceSubnets = new ArrayList<String>();

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
     * not support now
     * 
     * @return protectionType
     **/
    public String getProtectionType() {
        return protectionType;
    }

    public void setProtectionType(String protectionType) {
        this.protectionType = protectionType;
    }

    /**
     * default:true
     * 
     * @return enableSnat
     **/
    public String getEnableSnat() {
        return enableSnat;
    }

    public void setEnableSnat(String enableSnat) {
        this.enableSnat = enableSnat;
    }

    /**
     * up stream Bandwidth
     * 
     * @return upstreamBandwidth
     **/
    public String getUpstreamBandwidth() {
        return upstreamBandwidth;
    }

    public void setUpstreamBandwidth(String upstreamBandwidth) {
        this.upstreamBandwidth = upstreamBandwidth;
    }

    /**
     * down stream Bandwidth
     * 
     * @return downstreamBandwidth
     **/
    public String getDownstreamBandwidth() {
        return downstreamBandwidth;
    }

    public void setDownstreamBandwidth(String downstreamBandwidth) {
        this.downstreamBandwidth = downstreamBandwidth;
    }

    /**
     * vpn id
     * 
     * @return vpnId
     **/
    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(String vpnId) {
        this.vpnId = vpnId;
    }

    /**
     * public ip
     * 
     * @return publicIp
     **/
    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    /**
     * (scope=localFirst, cloudFirst)
     * 
     * @return deployPosition
     **/
    public String getDeployPosition() {
        return deployPosition;
    }

    public void setDeployPosition(String deployPosition) {
        this.deployPosition = deployPosition;
    }

    /**
     * port name list
     * 
     * @return sourceSubnets
     **/
    public List<String> getSourceSubnets() {
        return sourceSubnets;
    }

    public void setSourceSubnets(List<String> sourceSubnets) {
        this.sourceSubnets = sourceSubnets;
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
        sb.append("class NbiInternetGatewayModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    siteId: ").append(toIndentedString(siteId)).append("\n");
        sb.append("    protectionType: ").append(toIndentedString(protectionType)).append("\n");
        sb.append("    enableSnat: ").append(toIndentedString(enableSnat)).append("\n");
        sb.append("    upstreamBandwidth: ").append(toIndentedString(upstreamBandwidth)).append("\n");
        sb.append("    downstreamBandwidth: ").append(toIndentedString(downstreamBandwidth)).append("\n");
        sb.append("    vpnId: ").append(toIndentedString(vpnId)).append("\n");
        sb.append("    publicIp: ").append(toIndentedString(publicIp)).append("\n");
        sb.append("    deployPosition: ").append(toIndentedString(deployPosition)).append("\n");
        sb.append("    sourceSubnets: ").append(toIndentedString(sourceSubnets)).append("\n");
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
