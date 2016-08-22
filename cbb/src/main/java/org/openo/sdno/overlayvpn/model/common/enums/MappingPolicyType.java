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

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * The class of mapping policy. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public enum MappingPolicyType {
    GRE(0), IPSEC(1), VXLAN(2), GRE_OVER_IPSEC(3), VXLAN_OVER_IPSEC(4);

    private int value = 0;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param value
     *            The policy type
     */
    MappingPolicyType(int value) {
        this.value = value;
    }

    /**
     * It is used to get policy type name. <br/>
     * 
     * @return The policy type name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "gre";
            case 1:
                return "ipsec";
            case 2:
                return "vxlan";
            case 3:
                return "gre_over_ipsec";
            case 4:
                return "vxlan_over_ipsec";
            default:
                return "";
        }
    }

    /**
     * It is used to check the policy type name is valid or not. <br/>
     * 
     * @param name
     *            The policy type name
     * @return true if the policy type name is valid.
     * @since SDNO 0.5
     */
    public static boolean validateName(String name) {
        for(MappingPolicyType mappingPolicyType : MappingPolicyType.values()) {
            if(mappingPolicyType.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * It is used to check whether the policy type name is IPSEC. <br/>
     * 
     * @param name
     *            The policy type name
     * @return true if the policy type name is IPSEC.
     * @since SDNO 0.5
     */
    @JsonIgnore
    public static boolean isIpsec(String name) {
        return IPSEC.getName().equals(name);
    }

    /**
     * It is used to check whether the policy type name is VXLAN or
     * VXLAN_OVER_IPSEC. <br/>
     * 
     * @param name
     *            The policy type name
     * @return true if the policy type name is VXLAN or VXLAN_OVER_IPSEC.
     * @since SDNO 0.5
     */
    @JsonIgnore
    public static boolean isVxlanRelated(String name) {
        return VXLAN.getName().equals(name);
    }
}
