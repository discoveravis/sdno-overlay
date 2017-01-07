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

package org.openo.sdno.overlayvpn.esr.invdao;

import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.consts.HttpConst;
import org.openo.sdno.overlayvpn.esr.model.SdnController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * DAO Class of SDN Controller.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-9-21
 */
@Repository
public class SdnControllerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SdnControllerDao.class);

    private static final String SDN_CONTROLLER_URL = "/openoapi/extsys/v1/sdncontrollers";

    /**
     * Query SDN Controller by Id.<br>
     * 
     * @param controllerId Controller Id
     * @return SdnController Object queried out
     * @since SDNO 0.5
     */
    public SdnController querySdnControllerById(String controllerId) throws ServiceException {
        if(StringUtils.isEmpty(controllerId)) {
            LOGGER.error("SDN Controller id is invalid.");
            throw new ServiceException("SDN Controller Id is invalid");
        }

        String queryUrl = SDN_CONTROLLER_URL + "/" + controllerId;

        RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader(HttpConst.CONTEXT_TYPE_HEADER, HttpConst.MEDIA_TYPE_JSON);
        RestfulResponse queryRsp = RestfulProxy.get(queryUrl, restParametes);

        if(!HttpCode.isSucess(queryRsp.getStatus()) || StringUtils.isEmpty(queryRsp.getResponseContent())) {
            LOGGER.error("SDN Controller query failed.");
            throw new ServiceException("SDN Controller query failed");
        }

        return JsonUtil.fromJson(queryRsp.getResponseContent(), SdnController.class);
    }

}
