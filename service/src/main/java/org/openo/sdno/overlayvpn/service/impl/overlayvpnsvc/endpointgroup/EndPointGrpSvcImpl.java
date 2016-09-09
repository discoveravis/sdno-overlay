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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.dao.overlayvpn.OverlayVpnComplexDao;
import org.openo.sdno.overlayvpn.enums.OperaMethType;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.errorcode.ErrorCodeInfo;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.ModifyMaskType;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.UpdateVpnStatus;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.UpdateVpnStatusByEle;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.connection.ConnectionSvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask.MutilTecVpnByTree;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask.OverlayVpnTaskSvc;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.overlayvpn.OverlayVpnSvcUtil;
import org.openo.sdno.overlayvpn.service.inf.overlayvpn.IEndPointGrp;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.openo.sdno.overlayvpn.util.operation.ConnectionUtil;
import org.openo.sdno.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Endpoint group model implementation. <br>
 *
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */

public class EndPointGrpSvcImpl implements IEndPointGrp {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndPointGrpSvcImpl.class);

    @Resource
    private OverlayVpnComplexDao overlayVpnComplexDao;

    @Resource
    private OverlayVpnTaskSvc overlayVpnTaskSvc;

    @Resource
    private UpdateVpnStatus updateVpnStatus;

    @Resource
    private ConnectionSvcImpl connectionSvcImpl;

    @Autowired
    private InventoryDao<EndpointGroup> inventoryDao;

    /**
     * Create endpoint group in INACTIVE state. Build EPG for the technology type requested. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param reqEpg Endpoint group in the request message
     * @return returns created EndpointGroup
     * @throws ServiceException When create endpoint group failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<EndpointGroup> create(HttpServletRequest req, HttpServletResponse resp, EndpointGroup reqEpg)
            throws ServiceException {
        LOGGER.info("begin to create epg: " + reqEpg.getName());

        reqEpg.setAdminStatus(AdminStatus.INACTIVE.getName());
        inventoryDao.insert(reqEpg);

        return new ResultRsp<EndpointGroup>(ErrorCode.OVERLAYVPN_SUCCESS, reqEpg);
    }

    /**
     * Implementation of batch creation of EPG's using one request. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param reqEpgs Requested EndpointGroup list
     * @return returns list of created EndpointGroup
     * @throws ServiceException When create endpoint group failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<List<EndpointGroup>> batchCreate(HttpServletRequest req, HttpServletResponse resp,
            List<EndpointGroup> reqEpgs) throws ServiceException {
        LOGGER.info("begin to batchCreate epg: " + reqEpgs.size());

        ResultRsp<List<EndpointGroup>> totalRsp = new ResultRsp<List<EndpointGroup>>(ErrorCode.OVERLAYVPN_SUCCESS);
        List<ErrorCodeInfo> errorCodeInfoLst = new ArrayList<ErrorCodeInfo>();

        // Call create Endpointgroup service implmentation in loop
        for(EndpointGroup reqEpg : reqEpgs) {
            ResultRsp<EndpointGroup> rsp = this.create(req, resp, reqEpg);

            if(!rsp.isSuccess()) {
                ErrorCodeInfo smallErrorCodeInfo = new ErrorCodeInfo(rsp.getErrorCode());
                smallErrorCodeInfo.setObject(reqEpg);
                errorCodeInfoLst.add(smallErrorCodeInfo);
            }
        }

        if(CollectionUtils.isNotEmpty(errorCodeInfoLst)) {
            totalRsp.setErrorCode(ErrorCode.OVERLAYVPN_FAILED);
            totalRsp.setSmallErrorCodeList(new ArrayList<ErrorCodeInfo>());
            totalRsp.getSmallErrorCodeList().addAll(errorCodeInfoLst);
        }

        return totalRsp;
    }

    /**
     * Query an endpoint group using UUID and tenant id. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param epgUuid EndpointGroup UUID
     * @param tenantId Tenant id of the request
     * @return returns the endpoint group available in DB
     * @throws ServiceException When query failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<EndpointGroup> query(HttpServletRequest req, HttpServletResponse resp, String epgUuid,
            String tenantId) throws ServiceException {
        LOGGER.info("begin to query epg by uuid:" + epgUuid);

        ResultRsp<EndpointGroup> queryResult = inventoryDao.query(EndpointGroup.class, epgUuid, tenantId);
        ResultRsp<EndpointGroup> retResult = new ResultRsp<>(queryResult);
        if(queryResult.isSuccess()) {
            retResult.setData(queryResult.getData());
        } else {
            LOGGER.error("failed to query epg: " + retResult.toString());
        }
        return retResult;
    }

    /**
     * Query endpoint groups in batch. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param tenantId Tenant id of the request
     * @param filter Query filter
     * @return The endpoint group list
     * @throws ServiceException When query failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<List<EndpointGroup>> batchQuery(HttpServletRequest req, HttpServletResponse resp, String tenantId,
            String filter) throws ServiceException {
        LOGGER.info("begin to query epgs by filter: " + filter);
        return inventoryDao.batchQuery(EndpointGroup.class, filter);
    }

    /**
     * Update an endpoint group. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param newEpg New endpoint group model
     * @param invEpg Original endpoint group model
     * @return The ResultRsp object
     * @throws ServiceException When update failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<EndpointGroup> update(HttpServletRequest req, HttpServletResponse resp, EndpointGroup newEpg,
            EndpointGroup invEpg) throws ServiceException {
        String oldAdminStatus = invEpg.getAdminStatus();
        String newAdminStatus = newEpg.getAdminStatus();

        // only update DB if the admin status hasn't changed
        if(newAdminStatus.equals(oldAdminStatus)) {
            return inventoryDao.update(newEpg, null);
        }

        // deploy or undeploy by admin status
        ResultRsp<EndpointGroup> operRsp = new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        if(AdminStatus.ACTIVE.getName().equals(newAdminStatus)) {
            operRsp = deploy(req, resp, newEpg);
        } else {
            operRsp = undeploy(req, resp, newEpg);
        }

        return new ResultRsp<EndpointGroup>(operRsp, newEpg);
    }

    /**
     * Delete an endpoint group. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param endpointGrp Endpoint group to be deleted
     * @return The ResultRsp object
     * @throws ServiceException When delete failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<String> delete(HttpServletRequest req, HttpServletResponse resp, EndpointGroup endpointGrp)
            throws ServiceException {
        ResultRsp<String> totalResult = new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS);
        List<ErrorCodeInfo> smallErrorList = new ArrayList<ErrorCodeInfo>();

        if(!AdminStatus.INACTIVE.getName().equals(endpointGrp.getAdminStatus())) {
            totalResult.setErrorCode(ErrorCode.VPN_STATUS_NOT_INACTIVE);
            ErrorCodeInfo errorCodeInfo = new ErrorCodeInfo(ErrorCode.VPN_STATUS_NOT_INACTIVE);
            errorCodeInfo.setDescArg(ResourceUtil.getMessage("vpn.status.not_inactive.desc"));
            String msg = ResourceUtil.getMessage("vpn.status.not_inactive.reason");
            errorCodeInfo.setReasonArg(msg);
            errorCodeInfo.setDetailArg(msg);
            totalResult.setSmallErrorCodeList(Arrays.asList(errorCodeInfo));
            return totalResult;
        }

        ResultRsp<EndpointGroup> resultRsp =
                new ResultRsp<>(inventoryDao.delete(EndpointGroup.class, endpointGrp.getUuid()));
        if(!resultRsp.isSuccess()) {
            LOGGER.error("delete: delete epg from overlayvpn database failed, epgUuid = " + endpointGrp.getUuid());
            ErrorCodeInfo errorCodeInfo = new ErrorCodeInfo(resultRsp.getErrorCode());
            errorCodeInfo.setDescArg(ResourceUtil.getMessage("vpn.delete.delete_fail.desc"));
            errorCodeInfo.setObjectName(endpointGrp.getName());
            smallErrorList.add(errorCodeInfo);
        }

        ResultRsp<Connection> connRsp =
                connectionSvcImpl.query(req, resp, endpointGrp.getConnectionId(), endpointGrp.getTenantId());
        Connection conn = connRsp.getData();
        if(null == conn) {
            LOGGER.info("query connection return null.");
            ErrorCodeInfo errorCodeInfo = new ErrorCodeInfo(connRsp.getErrorCode());
            errorCodeInfo.setDescArg(ResourceUtil.getMessage("database.operation.query.fail"));
            errorCodeInfo.setObjectName(endpointGrp.getName());
            smallErrorList.add(errorCodeInfo);
            totalResult.setSmallErrorCodeList(smallErrorList);
            return totalResult;
        }

        ResultRsp<String> result = new ResultRsp<>();

        if(!result.isSuccess()) {
            LOGGER.error("delete: delete epg from gre database failed, epgUuid = " + endpointGrp.getUuid());
            ErrorCodeInfo errorCodeInfo = new ErrorCodeInfo(result.getErrorCode());
            errorCodeInfo.setDescArg(ResourceUtil.getMessage("vpn.delete.delete_gre.desc"));
            errorCodeInfo.setObjectName(endpointGrp.getName());
            smallErrorList.add(errorCodeInfo);
        }

        if(CollectionUtils.isNotEmpty(smallErrorList)) {
            totalResult.setSmallErrorCodeList(smallErrorList);
            totalResult.setErrorCode(ErrorCode.OVERLAYVPN_FAILED);
        }

        return totalResult;
    }

    /**
     * Deploy an endpoint group. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param reqEpg Endpoint group to be deployed
     * @return Deployed endpoint group
     * @throws ServiceException When deploy failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<EndpointGroup> deploy(HttpServletRequest req, HttpServletResponse resp, EndpointGroup reqEpg)
            throws ServiceException {
        ResultRsp<OverlayVpn> parentVpnRsp = getFullVpn(Arrays.asList(reqEpg), true);
        if(!parentVpnRsp.isSuccess()) {
            LOGGER.error("get operation complex overlayVPN failed.");
            return new ResultRsp<>(parentVpnRsp, reqEpg);
        }

        OverlayVpn vpnDataToGre =
                OverlayVpnSvcUtil.buildVpnDataByConnId(parentVpnRsp.getData(), reqEpg.getConnectionId());
        vpnDataToGre.getVpnConnections().get(0).setAdminStatus(AdminStatus.INACTIVE.getName());

        if(TopologyType.HUB_SPOKE.getName().equals(vpnDataToGre.getVpnConnections().get(0).getTopology())
                && !ConnectionUtil.isExistHubEpg(vpnDataToGre.getVpnConnections().get(0))) {
            ResultRsp<EndpointGroup> totalResult = new ResultRsp<EndpointGroup>(ErrorCode.VPN_NOT_CONTAIN_HUB_EPG);
            totalResult.setDescArg(ResourceUtil.getMessage("vpn.connection.not_contain_hub.desc"));
            String msg = ResourceUtil.getMessage("vpn.status.not_contain_hub.reason");
            totalResult.setReasonArg(msg);
            totalResult.setDetailArg(msg);
            return totalResult;
        }

        OverlayVpnSvcUtil.markEpgToVpn(vpnDataToGre, reqEpg, ModifyMaskType.DEPLOY);

        ResultRsp<Map<String, OverlayVpn>> tecToCloudVpnMapRsp =
                MutilTecVpnByTree.getTechToCloudVpnMap(parentVpnRsp.getData(), OperaMethType.DEPLOY);

        Map<String, Boolean> tecVpnToDeployedMap = new HashMap<String, Boolean>();
        try {
            ResultRsp<Map<String, OverlayVpn>> deployResult =
                    overlayVpnTaskSvc.deployVpnByTec(req, resp, tecToCloudVpnMapRsp.getData(), tecVpnToDeployedMap);

            UpdateVpnStatusByEle.updateVpnStatusByEpgStatus(deployResult, parentVpnRsp.getData(), reqEpg,
                    AdminStatus.ACTIVE, false, inventoryDao);

            return new ResultRsp<>(deployResult, reqEpg);
        } catch(ServiceException e) {
            LOGGER.error("failed to create vpn", e);
            return new ResultRsp<EndpointGroup>(ErrorCode.OVERLAYVPN_FAILED, reqEpg);
        }
    }

    /**
     * Undeploy an endpoint group. <br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param reqEpg Endpoint group to be Undeploy
     * @return Undeploy endpoint group
     * @throws ServiceException When undeploy failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<EndpointGroup> undeploy(HttpServletRequest req, HttpServletResponse resp, EndpointGroup reqEpg)
            throws ServiceException {
        ResultRsp<OverlayVpn> parentVpnRsp = getFullVpn(Arrays.asList(reqEpg), true);
        if(!parentVpnRsp.isSuccess()) {
            LOGGER.error("undeploy: get operation complex overlayVPN failed.");
            return new ResultRsp<>(parentVpnRsp, reqEpg);
        }

        OverlayVpn vpnDataToGre =
                OverlayVpnSvcUtil.buildVpnDataByConnId(parentVpnRsp.getData(), reqEpg.getConnectionId());
        vpnDataToGre.getVpnConnections().get(0).setAdminStatus(AdminStatus.ACTIVE.getName());
        OverlayVpnSvcUtil.markEpgToVpn(vpnDataToGre, reqEpg, ModifyMaskType.UNDEPLOY);

        ResultRsp<Map<String, OverlayVpn>> tecToCloudVpnMapRsp =
                MutilTecVpnByTree.getTechToCloudVpnMap(vpnDataToGre, OperaMethType.UNDEPLOY);

        Map<String, Boolean> tecVpnToUndeployedMap = new HashMap<String, Boolean>();
        ResultRsp<Map<String, OverlayVpn>> deployResult =
                overlayVpnTaskSvc.undeployVpnByTec(req, resp, tecToCloudVpnMapRsp.getData(), tecVpnToUndeployedMap);

        UpdateVpnStatusByEle.updateVpnStatusByEpgStatus(deployResult, parentVpnRsp.getData(), reqEpg,
                AdminStatus.INACTIVE, false, inventoryDao);
        return new ResultRsp<>(deployResult, reqEpg);
    }

    private ResultRsp<OverlayVpn> getFullVpn(List<EndpointGroup> reqEpgs, boolean isNeedMappingPolicy)
            throws ServiceException {
        List<OverlayVpn> overlayVpns =
                overlayVpnComplexDao.queryComplexVpnByEpgs(reqEpgs, isNeedMappingPolicy).getData();
        if(CollectionUtils.isEmpty(overlayVpns)) {
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("OverlayVpn", null);
        }

        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, overlayVpns.get(0));
    }

    public void setOverlayVpnTaskSvc(OverlayVpnTaskSvc overlayVpnTaskSvc) {
        this.overlayVpnTaskSvc = overlayVpnTaskSvc;
    }

    public void setConnectionSvcImpl(ConnectionSvcImpl connectionSvcImpl) {
        this.connectionSvcImpl = connectionSvcImpl;
    }

}
