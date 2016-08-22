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

package org.openo.sdno.overlayvpn.model.common.enums;

/**
 * The class of tunnel type. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public enum MplsTunnelType {
    NONE(0), LDP_LSP(1), MPLS_TE(2), VXLAN(3);

    private int value;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param value The tunnel type
     */
    MplsTunnelType(int value) {
        this.value = value;
    }

    /**
     * It is used to get the tunnel type name. <br/>
     * 
     * @return The tunnel type name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "none";
            case 1:
                return "ldp_lsp";
            case 2:
                return "mpls_te";
            case 3:
                return "VxLAN";
            default:
                return "";
        }
    }

}
