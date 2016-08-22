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

package org.openo.sdno.overlayvpn.brs.invdao;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.model.CommParamMO;
import org.openo.sdno.overlayvpn.brs.rest.BrsRestconfProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Class for querying communication parameter.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-16
 */
@Service
public class CommParamDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommParamDao.class);

    private static final String COMMPARAM_URL = "/openoapi/sdnobrs/v1/commparammgmt/access-objects/{0}/commparams";

    private static final String UPDATE_COMMPARAM_URL =
            "/openoapi/sdnobrs/v1/commparammgmt/access-objects/{0}/commparams/{1}";

    /**
     * Add CommParam Object.<br/>
     * 
     * @param controllerId Controller Id
     * @param commParamMO CommParam Object need to add
     * @since SDNO 0.5
     */
    public void addMO(String controllerId, CommParamMO commParamMO) throws ServiceException {
        LOGGER.info("insert CommParam begin,cur CommParam name:" + commParamMO.getName());
        String url = MessageFormat.format(COMMPARAM_URL, controllerId);
        BrsRestconfProxy.post(url, commParamMO.toJsonBody());
        LOGGER.info("insert CommParam end");
    }

    /**
     * Update CommParam Object.<br/>
     * 
     * @param controllerId controller ID
     * @param commParamMO commParamMO CommParam Object need to update
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    public void updateMO(String controllerId, CommParamMO commParamMO) throws ServiceException {
        LOGGER.info("update CommParam begin,cur CommParam name:" + commParamMO.getName());
        StringBuilder curID = new StringBuilder(commParamMO.getId());
        commParamMO.setId(null);
        String url = MessageFormat.format(UPDATE_COMMPARAM_URL, controllerId, curID.toString());
        BrsRestconfProxy.put(url, commParamMO.toJsonBody());
        LOGGER.info("update CommParam end");
    }

    /**
     * Get communication parameter.<br/>
     * 
     * @param controllerID Controller Id
     * @return Collection of communication parameters.
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public List<CommParamMO> getCommParam(String controllerID) throws ServiceException {
        String url = MessageFormat.format(COMMPARAM_URL, controllerID);
        RestfulResponse response = BrsRestconfProxy.get(url, null);
        if(!HttpCode.isSucess(response.getStatus()) || StringUtils.isEmpty(response.getResponseContent())) {
            LOGGER.error("Query Commparam failed!!");
            throw new ServiceException("Query Commparam failed!!");
        }

        CommParamMO[] moArray = JsonUtil.fromJson(response.getResponseContent(), CommParamMO[].class);

        if(null == moArray) {
            return new ArrayList<CommParamMO>();
        }

        return Arrays.asList(moArray);
    }

}
