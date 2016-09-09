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
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.model.common.enums.TransformProtocolType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.EncapsulationModeType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.IKEVersion;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Util class of SecurityPolicy.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public class SecurityPolicyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityPolicyUtil.class);

    private SecurityPolicyUtil() {
    }

    /**
     * Build filter map of query.<br>
     * 
     * @param tenantIdFromToken tenant id
     * @param name iKePolicy name
     * @param description iKePolicy description
     * @param authAlgorithm authentication algorithm
     * @param encryptionAlgorithm encryption algorithm
     * @param pfs pfs value
     * @param ikeVersion iKePolicy version
     * @param lifetime lifetime value
     * @return filter map of query
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static Map<String, List<String>> buildIkePolicyFilterMap(String tenantIdFromToken, String name,
            String description, String authAlgorithm, String encryptionAlgorithm, String pfs, String ikeVersion,
            String lifetime) throws ServiceException {
        Map<String, List<String>> filterMap = MergePolicy.buildQueryFilterMap(tenantIdFromToken, name, description,
                authAlgorithm, encryptionAlgorithm, pfs, lifetime);
        // ikeVersion
        if(StringUtils.hasLength(ikeVersion)) {
            if(!IKEVersion.validateName(ikeVersion)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("ikeVersion", ikeVersion);
            }

            filterMap.put("ikeVersion", Arrays.asList(ikeVersion));
        }

        return filterMap;
    }

    /**
     * Build filter map of query.<br>
     * 
     * @param tenantIdFromToken tenant id
     * @param name IpsecPolicy name
     * @param description IpsecPolicy description
     * @param transformProtocol transform protocol
     * @param encapsulationMode encapsulation mode
     * @param authAlgorithm authentication algorithm
     * @param encryptionAlgorithm encryption algorithm
     * @param pfs pfs value
     * @param lifetime lifetime value
     * @return filter map of query
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static Map<String, List<String>> buildIpsecPolicyFilterMap(String tenantIdFromToken, String name,
            String description, String transformProtocol, String encapsulationMode, String authAlgorithm,
            String encryptionAlgorithm, String pfs, String lifetime) throws ServiceException {
        Map<String, List<String>> filterMap = MergePolicy.buildQueryFilterMap(tenantIdFromToken, name, description,
                authAlgorithm, encryptionAlgorithm, pfs, lifetime);

        // transformProtocol
        if(StringUtils.hasLength(transformProtocol)) {
            if(!TransformProtocolType.validateName(transformProtocol)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("transformProtocol", transformProtocol);
            }

            filterMap.put("transformProtocol", Arrays.asList(transformProtocol));
        }

        // encapsulationMode
        if(StringUtils.hasLength(encapsulationMode)) {
            if(!EncapsulationModeType.validateName(encapsulationMode)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("encapsulationMode", encapsulationMode);
            }

            filterMap.put("encapsulationMode", Arrays.asList(encapsulationMode));
        }

        return filterMap;
    }

    /**
     * Build new IkePolicy based on old IkePolicy and new data.<br>
     * 
     * @param oldIkePolicy old IkePolicy
     * @param inputJsonStr new data in JSON format
     * @return new IkePolicy
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static IkePolicy mergeIkePolicy(IkePolicy oldIkePolicy, String inputJsonStr) throws ServiceException {
        Map<String, String> updateDataMap = null;
        try {
            updateDataMap = JsonUtil.fromJson(inputJsonStr, new TypeReference<Map<String, String>>() {});
        } catch(IllegalArgumentException e) {
            LOGGER.error("invalid update data format, inputJsonStr = " + inputJsonStr, e);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("IkePolicy", "Update data");
            return null;
        }

        // copy new data
        IkePolicy mergedIkePolicy = new IkePolicy();
        mergedIkePolicy.copyBasicData(oldIkePolicy);

        // merge attribute of parent class
        MergePolicy.mergeSecurityPolicy(mergedIkePolicy, updateDataMap);

        // merge attribute of itself
        // ikeVersion
        if(null == updateDataMap) {
            LOGGER.error("invalid updateDataMap parameter, inputJsonStr = " + inputJsonStr);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("ikeVersion", "updateDataMap");
        }

        String ikeVersion = updateDataMap.get("ikeVersion");
        if(StringUtils.hasLength(ikeVersion)) {
            if(!IKEVersion.validateName(ikeVersion)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("ikeVersion", ikeVersion);
            }

            mergedIkePolicy.setIkeVersion(ikeVersion);
        }

        return mergedIkePolicy;
    }

    /**
     * Build new IpSecPolicy based on old IpSecPolicy and new data.<br>
     * 
     * @param oldIpsecPolicy old IpSecPolicy
     * @param inputJsonStr new data in JSON format
     * @return new IpSecPolicy
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static IpSecPolicy mergeIpsecPolicy(IpSecPolicy oldIpsecPolicy, String inputJsonStr)
            throws ServiceException {
        Map<String, String> updateDataMap = null;
        try {
            updateDataMap = JsonUtil.fromJson(inputJsonStr, new TypeReference<Map<String, String>>() {});
        } catch(IllegalArgumentException e) {
            LOGGER.error("invalid update data format, inputJsonStr = " + inputJsonStr, e);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("IpSecPolicy", "Update data");
        }

        IpSecPolicy mergedIpsecPolicy = new IpSecPolicy();
        mergedIpsecPolicy.copyBasicData(oldIpsecPolicy);

        MergePolicy.mergeSecurityPolicy(mergedIpsecPolicy, updateDataMap);

        // transformProtocol
        if(null == updateDataMap) {
            LOGGER.error("invalid updateDataMap, inputJsonStr = " + inputJsonStr);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("transformProtocol", "updateDataMap");
        }

        String transformProtocol = updateDataMap.get("transformProtocol");
        if(StringUtils.hasLength(transformProtocol)) {
            if(!TransformProtocolType.validateName(transformProtocol)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("transformProtocol", transformProtocol);
            }

            mergedIpsecPolicy.setTransformProtocol(transformProtocol);
        }

        // encapsulationMode
        String encapsulationMode = updateDataMap.get("encapsulationMode");
        if(StringUtils.hasLength(encapsulationMode)) {
            if(!EncapsulationModeType.validateName(encapsulationMode)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("encapsulationMode", encapsulationMode);
            }

            mergedIpsecPolicy.setEncapsulationMode(encapsulationMode);
        }

        return mergedIpsecPolicy;
    }

}
