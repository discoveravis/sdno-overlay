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

package org.openo.sdno.overlayvpn.site2dc.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetRoute;
import org.openo.sdno.overlayvpn.site2dc.service.inf.VpnRouteService;

/**
 * The implements of VpnRouteService interface.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 13, 2017
 */
public class VpnRouteServiceImpl implements VpnRouteService {

    private BaseDao<NetRoute> dao = new BaseDao<>();

    @Override
    public BatchQueryResult<NetRoute> query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            NetRoute paramRoute, BatchQueryParam batchQueryParam) throws ServiceException {
        return dao.query(paramRoute, batchQueryParam);
    }

}
