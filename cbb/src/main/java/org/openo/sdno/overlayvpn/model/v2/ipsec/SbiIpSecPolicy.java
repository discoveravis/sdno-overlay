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
@XmlType(name = "SbiIpSecPolicy", propOrder = {"transformProtocol", "encapsulationMode", "authAlgorithm",
                "encryptionAlgorithm"})

@ApiModel(description = "IpSec Policy")
public class SbiIpSecPolicy extends SbiSecurityPolicy {

    @XmlElement(name = "transformProtocol")
    @ApiModelProperty(example = "null", value = "")
    private String transformProtocol = null;

    @XmlElement(name = "encapsulationMode")
    @ApiModelProperty(example = "null", value = "")
    private String encapsulationMode = null;

    @XmlElement(name = "authAlgorithm")
    @ApiModelProperty(example = "null", required = true, value = "")
    private String authAlgorithm = null;

    @XmlElement(name = "encryptionAlgorithm")
    @ApiModelProperty(example = "null", value = "")
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiIpSecPolicy {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    transformProtocol: ").append(toIndentedString(transformProtocol)).append("\n");
        sb.append("    encapsulationMode: ").append(toIndentedString(encapsulationMode)).append("\n");
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
