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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.inventory.sdk.DbOwerInfo;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationMO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationPuerMO;
import org.openo.sdno.rest.ResponseUtils;
import org.openo.sdno.result.InvRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Northbound interface for inventory relations.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class PuerInvRelationNbiBean extends PuerInvSuperNbiBean {

    private static final String DBURL = "/openoapi/sdnomss/v1/buckets/{0}/resources/";

    private static final Logger LOGGER = LoggerFactory.getLogger(PuerInvRelationNbiBean.class);

    /**
     * Add relative relation.<br>
     * 
     * @param relationType Relation type
     * @param listValue Collection of relation values
     * @throws ServiceException if send restful request failed.
     * @since SDNO 0.5
     */
    public void addRelation(String relationType, List<RelationMO> listValue) throws ServiceException {
        RestfulParametes restParametes = new RestfulParametes();

        RestfulResponse response;
        try {

            restParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

            Map<String, List> relationMap = new HashMap<String, List>();
            relationMap.put("relationships", RelationPuerMO.convert(listValue));

            restParametes.setRawData(JsonUtil.toJson(relationMap));

            String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + relationType + "/relationships";

            response = RestfulProxy.post(url, restParametes);
            if(HttpCode.isSucess(response.getStatus())) {
                return;
            } else {
                LOGGER.warn("addRelation fail!relationType=" + relationType);
                dealSvcException(response);
                throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
            }
        } catch(ServiceException e) {
            LOGGER.warn("addRelation fail!relationType=" + relationType, e);
            throw new ServiceException(e);
        }

    }

    /**
     * Query the data in relation table by sepecified Uuid.<br>
     * 
     * @param resType Resource type
     * @param dstType Destination type
     * @param srcUUId Source UUid
     * @param dstUUIdList List of destination UUId
     * @return Collection of query result
     * @throws ServiceException if query data failed.
     * @since SDNO 0.5
     */
    public InvRsp<List<Map<String, Object>>> getRelationObject(String resType, String dstType, String srcUUId,
            List<String> dstUUIdList) throws ServiceException {
        InvRsp<List<Map<String, Object>>> result = new InvRsp<List<Map<String, Object>>>();
        List<String> uuids = dstUUIdList;
        int num = uuids.size();
        int count = (num / QUERY_PAGE_NUM) + ((num % QUERY_PAGE_NUM) > 0 ? 1 : 0);
        for(int i = 0; i < count; i++) {
            int toIndex = (i + 1) * QUERY_PAGE_NUM;
            List<String> subList = uuids.subList(i * QUERY_PAGE_NUM, toIndex > num ? num : toIndex);
            InvRsp<List<Map<String, Object>>> rsp = getRelationObjectNbi(resType, dstType, srcUUId, subList);
            if((null != rsp) && rsp.isSucess()) {
                if(result.getData() != null) {
                    result.getData().addAll(rsp.getData());
                } else {
                    result = rsp;
                }
            } else {
                LOGGER.warn("Query " + resType + "-" + dstType + " failed.");
                return rsp;
            }
        }
        return result;
    }

    /**
     * Delete relation model, not include the contents in dst_type table.<br>
     * 
     * @param relation Relation model
     * @throws ServiceException if send restful request failed.
     * @since SDNO 0.5
     */
    public void deleteRelation(RelationMO relation) throws ServiceException {
        RestfulParametes restParametes = new RestfulParametes();

        restParametes.put("src_id", relation.getSrcUUID());

        restParametes.put("dst_id", relation.getDstUUID());

        restParametes.put("dst_type", relation.getDstResType());

        restParametes.put("relationship_name", relation.getRelation());

        try {
            String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + relation.getSrcResType()
                    + "/relationships";

            RestfulResponse response = RestfulProxy.post(url, restParametes);
            if(HttpCode.isSucess(response.getStatus())) {
                return;
            } else {
                LOGGER.warn("deleteRelation fail!" + relation.toString());
                dealSvcException(response);
                throw new ServiceException(ErrorCode.DB_RETURN_ERROR);
            }
        } catch(ServiceException e) {
            LOGGER.warn("deleteRelation fail!" + relation.toString(), e);
            throw new ServiceException(e);
        }

    }

    private String buildRelationListString(List<String> srcUUID) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < srcUUID.size(); i++) {
            sb.append(srcUUID.get(i));
            if(i != (srcUUID.size() - 1)) {
                sb.append(',');
            }
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private InvRsp<List<Map<String, Object>>> getRelationObjectNbi(String resType, String dstType, String srcUUId,
            List<String> dstUUIdList) throws ServiceException {
        RestfulParametes restParametes = new RestfulParametes();

        restParametes.put("dst_type", dstType);
        restParametes.put(srcUUId, buildRelationListString(dstUUIdList));
        InvRsp<List<Map<String, Object>>> responseValue = null;

        String url = MessageFormat.format(DBURL, DbOwerInfo.getBucketName()) + resType + "/relationships";

        RestfulResponse response = RestfulProxy.post(url, restParametes);
        if(HttpCode.isSucess(response.getStatus())) {
            responseValue = ResponseUtils.transferResponse(response, InvRsp.class);
        } else {
            dealSvcException(response);

            LOGGER.warn("getRelation fail!relationType=" + resType);
            responseValue = buildRsp(response, resType);
        }

        return responseValue;
    }

    private void dealSvcException(RestfulResponse response) throws ServiceException {
        if((null != response.getResponseContent()) && (response.getResponseContent().contains("exceptionId"))) {
            LOGGER.error("puer process return status:" + response.getStatus() + ",exception:"
                    + response.getResponseContent());
            ResponseUtils.checkResonseAndThrowException(response);
        }
    }
}
