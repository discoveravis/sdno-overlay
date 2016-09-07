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

package org.openo.sdno.overlayvpn.util.check;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gateway Data Verify Class.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 05-May-2016
 */
public class CheckGatewayUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckOverlayVpnUtil.class);

    private CheckGatewayUtil() {
    }

    /**
     * Check weather Gateway data is valid.<br/>
     * 
     * @param gateway Gateway need to verify
     * @throws ServiceException ServiceException throws when data is invalid
     * @since SDNO 0.5
     */
    public static void check(Gateway gateway) throws ServiceException {
        // Check the model data
        //checkModelData(gateway);

        // Check whether resource exists in database
        checkResourceInGateway(gateway);
    }

    private static void checkModelData(Gateway gateway) throws ServiceException {

        ValidationUtil.validateModel(gateway);

        LOGGER.info("Check gateway model OK, name = " + gateway.getName());
    }

    /**
     * Check weather resource of Gateway is valid.<br/>
     * 
     * @param gateway Gateway data need to verify
     * @throws ServiceException ServiceException throws when resource exist
     * @since SDNO 0.5
     */
    private static void checkResourceInGateway(Gateway gateway) throws ServiceException {
        NetworkElementInvDao neDao = new NetworkElementInvDao();
        NetworkElementMO ne = neDao.query(gateway.getNeId());
        if(null == ne) {
            LOGGER.error("ne not exist: " + gateway.getNeId());
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("NetworkElement", gateway.getNeId());
        }
    }

}
