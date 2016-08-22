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

package org.openo.sdno.overlayvpn.model.common.enums.ipsec;

/**
 * The class of encryption algorithm type. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public enum EncryptionAlgorithmType {
    DES(0), AES128(1), AES256(2), AES192(3);

    private int value = 1;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param value The encryption algorithm type
     */
    EncryptionAlgorithmType(int value) {
        this.value = value;
    }

    /**
     * It is used to get encryption algorithm type name. <br/>
     * 
     * @return The encryption algorithm type name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "3des";
            case 1:
                return "aes-128";
            case 2:
                return "aes-256";
            case 3:
                return "aes-192";
            default:
                return "";
        }
    }

    /**
     * It is used to check the encryption algorithm type is valid or not. <br/>
     * 
     * @param name The encryption algorithm name
     * @return true if the encryption algorithm name is valid.
     * @since SDNO 0.5
     */
    public static boolean validateName(String name) {
        for(EncryptionAlgorithmType encryptionAlgorithmType : EncryptionAlgorithmType.values()) {
            if(encryptionAlgorithmType.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
