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
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Site model object class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-5
 */
@MOResType(infoModelName = "site")
public class SiteMO extends BaseMO {

    private static final String MOKEY = "site";

    /**
     * Site type. Value: network_site, tenant_site. Can use enum class SiteType.
     */
    @AString(require = true, scope = "network_site,tenant_site")
    private String type;

    /**
     * Tenant ID.
     */
    @AString(require = false, min = 0, max = 36)
    private String tenantID;

    /**
     * Location info.
     */
    @AString(require = false, min = 0, max = 255)
    private String location;

    /**
     * tenant site id
     */
    private String tenantSiteIDs;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public SiteMO() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param name site name
     * @param type site type
     * @param tenantid tenant key id
     */
    public SiteMO(String name, String type, String tenantid) {
        super();
        setId(UuidUtils.createUuid());
        setName(name);
        setType(type);
        setTenantID(tenantid);
    }

    /**
     * Set type attribute.<br>
     * 
     * @param type String Object
     * @since SDNO 0.5
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get type attribute.<br>
     * 
     * @return type attribute
     * @since SDNO 0.5
     */
    public String getType() {
        return type;
    }

    /**
     * Set location attribute.<br>
     * 
     * @param location String Object
     * @since SDNO 0.5
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get location attribute.<br>
     * 
     * @return location attribute
     * @since SDNO 0.5
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get tenantID attribute.<br>
     * 
     * @return tenantID attribute
     * @since SDNO 0.5
     */
    public String getTenantID() {
        return tenantID;
    }

    /**
     * Set tenantID attribute.<br>
     * 
     * @param tenantID String Object
     * @since SDNO 0.5
     */
    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    /**
     * Get tenantSiteIDs attribute.<br>
     * 
     * @return tenantSiteIDs attribute
     * @since SDNO 0.5
     */
    public String getTenantSiteIDs() {
        return tenantSiteIDs;
    }

    /**
     * Set tenantSiteIDs attribute.<br>
     * 
     * @param tenantSiteIDs String Object
     * @since SDNO 0.5
     */
    public void setTenantSiteIDs(String tenantSiteIDs) {
        this.tenantSiteIDs = tenantSiteIDs;
    }

    /**
     * Override toJsonBody Function.<br>
     * 
     * @return JsonBody String
     * @since SDNO 0.5
     */
    @Override
    public String toJsonBody() {
        Map<String, Object> moListMap = new HashMap<String, Object>();
        moListMap.put(MOKEY, this);
        return JsonUtil.toJson(moListMap);
    }

}
