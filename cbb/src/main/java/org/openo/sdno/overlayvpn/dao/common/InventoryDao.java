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

package org.openo.sdno.overlayvpn.dao.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.PuerInvDAOImpl;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.PuerInvServicesImpl;
import org.openo.sdno.overlayvpn.inventory.sdk.model.QueryParams;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationMO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationPuerMO;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.openo.sdno.overlayvpn.util.dbresultprocess.FillDbResultRsp;
import org.openo.sdno.overlayvpn.util.objreflectoper.ObjectOperUtil;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * Inventory DAO class.<br>
 * 
 * @param <T> Inventory Model Class
 * @author
 * @version SDNO 0.5 2016-5-5
 */
public class InventoryDao<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryDao.class);

    @Autowired
    private PuerInvDAOImpl<T> invDao;

    @Autowired
    private PuerInvServicesImpl<T> invService;

    public void setInvDao(PuerInvDAOImpl<T> invDao) {
        this.invDao = invDao;
    }

    public PuerInvDAOImpl<T> getInvDao() {
        return invDao;
    }

    public PuerInvServicesImpl<T> getInvService() {
        return invService;
    }

    public void setInvService(PuerInvServicesImpl<T> invService) {
        this.invService = invService;
    }

    /**
     * Query data with filter field.<br>
     * 
     * @param clazz object type
     * @param filter filter
     * @param queryResultFields fields need be present in query result
     * @param <T> Inventory Model Class
     * @return query result
     * @throws ServiceException when query data failed.
     * @since SDNO 0.5
     */
    public ResultRsp<List<T>> queryByFilter(Class clazz, String filter, String queryResultFields)
            throws ServiceException {
        ResultRsp<List<T>> invResult = invDao.query(clazz, new QueryParams(filter, queryResultFields, null, null));
        if(invResult.isValid()) {
            // Success, translate error code to CloudVpn's success error code.
            return new ResultRsp<List<T>>(ErrorCode.OVERLAYVPN_SUCCESS, invResult.getData());
        }

        // Program will not execute to here. If the database operation fails, it
        // will throw
        // exceptions.
        ResultRsp<List<T>> retRsp = new ResultRsp<>(String.valueOf(invResult.getErrorCode()));
        FillDbResultRsp.fillQueryResultRsp(retRsp, "[Filter]" + filter + "[queryResultFields]" + queryResultFields);
        SvcExcptUtil.throwSvcExptionByResultRsp(retRsp);
        return retRsp;
    }

    /**
     * Query data by id and tenant id.<br>
     * 
     * @param clazz object type
     * @param uuid id
     * @param tenantId tenant id
     * @return query result
     * @throws ServiceException when query data failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
        // Construct filter.
        Map<String, Object> filterMap = new HashMap<String, Object>();
        if(StringUtils.hasLength(uuid)) {
            filterMap.put("uuid", Arrays.asList(uuid));
        }

        if(null != tenantId && StringUtils.hasLength(tenantId)) {
            filterMap.put("tenantId", Arrays.asList(tenantId));
        }

        String filter = JSONObject.fromObject(filterMap).toString();
        ResultRsp<List<T>> queryRsp = queryByFilter(clazz, filter, null);
        if(CollectionUtils.isNotEmpty(queryRsp.getData())) {
            return new ResultRsp<T>(queryRsp, queryRsp.getData().get(0));
        }
        return new ResultRsp<T>(queryRsp);
    }

    /**
     * Batch query data.<br>
     * 
     * @param clazz object type
     * @param filter filter
     * @return query result
     * @throws ServiceException when query data failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public ResultRsp<List<T>> batchQuery(Class clazz, String filter) throws ServiceException {
        return queryByFilter(clazz, filter, null);
    }

    /**
     * Batch query data.<br>
     * 
     * @param clazz object type
     * @param uuids id list
     * @return query result
     * @throws ServiceException when query data failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public ResultRsp<List<T>> batchQuery(Class clazz, List<String> uuids) throws ServiceException {
        if(CollectionUtils.isEmpty(uuids)) {
            return new ResultRsp<List<T>>(ErrorCode.OVERLAYVPN_SUCCESS, new ArrayList<T>());
        }

        // Construct filter.
        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("uuid", uuids);
        String filter = JSONObject.fromObject(filterMap).toString();
        return queryByFilter(clazz, filter, null);
    }

    /**
     * Delete data according to UUID. If UUID is null, do nothing and return
     * success.<br>
     * 
     * @param clazz object type
     * @param uuid UUID
     * @return operation result
     * @throws ServiceException when delete data failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public ResultRsp<String> delete(Class clazz, String uuid) throws ServiceException {
        if(!StringUtils.hasLength(uuid)) {
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }
        return batchDelete(clazz, Arrays.asList(uuid));
    }

    /**
     * Batch delete data according to UUID list.<br>
     * 
     * @param clazz object type
     * @param uuids list of UUIDs
     * @return operation result
     * @throws ServiceException when delete data failed.
     * @since SDNO 0.5
     */
    public ResultRsp<String> batchDelete(Class clazz, List<String> uuids) throws ServiceException {
        if(CollectionUtils.isEmpty(uuids)) {
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        ResultRsp result = invDao.batchDelete(uuids, clazz);

        if(result.isSuccess()) {
            // Success, translate error code to CloudVpn's success error code.
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        // Program will not execute to here. If the database operation fails, it
        // will throw
        // exceptions.
        ResultRsp<String> retRsp = new ResultRsp<>(String.valueOf(result.getErrorCode()));
        FillDbResultRsp.fillDeleteResultRsp(retRsp, "[UUID]" + uuids.get(0) + "...");
        SvcExcptUtil.throwSvcExptionByResultRsp(retRsp);
        return retRsp;
    }

    /**
     * Insert a single data.<br>
     * 
     * @param data data
     * @return operation result
     * @throws ServiceException when insert data failed.
     * @since SDNO 0.5
     */
    public ResultRsp<T> insert(T data) throws ServiceException {
        return new ResultRsp<>(batchInsert(Arrays.asList(data)), data);
    }

    /**
     * Batch insert data.<br>
     * 
     * @param dataList list of data
     * @return operation result
     * @throws ServiceException when insert data failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public ResultRsp<List<T>> batchInsert(List<T> dataList) throws ServiceException {
        if(CollectionUtils.isEmpty(dataList)) {
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        ResultRsp<List<T>> retResult = new ResultRsp<List<T>>();
        retResult = invService.add(dataList);

        if(retResult.isSuccess()) {
            // Success, translate error code to CloudVpn's success error code.
            return new ResultRsp<List<T>>(ErrorCode.OVERLAYVPN_SUCCESS, dataList);
        }

        // Program will not execute to here. If the database operation fails, it
        // will throw
        // exceptions.
        ResultRsp<List<T>> retRsp = new ResultRsp<>(String.valueOf(retResult.getErrorCode()));
        FillDbResultRsp.fillInsertResultRsp(retRsp, null);
        SvcExcptUtil.throwSvcExptionByResultRsp(retRsp);
        return retResult;
    }

    /**
     * Update data.<br>
     * 
     * @param data data to be updated
     * @param updateFieldListStr fields need to be updated
     * @return operation result
     * @throws ServiceException when update data failed.
     * @since SDNO 0.5
     */
    public ResultRsp<T> update(T data, String updateFieldListStr) throws ServiceException {
        if(null == data) {
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }
        return new ResultRsp<>(update(data.getClass(), Arrays.asList(data), updateFieldListStr), data);
    }

    /**
     * Batch update data.<br>
     * 
     * @param clazz object type
     * @param oriUpdateList data to be updated
     * @param updateFieldListStr fields need to be updated
     * @return operation result
     * @throws ServiceException when update data failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResultRsp<List<T>> update(Class clazz, List<T> oriUpdateList, String updateFieldListStr)
            throws ServiceException {
        if(CollectionUtils.isEmpty(oriUpdateList) || !StringUtils.hasLength(updateFieldListStr)) {
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        List<T> copyDataList = copyUpdateData(oriUpdateList, clazz, updateFieldListStr);

        ResultRsp retResult = invDao.update(copyDataList, clazz);
        if(retResult.isSuccess()) {
            // Success, translate error code to CloudVpn's success error code.
            return new ResultRsp<List<T>>(ErrorCode.OVERLAYVPN_SUCCESS, copyDataList);
        }

        // Program will not execute to here. If the database operation fails, it
        // will throw
        // exceptions.
        ResultRsp<List<T>> retRsp = new ResultRsp<>(String.valueOf(retResult.getErrorCode()));
        FillDbResultRsp.fillUpdateResultRsp(retRsp, null);
        SvcExcptUtil.throwSvcExptionByResultRsp(retRsp);
        return retResult;
    }

    /**
     * Copy out fields in update data list which need to be updated. UUID must
     * be copied.<br>
     * 
     * @param oriUpdateList data to be updated
     * @param tClass object type
     * @param updateFieldListStr fields need to be updated
     * @return operation result
     * @since SDNO 0.5
     */
    private List<T> copyUpdateData(List<T> oriUpdateList, Class<T> tClass, String updateFieldListStr) {
        // Construct query set.
        Set<String> updateFieldSet = new HashSet<>();
        String[] updateFieldArry = updateFieldListStr.split(CommConst.DEFAULT_STR_SEPARATOR);
        for(String temp : updateFieldArry) {
            updateFieldSet.add(temp);
        }
        // UUID can't be set to NULL.
        updateFieldSet.add("uuid");
        List<T> copyDataList = new ArrayList<T>(oriUpdateList.size());
        for(T updateItem : oriUpdateList) {
            try {
                T copyData = tClass.newInstance();
                copyDataList.add(copyData);

                List<Field> allField = JavaEntityUtil.getAllField(tClass);
                for(Field tempField : allField) {
                    // Get the field's setter
                    String setMethodName = ObjectOperUtil.getSetMethodByAttr(tempField.getName());
                    if(null == setMethodName) {
                        continue;
                    }

                    // Copy the fields which need to be updated from the
                    // original data.
                    if(updateFieldSet.contains(tempField.getName())) {
                        Object data = JavaEntityUtil.getFieldValue(tempField.getName(), updateItem);
                        ObjectOperUtil.setValueByMethod(copyData, tempField, setMethodName, data);
                    } else {
                        // Set other fields which do not need update to NULL.
                        ObjectOperUtil.setValueByMethod(copyData, tempField, setMethodName, null);
                    }
                }
            } catch(InstantiationException | IllegalAccessException e) {
                LOGGER.error("parser " + tClass.getName() + " from " + updateFieldListStr
                        + " failed for field is wrong or new instance failed.", e);
            }
        }

        return copyDataList;
    }

    /**
     * Add RelationMO.<br>
     * 
     * @param relationMO RelationMO Object
     * @return operation result
     * @throws ServiceException when add failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public ResultRsp<T> addRelation(RelationMO relationMO) throws ServiceException {
        if(null == relationMO) {
            LOGGER.error("relationMO is null");
            throw new ServiceException("relationMO is null");
        }
        return invDao.addRelation(relationMO);
    }

    /**
     * Delete RelationMO.<br>
     * 
     * @param relationMO RelationMO Object
     * @return operation result
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public ResultRsp<T> deleteRelation(RelationMO relationMO) throws ServiceException {
        if(null == relationMO) {
            LOGGER.error("relationMO is null");
            throw new ServiceException("relationMO is null");
        }
        return invDao.deleteRelation(relationMO);
    }

    /**
     * Query Relationship Objects.<br>
     * 
     * @param condition relationship of objects
     * @return objects queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public ResultRsp<List<RelationPuerMO>> queryByRelation(RelationMO condition) throws ServiceException {
        return invDao.queryByRelation(condition);
    }

}
