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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.overlayvpn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.dao.overlayvpn.OverlayVpnComplexDao;
import org.openo.sdno.overlayvpn.enums.OperaMethType;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.ModifyMaskType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.UpdateVpnStatus;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask.MutilTecVpnByTree;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask.OverlayVpnTaskSvc;
import org.openo.sdno.overlayvpn.service.inf.overlayvpn.IOverlayVpn;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.openo.sdno.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * OverlayVpn service implementation.<br/>
 * 
 * @author
 * @version SDNO 0.5 Jun 7, 2016
 */
public class OverlayVpnSvcImpl implements IOverlayVpn {

    private static final Logger LOGGER = LoggerFactory.getLogger(OverlayVpnSvcImpl.class);

    @Resource
    private OverlayVpnComplexDao overlayVpnComplexDao;

    @Resource
    private OverlayVpnTaskSvc overlayVpnTaskSvc;

    @Resource
    private UpdateVpnStatus updateVpnStatus;

    @Autowired
    private InventoryDao inventoryDao;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     */
    public OverlayVpnSvcImpl() {
        super();
    }

    /**
     * Create OverlayVpn.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param overlayVpn OverlayVpn need to create
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<OverlayVpn> create(HttpServletRequest req, HttpServletResponse resp, OverlayVpn overlayVpn)
            throws ServiceException {

        // Set Action Status to INAVTIVE
        overlayVpn.setActionState(ActionStatus.CREATING.getName());

        // Set Administrative Status to CREATING
        overlayVpn.setAdminStatus(AdminStatus.INACTIVE.getName());

        // Insert Data to Database
        return inventoryDao.insert(overlayVpn);
    }

    /**
     * Query OverlayVpn by UUID and Tenant id.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletRequest Object
     * @param uuid Key Id of OverlayVpn
     * @param tenantId tenant Id
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<OverlayVpn> query(HttpServletRequest req, HttpServletResponse resp, String uuid, String tenantId)
            throws ServiceException {
        ResultRsp<OverlayVpn> queryDbRsp = inventoryDao.query(OverlayVpn.class, uuid, tenantId);
        ResultRsp<OverlayVpn> retRsp = new ResultRsp<>(queryDbRsp);
        if(null == queryDbRsp.getData()) {
            LOGGER.warn("do not get data, uuid" + uuid + ", tenantId" + tenantId);
            return retRsp;
        }
        OverlayVpn queryOverlayVpn = queryDbRsp.getData();

        // Query all Connections of this OverlayVpn
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("overlayVpnId", Arrays.asList(uuid));
        ResultRsp<List<Connection>> connectionRsp =
                inventoryDao.queryByFilter(Connection.class, JsonUtil.toJson(filterMap), null);
        if(CollectionUtils.isNotEmpty(connectionRsp.getData())) {
            @SuppressWarnings("unchecked")
            List<String> connUuids =
                    new ArrayList<>(CollectionUtils.collect(connectionRsp.getData(), new Transformer() {

                        @Override
                        public Object transform(Object arg0) {
                            return ((Connection)arg0).getUuid();
                        }
                    }));
            queryOverlayVpn.setConnectionIds(connUuids);
        }
        retRsp.setData(queryOverlayVpn);
        return retRsp;
    }

    /**
     * Batch Query OverlayVpn by Tenant.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param tenantId tenant id
     * @param filter filter condition
     * @return OverlayVpn queried out
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<List<OverlayVpn>> batchQuery(HttpServletRequest req, HttpServletResponse resp, String tenantId,
            String filter) throws ServiceException {
        // Make sure filter contains tenant info
        if(!filter.contains(tenantId)) {
            LOGGER.error("no tenantId information in filter, tenantId = " + tenantId);
            ThrowOverlayVpnExcpt.throwTenantIdMissing(tenantId);
        }

        // Query OverlayVpn
        ResultRsp<List<OverlayVpn>> queryResult = inventoryDao.batchQuery(OverlayVpn.class, filter);
        ResultRsp<List<OverlayVpn>> retRsp = new ResultRsp<>(queryResult);
        if(!queryResult.isSuccess()) {
            LOGGER.error("query faild, info: " + retRsp.toString());
            return retRsp;
        }
        if(CollectionUtils.isEmpty(queryResult.getData())) {
            LOGGER.warn("do not get data, tenantId:" + tenantId);
            return retRsp;
        }
        List<OverlayVpn> queryOverlayVpnLst = queryResult.getData();

        // Query all connections of these OverlayVpns
        @SuppressWarnings("unchecked")
        List<String> overlayVpnIds = new ArrayList<>(CollectionUtils.collect(queryOverlayVpnLst, new Transformer() {

            @Override
            public Object transform(Object arg0) {
                return ((OverlayVpn)arg0).getUuid();
            }
        }));
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("overlayVpnId", overlayVpnIds);
        ResultRsp<List<Connection>> connectionRsp =
                inventoryDao.queryByFilter(Connection.class, JsonUtil.toJson(filterMap), null);

        // Set ConnectionIds to OverlayVpn
        if(CollectionUtils.isNotEmpty(connectionRsp.getData())) {
            for(OverlayVpn tempVpn : queryOverlayVpnLst) {
                for(Connection tempConn : connectionRsp.getData()) {
                    if(!tempVpn.getUuid().equals(tempConn.getCompositeVpnId())) {
                        continue;
                    }
                    if(null == tempVpn.getConnectionIds()) {
                        tempVpn.setConnectionIds(new ArrayList<String>());
                    }
                    tempVpn.getConnectionIds().add(tempConn.getUuid());
                }
            }
        }
        retRsp.setData(queryOverlayVpnLst);
        return retRsp;
    }

    /**
     * Update OverlayVpn.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param newTotalVpn new OverlayVpn instance
     * @param oldTotalVpn old OverlayVpn instance
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<OverlayVpn> update(HttpServletRequest req, HttpServletResponse resp, OverlayVpn newTotalVpn,
            OverlayVpn oldTotalVpn) throws ServiceException {

        // TEMP: only name and description update is allowed
        String fieldsUpdated = "name,description";

        // Check whether AdminStatus changes
        String newAdminStatus = newTotalVpn.getAdminStatus();
        if(newAdminStatus.equals(oldTotalVpn.getAdminStatus())) {
            // only update database
            return inventoryDao.update(newTotalVpn, fieldsUpdated);
        }

        // Deploy and UnDeploy according to Status Changes
        ResultRsp<OverlayVpn> operRsp = new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        if(AdminStatus.INACTIVE.getName().equals(newAdminStatus)) {
            // UnDeploy
            operRsp = undeploy(req, resp, newTotalVpn);
        } else {
            // Deploy
            operRsp = deploy(req, resp, newTotalVpn);
        }

        // Update database
        if(operRsp.isSuccess()) {
            return inventoryDao.update(newTotalVpn, null);
        }
        return new ResultRsp<>(operRsp, newTotalVpn);
    }

    /**
     * Delete OverlayVpn.<br/>
     * delete one OverlayVpn
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param cloudVpn OverlayVpn need to delete
     * @return operation result
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<String> delete(HttpServletRequest req, HttpServletResponse resp, OverlayVpn cloudVpn)
            throws ServiceException {

        // Delete non-inactive OverlayVpn is forbidden
        if(!AdminStatus.INACTIVE.getName().equals(cloudVpn.getAdminStatus())) {
            ResultRsp<String> retRsp = new ResultRsp<>(ErrorCode.VPN_STATUS_NOT_INACTIVE);
            retRsp.setDescArg(ResourceUtil.getMessage("vpn.status.not_inactive.desc"));
            String msg = ResourceUtil.getMessage("vpn.status.not_inactive.reason");
            retRsp.setReasonArg(msg);
            retRsp.setDetailArg(msg);
            retRsp.setAdviceArg(ResourceUtil.getMessage("vpn.status.not_inactive.advice"));
            return retRsp;
        }

        // Check whether OverlayVpn has connections
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("overlayVpnId", Arrays.asList(cloudVpn.getUuid()));
        ResultRsp<List<Connection>> connectionRsp =
                inventoryDao.queryByFilter(Connection.class, JsonUtil.toJson(filterMap), null);
        if(CollectionUtils.isNotEmpty(connectionRsp.getData())) {
            ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt(cloudVpn.getName(),
                    connectionRsp.getData().get(0).getName());
        }

        // Delete OverlayVpn
        return new ResultRsp<>(inventoryDao.delete(OverlayVpn.class, cloudVpn.getUuid()));
    }

    /**
     * Deploy OverlayVpn.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param overlayVpn OverlayVpn need to Deploy
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<OverlayVpn> deploy(HttpServletRequest req, HttpServletResponse resp, OverlayVpn overlayVpn)
            throws ServiceException {

        // Query Whole OverlayVpn Data
        ResultRsp<OverlayVpn> overlayVpnRsp = overlayVpnComplexDao.queryComplexVpnByVpnUuid(overlayVpn.getUuid(), true);

        if(CollectionUtils.isEmpty(overlayVpnRsp.getData().getVpnConnections())) {
            LOGGER.info("deploy overlayvpn: null connections.");
            ResultRsp<OverlayVpn> totalResult = new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_FAILED);
            totalResult.setDescArg(ResourceUtil.getMessage("vpn.overlayvpn.connection_less_one.desc"));
            String msg = ResourceUtil.getMessage("vpn.overlayvpn.connection_less_one.reason");
            totalResult.setReasonArg(msg);
            totalResult.setDetailArg(msg);
            return totalResult;
        }

        // Set Modify Type
        overlayVpnRsp.getData().setModifyMask(ModifyMaskType.DEPLOY.getName());

        // Divide OverlayVpn Connections by Technology type
        ResultRsp<Map<String, OverlayVpn>> tecToCloudVpnMapRsp =
                MutilTecVpnByTree.getTechToCloudVpnMap(overlayVpnRsp.getData(), OperaMethType.DEPLOY);

        // Deploy OverlayVpn
        Map<String, Boolean> tecVpnToDeployedMap = new HashMap<String, Boolean>();
        ResultRsp<Map<String, OverlayVpn>> deployResult =
                overlayVpnTaskSvc.deployVpnByTec(req, resp, tecToCloudVpnMapRsp.getData(), tecVpnToDeployedMap);

        // Update Deployment status
        OverlayVpn complexVpn = overlayVpnRsp.getData();
        List<EndpointGroup> epgList = new ArrayList<>();
        for(Connection tempConn : complexVpn.getVpnConnections()) {
            epgList.addAll(tempConn.getEndpointGroups());
        }
        updateVpnStatus.updateVpnStatus(deployResult, tecVpnToDeployedMap, complexVpn, complexVpn.getVpnConnections(),
                epgList, inventoryDao);
        return new ResultRsp<>(deployResult, overlayVpn);
    }

    /**
     * UnDeploy OverlayVpn.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param cloudVpn OverlayVpn need to UnDeploy
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<OverlayVpn> undeploy(HttpServletRequest req, HttpServletResponse resp, OverlayVpn cloudVpn)
            throws ServiceException {

        // Query Whole OverlayVpn Data
        ResultRsp<OverlayVpn> overlayVpnRsp = overlayVpnComplexDao.queryComplexVpnByVpnUuid(cloudVpn.getUuid(), true);

        // Set Modify Type
        overlayVpnRsp.getData().setModifyMask(ModifyMaskType.UNDEPLOY.getName());

        // Divide OverlayVpn Connections by Technology type
        ResultRsp<Map<String, OverlayVpn>> tecToCloudVpnMapRsp =
                MutilTecVpnByTree.getTechToCloudVpnMap(overlayVpnRsp.getData(), OperaMethType.UNDEPLOY);

        // UnDeploy OverlayVpn
        Map<String, Boolean> tecVpnToDeployedMap = new HashMap<String, Boolean>();
        ResultRsp<Map<String, OverlayVpn>> undeployResult =
                overlayVpnTaskSvc.undeployVpnByTec(req, resp, tecToCloudVpnMapRsp.getData(), tecVpnToDeployedMap);

        // Update Deployment status
        OverlayVpn complexVpn = overlayVpnRsp.getData();
        List<EndpointGroup> epgList = new ArrayList<>();
        for(Connection tempConn : complexVpn.getVpnConnections()) {
            epgList.addAll(tempConn.getEndpointGroups());
        }
        updateVpnStatus.updateVpnStatus(undeployResult, tecVpnToDeployedMap, complexVpn, complexVpn.getVpnConnections(),
                epgList, inventoryDao);
        return new ResultRsp<>(undeployResult, cloudVpn);
    }

}
