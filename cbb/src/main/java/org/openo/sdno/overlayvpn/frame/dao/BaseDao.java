/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.frame.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MORelatedField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOSubField;
import org.openo.sdno.overlayvpn.model.v2.overlay.BaseModel;
import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.openo.sdno.util.reflect.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The dao class to deal with data base.<br>
 * 
 * @param <T> The model to be handled
 * @author
 * @version SDNO 0.5 Feb 3, 2017
 */
public class BaseDao<T extends BaseModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);

    /**
     * Query single data through uuid,and not support association query.<br>
     * 
     * @param uuid The uuid of object to be queried
     * @param resType The object resource type
     * @return The queried data
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    public T query(String uuid, Class<T> resType) throws ServiceException {
        return query(uuid, resType, false);
    }

    /**
     * Batch query data through uuid,and not support association query.<br>
     * 
     * @param uuids The uuid of object to be queried
     * @param resType The object resource type
     * @return The queried data
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    public List<T> query(List<String> uuids, Class<T> resType) throws ServiceException {
        List<T> models = new ArrayList<>();
        for(String uuid : uuids) {
            models.add(query(uuid, resType, false));
        }
        return models;
    }

    /**
     * Query single data through uuid,and support association query.<br>
     * 
     * @param uuid The uuid of object to be queried
     * @param resType The object resource type
     * @param hierarchy Whether support association query
     * @return The queried data
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public T query(String uuid, Class<T> resType, boolean hierarchy) throws ServiceException {
        T data = (T)new MssInvProxy(resType).query(uuid);
        if(hierarchy && data != null) {
            deepQuery(data);
        }
        return data;
    }

    /**
     * Query data list through conditions,and not support association query.<br>
     * 
     * @param conditions The querying conditions
     * @return The queried data list
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    public List<T> query(T condition) throws ServiceException {
        return query(condition, null).getObjects();
    }

    /**
     * Paging query data list through conditions,and not support association query.<br>
     * 
     * @param conditions The querying conditions
     * @param param The query parameter
     * @return The queried data contains the page information
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    public BatchQueryResult<T> query(T condition, BatchQueryParam param) throws ServiceException {
        return query(condition, param, false);
    }

    /**
     * Paging query data list through conditions,and support association query.<br>
     * 
     * @param conditions The querying conditions
     * @param param The query parameter
     * @param hierarchy Whether support association query
     * @return The queried data contains the page information
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public BatchQueryResult<T> query(T condition, BatchQueryParam param, boolean hierarchy) throws ServiceException {
        if(condition == null) {
            return new BatchQueryResult<>();
        }
        BatchQueryResult queryResult = new MssInvProxy(condition.getClass()).query(condition, param);
        if(hierarchy && CollectionUtils.isNotEmpty(queryResult.getObjects())) {
            deepQuery(castToBaseModel(queryResult.getObjects().toArray()));
        }
        return queryResult;
    }

    /**
     * Insert the data to data base.<br>
     * 
     * @param data The data to be inserted
     * @return The response model
     * @throws ServiceException when inserting failed
     * @since SDNO 0.5
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public T insert(T data) throws ServiceException {
        if(data == null) {
            return null;
        }
        initActionState(Arrays.asList(data));
        List response = new MssInvProxy(data.getClass()).insert(Arrays.asList(data));
        return CollectionUtils.isNotEmpty(response) ? ((T)response.get(0)) : null;
    }

    /**
     * Batch insert data list to data base.<br>
     * 
     * @param datas The data list to be inserted
     * @return The response model list
     * @throws ServiceException when inserting failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public List<T> insert(List<T> datas) throws ServiceException {
        if(CollectionUtils.isEmpty(datas)) {
            return datas;
        }
        initActionState(datas);
        return new MssInvProxy(datas.get(0).getClass()).insert(datas);
    }

    /**
     * Update the data in data base.<br>
     * 
     * @param uuid The uuid of the data to be updated
     * @param data The data to be updated
     * @return The response model
     * @throws ServiceException when updating failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public T update(String uuid, T data) throws ServiceException {
        if(data == null) {
            return null;
        }
        data.setId(null);
        return (T)new MssInvProxy(data.getClass()).update(uuid, data);
    }

    /**
     * Batch update data list in data base.<br>
     * 
     * @param datas The data list to be updated
     * @return The response model list
     * @throws ServiceException when updating failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public List<T> update(List<T> datas) throws ServiceException {
        if(CollectionUtils.isEmpty(datas)) {
            return datas;
        }
        return new MssInvProxy(datas.get(0).getClass()).update(datas);
    }

    /**
     * Delete the data in data base through uuid.<br>
     * 
     * @param uuid The uuid of the data to be deleted
     * @param resType The resource type of the data
     * @throws ServiceException when deleting failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public void delete(String uuid, Class<T> resType) throws ServiceException {
        T dbData = (T)new MssInvProxy(resType).query(uuid);
        if(dbData == null) {
            return;
        }
        new MssInvProxy(resType).delete(uuid);
    }

    /**
     * Batch delete data list in data base.<br>
     * 
     * @param uuids The uuid list to be deleted
     * @param resType The resource type of the data
     * @throws ServiceException when deleting failed
     * @since SDNO 0.5
     */
    public void delete(List<String> uuids, Class<T> resType) throws ServiceException {
        new MssInvProxy(resType).delete(uuids);
    }

    private BaseModel[] castToBaseModel(Object[] objs) {
        if(ArrayUtils.isEmpty(objs)) {
            return new BaseModel[0];
        }
        BaseModel[] result = new BaseModel[objs.length];
        for(int i = 0; i < objs.length; i++) {
            result[i] = (BaseModel)objs[i];
        }
        return result;
    }

    private void initActionState(List<T> datas) {
        if(CollectionUtils.isEmpty(datas) || !ServiceModel.class.isAssignableFrom(datas.get(0).getClass())) {
            return;
        }
        for(T data : datas) {
            ((ServiceModel)data).setActionState(ActionState.CREATING.getState());
        }
    }

    private void deepQuery(BaseModel... models) throws ServiceException {
        LOGGER.info("Load sub model,model = " + models.getClass());
        if(ArrayUtils.isEmpty(models)) {
            return;
        }
        List<Field> fields = JavaEntityUtil.getAllField(models[0].getClass());
        for(Field field : fields) {
            loadSubModel(field, models);
        }
    }

    private void loadSubModel(Field field, BaseModel... models) throws ServiceException {
        if(ArrayUtils.isEmpty(models) || !BaseDaoHelper.isSupportDeepQuery(field)) {
            return;
        }
        Class<?> subType = BaseDaoHelper.getSubModelType(field);
        if(subType == null || !BaseModel.class.isAssignableFrom(subType)) {
            LOGGER.warn("Sub model type invalid");
            return;
        }
        List<BaseModel> conds = BaseDaoHelper.buildQueryCond(field, subType, models);
        List<BaseModel> queryDatas = new ArrayList<>();
        for(BaseModel cond : conds) {
            queryDatas.addAll(BaseDaoHelper.querySubModel(cond));
        }
        if(CollectionUtils.isEmpty(queryDatas)) {
            return;
        }
        deepQuery(queryDatas.toArray(new BaseModel[0]));
        Map<String, List<BaseModel>> sortDataMap = sortQueryData(field, queryDatas, Arrays.asList(models));
        loadSubModel2Field(field, sortDataMap, models);
    }

    private Map<String, List<BaseModel>> sortQueryData(Field field, List<BaseModel> queryData, List<BaseModel> models) {
        Map<String, List<BaseModel>> result = new HashMap<>();
        if(CollectionUtils.isEmpty(queryData)) {
            return result;
        }
        MOSubField subAnno = BaseDaoHelper.getSubModeAnno(field);
        MORelatedField relationAnno = BaseDaoHelper.getRelationModeAnno(field);

        loadQueryDataByParentId(queryData, result, subAnno);
        if(relationAnno == null) {
            return result;
        }
        for(BaseModel model : models) {
            String parentId = model.getId();
            String subId = (String)JavaEntityUtil.getFieldValue(relationAnno.relationId(), model);
            if(StringUtils.isEmpty(subId)) {
                continue;
            }
            for(BaseModel qdata : queryData) {
                if(!subId.equals(qdata.getId())) {
                    continue;
                }
                if(!result.containsKey(parentId)) {
                    result.put(parentId, new ArrayList<BaseModel>());
                }
                result.get(parentId).add(qdata);
            }
        }
        return result;
    }

    private void loadSubModel2Field(Field field, Map<String, List<BaseModel>> sortDataMap, BaseModel... models) {
        for(BaseModel model : models) {
            List<BaseModel> sortData = sortDataMap.get(model.getId());
            if(CollectionUtils.isEmpty(sortData)) {
                continue;
            }
            Object subData = buildModelSubData(sortData, field);
            if(subData != null) {
                JavaEntityUtil.setFieldValue(field, model, subData);
            }
        }
    }

    private void loadQueryDataByParentId(List<BaseModel> queryData, Map<String, List<BaseModel>> result,
            MOSubField subAnno) {
        for(BaseModel qdata : queryData) {
            String parentId = null;
            if(subAnno != null) {
                parentId = (String)JavaEntityUtil.getFieldValue(subAnno.parentId(), qdata);
            }
            if(StringUtils.isEmpty(parentId)) {
                continue;
            }
            if(!result.containsKey(parentId)) {
                result.put(parentId, new ArrayList<BaseModel>());
            }
            result.get(parentId).add(qdata);
        }
    }

    private Object buildModelSubData(List<BaseModel> queryData, Field field) {
        if(CollectionUtils.isEmpty(queryData)) {
            return null;
        }
        Class<?> attrType = BaseDaoHelper.getAttrType(field);
        List<Object> subData = new ArrayList<>();
        if(BaseModel.class.isAssignableFrom(attrType)) {
            subData.addAll(queryData);
        } else {
            String subAttr = BaseDaoHelper.getAnnoSubAttr(field);
            for(Object obj : queryData) {
                subData.add(ReflectionUtil.getFieldValue(obj, subAttr));
            }
        }
        return BaseDaoHelper.isListType(field) ? subData : subData.get(0);
    }
}
