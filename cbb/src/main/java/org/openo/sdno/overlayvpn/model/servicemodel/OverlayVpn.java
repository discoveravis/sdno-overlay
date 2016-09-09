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

package org.openo.sdno.overlayvpn.model.servicemodel;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.BaseServiceModel;

/**
 * Class of OverlayVpn Model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_overlayvpn")
public class OverlayVpn extends BaseServiceModel {

    /**
     * List of Connection id
     */
    @Null
    @NONInvField
    private List<String> connectionIds;

    /**
     * List of Connection Objects
     */
    @Valid
    @NONInvField
    private List<Connection> vpnConnections;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public OverlayVpn() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param uuid OverlayVpn UUid
     */
    public OverlayVpn(String uuid) {
        super();
        this.setUuid(uuid);
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param uuid OverlayVpn UUid
     * @param tenantId tenant id
     */
    public OverlayVpn(String uuid, String tenantId) {
        super();
        this.setUuid(uuid);
        this.setTenantId(tenantId);
    }

    public List<String> getConnectionIds() {
        return connectionIds;
    }

    public void setConnectionIds(List<String> connectionIds) {
        this.connectionIds = connectionIds;
    }

    public List<Connection> getVpnConnections() {
        return vpnConnections;
    }

    public void setVpnConnections(List<Connection> vpnConnections) {
        this.vpnConnections = vpnConnections;
    }

    /**
     * Copy Basic data from other OverlayVpn.<br>
     * 
     * @param data OverlayVpn Object
     * @since SDNO 0.5
     */
    public void copyBasicData(OverlayVpn data) {
        super.copyBasicData(data);
    }

}
