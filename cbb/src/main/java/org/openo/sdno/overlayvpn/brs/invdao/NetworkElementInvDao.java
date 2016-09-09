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

package org.openo.sdno.overlayvpn.brs.invdao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.brs.model.SiteMO;
import org.openo.sdno.overlayvpn.brs.rest.BrsRestconfProxy;
import org.openo.sdno.overlayvpn.consts.HttpCode;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NetworkElement data DAO class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-5
 */
public class NetworkElementInvDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkElementInvDao.class);

    private static final String NEURI = "/openoapi/sdnobrs/v1/managed-elements";

    private static final String NES_KEY = "managedElements";

    private static final String NE_KEY = "managedElement";

    /**
     * Add NetworkElement object.<br>
     * 
     * @param curMO NetworkElement need to be added
     * @throws ServiceException when operation failed
     * @since SDNO 0.5
     */
    public void addMO(NetworkElementMO curMO) throws ServiceException {

        LOGGER.info("insert NE begin,cur NE name:" + curMO.getName());
        BrsRestconfProxy.post(NEURI, curMO.toJsonBody());
        LOGGER.info("insert NE end");
    }

    /**
     * Delete NetworkElement object.<br>
     * 
     * @param uuid NetworkElement id
     * @throws ServiceException when delete model object failed
     * @since SDNO 0.5
     */
    public void deleteMO(String uuid) throws ServiceException {
        if(!UuidUtil.validate(uuid)) {
            LOGGER.error("delete uuid is ilegal!");
            SvcExcptUtil.throwBadRequestException("delete uuid is ilegal!");
        }

        RestfulResponse response = BrsRestconfProxy.delete(NEURI + "/" + uuid, null);
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("delete me:" + uuid + " failed!!");
        }
    }

    /**
     * Update NetworkElement object.<br>
     * 
     * @param curNeMO NetworkElement need to updated
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public void updateMO(NetworkElementMO curNeMO) throws ServiceException {
        StringBuilder curID = new StringBuilder(curNeMO.getId());
        curNeMO.setId(null);
        BrsRestconfProxy.put(NEURI + "/" + curID.toString(), curNeMO.toJsonBody());
    }

    /**
     * Query all NetworkElements.<br>
     * 
     * @return all NetworkElement queried out
     * @throws ServiceException when query failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public List<NetworkElementMO> getAllMO() throws ServiceException {
        RestfulResponse response = BrsRestconfProxy.get(NEURI, "");

        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        String data = JsonUtil.toJson(contentMap.get(NES_KEY));
        NetworkElementMO[] moArray = JsonUtil.fromJson(data, NetworkElementMO[].class);

        return Arrays.asList(moArray);
    }

    /**
     * Query NetworkElement by Controller.<br>
     * 
     * @param controllerID controller id
     * @return NetworkElement queried out
     * @throws ServiceException when query failed.
     * @since SDNO 0.5
     */
    public List<NetworkElementMO> queryMOByConntrollerId(String controllerID) throws ServiceException {
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("controllerID", controllerID);

        return query(conditionMap);
    }

    /**
     * Query NetworkElement model object info with input condition.Filter properties: name,
     * productName, ipAddress, adminState, source, owner, managementDomainID, controllerID,
     * siteID.<br>
     * 
     * @param condition condition
     * @return NetworkElement queried out
     * @throws ServiceException when query failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public List<NetworkElementMO> query(Map<String, String> condition) throws ServiceException {
        checkFilterData(condition);

        RestfulResponse response = BrsRestconfProxy.get(NEURI, "", condition);

        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        String data = JsonUtil.toJson(contentMap.get(NES_KEY));
        NetworkElementMO[] moArray = JsonUtil.fromJson(data, NetworkElementMO[].class);

        return Arrays.asList(moArray);
    }

    /**
     * Query NetworkElement by id.<br>
     * 
     * @param id NetworkElement id
     * @return NE NetworkElement queried out
     * @throws ServiceException when query failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public NetworkElementMO query(String id) throws ServiceException {
        RestfulResponse response = BrsRestconfProxy.get(NEURI + "/" + id, "");

        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        String data = JsonUtil.toJson(contentMap.get(NE_KEY));
        return JsonUtil.fromJson(data, NetworkElementMO.class);
    }

    /**
     * Query NetworkElement info with site ID.<br>
     * 
     * @param siteId site id
     * @return NetworkElement queried out
     * @throws ServiceException when query failed.
     * @since SDNO 0.5
     */
    public List<NetworkElementMO> getNeBySiteId(String siteId) throws ServiceException {
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("siteID", siteId);

        return query(conditionMap);
    }

    /**
     * Query NetworkElement info with site ID.<br>
     * 
     * @param name NetworkElement name
     * @return NetworkElement queried out
     * @throws ServiceException when query failed.
     * @since SDNO 0.5
     */
    public List<NetworkElementMO> getNeByName(String name) throws ServiceException {
        Map<String, String> conditionMap = new HashMap<String, String>();
        conditionMap.put("name", name);

        return query(conditionMap);
    }

    /**
     * Query site list by NetworkElement id.<br>
     * 
     * @param neId NetworkElement id
     * @return list of site queried out
     * @throws ServiceException when query failed.
     * @since SDNO 0.5
     */
    public List<SiteMO> getSiteByNeId(String neId) throws ServiceException {
        List<SiteMO> siteList = new ArrayList<SiteMO>();

        SiteInvDao siteDao = new SiteInvDao();

        NetworkElementMO ne = query(neId);

        if((ne != null) && (ne.getSiteID() != null)) {
            for(String siteId : ne.getSiteID()) {
                SiteMO site = siteDao.query(siteId);
                if(site != null) {
                    siteList.add(site);
                }
            }
        }

        return siteList;
    }

    /**
     * Query site list with tenant ID. <br>
     * 
     * @param tenantId tenant ID
     * @return list of site queried out
     * @throws ServiceException when query failed.
     * @since SDNO 0.5
     */
    public List<NetworkElementMO> getNeByTenantId(String tenantId) throws ServiceException {
        SiteInvDao siteDao = new SiteInvDao();

        List<NetworkElementMO> neList = new ArrayList<>();
        List<SiteMO> siteList = siteDao.getSiteByTenantId(tenantId);

        for(SiteMO site : siteList) {
            neList.addAll(getNeBySiteId(site.getId()));
        }

        return neList;
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
        suportKeys.add("productName");
        suportKeys.add("ipAddress");
        suportKeys.add("adminState");
        suportKeys.add("source");
        suportKeys.add("owner");
        suportKeys.add("managementDomainID");
        suportKeys.add("controllerID");
        suportKeys.add("siteID");
        suportKeys.add("serialNumber");

        for(Map.Entry<String, String> entry : condition.entrySet()) {
            if(!suportKeys.contains(entry.getKey())) {
                LOGGER.error("ne is not support filter feild with " + entry.getKey());
                throw new ServiceException(ErrorCode.DB_FILLTER_NOT_SUPPORT);
            }
        }
    }
}
