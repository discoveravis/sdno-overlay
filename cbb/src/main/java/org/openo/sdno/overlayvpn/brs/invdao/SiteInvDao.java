/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.brs.invdao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.enums.SiteType;
import org.openo.sdno.overlayvpn.brs.model.SiteMO;
import org.openo.sdno.overlayvpn.brs.rest.BrsRestconfProxy;
import org.openo.sdno.overlayvpn.consts.HttpCode;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Site data DAO class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-5
 */
@Repository
public class SiteInvDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteInvDao.class);

    private static final String SITEURI = "/openoapi/sdnobrs/v1/sites";

    private static final String SITES_KEY = "sites";

    private static final String SITE_KEY = "site";

    /**
     * Add site model object.<br>
     * 
     * @param curMO site model object info to be added
     * @throws ServiceException when add MO failed.
     * @since SDNO 0.5
     */
    public void addMO(SiteMO curMO) throws ServiceException {
        LOGGER.info("insert site begin,cur site name:" + curMO.getName());
        BrsRestconfProxy.post(SITEURI, curMO.toJsonBody());
        LOGGER.info("insert site end");
    }

    /**
     * Delete site model object.<br>
     * 
     * @param uuid site UUID
     * @throws ServiceException when delete model object failed.
     * @since SDNO 0.5
     */
    public void deleteMO(String uuid) throws ServiceException {
        if(!UuidUtil.validate(uuid)) {
            LOGGER.error("delete uuid is ilegal!");
            SvcExcptUtil.throwBadRequestException("delete uuid is ilegal!");
        }

        RestfulResponse response = BrsRestconfProxy.delete(SITEURI + "/" + uuid, null);
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("delete site:" + uuid + " failed!!");
        }
    }

    /**
     * Update site model object.<br>
     * 
     * @param curMO site info
     * @throws ServiceException when update failed.
     * @since SDNO 0.5
     */
    public void updateMO(SiteMO curMO) throws ServiceException {
        StringBuilder curID = new StringBuilder(curMO.getId());
        curMO.setId(null);
        BrsRestconfProxy.put(SITEURI + "/" + curID.toString(), curMO.toJsonBody());
    }

    /**
     * Query all site info.<br>
     * 
     * @return all site info.
     * @throws ServiceException when query site info failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public List<SiteMO> getAllMO() throws ServiceException {
        RestfulResponse response = BrsRestconfProxy.get(SITEURI, "");

        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        String data = JsonUtil.toJson(contentMap.get(SITES_KEY));
        SiteMO[] moArray = JsonUtil.fromJson(data, SiteMO[].class);

        return Arrays.asList(moArray);
    }

    /**
     * Query site model object info with input condition.Filter properties: name, type,
     * tenantID.<br>
     * 
     * @param condition condition
     * @return site model object info
     * @throws ServiceException when query site info failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public List<SiteMO> query(Map<String, String> condition) throws ServiceException {
        checkFilterData(condition);

        RestfulResponse response = BrsRestconfProxy.get(SITEURI, "", condition);

        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        String data = JsonUtil.toJson(contentMap.get(SITES_KEY));
        SiteMO[] moArray = JsonUtil.fromJson(data, SiteMO[].class);

        return Arrays.asList(moArray);
    }

    /**
     * Query site model object info with site ID.<br>
     * 
     * @param id site ID
     * @return site model object info
     * @throws ServiceException when query site info failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public SiteMO query(String id) throws ServiceException {
        RestfulResponse response = BrsRestconfProxy.get(SITEURI + "/" + id, "");

        Map<String, Map<String, Object>> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        Map<String, Object> siteDataMap = contentMap.get(SITE_KEY);
        if(null == siteDataMap || siteDataMap.isEmpty()) {
            LOGGER.debug("Current site does not exist");
            return null;
        }

        return JsonUtil.fromJson(JsonUtil.toJson(siteDataMap), SiteMO.class);
    }

    /**
     * Query site info with tenant ID.<br>
     * 
     * @param tenantId tenant ID
     * @return site info
     * @throws ServiceException when query site info failed.
     * @since SDNO 0.5
     */
    public List<SiteMO> getSiteByTenantId(String tenantId) throws ServiceException {
        Map<String, String> filter = new ConcurrentHashMap<String, String>();
        filter.put("tenantID", tenantId);
        filter.put("type", SiteType.TENANT_SITE.getName());

        return query(filter);
    }

    /**
     * Query SiteMO by name.<br>
     * 
     * @param name Site name
     * @return SiteMO list returned
     * @throws ServiceException ServiceException occured
     * @since SDNO 0.5
     */
    public List<SiteMO> getSiteByName(String name) throws ServiceException {
        Map<String, String> filter = new ConcurrentHashMap<String, String>();
        filter.put("name", name);
        filter.put("type", SiteType.TENANT_SITE.getName());

        return query(filter);
    }

    /**
     * Check whether the filter field is supported.<br>
     * 
     * @param condition condition
     * @throws ServiceException when filter field is not supported.
     * @since SDNO 0.5
     */
    private void checkFilterData(Map<String, String> condition) throws ServiceException {
        Set<String> suportKeys = new HashSet<String>();

        suportKeys.add("name");
        suportKeys.add("type");
        suportKeys.add("tenantID");

        for(Map.Entry<String, String> entry : condition.entrySet()) {
            if(!suportKeys.contains(entry.getKey())) {
                LOGGER.error("site is not support filter feild with " + entry.getKey());
                throw new ServiceException(ErrorCode.DB_FILLTER_NOT_SUPPORT);
            }
        }
    }
}
