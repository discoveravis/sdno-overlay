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
 * Class of Layer3 TP.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class L3Tp extends Tp {

    /**
     * TpId
     */
    @AString(min = 1, max = 255)
    private String tpId;

    /**
     * SubNet Cidr
     */
    @AString(min = 1, max = 255)
    private String subnetCidr;

    /**
     * Gateway IpAddress
     */
    @AString(min = 1, max = 255)
    private String gatewayIp;

    @AString(min = 1, max = 255)
    private String bgpId;

    @AString(min = 1, max = 255)
    private String ospfId;

    @AString(min = 1, max = 255)
    private String isisId;

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getSubnetCidr() {
        return subnetCidr;
    }

    public void setSubnetCidr(String subnetCidr) {
        this.subnetCidr = subnetCidr;
    }

    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public String getBgpId() {
        return bgpId;
    }

    public void setBgpId(String bgpId) {
        this.bgpId = bgpId;
    }

    public String getOspfId() {
        return ospfId;
    }

    public void setOspfId(String ospfId) {
        this.ospfId = ospfId;
    }

    public String getIsisId() {
        return isisId;
    }

    public void setIsisId(String isisId) {
        this.isisId = isisId;
    }
}
