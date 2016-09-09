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

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.CommParamDao;
import org.openo.sdno.overlayvpn.brs.invdao.ControllerDao;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.AuthInfo;
import org.openo.sdno.overlayvpn.brs.model.CommParamMO;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Util Class of OSDriver Configuration.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
public class OSDriverConfigUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(OSDriverConfigUtil.class);

    private static final String OS_DRIVER_NE_NAME = "OSDriverNE";

    private static final String OS_DRIVER_CONTROLLER_NAME = "OSController";

    private static OSDriverConfig osDriverConfig = new OSDriverConfig();

    private static NetworkElementInvDao neInvDao = new NetworkElementInvDao();

    private static ControllerDao controllerDao = new ControllerDao();

    private static CommParamDao commParamDao = new CommParamDao();

    /**
     * Load OS Driver Data From Property file.<br>
     * 
     * @throws ServiceException throws when load failed
     * @since SDNO 0.5
     */
    public static void LoadOSDriverConfigData() throws ServiceException {

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

    private static void addNeAndOSController() throws ServiceException {

        ControllerMO controller = new ControllerMO();
        controller.setName(OS_DRIVER_CONTROLLER_NAME);
        controller.setDescription(osDriverConfig.getOSDomainName());
        String controllerId = controllerDao.addMO(controller);

        CommParamMO commParam = new CommParamMO();
        commParam.setObjectId(controllerId);
        commParam.setProtocol("https");
        commParam.setHostName(osDriverConfig.getOSIpAddress());
        commParam.setPort(osDriverConfig.getOSPort());

        AuthInfo authInfo = new AuthInfo();
        authInfo.setUserName(osDriverConfig.getOSUserName());
        authInfo.setPort(osDriverConfig.getOSPort());
        authInfo.setPassword(osDriverConfig.getOSPassword());

        commParam.setAuthInfo(authInfo);
        commParamDao.addMO(controllerId, commParam);

        NetworkElementMO networkElement = new NetworkElementMO();
        networkElement.setName(OS_DRIVER_NE_NAME);
        networkElement.setControllerID(Arrays.asList(controllerId));
        networkElement.setAdminState("active");
        networkElement.setOperState("up");

        neInvDao.addMO(networkElement);
    }

    private static void updateNeAndOSController(NetworkElementMO neMO) throws ServiceException {

        ControllerMO controller = controllerDao.getController(neMO.getControllerID().get(0));
        controller.setDescription(osDriverConfig.getOSDomainName());
        controllerDao.updateMO(controller);

        String controllerId = controller.getObjectId();
        List<CommParamMO> commParamList = commParamDao.getCommParam(controllerId);
        if(CollectionUtils.isEmpty(commParamList)) {
            LOGGER.error("CommParam not exist!!");
            throw new ServiceException("CommParam not exist");
        }

        CommParamMO commParam = commParamList.get(0);
        commParam.setHostName(osDriverConfig.getOSIpAddress());
        commParam.setPort(osDriverConfig.getOSPort());

        AuthInfo authInfo = new AuthInfo();
        authInfo.setUserName(osDriverConfig.getOSUserName());
        authInfo.setPort(osDriverConfig.getOSPort());
        authInfo.setPassword(osDriverConfig.getOSPassword());

        commParam.setAuthInfo(authInfo);
        commParamDao.updateMO(controllerId, commParam);
    }

}
