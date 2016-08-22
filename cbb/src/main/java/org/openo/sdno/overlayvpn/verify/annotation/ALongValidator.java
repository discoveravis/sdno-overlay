/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.verify.annotation;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.openo.sdno.overlayvpn.consts.ValidationConsts;
import org.springframework.util.StringUtils;

/**
 * Annotation Validator Class of Long Object.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public class ALongValidator implements ConstraintValidator<ALong, Object> {

    private long max = Long.MAX_VALUE;

    private long min = Long.MIN_VALUE;

    private boolean isRequire = false;

    /**
     * Override initialize function.<br/>
     * 
     * @param constraintAnnotation Annotation Object
     * @since SDNO 0.5
     */
    @Override
    public void initialize(ALong constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        isRequire = constraintAnnotation.require();
    }

    /**
     * Override isValid function.<br/>
     * 
     * @param value Object need to validate
     * @param context ConstraintValidatorContext Object
     * @return true if Object is valid, false otherwise
     * @since SDNO 0.5
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value instanceof Long) {
            return isValid((Long)value);
        } else if(value instanceof String) {
            return isValid((String)value);
        } else {
            return true;
        }
    }

    private boolean isValid(Long value) {
        if(value > max || value < min) {
            return false;
        }
        return true;
    }

    private boolean isValid(String value) {
        if(StringUtils.hasLength(value)) {
            try {
                Pattern pattern = Pattern.compile(ValidationConsts.INT_REGEX);
                String newValue = Normalizer.normalize(value, Form.NFKC);
                Matcher isNum = pattern.matcher(newValue);
                if(!isNum.matches()) {
                    return false;
                } else if(Long.parseLong(value) > max || Long.parseLong(value) < min) {
                    return false;
                }
            } catch(NumberFormatException e) {
                return false;
            }
        } else if(isRequire) {
            return false;
        }
        return true;
    }
}
