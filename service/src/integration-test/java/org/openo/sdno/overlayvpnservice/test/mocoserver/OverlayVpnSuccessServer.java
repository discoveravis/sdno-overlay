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

package org.openo.sdno.overlayvpnservice.test.mocoserver;

import org.codehaus.jackson.type.TypeReference;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.moco.MocoHttpServer;
import org.openo.sdno.testframework.moco.responsehandler.MocoResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverlayVpnSuccessServer extends MocoHttpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OverlayVpnSuccessServer.class);

    private static String MOCO_TEST_PREFIX = "src/integration-test/resources/testcase/moco/";

    public OverlayVpnSuccessServer() {
        super();
    }

    public OverlayVpnSuccessServer(String configFile) {
        super(configFile);
    }

    @Override
    public void addRequestResponsePairs() {

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "createvxlansuccess.json", new VxLanSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "createipsecsuccess.json", new IpSecSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "delvxlansuccess.json", new VxLanSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "delipsecsuccess.json", new IpSecSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "undeployipsec.json", new IpSecSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "undeployvxlan.json", new VxLanSuccessResponseHandler());

    }

    private class VxLanSuccessResponseHandler extends MocoResponseHandler {

        @Override
        public void processRequestandResponse(HttpRquestResponse httpObject) {

            HttpRequest httpRequest = httpObject.getRequest();
            HttpResponse httpResponse = httpObject.getResponse();
            OverlayVpn inputInstanceList = JsonUtil.fromJson(httpRequest.getData(), new TypeReference<OverlayVpn>() {});

            ResultRsp<OverlayVpn> newResult = new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_SUCCESS);

            httpResponse.setStatus(200);
            newResult.setData(inputInstanceList);
            httpResponse.setData(JsonUtil.toJson(newResult));
        }
    }

    private class IpSecSuccessResponseHandler extends MocoResponseHandler {

        @Override
        public void processRequestandResponse(HttpRquestResponse httpObject) {

            HttpRequest httpRequest = httpObject.getRequest();
            HttpResponse httpResponse = httpObject.getResponse();
            OverlayVpn inputInstanceList = JsonUtil.fromJson(httpRequest.getData(), new TypeReference<OverlayVpn>() {});

            ResultRsp<OverlayVpn> newResult = new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_SUCCESS);

            httpResponse.setStatus(200);
            newResult.setData(inputInstanceList);
            httpResponse.setData(JsonUtil.toJson(newResult));
        }
    }

}
