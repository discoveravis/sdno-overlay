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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SbiIkePolicy", propOrder = {"ikeVersion", "psk", "authAlgorithm", "encryptionAlgorithm"})

@ApiModel(description = "Internet Key Exchange Policy")
public class SbiIkePolicy extends SbiSecurityPolicy {

    @XmlElement(name = "ikeVersion")
    @ApiModelProperty(example = "null", value = "Ike version(v1,v2)")
    private String ikeVersion = null;

    @XmlElement(name = "psk")
    @ApiModelProperty(example = "null", required = true, value = "Pre-shared-key")
    private String psk = null;

    @XmlElement(name = "authAlgorithm")
    @ApiModelProperty(example = "null", required = true, value = "auth hash algorithm (md5,sha1,sha2-256,sh2-384,sh2-512,sm3)")
    private String authAlgorithm = null;

    @XmlElement(name = "encryptionAlgorithm")
    @ApiModelProperty(example = "null", required = true, value = "encryption algorithm (3des,des,aes-128,aes-256,aes-192,sm1)")
    private String encryptionAlgorithm = null;

    /**
     * Ike version(v1,v2)
     * 
     * @return ikeVersion
     **/
    public String getIkeVersion() {
        return ikeVersion;
    }

    public void setIkeVersion(String ikeVersion) {
        this.ikeVersion = ikeVersion;
    }

    /**
     * Pre-shared-key
     * 
     * @return psk
     **/
    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }

    /**
     * auth hash algorithm (md5,sha1,sha2-256,sh2-384,sh2-512,sm3)
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
     * encryption algorithm (3des,des,aes-128,aes-256,aes-192,sm1)
     * 
     * @return encryptionAlgorithm
     **/
    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiIkePolicy {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    ikeVersion: ").append(toIndentedString(ikeVersion)).append("\n");
        sb.append("    psk: ").append(toIndentedString(psk)).append("\n");
        sb.append("    authAlgorithm: ").append(toIndentedString(authAlgorithm)).append("\n");
        sb.append("    encryptionAlgorithm: ").append(toIndentedString(encryptionAlgorithm)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(Object o) {
        if(o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
