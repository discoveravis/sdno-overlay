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

package org.openo.sdno.overlayvpn.res;

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
import org.openo.sdno.overlayvpn.result.ResultRsp;

import mockit.Mock;
import mockit.MockUp;

public class ResourcesUtilTest {

    @Test(expected = ServiceException.class)
    public void testRequestGloabelValueException() throws ServiceException {
        ResourcesUtil.requestGloabelValue("poolA", "labelA", 0, 0l, 10l);
    }

    @Test(expected = ServiceException.class)
    public void testRequestGloabelValueNormal() throws ServiceException {
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
        List<Long> valueList = ResourcesUtil.requestGloabelValue("poolA", "labelA", 1, 0l, 10l);
        // assertTrue(true);
    }

    @Test
    public void testFreeGlobalValueList() throws ServiceException {
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

            @Mock
            public RestfulResponse delete(String url, RestfulParametes restParametes) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(200);
                return resp;
            }

        };
        List<Long> list = new ArrayList<Long>();
        list.add(12345l);
        ResultRsp<String> resp = ResourcesUtil.freeGlobalValueList("poolA", "labelA", list);
        assertTrue(resp.isSuccess());
    }

}
