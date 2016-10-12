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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.invdao.LogicalTernminationPointInvDao;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.LogicalTernminationPointMO;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.consts.ValidationConsts;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.EndpointType;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyRole;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * EndPointGrp Data Verify Class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 1, 2016
 */
public class CheckEndPointGrpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckEndPointGrpUtil.class);

    private CheckEndPointGrpUtil() {
    }

    /**
     * Check weather EndpointGroup data is valid.<br>
     * 
     * @param epg EndpointGroup need t verify
     * @param tenantId tenant id
     * @return EndpointGroup Object filled all members
     * @throws ServiceException ServiceException throws when EndpointGroup is invalid
     * @since SDNO 0.5
     */
    public static ResultRsp<EndpointGroup> check(EndpointGroup epg, String tenantId) throws ServiceException {
        // Verify Model Data
        checkModelData(epg);

        checkEndpoints(epg);

        // 1. Gateway should be null now
        if(null != epg.getGateway()) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Gateway", "Not Null");
        }

        // 2.Check whether resource exists in database
        Connection parentConnection = checkResourceInEpg(epg);

        // 3. Validate the gateway
        Map<String, List<String>> filterMap = new HashMap<String, List<String>>();
        filterMap.put("tenantId", Arrays.asList(tenantId));
        filterMap.put("connectionId", Arrays.asList(epg.getConnectionId()));
        InventoryDao<EndpointGroup> epgDao = new InventoryDao<EndpointGroup>();

        // 4. Get the EPG information from database
        ResultRsp<List<EndpointGroup>> queryResult =
                epgDao.queryByFilter(EndpointGroup.class, JsonUtil.toJson(filterMap), null);
        if(!queryResult.isSuccess()) {
            return new ResultRsp<EndpointGroup>(queryResult, epg);
        }

        List<EndpointGroup> invEpgs = queryResult.getData();

        // 5. Check whether the NEID is same or not
        checkEpgWithSameNeIdInDb(epg, invEpgs);

        // 6. Check the topology and EPG role
        checkTopologyRole(epg, parentConnection, invEpgs);

        return new ResultRsp<EndpointGroup>(ErrorCode.OVERLAYVPN_SUCCESS, epg);
    }

    private static void checkModelData(EndpointGroup endpointGrp) throws ServiceException {

        ValidationUtil.validateModel(endpointGrp);

        LOGGER.info("check endpointGrp model OK, name = " + endpointGrp.getName());
    }

    /**
     * Verify EndPoints data of EndpointGroup.<br>
     * 
     * @param epg EndpointGroup need to verify
     * @throws ServiceException ServiceException throws when EndpointGroup is invalid
     * @since SDNO 0.5
     */
    private static void checkEndpoints(EndpointGroup epg) throws ServiceException {
        String endpoints = epg.getEndpoints();
        try {
            List<String> endpointList = JsonUtil.fromJson(endpoints, new TypeReference<List<String>>() {});
            if(CollectionUtils.isEmpty(endpointList)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("Endpoints", endpoints);
            }

            if(EndpointType.CIDR.getName().equals(epg.getType())) {
                checkCidrFormat(endpoints, endpointList);
            } else if(EndpointType.PORT_VLAN.getName().equals(epg.getType())) {
                List<String> portUuids = new ArrayList<>();

                Map<String, String> portToVlanMap = new HashMap<String, String>();
                checkVlanFormat(endpoints, endpointList, portUuids, portToVlanMap);

                // Check weather point exist
                checkNeLanPortExist(portUuids);
            } else {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("Type", epg.getType());
            }
            // Fill endpointList Data
            epg.setEndpointList(endpointList);
        } catch(IllegalArgumentException e) {
            LOGGER.error("content of endpoints is not a valid list of string:" + endpoints, e);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Endpoints", endpoints);
        }
    }

    /**
     * Check weather Port exist in Database.<br>
     * 
     * @param portUuids list of port id
     * @throws ServiceException ServiceException throws when port not exist or query failed
     * @since SDNO 0.5
     */
    private static void checkNeLanPortExist(List<String> portUuids) throws ServiceException {
        LogicalTernminationPointInvDao portDao = new LogicalTernminationPointInvDao();
        List<LogicalTernminationPointMO> tempTpList = portDao.getAllMO();
        if(null == tempTpList) {
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("Port", "All Ports not exist");
        }

        Map<String, LogicalTernminationPointMO> ltpMOMap = new HashMap<String, LogicalTernminationPointMO>();
        for(LogicalTernminationPointMO curMO : tempTpList) {
            ltpMOMap.put(curMO.getId(), curMO);
        }

        for(String portUuid : portUuids) {
            LogicalTernminationPointMO tempTp = ltpMOMap.get(portUuid);
            if(null == tempTp) {
                ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("Port", portUuid);
            }
        }
    }

    /**
     * Verify VLAN EndPoint Format.<br>
     * 
     * @param endpoints original end points String
     * @param endpointList List of EndPoint need to verify
     * @param portUuids list of port id
     * @param portToVlanMap port to VLAN Map
     * @throws ServiceException ServiceException throws when data is invalid
     * @since SDNO 0.5
     */
    private static void checkVlanFormat(String endpoints, List<String> endpointList, List<String> portUuids,
            Map<String, String> portToVlanMap) throws ServiceException {
        for(String endpoint : endpointList) {
            String[] uuuidAndVlans = endpoint.split("/");
            if((null == uuuidAndVlans) || (2 != uuuidAndVlans.length)) {
                LOGGER.error("do not match ***/*** format");
                ThrowOverlayVpnExcpt.throwParmaterInvalid("Endpoints", endpoints);
            }
            String portUuid = uuuidAndVlans[0];
            String vlan = uuuidAndVlans[1];

            // Verify portUUid format
            if(!UuidUtil.validate(portUuid)) {
                LOGGER.error("port uuid format err");
                ThrowOverlayVpnExcpt.throwParmaterInvalid("Endpoints", endpoints);
            }
            // Verify VLAN format
            if(!vlan.matches(ValidationConsts.INT_RANGE_REGEX)) {
                LOGGER.error("vlan format err");
                ThrowOverlayVpnExcpt.throwParmaterInvalid("Endpoints", endpoints);
            }

            portUuids.add(portUuid);
            portToVlanMap.put(portUuid, vlan);
        }
    }

    /**
     * Check CIDR EndPont Format.<br>
     * 
     * @param endpoints original end points String
     * @param endpointList List of EndPoint need to verify
     * @throws ServiceException ServiceException throws when data is invalid
     * @since SDNO 0.5
     */
    private static void checkCidrFormat(String endpoints, List<String> endpointList) throws ServiceException {
        for(String endpoint : endpointList) {
            if(!endpoint.matches(ValidationConsts.IP_MASK_REGEX)) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("Endpoints", endpoints);
            }
        }
    }

    /**
     * Check Resource of EndpointGroup is valid.<br>
     * 
     * @param epg EndpointGroup need to check
     * @return Connection this EndpointGroup belongs to
     * @throws ServiceException ServiceException throws when data is invalid
     * @since SDNO 0.5
     */
    private static Connection checkResourceInEpg(EndpointGroup epg) throws ServiceException {
        ResultRsp<Connection> connectionsResult =
                new InventoryDao<Connection>().query(Connection.class, epg.getConnectionId(), null);
        if(!connectionsResult.isValid()) {
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("Connection", epg.getConnectionId());
        }

        NetworkElementInvDao neDao = new NetworkElementInvDao();
        NetworkElementMO tempNe = neDao.query(epg.getNeId());

        if(null == tempNe) {
            String errMsg = "ne not exist: " + epg.getNeId();
            LOGGER.error(errMsg);
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("NetWorkElement", epg.getNeId());
            return null;
        }

        String deviceId = tempNe.getNativeID();
        if(!StringUtils.hasLength(deviceId)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Device ID", tempNe.getName());
        }

        epg.setDeviceId(deviceId);

        // Verify Gateway Data when it exist
        if(StringUtils.hasLength(epg.getGatewayId())) {
            ResultRsp<Gateway> gatewayRsp = new InventoryDao<Gateway>().query(Gateway.class, epg.getGatewayId(), null);
            if(!gatewayRsp.isValid()) {
                ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("Gateway", epg.getGatewayId());
            }
        }

        return connectionsResult.getData();
    }

    private static void checkEpgWithSameNeIdInDb(EndpointGroup reqEpg, List<EndpointGroup> invEpgs)
            throws ServiceException {
        String neId = reqEpg.getNeId();
        // Check whether there is the same network element EPG
        for(EndpointGroup invEpg : invEpgs) {
            if(neId.equals(invEpg.getNeId())) {
                LOGGER.error("epg of ne id:" + neId + " already exists");
                ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt("NE:" + neId, "EPG:" + invEpg.getUuid());
            }
        }
    }

    private static void checkTopologyRole(EndpointGroup reqEpg, Connection parentConnection,
            List<EndpointGroup> invEpgs) throws ServiceException {
        String reqEpgTopologyRole = reqEpg.getTopologyRole();
        if(TopologyType.FULL_MESH.getName().equals(parentConnection.getTopology())) {
            if(!TopologyRole.NONE.getName().equals(reqEpgTopologyRole)) {
                LOGGER.error("topology role:" + reqEpgTopologyRole + " not supported in full mesh");
                ThrowOverlayVpnExcpt.throwParmaterInvalid("Topology Role", reqEpgTopologyRole);
            }
        } else if(TopologyType.HUB_SPOKE.getName().equals(parentConnection.getTopology())) {
            if(TopologyRole.NONE.getName().equals(reqEpgTopologyRole)) {
                LOGGER.error("topology role:" + reqEpgTopologyRole + " not supported in hub-spoke");
                ThrowOverlayVpnExcpt.throwParmaterInvalid("Topology Role", reqEpgTopologyRole);
            }

            boolean existsHubEpg = false;
            for(EndpointGroup invEpg : invEpgs) {
                if(TopologyRole.HUB.getName().equals(invEpg.getTopologyRole())) {
                    existsHubEpg = true;
                    break;
                }
            }

            if(existsHubEpg && TopologyRole.HUB.getName().equals(reqEpgTopologyRole)) {
                LOGGER.error("connection [id]" + parentConnection.getUuid() + "[name]" + parentConnection.getName()
                        + "already has a hub epg");
                ThrowOverlayVpnExcpt.throwParmaterInvalid("Topology Role", reqEpgTopologyRole);
            }
        }
    }
}
