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

package org.openo.sdno.overlayvpn.security.authentication;

import org.openo.baseservice.roa.util.restclient.RestfulParametes;

/**
 * Class of Token Placer.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public final class TokenPlacer {

    private TokenPlacer() {
    }

    /**
     * Add Token to HTTP Header.<br>
     * 
     * @param restfulParameters Restful Parameters
     * @since SDNO 0.5
     */
    public static void addTenantToken2HttpHeader(RestfulParametes restfulParameters) {
        if(null != restfulParameters) {
            String token = getTenantToken();
            restfulParameters.putHttpContextHeader(HttpContext.X_AUTH_TOKEN, token);
        }
    }

    /**
     * Get token from Tenant.<br>
     * 
     * @return token data
     * @since SDNO 0.5
     */
    public static String getTenantToken() {
        return TokenDataHolder.getToken();
    }

}
