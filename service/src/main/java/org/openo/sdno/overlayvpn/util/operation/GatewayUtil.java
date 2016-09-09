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

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.consts.ValidationConsts;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Util class of Gateway.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public class GatewayUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayUtil.class);

    private GatewayUtil() {
    }

    /**
     * Build filter map of query.<br>
     * 
     * @param tenantId tenant id
     * @param name resource name
     * @param description resource description
     * @param ipAddress ip address of gateway
     * @param neId network element id
     * @return filter map of query
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static String buildBatchQueryFilter(String tenantId, String name, String description, String ipAddress,
            String neId) throws ServiceException {

        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("tenantId", Arrays.asList(tenantId));

        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }
            filterMap.put("name", Arrays.asList(name));
        }

        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }
            filterMap.put("description", Arrays.asList(description));
        }

        if(StringUtils.hasLength(ipAddress)) {
            String normalStr = Normalizer.normalize(ipAddress, Form.NFKC);
            if(!normalStr.matches(ValidationConsts.IP_REGEX)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("ipAddress", ipAddress);
            }
            filterMap.put("ipAddress", Arrays.asList(ipAddress));
        }

        if(StringUtils.hasLength(neId)) {
            if(!UuidUtil.validate(neId)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("neId", neId);
            }
            filterMap.put("neId", Arrays.asList(neId));
        }

        return JsonUtil.toJson(filterMap);
    }

    /**
     * Build new gateway data based on old gateway and new data.<br>
     * 
     * @param queryedOldGateway old gateway
     * @param inputJsonStr new data in JSON format
     * @return new gateway data
     * @throws ServiceException when failed.
     * @since SDNO 0.5
     */
    public static ResultRsp<Gateway> buildNewGatewayByOldAndInputData(Gateway queryedOldGateway, String inputJsonStr)
            throws ServiceException {
        Map<String, String> updateDataMap = null;
        try {
            updateDataMap = JsonUtil.fromJson(inputJsonStr, new TypeReference<Map<String, String>>() {});
        } catch(IllegalArgumentException e) {
            LOGGER.error("update data formate error, inputJsonStr = " + inputJsonStr, e);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Gateway", "Update data");
            return null;
        }
        if(null == updateDataMap) {
            LOGGER.error("update data formate error, updateDataMap is NULL");
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Gateway", "Update data");
        }
        Gateway newGateway = new Gateway();
        newGateway.copyBasicData(queryedOldGateway);

        String name = updateDataMap.get("name");
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }
            newGateway.setName(name);
        }

        String description = updateDataMap.get("description");
        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }
            newGateway.setDescription(description);
        }

        String ipAddress = updateDataMap.get("ipAddress");
        if(StringUtils.hasLength(ipAddress)) {
            String normalStr = Normalizer.normalize(ipAddress, Form.NFKC);
            if(!normalStr.matches(ValidationConsts.IP_REGEX)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("ipAddress", ipAddress);
            }
            newGateway.setIpAddress(ipAddress);
        }

        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, newGateway);
    }

}
