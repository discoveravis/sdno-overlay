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
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.openo.sdno.overlayvpn.site2dc.service.inf.BaseService;
import org.openo.sdno.overlayvpn.util.ModelUtils;

/**
 * The implements of VpnGatewayService interface.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 13, 2017
 */
public class VpnGatewayServiceImpl implements BaseService<NbiVpnGateway> {

    private BaseDao<NbiVpnGateway> dao = new BaseDao<>();

    @Override
    public NbiVpnGateway create(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            NbiVpnGateway vpnGateway) throws ServiceException {
        dao.insert(vpnGateway);
        ModelServiceUtil.updateActionState(ActionState.NORMAL, vpnGateway);
        return vpnGateway;
    }

    @Override
    public NbiVpnGateway delete(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        dao.delete(uuid, NbiVpnGateway.class);
        return ModelUtils.newModel(NbiVpnGateway.class, uuid);
    }

    @Override
    public NbiVpnGateway update(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid,
            NbiVpnGateway vpnGateway) throws ServiceException {
        return dao.update(uuid, vpnGateway);
    }

    @Override
    public NbiVpnGateway query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        return dao.query(uuid, NbiVpnGateway.class, true);
    }

    @Override
    public BatchQueryResult<NbiVpnGateway> query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            NbiVpnGateway condition, BatchQueryParam param) throws ServiceException {
        return dao.query(condition, param);
    }

    @Override
    public NbiVpnGateway deploy(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        return null;
    }

    @Override
    public NbiVpnGateway undeploy(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        return null;
    }

}
