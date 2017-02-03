/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.nbi.PuerInvRelationNbiBean;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.nbi.PuerInvServiceNbiBean;
import org.openo.sdno.overlayvpn.inventory.sdk.inf.InvDAO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.BatchQueryFileterEntity;
import org.openo.sdno.overlayvpn.inventory.sdk.model.QueryParams;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationMO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationPuerMO;
import org.openo.sdno.overlayvpn.inventory.sdk.util.MOModelProcessor;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * PUER Inventory DAO Implementation Class.<br>
 * 
 * @param <T> Inventory Model Class
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
public class PuerInvDAOImpl<T> implements InvDAO<T> {

    /**
     * Max number of every package.
     */
    private static final int MAX_NUM_PER_PKG_CU = 1000;

    /**
     * Logger handler.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PuerInvDAOImpl.class);

    /**
     * Object Operation Service.
     */
    @Autowired
    private PuerInvServiceNbiBean puerObjInvService;

    /**
     * Relation Operation Service.
     */
    @Autowired
    private PuerInvRelationNbiBean puerRelationService;

    public PuerInvServiceNbiBean getPuerObjInvService() {
        return puerObjInvService;
    }

    public void setPuerObjInvService(PuerInvServiceNbiBean puerObjInvService) {
        this.puerObjInvService = puerObjInvService;
    }

    public PuerInvRelationNbiBean getPuerRelationService() {
        return puerRelationService;
    }

    public void setPuerRelationService(PuerInvRelationNbiBean puerRelationService) {
        this.puerRelationService = puerRelationService;
    }

    @Override
    public ResultRsp<List<String>> add(List<T> moList, Class moType) throws ServiceException {
        LOGGER.debug("Start add " + moList.toString() + " to puer inv.");
        ResultRsp result = ResultRsp.SUCCESS;
        List<String> uuidList = new ArrayList<String>();

        // Process every package.
        int num = moList.size();
        int count = (num / MAX_NUM_PER_PKG_CU) + ((num % MAX_NUM_PER_PKG_CU) > 0 ? 1 : 0);
        for(int i = 0; i < count; i++) {
            int toIndex = (i + 1) * MAX_NUM_PER_PKG_CU;
            List<T> subList = moList.subList(i * MAX_NUM_PER_PKG_CU, toIndex > num ? num : toIndex);
            List<Object> listMapValue = PuerInvDAOUtil.buildMoMap(subList, moType);
            puerObjInvService.add(MOModelProcessor.getRestType(moType), listMapValue);
        }
        result.setData(uuidList);
        LOGGER.debug("Finish add  to puer inv " + result.toString());
        return result;
    }

    @Override
    public ResultRsp<String> delete(String uuid, Class moType) throws ServiceException {
        LOGGER.debug("Start delete " + uuid + " from puer inv.");
        ResultRsp result = ResultRsp.SUCCESS;
        puerObjInvService.deleteOne(MOModelProcessor.getRestType(moType), uuid);

        LOGGER.debug("Finish delete from puer inv " + result.toString());
        return result;
    }

    @Override
    public ResultRsp<List<String>> delete(List<T> moList, Class moType) throws ServiceException {
        LOGGER.debug("Start delete " + moList.toString() + " to puer inv.");

        List<String> uuidList = new ArrayList<String>();
        for(T mo : moList) {
            String uuid = MOModelProcessor.getUUID(mo, moType);
            if(uuid == null) {
                LOGGER.error("Cannot find mo uuid.");
                throw new InnerErrorServiceException("Cannot find mo uuid for " + mo.toString());
            }
            uuidList.add(uuid);
        }

        puerObjInvService.batchDelete(MOModelProcessor.getRestType(moType), uuidList);

        LOGGER.debug("Finish delete  to puer inv ");
        return ResultRsp.SUCCESS;
    }

    @Override
    public ResultRsp batchDelete(List<String> uuidList, Class moType) throws ServiceException {
        LOGGER.debug("Start batch delete " + uuidList.toString() + " to puer inv.");
        puerObjInvService.batchDelete(MOModelProcessor.getRestType(moType), uuidList);
        LOGGER.debug("Finish batch delete  to puer inv " + uuidList.toString());
        return ResultRsp.SUCCESS;
    }

    @Override
    public ResultRsp update(List<T> moList, Class moType) throws ServiceException {
        LOGGER.debug("Start update " + moList.toString() + " to puer inv.");
        ResultRsp result = ResultRsp.SUCCESS;
        int num = moList.size();
        int count = (num / MAX_NUM_PER_PKG_CU) + ((num % MAX_NUM_PER_PKG_CU) > 0 ? 1 : 0);
        for(int i = 0; i < count; i++) {
            int toIndex = (i + 1) * MAX_NUM_PER_PKG_CU;
            List<T> subList = moList.subList(i * MAX_NUM_PER_PKG_CU, toIndex > num ? num : toIndex);
            List<Object> listMapValue = PuerInvDAOUtil.buildMoMap(subList, moType);
            puerObjInvService.update(MOModelProcessor.getRestType(moType), listMapValue);
        }
        LOGGER.debug("Finish update  to puer inv " + result.toString());
        return result;
    }

    @Override
    public ResultRsp<List<T>> query(List<T> condition, Class moType) throws ServiceException {
        LOGGER.debug("Start query " + condition.toString() + " to puer inv.");

        Map<String, Object> condMap = PuerInvDAOUtil.buildQueryCondition(condition, moType);
        List<Map<String, Object>> rsp = puerObjInvService.queryAll(MOModelProcessor.getRestType(moType), condMap);
        ResultRsp result = PuerInvDAOUtil.buildQueryResult(moType, rsp);
        LOGGER.debug("Finish update  to puer inv " + result.toString());
        return result;
    }

    @Override
    public ResultRsp<List<T>> query(Class moType, QueryParams queryParams) throws ServiceException {
        List<Map<String, Object>> rsp = puerObjInvService.queryAll(MOModelProcessor.getRestType(moType), queryParams);
        ResultRsp result = PuerInvDAOUtil.buildQueryResult(moType, rsp);
        LOGGER.debug("Finish update  to puer inv " + result.toString());
        return result;
    }

    @Override
    public ResultRsp addRelation(RelationMO relation) throws ServiceException {
        LOGGER.debug("Start add relation" + relation.toString() + " to puer inv.");
        List<Map<String, Object>> listMapValue = new ArrayList<Map<String, Object>>();
        listMapValue.add(relation.buildRelationMap());

        List<RelationMO> dataList = new ArrayList<RelationMO>();
        dataList.add(relation);

        puerRelationService.addRelation(relation.getSrcResType(), dataList);

        LOGGER.debug("Finish add relation to puer inv " + relation.toString());
        return ResultRsp.SUCCESS;
    }

    @Override
    public ResultRsp deleteRelation(RelationMO relation) throws ServiceException {
        LOGGER.debug("Start delete relation" + relation.toString() + " to puer inv.");
        puerRelationService.deleteRelation(relation);
        LOGGER.debug("Finish delete relation to puer inv " + relation.toString());
        return ResultRsp.SUCCESS;
    }

    @Override
    public ResultRsp<T> query(String uuid, Class moType) throws ServiceException {
        LOGGER.debug("Start query " + uuid + " to puer inv.");
        Map<String, Object> rsp = puerObjInvService.get(uuid, moType, "");

        if(MapUtils.isEmpty(rsp)) {
            LOGGER.error("don't find " + uuid + " to puer inv.");
            SvcExcptUtil.throwNotFoundException();
        }

        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        dataList.add(rsp);

        ResultRsp<List<T>> getResult = PuerInvDAOUtil.buildQueryResult(moType, dataList);
        LOGGER.debug("Finish update  to puer inv ");
        ResultRsp<T> result = new ResultRsp<T>(ErrorCode.OVERLAYVPN_SUCCESS);

        if((getResult.getData() != null) && !getResult.getData().isEmpty()) {
            result.setData(getResult.getData().get(0));
        }
        return result;
    }

    @Override
    public long queryTotalNumber(String resType, String filter) throws ServiceException {
        return puerObjInvService.queryTotalNumber(resType, filter);
    }

    @Override
    public ResultRsp<List<T>> query(Class moType, BatchQueryFileterEntity filterEntity) throws ServiceException {
        List<Map<String, Object>> rsp = puerObjInvService.queryAll(MOModelProcessor.getRestType(moType), filterEntity);
        ResultRsp result = PuerInvDAOUtil.buildQueryResult(moType, rsp);
        LOGGER.debug("Finish update  to puer inv " + result.toString());
        return result;
    }

    @Override
    public ResultRsp<List<RelationPuerMO>> queryByRelation(RelationMO relation) throws ServiceException {
        return puerRelationService.queryRelation(relation);
    }

}
