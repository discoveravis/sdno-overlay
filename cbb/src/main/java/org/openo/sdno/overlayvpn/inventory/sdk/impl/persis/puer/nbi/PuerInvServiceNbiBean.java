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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.nbi;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.inventory.sdk.DbOwerInfo;
import org.openo.sdno.overlayvpn.inventory.sdk.model.BatchQueryFileterEntity;
import org.openo.sdno.overlayvpn.inventory.sdk.model.QueryParams;
import org.openo.sdno.overlayvpn.inventory.sdk.model.QueryResponeData;
import org.openo.sdno.overlayvpn.inventory.sdk.util.MOModelProcessor;
import org.openo.sdno.rest.ResponseUtils;
import org.openo.sdno.result.InvRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Northbound interface implementation for inventory service.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class PuerInvServiceNbiBean extends PuerInvSuperNbiBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(PuerInvServiceNbiBean.class);

    private static final String DBURL = "/openoapi/sdnomss/v1/buckets/{0}/resources/";

    /**
     * Add resources into database.<br>
     * 
     * @param resType
     *            Resource type
     * @param listMapValue
     *            Collection of resource values
     * @return Collection of adding results
     * @throws ServiceException
     *             if add resources failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, List<Object>> add(String resType, List listMapValue) throws ServiceException {
        RestfulParametes restParametes = new RestfulParametes();
        RestfulResponse response;
        Map<String, List<Object>> responseValue = new HashMap<String, List<Object>>();

        try {
            restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

            Map dataMap = new HashMap();
            dataMap.put("objects", listMapValue);

            restParametes.setRawData(JsonUtil.toJson(dataMap));

            String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + resType + "/objects";

            response = RestfulProxy.post(url, restParametes);
            if(HttpCode.isSucess(response.getStatus())) {
                responseValue = ResponseUtils.transferResponse(response, Map.class);
            } else {
                LOGGER.warn("add fail!resType=" + resType);
                dealSvcException(response);
                throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
            }
        } catch(ServiceException e) {
            LOGGER.warn("add fail!resType=" + resType, e);
            throw new ServiceException(e);
        }
        return responseValue;
    }

    /**
     * Update resources in database.<br>
     * 
     * @param resType
     *            Resource type
     * @param listMapValue
     *            Collection of resource values
     * @throws ServiceException
     *             if update resources failed.
     * @since SDNO 0.5
     */
    public void update(String resType, List<Object> listMapValue) throws ServiceException {
        LOGGER.info("start update restype: " + resType);
        RestfulParametes restParametes = new RestfulParametes();

        try {
            Map<String, List<Object>> dataMap = new HashMap<String, List<Object>>();
            dataMap.put("objects", listMapValue);

            restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

            restParametes.setRawData(JsonUtil.toJson(dataMap));

            String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + resType + "/objects";

            RestfulResponse response = RestfulProxy.put(url, restParametes);
            if(HttpCode.isSucess(response.getStatus())) {
                LOGGER.info("finish update restype: " + resType + " size=" + listMapValue.size());
                return;
            } else {
                LOGGER.warn("update fail!resType=" + resType + ",size=" + listMapValue.size());
                dealSvcException(response);
                throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
            }
        } catch(ServiceException e) {
            LOGGER.warn("update fail!resType=" + resType + ",size=" + +listMapValue.size(), e);
            throw new ServiceException(e);
        }

    }

    /**
     * Query database by uuid.<br>
     * 
     * @param uuid
     *            The uuid
     * @param resType
     *            Resource type
     * @param attr
     *            attribute name
     * @return Query result
     * @throws ServiceException
     *             if query database failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, Object> get(String uuid, Class resType, String attr) throws ServiceException {
        String resourceName = MOModelProcessor.getRestType(resType);

        RestfulParametes restParametes = new RestfulParametes();
        String queryAttr = (attr.isEmpty() || "*".equals(attr)) ? "all" : attr;
        restParametes.put("attr", queryAttr);
        restParametes.put("querytype", "all");
        Map<String, Map<String, Object>> responseMap = new HashMap<String, Map<String, Object>>();

        RestfulResponse response;
        try {

            String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + resourceName + "/objects/" + uuid;
            response = RestfulProxy.get(url, restParametes);

            if(HttpCode.isSucess(response.getStatus())) {
                responseMap = ResponseUtils.transferResponse(response, Map.class);
            } else {
                LOGGER.warn("get fail!resType=" + resourceName + ",uuid=" + uuid);
                dealSvcException(response);
                throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
            }
        } catch(ServiceException e) {
            LOGGER.warn("get fail!resType=" + resourceName + ",uuid=" + uuid + ",attr=" + attr, e);
            throw new ServiceException(e);
        }

        return responseMap.get("object");
    }

    /**
     * Query all resources by resource type.<br>
     * 
     * @param resType
     *            Resource type
     * @param queryParams
     *            Query parameters
     * @return Collection of query results
     * @throws ServiceException
     *             if query all resources failed.
     * @since SDNO 0.5
     */
    public List<Map<String, Object>> queryAll(String resType, QueryParams queryParams) throws ServiceException {
        RestfulParametes restParametes = buildQueryAllParamToFileterEntity(queryParams);

        QueryResponeData queryResponeData = new QueryResponeData();

        restParametes.put("pagesize", "1000");
        restParametes.put("pagenum", "0");

        String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + resType + "/objects";

        RestfulResponse response = RestfulProxy.get(url, restParametes);
        if(HttpCode.isSucess(response.getStatus())) {
            queryResponeData = ResponseUtils.transferResponse(response, QueryResponeData.class);

            if(queryResponeData.getTotalPageNum() > 1) {
                for(int i = 1; i < queryResponeData.getTotalPageNum(); i++) {
                    queryResponeData.getObjects().addAll(queryOhterPage(resType, restParametes, String.valueOf(i)));
                }
            }
        } else {
            LOGGER.warn("queryAll fail!resType=" + resType + "," + queryParams);
            dealSvcException(response);
            throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
        }
        return queryResponeData.getObjects();
    }

    /**
     * Query all resources by resource type.<br>
     * 
     * @param resType
     *            Resource type
     * @param condMap
     *            Collection of query conditions
     * @return Collection of query results
     * @throws ServiceException
     *             if query all resources failed.
     * @since SDNO 0.5
     */
    public List<Map<String, Object>> queryAll(String resType, Map<String, Object> condMap) throws ServiceException {
        String filter = condMap.isEmpty() ? "" : JsonUtil.toJson(condMap);
        return this.queryAll(resType, new QueryParams(filter, "", null, null));
    }

    /**
     * Query resources by Uuid.<br>
     * 
     * @param resType
     *            Resource type
     * @param uuid
     *            Collection of Uuid
     * @return Collection of query results
     * @throws ServiceException
     *             if query resources failed.
     * @since SDNO 0.5
     */
    public List<Map<String, Object>> queryByUUID(String resType, List<String> uuid) throws ServiceException {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        int num = uuid.size();
        int count = (num / QUERY_PAGE_NUM) + ((num % QUERY_PAGE_NUM) > 0 ? 1 : 0);
        for(int i = 0; i < count; i++) {
            int toIndex = (i + 1) * QUERY_PAGE_NUM;
            List<String> subList = uuid.subList(i * QUERY_PAGE_NUM, toIndex > num ? num : toIndex);

            result.addAll(queryByUUIDNbi(resType, subList));
        }
        return result;

    }

    /**
     * Query resources by Uuid.<br>
     * 
     * @param resType
     *            Resource type
     * @param uuid
     *            Collection of Uuid
     * @return Collection of query results
     * @throws ServiceException
     *             if query resources failed.
     * @since SDNO 0.5
     */
    public List<Map<String, Object>> queryByUUIDNbi(String resType, List<String> uuid) throws ServiceException {
        String filter = "{\"uuid\":" + JsonUtil.toJson(uuid) + "}";
        QueryParams queryParams = new QueryParams(filter, "all", null, null);
        return this.queryAll(resType, queryParams);
    }

    /**
     * Delete one resource by Uuid.<br>
     * 
     * @param resType
     *            Resource type
     * @param uuid
     *            The Uuid
     * @throws ServiceException
     *             if delete resource failed.
     * @since SDNO 0.5
     */
    public void deleteOne(String resType, String uuid) throws ServiceException {
        RestfulParametes restParametes = new RestfulParametes();

        try {

            String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + resType + "/objects/" + uuid;

            RestfulResponse response = RestfulProxy.delete(url, restParametes);
            if(HttpCode.isSucess(response.getStatus())) {
                return;
            } else {
                LOGGER.warn("deleteOne fail!resType=" + resType + ",uuid=" + uuid);
                dealSvcException(response);
                throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
            }
        } catch(ServiceException e) {
            LOGGER.warn("deleteOne fail!resType=" + resType + ",uuid=" + uuid, e);
            throw new ServiceException(e);
        }
    }

    /**
     * Query total number of records in a table.<br>
     * 
     * @param resType
     *            Resource type
     * @param filter
     *            The filter
     * @return Total number of records
     * @throws ServiceException
     *             if query total number of records failed.
     * @since SDNO 0.5
     */
    public long queryTotalNumber(String resType, String filter) throws ServiceException {
        RestfulParametes restParametes = new RestfulParametes();

        if((filter != null) && !filter.isEmpty()) {
            restParametes.put("filter", filter);
        }
        long total = 0;
        try {

            String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName())
                    + resType.toLowerCase(Locale.getDefault()) + "/statistics";

            RestfulResponse response = RestfulProxy.get(url, restParametes);
            if(HttpCode.isSucess(response.getStatus())) {
                total = Long.parseLong(response.getResponseContent());
            } else {
                LOGGER.warn("queryTotalNumber fail!resType=" + resType);
                dealSvcException(response);
                throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
            }
        } catch(ServiceException e) {
            LOGGER.warn("queryTotalNumber fail!resType=" + resType, e);
            throw new ServiceException(e);
        }
        return total;
    }

    /**
     * Batch delete resources in database.<br>
     * 
     * @param resType
     *            Resource type
     * @param uuidList
     *            Uuid list
     * @return Collection of batch deleting results
     * @throws ServiceException
     *             if delete resources failed.
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public InvRsp<List<Map<String, Object>>> batchDelete(String resType, List<String> uuidList)
            throws ServiceException {
        InvRsp<List<Map<String, Object>>> responseValue = null;
        RestfulParametes restfulParametes = new RestfulParametes();

        StringBuffer ids = new StringBuffer();
        int size = uuidList.size();
        for(int i = 0; i < size; i++) {
            if(i == (size - 1)) {
                ids.append(uuidList.get(i));
            } else {
                ids.append(uuidList.get(i)).append(',');
            }
        }

        restfulParametes.put("ids", ids.toString());
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + resType + "/objects";

        RestfulResponse response = RestfulProxy.delete(url, restfulParametes);
        if(HttpCode.isSucess(response.getStatus())) {
            // responseValue = ResponseUtils.transferResponse(response,
            // InvRsp.class);
        } else {
            LOGGER.warn("batchDelete fail!resType=" + resType + ",uuidList=" + uuidList);
            dealSvcException(response);
            throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
        }
        return responseValue;
    }

    /**
     * Query database by filterEntity.<br>
     * 
     * @param resType
     *            Resource type
     * @param filterEntity
     *            The query filter
     * @return Collection of query results
     * @throws ServiceException
     *             if query database failed.
     * @since SDNO 0.5
     */
    public List<Map<String, Object>> queryAll(String resType, BatchQueryFileterEntity filterEntity)
            throws ServiceException {
        RestfulParametes restParametes = new RestfulParametes();

        restParametes.put("pagesize", "1000");
        restParametes.put("pagenum", "0");

        restParametes.put("filter", JsonUtil.toJson(filterEntity));

        String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + resType + "/objects";
        RestfulResponse response = RestfulProxy.get(url, restParametes);

        QueryResponeData queryResponeData = new QueryResponeData();
        if(HttpCode.isSucess(response.getStatus())) {
            queryResponeData = ResponseUtils.transferResponse(response, QueryResponeData.class);
        } else {
            LOGGER.warn("queryAll fail!resType=" + resType + "," + filterEntity);
            dealSvcException(response);
            throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
        }

        return queryResponeData.getObjects();
    }

    /**
     * Query data in other page.<br>
     * 
     * @param resType
     *            Resource type
     * @param restParametes
     *            Restful parameters
     * @param currentPage
     *            Current page
     * @return Collection of query results
     * @throws ServiceException
     *             if query data failed.
     * @since SDNO 0.5
     */
    public List<Map<String, Object>> queryOhterPage(String resType, RestfulParametes restParametes, String currentPage)
            throws ServiceException {
        QueryResponeData queryResponeData = new QueryResponeData();
        restParametes.put("pagenum", currentPage);

        String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + resType + "/objects";

        RestfulResponse response = RestfulProxy.get(url, restParametes);
        if(HttpCode.isSucess(response.getStatus())) {
            queryResponeData = ResponseUtils.transferResponse(response, QueryResponeData.class);
        } else {
            LOGGER.warn("queryAll fail!resType=" + resType);
            dealSvcException(response);
            throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
        }
        return queryResponeData.getObjects();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private RestfulParametes buildQueryAllParamToFileterEntity(QueryParams queryParams) throws ServiceException {
        RestfulParametes restParametes = queryParams.convertRestfulParametes();
        restParametes.put("querytype", "all");

        BatchQueryFileterEntity batchQueryFileterEntity = new BatchQueryFileterEntity();

        if(StringUtils.hasLength(queryParams.getFilter())) {
            Map<String, List> filterMap = new HashMap<String, List>();

            filterMap = JsonUtil.fromJson(queryParams.getFilter(), Map.class);

            if(MapUtils.isEmpty(filterMap)) {
                batchQueryFileterEntity.setFilterData("");
                batchQueryFileterEntity.setFilterDsc("");

                restParametes.put("filter", JsonUtil.toJson(batchQueryFileterEntity));
                return restParametes;
            }

            StringBuffer filterDsc = new StringBuffer();

            Iterator iter = filterMap.keySet().iterator();

            Map<String, Object> entriyFilter = new HashMap<String, Object>();

            while(iter.hasNext()) {
                String key = iter.next().toString();
                List values = filterMap.get(key);

                if(null == values) {
                    continue;
                }

                int num = 1;
                int size = values.size();

                filterDsc.append('(');
                for(Object obj : values) {
                    if(size == num) {
                        filterDsc.append(key).append("=':").append(key).append(num).append('\'');
                    } else {
                        filterDsc.append(key).append("=':").append(key).append(num).append("' or ");
                    }

                    entriyFilter.put(key + num, obj);

                    num++;
                }
                filterDsc.append(')');

                if(iter.hasNext()) {
                    filterDsc.append(" and ");
                }
            }

            batchQueryFileterEntity.setFilterDsc(filterDsc.toString());
            batchQueryFileterEntity.setFilterData(JsonUtil.toJson(entriyFilter));
        } else {
            batchQueryFileterEntity.setFilterData("");
            batchQueryFileterEntity.setFilterDsc("");
        }

        restParametes.put("filter", JsonUtil.toJson(batchQueryFileterEntity));

        return restParametes;
    }

    private void dealSvcException(RestfulResponse response) throws ServiceException {
        if((null != response.getResponseContent()) && (response.getResponseContent().contains("exceptionId"))) {
            LOGGER.error("puer process return status:" + response.getStatus() + ",exception:"
                    + response.getResponseContent());
            ResponseUtils.checkResonseAndThrowException(response);
        }
    }
}
