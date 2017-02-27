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

package org.openo.sdno.overlayvpn.site2dc.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpn;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.overlayvpn.util.toolkit.FileUtils;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

public class VpnServiceImplTest {

    @Mocked
    HttpServletRequest request;

    @Mocked
    HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        new MockRestfulProxy();
    }

    @Test
    public void testCreate() throws ServiceException {
        File file = new File("src/test/resources/vpn.json");
        NbiVpn vpn = JsonUtils.fromJson(FileUtils.readFile(file), NbiVpn.class);
        VpnServiceImpl service = new VpnServiceImpl();
        assertEquals(vpn, service.create(request, response, vpn));
    }

    @Test
    public void testDelete() throws ServiceException {
        VpnServiceImpl service = new VpnServiceImpl();
        assertEquals("uuid", service.delete(request, response, "uuid").getId());
    }

    @Test
    public void testUpdate() throws ServiceException {
        File file = new File("src/test/resources/vpn.json");
        NbiVpn vpn = JsonUtils.fromJson(FileUtils.readFile(file), NbiVpn.class);
        VpnServiceImpl service = new VpnServiceImpl();
        service.update(request, response, "vpnId", vpn);
    }

    @Test
    public void testQuery() throws ServiceException {
        VpnServiceImpl service = new VpnServiceImpl();
        assertEquals("vpnId", service.query(request, response, "vpnId").getId());
    }

    @Test
    public void testDeploy() throws ServiceException {
        VpnServiceImpl service = new VpnServiceImpl();
        assertEquals("vpnId", service.deploy(request, response, "vpnId").getId());
    }

    @Test
    public void testUndeploy() throws ServiceException {
        VpnServiceImpl service = new VpnServiceImpl();
        assertEquals("vpnId", service.undeploy(request, response, "vpnId").getId());
    }

    private final class MockRestfulProxy extends MockUp<RestfulProxy> {

        @Mock
        RestfulResponse get(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            response.setStatus(HttpStatus.SC_OK);
            if(uri.contains("vpnId")) {
                File file = new File("src/test/resources/vpn.json");

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", FileUtils.readFile(file));
                response.setResponseJson(JsonUtil.toJson(responseMap));
            } else if(uri.contains("vpngateway")) {
                File file = new File("src/test/resources/gateway1.json");

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", FileUtils.readFile(file));
                response.setResponseJson(JsonUtil.toJson(responseMap));
            } else if(uri.contains("vpnconnection")) {
                File file = new File("src/test/resources/vpnconnection.json");
                NbiVpnConnection vpnConnection = JsonUtil.fromJson(FileUtils.readFile(file), NbiVpnConnection.class);
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", vpnConnection);
                response.setResponseJson(JsonUtil.toJson(responseMap));
            }

            return response;
        }

        @Mock
        RestfulResponse post(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            response.setStatus(HttpStatus.SC_OK);

            return response;
        }

        @Mock
        RestfulResponse delete(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            ResultRsp<String> sbiRsp = new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS);
            response.setStatus(HttpStatus.SC_OK);
            response.setResponseJson(JsonUtil.toJson(sbiRsp));

            return response;
        }

        @Mock
        RestfulResponse put(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            ResultRsp<String> sbiRsp = new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS);
            response.setStatus(HttpStatus.SC_OK);
            response.setResponseJson(JsonUtil.toJson(sbiRsp));

            return response;
        }

    }
}
