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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.baseservice.roa.util.restclient.RestfulParametes;

/**
 * Class of token data holder.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 3, 2016
 */
public class TokenDataHolder {

    private static final String REQUEST_URI = "X-HTTP-URI";

    private static final String REQUEST_METHOD = "X-HTTP-METHOD";

    /**
     * Token Holder Object
     */
    private static final ThreadLocal<Map<String, Object>> tokenHolder = new ThreadLocal<Map<String, Object>>() {

        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };

    private TokenDataHolder() {
    }

    /**
     * Add token to HttpRequest.<br>
     * 
     * @param restfulParametes Add token to HttpRequest
     * @since SDNO 0.5
     */
    public static void addToken2HttpRequest(RestfulParametes restfulParametes) {
        String token = getToken();
        restfulParametes.putHttpContextHeader(HttpContext.X_AUTH_TOKEN, token);
    }

    /**
     * Get token data.<br>
     * 
     * @return token data.
     * @since SDNO 0.5
     */
    public static String getToken() {
        return "abcd-efgh-ijk";
    }

    /**
     * Get request URI.<br>
     * 
     * @return request URI
     * @since SDNO 0.5
     */
    public static String getRequestURI() {
        return (String)tokenHolder.get().get(REQUEST_URI);
    }

    /**
     * Get request method..<br>
     * 
     * @return request method
     * @since SDNO 0.5
     */
    public static String getRequestMethod() {
        return (String)tokenHolder.get().get(REQUEST_METHOD);
    }

    /**
     * Get user name.<br>
     * 
     * @return user name
     * @since SDNO 0.5
     */
    public static String getUserName() {
        return null;
    }

    /**
     * Get tenant id.<br>
     * 
     * @return tenant id
     * @since SDNO 0.5
     */
    public static String getTenantID() {
        return "abc-def-0123-456";
    }

    /**
     * Get tenant name.<br>
     * 
     * @return tenant name
     * @since SDNO 0.5
     */
    public static String getTenantName() {
        return null;
    }

    /**
     * Get Role List.<br>
     * 
     * @return role list
     * @since SDNO 0.5
     */
    public static List<?> getRoleList() {
        return null;
    }
}
