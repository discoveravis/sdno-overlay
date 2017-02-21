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

package org.openo.sdno.overlayvpn.frame.dao.constant;

/**
 * The constant class for mss service proxy.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
public class MssConsts {

    /**
     * The max page size.
     */
    public static final Integer MAX_PAGE_SIZE = 1000;

    /**
     * The batch operation url.
     */
    public static final String URL_BATCH = "/openoapi/sdnomss/v1/buckets/%s/resources/%s/objects";

    /**
     * The single operation url.
     */
    public static final String URL_SINGLE = "/openoapi/sdnomss/v1/buckets/%s/resources/%s/objects/%s";

    /**
     * The object
     */
    public static final String KEY_OBJECT = "object";

    /**
     * The objects
     */
    public static final String KEY_OBJECTS = "objects";

    /**
     * The objects
     */
    public static final String SORT = "sort";

    /**
     * The objects
     */
    public static final String PAGE_NUMBER = "pagenum";

    /**
     * The objects
     */
    public static final String PAGE_SIZE = "pagesize";

    /**
     * The basic type array.
     */
    public static final Class<?>[] BASIC_TYPES = {Boolean.class, Byte.class, Character.class, Double.class, Float.class,
                    Integer.class, Long.class, Short.class, String.class};

    private MssConsts() {

    }
}
