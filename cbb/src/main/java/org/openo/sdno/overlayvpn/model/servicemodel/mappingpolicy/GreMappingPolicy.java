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

package org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.verify.annotation.ALong;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of GRE MappingPloicy.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_gremappingpolicy")
public class GreMappingPolicy extends BaseMappingPolicy {

    /**
     * Key of identification
     */
    @ALong(min = 0, max = 4294967295L)
    private String greTunnelKey;

    /**
     * Authentication Mode, only support "PSK"
     */
    @AString(scope = "PSK,certificate")
    private String authMode;

    /**
     * PreShare key
     */
    @AString(require = true, min = 1, max = 127)
    private String psk;

    /**
     * Copy Basic Data from other GreMappingPolicy Object.<br/>
     * 
     * @param mappingPolicy mappingPolicy copy form
     * @since SDNO 0.5
     */
    public void copyBasicData(GreMappingPolicy mappingPolicy) {
        super.copyBasicData(mappingPolicy);
        this.greTunnelKey = mappingPolicy.getGreTunnelKey();
        this.authMode = mappingPolicy.getAuthMode();
        this.psk = mappingPolicy.getPsk();
        this.ikePolicyId = mappingPolicy.getIkePolicyId();
        this.ipsecPolicyId = mappingPolicy.getIpsecPolicyId();
    }

    public String getGreTunnelKey() {
        return greTunnelKey;
    }

    public void setGreTunnelKey(String greTunnelKey) {
        this.greTunnelKey = greTunnelKey;
    }

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }
}
