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
 * The class of access type in VXLAN service. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public enum VxlanServiceTpAccessType {
    UNTAG(0), DOT1Q(1), QINQ(2), TRANSPORT(3), GLOBAL_VLAN(4), VXLAN(5);

    private int value;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value The access type
     */
    VxlanServiceTpAccessType(int value) {
        this.value = value;
    }

    /**
     * It is used to get access type name. <br>
     * 
     * @return The access type name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "untag";
            case 1:
                return "dot1q";
            case 2:
                return "qinq";
            case 3:
                return "transport";
            case 4:
                return "global-vlan";
            case 5:
                return "vxlan";
            default:
                return "";
        }
    }
}
