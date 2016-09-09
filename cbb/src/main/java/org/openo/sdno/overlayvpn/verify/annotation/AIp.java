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

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation Validator Interface of Ipaddress.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
@Target(value = {java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD,
                java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR,
                java.lang.annotation.ElementType.PARAMETER})
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = AIPValidator.class)
public @interface AIp {

    /**
     * Get message of the attribute.<br>
     * 
     * @return message of the attribute
     * @since SDNO 0.5
     */
    String message() default "ip is invalid";

    /**
     * Get groups of the attribute.<br>
     * 
     * @return groups of the attribute
     * @since SDNO 0.5
     */
    Class<?>[] groups() default {};

    /**
     * Get payload of the attribute.<br>
     * 
     * @return payload of the attribute
     * @since SDNO 0.5
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Require the attribute or not.<br>
     * 
     * @return true if the attribute is required
     * @since SDNO 0.5
     */
    public boolean require() default false;
}
