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

package org.openo.sdno.overlayvpn.brs.invdao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.brs.rest.BrsRestconfProxy;
import org.openo.sdno.overlayvpn.consts.HttpCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Controller data DAO class.This class can be used to access Controller Data.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-4
 */
@Service
public class ControllerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerDao.class);

    private static final String CONTROLLER_URL = "/openoapi/sdnobrs/v1/controller";

    /**
     * Add Controller Object.<br>
     * 
     * @param controllerMO ControllerMO Object need to add
     * @return Controller id
     * @throws ServiceException throws when operate failed
     * @since SDNO 0.5
     */
    public String addMO(ControllerMO controllerMO) throws ServiceException {
        LOGGER.info("Insert Controller begin,cur NE name:" + controllerMO.getName());
        RestfulResponse response = BrsRestconfProxy.post(CONTROLLER_URL, controllerMO.toJsonBody());
        if(!HttpCode.isSucess(response.getStatus()) || StringUtils.isEmpty(response.getResponseContent())) {
            LOGGER.error("Add Controller Failed!!");
            throw new ServiceException("Add Controller Failed!!");
        }
        LOGGER.info("Insert Controller end");
        return response.getResponseContent();
    }

    /**
     * Update Controller Object<br>
     * 
     * @param controllerMO ControllerMO Object need to update
     * @throws ServiceException throws when operate failed
     * @since SDNO 0.5
     */
    public void updateMO(ControllerMO controllerMO) throws ServiceException {
        StringBuilder curID = new StringBuilder(controllerMO.getId());
        controllerMO.setId(null);
        BrsRestconfProxy.put(CONTROLLER_URL + "/" + curID.toString(), controllerMO.toJsonBody());
    }

    /**
     * Query controller by UUID.<br>
     * 
     * @param uuid id of controller
     * @return controller Object query out
     * @throws ServiceException when ServiceException occurs
     * @since SDNO 0.5
     */
    public ControllerMO getController(String uuid) throws ServiceException {

        if(StringUtils.isEmpty(uuid)) {
            LOGGER.error("Get controller: uuid is invalid param!!");
            throw new ServiceException("uuid is invalid param");
        }

        RestfulResponse response = BrsRestconfProxy.get(CONTROLLER_URL + "/" + uuid, "");

        if(!HttpCode.isSucess(response.getStatus()) || StringUtils.isEmpty(response.getResponseContent())) {
            LOGGER.error("Get controller: response return error!!");
            throw new ServiceException("getController: response return error");
        }

        return JsonUtil.fromJson(response.getResponseContent(), ControllerMO.class);
    }

    /**
     * Query controller by networkElement ID.<br>
     * 
     * @param neId networkElement id
     * @return controller Object query out
     * @throws ServiceException when ServiceException occurs
     * @since SDNO 0.5
     */
    public List<ControllerMO> getControllerByNeId(String neId) throws ServiceException {
        LOGGER.info("Query controller by ne id: " + neId);
        List<ControllerMO> controllerMolist = new ArrayList<ControllerMO>();

        NetworkElementInvDao neInvDao = new NetworkElementInvDao();
        NetworkElementMO neMO = neInvDao.query(neId);
        List<String> controllerIdList = neMO.getControllerID();
        for(String controllerId : controllerIdList) {
            ControllerMO controller = new ControllerMO();
            controller.setId(controllerId);
            controller.setObjectId(controllerId);
            controllerMolist.add(controller);
        }

        return controllerMolist;
    }

    /**
     * Query controller by site ID.<br>
     * 
     * @param siteId site ID
     * @return controller Object query out
     * @throws ServiceExceptionwhen ServiceException occurs
     * @since SDNO 0.5
     */
    public List<ControllerMO> getControllerBySiteId(String siteId) throws ServiceException {
        LOGGER.info("Query controller by site id: " + siteId);
        NetworkElementInvDao neDao = new NetworkElementInvDao();

        List<NetworkElementMO> nes = neDao.getNeBySiteId(siteId);

        List<ControllerMO> controllers = new ArrayList<ControllerMO>();

        if(CollectionUtils.isNotEmpty(nes)) {
            for(NetworkElementMO ne : nes) {
                controllers.addAll(getControllerByNeId(ne.getId()));
            }
        }

        return controllers;
    }
}
