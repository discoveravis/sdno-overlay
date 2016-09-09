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

package org.openo.sdno.overlayvpn.util.check;

import org.apache.commons.lang3.StringUtils;

/**
 * Util class of String.<br>
 * <p>
 * This class provide some functions to operate String Object.
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public final class StringUtil {

    private StringUtil() {
    }

    /**
     * Check whether Sting is Digit.<br>
     * 
     * @param str String need to check
     * @return true if String is Digit,false otherwise
     * @since SDNO 0.5
     */
    public static boolean isDigit(String str) {

        if(StringUtils.isEmpty(str)) {
            return false;
        }

        for(char ch : str.toCharArray()) {
            if(!Character.isDigit(ch)) {
                return false;
            }
        }

        return true;
    }
}
