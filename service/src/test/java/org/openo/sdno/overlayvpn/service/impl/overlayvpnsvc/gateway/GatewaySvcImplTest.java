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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.gateway;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.result.ResultRsp;

import mockit.Mock;
import mockit.MockUp;

public class GatewaySvcImplTest {

    @SuppressWarnings("rawtypes")
    @Test
    public void testQuery() {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<Gateway> query(Class clazz, String uuid, String tenantId) {
                ResultRsp<Gateway> resp = new ResultRsp<Gateway>();
                resp.setErrorCode("200");
                return resp;
            }
        };

        GatewaySvcImpl gatewayObj = new GatewaySvcImpl();

        Field[] fields = gatewayObj.getClass().getFields();

        Class aClass = GatewaySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);
            field.set(gatewayObj, new InventoryDao());
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {

        }

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";
        try {
            gatewayObj.query(req, resp, uuid, tenantId);
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Exception occured");
        }
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testQueryFail() {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<Gateway> query(Class clazz, String uuid, String tenantId) {
                ResultRsp<Gateway> resp = new ResultRsp<Gateway>();
                resp.setErrorCode("overlayvpn.operation.success");
                return resp;
            }
        };

        GatewaySvcImpl gatewayObj = new GatewaySvcImpl();

        Field[] fields = gatewayObj.getClass().getFields();

        Class aClass = GatewaySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(gatewayObj);
            value = new InventoryDao();

            field.set(gatewayObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
			 fail("File Exception occured");
        }

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";
        try {
            gatewayObj.query(req, resp, uuid, tenantId);
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Exception occured");
        }
    }

    @Test
    public void testQueryAllNormal() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<List<Gateway>> queryByFilter(Class clazz, String uuid, String tenantId) {
                ResultRsp<List<Gateway>> resp = new ResultRsp<List<Gateway>>();
                resp.setErrorCode("overlayvpn.operation.success");
                return resp;
            }
        };

        GatewaySvcImpl gatewayObj = new GatewaySvcImpl();

        Field[] fields = gatewayObj.getClass().getFields();

        Class aClass = GatewaySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(gatewayObj);
            value = new InventoryDao();

            field.set(gatewayObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {

        }

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";

        ResultRsp<List<Gateway>> res = gatewayObj.batchQuery(req, resp, uuid, tenantId);
        assertTrue(res.isSuccess());

    }

    @Test
    public void testQueryAllFailed() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<List<Gateway>> queryByFilter(Class clazz, String uuid, String tenantId) {
                ResultRsp<List<Gateway>> resp = new ResultRsp<List<Gateway>>();
                resp.setErrorCode("overlayvpn.operation.failed");
                return resp;
            }
        };

        GatewaySvcImpl gatewayObj = new GatewaySvcImpl();

        Field[] fields = gatewayObj.getClass().getFields();

        Class aClass = GatewaySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(gatewayObj);
            value = new InventoryDao();

            field.set(gatewayObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {
        }

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";

        ResultRsp<List<Gateway>> res = gatewayObj.batchQuery(req, resp, uuid, tenantId);
        assertFalse(res.isSuccess());
    }

    @Test
    public void testQueryAllValid() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<List<Gateway>> queryByFilter(Class clazz, String uuid, String tenantId) {
                ResultRsp<List<Gateway>> resp = new ResultRsp<List<Gateway>>();
                List<Gateway> gatewayLst = new ArrayList<Gateway>();
                Gateway gw = new Gateway();
                gatewayLst.add(gw);
                resp.setErrorCode("overlayvpn.operation.success");
                resp.setData(gatewayLst);
                return resp;
            }
        };

        GatewaySvcImpl gatewayObj = new GatewaySvcImpl();

        Field[] fields = gatewayObj.getClass().getFields();

        Class aClass = GatewaySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(gatewayObj);
            value = new InventoryDao();

            field.set(gatewayObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {

        }

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";

        ResultRsp<List<Gateway>> res = gatewayObj.batchQuery(req, resp, uuid, tenantId);
        assertTrue(res.isSuccess());

    }

    @Test(expected = ServiceException.class)
    public void testQueryAllFailInvalidId() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<Gateway> query(Class clazz, String uuid, String tenantId) {
                ResultRsp<Gateway> resp = new ResultRsp<Gateway>();
                resp.setErrorCode("overlayvpn.operation.success");
                return resp;
            }
        };

        GatewaySvcImpl gatewayObj = new GatewaySvcImpl();

        Field[] fields = gatewayObj.getClass().getFields();

        Class aClass = GatewaySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(gatewayObj);
            value = new InventoryDao();

            field.set(gatewayObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {

        }

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "1234";
        String tenantId = "abcd";

        gatewayObj.batchQuery(req, resp, uuid, tenantId);
        fail("Exception occured");
    }

    @Test
    public void testUpdateNormal() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<List<Gateway>> queryByFilter(Class clazz, String uuid, String tenantId) {
                ResultRsp<List<Gateway>> resp = new ResultRsp<List<Gateway>>();
                resp.setErrorCode("overlayvpn.operation.success");
                return resp;
            }
        };

        GatewaySvcImpl gatewayObj = new GatewaySvcImpl();

        Field[] fields = gatewayObj.getClass().getFields();

        Class aClass = GatewaySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(gatewayObj);
            value = new InventoryDao();

            field.set(gatewayObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {

        }

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";

        ResultRsp<Gateway> res = gatewayObj.update(req, resp, new Gateway(), new Gateway());
        assertTrue(res.isSuccess());

    }

    @Test(expected = Exception.class)
    public void testUpdateException() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<List<EndpointGroup>> queryByFilter(Class clazz, String uuid, String tenantId) {
                ResultRsp<List<EndpointGroup>> resp = new ResultRsp<List<EndpointGroup>>();
                List<EndpointGroup> list = new ArrayList<EndpointGroup>();
                EndpointGroup epg = new EndpointGroup();
                list.add(epg);
                resp.setErrorCode("overlayvpn.operation.success");
                resp.setData(list);
                return resp;
            }
        };

        GatewaySvcImpl gatewayObj = new GatewaySvcImpl();

        Field[] fields = gatewayObj.getClass().getFields();

        Class aClass = GatewaySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(gatewayObj);
            value = new InventoryDao();

            field.set(gatewayObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {

        }

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";
        Gateway newGw = new Gateway();
        newGw.setName("rfer");
        ResultRsp<Gateway> res = gatewayObj.update(req, resp, newGw, newGw);
        fail("Exception occured");

    }

    @Test
    public void testDeleteNormal() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<List<EndpointGroup>> queryByFilter(Class clazz, String uuid, String tenantId) {
                ResultRsp<List<EndpointGroup>> resp = new ResultRsp<List<EndpointGroup>>();
                resp.setErrorCode("overlayvpn.operation.success");
                return resp;
            }
        };

        GatewaySvcImpl gatewayObj = new GatewaySvcImpl();

        Field[] fields = gatewayObj.getClass().getFields();

        Class aClass = GatewaySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(gatewayObj);
            value = new InventoryDao();

            field.set(gatewayObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {

        }

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";

        ResultRsp<String> res = gatewayObj.delete(req, resp, new Gateway());
        assertTrue(res.isSuccess());

    }

    @Test(expected = Exception.class)
    public void testDeleteNormalValidData() throws ServiceException {
        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp<List<EndpointGroup>> queryByFilter(Class clazz, String uuid, String tenantId) {
                ResultRsp<List<EndpointGroup>> resp = new ResultRsp<List<EndpointGroup>>();
                List<EndpointGroup> list = new ArrayList<EndpointGroup>();
                EndpointGroup epg = new EndpointGroup();
                list.add(epg);
                resp.setErrorCode("overlayvpn.operation.success");
                resp.setData(list);
                return resp;
            }
        };

        GatewaySvcImpl gatewayObj = new GatewaySvcImpl();

        Field[] fields = gatewayObj.getClass().getFields();

        Class aClass = GatewaySvcImpl.class;
        Field field;
        try {
            field = aClass.getDeclaredField("inventoryDao");
            field.setAccessible(true);

            Object value = field.get(gatewayObj);
            value = new InventoryDao();

            field.set(gatewayObj, value);
        } catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e2) {

        }

        HttpServletRequest req = null;
        HttpServletResponse resp = null;
        String uuid = "";
        String tenantId = "";

        ResultRsp<String> res = gatewayObj.delete(req, resp, new Gateway());
        fail("Exception occured");
    }

}
