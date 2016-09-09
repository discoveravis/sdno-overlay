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

package org.openo.sdno.overlayvpn.model.common.enums.ipsec;

/**
 * The class of authorization algorithm type. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public enum AuthAlgorithmType {
    SHA1(0);

    private int value = 0;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value The authorization algorithm type
     */
    AuthAlgorithmType(int value) {
        this.value = value;
    }

    /**
     * It is used to get authorization algorithm name. <br>
     * 
     * @return The authorization algorithm name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "sha1";
            default:
                return "";
        }
    }

    /**
     * It is used to check the authorization algorithm name is valid or not. <br>
     * 
     * @param name The authorization algorithm name
     * @return true if the authorization algorithm name is valid.
     * @since SDNO 0.5
     */
    public static boolean validateName(String name) {
        for(AuthAlgorithmType authAlgorithmType : AuthAlgorithmType.values()) {
            if(authAlgorithmType.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
