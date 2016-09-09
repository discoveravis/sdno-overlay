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
import org.openo.sdno.overlayvpn.model.servicemodel.SubNet;
import org.openo.sdno.overlayvpn.model.servicemodel.Vpc;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.sbi.IMicroSvcBasicOper;
import org.openo.sdno.rest.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Vpc SubNets Services with CRUD implementations.<br>
 * 
 * @author
 * @version SDNO 0.5 08-Aug-2016
 */
public class VpcSubnetService implements IMicroSvcBasicOper<SubNet> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpcSubnetService.class);

    private static final String VPC_SUBNET_BASIC_PATH = "/rest/svc/vpc/v1/subnets";

    /**
     * Instance of the VpcSubnetService
     */
    private static volatile VpcSubnetService instance = null;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    private VpcSubnetService() {

    }

    /**
     * Get singleton Instance of the VpcSubnetService<br>
     * 
     * @return VpcSubnetService instance
     * @since SDNO 0.5
     */
    public static synchronized VpcSubnetService getInstance() {
        if(instance == null) {
            instance = new VpcSubnetService();
        }
        return instance;
    }

    @Override
    public ResultRsp<SubNet> create(HttpServletRequest req, SubNet request) throws ServiceException {
        if(null == request) {
            LOGGER.info("Vpc SubNet. do not need creat OverlayVpn");
            return new ResultRsp<SubNet>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        restfulParametes.setRawData(JsonUtil.toJson(request));

        RestfulResponse response = RestfulProxy.post(VPC_SUBNET_BASIC_PATH, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<SubNet>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        return getResultRspFromResponse(response, request);
    }

    @Override
    public ResultRsp<SubNet> query(HttpServletRequest req, String uuid) throws ServiceException {
        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        RestfulResponse response = RestfulProxy.get(VPC_SUBNET_BASIC_PATH + "/" + uuid, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<SubNet>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        return getResultRspFromResponse(response, null);
    }

    @Override
    public ResultRsp<SubNet> update(HttpServletRequest req, SubNet request) throws ServiceException {
        return null;
    }

    @Override
    public ResultRsp<SubNet> delete(HttpServletRequest req, String uuid) throws ServiceException {
        if(!StringUtils.hasLength(uuid)) {
            return new ResultRsp<SubNet>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        RestfulResponse response = RestfulProxy.delete(VPC_SUBNET_BASIC_PATH + "/" + uuid, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<SubNet>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        if(!HttpCode.isSucess(response.getStatus())) {
            return new ResultRsp<SubNet>(ErrorCode.OVERLAYVPN_FAILED);
        }

        return new ResultRsp<SubNet>(ErrorCode.OVERLAYVPN_SUCCESS);
    }

    @Override
    public ResultRsp<SubNet> deploy(HttpServletRequest req, SubNet request) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultRsp<SubNet> undeploy(HttpServletRequest req, SubNet request) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    private ResultRsp<SubNet> getResultRspFromResponse(RestfulResponse response, SubNet request) {
        try {
            String vpcContent = ResponseUtils.transferResponse(response);
            ResultRsp<Vpc> result = JsonUtil.fromJson(vpcContent, new TypeReference<ResultRsp<Vpc>>() {});
            LOGGER.info("Vpc SubNet. OverlayVpn operation finish, result = " + result.toString());
            return new ResultRsp<SubNet>(result, request);
        } catch(ServiceException e) {
            LOGGER.error("Vpc SubNet. except info: ", e);
            return new ResultRsp<SubNet>(e.getId(), e.getExceptionArgs());
        }
    }

}
