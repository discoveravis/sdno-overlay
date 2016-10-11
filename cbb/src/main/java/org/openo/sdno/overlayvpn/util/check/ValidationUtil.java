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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.openo.sdno.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of Validation Operation.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 2, 2016
 */
public class ValidationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtil.class);

    private static final String VALIDATE_MESSAGE_DELIMITER = ": ";

    private ValidationUtil() {
    }

    /**
     * Validate Object Data.<br>
     * 
     * @param obj Object need to validate
     * @throws ServiceException if Object is not valid
     * @since SDNO 0.5
     */
    public static void validateModel(Object obj) throws ServiceException {
        if(obj == null) {
            LOGGER.error("input para of validator model is null");
            SvcExcptUtil.throwBadRequestException("validator model is null.");
        }

        // result of validation
        String result = "";
        if(obj instanceof List<?>) {
            for(Object o : (List<?>)obj) {
                result = validateObject(o);
                if(result.length() > 0) {
                    break;
                }
            }
        } else {
            result = validateObject(obj);
        }

        if(!result.isEmpty()) {
            LOGGER.error("Validation Error:" + result);
            String desc = ResourceUtil.getMessage("cloudvpn.model.parameter.invalid.desc");
            String reason = result;
            String detail = result;
            String advice = ResourceUtil.getMessage("cloudvpn.model.parameter.invalid.advice");
            SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, reason, detail,
                    advice);
        }
    }

    /**
     * Validate Object Data.<br>
     * 
     * @param obj Object need to validate
     * @return empty String if Object is valid
     * @since SDNO 0.5
     */
    private static String validateObject(Object obj) {
        StringBuffer buffer = new StringBuffer();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
        Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();
        ConstraintViolation<Object> objIterator = null;
        while(iter.hasNext()) {
            objIterator = iter.next();
            buffer.append(objIterator.getPropertyPath().toString()).append(VALIDATE_MESSAGE_DELIMITER)
                    .append(objIterator.getMessage());
            if(iter.hasNext()) {
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }
}
