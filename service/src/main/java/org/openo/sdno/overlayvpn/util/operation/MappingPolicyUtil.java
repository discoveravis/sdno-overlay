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

package org.openo.sdno.overlayvpn.util.operation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.model.common.enums.MappingPolicyType;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.BaseMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.util.check.EnumsUtils;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Utility class of MappingPolicy.<br>
 * 
 * @author
 * @version SDNO 0.5 June 1, 2016
 */
public class MappingPolicyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingPolicyUtil.class);

    private MappingPolicyUtil() {
    }

    /**
     * Build filter map of query.<br>
     * 
     * @param tenantIdFromToken
     *            tenant id
     * @param name
     *            resource name
     * @param description
     *            resource description
     * @param type
     *            resource type
     * @return filter map of query
     * @throws ServiceException
     *             when failed
     * @since SDNO 0.5
     */
    public static Map<String, List<String>> buildQueryFilterMap(String tenantIdFromToken, String name,
            String description, String type) throws ServiceException {

        // tenant id
        Map<String, List<String>> filterMap = new HashMap<String, List<String>>();
        if(StringUtils.hasLength(tenantIdFromToken)) {
            filterMap.put("tenantId", Arrays.asList(tenantIdFromToken));
        }

        // name
        if(StringUtils.hasLength(name)) {
            if(name.length() > 128) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }

            filterMap.put("name", Arrays.asList(name));
        }

        // description
        if(StringUtils.hasLength(description)) {
            if(description.length() > 256) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }

            filterMap.put("description", Arrays.asList(description));
        }

        // type
        if(StringUtils.hasLength(type)) {
            if(!MappingPolicyType.validateName(type)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("type", type);
            }

            filterMap.put("type", Arrays.asList(type));
        }

        return filterMap;
    }

    /**
     * Build new mapping policy data with old mappingPolicy and new data.<br>
     * 
     * @param oldMappingPolicy
     *            old mappingPolicy data
     * @param inputJsonStr
     *            new data in JSON format
     * @return new MappingPolicy data
     * @throws ServiceException
     *             when failed
     * @since SDNO 0.5
     */
    public static BaseMappingPolicy mergeMappingPolicy(BaseMappingPolicy oldMappingPolicy, String inputJsonStr)
            throws ServiceException {
        Map<String, String> updateNameValueMap = null;
        try {
            updateNameValueMap = JsonUtil.fromJson(inputJsonStr, new TypeReference<Map<String, String>>() {});
        } catch(IllegalArgumentException e) {
            LOGGER.error("invalid update data format, inputJsonStr = " + inputJsonStr, e);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("IpSecPolicy", "Update data");
            return null;
        }

        String mappingPolicyType = oldMappingPolicy.getType();
        if(MappingPolicyType.isIpsec(mappingPolicyType)) {
            IpsecMappingPolicy mergedIpsecMappingPolicy = new IpsecMappingPolicy();
            mergedIpsecMappingPolicy.copyBasicData((IpsecMappingPolicy)oldMappingPolicy);
            mergeBaseMappingPolicy(mergedIpsecMappingPolicy, updateNameValueMap);
            mergeIpsecMappingPolicy(mergedIpsecMappingPolicy, updateNameValueMap);
            return mergedIpsecMappingPolicy;
        } else if(MappingPolicyType.isVxlanRelated(mappingPolicyType)) {
            VxlanMappingPolicy mergedVxlanMappingPolicy = new VxlanMappingPolicy();
            mergedVxlanMappingPolicy.copyBasicData((VxlanMappingPolicy)oldMappingPolicy);
            mergeBaseMappingPolicy(mergedVxlanMappingPolicy, updateNameValueMap);
            mergeVxlanMappingPolicy(mergedVxlanMappingPolicy, updateNameValueMap);
            return mergedVxlanMappingPolicy;
        } else {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("type", mappingPolicyType);
        }

        return null;
    }

    private static void mergeBaseMappingPolicy(BaseMappingPolicy mappingPolicy, Map<String, String> updateNameValueMap)
            throws ServiceException {
        // name
        String name = updateNameValueMap.get("name");
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }

            mappingPolicy.setName(name);
        }

        // description
        String description = updateNameValueMap.get("description");
        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }

            mappingPolicy.setDescription(description);
        }
    }

    private static void mergeIpsecMappingPolicy(IpsecMappingPolicy mergedIpsecMappingPolicy,
            Map<String, String> updateNameValueMap) throws ServiceException {
        // routeMode
        String routeMode = updateNameValueMap.get("routeMode");
        if(StringUtils.hasLength(routeMode)) {
            if(!EnumsUtils.isRouteModevalid(routeMode)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("routeMode", routeMode);
            }

            mergedIpsecMappingPolicy.setRouteMode(routeMode);
        }

        // authMode
        String authMode = updateNameValueMap.get("authMode");
        if(StringUtils.hasLength(authMode)) {
            if(!EnumsUtils.isAuthModeValid(authMode)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("authMode", authMode);
            }

            mergedIpsecMappingPolicy.setAuthMode(authMode);
        }

        // psk
        String psk = updateNameValueMap.get("psk");
        if(StringUtils.hasLength(psk)) {
            if(psk.length() > CommConst.PSK_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("psk", psk);
            }

            mergedIpsecMappingPolicy.setPsk(psk);
        }
    }

    private static void mergeVxlanMappingPolicy(VxlanMappingPolicy mergedVxlanMappingPolicy,
            Map<String, String> updateNameValueMap) throws ServiceException {
        // arpProxy
        String arpProxy = updateNameValueMap.get("arpProxy");
        if(StringUtils.hasLength(arpProxy)) {
            if(!EnumsUtils.isBooleanStrValid(arpProxy)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("arpProxy", arpProxy);
            }

            mergedVxlanMappingPolicy.setArpProxy(arpProxy);
        }

        // arpBroadcastSuppress
        String arpBroadcastSuppress = updateNameValueMap.get("arpBroadcastSuppress");
        if(StringUtils.hasLength(arpBroadcastSuppress)) {
            if(!EnumsUtils.isBooleanStrValid(arpBroadcastSuppress)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("arpBroadcastSuppress", arpBroadcastSuppress);
            }

            mergedVxlanMappingPolicy.setArpBroadcastSuppress(arpBroadcastSuppress);
        }
    }
}
