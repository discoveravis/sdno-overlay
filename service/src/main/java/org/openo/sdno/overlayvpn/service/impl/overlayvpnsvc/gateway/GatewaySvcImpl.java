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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.gateway;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.inf.overlayvpn.IGateway;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Gate way model implementation. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public class GatewaySvcImpl implements IGateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewaySvcImpl.class);

    @Autowired
    private InventoryDao inventoryDao;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     */
    public GatewaySvcImpl() {
        super();
    }

    /**
     * Create gate way. <br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param gateway Gate way in the request message
     * @return created and stored gate way object
     * @throws ServiceException When store in DB failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<Gateway> create(HttpServletRequest req, HttpServletResponse resp, Gateway gateway)
            throws ServiceException {
        return inventoryDao.insert(gateway);
    }

    /**
     * Query a gate way. <br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param uuid UUID of gate way
     * @param tenantId Tenant id of gate way
     * @return Gate way object
     * @throws ServiceException When query failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<Gateway> query(HttpServletRequest req, HttpServletResponse resp, String uuid, String tenantId)
            throws ServiceException {
        ResultRsp<Gateway> queryDbRsp = inventoryDao.query(Gateway.class, uuid, tenantId);
        ResultRsp<Gateway> retRsp = new ResultRsp<>(queryDbRsp);
        if(queryDbRsp.isSuccess()) {
            retRsp.setData(queryDbRsp.getData());
        } else {
            LOGGER.error("query faild, info: " + retRsp.toString());
        }
        return retRsp;
    }

    /**
     * Query gate way in batch. <br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param tenantId Tenant id of the request
     * @param filter Query filter
     * @return The gate way list
     * @throws ServiceException When query failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<List<Gateway>> batchQuery(HttpServletRequest req, HttpServletResponse resp, String tenantId,
            String filter) throws ServiceException {
        if(!filter.contains(tenantId)) {
            LOGGER.error("no tenantId information in filter, tenantId = " + tenantId);
            ThrowOverlayVpnExcpt.throwTenantIdMissing(tenantId);
        }

        ResultRsp<List<Gateway>> queryResult = inventoryDao.queryByFilter(Gateway.class, filter, null);
        ResultRsp<List<Gateway>> retRsp = new ResultRsp<>(queryResult);
        if(queryResult.isSuccess()) {
            List<Gateway> gatewayLst = queryResult.getData();
            if(CollectionUtils.isNotEmpty(gatewayLst)) {
                retRsp.setData(gatewayLst);
            } else {
                LOGGER.warn("do not get data, tenantId" + tenantId);
            }
        } else {
            LOGGER.error("query faild, info: " + retRsp.toString());
        }
        return retRsp;
    }

    /**
     * Update a gate way. <br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param newGateway New gate way model
     * @param oldGateway Original gate way model
     * @return The ResultRsp object
     * @throws ServiceException When update failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<Gateway> update(HttpServletRequest req, HttpServletResponse resp, Gateway newGateway,
            Gateway oldGateway) throws ServiceException {
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("gatewayId", Arrays.asList(newGateway.getUuid()));
        ResultRsp<List<EndpointGroup>> endpointGrpRsp =
                inventoryDao.queryByFilter(EndpointGroup.class, JsonUtil.toJson(filterMap), null);
        if(CollectionUtils.isNotEmpty(endpointGrpRsp.getData())) {
            ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt(newGateway.getName(),
                    endpointGrpRsp.getData().get(0).getName());
        }

        return new InventoryDao<Gateway>().update(newGateway, null);
    }

    /**
     * Delete a gate way. <br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param gateway Gate way that want to delete
     * @return The ResultRsp object
     * @throws ServiceException When delete failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<String> delete(HttpServletRequest req, HttpServletResponse resp, Gateway gateway)
            throws ServiceException {
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("gatewayId", Arrays.asList(gateway.getUuid()));
        ResultRsp<List<EndpointGroup>> endpointGrpRsp =
                inventoryDao.queryByFilter(EndpointGroup.class, JsonUtil.toJson(filterMap), null);
        if(CollectionUtils.isNotEmpty(endpointGrpRsp.getData())) {
            ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt(gateway.getName(),
                    endpointGrpRsp.getData().get(0).getName());
        }

        return new ResultRsp<String>(new InventoryDao<Gateway>().delete(Gateway.class, gateway.getUuid()));
    }

}
