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

package org.openo.sdno.overlayvpn.util.operation;

import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class of Operate.<br>
 * 
 * @author
 * @version SDNO 0.5 August 22, 2016
 */
public class OperationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationUtil.class);

    private OperationUtil() {

    }

    /**
     * Get update data map from JSON string.<br>
     * 
     * @param inputJsonStr Input JSON String
     * @return update data map
     * @throws ServiceException when parse failed or update data is null
     * @since SDNO 0.5
     */
    public static Map<String, String> getUpdateDataMap(String inputJsonStr) throws ServiceException {

        Map<String, String> updateDataMap = null;
        try {
            updateDataMap = JsonUtil.fromJson(inputJsonStr, new TypeReference<Map<String, String>>() {});
        } catch(IllegalArgumentException e) {
            LOGGER.error("update data formate error, inputJsonStr = " + inputJsonStr, e);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Overlay VPN", "Update data");
            return null;
        }

        if(null == updateDataMap) {
            LOGGER.error("update data formate error, updateDataMap is NULL");
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Overlay VPN", "Update data");
            return null;
        }

        return updateDataMap;
    }

}
