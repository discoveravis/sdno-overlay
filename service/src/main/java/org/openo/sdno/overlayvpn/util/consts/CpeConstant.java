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

package org.openo.sdno.overlayvpn.util.consts;

/**
 * Definition of CPE Constants.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public final class CpeConstant {

    /**
     * network use type: vm
     */
    public static final String LAN_NETWORK_USE_TYPE_VM = "vm";

    /**
     * network use type: terminal
     */
    public static final String LAN_NETWORK_USE_TYPE_TERMINAL = "terminal";

    /**
     * network default access VLAN id
     */
    public static final int LAN_NETWORK_ACCESS_VLAN_ID_DEFAULT = 1;

    /**
     * network dhcp enable: true
     */
    public static final boolean LAN_NETWORK_DHCP_ENABLE_DEFAULT = true;

    /**
     * network change mode:false
     */
    public static final boolean LAN_NETWORK_CHANGE_MODE_DEFAULT = false;

    private CpeConstant() {
        // empty constructor

    }
}
