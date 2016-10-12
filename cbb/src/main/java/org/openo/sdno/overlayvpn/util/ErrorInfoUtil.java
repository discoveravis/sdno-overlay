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

package org.openo.sdno.overlayvpn.util;

import java.lang.reflect.Field;

import org.openo.sdno.framework.container.util.SysEnvironment;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.verify.annotation.AErrorInfo;
import org.openo.sdno.resource.ResourceUtil;
import org.springframework.util.StringUtils;

/**
 * Class of ErrorInfo operation.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 2, 2016
 */
public class ErrorInfoUtil {

    private static final String DCI_PARAM_ERROR = ResourceUtil.getMessage("dci.param.error");

    private ErrorInfoUtil() {

    }

    /**
     * Convert Error Message.<br>
     * 
     * @param className class name
     * @param errorInfo error Info
     * @return Error Message converted
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public static String convertErrorMessage(Class className, String[] errorInfo) {

        String result = DCI_PARAM_ERROR;

        String firstErrorInfo = checkErrorInfoParam(errorInfo);
        if(null == firstErrorInfo) {
            return result;
        }

        String[] propertyInfos = firstErrorInfo.split(", ");
        String[] propertys = propertyInfos[0].split(": ");

        Field[] fields = className.getDeclaredFields();

        // only get the first error info
        for(int i = 0; i < fields.length; i++) {
            if(fields[i].getName().equals(propertys[0])) {
                AErrorInfo errorInfos = fields[i].getAnnotation(AErrorInfo.class);

                if(null == errorInfos) {
                    return result;
                }

                String info = errorInfos.info();

                if(!StringUtils.hasLength(info)) {
                    return result;
                }

                if(CommConst.LANGUAGE_ZH_CN.equals(SysEnvironment.getLanguage())) {
                    return ResourceUtil.getMessage(info) + DCI_PARAM_ERROR;
                }

                return ResourceUtil.getMessage(info) + " " + DCI_PARAM_ERROR;
            }
        }

        return result;
    }

    private static String checkErrorInfoParam(String[] errorInfo) {

        if(errorInfo.length == 0) {
            return null;
        }

        String[] propertyInfos = errorInfo[0].split(", ");
        if(propertyInfos.length == 0) {
            return null;
        }

        String[] propertys = propertyInfos[0].split(": ");
        if(propertys.length == 0) {
            return null;
        }

        return errorInfo[0];
    }
}
