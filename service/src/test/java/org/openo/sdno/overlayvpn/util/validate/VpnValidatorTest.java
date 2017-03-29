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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.composer.TemplateManager;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateObject;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.overlayvpn.util.toolkit.FileUtils;

import mockit.Mock;
import mockit.MockUp;

public class VpnValidatorTest {

    @Before
    public void setUp() throws Exception {
        new MockRestfulProxy();
        new MockTemplateManager();
    }

    @Test
    public void testValidateCreate() throws ServiceException {
        File file = new File("src/test/resources/vpn.json");
        NbiVpn vpn = JsonUtils.fromJson(FileUtils.readFile(file), NbiVpn.class);
        vpn.setId("create_uuid");
        VpnValidator vpnValidator = new VpnValidator();
        vpnValidator.validateCreate(vpn);
    }

    @Test
    public void testValidateUpdate() throws ServiceException {
        File file = new File("src/test/resources/vpn.json");
        NbiVpn vpn = JsonUtils.fromJson(FileUtils.readFile(file), NbiVpn.class);
        VpnValidator vpnValidator = new VpnValidator();
        vpnValidator.validateUpdate("vpnId", vpn);
    }

    @Test
    public void testValidateDelete() throws ServiceException {
        VpnValidator vpnValidator = new VpnValidator();
        assertTrue(vpnValidator.validateDelete("vpnId"));
    }

    @Test
    public void testValidateDeploy() throws ServiceException {
        VpnValidator vpnValidator = new VpnValidator();
        assertFalse(vpnValidator.validateDeploy("vpnId"));
    }

    @Test
    public void testValidateUndeploy() throws ServiceException {
        VpnValidator vpnValidator = new VpnValidator();
        assertFalse(vpnValidator.validateUndeploy("vpnId"));
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

    private class MockTemplateManager extends MockUp<TemplateManager> {

        @Mock
        public Template getTemplate(String templateName) throws ServiceException {
            File file = new File("src/test/resources/IpSec_EthernetNetwork.json");
            TemplateObject templateObject = JsonUtil.fromJson(FileUtils.readFile(file), TemplateObject.class);

            Template template = new Template(templateObject);
            return template;
        }

        @Mock
        public Map<String, Template> getTemplateMap() {
            Map<String, Template> templateMap = new HashMap<>();
            File file = new File("src/test/resources/IpSec_EthernetNetwork.json");
            TemplateObject templateObject = JsonUtil.fromJson(FileUtils.readFile(file), TemplateObject.class);

            Template template = new Template(templateObject);
            String templateName = templateObject.getName();
            templateMap.put(templateName, template);
            return templateMap;
        }
    }
}
