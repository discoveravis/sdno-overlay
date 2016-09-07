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
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.consts.ValidationConsts;
import org.openo.sdno.overlayvpn.model.common.enums.PfsType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.AuthAlgorithmType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.EncryptionAlgorithmType;
import org.openo.sdno.overlayvpn.model.ipsec.SecurityPolicy;
import org.openo.sdno.overlayvpn.util.check.CheckStrUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;

/**
 * Class of merge Policy Operations.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public class MergePolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(MergePolicy.class);

    private MergePolicy() {
    }

    /**
     * Merge Security Policy.<br/>
     * 
     * @param mergedSecurityPolicy old SecurityPolicy data
     * @param updateNameValueMap new data
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static void mergeSecurityPolicy(SecurityPolicy mergedSecurityPolicy, Map<String, String> updateNameValueMap)
            throws ServiceException {
        // name
        mergeName(mergedSecurityPolicy, updateNameValueMap);

        // description
        mergeDescription(mergedSecurityPolicy, updateNameValueMap);

        // authAlgorithm
        mergeAuthAlgorithm(mergedSecurityPolicy, updateNameValueMap);

        // encryptionAlgorithm
        mergeEncryptionAlgorithm(mergedSecurityPolicy, updateNameValueMap);

        // pfs
        String pfs = updateNameValueMap.get("pfs");
        if(StringUtils.hasLength(pfs)) {
            if(!PfsType.validateName(pfs)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("pfs", pfs);
            }

            mergedSecurityPolicy.setPfs(pfs);
        }

        // lifetime
        String lifetime = updateNameValueMap.get("lifetime");
        if(StringUtils.hasLength(lifetime)) {
            checkLifeTime(lifetime);

            mergedSecurityPolicy.setLifeTime(lifetime);
        }
    }

    /**
     * Build filter map of query.<br/>
     * 
     * @param tenantIdFromToken tenant id
     * @param name resource name
     * @param description resource description
     * @param authAlgorithm authentication algorithm
     * @param encryptionAlgorithm encryption algorithm
     * @param pfs pfs value
     * @param lifetime lifetime value
     * @return filter map of query
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static Map<String, List<String>> buildQueryFilterMap(String tenantIdFromToken, String name,
            String description, String authAlgorithm, String encryptionAlgorithm, String pfs, String lifetime)
            throws ServiceException {
        // construt filter map
        Map<String, List<String>> filterMap = new HashMap<String, List<String>>();
        if(StringUtils.hasLength(tenantIdFromToken)) {
            filterMap.put("tenantId", Arrays.asList(tenantIdFromToken));
        }

        // name
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }

            filterMap.put("name", Arrays.asList(name));
        }

        // description
        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }

            filterMap.put("description", Arrays.asList(description));
        }

        // authAlgorithm
        if(StringUtils.hasLength(authAlgorithm)) {
            if(!AuthAlgorithmType.validateName(authAlgorithm)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("authAlgorithm", authAlgorithm);
            }

            filterMap.put("authAlgorithm", Arrays.asList(authAlgorithm));
        }

        // encryptionAlgorithm
        if(StringUtils.hasLength(encryptionAlgorithm)) {
            if(!EncryptionAlgorithmType.validateName(encryptionAlgorithm)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("encryptionAlgorithm", encryptionAlgorithm);
            }

            filterMap.put("encryptionAlgorithm", Arrays.asList(encryptionAlgorithm));
        }

        // pfs
        if(StringUtils.hasLength(pfs)) {
            if(!PfsType.validateName(pfs)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("pfs", pfs);
            }

            filterMap.put("pfs", Arrays.asList(pfs));
        }

        // lifetime
        if(StringUtils.hasLength(lifetime)) {
            checkLifeTime(lifetime);

            filterMap.put("lifetime", Arrays.asList(lifetime));
        }

        return filterMap;
    }

    private static void mergeEncryptionAlgorithm(SecurityPolicy mergedSecurityPolicy,
            Map<String, String> updateNameValueMap) throws ServiceException {
        String encryptionAlgorithm = updateNameValueMap.get("encryptionAlgorithm");
        if(StringUtils.hasLength(encryptionAlgorithm)) {
            if(!EncryptionAlgorithmType.validateName(encryptionAlgorithm)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("encryptionAlgorithm", encryptionAlgorithm);
            }

            mergedSecurityPolicy.setEncryptionAlgorithm(encryptionAlgorithm);
        }
    }

    private static void mergeAuthAlgorithm(SecurityPolicy mergedSecurityPolicy, Map<String, String> updateNameValueMap)
            throws ServiceException {
        String authAlgorithm = updateNameValueMap.get("authAlgorithm");
        if(StringUtils.hasLength(authAlgorithm)) {
            if(!AuthAlgorithmType.validateName(authAlgorithm)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("authAlgorithm", authAlgorithm);
            }

            mergedSecurityPolicy.setAuthAlgorithm(authAlgorithm);
        }
    }

    private static void mergeDescription(SecurityPolicy mergedSecurityPolicy, Map<String, String> updateNameValueMap)
            throws ServiceException {
        String description = updateNameValueMap.get("description");
        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }

            mergedSecurityPolicy.setDescription(description);
        }
    }

    private static void mergeName(SecurityPolicy mergedSecurityPolicy, Map<String, String> updateNameValueMap)
            throws ServiceException {
        String name = updateNameValueMap.get("name");
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }

            mergedSecurityPolicy.setName(name);
        }
    }

    private static void checkLifeTime(String lifetime) throws ServiceException {
        try {
            if(!CheckStrUtil.validateNormalizedString(lifetime, ValidationConsts.INT_REGEX)
                    || (Integer.parseInt(lifetime) <= 0)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("lifetime", lifetime);
            }
        } catch(NumberFormatException e) {
            LOGGER.error("invalid lifetime format:" + lifetime, e);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("lifetime", lifetime);
        }
    }
}
