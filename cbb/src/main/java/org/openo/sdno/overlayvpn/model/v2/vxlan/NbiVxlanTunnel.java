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

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.v2.basemodel.NbiBaseServiceModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of SbiNeVxlanInstance Model.<br>
 * 
 * @author
 * @version SDNO 0.5 June 6, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_nbi_vxlantunnel")
public class NbiVxlanTunnel extends NbiBaseServiceModel {

    @AString(require = true, scope = "vxlan,ipv6 over ipv4,ipv4 over ipv6")
    private String type = null;

    @AUuid(require = true)
    private String destNeId = null;

    @AString(require = true, scope = " localcpe,cloudcpe,vpc,dc-r")
    private String destNeRole = null;

    @AString(require = false, min = 1, max = 255)
    private String srcPortName = null;

    @AString(require = false, min = 1, max = 255)
    private String destPortName = null;

    /**
     * VxLAN Network Identifier
     */
    @AInt(require = true, min = 1, max = 16777215)
    private String vni = null;

    @AString(require = false, min = 1, max = 255)
    private String srcTunnelName = null;

    @AString(require = false, min = 1, max = 255)
    private String destTunnelName = null;

    @NONInvField
    @AString(require = false, min = 1, max = 4096)
    private String portVlanList = null;

    @NONInvField
    @JsonIgnore
    private List<PortVlan> portVlans;

    @NONInvField
    @JsonIgnore
    private Ip srcIp;

    @NONInvField
    @JsonIgnore
    private Ip destIp;

    @JsonIgnore
    private String srcDeviceId;

    @JsonIgnore
    private String destDeviceId;

    public String getSrcDeviceId() {
        return srcDeviceId;
    }

    public void setSrcDeviceId(String srcDeviceId) {
        this.srcDeviceId = srcDeviceId;
    }

    public String getDestDeviceId() {
        return destDeviceId;
    }

    public void setDestDeviceId(String destDeviceId) {
        this.destDeviceId = destDeviceId;
    }

    /**
     * vxlan's type(scope:vxlan,ipv6 over ipv4,ipv4 over ipv6,l3-gw-vxlan)
     * 
     * @return type
     **/
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * destination ne id
     * 
     * @return destNeId
     **/
    public String getDestNeId() {
        return destNeId;
    }

    public void setDestNeId(String destNeId) {
        this.destNeId = destNeId;
    }

    /**
     * @return Returns the srcIp.
     */
    public Ip getSrcIp() {
        return srcIp;
    }

    /**
     * @param srcIp The srcIp to set.
     */
    public void setSrcIp(Ip srcIp) {
        this.srcIp = srcIp;
    }

    /**
     * @return Returns the destIp.
     */
    public Ip getDestIp() {
        return destIp;
    }

    /**
     * @param destIp The destIp to set.
     */
    public void setDestIp(Ip destIp) {
        this.destIp = destIp;
    }

    /**
     * destination ne role(scope:localcpe,cloudcpe,vpc,dc-r)
     * 
     * @return destNeRole
     **/
    public String getDestNeRole() {
        return destNeRole;
    }

    public void setDestNeRole(String destNeRole) {
        this.destNeRole = destNeRole;
    }

    /**
     * source port name
     * 
     * @return srcPortName
     **/
    public String getSrcPortName() {
        return srcPortName;
    }

    public void setSrcPortName(String srcPortName) {
        this.srcPortName = srcPortName;
    }

    /**
     * destination port name
     * 
     * @return destPortName
     **/
    public String getDestPortName() {
        return destPortName;
    }

    public void setDestPortName(String destPortName) {
        this.destPortName = destPortName;
    }

    /**
     * vni
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
     * source tunnel name
     * 
     * @return srcTunnelName
     **/
    public String getSrcTunnelName() {
        return srcTunnelName;
    }

    public void setSrcTunnelName(String srcTunnelName) {
        this.srcTunnelName = srcTunnelName;
    }

    /**
     * destination tunnel name
     * 
     * @return destTunnelName
     **/
    public String getDestTunnelName() {
        return destTunnelName;
    }

    public void setDestTunnelName(String destTunnelName) {
        this.destTunnelName = destTunnelName;
    }

    /**
     * vxlan's name on controller
     * 
     * @return portVlanList
     **/
    public String getPortVlanList() {
        return portVlanList;
    }

    public void setPortVlanList(String portVlanList) {
        this.portVlanList = portVlanList;
    }

    /**
     * @return Returns the portVlans.
     */
    public List<PortVlan> getPortVlans() {
        return portVlans;
    }

    /**
     * @param portVlans The portVlans to set.
     */
    public void setPortVlans(List<PortVlan> portVlans) {
        this.portVlans = portVlans;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiVxlanTunnel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    destNeId: ").append(toIndentedString(destNeId)).append("\n");
        sb.append("    destNeRole: ").append(toIndentedString(destNeRole)).append("\n");
        sb.append("    srcPortName: ").append(toIndentedString(srcPortName)).append("\n");
        sb.append("    destPortName: ").append(toIndentedString(destPortName)).append("\n");
        sb.append("    vni: ").append(toIndentedString(vni)).append("\n");
        sb.append("    srcTunnelName: ").append(toIndentedString(srcTunnelName)).append("\n");
        sb.append("    destTunnelName: ").append(toIndentedString(destTunnelName)).append("\n");
        sb.append("    portVlanList: ").append(toIndentedString(portVlanList)).append("\n");
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
