/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.util.check;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of UUID Validate Operation.<br>
 * 
 * @author
 * @version SDNO 0.5 June 2, 2016
 */
public class UuidUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(UuidUtil.class);

    private UuidUtil() {
    }

    /**
     * Validate UUID Object.<br>
     * 
     * @param uuid UUID need to validate
     * @return true if UUID is valid, false otherwise
     * @since SDNO 0.5
     */
    public static boolean validate(String uuid) {
        try {
            UuidUtils.checkUuid(uuid);
        } catch(ServiceException e) {
            LOGGER.error("Check uuid failed ", e);
            return false;
        }

        return true;
    }

    /**
     * Validate UUID List Object.<br>
     * 
     * @param uuidList UUID list need to validate
     * @return true if all UUIDs are valid, false otherwise
     * @since SDNO 0.5
     */
    public static boolean validate(List<String> uuidList) {
        if(CollectionUtils.isEmpty(uuidList)) {
            return true;
        }

        for(String uuid : uuidList) {
            if(!validate(uuid)) {
                return false;
            }
        }

        return true;
    }

}
