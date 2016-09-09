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

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.Vpc;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.sbi.IMicroSvcBasicOper;
import org.openo.sdno.rest.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Vpc Services with CRUD implementations.<br>
 * 
 * @author
 * @version SDNO 0.5 08-Aug-2016
 */
public class VpcService implements IMicroSvcBasicOper<Vpc> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpcService.class);

    private static final String VPC_BASIC_PATH = "/openoapi/sdnovpc/v1/vpcs";

    /**
     * Instance of the VpcService
     */
    private static volatile VpcService instance = null;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    private VpcService() {

    }

    /**
     * Get singleton Instance of the VpcService<br>
     * 
     * @return VpcService instance
     * @since SDNO 0.5
     */
    public static synchronized VpcService getInstance() {
        if(instance == null) {
            instance = new VpcService();
        }
        return instance;
    }

    @Override
    public ResultRsp<Vpc> create(HttpServletRequest req, Vpc request) throws ServiceException {
        if(null == request) {
            LOGGER.info("Vpc. do not need creat OverlayVpn");
            return new ResultRsp<Vpc>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        restfulParametes.setRawData(JsonUtil.toJson(request));

        RestfulResponse response = RestfulProxy.post(VPC_BASIC_PATH, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            LOGGER.error("Vpc Restful Interface Not Found");
            return new ResultRsp<Vpc>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        return getResultRspFromResponse(response, request);
    }

    @Override
    public ResultRsp<Vpc> query(HttpServletRequest req, String uuid) throws ServiceException {
        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        RestfulResponse response = RestfulProxy.get(VPC_BASIC_PATH + "/" + uuid, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<Vpc>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        return getResultRspFromResponse(response, null);
    }

    @Override
    public ResultRsp<Vpc> update(HttpServletRequest req, Vpc request) throws ServiceException {
        return null;
    }

    @Override
    public ResultRsp<Vpc> delete(HttpServletRequest req, String uuid) throws ServiceException {
        if(!StringUtils.hasLength(uuid)) {
            return new ResultRsp<Vpc>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        RestfulResponse response = RestfulProxy.delete(VPC_BASIC_PATH + "/" + uuid, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<Vpc>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        if(!HttpCode.isSucess(response.getStatus())) {
            return new ResultRsp<Vpc>(ErrorCode.OVERLAYVPN_FAILED);
        }

        return new ResultRsp<Vpc>(ErrorCode.OVERLAYVPN_SUCCESS);
    }

    @Override
    public ResultRsp<Vpc> deploy(HttpServletRequest req, Vpc request) throws ServiceException {
        return null;
    }

    @Override
    public ResultRsp<Vpc> undeploy(HttpServletRequest req, Vpc request) throws ServiceException {
        return null;
    }

    private ResultRsp<Vpc> getResultRspFromResponse(RestfulResponse response, Vpc request) {
        try {
            String vpcContent = ResponseUtils.transferResponse(response);
            Vpc resultVpc = JsonUtil.fromJson(vpcContent, new TypeReference<Vpc>() {});
            LOGGER.info("Vpc. OverlayVpn operation finish, result = " + resultVpc.toString());
            return new ResultRsp<Vpc>(ErrorCode.OVERLAYVPN_SUCCESS, resultVpc);
        } catch(ServiceException e) {
            LOGGER.error("Vpc. except info: ", e);
            return new ResultRsp<Vpc>(e.getId(), e.getExceptionArgs());
        }
    }

}
