/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.model.netmodel.vxlan;

import java.util.List;

import org.openo.sdno.overlayvpn.model.NvString;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of VxLan Tunnel Connection.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public class VxlanTunnelconnection {

    /**
     * Key Id
     */
    @AString(min = 0, max = 36)
    private String uuid;

    /**
     * EvpnNeInstance Id
     */
    @AString(min = 0, max = 255)
    private String evpnNeInstanceId;

    /**
     * L3vpnNeInstanceId
     */
    @AString(min = 0, max = 255)
    private String l3vpnNeInstanceId;

    /**
     * VxLan Service Id
     */
    @AString(min = 0, max = 255)
    private String vxlanServiceId;

    private String vniId;

    private String dscp;

    private String ipsecEnable;

    @AString(min = 0, max = 255)
    private String name;

    @AString(min = 0, max = 255)
    private String description;

    private List<Tp> tunnelTpList;

    /**
     * Peer Gateway IpAddress
     */
    @AString(min = 0, max = 255)
    private String peerAddress;

    @AString(min = 0, max = 255)
    private String peerId;

    /**
     * Peer CIDR
     */
    private List<String> peerCidrs;

    /**
     * Route Mode
     */
    @AString(min = 0, max = 255)
    private String routeMode;

    @AString(min = 68, max = 90000000)
    private int mtu;

    /**
     * Authentication Mode:PSK or Certificate,default value PSK
     */
    @AString(scope = "psk,certs")
    private String authMode;

    /**
     * Initiator mode: default value bi-directional
     */
    @AString(scope = "bi-directional,response-only")
    private String initiator;

    /**
     * Administrative Status of VPN connection
     */
    @AString(scope = "TRUE,FALSE")
    private String adminStateUp;

    /**
     * Status of VPN Connection
     */
    @AString(scope = "ACTIVE, DOWN,BUILD,ERROR, PENDING_CREATE, PENDING_UPDATE,PENDING_DELETE。")
    private String status;

    /**
     * IkePolicy Id
     */
    @AString(min = 0, max = 255)
    private String ikepolicyId;

    /**
     * IPSecPolicy Id
     */
    @AString(min = 0, max = 255)
    private String ipsecPolicyId;

    private String dpd;

    /**
     * Additional Info
     */
    @AString()
    private List<NvString> addtionalInfo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEvpnNeInstanceId() {
        return evpnNeInstanceId;
    }

    public void setEvpnNeInstanceId(String evpnNeInstanceId) {
        this.evpnNeInstanceId = evpnNeInstanceId;
    }

    public String getL3vpnNeInstanceId() {
        return l3vpnNeInstanceId;
    }

    public void setL3vpnNeInstanceId(String l3vpnNeInstanceId) {
        this.l3vpnNeInstanceId = l3vpnNeInstanceId;
    }

    public String getVxlanServiceId() {
        return vxlanServiceId;
    }

    public void setVxlanServiceId(String vxlanServiceId) {
        this.vxlanServiceId = vxlanServiceId;
    }

    public String getVniId() {
        return vniId;
    }

    public void setVniId(String vniId) {
        this.vniId = vniId;
    }

    public String getDscp() {
        return dscp;
    }

    public void setDscp(String dscp) {
        this.dscp = dscp;
    }

    public String getIpsecEnable() {
        return ipsecEnable;
    }

    public void setIpsecEnable(String ipsecEnable) {
        this.ipsecEnable = ipsecEnable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tp> getTunnelTpList() {
        return tunnelTpList;
    }

    public void setTunnelTpList(List<Tp> tunnelTpList) {
        this.tunnelTpList = tunnelTpList;
    }

    public String getPeerAddress() {
        return peerAddress;
    }

    public void setPeerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
    }

    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public List<String> getPeerCidrs() {
        return peerCidrs;
    }

    public void setPeerCidrs(List<String> peerCidrs) {
        this.peerCidrs = peerCidrs;
    }

    public String getRouteMode() {
        return routeMode;
    }

    public void setRouteMode(String routeMode) {
        this.routeMode = routeMode;
    }

    public int getMtu() {
        return mtu;
    }

    public void setMtu(int mtu) {
        this.mtu = mtu;
    }

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getAdminStateUp() {
        return adminStateUp;
    }

    public void setAdminStateUp(String adminStateUp) {
        this.adminStateUp = adminStateUp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIkepolicyId() {
        return ikepolicyId;
    }

    public void setIkepolicyId(String ikepolicyId) {
        this.ikepolicyId = ikepolicyId;
    }

    public String getIpsecPolicyId() {
        return ipsecPolicyId;
    }

    public void setIpsecPolicyId(String ipsecPolicyId) {
        this.ipsecPolicyId = ipsecPolicyId;
    }

    public String getDpd() {
        return dpd;
    }

    public void setDpd(String dpd) {
        this.dpd = dpd;
    }

    public List<NvString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(List<NvString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

}
