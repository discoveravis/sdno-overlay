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

import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of Layer2 TP.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class L2Tp extends Tp {

    /**
     * TpId
     */
    @AString(min = 1, max = 255)
    private String tpId;

    /**
     * Access Type: {untag|dot1q|qinq|transport|global-vlan|vxlan}
     */
    @AString(scope = "untag,dot1q,qinq,transport,global-vlan,vxlan")
    private String accessType;

    /**
     * dot1q
     */
    @AString(min = 1, max = 4094)
    private String vlanId;

    @AString(min = 1, max = 34)
    private String vlans;

    /**
     * qinq
     */
    @AString(min = 1, max = 4094)
    private String cvlanBegin;

    /**
     * qinq
     */
    @AString(min = 1, max = 4094)
    private String cvlanEnd;

    /**
     * qinq
     */
    @AString(min = 1, max = 4094)
    private String svlanBegin;

    /**
     * qinq
     */
    @AString(min = 1, max = 4094)
    private String svlanEnd;

    /**
     * "global vlan access into evpn. "
     */
    @AString(min = 1, max = 4094)
    private String globalVlanId;

    @AString(min = 1, max = 34)
    private String globalVlans;

    /**
     * "vxlan tunnel access into the local evpn. "
     */
    @AString(min = 1, max = 16777215)
    private String vniId;

    /**
     * Vpn-target: adds"
     * + " VPN target extended community attribute to the export or import"
     * + " VPN target extended community list. The vpn-target can be expre"
     * + "ssed in either of the following formats:(1)16-bit AS number:32-b"
     * + "it user-defined numberFor example, 1:3. The AS number ranges fro"
     * + "m 0 to 65535. The user-defined number ranges from 0 to 429496729"
     * + "5. The AS number and the user-defined number cannot be 0s at the"
     * + " same time. That is, a VPN target cannot be 0:0.(2)32-bit IP add"
     * + "ress:16-bit user-defined numberFor example, 192.168.122.15:1. Th"
     * + "e IP address ranges from 0.0.0.0 to 255.255.255.255. The user-de"
     * + "fined number ranges from 0 to 65535
     */
    @AString(min = 1, max = 34)
    private String rtValue;

    /**
     * Specifies the vp"
     * + "n target type, export-extcommunity: specifies the extended commu"
     * + "nity attributes carried in routing information to be sent. impor"
     * + "t-extcommunity: receives routing information carrying specified "
     * + "extended community attributes.";
     */
    @AString(min = 1, max = 34)
    private String rtType;

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    @Override
    public String getAccessType() {
        return accessType;
    }

    @Override
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getVlanId() {
        return vlanId;
    }

    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    public String getVlans() {
        return vlans;
    }

    public void setVlans(String vlans) {
        this.vlans = vlans;
    }

    public String getCvlanBegin() {
        return cvlanBegin;
    }

    public void setCvlanBegin(String cvlanBegin) {
        this.cvlanBegin = cvlanBegin;
    }

    public String getCvlanEnd() {
        return cvlanEnd;
    }

    public void setCvlanEnd(String cvlanEnd) {
        this.cvlanEnd = cvlanEnd;
    }

    public String getSvlanBegin() {
        return svlanBegin;
    }

    public void setSvlanBegin(String svlanBegin) {
        this.svlanBegin = svlanBegin;
    }

    public String getSvlanEnd() {
        return svlanEnd;
    }

    public void setSvlanEnd(String svlanEnd) {
        this.svlanEnd = svlanEnd;
    }

    public String getGlobalVlanId() {
        return globalVlanId;
    }

    public void setGlobalVlanId(String globalVlanId) {
        this.globalVlanId = globalVlanId;
    }

    public String getGlobalVlans() {
        return globalVlans;
    }

    public void setGlobalVlans(String globalVlans) {
        this.globalVlans = globalVlans;
    }

    public String getVniId() {
        return vniId;
    }

    public void setVniId(String vniId) {
        this.vniId = vniId;
    }

    public String getRtValue() {
        return rtValue;
    }

    public void setRtValue(String rtValue) {
        this.rtValue = rtValue;
    }

    public String getRtType() {
        return rtType;
    }

    public void setRtType(String rtType) {
        this.rtType = rtType;
    }
}
