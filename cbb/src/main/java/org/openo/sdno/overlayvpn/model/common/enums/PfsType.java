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
 * The class of pfs type. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public enum PfsType {
    GROUP2(0), GROUP5(1), GROUP14(2);

    private int value = 1;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value The pfs value
     */
    PfsType(int value) {
        this.value = value;
    }

    /**
     * It is used to get pfs type name. <br>
     * 
     * @return The pfs type name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "group2";
            case 1:
                return "group5";
            case 2:
                return "group14";
            default:
                return "";
        }
    }

    /**
     * It is used to check the pfs type name is valid or not. <br>
     * 
     * @param name The pfs type name
     * @return true if the pfs type name is valid.
     * @since SDNO 0.5
     */
    public static boolean validateName(String name) {
        for(PfsType pfsType : PfsType.values()) {
            if(pfsType.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
