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

package org.openo.sdno.overlayvpn.inventory.sdk.model.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * It is used to define resource name in PUER about current MO. <br>
 * <p>
 * The resource name in MO must be the same as in model file of PUER. for example: MO define is:
 * 
 * @MOResType(infoModelName = "overlayvpn_tenant_ipsecpolicy")
 *                          public class IpSecPolicy extends SecurityPolicy{
 *                          }
 *                          PUER model file define is:
 *                          <datamodel name="overlayvpn_tenant_ipsecpolicy">
 *                          </p>
 * @author
 * @version SDNO 0.5 2016-6-3
 */
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
public @interface MOResType {

    /**
     * Get InfoModel name.<br>
     * 
     * @return InfoModel name
     * @since SDNO 0.5
     */
    String infoModelName();
}
