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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.nbi.PuerInvRelationNbiBean;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.nbi.PuerInvServiceNbiBean;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.ITransactionContext;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.TransactionMgr;
import org.openo.sdno.overlayvpn.inventory.sdk.model.BatchQueryFileterEntity;
import org.openo.sdno.overlayvpn.inventory.sdk.model.QueryParams;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationMO;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.rest.ResponseUtils;

import mockit.Mock;
import mockit.MockUp;

public class PuerInvDAOImplTest {

    @Test
    public void testAdd() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        List<Gateway> gwList = new ArrayList<Gateway>();
        Gateway gw = new Gateway();
        gw.setUuid("uuid");
        ResultRsp<List<String>> result = dao.add(gwList, Gateway.class);

        assertTrue(result.isSuccess());

    }

    @Test(expected = ServiceException.class)
    public void testDeleteException() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        List<Gateway> gwList = new ArrayList<Gateway>();
        Gateway gw = new Gateway();
        gw.setUuid("uuid");
        ResultRsp<String> result = dao.delete("uuid", Gateway.class);
        fail("Exception was not thrown");
    }

    @Test
    public void testDeleteNormal() throws ServiceException {

        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse delete(String url, RestfulParametes restParametes) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(200);
                return resp;
            }

        };
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        List<Gateway> gwList = new ArrayList<Gateway>();
        Gateway gw = new Gateway();
        gw.setUuid("uuid");
        ResultRsp<String> result = dao.delete("uuid", Gateway.class);
        assertTrue(result.isSuccess());

    }

    @Test(expected = ServiceException.class)
    public void testDeleteMOListException() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        List<Gateway> gwList = new ArrayList<Gateway>();
        Gateway gw = new Gateway();
        gw.setUuid("uuid");
        gwList.add(gw);
        ResultRsp<List<String>> result = dao.delete(gwList, Gateway.class);
        fail("Exception was not thrown");
    }

    /*
     * //TODO: check by below test case throwing exception
     * @Test
     * public void testDeleteMOListNormal() throws ServiceException {
     * new MockUp<RestfulProxy>() {
     * @Mock
     * public RestfulResponse delete(String url, RestfulParametes restParametes) {
     * RestfulResponse resp = new RestfulResponse();
     * List<Gateway> gwList = new ArrayList<Gateway>();
     * Gateway gw = new Gateway();
     * gw.setUuid("uuid");
     * resp.setResponseJson(JsonUtil.toJson(gwList));
     * resp.setStatus(200);
     * return resp;
     * }
     * };
     * PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
     * PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();
     * PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
     * dao.setPuerObjInvService(serviceNbiBean);
     * dao.setPuerRelationService(relationNbiBean);
     * List<Gateway> gwList = new ArrayList<Gateway>();
     * Gateway gw = new Gateway();
     * gw.setUuid("uuid");
     * gwList.add(gw);
     * ResultRsp<List<String>> result = dao.delete(gwList, Gateway.class);
     * assertTrue(result.isSuccess());
     * }
     */

    @Test(expected = ServiceException.class)
    public void testBatchDeleteException() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        List<String> gwList = new ArrayList<String>();
        gwList.add("uuid");
        ResultRsp result = dao.batchDelete(gwList, Gateway.class);
        fail("Exception was not thrown");
    }

    // TODO: test normal batchDelete case

    @Test(expected = ServiceException.class)
    public void testUpdateException() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        List<Gateway> gwList = new ArrayList<Gateway>();
        Gateway gw = new Gateway();
        gw.setUuid("uuid");
        gwList.add(gw);
        ResultRsp result = dao.update(gwList, Gateway.class);
        fail("Exception was not thrown");
    }

    // TODO: test normal update case

    @Test(expected = ServiceException.class)
    public void testQueryException() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        List<Gateway> gwList = new ArrayList<Gateway>();
        Gateway gw = new Gateway();
        gw.setUuid("uuid");
        gwList.add(gw);
        ResultRsp result = dao.query(gwList, Gateway.class);
        fail("Exception was not thrown");
    }

    // TODO: test normal query case

    @Test(expected = ServiceException.class)
    public void testQueryByParamException() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        List<Gateway> gwList = new ArrayList<Gateway>();
        Gateway gw = new Gateway();
        gw.setUuid("uuid");
        gwList.add(gw);
        QueryParams queryParams = new QueryParams(null, null, null, null);
        ResultRsp result = dao.query(Gateway.class, queryParams);
        fail("Exception was not thrown");
    }

    // TODO: test nortmal query case

    @Test(expected = Exception.class)
    public void testAddRelationException() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        RelationMO relation = new RelationMO();

        relation.setServiceType("OverlayVPNService");
        relation.setDstAttr("dstAttr");
        relation.setDstType(Gateway.class);
        relation.setDstUUID("destuuid");

        relation.setSrcAttr("srcAttr");
        relation.setSrcType(Gateway.class);
        relation.setSrcUUID("srcuuid");
        ResultRsp result = dao.addRelation(relation);
        fail("Exception was not thrown");
    }

    // TODO: test normal case of add relation

    @Test(expected = Exception.class)
    public void testDeleteRelationException() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        RelationMO relation = new RelationMO();

        relation.setServiceType("OverlayVPNService");
        relation.setDstAttr("dstAttr");
        relation.setDstType(Gateway.class);
        relation.setDstUUID("destuuid");

        relation.setSrcAttr("srcAttr");
        relation.setSrcType(Gateway.class);
        relation.setSrcUUID("srcuuid");
        ResultRsp result = dao.deleteRelation(relation);
        fail("Exception was not thrown");
    }

    // TODO: test normal case for delete relation

    @Test(expected = Exception.class)
    public void testQueryByUUIDException() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);
        ResultRsp result = dao.query("uuid", Gateway.class);
        fail("Exception was not thrown");
    }

    /*
     * //TODO: test normal case for query by uuid.
     * @Test
     * public void testQueryByUUIDNormal() throws ServiceException {
     * new MockUp<RestfulProxy>() {
     * @Mock
     * public RestfulResponse get(String url, RestfulParametes restParametes) {
     * RestfulResponse resp = new RestfulResponse();
     * List<Gateway> gwList = new ArrayList<Gateway>();
     * Gateway gw = new Gateway();
     * gw.setUuid("uuid");
     * resp.setResponseJson(JsonUtil.toJson(gwList));
     * resp.setStatus(200);
     * return resp;
     * }
     * };
     * new MockUp<ResponseUtils>() {
     * @Mock
     * public Map<String,Object> transferResponse(RestfulResponse url, Class clazz) {
     * Map<String,Object> map = new HashMap<String,Object>();
     * map.put("uuid", "12345");
     * return map;
     * }
     * };
     * PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
     * PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();
     * PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
     * dao.setPuerObjInvService(serviceNbiBean);
     * dao.setPuerRelationService(relationNbiBean);
     * ResultRsp result = dao.query("uuid", Gateway.class);
     * assertTrue(result.isSuccess());
     * }
     */

    @Test(expected = Exception.class)
    public void testQueryByMOTypeException() throws ServiceException {
        PuerInvServiceNbiBean serviceNbiBean = new PuerInvServiceNbiBean();
        PuerInvRelationNbiBean relationNbiBean = new PuerInvRelationNbiBean();

        PuerInvDAOImpl<Gateway> dao = new PuerInvDAOImpl<Gateway>();
        dao.setPuerObjInvService(serviceNbiBean);
        dao.setPuerRelationService(relationNbiBean);

        BatchQueryFileterEntity filterEntity = new BatchQueryFileterEntity();
        ResultRsp result = dao.query(Gateway.class, filterEntity);
        fail("Exception was not thrown");
    }
}
