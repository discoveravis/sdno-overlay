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

package org.openo.sdno.overlayvpn.consts;

/**
 * Parameter validation Constant Class.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
public class ValidationConsts {

    /**
     * IP regular expression
     */
    public static final String IP_REGEX =
            "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    /**
     * IP Mask regular expression
     */
    public static final String IP_MASK_REGEX =
            "^([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}/([1-9]|[1-2]\\d|3[0-2])$";

    /**
     * Integer regular expression
     */
    public static final String INT_REGEX = "[0-9]*";

    /**
     * Integer range regular expression
     */
    public static final String INT_RANGE_REGEX = "\\d+(-\\d+)?((,\\d+)+(-\\d+)?)*";

    /**
     * Network Element ID regular expression
     */
    public static final String NEID_REGEX =
            "^((([1-9][0-9]{0,1}|1[0-9]{2}|2[0-4][0-9]|25[0-5]))-([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]))$";

    private ValidationConsts() {

    }
}
