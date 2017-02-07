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

package org.openo.sdno.overlayvpn.inventory.sdk.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The json field and the type is string.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
@Target(value = {java.lang.annotation.ElementType.FIELD})
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface MOJsonField {

    /**
     * The field name in DB,it is same if not set.<br>
     * 
     * @return The field name in DB
     * @since SDNO 0.5
     */
    String invName() default "";

    /**
     * The field type in DB,it is string if not set.<br>
     * 
     * @return The field type in DB
     * @since SDNO 0.5
     */
    Class<?> type() default String.class;
}
