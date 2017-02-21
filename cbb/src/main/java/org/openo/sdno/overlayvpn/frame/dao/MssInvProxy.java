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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.frame.dao.constant.MssConsts;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryFilterEntity;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchRequestData;
import org.openo.sdno.overlayvpn.frame.dao.model.InvMapping;
import org.openo.sdno.overlayvpn.inventory.sdk.DbOwerInfo;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOJsonField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.inventory.sdk.util.MOModelProcessorUtil;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.overlayvpn.util.rsp.ResponseUtils;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The mss service proxy to inventory data base.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
public class MssInvProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(MssInvProxy.class);

    private String resType;

    private String bucketName;

    private String owner;

    private Class<?> modelType;

    /**
     * Constructor.<br>
     * 
     * @param modelType The resource type class
     * @since SDNO 0.5
     */
    public MssInvProxy(Class<?> modelType) {
        super();
        MOResType invAnno = modelType.getAnnotation(MOResType.class);
        if(invAnno == null) {
            this.resType = MOModelProcessorUtil.buildRestType(modelType);
        } else {
            this.resType = invAnno.infoModelName();
        }
        this.bucketName = DbOwerInfo.getBucketName();
        this.owner = DbOwerInfo.getOwerInfo();
        this.modelType = modelType;
    }

    /**
     * Insert data list to data base.<br>
     * 
     * @param datas The data list to be inserted
     * @return The parsed response list
     * @throws ServiceException when translating object to json failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public List insert(List datas) throws ServiceException {
        String url = String.format(MssConsts.URL_BATCH, bucketName, resType);
        if(CollectionUtils.isEmpty(datas)) {
            return datas;
        }
        BatchRequestData requestData = new BatchRequestData(parseRequest(datas));
        RestfulResponse response = getRestProxy().post(url, JsonUtils.toJson(requestData), null);
        return parseResponseList(response);
    }

    /**
     * Delete data through uuid.<br>
     * 
     * @param uuid The uuid of the data to be deleted
     * @throws ServiceException when the response status is not success
     * @since SDNO 0.5
     */
    public void delete(String uuid) throws ServiceException {
        String url = String.format(MssConsts.URL_SINGLE, bucketName, resType, uuid);
        RestfulResponse response = getRestProxy().delete(url, null, null);
        ResponseUtils.checkResponseAndThrowException(response);
    }

    /**
     * Batch delete data through uuid list.<br>
     * 
     * @param uuids The uuid list of the data to be deleted
     * @throws ServiceException when the response status is not success
     * @since SDNO 0.5
     */
    public void delete(List<String> uuids) throws ServiceException {
        if(CollectionUtils.isEmpty(uuids)) {
            LOGGER.info("Delete models failed,uuid list is empty");
            return;
        }
        String url = String.format(MssConsts.URL_BATCH, bucketName, resType);
        RestfulParametes restParam = new RestfulParametes();
        restParam.put("ids", listToString(uuids));
        RestfulResponse response = getRestProxy().delete(url, null, restParam);
        ResponseUtils.checkResponseAndThrowException(response);
    }

    /**
     * Update data through uuid.<br>
     * 
     * @param uuid The uuid of the data to be updated
     * @param data The data to update
     * @return The parsed response object
     * @throws ServiceException when uuid or data is null,or resource is not exist
     * @since SDNO 0.5
     */
    public Object update(String uuid, Object data) throws ServiceException {
        if(StringUtils.isEmpty(uuid) || data == null) {
            LOGGER.error(
                    "Mss.update.error,update model failed,uuid or data is null:uuid=" + uuid + ",model=" + resType);
            throw new InnerErrorServiceException(
                    "Mss.update.error,update model failed,uuid or data is null:uuid=" + uuid + ",model=" + resType);
        }
        Object dbData = query(uuid);
        if(dbData == null) {
            LOGGER.error("Mss.update.error,update model failed,res is not exist:uuid=" + uuid + ",model=" + resType);
            throw new InnerErrorServiceException(
                    "Mss.update.error,update model failed,res is not exist:uuid=" + uuid + ",model=" + resType);
        }
        String url = String.format(MssConsts.URL_SINGLE, bucketName, resType, uuid);
        RestfulResponse response = getRestProxy().put(url, JsonUtils.toJson(parseRequest(data)), null);
        return parseResponseObj(response);
    }

    /**
     * Batch update data.<br>
     * 
     * @param datas The data list to update
     * @return The parsed response list
     * @throws ServiceException when translating object to json failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public List update(List datas) throws ServiceException {
        if(CollectionUtils.isEmpty(datas)) {
            LOGGER.info("Batch update model failed,model list is empty.");
            return datas;
        }
        String url = String.format(MssConsts.URL_BATCH, bucketName, resType);
        BatchRequestData requestData = new BatchRequestData(parseRequest(datas));
        RestfulResponse response = getRestProxy().put(url, JsonUtils.toJson(requestData), null);
        return parseResponseList(response);
    }

    /**
     * Query data through uuid.<br>
     * 
     * @param uuid The uuid of the data to be queried
     * @return The parsed response object if the response status is success,otherwise return null
     * @throws ServiceException when translating object to json failed
     * @since SDNO 0.5
     */
    public Object query(String uuid) throws ServiceException {
        if(StringUtils.isEmpty(uuid)) {
            LOGGER.info("Query model failed,uuid is null:model=" + resType);
            return null;
        }
        String url = String.format(MssConsts.URL_SINGLE, bucketName, resType, uuid);
        RestfulParametes parms = new RestfulParametes();
        RestfulResponse response = getRestProxy().get(url, null, parms);

        return ResponseUtils.validHttpStatus(response.getStatus()) ? parseResponseObj(response) : null;
    }

    /**
     * Batch query data by conditions and query parameters.<br>
     * 
     * @param conditions The query conditions
     * @param param The query parameters
     * @return The queried result
     * @throws ServiceException when translating object to json failed
     * @since SDNO 0.5
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public BatchQueryResult query(Object condition, BatchQueryParam param) throws ServiceException {
        String url = String.format(MssConsts.URL_BATCH, bucketName, resType);
        if(param == null) {
            param = new BatchQueryParam();
        }
        RestfulParametes restParam = param.toRestParam(null);
        BatchQueryFilterEntity filter = ModelInvUtil.buildComlexFilter(condition);
        restParam.put("filter", JsonUtils.toJson(filter));
        RestfulResponse response = getRestProxy().get(url, null, restParam);
        ResponseUtils.checkResponseAndThrowException(response);
        BatchQueryResult queryResult =
                JsonUtils.fromJson(response.getResponseContent(), new TypeReference<BatchQueryResult>() {});
        if(queryResult == null) {
            LOGGER.warn("Query result is null.");
            queryResult = new BatchQueryResult();
        }
        queryResult.setObjects(parseResponseList(response));
        return queryResult;
    }

    private MssRestProxy getRestProxy() {
        return new MssRestProxy(owner);
    }

    private Object parseResponseObj(RestfulResponse response) throws ServiceException {
        ResponseUtils.checkResponseAndThrowException(response);
        String jobj = JsonUtils.getJsonByKey(response.getResponseContent(), MssConsts.KEY_OBJECT);
        Map<String, Object> respData = JsonUtils.parseJson2SimpleMap(jobj);
        if(MapUtils.isEmpty(respData)) {
            return null;
        }
        Map<String, InvMapping> invMapping = getInvModelMapping();
        Map<String, Object> modelData = buildModelData(respData, invMapping);
        return JsonUtils.fromJson(JsonUtils.toJson(modelData), modelType);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List parseResponseList(RestfulResponse response) throws ServiceException {
        ResponseUtils.checkResponseAndThrowException(response);
        String jobj = JsonUtils.getJsonByKey(response.getResponseContent(), MssConsts.KEY_OBJECTS);
        List<Map<String, Object>> respList = JsonUtils.parseJson2MapList(jobj);
        if(CollectionUtils.isEmpty(respList)) {
            return new ArrayList<>();
        }
        List result = new ArrayList<>();
        Map<String, InvMapping> invMapping = getInvModelMapping();
        for(Map<String, Object> respData : respList) {
            Map<String, Object> modelData = buildModelData(respData, invMapping);
            result.add(JsonUtils.fromJson(JsonUtils.toJson(modelData), modelType));
        }
        return result;
    }

    @SuppressWarnings("rawtypes")
    private List<Map<String, Object>> parseRequest(List models) throws ServiceException {
        if(CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> requests = new ArrayList<>();
        Map<String, InvMapping> modelMapping = getModelInvMapping();
        List<Field> fields = getAllInvFields(models.get(0).getClass());
        for(Object model : models) {
            requests.add(buildRequestMap(modelMapping, fields, model));
        }
        return requests;
    }

    private Map<String, Object> parseRequest(Object model) throws ServiceException {
        if(model == null) {
            LOGGER.info("Parse model to request failed,model invalid.");
            return new HashMap<>();
        }
        Map<String, InvMapping> modelMapping = getModelInvMapping();
        List<Field> fields = getAllInvFields(model.getClass());
        Map<String, Object> reqMap = buildRequestMap(modelMapping, fields, model);
        return reqMap;
    }

    private Map<String, InvMapping> getModelInvMapping() {
        Map<String, InvMapping> mapData = new HashMap<>();
        List<Field> fields = getAllInvFields(modelType);
        for(Field field : fields) {
            InvMapping invMapping = ModelInvUtil.getModelInvMapping(field);
            mapData.put(field.getName(), invMapping);
        }
        return mapData;
    }

    private Map<String, InvMapping> getInvModelMapping() {
        Map<String, InvMapping> mapData = new HashMap<>();
        List<Field> fields = getAllInvFields(modelType);
        for(Field field : fields) {
            InvMapping invMapping = ModelInvUtil.getModelInvMapping(field);
            mapData.put(invMapping.getInvName(), invMapping);
        }
        return mapData;
    }

    private Map<String, Object> buildModelData(Map<String, Object> invData, Map<String, InvMapping> invMappings)
            throws ServiceException {
        if(MapUtils.isEmpty(invData)) {
            return invData;
        }
        Map<String, Object> modelData = new HashMap<>();
        for(String invName : invData.keySet()) {
            InvMapping invMapping = invMappings.get(invName);
            if(invMapping == null) {
                continue;
            }
            Object invValue = invData.get(invName);
            Object modelValue = invMapping.getModelValue(invValue);
            modelData.put(invMapping.getAttrName(), modelValue);
        }
        return modelData;
    }

    private Map<String, Object> buildRequestMap(Map<String, InvMapping> modelMappings, List<Field> fields, Object model)
            throws ServiceException {
        Map<String, Object> reqMap = new HashMap<>();
        for(Field field : fields) {
            Object fieldValue = JavaEntityUtil.getFieldValue(field.getName(), model);
            if(fieldValue == null) {
                continue;
            }
            InvMapping invMapping = modelMappings.get(field.getName());
            if(invMapping == null) {
                continue;
            }
            String invValue = invMapping.getInvValue(fieldValue);
            reqMap.put(invMapping.getInvName(), invValue);
        }
        return reqMap;
    }

    @SuppressWarnings("rawtypes")
    private List<Field> getAllInvFields(Class resType) {
        List<Field> fields = JavaEntityUtil.getAllField(resType);
        Iterator<Field> iterator = fields.iterator();
        while(iterator.hasNext()) {
            Field field = iterator.next();
            if(isNotInvField(field)) {
                iterator.remove();
            }
        }
        return fields;
    }

    private boolean isNotInvField(Field field) {
        NONInvField nonInvAnno = field.getAnnotation(NONInvField.class);
        if(nonInvAnno != null) {
            return true;
        }
        MOJsonField jsonInvAnno = field.getAnnotation(MOJsonField.class);
        if(jsonInvAnno != null) {
            return false;
        }
        int modifier = field.getModifiers();
        if(Modifier.isStatic(modifier) || Modifier.isFinal(modifier)) {
            return true;
        }
        Class<?> type = field.getType();
        if(!isBasicType(type)) {
            return true;
        }
        return false;
    }

    private boolean isBasicType(Class<?> type) {
        if(type == null) {
            return false;
        }
        if(type.isPrimitive()) {
            return true;
        }
        for(Class<?> basicType : MssConsts.BASIC_TYPES) {
            if(basicType.getName().equals(type.getName())) {
                return true;
            }
        }
        return false;
    }

    private String listToString(List<String> attrList) {
        if(CollectionUtils.isEmpty(attrList)) {
            return "";
        }
        Set<String> attrs = new HashSet<>(attrList);
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for(String attr : attrs) {
            if(StringUtils.isEmpty(attr)) {
                continue;
            }
            if(!first) {
                builder.append(',');
            }
            builder.append(attr);
            first = false;
        }
        return builder.toString();
    }
}
