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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.openo.sdno.overlayvpn.util.FilterDataUtil;
import org.openo.sdno.overlayvpn.util.dbresultprocess.FillDbResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * OverlayVpn Complex DAO Class.<br>
 * <p>
 * Provide some complex query interfaces.
 * </p>
 * 
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
@Service
public class OverlayVpnComplexDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OverlayVpnComplexDao.class);

    @Autowired
    private InventoryDao inventoryDao;

    /**
     * Query OverlayVpn Object by id.<br>
     * 
     * @param vpnUuid
     *            OverlayVpn id
     * @param isNeedMappingPolicy
     *            need MappingPolicy or not
     * @return OverlayVpn instance returned
     * @throws ServiceException
     *             Service Exception
     * @since SDNO 0.5
     */
    public ResultRsp<OverlayVpn> queryComplexVpnByVpnUuid(String vpnUuid, boolean isNeedMappingPolicy)
            throws ServiceException {
        if(!StringUtils.hasLength(vpnUuid)) {
            LOGGER.error("invalid query param.");
            ResultRsp<OverlayVpn> retRsp = new ResultRsp<>(ErrorCode.OVERLAYVPN_DATABASE_QUERY_FAILED);
            FillDbResultRsp.fillQueryResultRsp(retRsp, "[UUID] NULL");
            SvcExcptUtil.throwSvcExptionByResultRsp(retRsp);
        }

        // Query OverlayVpn by id
        ResultRsp<OverlayVpn> overlayVpnRsp = inventoryDao.query(OverlayVpn.class, vpnUuid, null);
        if(null == overlayVpnRsp.getData()) {
            LOGGER.error("OverlayVpn do not find. filter = " + vpnUuid);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("Overlay VPN");
        }

        // Query all connections of this OverlayVpn
        Map<String, Object> filterConnMap = new HashMap<String, Object>();
        filterConnMap.put("overlayVpnId", Arrays.asList(vpnUuid));
        ResultRsp<List<Connection>> connectionRsp =
                inventoryDao.batchQuery(Connection.class, JsonUtil.toJson(filterConnMap));
        if(CollectionUtils.isEmpty(connectionRsp.getData())) {
            LOGGER.error("OverlayVpn do not have Connection. filter = " + filterConnMap);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("Connecton");
        }

        // Query EndGroups by connections
        List<String> connUuids = new ArrayList<>();
        for(Connection tempConn : connectionRsp.getData()) {
            connUuids.add(tempConn.getUuid());
        }
        Map<String, Object> filterEpgMap = new HashMap<String, Object>();
        filterEpgMap.put("connectionId", connUuids);
        ResultRsp<List<EndpointGroup>> endpointGrpRsp =
                inventoryDao.queryByFilter(EndpointGroup.class, JsonUtil.toJson(filterEpgMap), null);
        if(CollectionUtils.isEmpty(endpointGrpRsp.getData())) {
            LOGGER.error("OverlayVpn do not have EndpointGroup. filter = " + filterEpgMap);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("EndpointGroup");
        }

        // Construct Complex OverlayVpn
        List<OverlayVpn> retOverlayVpns = Arrays.asList(overlayVpnRsp.getData());
        OverlayVpnComplexDaoUtil.buildComplexOverlayVpn(retOverlayVpns, connectionRsp.getData(),
                endpointGrpRsp.getData(), isNeedMappingPolicy, inventoryDao);
        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, retOverlayVpns.get(0));
    }

    /**
     * Query OverlayVpn by Connection.<br>
     * 
     * @param connectionUuid
     *            connection id
     * @return OverlayVpn Object
     * @throws ServiceException
     *             Service Exception occurs
     * @since SDNO 0.5
     */
    public ResultRsp<OverlayVpn> queryComplexVpnByConnUuid(String connectionUuid) throws ServiceException {
        if(!StringUtils.hasLength(connectionUuid)) {
            LOGGER.error("invalid query param.");
            ResultRsp<OverlayVpn> retRsp = new ResultRsp<>(ErrorCode.OVERLAYVPN_DATABASE_QUERY_FAILED);
            FillDbResultRsp.fillQueryResultRsp(retRsp, "[UUID] NULL");
            SvcExcptUtil.throwSvcExptionByResultRsp(retRsp);
        }

        // Query current Connection
        ResultRsp<Connection> connectionRsp = inventoryDao.query(Connection.class, connectionUuid, null);
        if(null == connectionRsp.getData()) {
            LOGGER.error("Connection do not find. filter = " + connectionUuid);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("Connection");
        }

        // Query current overlayVpn
        String compositeVpnId = connectionRsp.getData().getCompositeVpnId();
        ResultRsp<OverlayVpn> overlayVpnRsp = inventoryDao.query(OverlayVpn.class, compositeVpnId, null);
        if(null == overlayVpnRsp.getData()) {
            LOGGER.error("OverlayVpn do not find. filter = " + compositeVpnId);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("Overlay VPN");
        }

        // Query all EndpointGroups of this Connection
        Map<String, Object> filterEpgMap = new HashMap<String, Object>();
        filterEpgMap.put("connectionId", Arrays.asList(connectionUuid));
        ResultRsp<List<EndpointGroup>> endpointGrpRsp =
                inventoryDao.queryByFilter(EndpointGroup.class, JsonUtil.toJson(filterEpgMap), null);
        if(CollectionUtils.isEmpty(endpointGrpRsp.getData())) {
            LOGGER.error("OverlayVpn do not have EndpointGroup. filter = " + filterEpgMap);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("EndpointGroup");
        }

        // Construct complex OverlayVpn
        List<OverlayVpn> retOverlayVpns = Arrays.asList(overlayVpnRsp.getData());
        List<Connection> retConncetions = Arrays.asList(connectionRsp.getData());
        OverlayVpnComplexDaoUtil.buildComplexOverlayVpn(retOverlayVpns, retConncetions, endpointGrpRsp.getData(), true,
                inventoryDao);
        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, retOverlayVpns.get(0));
    }

    /**
     * Query OverlayVpns by EndpointGroups.<br>
     * 
     * @param epgs
     *            list of EndpointGroups
     * @param isNeedMappingPolicy
     *            need MappingPolicy or not
     * @return list of OverlayVpn
     * @throws ServiceException
     *             Service Exception
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public ResultRsp<List<OverlayVpn>> queryComplexVpnByEpgs(List<EndpointGroup> epgs, boolean isNeedMappingPolicy)
            throws ServiceException {
        List<String> parentConnUuids = new ArrayList<>(CollectionUtils.collect(epgs, new Transformer() {

            @Override
            public Object transform(Object arg0) {
                return ((EndpointGroup)arg0).getConnectionId();
            }
        }));

        // Query all connections
        List<Connection> parentConns =
                (List<Connection>)inventoryDao.batchQuery(Connection.class, parentConnUuids).getData();
        if(CollectionUtils.isEmpty(parentConns)) {
            LOGGER.error("Connection not found. uuid = " + parentConnUuids);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("Connection");
        }

        // Query all OverlayVpns
        List<String> parentVpnUuids = new ArrayList<>(CollectionUtils.collect(parentConns, new Transformer() {

            @Override
            public Object transform(Object arg0) {
                return ((Connection)arg0).getCompositeVpnId();
            }
        }));

        List<OverlayVpn> totalOverlanVpns =
                (List<OverlayVpn>)inventoryDao.batchQuery(OverlayVpn.class, parentVpnUuids).getData();
        if(CollectionUtils.isEmpty(totalOverlanVpns)) {
            LOGGER.error("OverlayVpn not found. filter = " + parentVpnUuids);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("OverlayVpn");
        }

        // Query all connections of these OverlayVpns
        String totalConnFilter = FilterDataUtil.getFilterData("overlayVpnId", parentVpnUuids);
        List<Connection> totalConns =
                (List<Connection>)inventoryDao.batchQuery(Connection.class, totalConnFilter).getData();
        if(CollectionUtils.isEmpty(totalConns)) {
            LOGGER.error("Connection not found. filter = " + totalConnFilter);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("Connection");
        }

        // Query all EndpointGroups by Connections
        List<String> totalConnUuids = new ArrayList<>(CollectionUtils.collect(totalConns, new Transformer() {

            @Override
            public Object transform(Object arg0) {
                return ((Connection)arg0).getUuid();
            }
        }));
        String totalEpgFilter = FilterDataUtil.getFilterData("connectionId", totalConnUuids);
        List<EndpointGroup> totalEpgs =
                (List<EndpointGroup>)inventoryDao.queryByFilter(EndpointGroup.class, totalEpgFilter, null).getData();
        if(CollectionUtils.isEmpty(totalEpgs)) {
            LOGGER.error("EndpointGroup not found. filter = " + totalEpgFilter);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("EndpointGroup");
        }

        OverlayVpnComplexDaoUtil.buildComplexOverlayVpn(totalOverlanVpns, totalConns, totalEpgs, isNeedMappingPolicy,
                inventoryDao);

        ResultRsp<List<OverlayVpn>> retResult = new ResultRsp<>();
        retResult.setData(totalOverlanVpns);
        return retResult;
    }

    /**
     * Query OverlayVpn Object by EndpointGroup.<br>
     * 
     * @param epg
     *            EndpointGroup object
     * @return OverlayVpn Object
     * @throws ServiceException
     *             Service Exception
     * @since SDNO 0.5
     */
    public ResultRsp<OverlayVpn> queryComplexVpnByEpg(EndpointGroup epg) throws ServiceException {
        String connectionId = epg.getConnectionId();
        ResultRsp<OverlayVpn> retResult = new ResultRsp<>();
        // Query Connection of epg
        Connection parentConn = (Connection)inventoryDao.query(Connection.class, connectionId, null).getData();
        if(null == parentConn) {
            LOGGER.error("Connection not found. uuid = " + connectionId);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("Connection");
            return retResult;
        }

        // Query OverlayVpn of parent Connection
        String parentVpnUuid = parentConn.getCompositeVpnId();
        if(null == parentVpnUuid) {
            LOGGER.error("parentConn not found. uuid = " + connectionId);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("Connection");
        }

        OverlayVpn overlanVpn = (OverlayVpn)inventoryDao.query(OverlayVpn.class, parentVpnUuid, null).getData();
        if(null == overlanVpn) {
            LOGGER.error("OverlayVpn not found. filter = " + parentVpnUuid);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("OverlayVpn");
            return retResult;
        }

        // Query all EndpointGroups of this connection
        String totalEpgFilter = FilterDataUtil.getFilterData("connectionId", connectionId);
        List<EndpointGroup> totalEpgs =
                (List<EndpointGroup>)inventoryDao.queryByFilter(EndpointGroup.class, totalEpgFilter, null).getData();
        if(CollectionUtils.isEmpty(totalEpgs)) {
            LOGGER.error("EndpointGroup not found. filter = " + totalEpgFilter);
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt("EndpointGroup");
        }

        parentConn.setEndpointGroups(totalEpgs);
        overlanVpn.setVpnConnections(Arrays.asList(parentConn));

        retResult.setData(overlanVpn);
        return retResult;
    }

}
