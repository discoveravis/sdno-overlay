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

package org.openo.sdno.overlayvpnservice.site2dc.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.overlayvpnservice.site2dc.msbmanager.MsbRegisterManager;
import org.openo.sdno.testframework.checker.IChecker;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.testmanager.TestManager;

public class VpnLinksTest extends TestManager {

    private static final String QUERY_LINKS_URL = "/openoapi/sdnooverlay/v1/vpn-links/%s";

    @BeforeClass
    public static void setup() throws ServiceException {
        MsbRegisterManager.registerMsb();
    }

    @AfterClass
    public static void tearDown() throws ServiceException {
        MsbRegisterManager.unRegisterMsb();
    }

    @Test
    public void testQueryIpsecSuccess() throws ServiceException {
        HttpRequest httpRequest = buildHttpRequest("ipsec");
        execTestCase(httpRequest, new SuccessChecker());
    }

    @Test
    public void testQueryVxlanSuccess() throws ServiceException {
        HttpRequest httpRequest = buildHttpRequest("vxlan");
        execTestCase(httpRequest, new SuccessChecker());
    }

    @Test
    public void testQueryNullSuccess() throws ServiceException {
        HttpRequest httpRequest = buildHttpRequest("gre");
        execTestCase(httpRequest, new SuccessChecker());
    }

    private HttpRequest buildHttpRequest(String type) {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
        httpRequest.addRequestHeader("Accept", "application/json");
        httpRequest.setUri(String.format(QUERY_LINKS_URL, type));
        httpRequest.setMethod("get");
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", "test_name");
        httpRequest.setQueries(paramMap);
        return httpRequest;
    }

    private class SuccessChecker implements IChecker {

        @Override
        public boolean check(HttpResponse response) {
            if(HttpCode.isSucess(response.getStatus())) {
                return true;
            }

            return false;
        }
    }
}
