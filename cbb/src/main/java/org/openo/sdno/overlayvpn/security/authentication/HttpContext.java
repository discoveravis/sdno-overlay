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

package org.openo.sdno.overlayvpn.security.authentication;

/**
 * Class of HttpContext.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public final class HttpContext {

    /**
     * Header of Content Type
     */
    public static final String CONTENT_TYPE_HEADER = "Content-Type";

    /**
     * MediaType of JSON
     */
    public static final String MEDIA_TYPE_JSON = "application/json;charset=UTF-8";

    /**
     * Tag of Tenant id
     */
    public static final String X_TENANT_ID = "X-Tenant-Id";

    /**
     * Tag of Request Token
     */
    public static final String X_AUTH_TOKEN = "X-Auth-Token";

    /**
     * Tag of Response Token
     */
    public static final String X_SUBJECT_TOKEN = "X-Subject-Token";

    private HttpContext() {
    }

    /**
     * Check weather HttpCode is success..<br/>
     * 
     * @param statusCode Status Code
     * @return true if statusCode is success, false otherwise.
     * @since SDNO 0.5
     */
    public static boolean isHttpStatusOk(int statusCode) {
        return statusCode / 100 == 2;
    }
}
