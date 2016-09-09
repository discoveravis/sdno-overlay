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

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.sbi.IMicroSvcBasicOper;
import org.openo.sdno.rest.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * IPSec Services with CRUD implementations<br>
 * 
 * @author
 * @version SDNO 0.5 01-Jun-2016
 */
public class IpSecService implements IMicroSvcBasicOper<OverlayVpn> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpSecService.class);

    private static volatile IpSecService instance = null;

    private static final String IPSEC_VPN_BASIC_PATH = "/rest/svc/ipsec/v1/ipsecs";

    private static final String IPSEC_VPN_QUERY_PATH = "/rest/svc/ipsec/v1/ipsecs//{connectionid}";

    private IpSecService() {

    }

    /**
     * Get singleton Instance of the IP Sec service<br>
     * 
     * @return IpSecService instance
     * @since SDNO 0.5
     */
    public static synchronized IpSecService getInstance() {
        if(instance == null) {
            instance = new IpSecService();
        }
        return instance;
    }

    @Override
    public ResultRsp<OverlayVpn> create(HttpServletRequest req, OverlayVpn request) throws ServiceException {
        if(null == request) {
            LOGGER.info("IPSec. do not need creat OverlayVpn");
            return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        restfulParametes.setRawData(JsonUtil.toJson(request));

        RestfulResponse response = RestfulProxy.post(IPSEC_VPN_BASIC_PATH, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<OverlayVpn>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        return getResultRspFromResponse(response, request);
    }

    @Override
    public ResultRsp<OverlayVpn> query(HttpServletRequest req, String uuid) throws ServiceException {

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        String queryUrl = MessageFormat.format(IPSEC_VPN_QUERY_PATH, uuid);
        RestfulResponse response = RestfulProxy.post(queryUrl, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<OverlayVpn>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        return getResultRspFromResponse(response, null);
    }

    @Override
    public ResultRsp<OverlayVpn> update(HttpServletRequest req, OverlayVpn request) throws ServiceException {
        return null;
    }

    @Override
    public ResultRsp<OverlayVpn> delete(HttpServletRequest req, String uuid) throws ServiceException {
        if(!StringUtils.hasLength(uuid)) {
            return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        RestfulResponse response = RestfulProxy.delete(IPSEC_VPN_BASIC_PATH + "/" + uuid, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<OverlayVpn>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("IPSec. delete return error");
            return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_FAILED);
        }

        ResultRsp<String> deleteResult =
                JsonUtil.fromJson(response.getResponseContent(), new TypeReference<ResultRsp<String>>() {});
        if(!deleteResult.isSuccess()) {
            LOGGER.error("IPSec. delete return error");
            return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_FAILED);
        }

        return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_SUCCESS);
    }

    @Override
    public ResultRsp<OverlayVpn> deploy(HttpServletRequest req, OverlayVpn request) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultRsp<OverlayVpn> undeploy(HttpServletRequest req, OverlayVpn request) throws ServiceException {
        if(!StringUtils.hasLength(request.getUuid())) {
            return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        restfulParametes.setRawData(JsonUtil.toJson(request));

        RestfulResponse response =
                RestfulProxy.put(IPSEC_VPN_BASIC_PATH + "/" + request.getUuid() + "/undeploy", restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<OverlayVpn>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        return getResultRspFromResponse(response, request);
    }

    private ResultRsp<OverlayVpn> getResultRspFromResponse(RestfulResponse response, OverlayVpn request) {
        try {
            String overlayVpnContent = ResponseUtils.transferResponse(response);
            ResultRsp<OverlayVpn> result =
                    JsonUtil.fromJson(overlayVpnContent, new TypeReference<ResultRsp<OverlayVpn>>() {});
            LOGGER.info("IPSec. OverlayVpn operation finish, result = " + result.toString());
            return new ResultRsp<OverlayVpn>(result, request);
        } catch(ServiceException e) {
            LOGGER.error("IPsec. except info: ", e);
            return new ResultRsp<OverlayVpn>(e.getId(), e.getExceptionArgs());
        }
    }

}
