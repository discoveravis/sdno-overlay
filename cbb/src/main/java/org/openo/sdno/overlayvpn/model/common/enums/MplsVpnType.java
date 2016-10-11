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

package org.openo.sdno.overlayvpn.model.common.enums;

/**
 * The class of VPN type. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public enum MplsVpnType {
    NONE(0), BGP_MPLS_VPN(1), VLL(2);

    private int value;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value The VPN type
     */
    MplsVpnType(int value) {
        this.value = value;
    }

    /**
     * It is used to get VPN type name. <br>
     * 
     * @return The VPN type name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "none";
            case 1:
                return "bgp_mpls_vpn";
            case 2:
                return "vll";
            default:
                return "";
        }
    }

}
