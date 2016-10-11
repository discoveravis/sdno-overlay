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
import java.util.Iterator;
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
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.BaseMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.openo.sdno.resource.ResourceUtil;
import org.springframework.util.StringUtils;

/**
 * Utility class of OverlayVpn Complex DAO.<br>
 * 
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
public class OverlayVpnComplexDaoUtil {

    private OverlayVpnComplexDaoUtil() {
    }

    /**
     * Construct Complex Overlay Object.<br>
     * OverlayVpn has many connections and Connection has many EndpointGroups
     * 
     * @param totalVpns
     *            list of OverlayVpn
     * @param connList
     *            list of Connection
     * @param epgList
     *            list of EndpointGroup
     * @param isNeedMappingPolicy
     *            need MappingPolicy or not
     * @param inventoryDao inventory data object
     * @throws ServiceException
     *             when failed
     * @since SDNO 0.5
     */
    public static void buildComplexOverlayVpn(List<OverlayVpn> totalVpns, List<Connection> connList,
            List<EndpointGroup> epgList, boolean isNeedMappingPolicy, InventoryDao inventoryDao)
            throws ServiceException {
        buildComplexEpgs(epgList, inventoryDao);

        buildComplexConns(connList, epgList, isNeedMappingPolicy, inventoryDao);

        Map<String, List<Connection>> vpnUuidToConnsMap = buildVpnUuidToConnsMap(connList);
        Iterator<OverlayVpn> totalVpnItor = totalVpns.iterator();
        while(totalVpnItor.hasNext()) {
            OverlayVpn overlayVpn = totalVpnItor.next();
            List<Connection> connections = vpnUuidToConnsMap.get(overlayVpn.getUuid());
            if(CollectionUtils.isEmpty(connections)) {
                totalVpnItor.remove();
                continue;
            }
            overlayVpn.setVpnConnections(connections);
        }
    }

    /**
     * Throw Data Miss Exception.<br>
     * 
     * @param resource
     *            exception resource
     * @throws ServiceException
     *             when failed
     * @since SDNO 0.5
     */
    public static void throwCloudVpnDataMissExpt(String resource) throws ServiceException {
        ResultRsp<OverlayVpn> retRsp = new ResultRsp<>(ErrorCode.OVERLAYVPNSVC_CONN_NOT_COMPLETE);
        retRsp.setDescArg(ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.desc"));

        String msg = "";
        if(CommConst.LANGUAGE_ZH_CN.equals(SysEnvironment.getLanguage())) {
            msg = resource + ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.reason");
        } else {
            msg = resource + " " + ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.reason");
        }

        retRsp.setReasonArg(msg);
        retRsp.setDetailArg(msg);
        retRsp.setDetailArg(ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.advice") + resource);

        SvcExcptUtil.throwSvcExptionByResultRsp(retRsp);
    }

    /**
     * Build Whole Connection Data.<br>
     * 
     * @param totalConnections
     *            Connections need to fill data
     * @param totalEpgs
     *            all EndpointGroup of these connections
     * @param isNeedMappingPolicy
     *            need to fill MappingPolicy or not
     * @throws ServiceException
     *             throws ServiceException if operate failed
     * @since SDNO 0.5
     */
    private static void buildComplexConns(List<Connection> totalConnections, List<EndpointGroup> totalEpgs,
            boolean isNeedMappingPolicy, InventoryDao invDao) throws ServiceException {
        Map<String, List<EndpointGroup>> connUuidToEpgsMap = buildConnUuidToEpgsMap(totalEpgs);

        List<BaseMappingPolicy> totalMappingPolicies = new ArrayList<BaseMappingPolicy>();
        Map<String, BaseMappingPolicy> uuidToMappingPolicyMap = new ConcurrentHashMap<String, BaseMappingPolicy>();

        if(isNeedMappingPolicy) {
            // Query all MappingPolicies of connections
            totalMappingPolicies = MappingPolicyUtil.getTotalMappingPoliciesFromConn(totalConnections, invDao);
            uuidToMappingPolicyMap = MappingPolicyUtil.buildUuidToMappingPolicyMap(totalMappingPolicies);
        }

        Iterator<Connection> totalConnItor = totalConnections.iterator();
        while(totalConnItor.hasNext()) {
            Connection connection = totalConnItor.next();
            List<EndpointGroup> endpointGrps = connUuidToEpgsMap.get(connection.getUuid());
            if(CollectionUtils.isEmpty(endpointGrps)) {
                totalConnItor.remove();
                continue;
            }

            // Fill EndpointGroups of Connection
            connection.setEndpointGroups(endpointGrps);
            // Fill MappingPolicy of Connection
            String mappingPolicyUuid = connection.getMappingPolicyId();
            if((isNeedMappingPolicy) && (StringUtils.hasLength(mappingPolicyUuid))
                    && (uuidToMappingPolicyMap.containsKey(mappingPolicyUuid))) {
                fillMappingPolicy(uuidToMappingPolicyMap, connection, mappingPolicyUuid);
            }
        }
    }

    private static void fillMappingPolicy(Map<String, BaseMappingPolicy> uuidToMappingPolicyMap, Connection connection,
            String mappingPolicyUuid) {
        BaseMappingPolicy mappingPolicy = uuidToMappingPolicyMap.get(mappingPolicyUuid);
        String mappingPolicyType = mappingPolicy.getType();
        if(MappingPolicyType.isIpsec(mappingPolicyType)) {
            connection.setIpsecMappingPolicy((IpsecMappingPolicy)mappingPolicy);
        } else if(MappingPolicyType.isVxlanRelated(mappingPolicyType)) {
            connection.setVxlanMappingPolicy((VxlanMappingPolicy)mappingPolicy);
        }
    }

    private static Map<String, List<Connection>> buildVpnUuidToConnsMap(List<Connection> totalConns) {
        Map<String, List<Connection>> vpnUuidToConnsMap = new ConcurrentHashMap<String, List<Connection>>();
        for(Connection connection : totalConns) {
            String vpnUuid = connection.getCompositeVpnId();
            if(!vpnUuidToConnsMap.containsKey(vpnUuid)) {
                vpnUuidToConnsMap.put(vpnUuid, new ArrayList<Connection>());
            }
            vpnUuidToConnsMap.get(vpnUuid).add(connection);
        }

        return vpnUuidToConnsMap;
    }

    /**
     * Fill members of EndpointGroup.<br>
     * 
     * @param totalEpgs
     *            list of EndpointGroup
     * @throws ServiceException
     *             when failed
     * @since SDNO 0.5
     */
    private static void buildComplexEpgs(List<EndpointGroup> totalEpgs, InventoryDao inventoryDao)
            throws ServiceException {
        // Query all Gateways
        Set<String> totalGwUuids = new HashSet<>();
        for(EndpointGroup epg : totalEpgs) {
            String gwUuid = epg.getGatewayId();
            if(StringUtils.hasLength(gwUuid)) {
                totalGwUuids.add(gwUuid);
            }
        }

        // if no one gateway,then return
        if(CollectionUtils.isEmpty(totalGwUuids)) {
            return;
        }

        List<Gateway> totalGws =
                (List<Gateway>)inventoryDao.batchQuery(Gateway.class, new ArrayList<>(totalGwUuids)).getData();
        Map<String, Gateway> uuidToGwMap = buildGwUuidToGwMap(totalGws);

        // Fill GateWay of EndGroup
        for(EndpointGroup tempEpg : totalEpgs) {
            String gwUuid = tempEpg.getGatewayId();
            if(StringUtils.hasLength(gwUuid) && uuidToGwMap.containsKey(gwUuid)) {
                tempEpg.setGateway(uuidToGwMap.get(gwUuid));
            }
        }
    }

    /**
     * Build Connection to EndpointGroups map.<br>
     * 
     * @param epgList
     *            list of EndpointGroup
     * @return Map of Connection to EndpointGroups key:connection id
     *         value:EndpointGroups of this connection
     * @since SDNO 0.5
     */
    private static Map<String, List<EndpointGroup>> buildConnUuidToEpgsMap(List<EndpointGroup> epgList) {
        // construct map instance
        Map<String, List<EndpointGroup>> connUuidToEpgListMap = new HashMap<>();
        for(EndpointGroup tempEpg : epgList) {
            String connUuid = tempEpg.getConnectionId();
            if(!connUuidToEpgListMap.containsKey(connUuid)) {
                connUuidToEpgListMap.put(connUuid, new ArrayList<EndpointGroup>());
            }
            connUuidToEpgListMap.get(connUuid).add(tempEpg);
        }

        return connUuidToEpgListMap;
    }

    /**
     * Build Gateway id to Gateway map.<br>
     * 
     * @param totalGwList
     *            list of gateway
     * @return map of Gateway id to Gateway
     * @since SDNO 0.5
     */
    private static Map<String, Gateway> buildGwUuidToGwMap(List<Gateway> totalGwList) {
        // construct map instance
        Map<String, Gateway> uuidToGwMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(totalGwList)) {
            for(Gateway gw : totalGwList) {
                if(uuidToGwMap.containsKey(gw.getUuid())) {
                    continue;
                }

                uuidToGwMap.put(gw.getUuid(), gw);
            }
        }
        return uuidToGwMap;
    }

}
