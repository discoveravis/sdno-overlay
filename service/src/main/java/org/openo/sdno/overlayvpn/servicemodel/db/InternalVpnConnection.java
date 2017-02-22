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

package org.openo.sdno.overlayvpn.servicemodel.db;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOEditableField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;

/**
 * The internal vpn connection model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
@MOResType(infoModelName = "internal_vpnconnection")
public class InternalVpnConnection extends NbiServiceConnection {

    private String siteId;

    private String srcNeId;

    private String destNeId;

    private String vpnTemplateName;

    @MOEditableField
    private String deployPosition;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public InternalVpnConnection() {
        super();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSrcNeId() {
        return srcNeId;
    }

    public void setSrcNeId(String srcNeId) {
        this.srcNeId = srcNeId;
    }

    public String getDestNeId() {
        return destNeId;
    }

    public void setDestNeId(String destNeId) {
        this.destNeId = destNeId;
    }

    public String getVpnTemplateName() {
        return vpnTemplateName;
    }

    public void setVpnTemplateName(String vpnTemplateName) {
        this.vpnTemplateName = vpnTemplateName;
    }

    public String getDeployPosition() {
        return deployPosition;
    }

    public void setDeployPosition(String deployPosition) {
        this.deployPosition = deployPosition;
    }
}
