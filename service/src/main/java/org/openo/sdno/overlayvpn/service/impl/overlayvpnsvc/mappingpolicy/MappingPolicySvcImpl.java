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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mappingpolicy;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.BaseMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.GreMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.inf.overlayvpn.IMappingPolicy;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The mapping policy implementation. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */

public class MappingPolicySvcImpl implements IMappingPolicy {

    @Autowired
    private InventoryDao inventoryDao;

    /**
     * Create mapping policy. <br/>
     * 
     * @param req
     *            HttpServletRequest Object
     * @param resp
     *            HttpServletResponse Object
     * @param mappingPolicy
     *            Mapping policy in the request message
     * @return The mapping policy object
     * @throws ServiceException
     *             When store in DB failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<BaseMappingPolicy> create(HttpServletRequest req, HttpServletResponse resp,
            BaseMappingPolicy mappingPolicy) throws ServiceException {

        return null;
    }

    /**
     * Query a mapping policy. <br/>
     * 
     * @param req
     *            HttpServletRequest Object
     * @param resp
     *            HttpServletResponse Object
     * @param uuid
     *            UUID of mapping policy
     * @param tenantId
     *            Tenant id of mapping policy
     * @return Mapping policy object
     * @throws ServiceException
     *             When query failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<BaseMappingPolicy> query(HttpServletRequest req, HttpServletResponse resp, String uuid,
            String tenantId) throws ServiceException {
        ResultRsp<? extends BaseMappingPolicy> queryResult = new ResultRsp<>();

        queryResult = inventoryDao.query(GreMappingPolicy.class, uuid, tenantId);
        if(!queryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(queryResult);
        }
        if(null != queryResult.getData()) {
            return new ResultRsp<BaseMappingPolicy>(queryResult, queryResult.getData());
        }

        queryResult = inventoryDao.query(IpsecMappingPolicy.class, uuid, tenantId);
        if(!queryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(queryResult);
        }
        if(null != queryResult.getData()) {
            return new ResultRsp<BaseMappingPolicy>(queryResult, queryResult.getData());
        }

        queryResult = inventoryDao.query(VxlanMappingPolicy.class, uuid, tenantId);
        if(!queryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(queryResult);
        }
        if(null != queryResult.getData()) {
            return new ResultRsp<BaseMappingPolicy>(queryResult, queryResult.getData());
        }

        return new ResultRsp<>();
    }

    /**
     * Query mapping policy in batch. <br/>
     * 
     * @param req
     *            HttpServletRequest Object
     * @param resp
     *            HttpServletResponse Object
     * @param tenantId
     *            Tenant id of the request
     * @param filter
     *            Query filter
     * @return The mapping policy list
     * @throws ServiceException
     *             When query failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<List<BaseMappingPolicy>> batchQuery(HttpServletRequest req, HttpServletResponse resp,
            String tenantId, String filter) throws ServiceException {
        ResultRsp<? extends List<? extends BaseMappingPolicy>> queryResult = new ResultRsp<>();

        ResultRsp<List<BaseMappingPolicy>> retResult = new ResultRsp<>();
        retResult.setData(new ArrayList<BaseMappingPolicy>());

        queryResult = inventoryDao.batchQuery(GreMappingPolicy.class, filter);
        if(!queryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(queryResult);
        }
        if(CollectionUtils.isNotEmpty(queryResult.getData())) {
            retResult.getData().addAll(queryResult.getData());
        }

        queryResult = inventoryDao.batchQuery(IpsecMappingPolicy.class, filter);
        if(!queryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(queryResult);
        }
        if(CollectionUtils.isNotEmpty(queryResult.getData())) {
            retResult.getData().addAll(queryResult.getData());
        }

        queryResult = inventoryDao.batchQuery(VxlanMappingPolicy.class, filter);
        if(!queryResult.isSuccess()) {
            ThrowOverlayVpnExcpt.checkRspThrowException(queryResult);
        }
        if(CollectionUtils.isNotEmpty(queryResult.getData())) {
            retResult.getData().addAll(queryResult.getData());
        }

        return retResult;
    }

    /**
     * Update a mapping policy. <br/>
     * 
     * @param req
     *            HttpServletRequest Object
     * @param resp
     *            HttpServletResponse Object
     * @param newMappingPolicy
     *            New mapping policy
     * @param oldMappingPolicy
     *            Original mapping policy
     * @return ResultRsp object
     * @throws ServiceException
     *             When update failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<BaseMappingPolicy> update(HttpServletRequest req, HttpServletResponse resp,
            BaseMappingPolicy newMappingPolicy, BaseMappingPolicy oldMappingPolicy) throws ServiceException {

        return null;
    }

    /**
     * Delete a mapping policy. <br/>
     * 
     * @param req
     *            HttpServletRequest Object
     * @param resp
     *            HttpServletResponse Object
     * @param mappingPolicy
     *            Mapping policy that want to delete
     * @return The ResultRsp object
     * @throws ServiceException
     *             When delete failed.
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<String> delete(HttpServletRequest req, HttpServletResponse resp, BaseMappingPolicy mappingPolicy)
            throws ServiceException {

        return null;
    }

    /**
     * Deploy a connection. <br/>
     * 
     * @param req
     *            HttpServletRequest Object
     * @param resp
     *            HttpServletResponse Object
     * @param mappingPolicy
     *            Mapping policy that want to deploy
     * @return Deployed mapping policy
     * @throws ServiceException
     *             when deploy failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<BaseMappingPolicy> deploy(HttpServletRequest req, HttpServletResponse resp,
            BaseMappingPolicy mappingPolicy) throws ServiceException {

        return null;
    }

    /**
     * Undeploy a mapping policy. <br/>
     * 
     * @param req
     *            HttpServletRequest Object
     * @param resp
     *            HttpServletResponse Object
     * @param mappingPolicy
     *            Mapping policy that want to undeploy
     * @return Undeployed mapping policy
     * @throws ServiceException
     *             When undeploy failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<BaseMappingPolicy> undeploy(HttpServletRequest req, HttpServletResponse resp,
            BaseMappingPolicy mappingPolicy) throws ServiceException {

        return null;
    }

}
