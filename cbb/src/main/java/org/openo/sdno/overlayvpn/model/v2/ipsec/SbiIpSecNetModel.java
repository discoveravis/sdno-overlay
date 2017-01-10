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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseModel;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of IpSec Net Model.<br>
 * 
 * @author
 * @version SDNO 0.5 June 6, 2016
 */
public abstract class SbiIpSecNetModel extends BaseModel {

    /**
     * Controller Id
     */
    @AUuid(require = false)
    private String controllerId;

    /**
     * External Id
     */
    @NONInvField
    @AUuid(require = false)
    private String externalId;

    /**
     * Connection Id
     */
    @AUuid(require = true)
    private String connectionServiceId;

    /**
     * Device Ne Id
     */
    @AUuid(require = true)
    private String neId;

    /**
     * Peer Device Ne Id
     */
    @JsonIgnore
    @AUuid(require = false)
    private String peerNeId;

    /**
     * Device Id
     */
    @AUuid(require = true)
    private String deviceId;

    /**
     * Peer device Id
     */
    @AUuid(require = false)
    private String peerDeviceId;

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getConnectionServiceId() {
        return connectionServiceId;
    }

    public void setConnectionServiceId(String connectionServiceId) {
        this.connectionServiceId = connectionServiceId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getPeerNeId() {
        return peerNeId;
    }

    public void setPeerNeId(String peerNeId) {
        this.peerNeId = peerNeId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPeerDeviceId() {
        return peerDeviceId;
    }

    public void setPeerDeviceId(String peerDeviceId) {
        this.peerDeviceId = peerDeviceId;
    }

}
