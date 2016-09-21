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

package org.openo.sdno.overlayvpn.sbi.overlayvpn;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicechain.ServiceChainPath;
import org.openo.sdno.overlayvpn.model.servicechain.ServiceChainPathRsp;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.sbi.IMicroSvcBasicOper;
import org.openo.sdno.rest.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * ServiceChain service SBI class.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 20, 2016
 */
public class ServiceChainService implements IMicroSvcBasicOper<ServiceChainPath> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceChainService.class);

    private static final String SERVICE_CHAIN_BASIC_PATH = "/openoapi/sdnoservicechain/v1/paths";

    private static final String SERVICE_CHAIN_PATH_KEY = "serviceChainPath";

    @Override
    public ResultRsp<ServiceChainPath> create(HttpServletRequest req, ServiceChainPath sfp) throws ServiceException {

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        Map<String, ServiceChainPath> serviceChainPathMap = new HashMap<String, ServiceChainPath>();
        serviceChainPathMap.put(SERVICE_CHAIN_PATH_KEY, sfp);

        restfulParametes.setRawData(JsonUtil.toJson(serviceChainPathMap));

        RestfulResponse response = RestfulProxy.post(SERVICE_CHAIN_BASIC_PATH, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<ServiceChainPath>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        return getResultRspFromResponse(response, sfp);

    }

    @Override
    public ResultRsp<ServiceChainPath> query(HttpServletRequest req, String uuid) throws ServiceException {

        return null;
    }

    @Override
    public ResultRsp<ServiceChainPath> update(HttpServletRequest req, ServiceChainPath request)
            throws ServiceException {

        return null;
    }

    @Override
    public ResultRsp<ServiceChainPath> delete(HttpServletRequest req, String uuid) throws ServiceException {
        if(!StringUtils.hasLength(uuid)) {
            return new ResultRsp<ServiceChainPath>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        String url = MessageFormat.format(SERVICE_CHAIN_BASIC_PATH + "/{0}", uuid);

        RestfulResponse response = RestfulProxy.delete(url, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<ServiceChainPath>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        if(!HttpCode.isSucess(response.getStatus())) {
            return new ResultRsp<ServiceChainPath>(ErrorCode.OVERLAYVPN_FAILED);
        }

        return new ResultRsp<ServiceChainPath>(ErrorCode.OVERLAYVPN_SUCCESS);
    }

    @Override
    public ResultRsp<ServiceChainPath> deploy(HttpServletRequest req, ServiceChainPath request)
            throws ServiceException {

        return null;
    }

    @Override
    public ResultRsp<ServiceChainPath> undeploy(HttpServletRequest req, ServiceChainPath request)
            throws ServiceException {

        return null;
    }

    private ResultRsp<ServiceChainPath> getResultRspFromResponse(RestfulResponse response, ServiceChainPath sfp) {
        try {
            String content = ResponseUtils.transferResponse(response);
            ResultRsp<ServiceChainPathRsp> resultObj =
                    JsonUtil.fromJson(content, new TypeReference<ResultRsp<ServiceChainPathRsp>>() {});
            LOGGER.info("ServiceChainPath. OverlayVpn operation finish, result = " + resultObj.toString());

            return new ResultRsp<ServiceChainPath>(resultObj, sfp);
        } catch(ServiceException e) {
            LOGGER.error("ServiceChainPath except information: ", e);
            return new ResultRsp<ServiceChainPath>(e.getId(), e.getExceptionArgs());
        }
    }
}
