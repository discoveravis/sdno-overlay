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

import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of VxlanTunnelTp.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class VxlanTunnelTp extends Tp {

    /**
     * VNI Id
     */
    @AString(min = 0, max = 255)
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
    @AString(min = 0, max = 255)
    private String rtValue;

    /**
     * Specifies the vp"
     * + "n target type, export-extcommunity: specifies the extended commu"
     * + "nity attributes carried in routing information to be sent. impor"
     * + "t-extcommunity: receives routing information carrying specified "
     * + "extended community attributes.";
     */
    @AString(min = 0, max = 255)
    private String rtType;

    /**
     * WanSubInf Topology Id
     */
    @AString(min = 0, max = 255)
    private String wanTpId;

    /**
     * IpSec Topology Id
     */
    @AString(min = 0, max = 255)
    private String ipsecTpId;

    public void setVniId(String vniId) {
        this.vniId = vniId;
    }

    public String getVniId() {
        return vniId;
    }

    public void setRtValue(String rtValue) {
        this.rtValue = rtValue;
    }

    public String getRtValue() {
        return rtValue;
    }

    public void setRtType(String rtType) {
        this.rtType = rtType;
    }

    public String getRtType() {
        return rtType;
    }

    public String getWanTpId() {
        return wanTpId;
    }

    public void setWanTpId(String wanTpId) {
        this.wanTpId = wanTpId;
    }

    public String getIpsecTpId() {
        return ipsecTpId;
    }

    public void setIpsecTpId(String ipsecTpId) {
        this.ipsecTpId = ipsecTpId;
    }

}
