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

package org.openo.sdno.overlayvpn.site2dc.service.inf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.service.IService;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetRoute;

/**
 * The interface to query routes,extends IService.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public interface VpnRouteService extends IService {

    /**
     * Batch query vpn routes by conditions.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param paramRoute Query condition
     * @param batchQueryParam Batch query parameters
     * @return Operation result with route information wrapped
     * @throws ServiceException throws serviceException when operation on database fails
     * @since SDNO 0.5
     */
    BatchQueryResult<NetRoute> query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            NetRoute paramRoute, BatchQueryParam batchQueryParam) throws ServiceException;
}
