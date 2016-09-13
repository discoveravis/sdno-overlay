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

package org.openo.sdno.overlayvpn.model.tunnel;

import org.openo.sdno.overlayvpn.verify.annotation.AIp;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of Tunnel Object.<br>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public class Tunnel {

    /**
     * Id of OverlayVpn instance
     */
    @AUuid(require = true)
    private String serviceId;

    /**
     * type of tunnel
     */
    @AString(require = true, scope = "GRE,VXLAN")
    private String tunnelType;

    /**
     * id of GRE tunnel
     */
    @AUuid(require = true)
    private String tunnelId;

    /**
     * UUID of tunnel source NetworkElement
     */
    @AUuid(require = true)
    private String srcNeId;

    /**
     * UUID of source EndPointGroup
     */
    @AUuid(require = false)
    private String srcEpgId;

    /**
     * IpAddress of tunnel source NetworkElement
     */
    @AIp(require = true)
    private String srcIp;

    /**
     * UUID of tunnel destination NetworkElement
     */
    @AUuid(require = true)
    private String dstNeId;

    /**
     * UUID of destination EndPointGroup
     */
    @AUuid(require = false)
    private String dstEpgId;

    /**
     * IpAddress of tunnel destination NetworkElement
     */
    private String dstIp;

    /**
     * id of VxLan
     */
    private String vni;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public Tunnel() {
        super();
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTunnelType() {
        return tunnelType;
    }

    public void setTunnelType(String tunnelType) {
        this.tunnelType = tunnelType;
    }

    public String getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(String tunnelId) {
        this.tunnelId = tunnelId;
    }

    public String getSrcNeId() {
        return srcNeId;
    }

    public void setSrcNeId(String srcNeId) {
        this.srcNeId = srcNeId;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getSrcEpgId() {
        return srcEpgId;
    }

    public void setSrcEpgId(String srcEpgId) {
        this.srcEpgId = srcEpgId;
    }

    public String getDstEpgId() {
        return dstEpgId;
    }

    public void setDstEpgId(String dstEpgId) {
        this.dstEpgId = dstEpgId;
    }

    public String getDstNeId() {
        return dstNeId;
    }

    public void setDstNeId(String dstNeId) {
        this.dstNeId = dstNeId;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public String getVni() {
        return vni;
    }

    public void setVni(String vni) {
        this.vni = vni;
    }
}
