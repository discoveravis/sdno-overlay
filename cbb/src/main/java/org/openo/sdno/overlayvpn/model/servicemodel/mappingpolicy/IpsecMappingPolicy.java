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
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of IpSec MappingPloicy.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_ipsecmappingpolicy")
public class IpsecMappingPolicy extends BaseMappingPolicy {

    /**
     * Route mode,only support "static" mode
     */
    @AString(scope = "static")
    private String routeMode;

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
     * Copy Basic attribute of other Object to this Object.<br/>
     * 
     * @param mappingPolicy other IpsecMappingPolicy object
     * @since SDNO 0.5
     */
    public void copyBasicData(IpsecMappingPolicy mappingPolicy) {
        super.copyBasicData(mappingPolicy);
        this.routeMode = mappingPolicy.getRouteMode();
        this.authMode = mappingPolicy.getAuthMode();
        this.psk = mappingPolicy.getPsk();
        this.ikePolicyId = mappingPolicy.getIkePolicyId();
        this.ipsecPolicyId = mappingPolicy.getIpsecPolicyId();
    }

    public String getRouteMode() {
        return routeMode;
    }

    public void setRouteMode(String routeMode) {
        this.routeMode = routeMode;
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
