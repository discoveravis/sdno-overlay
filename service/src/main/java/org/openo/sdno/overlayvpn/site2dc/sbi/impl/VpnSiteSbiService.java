/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.site2dc.sbi.impl;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.overlayvpn.util.rsp.ResponseUtils;
import org.openo.sdno.rest.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SBI service of vpn site.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 17, 2017
 */
public class VpnSiteSbiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnSiteSbiService.class);

    private static final String SBI_URL = "/openoapi/sdnolocalsite/v1/sites";

    private VpnSiteSbiService() {

    }

    public static VpnSite query(String uuid) throws ServiceException {
        String url = new StringBuilder(SBI_URL).append('/').append(uuid).toString();
        RestfulParametes restfulParametes = RequestUtils.constructRestParameters(null);

        LOGGER.info("Do query :" + url + ": Site :" + restfulParametes.getRawData());
        RestfulResponse response = RestfulProxy.get(url, restfulParametes);
        LOGGER.info("Do query : status=" + response.getStatus() + ",response=" + response.getResponseContent());

        ResponseUtils.checkResponseAndThrowException(response);
        return JsonUtils.fromJson(response.getResponseContent(), new TypeReference<VpnSite>() {});
    }
}
