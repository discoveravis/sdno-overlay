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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.tunnel.Tunnel;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.sbi.IMicroSvcBasicOper;
import org.openo.sdno.rest.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * VxLan Services with CRUD implementations<br>
 * 
 * @author
 * @version SDNO 0.5 June 7, 2016
 */
public class VxLANService implements IMicroSvcBasicOper<OverlayVpn> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VxLANService.class);

    private static volatile VxLANService instance = null;

    private static final String VXLAN_VPN_BASIC_PATH = "/openoapi/sdnovxlan/v1/vxlans";

    private static final String VXLAN_QUERY_TUNNEL_PATH = "/openoapi/sdnovxlan/v1/vxlans/{connectionid}/vxlantunnels";

    /**
     * Get singleton Instance of VxLANService.<br>
     * 
     * @return Singleton Instance of VxLan Service
     * @since SDNO 0.5
     */
    public static synchronized VxLANService getInstance() {
        if(instance == null) {
            instance = new VxLANService();
        }
        return instance;
    }

    @Override
    public ResultRsp<OverlayVpn> create(HttpServletRequest req, OverlayVpn request) throws ServiceException {
        if(null == request) {
            LOGGER.info("VXLAN. do not need creat OverlayVpn");
            return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");
        restfulParametes.setRawData(JsonUtil.toJson(request));

        RestfulResponse response = RestfulProxy.post(VXLAN_VPN_BASIC_PATH, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<OverlayVpn>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        return getResultRspFromResponse(response, request);
    }

    @Override
    public ResultRsp<OverlayVpn> update(HttpServletRequest req, OverlayVpn request) throws ServiceException {
        return null;
    }

    /**
     * Query tunnel info.<br>
     * 
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @param connectionUuid connection uuid
     * @return tunnel info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public ResultRsp<List<Tunnel>> queryTunnel(HttpServletRequest req, HttpServletResponse resp, String connectionUuid)
            throws ServiceException {
        RestfulParametes restfulParameters = new RestfulParametes();
        restfulParameters.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        String queryUrl = MessageFormat.format(VXLAN_QUERY_TUNNEL_PATH, connectionUuid);

        RestfulResponse response = RestfulProxy.get(queryUrl, restfulParameters);

        if(null == response) {
            LOGGER.error("query tunnel failed!svc return null");
            throw new ServiceException(ErrorCode.OVERLAYVPN_FAILED, "query tunnel failed");
        }

        // if return failed,need to throw exception with detailed error info
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("query tunnel failed!svc return error:{0}", response.getResponseContent());
            throw new ServiceException(ErrorCode.OVERLAYVPN_FAILED, response.getResponseContent());
        }

        ResultRsp<List<Tunnel>> rsp = null;
        try {
            String tunnelContent = ResponseUtils.transferResponse(response);
            rsp = JsonUtil.fromJson(tunnelContent, new TypeReference<ResultRsp<List<Tunnel>>>() {});
        } catch(ServiceException e) {
            LOGGER.error("deserialize failed!response:" + response.getResponseContent(), e);
        }

        if((null != rsp) && !rsp.isSuccess()) {
            LOGGER.error("deserialize  failed!errorCode=" + rsp.getErrorCode() + ",message:" + rsp.toString());
        }
        return rsp;
    }

    @Override
    public ResultRsp<OverlayVpn> query(HttpServletRequest req, String uuid) throws ServiceException {
        return null;
    }

    @Override
    public ResultRsp<OverlayVpn> delete(HttpServletRequest req, String uuid) throws ServiceException {
        if(!StringUtils.hasLength(uuid)) {
            return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");

        RestfulResponse response = RestfulProxy.delete(VXLAN_VPN_BASIC_PATH + "/" + uuid, restfulParametes);
        if(response.getStatus() == HttpCode.NOT_FOUND) {
            return new ResultRsp<OverlayVpn>(ErrorCode.RESTFUL_COMMUNICATION_FAILED);
        }

        if(!HttpCode.isSucess(response.getStatus())) {
            return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_FAILED);
        }

        ResultRsp<String> deleteResult =
                JsonUtil.fromJson(response.getResponseContent(), new TypeReference<ResultRsp<String>>() {});
        if(!deleteResult.isSuccess()) {
            return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_FAILED);
        }

        return new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_SUCCESS);
    }

    @Override
    public ResultRsp<OverlayVpn> deploy(HttpServletRequest req, OverlayVpn request) throws ServiceException {
        return null;
    }

    @Override
    public ResultRsp<OverlayVpn> undeploy(HttpServletRequest req, OverlayVpn request) throws ServiceException {
        return null;
    }

    private ResultRsp<OverlayVpn> getResultRspFromResponse(RestfulResponse response, OverlayVpn request) {
        try {
            String overlayVpnContent = ResponseUtils.transferResponse(response);
            ResultRsp<OverlayVpn> resultObj =
                    JsonUtil.fromJson(overlayVpnContent, new TypeReference<ResultRsp<OverlayVpn>>() {});
            LOGGER.info("VXLAN's OverlayVpn operation finish, result = " + resultObj.toString());
            return new ResultRsp<OverlayVpn>(resultObj, request);
        } catch(ServiceException e) {
            LOGGER.error("VXLAN's except information: ", e);
            return new ResultRsp<OverlayVpn>(e.getId(), e.getExceptionArgs());
        }
    }
}
