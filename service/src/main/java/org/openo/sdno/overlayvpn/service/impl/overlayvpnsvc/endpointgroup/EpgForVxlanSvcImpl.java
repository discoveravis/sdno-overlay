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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.endpointgroup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.brs.invdao.LogicalTernminationPointInvDao;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.LogicalTernminationPointMO;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.ModifyMaskType;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyRole;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.res.ResourcesUtil;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.UpdateVpnStatusByEle;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.gateway.GatewaySvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask.OverlayVpnTaskSvc;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.overlayvpn.OverlayVpnSvcUtil;
import org.openo.sdno.overlayvpn.util.check.CheckGatewayUtil;
import org.openo.sdno.overlayvpn.util.exception.FillOverlayVpnResRsp;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.openo.sdno.overlayvpn.util.objreflectoper.UuidAllocUtil;
import org.openo.sdno.overlayvpn.util.operation.GatewayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * The implementation of Endpoint group for Vxlan service. <br>
 *
 * @author
 * @version SDNO 0.5 2016-6-7
 */
@Service
public class EpgForVxlanSvcImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(EpgForVxlanSvcImpl.class);

    public static final String RES_VLAN_POOLNAME_OVERLAYVPN = "overlayvpnCloudGwVlan";

    public static final String RES_OVERLAYVPN_USER_LABEL = "com.huawei.netmatrix.svc.overlayvpn";

    public static final String OVERLAYVPN_CLOUDGW_LAN_NAME = "cloudGatewayLanName";

    public static final String OVERLAYVPN_CLOUDGW_ADMIN_IP = "cloudGatewayAdminIp";

    @Resource
    private OverlayVpnTaskSvc overlayVpnTaskSvc;

    @Autowired
    private GatewaySvcImpl gatewaySvc;

    @Autowired
    InventoryDao inventoryDao;

    private EpgForVxlanSvcImpl() {
    }

    /**
     * The handling for create Vxlan.<br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param reqEpg Endpoint group in the request message
     * @param totalOverlayVpn The full overlayvpn, it includes all connections
     * @param thisOverlayVpn The current overlayvpn, it only include the connection about endpoint
     *            group in the request message
     * @return The EndpointGroup object of handling
     * @throws ServiceException When handle failed.
     * @since SDNO 0.5
     */
    public ResultRsp<EndpointGroup> handleCreateForVxlan(HttpServletRequest req, HttpServletResponse resp,
            EndpointGroup reqEpg, OverlayVpn totalOverlayVpn, OverlayVpn thisOverlayVpn) throws ServiceException {
        ResultRsp<NetworkElementMO> queryCloudGwRsp = getCloudGwNeMoByCommCfg();
        if(!queryCloudGwRsp.isSuccess()) {
            return new ResultRsp<>(queryCloudGwRsp, reqEpg);
        }
        NetworkElementMO cloudGw = queryCloudGwRsp.getData();

        ResultRsp<LogicalTernminationPointMO> queryTpRsp = getTpByCloudGwAndCommCfg(cloudGw);
        if(!queryTpRsp.isSuccess()) {
            return new ResultRsp<>(queryTpRsp, reqEpg);
        }
        LogicalTernminationPointMO ltpMO = queryTpRsp.getData();

        Connection parentConnection = thisOverlayVpn.getVpnConnections().get(0);

        boolean isContainCloudGw = findCloudGwFromEpgs(parentConnection.getEndpointGroups(), cloudGw);

        if(isContainCloudGw) {
            return doAddEpgForVxlanVpn(req, resp, reqEpg, totalOverlayVpn, thisOverlayVpn);
        }

        ResultRsp<EndpointGroup> cloudGwEpgRsp =
                buildNewEpgForCloudGw(thisOverlayVpn, parentConnection, cloudGw, ltpMO);
        if(!cloudGwEpgRsp.isSuccess()) {
            LOGGER.error("build epg for cloud gateway failed. cloudGw ip = " + cloudGw.getIpAddress());
            return new ResultRsp<>(cloudGwEpgRsp, reqEpg);
        }
        EndpointGroup cloudGwEpg = cloudGwEpgRsp.getData();

        inventoryDao.insert(cloudGwEpg);

        return doCreateForVxlanVpn(req, resp, reqEpg, totalOverlayVpn, thisOverlayVpn);
    }

    private ResultRsp<EndpointGroup> doCreateForVxlanVpn(HttpServletRequest req, HttpServletResponse resp,
            EndpointGroup reqEpg, OverlayVpn totalOverlayVpn, OverlayVpn thisOverlayVpn) {
        Map<String, Boolean> tecVpnToDeployedMap = new HashMap<String, Boolean>();
        try {
            Map<String, OverlayVpn> vxlanCreateMap = new HashMap<String, OverlayVpn>();
            vxlanCreateMap.put(CommConst.VPN_TEC_VXLAN, thisOverlayVpn);
            ResultRsp<Map<String, OverlayVpn>> deployResult =
                    overlayVpnTaskSvc.creatVpnByTec(req, resp, vxlanCreateMap, tecVpnToDeployedMap);
            UpdateVpnStatusByEle.updateVpnStatusByEpgStatus(deployResult, totalOverlayVpn, reqEpg, AdminStatus.ACTIVE,
                    false, inventoryDao);
            return new ResultRsp<>(deployResult, reqEpg);
        } catch(ServiceException e) {
            LOGGER.error("failed to create vpn", e);
            return new ResultRsp<EndpointGroup>(ErrorCode.OVERLAYVPN_FAILED, reqEpg);
        }
    }

    private ResultRsp<EndpointGroup> buildNewEpgForCloudGw(OverlayVpn thisOverlayVpn, Connection parentConnection,
            NetworkElementMO cloudGw, LogicalTernminationPointMO ltpMO) throws ServiceException {
        EndpointGroup cloudGwEpg = new EndpointGroup();
        parentConnection.getEndpointGroups().add(cloudGwEpg);
        cloudGwEpg.setUuid(UuidUtils.createUuid());
        cloudGwEpg.setTenantId(thisOverlayVpn.getTenantId());
        cloudGwEpg.setName("Cloudgateway - " + cloudGw.getIpAddress());
        cloudGwEpg.setAdminStatus(AdminStatus.INACTIVE.getName());
        cloudGwEpg.setType("vlan");
        cloudGwEpg.setNeId(cloudGw.getId());
        cloudGwEpg.setDeviceId(cloudGw.getPhyNeID());
        cloudGwEpg.setConnectionId(parentConnection.getUuid());

        Gateway oGateway = createDefaultGateway(cloudGw.getId(), cloudGw.getIpAddress(), thisOverlayVpn.getTenantId());
        cloudGwEpg.setGateway(oGateway);
        cloudGwEpg.setGatewayId(oGateway.getUuid());
        if(TopologyType.HUB_SPOKE.equals(parentConnection.getTopology())) {
            cloudGwEpg.setTopologyRole(TopologyRole.HUB.getName());
        } else {
            cloudGwEpg.setTopologyRole(TopologyRole.NONE.getName());
        }

        ResultRsp<String> vlanRsp = getVlan(parentConnection, cloudGw);
        if(!vlanRsp.isSuccess()) {
            return new ResultRsp<>(vlanRsp);
        }

        List<String> endpointList = Arrays.asList(ltpMO.getId() + "/" + vlanRsp.getData());
        cloudGwEpg.setEndpoints(JsonUtil.toJson(endpointList).toString());

        // cloudGwEpg.setAdditionalInfo(new ArrayList<NvString>());
        // cloudGwEpg.getAdditionalInfo()
        // .add(new NvString(cloudGwEpg.getType(), cloudGwEpg.getEndpoints(),
        // cloudGwEpg.getUuid()));
        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, cloudGwEpg);
    }

    /**
     * Create GW for Site<br>
     * 
     * @param neId NE id of gateway
     * @param ipAddress IP address of gateway
     * @param tenantId tenant Id
     * @return Gateway created
     * @throws ServiceException throws exception if operation fails
     * @since SDNO 0.5
     */
    public Gateway createDefaultGateway(String neId, String ipAddress, String tenantId) throws ServiceException {

        // Create GW for Site
        Gateway gateway = new Gateway();

        // Set Gateway IP from SiteToDC CIDR. TODO: Default gateway
        // cannot be assumed. So Gateway needs to be explicitly set.
        gateway.setNeId(neId);
        gateway.setIpAddress(ipAddress);

        // Validate the gateway information - throws exception if NEID or Ip address is not valid
        CheckGatewayUtil.check(gateway);

        // Query and check whether the gateway exists already
        String filter =
                GatewayUtil.buildBatchQueryFilter(tenantId, null, null, gateway.getIpAddress(), gateway.getNeId());

        // If gateway already exists return
        ResultRsp<List<Gateway>> resultResp = gatewaySvc.batchQuery(null, null, tenantId, filter);
        if(null != resultResp.getData()) {
            if(resultResp.getData().size() > 1) {
                ThrowOverlayVpnExcpt.throwExceptionWithIdDesc(gateway.getIpAddress(), "Multiple gateway exists");
            }

            if(resultResp.getData().size() > 0) {
                return resultResp.getData().get(0);
            }
        }

        // Allocate UUID
        UuidAllocUtil.allocUuid(gateway);

        // Insert gateway instance into database
        ResultRsp<Gateway> resultRsp = gatewaySvc.create(null, null, gateway);

        // Throw exception if any error
        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        return resultRsp.getData();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private ResultRsp<String> getVlan(Connection parentConnection, NetworkElementMO cloudGw) throws ServiceException {
        String vlan = null;
        Map<String, Object> epgFilter = new HashMap<>();
        epgFilter.put("tenantId", Arrays.asList(parentConnection.getTenantId()));
        epgFilter.put("neId", Arrays.asList(cloudGw.getId()));
        ResultRsp<List<EndpointGroup>> queryEpgsByTenantRsp =
                inventoryDao.queryByFilter(EndpointGroup.class, JsonUtil.toJson(epgFilter).toString(), "endpoints");

        if(queryEpgsByTenantRsp.isSuccess() && CollectionUtils.isNotEmpty(queryEpgsByTenantRsp.getData())) {
            String endpoints = queryEpgsByTenantRsp.getData().get(0).getEndpoints();
            List<String> endpointList = JsonUtil.fromJson(endpoints, new TypeReference<List<String>>() {});
            String[] tempArry = endpointList.get(0).split("/");
            vlan = tempArry[1];
        }
        if(!StringUtils.hasLength(vlan)) {
            List<Long> vlanList = ResourcesUtil.requestGloabelValue(RES_VLAN_POOLNAME_OVERLAYVPN,
                    RES_OVERLAYVPN_USER_LABEL, 1, CommConst.VLAN_MIN, CommConst.VLAN_MAX);
            if(CollectionUtils.isEmpty(vlanList)) {
                return FillOverlayVpnResRsp.allocResFailed("LongID", 1);
            }
            vlan = String.valueOf(vlanList.get(0));
        }

        ResultRsp<String> retRsp = new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        retRsp.setData(vlan);
        return retRsp;
    }

    private ResultRsp<EndpointGroup> doAddEpgForVxlanVpn(HttpServletRequest req, HttpServletResponse resp,
            EndpointGroup reqEpg, OverlayVpn totalOverlayVpn, OverlayVpn thisOverlayVpn) {
        Map<String, Boolean> tecVpnToDeployedMap = new HashMap<String, Boolean>();

        OverlayVpnSvcUtil.markEpgToVpn(thisOverlayVpn, reqEpg, ModifyMaskType.ADD);
        Map<String, OverlayVpn> vxlanCreateMap = new HashMap<String, OverlayVpn>();
        vxlanCreateMap.put(CommConst.VPN_TEC_VXLAN, thisOverlayVpn);

        try {
            ResultRsp<Map<String, OverlayVpn>> deployResult =
                    overlayVpnTaskSvc.updateVpnByTec(req, resp, vxlanCreateMap, tecVpnToDeployedMap);
            UpdateVpnStatusByEle.updateVpnStatusByEpgStatus(deployResult, totalOverlayVpn, reqEpg, AdminStatus.ACTIVE,
                    true, inventoryDao);
            return new ResultRsp<EndpointGroup>(deployResult, reqEpg);
        } catch(ServiceException e) {
            LOGGER.error("HandleEpgForGre: failed to create vpn", e);
            return new ResultRsp<EndpointGroup>(ErrorCode.OVERLAYVPN_FAILED, reqEpg);
        }
    }

    private boolean findCloudGwFromEpgs(List<EndpointGroup> endpointGroups, NetworkElementMO cloudGw) {
        if(CollectionUtils.isNotEmpty(endpointGroups)) {
            for(EndpointGroup tempEpg : endpointGroups) {
                if(tempEpg.getNeId().equals(cloudGw.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private ResultRsp<LogicalTernminationPointMO> getTpByCloudGwAndCommCfg(NetworkElementMO cloudGw)
            throws ServiceException {
        String portName = "GigaBitEthernet0/0/0";// GetCommonConfig.getInstanceOf().getCommonValueOf(OVERLAYVPN_CLOUDGW_LAN_NAME);
        if(!StringUtils.hasLength(portName)) {
            LOGGER.error("failed to create vpn, do not find cloudgw lan port name");

            return FillOverlayVpnResRsp.commConfigNotExist(OVERLAYVPN_CLOUDGW_LAN_NAME);
        }

        Map<String, String> tpFilter = new HashMap<>();
        tpFilter.put("meID", cloudGw.getId());
        tpFilter.put("name", portName);
        List<LogicalTernminationPointMO> tpList = new LogicalTernminationPointInvDao().query(tpFilter);
        if(CollectionUtils.isEmpty(tpList) || (tpList.size() > 1)) {
            LOGGER.error("query tp of cloudGw failed. Do not find or size > 2. filter = " + tpFilter);

            return FillOverlayVpnResRsp.inventoryResNotExist("Port", portName);
        }
        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, tpList.get(0));
    }

    @SuppressWarnings("unchecked")
    private ResultRsp<NetworkElementMO> getCloudGwNeMoByCommCfg() throws ServiceException {
        String ipAddress = "10.0.0.1";// GetCommonConfig.getInstanceOf().getCommonValueOf(OVERLAYVPN_CLOUDGW_ADMIN_IP);
        if(!StringUtils.hasLength(ipAddress)) {
            LOGGER.error("failed to create vpn, do not find cloudgw");

            return FillOverlayVpnResRsp.commConfigNotExist(OVERLAYVPN_CLOUDGW_ADMIN_IP);
        }

        Map<String, String> neFilter = new HashMap<>();
        neFilter.put("ipAddress", ipAddress);
        List<NetworkElementMO> cloudGwList = new NetworkElementInvDao().query(neFilter);
        if(CollectionUtils.isEmpty(cloudGwList) || (cloudGwList.size() > 1)) {
            LOGGER.error("query cloudGw failed. Do not find or size > 2. filter = " + neFilter);
            return FillOverlayVpnResRsp.inventoryResNotExist("NetworkElement", ipAddress);
        }
        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, cloudGwList.get(0));
    }

    public void setOverlayVpnTaskSvc(OverlayVpnTaskSvc overlayVpnTaskSvc) {
        this.overlayVpnTaskSvc = overlayVpnTaskSvc;
    }

}
