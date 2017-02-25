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

package org.openo.sdno.overlayvpnservice.site2dc.msbmanager;

import java.io.File;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.testframework.http.model.HttpRequest;
import org.openo.sdno.testframework.http.model.HttpRquestResponse;
import org.openo.sdno.testframework.restclient.HttpRestClient;
import org.openo.sdno.testframework.util.file.FileUtils;
import org.openo.sdno.testframework.util.file.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsbRegisterManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsbRegisterManager.class);

    private static final String MSB_REGISTER_FILE = "src/integration-test/resources/msb/registermsb.json";

    private static final String MSB_UNREGISTER_FILE = "src/integration-test/resources/msb/unregistermsb.json";

    private static final HttpRestClient restClient = new HttpRestClient();

    public static void registerMsb() throws ServiceException {
        String content = FileUtils.readFromJson(new File(MSB_REGISTER_FILE));
        List<HttpRquestResponse> httpObjectList =
                JsonUtil.fromJson(content, new TypeReference<List<HttpRquestResponse>>() {});
        for(HttpRquestResponse httpObject : httpObjectList) {
            HttpRequest httpRegisterRequest = httpObject.getRequest();
            RestfulParametes restfulParametes = new RestfulParametes();
            restfulParametes.setHeaderMap(httpRegisterRequest.getHeaders());
            restfulParametes.setRawData(JsonUtil.toJson(httpRegisterRequest.getJson()));
            RestfulResponse registerReponse = restClient.post(httpRegisterRequest.getUri(), restfulParametes);
            LOGGER.info("Register Msb success,the node is :" + JsonUtil.toJson(httpRegisterRequest.getJson()));
            if(!HttpCode.isSucess(registerReponse.getStatus())) {
                LOGGER.error("Register Msb failed,the node is :" + JsonUtil.toJson(httpRegisterRequest.getJson()));
                throw new ServiceException("Register Msb failed");
            }
        }
    }

    public static void unRegisterMsb() throws ServiceException {
        String content = FileUtils.readFromJson(new File(MSB_UNREGISTER_FILE));
        List<HttpRquestResponse> httpObjectList =
                JsonUtil.fromJson(content, new TypeReference<List<HttpRquestResponse>>() {});
        for(HttpRquestResponse httpObject : httpObjectList) {
            HttpRequest httpUnRegisterRequest = httpObject.getRequest();
            RestfulParametes restfulParametes = new RestfulParametes();
            restfulParametes.setHeaderMap(httpUnRegisterRequest.getHeaders());
            RestfulResponse unRegisterReponse = restClient.delete(httpUnRegisterRequest.getUri(), restfulParametes);
            LOGGER.info("Register Msb success,the node is :" + httpUnRegisterRequest.getUri());
            if(!HttpCode.isSucess(unRegisterReponse.getStatus())) {
                LOGGER.error("Register Msb failed,the node is :" + httpUnRegisterRequest.getUri());
                throw new ServiceException("UnRegister Msb failed");
            }
        }
    }
}
