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

package org.openo.sdno.overlayvpn.verify.annotation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

/**
 * Annotation Validator Class of String Object.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public class AStringValidator implements ConstraintValidator<AString, Object> {

    private int max = 255;

    private int min = 0;

    private boolean isRequire = false;

    private String forbidenChar = "";

    /**
     * 
     */
    private Set<String> scopeList = new HashSet<String>();

    /**
     * Override initialize function.<br>
     * 
     * @param constraintAnnotation Annotation Object
     * @since SDNO 0.5
     */
    @Override
    public void initialize(AString constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        isRequire = constraintAnnotation.require();
        forbidenChar = constraintAnnotation.forbiddenChar();
        if(StringUtils.hasLength(constraintAnnotation.scope())) {
            scopeList.addAll(Arrays.asList(constraintAnnotation.scope().split(",")));
        }
    }

    /**
     * Override isValid function.<br>
     * 
     * @param value Object need to validate
     * @param context ConstraintValidatorContext Object
     * @return true if Object is valid, false otherwise
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value == null) {
            return !isRequire;
        } else if(value instanceof Integer) {
            return isValid((Integer)value);
        } else if(value instanceof List) {
            return isValid((List)value);
        } else if(value instanceof String) {
            return isValid((String)value);
        } else {
            return true;
        }
    }

    @SuppressWarnings("rawtypes")
    private boolean isValid(List values) {
        if(values != null) {
            for(Object value : values) {
                if(value instanceof Integer && !isValid((Integer)value)) {
                    return false;
                } else if(value instanceof String && !isValid((String)value)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(Integer value) {
        if(value != null && !scopeList.isEmpty() && !scopeList.contains(String.valueOf(value))) {
            return false;
        }
        return true;
    }

    private boolean isValid(String value) {
        if(StringUtils.hasLength(value)) {
            if(!scopeList.isEmpty() && !scopeList.contains(value)) {
                return false;
            }
            // Verify Forbidden char
            if(!verifyForbiddenchar(value)) {
                return false;
            }
            // if scopeList not setting, the length need to verify
            if((value.length() > max) || (value.length() < min)) {
                return false;
            }
        } else if(isRequire) {
            return false;
        }
        return true;
    }

    private boolean verifyForbiddenchar(final String value) {
        // if forbidenChar is setting, value should not contain this string
        if((forbidenChar != null) && (!forbidenChar.isEmpty())) {
            final String[] strs = forbidenChar.split(",");
            for(String achar : strs) {
                if(value.contains(achar)) {
                    return false;
                }
            }
        }
        return true;
    }

}
