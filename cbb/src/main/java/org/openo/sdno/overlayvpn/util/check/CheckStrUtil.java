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

package org.openo.sdno.overlayvpn.util.check;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.consts.ValidationConsts;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.openo.sdno.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Class of String Data Validation.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public class CheckStrUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckStrUtil.class);

    private static final int QUERY_TUNNEL_MAX_LENGTH = 1000;

    private static final String PARAMETER_INVALID_DESC = ResourceUtil.getMessage("parameter.invalid.desc");

    private static final String PARAMETER_INVALID_UUID = ResourceUtil.getMessage("parameter.invalid.uuid");

    private static final String PARAMETER_INVALID_UUID_REASON =
            ResourceUtil.getMessage("parameter.invalid.uuid.reason");

    private static final String PARAMETER_INVALID_ADVICE = ResourceUtil.getMessage("parameter.invalid.advice");

    private static final String PARAMETER_INVALID_UUID_NOT_SAME_REASON =
            ResourceUtil.getMessage("parameter.invalid.uuid_not_same.reason");

    private static final String PARAMETER_INVALID_RANGE = ResourceUtil.getMessage("parameter.invalid.range");

    private static final String PARAMETER_INVALID_RANGE_REASON =
            ResourceUtil.getMessage("parameter.invalid.range.reason");

    private static final String PARAMETER_INVALID_IPSEGMENT = ResourceUtil.getMessage("parameter.invalid.ipsegment");

    private static final String PARAMETER_INVALID_IPSEGMENT_REASON =
            ResourceUtil.getMessage("parameter.invalid.ipsegment.reason");

    private static final String PARAMETER_INVALID_BANDWIDTH = ResourceUtil.getMessage("parameter.invalid.bandwidth");

    private static final String PARAMETER_INVALID_BANDWIDTH_REASON =
            ResourceUtil.getMessage("parameter.invalid.bandwidth.reason");

    private static final String PARAMETER_INVALID_STATICROUTE =
            ResourceUtil.getMessage("parameter.invalid.staticroute");

    private static final String PARAMETER_INVALID_STATICROUTE_REASON =
            ResourceUtil.getMessage("parameter.invalid.staticroute.reason");

    private static final String PARAMETER_INVALID_BGPEER = ResourceUtil.getMessage("parameter.invalid.bgpeer");

    private static final String PARAMETER_INVALID_BGPEER_REASON =
            ResourceUtil.getMessage("parameter.invalid.bgpeer.reason");

    private CheckStrUtil() {
    }

    /**
     * Validate UUid Data.<br>
     * 
     * @param uuid data need to validate
     * @throws ServiceException when ServiceException occurs
     * @since SDNO 0.5
     */
    public static void checkUuidStr(String uuid) throws ServiceException {
        if(UuidUtil.validate(uuid)) {
            return;
        }

        LOGGER.error("uuid invalid. uuids = " + uuid);

        // construct error message
        String desc = PARAMETER_INVALID_DESC;
        String message = PARAMETER_INVALID_UUID + uuid + " " + PARAMETER_INVALID_UUID_REASON;
        String advice = PARAMETER_INVALID_ADVICE;
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                advice);
    }

    /**
     * Validate list of UUid Data.<br>
     * 
     * @param uuidList list of UUid need to validate
     * @throws ServiceException when ServiceException occurs
     * @since SDNO 0.5
     */
    public static void checkUuidStr(List<String> uuidList) throws ServiceException {

        String desc = PARAMETER_INVALID_DESC;
        String message = PARAMETER_INVALID_UUID + " " + PARAMETER_INVALID_UUID_REASON;
        String advice = PARAMETER_INVALID_ADVICE;

        if(null == uuidList) {
            LOGGER.error("uuidList invalid. uuidList is null.");
            SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                    advice);
            return;
        }

        if(uuidList.size() > QUERY_TUNNEL_MAX_LENGTH) {
            LOGGER.error("uuidList invalid. uuidList size too long. size:" + uuidList.size());
            SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                    advice);
        }

        for(String uuid : uuidList) {
            if(!UuidUtil.validate(uuid)) {
                LOGGER.error("uuidList invalid. uuidList = " + uuidList);
                SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message,
                        message, advice);
            }
        }
    }

    /**
     * Check Whether the two UUids is the same.<br>
     * 
     * @param uuid1 first UUID Object
     * @param uuid2 second UUID Object
     * @throws ServiceException when ServiceException occurs
     * @since SDNO 0.5
     */
    public static void checkIsSameUuid(String uuid1, String uuid2) throws ServiceException {
        // first check UUID is valid
        checkUuidStr(uuid1);
        checkUuidStr(uuid2);

        // then check if they are the same
        if(!uuid1.equals(uuid2)) {
            LOGGER.error("uuids is not same. uuid = " + uuid1 + ", uuid2 = " + uuid2);
            // construct error message
            String desc = PARAMETER_INVALID_DESC;
            String message = PARAMETER_INVALID_UUID + uuid1 + ", " + PARAMETER_INVALID_UUID + uuid2
                    + PARAMETER_INVALID_UUID_NOT_SAME_REASON;
            String advice = PARAMETER_INVALID_ADVICE;
            SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                    advice);
        }
    }

    /**
     * Validate Integer Range.<br>
     * 
     * @param range Integer Range
     * @throws ServiceException when ServiceException occurs
     * @since SDNO 0.5
     */
    public static void checkIntRangeFormat(String range) throws ServiceException {
        if(!StringUtils.hasLength(range)) {
            return;
        }
        if(!range.matches(ValidationConsts.INT_RANGE_REGEX)) {
            LOGGER.error("range invalid. range = " + range);
            // construct error message
            String desc = PARAMETER_INVALID_DESC;
            String message = PARAMETER_INVALID_RANGE + range + " " + PARAMETER_INVALID_RANGE_REASON;
            String advice = PARAMETER_INVALID_ADVICE;
            SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                    advice);
        }
    }

    /**
     * Normalize InputString and Check whether String matches regEx.<br>
     * 
     * @param inputStr input String
     * @param regEx regExpression
     * @return true if String matches,false otherwise
     * @since SDNO 0.5
     */
    public static boolean validateNormalizedString(String inputStr, String regEx) {
        if((null == inputStr) || (null == regEx)) {
            return true;
        }

        Pattern pattern = Pattern.compile(regEx);
        String normalizedString = Normalizer.normalize(inputStr, Form.NFKC);
        Matcher matcher = pattern.matcher(normalizedString);
        if(matcher.matches()) {
            return true;
        }

        return false;
    }
}
