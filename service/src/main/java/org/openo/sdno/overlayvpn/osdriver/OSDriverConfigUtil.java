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

package org.openo.sdno.overlayvpn.osdriver;

import java.util.Arrays;
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.esr.invdao.VimInvDao;
import org.openo.sdno.overlayvpn.esr.model.Vim;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.OperStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility Class of OSDriver Configuration.<br>
 * 
 * @author
 * @version SDNO 0.5 August 9, 2016
 */
public class OSDriverConfigUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(OSDriverConfigUtil.class);

    private static final String OS_DRIVER_NE_NAME = "OSDriverNE";

    private static final String OS_DRIVER_NE_NATIVEID = "OSDriverNEID";

    private static OSDriverConfig osDriverConfig = new OSDriverConfig();

    private static NetworkElementInvDao neInvDao = new NetworkElementInvDao();

    private static VimInvDao vimInvDao = new VimInvDao();

    private OSDriverConfigUtil() {
    }

    /**
     * Load OS Driver Data From Property file.<br>
     * 
     * @throws ServiceException throws when load failed
     * @since SDNO 0.5
     */
    public static void loadOSDriverConfigData() throws ServiceException {

        NetworkElementInvDao neInvDao = new NetworkElementInvDao();
        List<NetworkElementMO> neList = neInvDao.getNeByName(OS_DRIVER_NE_NAME);
        if(neList.isEmpty()) {
            addNeAndOSController();
        } else {
            NetworkElementMO fullNetworkElement = neInvDao.query(neList.get(0).getId());
            updateNeAndOSController(fullNetworkElement);
        }
    }

    /**
     * Query OS Driver NetworkElement Id.<br>
     * 
     * @return OS Driver NetworkElement Id
     * @throws ServiceException throws query failed
     * @since SDNO 0.5
     */
    public static String getOSDriverNeId() throws ServiceException {
        NetworkElementInvDao neInvDao = new NetworkElementInvDao();
        List<NetworkElementMO> neList = neInvDao.getNeByName(OS_DRIVER_NE_NAME);
        return neList.get(0).getId();
    }

    /**
     * Query OS Controller Id.<br>
     * 
     * @return Controller Id queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static String getOSControllerId() throws ServiceException {
        NetworkElementInvDao neInvDao = new NetworkElementInvDao();
        NetworkElementMO neMo = neInvDao.query(getOSDriverNeId());

        return neMo.getControllerID().get(0);
    }

    private static void addNeAndOSController() throws ServiceException {

        Vim vim = vimInvDao.queryVimByName(osDriverConfig.getVimName());
        if(null == vim) {
            LOGGER.error("vim is null");
            throw new ServiceException("vim is null");
        }

        NetworkElementMO networkElement = new NetworkElementMO();
        networkElement.setName(OS_DRIVER_NE_NAME);
        networkElement.setControllerID(Arrays.asList(vim.getVimId()));
        networkElement.setNativeID(OS_DRIVER_NE_NATIVEID);
        networkElement.setAdminState(AdminStatus.ACTIVE.getName());
        networkElement.setOperState(OperStatus.UP.getName());

        neInvDao.addMO(networkElement);
    }

    private static void updateNeAndOSController(NetworkElementMO networkElement) throws ServiceException {

        Vim vim = vimInvDao.queryVimByName(osDriverConfig.getVimName());
        if(null == vim) {
            LOGGER.error("Vim is null");
            throw new ServiceException("Vim is null");
        }

        networkElement.setControllerID(Arrays.asList(vim.getVimId()));

        neInvDao.updateMO(networkElement);
    }

}
