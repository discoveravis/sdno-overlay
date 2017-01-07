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

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.consts.HttpConst;
import org.openo.sdno.overlayvpn.esr.model.Vim;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * DAO Class of VIM.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-9-21
 */
@Repository
public class VimInvDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(VimInvDao.class);

    private static final String VIM_URL = "/openoapi/extsys/v1/vims";

    /**
     * Query VIM by name.<br>
     * 
     * @param vimName VIM name
     * @return VIM Object queried out
     * @since SDNO 0.5
     */
    public Vim queryVimByName(String vimName) throws ServiceException {
        if(StringUtils.isEmpty(vimName)) {
            LOGGER.error("Vim name is invalid.");
            throw new ServiceException("Vim name is invalid");
        }

        RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader(HttpConst.CONTEXT_TYPE_HEADER, HttpConst.MEDIA_TYPE_JSON);
        RestfulResponse queryRsp = RestfulProxy.get(VIM_URL, restParametes);

        if(!HttpCode.isSucess(queryRsp.getStatus()) || StringUtils.isEmpty(queryRsp.getResponseContent())) {
            LOGGER.error("Vim query failed.");
            throw new ServiceException("Vim query failed");
        }

        List<Vim> vimList = JsonUtil.fromJson(queryRsp.getResponseContent(), new TypeReference<List<Vim>>() {});
        for(Vim vim : vimList) {
            if(vimName.equals(vim.getName())) {
                return vim;
            }
        }

        return null;
    }

    /**
     * Query VIM by Id.<br>
     * 
     * @param vimId VIM Id
     * @return VIM Object queried out
     * @since SDNO 0.5
     */
    public Vim queryVimById(String vimId) throws ServiceException {
        if(StringUtils.isEmpty(vimId)) {
            LOGGER.error("Vim id is invalid.");
            throw new ServiceException("Vim id is invalid");
        }

        String queryUrl = VIM_URL + "/" + vimId;

        RestfulParametes restParametes = new RestfulParametes();
        restParametes.putHttpContextHeader(HttpConst.CONTEXT_TYPE_HEADER, HttpConst.MEDIA_TYPE_JSON);
        RestfulResponse queryRsp = RestfulProxy.get(queryUrl, restParametes);

        if(!HttpCode.isSucess(queryRsp.getStatus()) || StringUtils.isEmpty(queryRsp.getResponseContent())) {
            LOGGER.error("Vim query failed.");
            throw new ServiceException("Vim query failed");
        }

        return JsonUtil.fromJson(queryRsp.getResponseContent(), Vim.class);
    }

}
