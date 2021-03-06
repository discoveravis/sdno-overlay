/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.model.v2.ipsec;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

@MOResType(infoModelName = "ipsec_sbi_ipsecpolicy")
public class SbiIpSecPolicy extends SbiSecurityPolicy {

    @AString(scope = "esp,ah,ah-esp")
    private String transformProtocol = null;

    @AString(scope = "tunnel, transport")
    private String encapsulationMode = null;

    @AString(require = true, scope = "md5,sha1,sha2-256,sha2-384,sha2-512,sm3")
    private String authAlgorithm = null;

    @AString(require = false, scope = "3des,des,aes-128,aes-256,aes-192,sm1")
    private String encryptionAlgorithm = null;

    /**
     * Get transformProtocol
     * 
     * @return transformProtocol
     **/
    public String getTransformProtocol() {
        return transformProtocol;
    }

    public void setTransformProtocol(String transformProtocol) {
        this.transformProtocol = transformProtocol;
    }

    /**
     * Get encapsulationMode
     * 
     * @return encapsulationMode
     **/
    public String getEncapsulationMode() {
        return encapsulationMode;
    }

    public void setEncapsulationMode(String encapsulationMode) {
        this.encapsulationMode = encapsulationMode;
    }

    /**
     * Get authAlgorithm
     * 
     * @return authAlgorithm
     **/
    public String getAuthAlgorithm() {
        return authAlgorithm;
    }

    public void setAuthAlgorithm(String authAlgorithm) {
        this.authAlgorithm = authAlgorithm;
    }

    /**
     * Get encryptionAlgorithm
     * 
     * @return encryptionAlgorithm
     **/
    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

}
