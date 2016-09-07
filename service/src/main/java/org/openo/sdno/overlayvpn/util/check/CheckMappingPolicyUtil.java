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

package org.openo.sdno.overlayvpn.util.check;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.common.enums.MappingPolicyType;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.BaseMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.GreMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.FilterDataUtil;
import org.openo.sdno.overlayvpn.util.check.ValidationUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;

/**
 * MappingPolicy Data Verify Class.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 05-May-2016
 */
public class CheckMappingPolicyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckMappingPolicyUtil.class);

    private CheckMappingPolicyUtil() {
    }

    /**
     * Check weather MappingPolicy data is valid.<br/>
     * 
     * @param mappingPolicy MappingPolicy data need to check
     * @throws ServiceException ServiceException throws when data is invalid
     * @since SDNO 0.5
     */
    public static void check(BaseMappingPolicy mappingPolicy) throws ServiceException {
        // Validate model data
        checkModelData(mappingPolicy);

        // Check whether the resource exists in database or not
        checkResourceInMappingPolicy(mappingPolicy);

        // check whether Ikepolicy is null
        if(null != mappingPolicy.getIkePolicy()) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Ike Policy", "Not Null");
        }

        // check whether IPsecpolicy is null
        if(null != mappingPolicy.getIpSecPolicy()) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Ipsec Policy", "Not Null");
        }
    }

    /**
     * <br/>
     * 
     * @param mappingPolicy
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public static void checkMappingPolicyIsUsed(BaseMappingPolicy mappingPolicy) throws ServiceException {
        String mappingPolicyUuid = mappingPolicy.getUuid();
        String filter = FilterDataUtil.getFilterData("mappingPolicyId", Arrays.asList(mappingPolicyUuid));
        ResultRsp<List<Connection>> connQueryResult =
                new InventoryDao<Connection>().queryByFilter(Connection.class, filter, null);
        if(!connQueryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(connQueryResult);
        }

        if(CollectionUtils.isNotEmpty(connQueryResult.getData())) {
            ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt("[Mapping policy]" + mappingPolicy.getName(), "[Connection]"
                    + connQueryResult.getData().get(0).getName() + "...");
        }
    }

    private static void checkModelData(BaseMappingPolicy mappingPolicy) throws ServiceException {
        // Validate the mapping policy object
        ValidationUtil.validateModel(mappingPolicy);

        // Check policy instance type
        String policyInstanceType = null;
        if(mappingPolicy instanceof GreMappingPolicy) {
            GreMappingPolicy greMappingPolicy = (GreMappingPolicy)mappingPolicy;
            if(StringUtils.isNotEmpty(greMappingPolicy.getIkePolicyId())
                    && StringUtils.isNotEmpty(greMappingPolicy.getIpsecPolicyId())) {
                policyInstanceType = MappingPolicyType.GRE_OVER_IPSEC.getName();
            } else {
                policyInstanceType = MappingPolicyType.GRE.getName();
            }
        } else if(mappingPolicy instanceof IpsecMappingPolicy) {
            policyInstanceType = MappingPolicyType.IPSEC.getName();
        } else if(mappingPolicy instanceof VxlanMappingPolicy) {
            VxlanMappingPolicy vxlanMappingPolicy = (VxlanMappingPolicy)mappingPolicy;
            if(StringUtils.isNotEmpty(vxlanMappingPolicy.getIkePolicyId())
                    && StringUtils.isNotEmpty(vxlanMappingPolicy.getIpsecPolicyId())) {
                policyInstanceType = MappingPolicyType.VXLAN_OVER_IPSEC.getName();
            } else {
                policyInstanceType = MappingPolicyType.VXLAN.getName();
            }
        }

        if(!mappingPolicy.getType().equals(policyInstanceType)) {
            LOGGER.error("type:" + mappingPolicy.getType() + " doesn't match object type:" + policyInstanceType);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("type", mappingPolicy.getType());
        }

        LOGGER.info("check mappingPolicy model OK, name = " + mappingPolicy.getName());
    }

    private static void checkResourceInMappingPolicy(BaseMappingPolicy mappingPolicy) throws ServiceException {
        checkIkePolicyExist(mappingPolicy.getIkePolicyId());
        checkIpsecPolicyExists(mappingPolicy.getIpsecPolicyId());
    }

    /**
     * Check weather IkePolicy Exist.<br/>
     * 
     * @param ikePolicyUuid IkePolicy id
     * @throws ServiceException ServiceException throws when IkePolicy not exist
     * @since SDNO 0.5
     */
    private static void checkIkePolicyExist(String ikePolicyUuid) throws ServiceException {
        if(StringUtils.isEmpty(ikePolicyUuid)) {
            return;
        }

        ResultRsp<IkePolicy> ikeQueryResult = new InventoryDao<IkePolicy>().query(IkePolicy.class, ikePolicyUuid, null);
        if(!ikeQueryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(ikeQueryResult);
        }

        if(null == ikeQueryResult.getData()) {
            LOGGER.error("IkePolicy not found, uuid:" + ikePolicyUuid);
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("IkePolicy", ikePolicyUuid);
        }
    }

    /**
     * Check weather IpSecPolicy Exist.<br/>
     * 
     * @param ipsecPolicyUuid IpSecPolicy id
     * @throws ServiceException ServiceException throws when IpSecPolicy not exist
     * @since SDNO 0.5
     */
    private static void checkIpsecPolicyExists(String ipsecPolicyUuid) throws ServiceException {
        if(StringUtils.isEmpty(ipsecPolicyUuid)) {
            return;
        }

        ResultRsp<IpSecPolicy> ipsecPolicyQueryResult =
                new InventoryDao<IpSecPolicy>().query(IpSecPolicy.class, ipsecPolicyUuid, null);
        if(!ipsecPolicyQueryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(ipsecPolicyQueryResult);
        }

        if(null == ipsecPolicyQueryResult.getData()) {
            LOGGER.error("IpsecPolicy not found, uuid:" + ipsecPolicyUuid);
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("IpsecPolicy", ipsecPolicyUuid);
        }
    }
}
