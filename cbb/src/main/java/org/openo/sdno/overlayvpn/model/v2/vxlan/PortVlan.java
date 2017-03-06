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
import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Port vlan model class.<br>
 *
 * @author
 * @version SDNO 0.5 Jan 11, 2017
 */
@MOResType(infoModelName = "overlayvpn_tenant_nbi_portvlan")
public class PortVlan extends UuidModel {

    @AUuid
    private String port;

    @AString
    private String vlan;

    @AUuid
    private String neId;

    @JsonIgnore
    private String portName;

    @JsonIgnore
    private String portNativeId;

    @JsonIgnore
    private String vxlanTunnelId;

    @NONInvField
    @JsonIgnore
    private List<String> vlanList;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public PortVlan() {
        super();
    }

    public String getNeId() {
        return neId;
    }

    public String getPort() {
        return port;
    }

    public String getPortName() {
        return portName;
    }

    public String getPortNativeId() {
        return portNativeId;
    }

    public String getVlan() {
        return vlan;
    }

    public List<String> getVlanList() {
        return vlanList;
    }

    public String getVxlanTunnelId() {
        return vxlanTunnelId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public void setPortNativeId(String portNativeId) {
        this.portNativeId = portNativeId;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }

    public void setVlanList(List<String> vlanList) {
        this.vlanList = vlanList;
    }

    public void setVxlanTunnelId(String vxlanTunnelId) {
        this.vxlanTunnelId = vxlanTunnelId;
    }

}
