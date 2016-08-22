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

package org.openo.sdno.overlayvpn.model.servicemodel;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.ModelBase;
import org.openo.sdno.overlayvpn.verify.annotation.AIp;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of Gateway Model.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_gateway")
public class Gateway extends ModelBase {

    /**
     * IpAddress of Gateway
     */
    @AIp(require = true)
    private String ipAddress;

    /**
     * id of network element
     */
    @AUuid(require = true)
    private String neId;

    /**
     * id of network element device
     */
    @JsonIgnore
    private String deviceId;

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    public Gateway() {
        super();
    }

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param uuid
     */
    public Gateway(String uuid) {
        super();
        this.setUuid(uuid);
    }

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param uuid Gateway UUid
     * @param tenantId tanant id
     */
    public Gateway(String uuid, String tenantId) {
        super();
        this.setUuid(uuid);
        this.setTenantId(tenantId);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Copy basic data from other Object.<br/>
     * 
     * @param data other Gateway Data
     * @since SDNO 0.5
     */
    public void copyBasicData(Gateway data) {
        super.copyBasicData(data);
        this.ipAddress = data.getIpAddress();
        this.neId = data.getNeId();
        this.deviceId = data.getDeviceId();
    }

}
