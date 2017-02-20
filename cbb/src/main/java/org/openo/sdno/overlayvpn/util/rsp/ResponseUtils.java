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

package org.openo.sdno.overlayvpn.util.rsp;

import org.apache.http.HttpStatus;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.exception.NotFoundServiceException;
import org.openo.sdno.exception.ParameterServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class of response.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 3, 2017
 */
public class ResponseUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);

    private ResponseUtils() {
    }

    /**
     * Validate the http status.<br>
     * 
     * @param httpStatus The http status
     * @return true when the status is 200~299,otherwise is false
     * @since SDNO 0.5
     */
    public static boolean validHttpStatus(int httpStatus) {
        return httpStatus / 100 == 2;
    }

    /**
     * Check the response,and through the status to return or throw different exception.<br>
     * 
     * @param response The restful response
     * @throws ServiceException when the http status is not legal
     * @since SDNO 0.5
     */
    public static void checkResponseAndThrowException(RestfulResponse response) throws ServiceException {
        int httpStatus = response.getStatus();
        if(validHttpStatus(httpStatus)) {
            return;
        }
        if(HttpStatus.SC_NOT_FOUND == httpStatus) {
            LOGGER.error("The interface not found.");
            throw new NotFoundServiceException("The interface is not found.");
        } else if(HttpStatus.SC_BAD_REQUEST == httpStatus) {
            LOGGER.error("The parameters are error.");
            throw new ParameterServiceException("The parameter is invalid.");
        } else {
            LOGGER.error("The response contains an inner exception.");
            throw new InnerErrorServiceException("The response contains inner exception.");
        }
    }
}
