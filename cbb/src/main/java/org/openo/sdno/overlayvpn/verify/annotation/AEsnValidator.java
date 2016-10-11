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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

/**
 * Annotation Validator Class of ESN.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 2, 2016
 */
public class AEsnValidator implements ConstraintValidator<AEsn, String> {

    /**
     * Pattern regular expression
     */
    private static final String PATTERN = "[A-Z0-9]{20}";

    private boolean isRequired;

    /**
     * Override initialize function.<br>
     * 
     * @param constraintAnnotation Annotation Object
     * @since SDNO 0.5
     */
    @Override
    public void initialize(AEsn constraintAnnotation) {
        this.isRequired = constraintAnnotation.require();
    }

    /**
     * Override isValid function.<br>
     * 
     * @param value Object need to validate
     * @param context ConstraintValidatorContext Object
     * @return true if Object is valid, false otherwise
     * @since SDNO 0.5
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.hasLength(value)) {
            Pattern pattern = Pattern.compile(PATTERN);
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return !isRequired;
    }
}
