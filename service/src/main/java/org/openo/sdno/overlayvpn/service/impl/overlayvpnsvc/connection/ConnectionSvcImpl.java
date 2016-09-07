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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.connection;

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
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.ModifyMaskType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.UpdateVpnStatus;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.UpdateVpnStatusByEle;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask.MutilTecVpnByTree;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask.OverlayVpnTaskSvc;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.overlayvpn.OverlayVpnSvcUtil;
import org.openo.sdno.overlayvpn.service.inf.overlayvpn.IConnection;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.openo.sdno.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Connection service model implementation. <br/>
 * <p>
 * Defines VPN connection properties.
 * </p>
 *
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */

public class ConnectionSvcImpl implements IConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionSvcImpl.class);

    @Resource
    private OverlayVpnComplexDao overlayVpnComplexDao;

    @Resource
    private OverlayVpnTaskSvc overlayVpnTaskSvc;

    @Resource
    private UpdateVpnStatus updateVpnStatus;

    @Autowired
    private InventoryDao inventoryDao;

    /**
     * Set the connection status and store in DB. <br/>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param connection Connection data received in connection request
     * @return created and stored connection object
     * @throws ServiceException When store in DB failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<Connection> create(HttpServletRequest req, HttpServletResponse resp, Connection connection)
            throws ServiceException {
        // Set service state INACTIVE
        connection.setAdminStatus(AdminStatus.INACTIVE.getName());

        // Insert the new connection into DB
        return inventoryDao.insert(connection);
    }

    /**
     * Query a single connection. <br/>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param vpnConnUuid UUID of connection received as part of request
     * @param tenantId Tenant id of the request
     * @return Returns Connection object on success
     * @throws ServiceException When query failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<Connection> query(HttpServletRequest req, HttpServletResponse resp, String vpnConnUuid,
            String tenantId) throws ServiceException {
        ResultRsp<Connection> queryDbRsp = inventoryDao.query(Connection.class, vpnConnUuid, tenantId);
        ResultRsp<Connection> retRsp = new ResultRsp<>(queryDbRsp);
        if(null == queryDbRsp.getData()) {
            LOGGER.warn("do not get data, uuid" + vpnConnUuid + ", tenantId" + tenantId);
            return retRsp;
        }
        Connection queryConnection = queryDbRsp.getData();

        // Find Endpoint group models associated with the connection
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("connectionId", Arrays.asList(vpnConnUuid));
        ResultRsp<List<EndpointGroup>> endpointGrpRsp =
                inventoryDao.queryByFilter(EndpointGroup.class, JsonUtil.toJson(filterMap), null);
        if(CollectionUtils.isNotEmpty(endpointGrpRsp.getData())) {
            @SuppressWarnings("unchecked")
            List<String> epgUuids =
                    new ArrayList<>(CollectionUtils.collect(endpointGrpRsp.getData(), new Transformer() {

                        @Override
                        public Object transform(Object arg0) {
                            return ((EndpointGroup)arg0).getUuid();
                        }
                    }));
            queryConnection.setEpgIds(epgUuids);
        }
        retRsp.setData(queryConnection);
        return retRsp;
    }

    /**
     * Batch query of connections associated with tenant and query filter. <br/>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param tenantId Tenant id of the request
     * @param filter Query filter string
     * @return The list of connection object
     * @throws ServiceException When query failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<List<Connection>> batchQuery(HttpServletRequest req, HttpServletResponse resp, String tenantId,
            String filter) throws ServiceException {

        if(!filter.contains(tenantId)) {
            LOGGER.error("no tenantId information in filter, tenantId = " + tenantId);
            ThrowOverlayVpnExcpt.throwTenantIdMissing(tenantId);
        }
        // Query DAO with filter
        ResultRsp<List<Connection>> queryResult = inventoryDao.batchQuery(Connection.class, filter);
        ResultRsp<List<Connection>> retRsp = new ResultRsp<>(queryResult);
        if(!queryResult.isSuccess()) {
            LOGGER.error("query faild, info: " + retRsp.toString());
            return retRsp;
        }
        if(CollectionUtils.isEmpty(queryResult.getData())) {
            LOGGER.warn("do not get data, tenantId" + tenantId);
            return retRsp;
        }
        List<Connection> queryConnectionLst = queryResult.getData();

        // Query connections associated endpoint groups
        @SuppressWarnings("unchecked")
        List<String> connectionIds = new ArrayList<>(CollectionUtils.collect(queryConnectionLst, new Transformer() {

            @Override
            public Object transform(Object arg0) {
                return ((Connection)arg0).getUuid();
            }
        }));
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("connectionId", connectionIds);
        ResultRsp<List<EndpointGroup>> endpointGrpRsp =
                inventoryDao.batchQuery(EndpointGroup.class, JsonUtil.toJson(filterMap));
        // Fill the connection query list with edpoint groups data
        if(CollectionUtils.isNotEmpty(endpointGrpRsp.getData())) {
            for(Connection tempConn : queryConnectionLst) {
                for(EndpointGroup tempEpg : endpointGrpRsp.getData()) {
                    if(!tempConn.getUuid().equals(tempEpg.getConnectionId())) {
                        continue;
                    }
                    if(null == tempConn.getEpgIds()) {
                        tempConn.setEpgIds(new ArrayList<String>());
                    }
                    tempConn.getEpgIds().add(tempEpg.getUuid());
                }
            }
        }
        retRsp.setData(queryConnectionLst);
        return retRsp;
    }

    /**
     * Update a connection. <br/>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param newVpnConn New VPN connection model
     * @param oldVpnConn Original VPN connection model
     * @return Connection response
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<Connection> update(HttpServletRequest req, HttpServletResponse resp, Connection newVpnConn,
            Connection oldVpnConn) throws ServiceException {
        // If new admin status is same then return the stored connection object
        String newAdminStatus = newVpnConn.getAdminStatus();
        if(newAdminStatus.equals(oldVpnConn.getAdminStatus())) {
            return inventoryDao.update(newVpnConn, null);
        }

        ResultRsp<Connection> operRsp = new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        if(AdminStatus.INACTIVE.getName().equals(newAdminStatus)) {
            operRsp = undeploy(req, resp, newVpnConn);
        } else {
            operRsp = deploy(req, resp, newVpnConn);
        }

        // Update inventory with updated connection object
        if(operRsp.isSuccess()) {
            return inventoryDao.update(newVpnConn, null);
        }
        return new ResultRsp<>(operRsp, newVpnConn);
    }

    /**
     * Delete a connection. <br/>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param connection Connection to be deleted
     * @return The ResultRsp object
     * @throws ServiceException When delete failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<String> delete(HttpServletRequest req, HttpServletResponse resp, Connection connection)
            throws ServiceException {
        // Check the service model running status
        if(!AdminStatus.INACTIVE.getName().equals(connection.getAdminStatus())) {
            ResultRsp<String> retRsp = new ResultRsp<>(ErrorCode.VPN_STATUS_NOT_INACTIVE);
            retRsp.setDescArg(ResourceUtil.getMessage("vpn.status.not_inactive.desc"));
            String msg = ResourceUtil.getMessage("vpn.status.not_inactive.reason");
            retRsp.setReasonArg(msg);
            retRsp.setDetailArg(msg);
            retRsp.setAdviceArg(ResourceUtil.getMessage("vpn.status.not_inactive.advice"));
            return retRsp;
        }

        // Find the dependencies
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("connectionId", Arrays.asList(connection.getUuid()));
        ResultRsp<List<EndpointGroup>> endpointGrpRsp =
                inventoryDao.queryByFilter(EndpointGroup.class, JsonUtil.toJson(filterMap), null);
        if(CollectionUtils.isEmpty(endpointGrpRsp.getData())) {
            ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt(connection.getName(),
                    endpointGrpRsp.getData().get(0).getName());
        }

        return new ResultRsp<>(inventoryDao.delete(Connection.class, connection.getUuid()));
    }

    /**
     * Deploy a connection. <br/>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param connection Connection model to be deployed
     * @return Deployed connection
     * @throws ServiceException when deploy failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<Connection> deploy(HttpServletRequest req, HttpServletResponse resp, Connection connection)
            throws ServiceException {

        ResultRsp<OverlayVpn> overlayVpnRsp =
                overlayVpnComplexDao.queryComplexVpnByVpnUuid(connection.getCompositeVpnId(), true);

        OverlayVpn vpnDataToDeploy =
                OverlayVpnSvcUtil.buildVpnDataByConnId(overlayVpnRsp.getData(), connection.getUuid());

        OverlayVpnSvcUtil.markConnToVpn(vpnDataToDeploy, connection, ModifyMaskType.DEPLOY);

        // Schedule VPN service to task
        ResultRsp<Map<String, OverlayVpn>> tecToCloudVpnMapRsp =
                MutilTecVpnByTree.getTechToCloudVpnMap(overlayVpnRsp.getData(), OperaMethType.DEPLOY);

        Map<String, Boolean> tecVpnToDeployedMap = new HashMap<String, Boolean>();
        ResultRsp<Map<String, OverlayVpn>> deployResult =
                overlayVpnTaskSvc.deployVpnByTec(req, resp, tecToCloudVpnMapRsp.getData(), tecVpnToDeployedMap);

        // update deployment status
        UpdateVpnStatusByEle.updateVpnStatusByConnStatus(deployResult, overlayVpnRsp.getData(),
                vpnDataToDeploy.getVpnConnections().get(0), AdminStatus.ACTIVE);

        return new ResultRsp<>(deployResult, connection);
    }

    /**
     * Undeploy a connection. <br/>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param connection Connection model to be undeployed
     * @return Undeployed connection
     * @throws ServiceException When undeploy failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<Connection> undeploy(HttpServletRequest req, HttpServletResponse resp, Connection connection)
            throws ServiceException {
        // Fetch all VPN models
        ResultRsp<OverlayVpn> overlayVpnRsp =
                overlayVpnComplexDao.queryComplexVpnByVpnUuid(connection.getCompositeVpnId(), true);
        if(!overlayVpnRsp.isSuccess()) {
            LOGGER.error("get operation complex overlayVPN failed.");
            return new ResultRsp<>(overlayVpnRsp, connection);
        }

        OverlayVpn vpnDataToUndeploy =
                OverlayVpnSvcUtil.buildVpnDataByConnId(overlayVpnRsp.getData(), connection.getUuid());

        OverlayVpnSvcUtil.markConnToVpn(vpnDataToUndeploy, connection, ModifyMaskType.UNDEPLOY);

        ResultRsp<Map<String, OverlayVpn>> tecToCloudVpnMapRsp =
                MutilTecVpnByTree.getTechToCloudVpnMap(vpnDataToUndeploy, OperaMethType.UNDEPLOY);

        Map<String, Boolean> tecVpnToDeployedMap = new HashMap<String, Boolean>();
        ResultRsp<Map<String, OverlayVpn>> undeployResult =
                overlayVpnTaskSvc.undeployVpnByTec(req, resp, tecToCloudVpnMapRsp.getData(), tecVpnToDeployedMap);

        // Update deployment
        UpdateVpnStatusByEle.updateVpnStatusByConnStatus(undeployResult, overlayVpnRsp.getData(),
                vpnDataToUndeploy.getVpnConnections().get(0), AdminStatus.INACTIVE);

        return new ResultRsp<>(undeployResult, connection);
    }

    public void setOverlayVpnTaskSvc(OverlayVpnTaskSvc overlayVpnTaskSvc) {
        this.overlayVpnTaskSvc = overlayVpnTaskSvc;
    }

}
