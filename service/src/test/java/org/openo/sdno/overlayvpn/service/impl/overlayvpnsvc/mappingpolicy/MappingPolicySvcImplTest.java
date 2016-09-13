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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mappingpolicy;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.BaseMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;

import mockit.Mock;
import mockit.MockUp;

public class MappingPolicySvcImplTest {

    @Test(expected = ServiceException.class)
    public void testQueryNormalException() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<BaseMappingPolicy> query(Class clazz, String uuid, String tenantId) {
                ResultRsp<BaseMappingPolicy> resp = new ResultRsp<BaseMappingPolicy>();
                resp.setErrorCode("overlayvpn.operation.failed");
                return resp;
            }
        };

        MappingPolicySvcImpl svcObj = new MappingPolicySvcImpl();

        Field[] fields = svcObj.getClass().getFields();

        Class aClass = MappingPolicySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(svcObj);
            value = new InventoryDao();

            field.set(svcObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";
        BaseMappingPolicy mappingPolicy = new BaseMappingPolicy();
        ResultRsp<BaseMappingPolicy> res = svcObj.query(req, resp, uuid, tenantId);
        fail("Exception occured");
    }

    @Test
    public void testQueryNormal() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<BaseMappingPolicy> query(Class clazz, String uuid, String tenantId) {
                ResultRsp<BaseMappingPolicy> resp = new ResultRsp<BaseMappingPolicy>();
                BaseMappingPolicy policy = new BaseMappingPolicy();
                resp.setData(policy);
                resp.setErrorCode("overlayvpn.operation.success");
                return resp;
            }
        };

        MappingPolicySvcImpl svcObj = new MappingPolicySvcImpl();

        Field[] fields = svcObj.getClass().getFields();

        Class aClass = MappingPolicySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(svcObj);
            value = new InventoryDao();

            field.set(svcObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";
        BaseMappingPolicy mappingPolicy = new BaseMappingPolicy();
        ResultRsp<BaseMappingPolicy> res = svcObj.query(req, resp, uuid, tenantId);
        assertTrue(res.isSuccess());
    }

    @Test
    public void testQueryNormalNullData() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<BaseMappingPolicy> query(Class clazz, String uuid, String tenantId) {
                ResultRsp<BaseMappingPolicy> resp = new ResultRsp<BaseMappingPolicy>();
                BaseMappingPolicy policy = new BaseMappingPolicy();
                resp.setData(null);
                resp.setErrorCode("overlayvpn.operation.success");
                return resp;
            }
        };

        MappingPolicySvcImpl svcObj = new MappingPolicySvcImpl();

        Field[] fields = svcObj.getClass().getFields();

        Class aClass = MappingPolicySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(svcObj);
            value = new InventoryDao();

            field.set(svcObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";
        BaseMappingPolicy mappingPolicy = new BaseMappingPolicy();
        ResultRsp<BaseMappingPolicy> res = svcObj.query(req, resp, uuid, tenantId);
        assertTrue(res.isSuccess());
    }

    @Test(expected = ServiceException.class)
    public void testQueryNormalFail() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<BaseMappingPolicy> query(Class clazz, String uuid, String tenantId) {
                ResultRsp<BaseMappingPolicy> resp = new ResultRsp<BaseMappingPolicy>();
                BaseMappingPolicy policy = new BaseMappingPolicy();
                resp.setData(null);
                resp.setErrorCode("overlayvpn.operation.failed");
                return resp;
            }

        };

        MappingPolicySvcImpl svcObj = new MappingPolicySvcImpl();

        Field[] fields = svcObj.getClass().getFields();

        Class aClass = MappingPolicySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(svcObj);
            value = new InventoryDao();

            field.set(svcObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";
        BaseMappingPolicy mappingPolicy = new BaseMappingPolicy();
        ResultRsp<BaseMappingPolicy> res = svcObj.query(req, resp, uuid, tenantId);
        fail("Exception occured");
    }

    @Test(expected = ServiceException.class)
    public void testBatchQueryFail() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<BaseMappingPolicy> batchQuery(Class clazz, String filter) {
                ResultRsp<BaseMappingPolicy> resp = new ResultRsp<BaseMappingPolicy>();
                BaseMappingPolicy policy = new BaseMappingPolicy();
                resp.setData(null);
                resp.setErrorCode("overlayvpn.operation.failed");
                return resp;
            }

        };

        MappingPolicySvcImpl svcObj = new MappingPolicySvcImpl();

        Field[] fields = svcObj.getClass().getFields();

        Class aClass = MappingPolicySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(svcObj);
            value = new InventoryDao();

            field.set(svcObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String tenantId = "";
        String filter = "";
        BaseMappingPolicy mappingPolicy = new BaseMappingPolicy();
        ResultRsp<List<BaseMappingPolicy>> res = svcObj.batchQuery(req, resp, tenantId, filter);
        assertTrue(res.isSuccess());
    }

    @Test
    public void testBatchQuerySuccess() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<BaseMappingPolicy> batchQuery(Class clazz, String filter) {
                ResultRsp<BaseMappingPolicy> resp = new ResultRsp<BaseMappingPolicy>();
                BaseMappingPolicy policy = new BaseMappingPolicy();
                resp.setData(null);
                resp.setErrorCode("overlayvpn.operation.success");
                return resp;
            }

        };

        MappingPolicySvcImpl svcObj = new MappingPolicySvcImpl();

        Field[] fields = svcObj.getClass().getFields();

        Class aClass = MappingPolicySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(svcObj);
            value = new InventoryDao();

            field.set(svcObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String tenantId = "";
        String filter = "";
        BaseMappingPolicy mappingPolicy = new BaseMappingPolicy();
        ResultRsp<List<BaseMappingPolicy>> res = svcObj.batchQuery(req, resp, tenantId, filter);
        assertTrue(res.isSuccess());
    }

}
