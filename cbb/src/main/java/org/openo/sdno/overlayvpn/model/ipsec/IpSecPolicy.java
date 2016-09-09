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

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.common.enums.TransformProtocolType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.EncapsulationModeType;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * The class of Internet Protocol Security policy. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
@MOResType(infoModelName = "overlayvpn_tenant_ipsecpolicy")
public class IpSecPolicy extends SecurityPolicy {

    @AString(scope = "esp,ah,ah-esp")
    private String transformProtocol = TransformProtocolType.ESP.getName();

    @AString(scope = "tunnel,transport")
    private String encapsulationMode = EncapsulationModeType.TUNNEL.getName();

    /**
     * It is used to copy the attributes of Internet Protocol Security policy. <br>
     * 
     * @param ipSecPolicy The source Internet Protocol Security policy
     * @since SDNO 0.5
     */
    public void copyBasicData(IpSecPolicy ipSecPolicy) {
        super.copyBasicData(ipSecPolicy);
        this.transformProtocol = ipSecPolicy.getTransformProtocol();
        this.encapsulationMode = ipSecPolicy.getEncapsulationMode();
    }

    public String getTransformProtocol() {
        return transformProtocol;
    }

    public void setTransformProtocol(String transformProtocol) {
        this.transformProtocol = transformProtocol;
    }

    public String getEncapsulationMode() {
        return encapsulationMode;
    }

    public void setEncapsulationMode(String encapsulationMode) {
        this.encapsulationMode = encapsulationMode;
    }
}
