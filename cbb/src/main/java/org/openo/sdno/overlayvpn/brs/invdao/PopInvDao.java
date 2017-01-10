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

package org.openo.sdno.overlayvpn.brs.invdao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.model.PopMO;
import org.openo.sdno.overlayvpn.brs.rest.BrsRestconfProxy;
import org.openo.sdno.overlayvpn.consts.HttpCode;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Pop Data DAO class.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-01-10
 */
@Repository
public class PopInvDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PopInvDao.class);

    private static final String POP_URL = "/openoapi/sdnobrs/v1/pops";

    private static final String POPS_KEY = "pops";

    private static final String POP_KEY = "pop";

    /**
     * Create PopMO.<br>
     * 
     * @param curMO need to create
     * @throws ServiceException when add failed
     * @since SDNO 0.5
     */
    public void addMO(PopMO curMO) throws ServiceException {

        RestfulResponse response = BrsRestconfProxy.post(POP_URL, curMO.toJsonBody());
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("PopMO add failed");
            throw new ServiceException("PopMO add failed");
        }

    }

    /**
     * Delete PopMO.<br>
     * 
     * @param uuid uuid of PopMO which need to delete
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public void deleteMO(String uuid) throws ServiceException {

        if(!UuidUtil.validate(uuid)) {
            LOGGER.error("uuid is invalid");
            throw new ServiceException("uuid is invalid");
        }

        RestfulResponse response = BrsRestconfProxy.delete(POP_URL + "/" + uuid, null);
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("delete pop:" + uuid + " failed!!");
        }

    }

    /**
     * Update PopMO.<br>
     * 
     * @param curMO PopMO need to delete
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    public void updateMO(PopMO curMO) throws ServiceException {
        StringBuilder curID = new StringBuilder(curMO.getId());
        curMO.setId(null);
        BrsRestconfProxy.put(POP_URL + "/" + curID.toString(), curMO.toJsonBody());
    }

    /**
     * Query PopMOs.<br>
     * 
     * @return PopMO queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<PopMO> getAllMO() throws ServiceException {
        RestfulResponse response = BrsRestconfProxy.get(POP_URL, null);
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("Query Pop datas failed");
            throw new ServiceException("Query Pop datas failed");
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        String data = JsonUtil.toJson(contentMap.get(POP_URL));
        return JsonUtil.fromJson(data, new TypeReference<List<PopMO>>() {});
    }

    /**
     * Query PopMO By Id.<br>
     * 
     * @param uuid PopMO Uuid
     * @return PopMO queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public PopMO query(String uuid) throws ServiceException {

        RestfulResponse response = BrsRestconfProxy.get(POP_URL + "/" + uuid, null);
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("Query Pop data failed");
            throw new ServiceException("Query Pop data failed");
        }

        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        Map<String, Object> objMap = (Map<String, Object>)contentMap.get(POP_KEY);
        if(null == objMap || objMap.isEmpty()) {
            return null;
        }

        return JsonUtil.fromJson(JsonUtil.toJson(objMap), PopMO.class);
    }

    /**
     * Query PopMos by Condition Map.<br>
     * 
     * @param conditionMap condition map
     * @return Pop data queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<PopMO> query(Map<String, String> conditionMap) throws ServiceException {
        checkFilterData(conditionMap);

        RestfulResponse response = BrsRestconfProxy.get(POP_URL, null, conditionMap);
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("Query Pop data failed");
            throw new ServiceException("Query Pop data failed");
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        String data = JsonUtil.toJson(contentMap.get(POPS_KEY));
        return JsonUtil.fromJson(data, new TypeReference<List<PopMO>>() {});
    }

    private void checkFilterData(Map<String, String> conditionMap) throws ServiceException {
        Set<String> supportKeys = new HashSet<String>();
        supportKeys.add("name");
        supportKeys.add("type");
        supportKeys.add("tenantID");

        for(Map.Entry<String, String> entry : conditionMap.entrySet()) {
            if(!supportKeys.contains(entry)) {
                LOGGER.error("Pop do not support field with " + entry.getKey());
                throw new ServiceException("Pop do not support field with " + entry.getKey());
            }
        }
    }

}
