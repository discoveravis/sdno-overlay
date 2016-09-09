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

package org.openo.sdno.overlayvpn.sbi.cpe;

import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.netmodel.localsite.AdapterDeviceNetwork;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.security.authentication.TokenPlacer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Class for REST call to the SBI adapter for configuring the network<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 01-Jun-2016
 */
@Service
public class NetworkAdapterService {

    private static final String ADAPTER_BASE_URL = "/rest/svc/sbiadp/controller/";

    private static final String CONFIG_NETWORK_RESOURCE = "/overlay/v1/{deviceid}/network";

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkAdapterService.class);

    /**
     * to create a network <br>
     * 
     * @param ctrlUuid UUID of the controller
     * @param deviceid Device ID to be operation on
     * @param networkList List of the adapter device network to be configured
     * @return Operation result with the list of adapter device network
     * @throws ServiceException throws ServiceException if the input is invalid or the operation
     *             fails
     * @since SDNO 0.5
     */
    public ResultRsp<List<AdapterDeviceNetwork>> createNetwork(String ctrlUuid, String deviceid,
            List<AdapterDeviceNetwork> networkList)
            throws org.openo.baseservice.remoteservice.exception.ServiceException {
        LOGGER.info("enter createNetwork");
        RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");
        // add token
        TokenPlacer.addTenantToken2HttpHeader(restfulParametes);
        String strJsonReq = JsonUtil.toJson(networkList);
        restfulParametes.setRawData(strJsonReq);
        restfulParametes.put("resource", CONFIG_NETWORK_RESOURCE);
        String url = ADAPTER_BASE_URL + ctrlUuid + CONFIG_NETWORK_RESOURCE;
        url = url.replace("{deviceid}", deviceid);
        RestfulResponse response = RestfulProxy.post(url, restfulParametes);

        if(null == response) {
            LOGGER.error("createNetwork failed!svc process return null");
            throw new ServiceException(ErrorCode.OVERLAYVPN_FAILED, "create cpe failed");
        }
        // check the response status and proceed
        if(!HttpCode.isSucess(response.getStatus())) {
            LOGGER.error("createNetwork  failed!svc return error:{0}", response.getResponseContent());
            throw new ServiceException(ErrorCode.OVERLAYVPN_FAILED, response.getResponseContent());
        }

        ResultRsp<List<AdapterDeviceNetwork>> rsp = null;
        rsp = JsonUtil.fromJson(response.getResponseContent(),
                new TypeReference<ResultRsp<List<AdapterDeviceNetwork>>>() {});

        if((null != rsp) && !rsp.isSuccess()) {
            LOGGER.error("deserialize  failed!errorCode=" + rsp.getErrorCode() + ",message:" + rsp.toString());
        }
        LOGGER.info("end createNetwork");
        return rsp;
    }
}
