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

package org.openo.sdno.overlayvpn.inventory.sdk.util;

import java.lang.reflect.Field;
import java.util.Locale;

import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class of MOModelProcessor.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-8-22
 */
public class MOModelProcessorUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MOModelProcessorUtil.class);

    private MOModelProcessorUtil() {

    }

    /**
     * Get Field Value by name.<br>
     * 
     * @param obj Object data
     * @param field Field data
     * @return name of that field
     * @throws InnerErrorServiceException
     * @since SDNO 0.5
     */
    public static String getFieldValueByName(Object obj, Field field) throws InnerErrorServiceException {
        Object fieldValueByName = JavaEntityUtil.getFieldValueByName(field.getName(), obj);
        if(fieldValueByName != null) {
            return fieldValueByName.toString();
        } else {
            LOGGER.error("getFieldValueByName fieldValueByName is null,return null.");
            return null;
        }
    }

    /**
     * Get Resource Type.<br>
     * 
     * @param moType Resource Type class
     * @return resource type
     * @since SDNO 0.5
     */
    public static String buildRestType(Class moType) {
        String resttype = moType.getName().replaceAll("\\.", "_");
        return resttype.toLowerCase(Locale.getDefault());
    }
}
