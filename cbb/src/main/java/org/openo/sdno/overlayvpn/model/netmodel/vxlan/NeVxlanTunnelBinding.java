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

package org.openo.sdno.overlayvpn.model.netmodel.vxlan;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of NeVxlanTunnelBinding Model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_nevxlantunnelbinding")
public class NeVxlanTunnelBinding extends AbstUuidModel {

    @AUuid(require = true)
    private String vxlanTunnelId;

    /**
     * VxLAN ID
     */
    @AUuid(require = true)
    private String attachBy;

    @AInt(require = true, min = 1, max = 16777215)
    private String vni;

    public String getVxlanTunnelId() {
        return vxlanTunnelId;
    }

    public void setVxlanTunnelId(String vxlanTunnelId) {
        this.vxlanTunnelId = vxlanTunnelId;
    }

    public String getAttachBy() {
        return attachBy;
    }

    public void setAttachBy(String attachBy) {
        this.attachBy = attachBy;
    }

    public String getVni() {
        return vni;
    }

    public void setVni(String vni) {
        this.vni = vni;
    }
}
