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

package org.openo.sdno.overlayvpn.model.netmodel.vxlan;

import java.util.List;

import org.openo.sdno.overlayvpn.model.NvString;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of TpQos.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class TpQos {

    /**
     * Key Id
     */
    @AString(min = 1, max = 36)
    private String uuid;

    private List<NvString> addtion;

    @AString(min = 1, max = 255)
    private String acId;

    @AString(min = 1, max = 255)
    private String vpnServiceId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<NvString> getAddtion() {
        return addtion;
    }

    public void setAddtion(List<NvString> addtion) {
        this.addtion = addtion;
    }

    public String getAcId() {
        return acId;
    }

    public void setAcId(String acId) {
        this.acId = acId;
    }

    public String getVpnServiceId() {
        return vpnServiceId;
    }

    public void setVpnServiceId(String vpnServiceId) {
        this.vpnServiceId = vpnServiceId;
    }
}
