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

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.EnumsUtils;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Util class of OverlayVpn.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 06-May-2016
 */
public class OverlayVpnUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(OverlayVpnUtil.class);

    private OverlayVpnUtil() {
    }

    /**
     * Build filter map of query.<br/>
     * 
     * @param tenantId tenant id
     * @param name resource name
     * @param description resource description
     * @param adminStatus administrative status
     * @param operStatus operation status
     * @return filter map of query
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static String buildBatchQueryFilter(String tenantId, String name, String description, String adminStatus,
            String operStatus) throws ServiceException {

        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("tenantId", Arrays.asList(tenantId));

        // description
        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }
            filterMap.put("description", Arrays.asList(description));
        }

        // name
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }
            filterMap.put("name", Arrays.asList(name));
        }

        // adminStatus
        if(StringUtils.hasLength(adminStatus)) {
            if(!EnumsUtils.isAdminStatusValid(adminStatus)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("adminStatus", adminStatus);
            }
            filterMap.put("adminStatus", Arrays.asList(adminStatus));
        }

        // operStatus
        if(StringUtils.hasLength(operStatus)) {
            if(!EnumsUtils.isOperStatusValid(operStatus)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("operStatus", operStatus);
            }
            filterMap.put("operStatus", Arrays.asList(operStatus));
        }

        return JsonUtil.toJson(filterMap);
    }

    /**
     * Build new OverlayVpn based on old OverlayVpn and new data. <br/>
     * 
     * @param queryedOldVpn old OverlayVpn
     * @param inputJsonStr new data in JSON format
     * @return new OverlayVpn
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static ResultRsp<OverlayVpn> buildNewVpnByOldAndInputData(OverlayVpn queryedOldVpn, String inputJsonStr)
            throws ServiceException {
        Map<String, String> updateDataMap = null;
        try {
            updateDataMap = JsonUtil.fromJson(inputJsonStr, new TypeReference<Map<String, String>>() {});
        } catch(IllegalArgumentException e) {
            LOGGER.error("update data formate error, inputJsonStr = " + inputJsonStr, e);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Overlay VPN", "Update data");
            return null;
        }
        if(null == updateDataMap) {
            LOGGER.error("update data formate error, updateDataMap is NULL");
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Overlay VPN", "Update data");
        }
        OverlayVpn newOverlayVpn = new OverlayVpn();
        newOverlayVpn.copyBasicData(queryedOldVpn);

        String name = updateDataMap.get("name");
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }
            newOverlayVpn.setName(name);
        }

        String description = updateDataMap.get("description");
        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }
            newOverlayVpn.setDescription(description);
        }

        String adminStatus = updateDataMap.get("adminStatus");
        if(StringUtils.hasLength(adminStatus)) {
            if(!EnumsUtils.isAdminStatusValid(adminStatus)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("adminStatus", adminStatus);
            }
            newOverlayVpn.setAdminStatus(adminStatus);
        }

        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, newOverlayVpn);
    }
}
