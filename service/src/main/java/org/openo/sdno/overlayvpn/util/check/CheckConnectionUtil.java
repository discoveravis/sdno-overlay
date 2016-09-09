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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.common.enums.TechnologyType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.BaseMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.ValidationUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connection Data Verify Class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 05-May-2016
 */
public class CheckConnectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckConnectionUtil.class);

    private CheckConnectionUtil() {
    }

    /**
     * Check weather Connection data is valid.<br>
     * 
     * @param connection Connection need to verify
     * @throws ServiceException ServiceException throws when connection is invalid
     * @since SDNO 0.5
     */
    public static void check(Connection connection) throws ServiceException {
        // 0.Model data validation
        checkModelData(connection);

        // Validate End point groups
        // 1.Validate EPG UUID
        if(!CollectionUtils.isEmpty(connection.getEpgIds())) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("EndpointGroup UUIDs", "Not Null");
        }
        // 2.Validate End point groups
        if(!CollectionUtils.isEmpty(connection.getEndpointGroups())) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("EndpointGroups", "Not Null");
        }

        // 3.MappingPolicy should be null
        checkMappingPolicyMatchesConn(connection);

        checkResourceInConn(connection);
    }

    /**
     * Check weather the mappingPloicy matches to connection.<br>
     * 
     * @param technologyType technology type of connection
     * @param mappingPolicyType type of mappingPolicy
     * @throws ServiceException ServiceException throws when not matches
     * @since SDNO 0.5
     */
    private static void validateTypeConsistency(String technologyType, String mappingPolicyType)
            throws ServiceException {
        if(!technologyType.equals(mappingPolicyType)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Mapping Policy Type", mappingPolicyType);
        }
    }

    /**
     * Check weather MappingPolicy matches connection.<br>
     * 
     * @param connection
     * @throws ServiceException
     * @since SDNO 0.5
     */
    private static void checkMappingPolicyMatchesConn(Connection connection) throws ServiceException {
        mappingPolicyNullCheck(connection);

        String technologyType = connection.getTechnology();
        String mappingPolicyId = connection.getMappingPolicyId();
        if(StringUtils.isEmpty(mappingPolicyId)
                && (TechnologyType.GRE_OVER_IPSEC.getName().equals(technologyType)
                        || TechnologyType.isIpsec(technologyType) || TechnologyType.isVxlanRelated(technologyType))) {
            ThrowOverlayVpnExcpt.throwNotHavingPolicyIdAsParmaterInvalid(connection.getName());
        }
    }

    private static void mappingPolicyNullCheck(Connection connection) throws ServiceException {
        if((null != connection.getIpsecMappingPolicy()) || (null != connection.getGreMappingPolicy())
                || (null != connection.getVxlanMappingPolicy())) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Mapping Policy", "Not Null");
        }
    }

    /**
     * Check weather connection data is valid.<br>
     * 
     * @param connection connection data need to validate
     * @throws ServiceException ServiceException throws when connection is invalid
     * @since SDNO 0.5
     */
    private static void checkModelData(Connection connection) throws ServiceException {
        // model validate
        ValidationUtil.validateModel(connection);
        LOGGER.info("Check connection model OK, name = " + connection.getName());
    }

    /**
     * Check weather connection resource exist.<br>
     * 
     * @param conn Connection Object
     * @throws ServiceException ServiceException throws when resource not exist
     * @since SDNO 0.5
     */
    private static void checkResourceInConn(Connection conn) throws ServiceException {
        // Check weather OverlayVpn exist
        ResultRsp<OverlayVpn> overlayVpnRsp =
                new InventoryDao<OverlayVpn>().query(OverlayVpn.class, conn.getCompositeVpnId(), null);
        if(!overlayVpnRsp.isValid()) {
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("OverlayVpn", conn.getCompositeVpnId());
        }

        BaseMappingPolicy mappingPolicy = getMappingPolicy(conn.getTechnology(), conn.getMappingPolicyId());

        if(null != mappingPolicy) {
            validateTypeConsistency(conn.getTechnology(), mappingPolicy.getType());
        }
    }

    /**
     * Get MappingPolicy.<br>
     * 
     * @param technologyType technology type of connection
     * @param mappingPolicyId mapping policy id
     * @return MappingPolicy Object
     * @throws ServiceException ServiceException throws when MappingPolicy not exist
     * @since SDNO 0.5
     */
    private static BaseMappingPolicy getMappingPolicy(String technologyType, String mappingPolicyId)
            throws ServiceException {
        if(StringUtils.isEmpty(mappingPolicyId)) {
            return null;
        }

        ResultRsp<? extends BaseMappingPolicy> mappingPolicyRsp = new ResultRsp<BaseMappingPolicy>();
        // Check weather MappingPolicy exist
        if(TechnologyType.isIpsec(technologyType)) {
            mappingPolicyRsp =
                    new InventoryDao<IpsecMappingPolicy>().query(IpsecMappingPolicy.class, mappingPolicyId, null);
        } else if(TechnologyType.isVxlanRelated(technologyType)) {
            mappingPolicyRsp =
                    new InventoryDao<VxlanMappingPolicy>().query(VxlanMappingPolicy.class, mappingPolicyId, null);
        }

        if(!mappingPolicyRsp.isValid()) {
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("Mapping Policy", mappingPolicyId);
        }

        return mappingPolicyRsp.getData();
    }

}
