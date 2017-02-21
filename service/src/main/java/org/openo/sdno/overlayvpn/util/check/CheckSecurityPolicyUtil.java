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

package org.openo.sdno.overlayvpn.util.check;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.model.ipsec.SecurityPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.GreMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.FilterDataUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SecurityPolicy Data Verify Class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 1, 2016
 */
public class CheckSecurityPolicyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckSecurityPolicyUtil.class);

    private CheckSecurityPolicyUtil() {
    }

    /**
     * Check weather SecurityPolicy data is valid.<br>
     * 
     * @param securityPolicy
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public static void check(SecurityPolicy securityPolicy) throws ServiceException {
        // Check the model parameters
        checkModelData(securityPolicy);
    }

    /**
     * Check weather IkePolicy is used.<br>
     * 
     * @param ikePolicy IkePolicy need to check
     * @throws ServiceException ServiceException throws when IkePolicy is used
     * @since SDNO 0.5
     */
    public static void checkIkePolicyIsUsed(IkePolicy ikePolicy) throws ServiceException {
        String ikeFilterForMappingPolicy =
                FilterDataUtil.getFilterData("ikePolicyId", Arrays.asList(ikePolicy.getUuid()));

        ResultRsp<List<GreMappingPolicy>> greQueryResult =
                new InventoryDao<GreMappingPolicy>().batchQuery(GreMappingPolicy.class, ikeFilterForMappingPolicy);
        if(!greQueryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(greQueryResult);
        }

        if(CollectionUtils.isNotEmpty(greQueryResult.getData())) {
            ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt("[Ike policy]" + ikePolicy.getName(),
                    "[Gre mapping policy]" + greQueryResult.getData().get(0).getName() + "...");
        }

        ResultRsp<List<IpsecMappingPolicy>> ipsecQueryResult =
                new InventoryDao<IpsecMappingPolicy>().batchQuery(IpsecMappingPolicy.class, ikeFilterForMappingPolicy);
        if(!ipsecQueryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(ipsecQueryResult);
        }

        if(CollectionUtils.isNotEmpty(ipsecQueryResult.getData())) {
            ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt("[Ike policy]" + ikePolicy.getName(),
                    "[Ipsec mapping policy]" + ipsecQueryResult.getData().get(0).getName() + "...");
        }
    }

    /**
     * Check weather IpSecPolicy is used.<br>
     * 
     * @param ipSecPolicy IpSecPolicy need to check
     * @throws ServiceException ServiceException throws when IpSecPolicy is used
     * @since SDNO 0.5
     */
    public static void checkIpsecPolicyIsUsed(IpSecPolicy ipSecPolicy) throws ServiceException {
        String ipsecFilterForMappingPolicy =
                FilterDataUtil.getFilterData("ipsecPolicyId", Arrays.asList(ipSecPolicy.getUuid()));

        ResultRsp<List<GreMappingPolicy>> greQueryResult =
                new InventoryDao<GreMappingPolicy>().batchQuery(GreMappingPolicy.class, ipsecFilterForMappingPolicy);
        if(!greQueryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(greQueryResult);
        }

        if(CollectionUtils.isNotEmpty(greQueryResult.getData())) {
            ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt("[Ipsec policy]" + ipSecPolicy.getName(),
                    "[Gre mapping policy]" + greQueryResult.getData().get(0).getName() + "...");
        }

        ResultRsp<List<IpsecMappingPolicy>> ipsecQueryResult = new InventoryDao<IpsecMappingPolicy>()
                .batchQuery(IpsecMappingPolicy.class, ipsecFilterForMappingPolicy);
        if(!ipsecQueryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(ipsecQueryResult);
        }

        if(CollectionUtils.isNotEmpty(ipsecQueryResult.getData())) {
            ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt("[Ipsec policy]" + ipSecPolicy.getName(),
                    "[Ipsec mapping policy]" + ipsecQueryResult.getData().get(0).getName() + "...");
        }
    }

    private static void checkModelData(SecurityPolicy securityPolicy) throws ServiceException {

        ValidationUtil.validateModel(securityPolicy);

        LOGGER.info("check SecurityPolicy model OK, name = " + securityPolicy.getName());
    }
}
