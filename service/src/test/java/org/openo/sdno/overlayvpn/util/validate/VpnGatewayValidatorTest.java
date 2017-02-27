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

package org.openo.sdno.overlayvpn.util.validate;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.invdao.LogicalTernminationPointInvDao;
import org.openo.sdno.overlayvpn.brs.model.LogicalTernminationPointMO;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpn;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.overlayvpn.util.toolkit.FileUtils;

import mockit.Mock;
import mockit.MockUp;

public class VpnGatewayValidatorTest {

    private static final String SITE_BASIC_PATH = "/openoapi/sdnolocalsite/v1/sites";

    @Before
    public void setUp() throws Exception {
        new MockRestfulProxy();
        new MockLogicalTernminationPointDao();
    }

    @Test
    public void testValidateCreate() throws ServiceException {
        File file = new File("src/test/resources/gateway2.json");
        NbiVpnGateway vpnGateway = JsonUtils.fromJson(FileUtils.readFile(file), NbiVpnGateway.class);
        List<String> ports = new ArrayList<>();
        List<String> portNames = new ArrayList<>();
        ports.add("test_uuid");
        portNames.add("Port01");
        vpnGateway.setPorts(ports);
        vpnGateway.setPortNames(portNames);
        VpnGatewayValidator vpnGatewayValidator = new VpnGatewayValidator();
        vpnGatewayValidator.validateCreate(vpnGateway);
    }

    @Test
    public void testValidateUpdate() throws ServiceException {
        File file = new File("src/test/resources/gateway1.json");
        NbiVpnGateway vpnGateway = JsonUtils.fromJson(FileUtils.readFile(file), NbiVpnGateway.class);
        VpnGatewayValidator vpnGatewayValidator = new VpnGatewayValidator();
        vpnGatewayValidator.validateUpdate("gateway_id", vpnGateway);
    }

    @Test
    public void testValidateDelete() throws ServiceException {
        VpnGatewayValidator vpnGatewayValidator = new VpnGatewayValidator();
        assertTrue(vpnGatewayValidator.validateDelete("gateway_id"));
    }

    private final class MockRestfulProxy extends MockUp<RestfulProxy> {

        @Mock
        RestfulResponse get(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            response.setStatus(HttpStatus.SC_OK);
            if(uri.startsWith(SITE_BASIC_PATH)) {
                File file = new File("src/test/resources/querysite.json");
                VpnSite vpnSite = JsonUtil.fromJson(FileUtils.readFile(file), VpnSite.class);

                response.setResponseJson(JsonUtil.toJson(vpnSite));
            } else if(uri.contains("gateway_id")) {
                File file = new File("src/test/resources/gateway1.json");

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", FileUtils.readFile(file));
                response.setResponseJson(JsonUtil.toJson(responseMap));
            } else if(uri.contains("vpnId")) {
                File file = new File("src/test/resources/vpn.json");
                NbiVpn vpn = JsonUtil.fromJson(FileUtils.readFile(file), NbiVpn.class);
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", vpn);
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

    private class MockLogicalTernminationPointDao extends MockUp<LogicalTernminationPointInvDao> {

        @Mock
        public List<LogicalTernminationPointMO> query(Map<String, String> condition) throws ServiceException {
            List<LogicalTernminationPointMO> portList = new ArrayList<>();
            LogicalTernminationPointMO port = new LogicalTernminationPointMO();
            port.setId("test_uuid");
            port.setName("Port01");
            portList.add(port);
            return portList;
        }

    }
}
