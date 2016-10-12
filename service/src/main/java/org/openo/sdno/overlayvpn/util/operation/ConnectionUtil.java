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

package org.openo.sdno.overlayvpn.util.operation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.TechnologyType;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyRole;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.EnumsUtils;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Utility class of Connection.<br>
 * <p>
 * This class provide some functions to operation Connection Data.
 * </p>
 * 
 * @author
 * @version SDNO 0.5 06-May-2016
 */
public class ConnectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionUtil.class);

    private ConnectionUtil() {
    }

    /**
     * Build Batch query Filter.<br>
     * 
     * @param tenantId tenant id
     * @param overlayVpnId OverlayVpn id
     * @return filter map of query
     * @throws ServiceException when overlay VPN ID is invalid
     * @since SDNO 0.5
     */
    public static String buildBatchQueryFilter(String tenantId, String overlayVpnId) throws ServiceException {
        // construct filter map
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("tenantId", Arrays.asList(tenantId));

        // overlayVpnId
        if(StringUtils.hasLength(overlayVpnId) && !UuidUtil.validate(overlayVpnId)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("overlayVpnId", overlayVpnId);
        }
        if(StringUtils.hasLength(overlayVpnId)) {
            filterMap.put("overlayVpnId", Arrays.asList(overlayVpnId));
        }

        return JsonUtil.toJson(filterMap);
    }

    /**
     * First step to build Filter map.<br>
     * 
     * @param filterMap filter map of query
     * @param name resource name
     * @param description resource description
     * @param adminStatus Administrative status
     * @param operStatus Operation Status
     * @throws ServiceException when data is invalid
     * @since SDNO 0.5
     */
    private static void doBuildFilterMap(Map<String, Object> filterMap, String name, String description,
            String adminStatus, String operStatus) throws ServiceException {
        // name
        if(StringUtils.hasLength(name) && (name.length() > CommConst.NAME_MAX_LENGTH)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
        }
        if(StringUtils.hasLength(name)) {
            filterMap.put("name", Arrays.asList(name));
        }

        // description
        if(StringUtils.hasLength(description) && (description.length() > CommConst.DESCRIPTION_MAX_LENGTH)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
        }
        if(StringUtils.hasLength(description)) {
            filterMap.put("description", Arrays.asList(description));
        }

        // adminStatus
        if(StringUtils.hasLength(adminStatus) && !EnumsUtils.isAdminStatusValid(adminStatus)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("adminStatus", adminStatus);
        }
        if(StringUtils.hasLength(adminStatus)) {
            filterMap.put("adminStatus", Arrays.asList(adminStatus));
        }

        // operStatus
        if(StringUtils.hasLength(operStatus) && !EnumsUtils.isOperStatusValid(operStatus)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("operStatus", operStatus);
        }
        if(StringUtils.hasLength(operStatus)) {
            filterMap.put("operStatus", Arrays.asList(operStatus));
        }
    }

    /**
     * Second step to build Filter map.<br>
     * 
     * @param filterMap filter map of query
     * @param overlayVpnId OverlayVpn id
     * @param topology topology value
     * @param technology technology value
     * @param mappingPolicyId mappingPolicy id
     * @throws ServiceException when data is invalid
     * @since SDNO 0.5
     */
    private static void doBuildFilterMap2(Map<String, Object> filterMap, String overlayVpnId, String topology,
            String technology, String mappingPolicyId) throws ServiceException {
        // overlayVpnId
        if(StringUtils.hasLength(overlayVpnId) && !UuidUtil.validate(overlayVpnId)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("overlayVpnId", overlayVpnId);
        }
        if(StringUtils.hasLength(overlayVpnId)) {
            filterMap.put("overlayVpnId", Arrays.asList(overlayVpnId));
        }

        // topology
        if(StringUtils.hasLength(topology) && !EnumsUtils.isTopologyValid(topology)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("topology", topology);
        }
        if(StringUtils.hasLength(topology)) {
            filterMap.put("topology", Arrays.asList(topology));
        }

        // technology
        if(StringUtils.hasLength(technology) && !TechnologyType.validateName(technology)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("technology", technology);
        }
        if(StringUtils.hasLength(technology)) {
            filterMap.put("technology", Arrays.asList(technology));
        }

        // mappingPolicyId
        if(StringUtils.hasLength(mappingPolicyId) && !UuidUtil.validate(mappingPolicyId)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("mappingPolicyId", mappingPolicyId);
        }
        if(StringUtils.hasLength(mappingPolicyId)) {
            filterMap.put("mappingPolicyId", Arrays.asList(mappingPolicyId));
        }
    }

    /**
     * Build new connection based on old connection and new data.<br>
     * 
     * @param queryedOldConn old connection
     * @param inputJsonStr new data in JSON format
     * @return new connection data
     * @throws ServiceException when operation failed
     * @since SDNO 0.5
     */
    public static ResultRsp<Connection> buildNewConnByOldAndInputData(Connection queryedOldConn, String inputJsonStr)
            throws ServiceException {
        Map<String, String> updateDataMap = OperationUtil.getUpdateDataMap(inputJsonStr);

        Connection newConnection = new Connection();
        newConnection.copyBasicData(queryedOldConn);

        String name = updateDataMap.get("name");
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }
            newConnection.setName(name);
        }

        String description = updateDataMap.get("description");
        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }
            newConnection.setDescription(description);
        }

        String adminStatus = updateDataMap.get("adminStatus");
        if(StringUtils.hasLength(adminStatus)) {
            if(!EnumsUtils.isAdminStatusValid(adminStatus)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("adminStatus", adminStatus);
            }
            newConnection.setAdminStatus(adminStatus);
        }

        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, newConnection);
    }

    /**
     * Check whether any Hub exists in the EndpointGroups.<br>
     * 
     * @param conn connection
     * @return true if Hub exists exist,false otherwise
     * @since SDNO 0.5
     */
    public static boolean isExistHubEpg(Connection conn) {
        boolean isExistHubEpg = false;

        if(null == conn || CollectionUtils.isEmpty(conn.getEndpointGroups())) {
            return isExistHubEpg;
        }

        for(EndpointGroup childEpg : conn.getEndpointGroups()) {
            if(TopologyRole.HUB.getName().equals(childEpg.getTopologyRole())) {
                isExistHubEpg = true;
                return isExistHubEpg;
            }
        }

        return isExistHubEpg;
    }

}
