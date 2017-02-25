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
import org.openo.sdno.overlayvpnservice.site2dc.mocoserver.MockIpsecSbiSuccessServer;
import org.openo.sdno.overlayvpnservice.site2dc.mocoserver.MockRouteSbiSuccessServer;
import org.openo.sdno.overlayvpnservice.site2dc.mocoserver.MockSiteSbiSuccessServer;
import org.openo.sdno.overlayvpnservice.site2dc.mocoserver.MockVxlanSbiSuccessServer;
import org.openo.sdno.overlayvpnservice.site2dc.msbmanager.MsbRegisterManager;
import org.openo.sdno.overlayvpnservice.site2dc.util.PopUtil;
import org.openo.sdno.testframework.checker.IChecker;
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.replace.PathReplace;
import org.openo.sdno.testframework.testmanager.TestManager;
import org.openo.sdno.testframework.topology.Topology;

public class VpnConnectionTest extends TestManager {

    private static final String CREATE_VPN_FILE = "src/integration-test/resources/site2dc/testcase/vpn/createvpn.json";

    private static final String DELETE_VPN_FILE = "src/integration-test/resources/site2dc/testcase/vpn/deletevpn.json";

    private static final String QUERY_VPN_FILE = "src/integration-test/resources/site2dc/testcase/vpn/queryvpn.json";

    private static final String CREATE_VPNGATEWAY_FILE =
            "src/integration-test/resources/site2dc/testcase/vpngateway/createvpngateway.json";

    private static final String CREATE_VPNGATEWAY_FILE2 =
            "src/integration-test/resources/site2dc/testcase/vpngateway/createvpngateway2.json";

    private static final String DELETE_VPNGATEWAY_FILE =
            "src/integration-test/resources/site2dc/testcase/vpngateway/deletevpngateway.json";

    private static final String CREATE_VPN_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/vpnconnection/createvpnconnection.json";

    private static final String DELETE_VPN_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/vpnconnection/deletevpnconnection.json";

    private static final String UPDATE_VPN_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/vpnconnection/updatevpnconnection.json";

    private static final String QUERY_VPN_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/vpnconnection/queryvpnconnection.json";

    private static final String BATCH_QUERY_VPN_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/vpnconnection/batchqueryvpnconnection.json";

    private static final String DEPLOY_VPN_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/vpnconnection/deployvpnconnection.json";

    private static final String UNDEPLOY_VPN_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/vpnconnection/undeployvpnconnection.json";

    private static final String TOPODATA_PATH = "src/integration-test/resources/site2dc/topo";

    private static MockIpsecSbiSuccessServer ipsecServer = new MockIpsecSbiSuccessServer();

    private static MockRouteSbiSuccessServer routeServer = new MockRouteSbiSuccessServer();

    private static MockSiteSbiSuccessServer siteServer = new MockSiteSbiSuccessServer();

    private static MockVxlanSbiSuccessServer vxlanServer = new MockVxlanSbiSuccessServer();

    private static Topology topo = new Topology(TOPODATA_PATH);

    @BeforeClass
    public static void setup() throws ServiceException {
        MsbRegisterManager.registerMsb();
        topo.createInvTopology();
        PopUtil.addPop();
        ipsecServer.start();
        routeServer.start();
        siteServer.start();
        vxlanServer.start();
    }

    @AfterClass
    public static void tearDown() throws ServiceException {
        ipsecServer.stop();
        routeServer.stop();
        siteServer.stop();
        vxlanServer.stop();
        topo.clearInvTopology();
        PopUtil.deletePop();
        MsbRegisterManager.unRegisterMsb();
    }

    @Test
    public void testCreateVpnConnectionFailed() throws ServiceException {
        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPN_CONNECTION_FILE);
        HttpRequest createRequest = httpCreateObject.getRequest();

        // Vpn is null
        execTestCase(createRequest, new FailedChecker());

        // vpn gateway is null
        HttpRquestResponse httpCreateVpnObject = HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPN_FILE);
        HttpRequest createVpnRequest = httpCreateVpnObject.getRequest();
        execTestCase(createVpnRequest, new SuccessChecker());
        execTestCase(createRequest, new FailedChecker());

        // model is null
        createRequest.setData(null);
        execTestCase(createRequest, new FailedChecker());

        HttpRquestResponse deleteVpnHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_VPN_FILE);
        HttpRequest deleteVpnReq = deleteVpnHttpObject.getRequest();
        deleteVpnReq.setUri(PathReplace.replaceUuid("uuid", deleteVpnReq.getUri(), "test_uuid"));
        execTestCase(deleteVpnReq, new SuccessChecker());
    }

    @Test
    public void testOperateVpnConnectionSuccess() throws ServiceException {
        // create vpn
        HttpRquestResponse httpCreateVpnObject = HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPN_FILE);
        HttpRequest createVpnRequest = httpCreateVpnObject.getRequest();
        execTestCase(createVpnRequest, new SuccessChecker());

        // create vpnGateway
        HttpRquestResponse httpCreateGatewayObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPNGATEWAY_FILE);
        HttpRequest createGatewayRequest = httpCreateGatewayObject.getRequest();
        execTestCase(createGatewayRequest, new SuccessChecker());

        HttpRquestResponse httpCreateGateway2Object =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPNGATEWAY_FILE2);
        HttpRequest createGateway2Request = httpCreateGateway2Object.getRequest();
        execTestCase(createGateway2Request, new SuccessChecker());

        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_VPN_CONNECTION_FILE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        execTestCase(createRequest, new SuccessChecker());

        HttpRquestResponse updateHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(UPDATE_VPN_CONNECTION_FILE);
        HttpRequest updateReq = updateHttpObject.getRequest();
        updateReq.setUri(PathReplace.replaceUuid("uuid", updateReq.getUri(), "test_uuid"));
        execTestCase(updateReq, new SuccessChecker());

        HttpRquestResponse queryHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_VPN_CONNECTION_FILE);
        HttpRequest queryReq = queryHttpObject.getRequest();
        queryReq.setUri(PathReplace.replaceUuid("uuid", queryReq.getUri(), "test_uuid"));
        execTestCase(queryReq, new SuccessChecker());

        HttpRquestResponse batchQueryHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(BATCH_QUERY_VPN_CONNECTION_FILE);
        HttpRequest batchQueryReq = batchQueryHttpObject.getRequest();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", "test_name");
        batchQueryReq.setQueries(paramMap);
        execTestCase(batchQueryReq, new SuccessChecker());

        paramMap.clear();
        paramMap.put("aEndVpnGatewayId", "test_uuid");
        batchQueryReq.setQueries(paramMap);
        execTestCase(batchQueryReq, new SuccessChecker());

        paramMap.clear();
        paramMap.put("zEndVpnGatewayId", "test_uuidz");
        batchQueryReq.setQueries(paramMap);
        execTestCase(batchQueryReq, new SuccessChecker());

        HttpRquestResponse queryVpnHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_VPN_FILE);
        HttpRequest queryVpnReq = queryVpnHttpObject.getRequest();
        queryVpnReq.setUri(PathReplace.replaceUuid("uuid", queryVpnReq.getUri(), "test_uuid"));
        execTestCase(queryVpnReq, new SuccessChecker());

        HttpRquestResponse httpDeployObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DEPLOY_VPN_CONNECTION_FILE);
        HttpRequest deployRequest = httpDeployObject.getRequest();
        deployRequest.setUri(PathReplace.replaceUuid("uuid", deployRequest.getUri(), "test_uuid"));
        execTestCase(deployRequest, new SuccessChecker());

        HttpRquestResponse httpUneployObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(UNDEPLOY_VPN_CONNECTION_FILE);
        HttpRequest undeployRequest = httpUneployObject.getRequest();
        undeployRequest.setUri(PathReplace.replaceUuid("uuid", undeployRequest.getUri(), "test_uuid"));
        execTestCase(undeployRequest, new SuccessChecker());

        execTestCase(undeployRequest, new SuccessChecker());

        execTestCase(deployRequest, new SuccessChecker());

        // gateway is used by connection
        HttpRquestResponse deleteGatewayHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_VPNGATEWAY_FILE);
        HttpRequest deletegatewayReq = deleteGatewayHttpObject.getRequest();
        deletegatewayReq.setUri(PathReplace.replaceUuid("uuid", deletegatewayReq.getUri(), "test_uuid"));
        execTestCase(deletegatewayReq, new FailedChecker());

        // Vpn is used by connection
        HttpRquestResponse deleteVpnHttpObject = HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_VPN_FILE);
        HttpRequest deleteVpnReq = deleteVpnHttpObject.getRequest();
        deleteVpnReq.setUri(PathReplace.replaceUuid("uuid", deleteVpnReq.getUri(), "test_uuid"));
        execTestCase(deleteVpnReq, new FailedChecker());

        HttpRquestResponse deleteHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_VPN_CONNECTION_FILE);
        HttpRequest deleteReq = deleteHttpObject.getRequest();
        deleteReq.setUri(PathReplace.replaceUuid("uuid", deleteReq.getUri(), "test_uuid"));
        execTestCase(deleteReq, new SuccessChecker());

        execTestCase(deleteReq, new SuccessChecker());

        // Vpn is used by gateway
        execTestCase(deleteVpnReq, new FailedChecker());

        execTestCase(deletegatewayReq, new SuccessChecker());

        HttpRquestResponse deleteGatewayzHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_VPNGATEWAY_FILE);
        HttpRequest deletegatewayzReq = deleteGatewayzHttpObject.getRequest();
        deletegatewayzReq.setUri(PathReplace.replaceUuid("uuid", deletegatewayzReq.getUri(), "test_uuidz"));
        execTestCase(deletegatewayzReq, new SuccessChecker());

        execTestCase(deleteVpnReq, new SuccessChecker());
    }

    @Test
    public void testOperateVpnConnectionFailed() throws ServiceException {

        HttpRquestResponse updateHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(UPDATE_VPN_CONNECTION_FILE);
        HttpRequest updateReq = updateHttpObject.getRequest();
        updateReq.setUri(PathReplace.replaceUuid("uuid", updateReq.getUri(), "test_uuid"));
        execTestCase(updateReq, new FailedChecker());

        updateReq.setData(null);
        execTestCase(updateReq, new FailedChecker());

        HttpRquestResponse httpDeployObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DEPLOY_VPN_CONNECTION_FILE);
        HttpRequest deployRequest = httpDeployObject.getRequest();
        deployRequest.setUri(PathReplace.replaceUuid("uuid", deployRequest.getUri(), "test_uuid"));
        execTestCase(deployRequest, new FailedChecker());

        HttpRquestResponse httpUneployObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(UNDEPLOY_VPN_CONNECTION_FILE);
        HttpRequest undeployRequest = httpUneployObject.getRequest();
        undeployRequest.setUri(PathReplace.replaceUuid("uuid", undeployRequest.getUri(), "test_uuid"));
        execTestCase(undeployRequest, new FailedChecker());
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
