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
 * The sub object.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
@Target(value = {java.lang.annotation.ElementType.FIELD})
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface MOSubField {

    /**
     * Get the parent object uuid field.<br>
     * 
     * @return The parent object uuid field
     * @since SDNO 0.5
     */
    String parentId();

    /**
     * If not empty, read the corresponding fields of the resource to the parent object.<br>
     * 
     * @return The corresponding fields of the resource
     * @since SDNO 0.5
     */
    String attr() default "uuid";

    /**
     * If attr is not empty,this is the type of sub object.<br>
     * 
     * @return The type of sub object
     * @since SDNO 0.5
     */
    Class<?> type() default Object.class;
}
