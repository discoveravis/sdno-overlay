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
import org.openo.sdno.overlayvpn.model.servicechain.ServiceChainPath;
import org.openo.sdno.overlayvpn.model.servicechain.ServiceChainPathRsp;
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

    @Override
    public void addRequestResponsePairs() {

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "createvxlansuccess.json", new VxLanSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "createipsecsuccess.json", new IpSecSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "delvxlansuccess.json", new VxLanSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "delipsecsuccess.json", new IpSecSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "undeployipsec.json", new IpSecSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "undeployvxlan.json", new VxLanSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "deployipsec.json");

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "deployvxlan.json");

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "deleteipsec.json");

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "deletevxlan.json");

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "createvpcsuccess.json", new VpcSubnetResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "createsubnetsuccess.json", new VpcSubnetResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "deletevpcsuccess.json", new VpcSubnetResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "deletesubnetsuccess.json", new VpcSubnetResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "createservicechainsuccess.json",
                new ServiceChainSuccessResponseHandler());

        this.addRequestResponsePair(MOCO_TEST_PREFIX + "deleteservicechainsuccess.json");

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

    private class VpcSubnetResponseHandler extends MocoResponseHandler {

        @Override
        public void processRequestandResponse(HttpRquestResponse httpObject) {
            HttpRequest req = httpObject.getRequest();
            HttpResponse res = httpObject.getResponse();

            System.out.println(req);
            System.out.println(res);

        }
    }

    private class ServiceChainSuccessResponseHandler extends MocoResponseHandler {

        @Override
        public void processRequestandResponse(HttpRquestResponse httpObject) {
            HttpRequest httpRequest = httpObject.getRequest();
            HttpResponse httpResponse = httpObject.getResponse();
            System.out.println(httpRequest);
            System.out.println(httpResponse);

            ServiceChainPathRsp sfpr = new ServiceChainPathRsp();
            ServiceChainPath sfp = JsonUtil.fromJson(httpRequest.getData(), new TypeReference<ServiceChainPath>() {});

            sfpr.setUuid(sfp.getName());
            ResultRsp<ServiceChainPathRsp> newResult = new ResultRsp<ServiceChainPathRsp>(ErrorCode.OVERLAYVPN_SUCCESS);

            httpResponse.setStatus(200);
            newResult.setData(sfpr);
            httpResponse.setData(JsonUtil.toJson(newResult));
        }
    }

}
