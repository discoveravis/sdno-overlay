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

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.netmodel.vxlan.NeVxlanInstance;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.testframework.http.model.HttpModelUtils;
import org.openo.sdno.testframework.http.model.HttpResponse;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.moco.MocoHttpServer;
import org.openo.sdno.testframework.moco.requestmacher.RequestMatcherUtils;
import org.openo.sdno.testframework.moco.responsehandler.MocoResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverlayVpnFailServer extends MocoHttpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OverlayVpnFailServer.class);

    public OverlayVpnFailServer() {
        super();
    }

    public OverlayVpnFailServer(String configFile) {
        super(configFile);
    }

    @Override
    public void addRequestResponsePairs() {

        HttpRquestResponse httpObject = null;
        try {
            httpObject = HttpModelUtils
                    .praseHttpRquestResponseFromFile("src/main/resources/MocoVxLanSbiAdapter/createvxlan.json");
        } catch(ServiceException e) {
            LOGGER.error("Read Json File failed!!");
            return;
        }

        this.addRequestResponsePair(RequestMatcherUtils.createDefaultRequestMatcher(httpObject.getRequest()),
                new CreateVxLanFailResponseHandler());
    }

    private class CreateVxLanFailResponseHandler extends MocoResponseHandler {

        @Override
        public void processRequestandResponse(HttpRquestResponse httpObject) {

            HttpResponse httpResponse = httpObject.getResponse();

            ResultRsp<List<NeVxlanInstance>> newResult =
                    new ResultRsp<List<NeVxlanInstance>>(ErrorCode.OVERLAYVPN_FAILED);

            httpResponse.setStatus(500);
            httpResponse.setData(JsonUtil.toJson(newResult));
        }
    }
}
