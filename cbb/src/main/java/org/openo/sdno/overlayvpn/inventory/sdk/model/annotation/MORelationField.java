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

package org.openo.sdno.overlayvpn.inventory.sdk.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * It means the attribute is relative field. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
@Target(value = {java.lang.annotation.ElementType.FIELD})
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface MORelationField {

    /**
     * Get Relation type.<br/>
     * 
     * @return relation type:aggregation、composition、association
     * @since SDNO 0.5
     */
    String type() default "composition";

    /**
     * Get Service type.<br/>
     * 
     * @return Service type
     * @since SDNO 0.5
     */
    String serviceType() default "";

}
