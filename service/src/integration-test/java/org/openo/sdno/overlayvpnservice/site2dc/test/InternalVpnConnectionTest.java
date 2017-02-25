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

import org.codehaus.jackson.type.TypeReference;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.servicemodel.db.InternalVpnConnection;
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

public class InternalVpnConnectionTest extends TestManager {

    private static final String CREATE_INTERNAL_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/internalconnection/createinternalconnection.json";

    private static final String DELETE_INTERNAL_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/internalconnection/deleteinternalconnection.json";

    private static final String UPDATE_INTERNAL_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/internalconnection/updateinternalconnection.json";

    private static final String QUERY_INTERNAL_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/internalconnection/queryinternalconnection.json";

    private static final String BATCH_QUERY_INTERNAL_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/internalconnection/batchqueryinternalconnection.json";

    private static final String DEPLOY_INTERNAL_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/internalconnection/deployinternalconnection.json";

    private static final String UNDEPLOY_INTERNAL_CONNECTION_FILE =
            "src/integration-test/resources/site2dc/testcase/internalconnection/undeployinternalconnection.json";

    private static final String TOPODATA_PATH = "src/integration-test/resources/site2dc/topo";

    private static Topology topo = new Topology(TOPODATA_PATH);

    private static MockIpsecSbiSuccessServer ipsecServer = new MockIpsecSbiSuccessServer();

    private static MockRouteSbiSuccessServer routeServer = new MockRouteSbiSuccessServer();

    private static MockSiteSbiSuccessServer siteServer = new MockSiteSbiSuccessServer();

    private static MockVxlanSbiSuccessServer vxlanServer = new MockVxlanSbiSuccessServer();

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
    public void testCreateInternalConnectionFailed() throws ServiceException {

        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_INTERNAL_CONNECTION_FILE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        InternalVpnConnection newNbiData =
                JsonUtil.fromJson(createRequest.getData(), new TypeReference<InternalVpnConnection>() {});

        // the neId is empty
        execTestCase(createRequest, new FailedChecker());

        // ne not exist
        newNbiData.setSrcNeId("unexist");
        createRequest.setData(JsonUtil.toJson(newNbiData));
        execTestCase(createRequest, new FailedChecker());

        // siteId is empty
        newNbiData.setSrcNeId("NeId1");
        newNbiData.setDestNeId("NeId2");
        createRequest.setData(JsonUtil.toJson(newNbiData));
        execTestCase(createRequest, new FailedChecker());

        // site not exist
        newNbiData.setSrcNeId("NeId1");
        newNbiData.setDestNeId("NeId2");
        newNbiData.setSiteId("unexist");
        createRequest.setData(JsonUtil.toJson(newNbiData));
        execTestCase(createRequest, new FailedChecker());

        // template name is empty
        newNbiData.setSrcNeId("NeId1");
        newNbiData.setDestNeId("NeId2");
        newNbiData.setSiteId("test_uuid");
        createRequest.setData(JsonUtil.toJson(newNbiData));
        execTestCase(createRequest, new FailedChecker());

        // template is not exist
        newNbiData.setSrcNeId("NeId1");
        newNbiData.setDestNeId("NeId2");
        newNbiData.setSiteId("test_uuid");
        newNbiData.setVpnTemplateName("unexist");
        createRequest.setData(JsonUtil.toJson(newNbiData));
        execTestCase(createRequest, new FailedChecker());

        // model is null
        createRequest.setData(null);
        execTestCase(createRequest, new FailedChecker());

        newNbiData.setSrcNeId("NeId1");
        newNbiData.setDestNeId("NeId2");
        newNbiData.setSiteId("test_uuid");
        newNbiData.setVpnTemplateName("enterprise_l2cpe_EthernetNetwork");
        createRequest.setData(JsonUtil.toJson(newNbiData));
        execTestCase(createRequest, new SuccessChecker());

        // internal connection is exist
        execTestCase(createRequest, new FailedChecker());

        HttpRquestResponse deleteHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_INTERNAL_CONNECTION_FILE);
        HttpRequest deleteReq = deleteHttpObject.getRequest();
        deleteReq.setUri(PathReplace.replaceUuid("uuid", deleteReq.getUri(), "test_uuid"));
        execTestCase(deleteReq, new SuccessChecker());
    }

    @Test
    public void testOperateInternalConnectionSuccess() throws ServiceException {
        HttpRquestResponse httpCreateObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(CREATE_INTERNAL_CONNECTION_FILE);
        HttpRequest createRequest = httpCreateObject.getRequest();
        InternalVpnConnection newNbiData =
                JsonUtil.fromJson(createRequest.getData(), new TypeReference<InternalVpnConnection>() {});

        newNbiData.setSrcNeId("NeId1");
        newNbiData.setDestNeId("NeId2");
        newNbiData.setSiteId("test_uuid");
        newNbiData.setVpnTemplateName("enterprise_l2cpe_EthernetNetwork");
        createRequest.setData(JsonUtil.toJson(newNbiData));
        execTestCase(createRequest, new SuccessChecker());

        HttpRquestResponse updateHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(UPDATE_INTERNAL_CONNECTION_FILE);
        HttpRequest updateReq = updateHttpObject.getRequest();
        updateReq.setUri(PathReplace.replaceUuid("uuid", updateReq.getUri(), "test_uuid"));
        execTestCase(updateReq, new SuccessChecker());

        HttpRquestResponse queryHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(QUERY_INTERNAL_CONNECTION_FILE);
        HttpRequest queryReq = queryHttpObject.getRequest();
        queryReq.setUri(PathReplace.replaceUuid("uuid", queryReq.getUri(), "test_uuid"));
        execTestCase(queryReq, new SuccessChecker());

        HttpRquestResponse batchQueryHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(BATCH_QUERY_INTERNAL_CONNECTION_FILE);
        HttpRequest batchQueryReq = batchQueryHttpObject.getRequest();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("name", "test_name");
        batchQueryReq.setQueries(paramMap);
        execTestCase(batchQueryReq, new SuccessChecker());

        HttpRquestResponse httpDeployObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DEPLOY_INTERNAL_CONNECTION_FILE);
        HttpRequest deployRequest = httpDeployObject.getRequest();
        deployRequest.setUri(PathReplace.replaceUuid("uuid", deployRequest.getUri(), "test_uuid"));
        execTestCase(deployRequest, new SuccessChecker());

        HttpRquestResponse httpUneployObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(UNDEPLOY_INTERNAL_CONNECTION_FILE);
        HttpRequest undeployRequest = httpUneployObject.getRequest();
        undeployRequest.setUri(PathReplace.replaceUuid("uuid", undeployRequest.getUri(), "test_uuid"));
        execTestCase(undeployRequest, new SuccessChecker());

        execTestCase(undeployRequest, new SuccessChecker());

        execTestCase(deployRequest, new SuccessChecker());

        HttpRquestResponse deleteHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DELETE_INTERNAL_CONNECTION_FILE);
        HttpRequest deleteReq = deleteHttpObject.getRequest();
        deleteReq.setUri(PathReplace.replaceUuid("uuid", deleteReq.getUri(), "test_uuid"));
        execTestCase(deleteReq, new SuccessChecker());

        execTestCase(deleteReq, new SuccessChecker());
    }

    @Test
    public void testOperateInternalConnectionFailed() throws ServiceException {

        HttpRquestResponse updateHttpObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(UPDATE_INTERNAL_CONNECTION_FILE);
        HttpRequest updateReq = updateHttpObject.getRequest();
        updateReq.setUri(PathReplace.replaceUuid("uuid", updateReq.getUri(), "test_uuid"));
        execTestCase(updateReq, new FailedChecker());

        updateReq.setData(null);
        execTestCase(updateReq, new FailedChecker());

        HttpRquestResponse httpDeployObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(DEPLOY_INTERNAL_CONNECTION_FILE);
        HttpRequest deployRequest = httpDeployObject.getRequest();
        deployRequest.setUri(PathReplace.replaceUuid("uuid", deployRequest.getUri(), "test_uuid"));
        execTestCase(deployRequest, new FailedChecker());

        HttpRquestResponse httpUneployObject =
                HttpModelUtils.praseHttpRquestResponseFromFile(UNDEPLOY_INTERNAL_CONNECTION_FILE);
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
