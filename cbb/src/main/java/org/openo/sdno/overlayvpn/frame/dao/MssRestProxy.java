/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.frame.dao;

import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.overlayvpn.frame.dao.model.RestAction;
import org.openo.sdno.overlayvpn.util.rsp.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The mss service restful proxy.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
public class MssRestProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(MssRestProxy.class);

    private String owner = null;

    /**
     * Constructor.<br>
     * 
     * @param owner The owner to set
     * @since SDNO 0.5
     */
    public MssRestProxy(String owner) {
        super();
        this.owner = owner;
    }

    /**
     * The get function proxy.<br>
     * 
     * @param url The url for querying
     * @param body The body for querying
     * @param restfulParametes The restful parameters for querying
     * @return The queried response
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    public RestfulResponse get(String url, String body, RestfulParametes restfulParametes) throws ServiceException {
        return restInvoke(url, RestAction.GET, body, restfulParametes);
    }

    /**
     * The insert function proxy.<br>
     * 
     * @param url The url for inserting
     * @param body The body for inserting
     * @param restfulParametes The restful parameters for inserting
     * @return The inserted response
     * @throws ServiceException when inserting failed
     * @since SDNO 0.5
     */
    public RestfulResponse post(String url, String body, RestfulParametes restfulParametes) throws ServiceException {
        return restInvoke(url, RestAction.POST, body, restfulParametes);
    }

    /**
     * The delete function proxy.<br>
     * 
     * @param url The url for deleting
     * @param body The body for deleting
     * @param restfulParametes The restful parameters for deleting
     * @return The deleted response
     * @throws ServiceException when deleting failed
     * @since SDNO 0.5
     */
    public RestfulResponse delete(String url, String body, RestfulParametes restfulParametes) throws ServiceException {
        return restInvoke(url, RestAction.DELETE, body, restfulParametes);
    }

    /**
     * The update function proxy.<br>
     * 
     * @param url The url for updating
     * @param body The body for updating
     * @param restfulParametes The restful parameters for updating
     * @return The updated response
     * @throws ServiceException when updating failed
     * @since SDNO 0.5
     */
    public RestfulResponse put(String url, String body, RestfulParametes restfulParametes) throws ServiceException {
        return restInvoke(url, RestAction.PUT, body, restfulParametes);
    }

    private RestfulResponse restInvoke(String url, RestAction action, String body, RestfulParametes restParams)
            throws ServiceException {
        RestfulParametes innerRestParameters = constructRestParameters(restParams);

        if(StringUtils.isNotEmpty(body)) {
            innerRestParameters.setRawData(body);
        }
        RestfulResponse response = null;

        switch(action) {
            case GET:
                response = RestfulProxy.get(url, innerRestParameters);
                break;
            case POST:
                response = RestfulProxy.post(url, innerRestParameters);
                break;
            case PUT:
                response = RestfulProxy.put(url, innerRestParameters);
                break;
            case DELETE:
                response = RestfulProxy.delete(url, innerRestParameters);
                break;
            default:
                LOGGER.error("Mss.rest.error,rest action is not supported" + action);
                throw new InnerErrorServiceException("Mss.rest.error,rest action is not supported" + action);
        }
        if(!ResponseUtils.validHttpStatus(response.getStatus())) {
            LOGGER.warn("Mss request failed,url=" + url + ",response=" + response.getStatus() + ":"
                    + response.getResponseContent());
        }
        return response;
    }

    private RestfulParametes constructRestParameters(RestfulParametes params) {
        RestfulParametes restParams;
        if(params == null) {
            restParams = new RestfulParametes();
        } else {
            restParams = params;
        }
        restParams.putHttpContextHeader("x-mss-request-owner", owner);
        restParams.putHttpContextHeader("Content-Type", "application/json;charset=UTF-8");
        restParams.putHttpContextHeader("Accept", "application/json");
        return restParams;
    }
}
