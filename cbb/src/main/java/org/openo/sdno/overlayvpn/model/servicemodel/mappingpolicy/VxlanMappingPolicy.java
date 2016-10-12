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

package org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of VxLan MappingPloicy.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 3, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_vxlanmappingpolicy")
public class VxlanMappingPolicy extends BaseMappingPolicy {

    /**
     * ARP Proxy Enable or not
     */
    @AString(scope = "true,false")
    private String arpProxy;

    /**
     * ARP Broadcast Suppress or not
     */
    @AString(scope = "true,false")
    private String arpBroadcastSuppress;

    /**
     * Copy basic attribute from other Object.<br>
     * 
     * @param mappingPolicy other VxlanMappingPolicy object
     * @since SDNO 0.5
     */
    public void copyBasicData(VxlanMappingPolicy mappingPolicy) {
        super.copyBasicData(mappingPolicy);
        this.arpProxy = mappingPolicy.getArpProxy();
        this.arpBroadcastSuppress = mappingPolicy.getArpBroadcastSuppress();
    }

    public String getArpProxy() {
        return arpProxy;
    }

    public void setArpProxy(String arpProxy) {
        this.arpProxy = arpProxy;
    }

    public String getArpBroadcastSuppress() {
        return arpBroadcastSuppress;
    }

    public void setArpBroadcastSuppress(String arpBroadcastSuppress) {
        this.arpBroadcastSuppress = arpBroadcastSuppress;
    }
}
