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

package org.openo.sdno.overlayvpn.dao.overlayvpn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.SysEnvironment;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.MappingPolicyType;
import org.openo.sdno.overlayvpn.model.common.enums.TechnologyType;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.BaseMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.openo.sdno.overlayvpn.util.FilterDataUtil;
import org.openo.sdno.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Utility class of Mapping Policy.<br>
 * 
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
public class MappingPolicyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MappingPolicyUtil.class);

    private MappingPolicyUtil() {

    }

    /**
     * Get All Mapping policies from Connection.<br>
     * 
     * @param totalConns
     *            all connections
     * @param invDao Inventory data object
     * @return Mapping policies
     * @throws ServiceException
     *             service exception occurs
     * @since SDNO 0.5
     */
    public static List<BaseMappingPolicy> getTotalMappingPoliciesFromConn(List<Connection> totalConns,
            InventoryDao invDao) throws ServiceException {
        List<BaseMappingPolicy> totalMappingPolicies = new ArrayList<>();

        List<String> totalMappingPolicyUuidsForVxlan = new ArrayList<String>();
        List<String> totalMappingPolicyUuidsForIpsec = new ArrayList<String>();

        for(Connection connection : totalConns) {
            if(StringUtils.hasLength(connection.getMappingPolicyId())) {
                String technologyType = connection.getTechnology();
                if(TechnologyType.isVxlanRelated(technologyType)) {
                    totalMappingPolicyUuidsForVxlan.add(connection.getMappingPolicyId());
                } else if(TechnologyType.isIpsec(technologyType)) {
                    totalMappingPolicyUuidsForIpsec.add(connection.getMappingPolicyId());
                }
            }
        }

        // query VXLAN Mapping Policies
        if(CollectionUtils.isNotEmpty(totalMappingPolicyUuidsForVxlan)) {
            String totalMappingPolicyFilterForVxlan =
                    FilterDataUtil.getFilterData("uuid", totalMappingPolicyUuidsForVxlan);
            List<VxlanMappingPolicy> vxlanMappingPolicies = (List<VxlanMappingPolicy>)invDao
                    .batchQuery(VxlanMappingPolicy.class, totalMappingPolicyFilterForVxlan).getData();

            totalMappingPolicies.addAll(vxlanMappingPolicies);
        }

        // query IPSec Mapping Policies
        if(CollectionUtils.isNotEmpty(totalMappingPolicyUuidsForIpsec)) {
            String totalMappingPolicyFilterForIpsec =
                    FilterDataUtil.getFilterData("uuid", totalMappingPolicyUuidsForIpsec);
            List<IpsecMappingPolicy> ipsecMappingPolicies = (List<IpsecMappingPolicy>)invDao
                    .batchQuery(IpsecMappingPolicy.class, totalMappingPolicyFilterForIpsec).getData();

            totalMappingPolicies.addAll(ipsecMappingPolicies);
        }

        // Fill IkePolicy and IpsecPolicy by Mapping Policy
        buildComplexMappingPolicies(totalMappingPolicies, invDao);

        return totalMappingPolicies;
    }

    /**
     * Build map of MappingPolicy id to MappingPolicy.<br>
     * 
     * @param totalMappingPolicies
     *            List of MappingPolicy
     * @return map of MappingPolicy id to MappingPolicy
     * @since SDNO 0.5
     */
    public static Map<String, BaseMappingPolicy>
            buildUuidToMappingPolicyMap(List<BaseMappingPolicy> totalMappingPolicies) {
        Map<String, BaseMappingPolicy> uuidToMappingPolicyMap = new ConcurrentHashMap<>();
        for(BaseMappingPolicy mappingPolicy : totalMappingPolicies) {
            uuidToMappingPolicyMap.put(mappingPolicy.getUuid(), mappingPolicy);
        }

        return uuidToMappingPolicyMap;
    }

    /**
     * Fill Member of MappingPolicy.<br>
     * 
     * @param totalMappingPolicies
     *            all Mapping Policies
     * @throws ServiceException
     *             Service Exception
     * @since SDNO 0.5
     */
    private static void buildComplexMappingPolicies(List<BaseMappingPolicy> totalMappingPolicies, InventoryDao invDao)
            throws ServiceException {
        if(CollectionUtils.isEmpty(totalMappingPolicies)) {
            return;
        }

        Set<String> totalIkePolicyUuids = new HashSet<String>();
        Set<String> totalIpsecPolicyUuids = new HashSet<String>();
        fillSecuPolicyUuidsFromMappingPolicy(totalMappingPolicies, totalIkePolicyUuids, totalIpsecPolicyUuids);

        Map<String, IkePolicy> uuidToIkePolicyMap = buildUuidToIkePolicyMap(totalIkePolicyUuids, invDao);
        Map<String, IpSecPolicy> uuidToIpsecPolicyMap = buildUuidToIpsecPolicyMap(totalIpsecPolicyUuids, invDao);

        // Fill IkePolicy and IpsecPolicy
        for(BaseMappingPolicy mappingPolicy : totalMappingPolicies) {
            String type = mappingPolicy.getType();
            if(MappingPolicyType.isIpsec(type)) {
                fillIpsecPolicy(uuidToIkePolicyMap, uuidToIpsecPolicyMap, mappingPolicy);
            } else if(MappingPolicyType.VXLAN_OVER_IPSEC.getName().equals(type)) {
                VxlanMappingPolicy vxlanMappingPolicy = (VxlanMappingPolicy)mappingPolicy;
                fillMappingPolicyData(uuidToIkePolicyMap, uuidToIpsecPolicyMap, vxlanMappingPolicy);
            }
        }
    }

    private static void fillMappingPolicyData(Map<String, IkePolicy> uuidToIkePolicyMap,
            Map<String, IpSecPolicy> uuidToIpsecPolicyMap, VxlanMappingPolicy vxlanMappingPolicy) {
        vxlanMappingPolicy.setIkePolicy(uuidToIkePolicyMap.get(vxlanMappingPolicy.getIkePolicyId()));
        vxlanMappingPolicy.setIpSecPolicy(uuidToIpsecPolicyMap.get(vxlanMappingPolicy.getIpsecPolicyId()));
    }

    private static void fillIpsecPolicy(Map<String, IkePolicy> uuidToIkePolicyMap,
            Map<String, IpSecPolicy> uuidToIpsecPolicyMap, BaseMappingPolicy mappingPolicy) {
        IpsecMappingPolicy ipsecMappingPolicy = (IpsecMappingPolicy)mappingPolicy;
        ipsecMappingPolicy.setIkePolicy(uuidToIkePolicyMap.get(ipsecMappingPolicy.getIkePolicyId()));
        ipsecMappingPolicy.setIpSecPolicy(uuidToIpsecPolicyMap.get(ipsecMappingPolicy.getIpsecPolicyId()));
    }

    /**
     * Get IkePolicy id and IpSec id.<br>
     * 
     * @param totalMappingPolicies
     *            all Mapping Policies
     * @param totalIkePolicyUuids
     *            IkePolicy id List returned
     * @param totalIpsecPolicyUuids
     *            IpSec id list returned
     * @since SDNO 0.5
     */
    private static void fillSecuPolicyUuidsFromMappingPolicy(List<BaseMappingPolicy> totalMappingPolicies,
            Set<String> totalIkePolicyUuids, Set<String> totalIpsecPolicyUuids) {
        for(BaseMappingPolicy mappingPolicy : totalMappingPolicies) {
            String mappingPolicyType = mappingPolicy.getType();
            if(MappingPolicyType.IPSEC.getName().equals(mappingPolicyType)) {
                totalIkePolicyUuids.add(((IpsecMappingPolicy)mappingPolicy).getIkePolicyId());
                totalIpsecPolicyUuids.add(((IpsecMappingPolicy)mappingPolicy).getIpsecPolicyId());
            } else if(MappingPolicyType.VXLAN_OVER_IPSEC.getName().equals(mappingPolicyType)) {
                totalIkePolicyUuids.add(((VxlanMappingPolicy)mappingPolicy).getIkePolicyId());
                totalIpsecPolicyUuids.add(((VxlanMappingPolicy)mappingPolicy).getIpsecPolicyId());
            }
        }
    }

    /**
     * Get IkePolicy Object Map by id.<br>
     * 
     * @param totalIkePolicyUuids
     *            IkePolicy id list
     * @return IkePolicy Object Map. key: IkePolicy id value:IkePolicy Object
     * @throws ServiceException
     *             Service Exception occurs
     * @since SDNO 0.5
     */
    private static Map<String, IkePolicy> buildUuidToIkePolicyMap(Set<String> totalIkePolicyUuids, InventoryDao invDao)
            throws ServiceException {
        Map<String, IkePolicy> uuidToIkePolicyMap = new HashMap<>();

        if(CollectionUtils.isEmpty(totalIkePolicyUuids)) {
            return uuidToIkePolicyMap;
        }

        String totalIkePolicyFilter = FilterDataUtil.getFilterData("uuid", totalIkePolicyUuids);
        List<IkePolicy> totalIkePolicies =
                (List<IkePolicy>)invDao.queryByFilter(IkePolicy.class, totalIkePolicyFilter, null).getData();
        if(CollectionUtils.isEmpty(totalIkePolicies)) {
            LOGGER.error("IkePolicy not found. filter = " + totalIkePolicyFilter);
            throwCloudVpnDataMissExpt("IkePolicy");
        }

        for(IkePolicy ikePolicy : totalIkePolicies) {
            uuidToIkePolicyMap.put(ikePolicy.getUuid(), ikePolicy);
        }
        return uuidToIkePolicyMap;
    }

    /**
     * Get IpSecPolicy Object Map by id.<br>
     * 
     * @param totalIpsecPolicyUuids
     *            IpSecPolicy id list
     * @return IkePolicy Object Map. key: IpSecPolicy id value:IpSecPolicy
     *         Object
     * @throws ServiceException
     *             Service Exception occurs
     * @since SDNO 0.5
     */
    private static Map<String, IpSecPolicy> buildUuidToIpsecPolicyMap(Set<String> totalIpsecPolicyUuids,
            InventoryDao invDao) throws ServiceException {
        Map<String, IpSecPolicy> uuidToIpsecPolicyMap = new HashMap<>();

        if(CollectionUtils.isEmpty(totalIpsecPolicyUuids)) {
            return uuidToIpsecPolicyMap;
        }

        String totalIpsecPolicyFilter = FilterDataUtil.getFilterData("uuid", totalIpsecPolicyUuids);
        List<IpSecPolicy> totalIpSecPolicies =
                (List<IpSecPolicy>)invDao.queryByFilter(IpSecPolicy.class, totalIpsecPolicyFilter, null).getData();
        if(CollectionUtils.isEmpty(totalIpSecPolicies)) {
            LOGGER.error("IpSecPolicy not found. filter = " + totalIpsecPolicyFilter);
            throwCloudVpnDataMissExpt("IpSecPolicy");
        }

        for(IpSecPolicy ipsecPolicy : totalIpSecPolicies) {
            uuidToIpsecPolicyMap.put(ipsecPolicy.getUuid(), ipsecPolicy);
        }

        return uuidToIpsecPolicyMap;
    }

    private static void throwCloudVpnDataMissExpt(String resource) throws ServiceException {
        ResultRsp<OverlayVpn> retRsp = new ResultRsp<>(ErrorCode.OVERLAYVPNSVC_CONN_NOT_COMPLETE);
        retRsp.setDescArg(ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.desc"));
        if(CommConst.LANGUAGE_ZH_CN.equals(SysEnvironment.getLanguage())) {
            String msg = resource + ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.reason");
            retRsp.setReasonArg(msg);
            retRsp.setDetailArg(msg);
            retRsp.setDetailArg(ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.advice") + resource);
        } else {
            String msg = resource + " " + ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.reason");
            retRsp.setReasonArg(msg);
            retRsp.setDetailArg(msg);
            retRsp.setDetailArg(ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.advice") + resource);
        }
        SvcExcptUtil.throwSvcExptionByResultRsp(retRsp);
    }
}
