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

package org.openo.sdno.overlayvpn.brs.model;

import java.util.HashMap;
import java.util.Map;

import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;

/**
 * Controller model class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-5
 */
@MOResType(infoModelName = "controller")
public final class ControllerMO extends BaseMO {

    private static final String MOKEY = "controller";

    private String objectId;

    /**
     * type of controller
     */
    private String productName;

    /**
     * version of controller
     */
    private String version;

    /**
     * IP address of controller
     */
    private String hostName;

    private String slaveHostName;

    private String description;

    private String vendor;

    /**
     * Get objectId attribute.<br>
     * 
     * @return objectId attribute
     * @since SDNO 0.5
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Set objectId attribute.<br>
     * 
     * @param objectId String Object
     * @since SDNO 0.5
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * Get productName attribute..<br>
     * 
     * @return productName attribute
     * @since SDNO 0.5
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Set productName attribute.<br>
     * 
     * @param productName String Object
     * @since SDNO 0.5
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Get version attribute.<br>
     * 
     * @return version attribute
     * @since SDNO 0.5
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set version attribute.<br>
     * 
     * @param version String Object
     * @since SDNO 0.5
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Get hostName attribute.<br>
     * 
     * @return hostName attribute
     * @since SDNO 0.5
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Set hostName attribute.<br>
     * 
     * @param hostName String Object
     * @since SDNO 0.5
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Get slaveHostName attribute.<br>
     * 
     * @return slaveHostName attribute
     * @since SDNO 0.5
     */
    public String getSlaveHostName() {
        return slaveHostName;
    }

    /**
     * Set slaveHostName attribute.<br>
     * 
     * @param slaveHostName String Object
     * @since SDNO 0.5
     */
    public void setSlaveHostName(String slaveHostName) {
        this.slaveHostName = slaveHostName;
    }

    /**
     * Get vendor attribute.<br>
     * 
     * @return vendor attribute
     * @since SDNO 0.5
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Set vendor attribute.<br>
     * 
     * @param vendor String Object
     * @since SDNO 0.5
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toJsonBody() {
        Map<String, Object> moListMap = new HashMap<String, Object>();
        moListMap.put(MOKEY, this);
        return JsonUtil.toJson(moListMap);
    }

}
