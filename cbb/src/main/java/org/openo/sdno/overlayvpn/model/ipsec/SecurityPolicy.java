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

package org.openo.sdno.overlayvpn.model.ipsec;

import org.openo.sdno.overlayvpn.model.ModelBase;
import org.openo.sdno.overlayvpn.model.common.enums.PfsType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.EncryptionAlgorithmType;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * The class of security policy. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public class SecurityPolicy extends ModelBase {

    @AString(require = false, scope = "sha1")
    private String authAlgorithm = "sha1";

    @AString(require = false, scope = "3des,aes-128,aes-256,aes-192")
    private String encryptionAlgorithm = EncryptionAlgorithmType.AES128.getName();

    @AString(scope = "group2,group5,group14")
    private String pfs = PfsType.GROUP5.getName();

    @AInt(require = false, min = 0, max = 3600)
    private String lifeTime = "3600";

    /**
     * It is used to copy the attributes of security policy. <br>
     * 
     * @param securityPolicy The source security policy
     * @since SDNO 0.5
     */
    public void copyBasicData(SecurityPolicy securityPolicy) {
        super.copyBasicData(securityPolicy);
        this.authAlgorithm = securityPolicy.getAuthAlgorithm();
        this.encryptionAlgorithm = securityPolicy.getEncryptionAlgorithm();
        this.pfs = securityPolicy.getPfs();
        this.lifeTime = securityPolicy.getLifeTime();
    }

    public String getAuthAlgorithm() {
        return authAlgorithm;
    }

    public void setAuthAlgorithm(String authAlgorithm) {
        this.authAlgorithm = authAlgorithm;
    }

    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public String getPfs() {
        return pfs;
    }

    public void setPfs(String pfs) {
        this.pfs = pfs;
    }

    public String getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(String lifeTime) {
        this.lifeTime = lifeTime;
    }
}
