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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.model.LogicalTernminationPointMO;
import org.openo.sdno.overlayvpn.brs.rest.BrsRestconfProxy;
import org.openo.sdno.overlayvpn.consts.HttpCode;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogicalTernminationPoint Data DAO class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-4
 */
public class LogicalTernminationPointInvDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogicalTernminationPointInvDao.class);

    private static final String LTPURI = "/openoapi/sdnobrs/v1/logical-termination-points";

    private static final String LTP_KEY = "logicalTerminationPoint";

    private static final String LTPS_KEY = "logicalTerminationPoints";

    private static final Set<String> suportKeys = new HashSet<String>();

    static {
        suportKeys.add("name");
        suportKeys.add("logicalType");
        suportKeys.add("adminState");
        suportKeys.add("source");
        suportKeys.add("owner");
        suportKeys.add("meID");
        suportKeys.add("isEdgePoint");
        suportKeys.add("ipAddress");
    }

    /**
     * Add LogicalTernminationPoint.<br>
     * 
     * @param curMO LogicalTernminationPoint need to be added
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public void addMO(LogicalTernminationPointMO curMO) throws ServiceException {
        LOGGER.info("insert LogicalTernminationPoint begin, name:" + curMO.getName());
        BrsRestconfProxy.post(LTPURI, curMO.toJsonBody());
        LOGGER.info("insert LogicalTernminationPoint end, name:" + curMO.getName());
    }

    /**
     * Delete LogicalTernminationPoint.<br>
     * 
     * @param uuid LogicalTernminationPoint id
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public void deleteMO(String uuid) throws ServiceException {
        if(!UuidUtil.validate(uuid)) {
            LOGGER.error("delete uuid is ilegal!");
            SvcExcptUtil.throwBadRequestException("delete uuid is ilegal!");
        }

        RestfulResponse response = BrsRestconfProxy.delete(LTPURI + "/" + uuid, null);
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("delete logical-ternmination-point:" + uuid + " failed!!");
        }
    }

    /**
     * Update LogicalTernminationPoint.<br>
     * 
     * @param curLtpMO LogicalTernminationPoint need to be updated
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public void updateMO(LogicalTernminationPointMO curLtpMO) throws ServiceException {
        StringBuilder curID = new StringBuilder(curLtpMO.getId());
        curLtpMO.setId(null);
        BrsRestconfProxy.put(LTPURI + "/" + curID.toString(), curLtpMO.toJsonBody());
    }

    /**
     * Query all LogicalTernminationPoints.<br>
     * 
     * @return all LogicalTernminationPoints queried out
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public List<LogicalTernminationPointMO> getAllMO() throws ServiceException {
        RestfulResponse response = BrsRestconfProxy.get(LTPURI, "");
        if(!HttpCode.isSucess(response.getStatus())) {
            throw new ServiceException("query Mos Failed!!");
        }

        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        String data = JsonUtil.toJson(contentMap.get(LTPS_KEY));
        LogicalTernminationPointMO[] moArray = JsonUtil.fromJson(data, LogicalTernminationPointMO[].class);

        return Arrays.asList(moArray);
    }

    /**
     * Query LogicalTernminationPoints by input condition.Filter properties: name, logicalType,
     * adminState,
     * source, owner, meID, isEdgePoint, ipAddress.<br>
     * 
     * @param condition condition
     * @return LogicalTernminationPoints queried
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public List<LogicalTernminationPointMO> query(Map<String, String> condition) throws ServiceException {
        checkFilterData(condition);

        RestfulResponse response = BrsRestconfProxy.get(LTPURI, "", condition);

        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        String data = JsonUtil.toJson(contentMap.get(LTPS_KEY));
        LogicalTernminationPointMO[] moArray = JsonUtil.fromJson(data, LogicalTernminationPointMO[].class);

        return Arrays.asList(moArray);
    }

    /**
     * Query LogicalTernminationPoint by key id.<br>
     * 
     * @param id LogicalTernminationPoint id
     * @return port LogicalTernminationPoint queried out
     * @throws ServiceException when failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public LogicalTernminationPointMO query(String id) throws ServiceException {
        RestfulResponse response = BrsRestconfProxy.get(LTPURI + "/" + id, "");

        Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        String data = JsonUtil.toJson(contentMap.get(LTP_KEY));

        return JsonUtil.fromJson(data, LogicalTernminationPointMO.class);
    }

    /**
     * Check whether the filter field is supported.<br>
     * 
     * @param condition condition
     * @throws ServiceException when filter field is not supported.
     * @since SDNO 0.5
     */
    private void checkFilterData(Map<String, String> condition) throws ServiceException {
        for(Map.Entry<String, String> entry : condition.entrySet()) {
            if(!suportKeys.contains(entry.getKey())) {
                LOGGER.error("link is not support filter feild with " + entry.getKey());
                throw new ServiceException(ErrorCode.DB_FILLTER_NOT_SUPPORT);
            }
        }
    }

}
