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
 * The class of IP version. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public enum IPVersion {
    IPV4(0), IPV6(1);

    private int value;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value The IP version
     */
    IPVersion(int value) {
        this.value = value;
    }

    /**
     * It is used to get IP version name. <br>
     * 
     * @return The IP version name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "ipv4";
            case 1:
                return "ipv6";
            default:
                return "";
        }
    }
}
