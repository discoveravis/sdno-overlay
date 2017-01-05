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

import org.openo.sdno.overlayvpn.model.v2.basemodel.SbiBaseNetModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiNeVxlanInstance", propOrder = {"nbiVxlanTunnelId", "vni", "arpProxy", "arpBroadcastSuppress",
                "keepAlive", "vxlanInterfaces", "vxlanTunnels", "vxlanInterfaceList", "vxlanTunnelList",
                "vxlanGatewayList"})

public class SbiNeVxlanInstance extends SbiBaseNetModel {

    @XmlElement(name = "nbiVxlanTunnelId")
    @ApiModelProperty(example = "null", required = true, value = "associated nbi vxlan ID")
    private String nbiVxlanTunnelId = null;

    @XmlElement(name = "vni")
    @ApiModelProperty(example = "null", required = true, value = "net ID which vxlan belongs to")
    private String vni = null;

    @XmlElement(name = "arpProxy")
    @ApiModelProperty(example = "null", value = "whether to enable arpProxy(default:\"false\")")
    private String arpProxy = null;

    @XmlElement(name = "arpBroadcastSuppress")
    @ApiModelProperty(example = "null", value = "arp broadcast suppress enable(default:\"false\")")
    private String arpBroadcastSuppress = null;

    @XmlElement(name = "keepAlive")
    @ApiModelProperty(example = "null", value = "")
    private String keepAlive = null;

    @XmlElement(name = "vxlanInterfaces")
    @ApiModelProperty(example = "null", value = "")
    private List<String> vxlanInterfaces = new ArrayList<String>();

    @XmlElement(name = "vxlanTunnels")
    @ApiModelProperty(example = "null", value = "")
    private List<String> vxlanTunnels = new ArrayList<String>();

    @XmlElement(name = "vxlanInterfaceList")
    @ApiModelProperty(example = "null", value = "")
    private List<SbiNeVxlanInterface> vxlanInterfaceList = new ArrayList<SbiNeVxlanInterface>();

    @XmlElement(name = "vxlanTunnelList")
    @ApiModelProperty(example = "null", value = "")
    private List<SbiNeVxlanTunnel> vxlanTunnelList = new ArrayList<SbiNeVxlanTunnel>();

    @XmlElement(name = "vxlanGatewayList")
    @ApiModelProperty(example = "null", value = "")
    private List<SbiNeVxlanGateway> vxlanGatewayList = new ArrayList<SbiNeVxlanGateway>();

    /**
     * associated nbi vxlan ID
     * 
     * @return nbiVxlanTunnelId
     **/
    public String getNbiVxlanTunnelId() {
        return nbiVxlanTunnelId;
    }

    public void setNbiVxlanTunnelId(String nbiVxlanTunnelId) {
        this.nbiVxlanTunnelId = nbiVxlanTunnelId;
    }

    /**
     * net ID which vxlan belongs to
     * 
     * @return vni
     **/
    public String getVni() {
        return vni;
    }

    public void setVni(String vni) {
        this.vni = vni;
    }

    /**
     * whether to enable arpProxy(default:\"false\")
     * 
     * @return arpProxy
     **/
    public String getArpProxy() {
        return arpProxy;
    }

    public void setArpProxy(String arpProxy) {
        this.arpProxy = arpProxy;
    }

    /**
     * arp broadcast suppress enable(default:\"false\")
     * 
     * @return arpBroadcastSuppress
     **/
    public String getArpBroadcastSuppress() {
        return arpBroadcastSuppress;
    }

    public void setArpBroadcastSuppress(String arpBroadcastSuppress) {
        this.arpBroadcastSuppress = arpBroadcastSuppress;
    }

    /**
     * Get keepAlive
     * 
     * @return keepAlive
     **/
    public String getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(String keepAlive) {
        this.keepAlive = keepAlive;
    }

    /**
     * Get vxlanInterfaces
     * 
     * @return vxlanInterfaces
     **/
    public List<String> getVxlanInterfaces() {
        return vxlanInterfaces;
    }

    public void setVxlanInterfaces(List<String> vxlanInterfaces) {
        this.vxlanInterfaces = vxlanInterfaces;
    }

    /**
     * Get vxlanTunnels
     * 
     * @return vxlanTunnels
     **/
    public List<String> getVxlanTunnels() {
        return vxlanTunnels;
    }

    public void setVxlanTunnels(List<String> vxlanTunnels) {
        this.vxlanTunnels = vxlanTunnels;
    }

    /**
     * Get vxlanInterfaceList
     * 
     * @return vxlanInterfaceList
     **/
    public List<SbiNeVxlanInterface> getVxlanInterfaceList() {
        return vxlanInterfaceList;
    }

    public void setVxlanInterfaceList(List<SbiNeVxlanInterface> vxlanInterfaceList) {
        this.vxlanInterfaceList = vxlanInterfaceList;
    }

    /**
     * Get vxlanTunnelList
     * 
     * @return vxlanTunnelList
     **/
    public List<SbiNeVxlanTunnel> getVxlanTunnelList() {
        return vxlanTunnelList;
    }

    public void setVxlanTunnelList(List<SbiNeVxlanTunnel> vxlanTunnelList) {
        this.vxlanTunnelList = vxlanTunnelList;
    }

    /**
     * Get vxlanGatewayList
     * 
     * @return vxlanGatewayList
     **/
    public List<SbiNeVxlanGateway> getVxlanGatewayList() {
        return vxlanGatewayList;
    }

    public void setVxlanGatewayList(List<SbiNeVxlanGateway> vxlanGatewayList) {
        this.vxlanGatewayList = vxlanGatewayList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiNeVxlanInstance {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    nbiVxlanTunnelId: ").append(toIndentedString(nbiVxlanTunnelId)).append("\n");
        sb.append("    vni: ").append(toIndentedString(vni)).append("\n");
        sb.append("    arpProxy: ").append(toIndentedString(arpProxy)).append("\n");
        sb.append("    arpBroadcastSuppress: ").append(toIndentedString(arpBroadcastSuppress)).append("\n");
        sb.append("    keepAlive: ").append(toIndentedString(keepAlive)).append("\n");
        sb.append("    vxlanInterfaces: ").append(toIndentedString(vxlanInterfaces)).append("\n");
        sb.append("    vxlanTunnels: ").append(toIndentedString(vxlanTunnels)).append("\n");
        sb.append("    vxlanInterfaceList: ").append(toIndentedString(vxlanInterfaceList)).append("\n");
        sb.append("    vxlanTunnelList: ").append(toIndentedString(vxlanTunnelList)).append("\n");
        sb.append("    vxlanGatewayList: ").append(toIndentedString(vxlanGatewayList)).append("\n");
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
