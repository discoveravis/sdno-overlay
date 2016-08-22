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
 * Annotation Validator Class of Integer.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public class AIntValidator implements ConstraintValidator<AInt, Object> {

    private int max = Integer.MAX_VALUE;

    private int min = Integer.MIN_VALUE;

    private boolean isRequire = false;

    /**
     * Override initialize function.<br/>
     * 
     * @param constraintAnnotation Annotation Object
     * @since SDNO 0.5
     */
    @Override
    public void initialize(AInt constraintAnnotation) {
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
        if(value instanceof Integer) {
            return isValid((Integer)value);
        } else if(value instanceof String) {
            return isValid((String)value);
        } else {
            return true;
        }
    }

    private boolean isValid(Integer intValue) {
        if(intValue > max || intValue < min) {
            return false;
        }
        return true;
    }

    private boolean isValid(String strValue) {
        if(StringUtils.hasLength(strValue)) {
            try {
                Pattern pattern = Pattern.compile(ValidationConsts.INT_REGEX);
                String newValue = Normalizer.normalize(strValue, Form.NFKC);
                Matcher isNum = pattern.matcher(newValue);
                if(!isNum.matches()) {
                    return false;
                } else if(Integer.parseInt(strValue) > max || Integer.parseInt(strValue) < min) {
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
