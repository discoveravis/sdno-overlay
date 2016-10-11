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

package org.openo.sdno.overlayvpn.result;

import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Class of Service Exception Operation.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 3, 2016
 */
public class SvcExcptUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SvcExcptUtil.class);

    private SvcExcptUtil() {
    }

    /**
     * Throws NotFound Exception.<br>
     * 
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public static void throwNotFoundException() throws ServiceException {
        throw new ServiceException(ServiceException.DEFAULT_ID, HttpCode.NOT_FOUND);
    }

    /**
     * Throws BadRequest Exception.<br>
     * 
     * @param msg error Message
     * @throws ServiceException Exception occurs
     * @since SDNO 0.5
     */
    public static void throwBadRequestException(String msg) throws ServiceException {
        ServiceException serviceException = new ServiceException(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, msg);
        serviceException.setHttpCode(HttpCode.BAD_REQUEST);
        throw serviceException;
    }

    /**
     * Throws NotFound Exception with errorInfo.<br>
     * 
     * @param excptId exception id
     * @param desc error description
     * @param reason error reason
     * @param detail error detail
     * @param advice advice info
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static void throwNotFoundSvcExptionWithInfo(String excptId, String desc, String reason, String detail,
            String advice) throws ServiceException {
        ServiceException serviceException = new ServiceException();
        serviceException.setHttpCode(HttpCode.NOT_FOUND);
        serviceException.setId(excptId);
        throwSvcExptionWithInfo(desc, reason, detail, advice, serviceException);
    }

    /**
     * Throws BadRequest Exception with errorInfo.<br>
     * 
     * @param excptId exception id
     * @param desc error description
     * @param reason error reason
     * @param detail error detail
     * @param advice advice info
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static void throwBadReqSvcExptionWithInfo(String excptId, String desc, String reason, String detail,
            String advice) throws ServiceException {
        ServiceException serviceException = new ServiceException();
        serviceException.setHttpCode(HttpCode.BAD_REQUEST);
        serviceException.setId(excptId);
        throwSvcExptionWithInfo(desc, reason, detail, advice, serviceException);
    }

    /**
     * Throws InnerServiceError Exception with errorInfo.<br>
     * 
     * @param excptId exception id
     * @param desc error description
     * @param reason error reason
     * @param detail error detail
     * @param advice advice info
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static void throwInnerErrSvcExptionWithInfo(String excptId, String desc, String reason, String detail,
            String advice) throws ServiceException {
        ServiceException serviceException = new ServiceException();
        serviceException.setHttpCode(HttpCode.ERR_FAILED);
        serviceException.setId(excptId);
        throwSvcExptionWithInfo(desc, reason, detail, advice, serviceException);
    }

    /**
     * Throws Service Exception with errorInfo.<br>
     * 
     * @param desc error description
     * @param reason error reason
     * @param detail error detail
     * @param advice advice info
     * @param svcExption ServiceException
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    private static void throwSvcExptionWithInfo(String desc, String reason, String detail, String advice,
            ServiceException svcExption) throws ServiceException {
        String[] descArray = {desc};
        String[] reasonArray = {reason};
        String[] detailArray = {detail};
        String[] adviceArray = {advice};
        svcExption.setExceptionArgs(new ExceptionArgs(descArray, reasonArray, detailArray, adviceArray));
        LOGGER.error("ServieException ErrorInfo: " + new ResultRsp<>(svcExption).toString());
        throw svcExption;
    }

    /**
     * Throws Service Exception with ResultRsp.<br>
     * 
     * @param result ResultRsp object
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static void throwSvcExptionByResultRsp(ResultRsp<?> result) throws ServiceException {
        if(!StringUtils.hasLength(result.getDescArg())) {
            result.setDescArg("no error information");
        }
        LOGGER.error("ResultRsp ErrorInfo: " + result.toString());
        throw new ServiceException(result.getErrorCode(), HttpCode.ERR_FAILED, result.buidlExceptionArgs());
    }
}
