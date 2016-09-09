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

package org.openo.sdno.overlayvpn.consts;

/**
 * Definition class of HTTP code.<br>
 * 
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
public class HttpCode {

    /**
     * Server Failed Code.
     */
    public static final int ERR_FAILED = 500;

    /**
     * OK Code.
     */
    public static final int RESPOND_OK = 200;

    /**
     * CREATED OK Code.
     */
    public static final int CREATE_OK = 201;

    /**
     * Not Found Code.
     */
    public static final int NOT_FOUND = 404;

    /**
     * BAD REQUEST CODE.
     */
    public static final int BAD_REQUEST = 400;

    /**
     * EXCEED REQUEST CODE.
     */
    public static final int EXCEED_REQUEST = 413;

    private HttpCode() {
    }

    /**
     * Check whether code is success.<br>
     * 
     * @param httpCode HTTP Code
     * @return true if success HTTP code,false otherwise
     * @since SDNO 0.5
     */
    public static boolean isSucess(int httpCode) {
        return httpCode / 100 == 2;
    }
}
