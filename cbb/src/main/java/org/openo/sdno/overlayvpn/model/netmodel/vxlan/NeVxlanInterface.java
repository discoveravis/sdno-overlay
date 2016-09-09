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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.netmodel.BaseNetModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of NeVxlanInterface Model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_nevxlaninterface")
public class NeVxlanInterface extends BaseNetModel {

    /**
     * Device Id
     */
    @JsonIgnore
    private String deviceId;

    @AString(min = 1, max = 36)
    private String localName;

    /**
     * VxLan Instance Id
     */
    @AUuid(require = true)
    private String vxlanInstanceId;

    /**
     * Access type: port/dot1q
     */
    @AString(require = true, scope = "port,dot1q")
    private String accessType;

    /**
     * Port Id
     */
    @AUuid(require = true)
    private String portId;

    /**
     * VLAN Range
     */
    @AInt(min = 1, max = 4094)
    private String dot1qVlanBitmap;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return Returns the localName.
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * @param localName The localName to set.
     */
    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getVxlanInstanceId() {
        return vxlanInstanceId;
    }

    public void setVxlanInstanceId(String vxlanInstanceId) {
        this.vxlanInstanceId = vxlanInstanceId;
    }

    /**
     * @return Returns the accessType.
     */
    public String getAccessType() {
        return accessType;
    }

    /**
     * @param accessType The accessType to set.
     */
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    /**
     * @return Returns the portId.
     */
    public String getPortId() {
        return portId;
    }

    /**
     * @param portId The portId to set.
     */
    public void setPortId(String portId) {
        this.portId = portId;
    }

    /**
     * @return Returns the dot1qVlanBitmap.
     */
    public String getDot1qVlanBitmap() {
        return dot1qVlanBitmap;
    }

    /**
     * @param dot1qVlanBitmap The dot1qVlanBitmap to set.
     */
    public void setDot1qVlanBitmap(String dot1qVlanBitmap) {
        this.dot1qVlanBitmap = dot1qVlanBitmap;
    }

    /**
     * Generate NeVxlanInterface Unique Service Id.<br>
     * 
     * @param dbVxlanInterface NeVxlanInterface Object
     * @return Unique Service Id
     * @since SDNO 0.5
     */
    public String generateUniqueServiceId(NeVxlanInterface dbVxlanInterface) {
        return dbVxlanInterface.getConnectionServiceId() + dbVxlanInterface.getVxlanInstanceId()
                + dbVxlanInterface.getDeviceId() + dbVxlanInterface.getAccessType() + dbVxlanInterface.getPortId();
    }
}
