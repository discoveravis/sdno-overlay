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
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.IKEVersion;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * The class of IKE policy. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
@MOResType(infoModelName = "overlayvpn_tenant_ikepolicy")
public class IkePolicy extends SecurityPolicy {

    @AString(scope = "v1,v2")
    private String ikeVersion = IKEVersion.V1.getName();

    /**
     * It is used to copy the attributes of IKE policy. <br>
     * 
     * @param ikePolicy The source IKE policy
     * @since SDNO 0.5
     */
    public void copyBasicData(IkePolicy ikePolicy) {
        super.copyBasicData(ikePolicy);
        this.ikeVersion = ikePolicy.getIkeVersion();
    }

    public String getIkeVersion() {
        return ikeVersion;
    }

    public void setIkeVersion(String ikeVersion) {
        this.ikeVersion = ikeVersion;
    }
}
