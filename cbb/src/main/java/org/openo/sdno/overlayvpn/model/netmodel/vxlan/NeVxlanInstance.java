/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.model.netmodel.vxlan;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.netmodel.BaseNetModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of NeVxlanInstance Model.<br>
 * 
 * @author
 * @version SDNO 0.5 June 6, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_nevxlaninstance")
public class NeVxlanInstance extends BaseNetModel {

    /**
     * VxLAN Network Identifier
     */
    @AInt(require = true, min = 1, max = 16777215)
    private String vni;

    /**
     * ArpProxy Enable or not, default is false
     */
    @AString(scope = "true,false")
    private String arpProxy = "false";

    /**
     * ArpBroadcast Suppress Enable or not,default is false
     */
    @AString(scope = "true,false")
    private String arpBroadcastSuppress = "false";

    /**
     * Device id of VxLAN Instance
     */
    @AUuid(require = true)
    private String neId;

    /**
     * Related VxLan Interfaces
     */
    @NONInvField
    @JsonIgnore
    private List<String> vxlanInterfaces;

    /**
     * Related VxLan Tunnels
     */
    @NONInvField
    @JsonIgnore
    private List<String> vxlanTunnels;

    @NONInvField
    private List<NeVxlanInterface> vxlanInterfaceList;

    @NONInvField
    private List<NeVxlanTunnel> vxlanTunnelList;

    public String getVni() {
        return vni;
    }

    public void setVni(String vni) {
        this.vni = vni;
    }

    public String getArpProxy() {
        return arpProxy;
    }

    public void setArpProxy(String arpProxy) {
        this.arpProxy = arpProxy;
    }

    public String getArpBroadcastSuppress() {
        return arpBroadcastSuppress;
    }

    public void setArpBroadcastSuppress(String arpBroadcastSuppress) {
        this.arpBroadcastSuppress = arpBroadcastSuppress;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public List<String> getVxlanInterfaces() {
        return vxlanInterfaces;
    }

    public void setVxlanInterfaces(List<String> vxlanInterfaces) {
        this.vxlanInterfaces = vxlanInterfaces;
    }

    public List<String> getVxlanTunnels() {
        return vxlanTunnels;
    }

    public void setVxlanTunnels(List<String> vxlanTunnels) {
        this.vxlanTunnels = vxlanTunnels;
    }

    public List<NeVxlanInterface> getVxlanInterfaceList() {
        return vxlanInterfaceList;
    }

    public void setVxlanInterfaceList(List<NeVxlanInterface> vxlanInterfaceList) {
        this.vxlanInterfaceList = vxlanInterfaceList;
    }

    public List<NeVxlanTunnel> getVxlanTunnelList() {
        return vxlanTunnelList;
    }

    public void setVxlanTunnelList(List<NeVxlanTunnel> vxlanTunnelList) {
        this.vxlanTunnelList = vxlanTunnelList;
    }
}
