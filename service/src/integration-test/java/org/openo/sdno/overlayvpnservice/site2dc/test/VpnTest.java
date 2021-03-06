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
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.replace.PathReplace;
import org.openo.sdno.testframework.testmanager.TestManager;

public class VpnTest extends TestManager {

    private static final String CREATE_VPN_FILE = "src/integration-test/resources/site2dc/testcase/vpn/createvpn.json";

    private static final String CREATE_VPNGATEWAY_FILE =
            "src/integration-test/resources/site2dc/testcase/vpngateway/createvpngateway.json";

    private static final String CREATE_VPN_INVALID_FILE =
            "src/integration-test/resources/site2dc/testcase/vpn/createvpninvalid.json";

    private static final String DELETE_VPN_FILE = "src/integration-test/resources/site2dc/testcase/vpn/deletevpn.json";

    private static final String DELETE_VPNGATEWAY_FILE =
            "src/integration-test/resources/site2dc/testcase/vpngateway/deletevpngateway.json";

    private static final String UPDATE_VPN_FILE = "src/integration-test/resources/site2dc/testcase/vpn/updatevpn.json";

    private static final String QUERY_VPN_FILE = "src/integration-test/resources/site2dc/testcase/vpn/queryvpn.json";

    private static final String BATCH_QUERY_VPN_FILE =
            "src/integration-test/resources/site2dc/testcase/vpn/batchqueryvpn.json";

    private static final String DEPLOY_VPN_FILE = "src/integration-test/resources/site2dc/testcase/vpn/deployvpn.json";

    private static final String UNDEPLOY_VPN_FILE =
            "src/integration-test/resources/site2dc/testcase/vpn/undeployvpn.json";

    @BeforeClass
    public static void setup() throws ServiceException {
        MsbRegisterManager.registerMsb();
    }

    @AfterClass
    public static void tearDown() throws ServiceException {
        MsbRegisterManager.unRegisterMsb();
    }

    @Test
    public void testOperateVpn() throws ServiceException {

        HttpRquestResponse httpCreateObject = HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPN_FILE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        execTestCase(createRequest, new SuccessChecker());

        execTestCase(createRequest, new FailedChecker());

        createRequest.setData(null);
        execTestCase(createRequest, new FailedChecker());

        HttpRquestResponse updateHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(UPDATE_VPN_FILE);
        HttpRequest updateReq = updateHttpObject.getRequest();
        updateReq.setUri(PathReplace.replaceUuid("uuid", updateReq.getUri(), "test_uuid"));
        execTestCase(updateReq, new SuccessChecker());

        HttpRquestResponse queryUnexistHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_VPN_FILE);
        HttpRequest queryUnexistReq = queryUnexistHttpObject.getRequest();
        queryUnexistReq.setUri(PathReplace.replaceUuid("uuid", queryUnexistReq.getUri(), "unexist"));
        execTestCase(queryUnexistReq, new SuccessChecker());

        HttpRquestResponse queryHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_VPN_FILE);
        HttpRequest queryReq = queryHttpObject.getRequest();
        queryReq.setUri(PathReplace.replaceUuid("uuid", queryReq.getUri(), "test_uuid"));
        execTestCase(queryReq, new SuccessChecker());

        HttpRquestResponse httpCreateGatewayObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPNGATEWAY_FILE);
        HttpRequest createGatewayRequest = httpCreateGatewayObject.getRequest();
        execTestCase(createGatewayRequest, new SuccessChecker());
        execTestCase(queryReq, new SuccessChecker());

        HttpRquestResponse batchQueryHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(BATCH_QUERY_VPN_FILE);
        HttpRequest batchQueryReq = batchQueryHttpObject.getRequest();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("vpnConnectionId", "test_uuid");
        batchQueryReq.setQueries(paramMap);
        execTestCase(batchQueryReq, new SuccessChecker());

        HttpRquestResponse httpDeployObject = HttpModelUtils.praseHttpRquestResponseFromFile(DEPLOY_VPN_FILE);
        HttpRequest deployRequest = httpDeployObject.getRequest();
        deployRequest.setUri(PathReplace.replaceUuid("uuid", deployRequest.getUri(), "test_uuid"));
        execTestCase(deployRequest, new SuccessChecker());

        HttpRquestResponse httpUneployObject = HttpModelUtils.praseHttpRquestResponseFromFile(UNDEPLOY_VPN_FILE);
        HttpRequest undeployRequest = httpUneployObject.getRequest();
        undeployRequest.setUri(PathReplace.replaceUuid("uuid", undeployRequest.getUri(), "test_uuid"));
        execTestCase(undeployRequest, new SuccessChecker());

        HttpRquestResponse deleteHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_VPN_FILE);
        HttpRequest deleteReq = deleteHttpObject.getRequest();
        deleteReq.setUri(PathReplace.replaceUuid("uuid", deleteReq.getUri(), "test_uuid"));
        execTestCase(deleteReq, new FailedChecker());

        HttpRquestResponse deleteGatewayHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_VPNGATEWAY_FILE);
        HttpRequest deleteatewayReq = deleteGatewayHttpObject.getRequest();
        deleteatewayReq.setUri(PathReplace.replaceUuid("uuid", deleteatewayReq.getUri(), "test_uuid"));
        execTestCase(deleteatewayReq, new SuccessChecker());

        execTestCase(deleteReq, new SuccessChecker());

        execTestCase(deployRequest, new FailedChecker());

        execTestCase(undeployRequest, new FailedChecker());

    }

    @Test
    public void testCreateVpnInvalidFailed() throws ServiceException {
        HttpRquestResponse httpCreateObject = HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPN_INVALID_FILE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        execTestCase(createRequest, new FailedChecker());
    }

    @Test
    public void testDeleteVpnSuccess() throws ServiceException {
        HttpRquestResponse deleteHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_VPN_FILE);
        HttpRequest deleteReq = deleteHttpObject.getRequest();
        deleteReq.setUri(PathReplace.replaceUuid("uuid", deleteReq.getUri(), "unexist"));
        execTestCase(deleteReq, new SuccessChecker());
    }

    @Test
    public void testUpdateVpnFailed() throws ServiceException {

        HttpRquestResponse updateUnexistHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(UPDATE_VPN_FILE);
        HttpRequest updateUnexistReq = updateUnexistHttpObject.getRequest();
        updateUnexistReq.setUri(PathReplace.replaceUuid("uuid", updateUnexistReq.getUri(), "unexist"));
        execTestCase(updateUnexistReq, new FailedChecker());

        HttpRquestResponse updateNullHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(UPDATE_VPN_FILE);
        HttpRequest updateNullReq = updateNullHttpObject.getRequest();
        updateNullReq.setUri(PathReplace.replaceUuid("uuid", updateNullReq.getUri(), "test_uuid"));
        updateNullReq.setData(null);
        execTestCase(updateNullReq, new FailedChecker());

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

    private class FailedChecker implements IChecker {

        @Override
        public boolean check(HttpResponse response) {
            if(!HttpCode.isSucess(response.getStatus())) {
                return true;
            }

            return false;
        }
    }
}
