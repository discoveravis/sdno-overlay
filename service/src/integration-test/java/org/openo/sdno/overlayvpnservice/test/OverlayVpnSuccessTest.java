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

package org.openo.sdno.overlayvpnservice.test;

import java.io.File;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpnservice.test.mocoserver.OverlayVpnSuccessServer;
import org.openo.sdno.testframework.checker.RegularExpChecker;
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.replace.PathReplace;
import org.openo.sdno.testframework.testmanager.TestManager;
import org.openo.sdno.testframework.util.file.FileUtils;

public class OverlayVpnSuccessTest extends TestManager {

    private OverlayVpnSuccessServer vxlanServer = new OverlayVpnSuccessServer();

    @Before
    public void setup() throws ServiceException {
        vxlanServer.start();
    }

    @After
    public void tearDown() {
        vxlanServer.stop();
    }

    @Test
    public void createFulltest() throws ServiceException {

        // Create Site to DC test case
        File createFile = new File("src/integration-test/resources/testcase/create.json");
        HttpRquestResponse createHttpObject =
                HttpModelUtils.praseHttpRquestResponse(FileUtils.readFromJson(createFile));
        HttpResponse createResponse = execTestCase(new File("src/integration-test/resources/testcase/create.json"),
                new RegularExpChecker(createHttpObject.getResponse()));
        String response = createResponse.getData();

        Map<String, String> configList = JsonUtil.fromJson(response, new TypeReference<Map<String, String>>() {});
        String uuid = configList.get("vpnId");

        // Update Site to DC test case
        File updateFile = new File("src/integration-test/resources/testcase/update.json");
        HttpRquestResponse updateHttpObject =
                HttpModelUtils.praseHttpRquestResponse(FileUtils.readFromJson(updateFile));
        HttpRequest updateRequest = updateHttpObject.getRequest();
        updateRequest.setUri(PathReplace.replaceUuid("objectId", updateHttpObject.getRequest().getUri(), uuid));
        send(updateRequest, new RegularExpChecker(updateHttpObject.getResponse()));

        // Query Site to DC
        File queryFile = new File("src/integration-test/resources/testcase/query.json");
        HttpRquestResponse queryHttpObject = HttpModelUtils.praseHttpRquestResponse(FileUtils.readFromJson(queryFile));
        HttpRequest queryRequest = queryHttpObject.getRequest();
        queryRequest.setUri(PathReplace.replaceUuid("objectId", queryHttpObject.getRequest().getUri(), uuid));
        send(queryRequest, new RegularExpChecker(queryHttpObject.getResponse()));

        // Delete Site to DC
        File deleteFile = new File("src/integration-test/resources/testcase/delete.json");
        HttpRquestResponse deleteHttpObject =
                HttpModelUtils.praseHttpRquestResponse(FileUtils.readFromJson(deleteFile));
        HttpRequest deleteRequest = deleteHttpObject.getRequest();
        deleteRequest.setUri(PathReplace.replaceUuid("objectId", deleteHttpObject.getRequest().getUri(), uuid));
        send(deleteRequest, new RegularExpChecker(deleteHttpObject.getResponse()));

    }

}
