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

package org.openo.sdno.overlayvpn.res;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.consts.HttpCode;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.security.authentication.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dao class of AllocIdResource.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-7-20
 */
public class AllocIdResourceInvDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllocIdResourceInvDao.class);

    private static final String RESOURCE_MSS_URL =
            "/openoapi/sdnomss/v1/buckets/compositevpndb/resources/allocidresource/objects";

    private static final String OBJECTS_KEY = "objects";

    /**
     * Batch insert resource data.<br/>
     * 
     * @param resourceList resource data
     * @throws ServiceException when insert failed
     * @since SDNO 0.5
     */
    public void batchInsert(List<AllocIdResource> resourceList) throws ServiceException {
        if(CollectionUtils.isEmpty(resourceList)) {
            LOGGER.warn("No resource data need to insert!!");
            return;
        }

        RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader(HttpContext.CONTENT_TYPE_HEADER, HttpContext.MEDIA_TYPE_JSON);

        for(AllocIdResource curResource : resourceList) {
            curResource.setUuid(UuidUtils.createUuid());
        }

        Map<String, Object> resourceObject = new HashMap<String, Object>();
        resourceObject.put(OBJECTS_KEY, resourceList);
        restParametes.setRawData(JsonUtil.toJson(resourceObject));

        RestfulResponse response = RestfulProxy.post(RESOURCE_MSS_URL, restParametes);
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("Add Id Resource return error!!");
            throw new ServiceException("Add Id Resource return error");
        }
    }

    /**
     * Batch delete resource data.<br/>
     * 
     * @param resourceList resource data
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public void batchDelete(List<AllocIdResource> resourceList) throws ServiceException {

        if(CollectionUtils.isEmpty(resourceList)) {
            LOGGER.warn("No resource data need to delete!!");
            return;
        }

        @SuppressWarnings("unchecked")
        List<String> uuidList = new ArrayList<String>(CollectionUtils.collect(resourceList, new Transformer() {

            @Override
            public Object transform(Object arg0) {
                return ((AllocIdResource)arg0).getUuid();
            }
        }));

        StringBuffer resourceIds = new StringBuffer();
        int size = uuidList.size();
        for(int i = 0; i < size; i++) {
            if(i == (size - 1)) {
                resourceIds.append(uuidList.get(i));
            } else {
                resourceIds.append(uuidList.get(i)).append(',');
            }
        }

        RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader(HttpContext.CONTENT_TYPE_HEADER, HttpContext.MEDIA_TYPE_JSON);
        restParametes.put("ids", resourceIds.toString());

        RestfulResponse response = RestfulProxy.delete(RESOURCE_MSS_URL, restParametes);
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("Delete Id Resource return error!!");
            throw new ServiceException("Delete Id Resource return error");
        }
    }

    /**
     * Batch query AllocIdResource Data.<br/>
     * 
     * @param poolName resource pool name
     * @param idList list of resource id
     * @return AllocIdResource Object queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public ResultRsp<List<AllocIdResource>> batchQuery(String poolName, List<Long> idList) throws ServiceException {

        if(StringUtils.isEmpty(poolName)) {
            LOGGER.error("param is invalid!!");
            return new ResultRsp<List<AllocIdResource>>(ErrorCode.OVERLAYVPN_DATABASE_QUERY_FAILED);
        }

        RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader(HttpContext.CONTENT_TYPE_HEADER, HttpContext.MEDIA_TYPE_JSON);
        RestfulResponse response = RestfulProxy.get(RESOURCE_MSS_URL, restParametes);
        if(!HttpCode.isSucess(response.getStatus()) || StringUtils.isEmpty(response.getResponseContent())) {
            LOGGER.error("Query Id Resource return error!!");
            throw new ServiceException("Query Id Resource return error");
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> responseMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
        Object responseObject = responseMap.get(OBJECTS_KEY);
        String responseObjectStr = JsonUtil.toJson(responseObject);
        List<AllocIdResource> resourceList =
                JsonUtil.fromJson(responseObjectStr, new TypeReference<List<AllocIdResource>>() {});
        if(resourceList == null) {
            LOGGER.error("Query Id Resource failed!!");
            return new ResultRsp<List<AllocIdResource>>(ErrorCode.OVERLAYVPN_DATABASE_QUERY_FAILED);
        }

        List<AllocIdResource> resultList = new ArrayList<AllocIdResource>();
        for(AllocIdResource idResource : resourceList) {
            String curPoolName = idResource.getPoolname();
            Long curId = idResource.getIdres();
            if(poolName.equals(curPoolName)) {
                if(CollectionUtils.isEmpty(idList)) {
                    resultList.add(idResource);
                } else if(idList.contains(curId)) {
                    resultList.add(idResource);
                }
            }
        }

        return new ResultRsp<List<AllocIdResource>>(ErrorCode.OVERLAYVPN_SUCCESS, resultList);
    }

}
