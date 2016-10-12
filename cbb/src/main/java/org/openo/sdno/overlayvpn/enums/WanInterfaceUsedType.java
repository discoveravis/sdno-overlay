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

package org.openo.sdno.overlayvpn.enums;

import org.apache.commons.lang.StringUtils;

/**
 * Class of WanSubInf Used Type.<br>
 * 
 * @author
 * @version SDNO 0.5 June 7, 2016
 */
public enum WanInterfaceUsedType {
    INTERNET(0), GRE(1), VXLAN(2), MANAGE(3), IPSEC(4), ALL(5);

    private int value;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value used type
     */
    WanInterfaceUsedType(int value) {
        this.value = value;
    }

    /**
     * Get name of WanInterfaceUsedType.<br>
     * 
     * @return name of WanInterfaceUsedType
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "wanInterfaceForInternet";
            case 1:
                return "wanInterfaceForGre";
            case 2:
                return "wanInterfaceForVxlan";
            case 3:
                return "wanInterfaceForManage";
            case 4:
                return "wanInterfaceForIPSec";
            case 5:
                return "wanInterfaceForAll";
            default:
                return "";
        }
    }

    /**
     * Check Whether value is valid.<br>
     * 
     * @param value name of used type
     * @return true if value is valid,false otherwise
     * @since SDNO 0.5
     */
    public static boolean isValid(String value) {
        if(StringUtils.isEmpty(value)) {
            return false;
        }

        if(WanInterfaceUsedType.INTERNET.getName().equals(value) || WanInterfaceUsedType.GRE.getName().equals(value)
                || WanInterfaceUsedType.VXLAN.getName().equals(value)
                || WanInterfaceUsedType.MANAGE.getName().equals(value)
                || WanInterfaceUsedType.IPSEC.getName().equals(value)
                || WanInterfaceUsedType.ALL.getName().equals(value)) {
            return true;
        }

        return false;
    }

}
