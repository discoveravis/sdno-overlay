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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import org.openo.sdno.overlayvpn.result.ResultRsp;

import mockit.Mock;
import mockit.MockUp;

public class AllocIdResourceInvDaoTest {

    @Test
    public void testBatchInsert() throws ServiceException {

        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse post(String url, RestfulParametes restParametes) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(200);
                return resp;
            }

        };
        AllocIdResourceInvDao dao = new AllocIdResourceInvDao();

        List<AllocIdResource> resourceList = new ArrayList<AllocIdResource>();
        AllocIdResource resource = new AllocIdResource();
        resource.setIdres(123456l);
        resource.setPoolname("poolA");
        resource.setUserlabel("labelA");
        resource.setUuid("12345");
        resourceList.add(resource);
        dao.batchInsert(resourceList);

        assertTrue(true);
    }

    @Test
    public void testBatchDelete() throws ServiceException {

        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse delete(String url, RestfulParametes restParametes) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(200);
                return resp;
            }

        };
        AllocIdResourceInvDao dao = new AllocIdResourceInvDao();

        List<AllocIdResource> resourceList = new ArrayList<AllocIdResource>();
        AllocIdResource resource = new AllocIdResource();
        resource.setIdres(123456l);
        resource.setPoolname("poolA");
        resource.setUserlabel("labelA");
        resource.setUuid("12345");
        resourceList.add(resource);
        dao.batchDelete(resourceList);

        assertTrue(true);
    }

    @Test
    public void testBatchQuery() throws ServiceException {

        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse get(String url, RestfulParametes restParametes) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(200);
                List<AllocIdResource> resourceList = new ArrayList<AllocIdResource>();
                AllocIdResource resource = new AllocIdResource();
                resource.setIdres(123456l);
                resource.setPoolname("poolA");
                resource.setUserlabel("labelA");
                resource.setUuid("12345");
                resourceList.add(resource);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("objects", resourceList);
                resp.setResponseJson(JsonUtil.toJson(map));
                return resp;
            }

        };
        AllocIdResourceInvDao dao = new AllocIdResourceInvDao();
        List<Long> idList = new ArrayList<Long>();
        idList.add(123456l);
        ResultRsp<List<AllocIdResource>> resList = dao.batchQuery("poolA", idList);
        AllocIdResource res = resList.getData().get(0);

        Long id = res.getIdres();
        boolean isEq = (id == 123456l);
        assertTrue(isEq);

        assertEquals("poolA", res.getPoolname());
        assertEquals("labelA", res.getUserlabel());
        assertEquals("12345", res.getUuid());

        assertTrue(resList.isSuccess());
    }

}
