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

package org.openo.sdno.overlayvpn.util.operation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.EnumsUtils;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Util class of EndpointGroup.<br/>
 * <p>
 * This class provide some functions to operation EndpointGroup Data
 * </p>
 * 
 * @author
 * @version SDNO 0.5 06-May-2016
 */
public class EpgUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionUtil.class);

    private EpgUtil() {
    }

    /**
     * Build filter map of Query.<br/>
     * 
     * @param name resource name
     * @param type resource type
     * @param tenantIdFromToken tenant id from token
     * @param gatewayId gateway id
     * @param topologyRole topologyRole value
     * @param connectionId connection id
     * @return filter map of query
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static Map<String, Object> buildQueryFilterMap(String name, String type, String tenantIdFromToken,
            String gatewayId, String topologyRole, String connectionId) throws ServiceException {

        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("tenantId", Arrays.asList(tenantIdFromToken));

        doBuildFilterMap(filterMap, name, type);
        doBuildFilterMap2(filterMap, gatewayId, topologyRole, connectionId);

        return filterMap;
    }

    /**
     * First step to build filter map.<br/>
     * 
     * @param filterMap filter map of query
     * @param name resource name
     * @param type resource type
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    private static void doBuildFilterMap(Map<String, Object> filterMap, String name, String type)
            throws ServiceException {
        // name
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }
            filterMap.put("name", Arrays.asList(name));
        }

        if(StringUtils.hasLength(type)) {
            if(!EnumsUtils.isEpgTypeValid(type)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("type", type);
            }
            filterMap.put("type", type);
        }
    }

    /**
     * Second step to build filter map.<br/>
     * 
     * @param filterMap filter map of query
     * @param gatewayId gateway id
     * @param topologyRole topologyRole value
     * @param connectionId connection id
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    private static void doBuildFilterMap2(Map<String, Object> filterMap, String gatewayId, String topologyRole,
            String connectionId) throws ServiceException {
        // gatewayId
        if(StringUtils.hasLength(gatewayId)) {
            if(!UuidUtil.validate(gatewayId)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("gatewayId", gatewayId);
            }
            filterMap.put("gatewayId", Arrays.asList(gatewayId));
        }

        // topologyRole
        if(StringUtils.hasLength(topologyRole)) {
            if(!EnumsUtils.isTopologyRoleValid(topologyRole)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("topologyRole", topologyRole);
            }
            filterMap.put("topologyRole", topologyRole);
        }

        // name
        if(StringUtils.hasLength(connectionId)) {
            if(!UuidUtil.validate(connectionId)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("connectionId", connectionId);
            }
            filterMap.put("connectionId", Arrays.asList(connectionId));
        }
    }

    /**
     * Build new EndpointGroup based on old EndpointGroup end new data.<br/>
     * 
     * @param queryedOldEpg old EndpointGroup
     * @param inputJsonStr new data in JSON format
     * @return new EndpointGroup data
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static ResultRsp<EndpointGroup> mergeEpg(EndpointGroup queryedOldEpg, String inputJsonStr)
            throws ServiceException {
        Map<String, String> updateDataMap = null;
        try {
            updateDataMap = JsonUtil.fromJson(inputJsonStr, new TypeReference<Map<String, String>>() {});
        } catch(IllegalArgumentException e) {
            LOGGER.error("invalid update data format, inputJsonStr = " + inputJsonStr, e);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("EndpointGroup", "Update data");
        }
        if(null == updateDataMap) {
            LOGGER.error("update data formate error, updateDataMap is NULL");
            ThrowOverlayVpnExcpt.throwParmaterInvalid("EndpointGroup", "Update data");
        }
        EndpointGroup newEpg = new EndpointGroup();
        newEpg.copyBasicData(queryedOldEpg);

        String name = updateDataMap.get("name");
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }
            newEpg.setName(name);

            updateDataMap.remove("name");
        }

        String description = updateDataMap.get("description");
        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }
            newEpg.setDescription(description);

            updateDataMap.remove("description");
        }

        String adminStatus = updateDataMap.get("adminStatus");
        if(StringUtils.hasLength(adminStatus)) {
            if(!EnumsUtils.isAdminStatusValid(adminStatus)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("adminStatus", adminStatus);
            }
            newEpg.setAdminStatus(adminStatus);

            updateDataMap.remove("adminStatus");
        }

        // Modify Fields：name description adminStatus
        if(MapUtils.isNotEmpty(updateDataMap)) {
            ThrowOverlayVpnExcpt.throwUpdateEpgInvalid();
        }

        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, newEpg);
    }
}
