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

package org.openo.sdno.overlayvpn.util.check;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.util.check.ValidationUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;

/**
 * OverlayVpn Data Verify Class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 05-May-2016
 */
public class CheckOverlayVpnUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckOverlayVpnUtil.class);

    private CheckOverlayVpnUtil() {
    }

    /**
     * Check weather OverlayVpn data is valid.<br>
     * 
     * @param overlayVpn OverlayVpn data need to check
     * @throws ServiceException ServiceException throws when data is invaid
     * @since SDNO 0.5
     */
    public static void check(OverlayVpn overlayVpn) throws ServiceException {
        // Check Model Data
        checkModelData(overlayVpn);

        // Verify connection id
        if(!CollectionUtils.isEmpty(overlayVpn.getConnectionIds())) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Connection UUIDs", "Not Null");
        }

        // Verify connections
        if(!CollectionUtils.isEmpty(overlayVpn.getVpnConnections())) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("Connection", "Not Null");
        }
    }

    private static void checkModelData(OverlayVpn overlayVpn) throws ServiceException {

        ValidationUtil.validateModel(overlayVpn);

        LOGGER.info("Check overlayvpn model OK, name = " + overlayVpn.getName());
    }
}
