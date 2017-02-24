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

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnRelationUtil;
import org.openo.sdno.overlayvpn.distribute.Distributer;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiConnectionRelation;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;
import org.openo.sdno.overlayvpn.site2dc.service.inf.BaseService;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implements of VpnConnectionService interface.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 13, 2017
 */
public class VpnConnectionServiceImpl implements BaseService<NbiVpnConnection> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnConnectionServiceImpl.class);

    private BaseDao<NbiVpnConnection> dao = new BaseDao<>();

    private Distributer<NbiVpnConnection> distributer = new Distributer<>();

    @Override
    public NbiVpnConnection create(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            NbiVpnConnection vpnConnection) throws ServiceException {
        LOGGER.info("Start insert VpnConnection to DB.uuid=" + vpnConnection.getId());
        dao.insert(vpnConnection);
        ModelServiceUtil.updateActionState(ActionState.CREATE_EXCEPTION, vpnConnection);

        LOGGER.info("Start create VpnConnection.uuid=" + vpnConnection.getId());
        distributer.create(vpnConnection);
        ModelServiceUtil.updateActionState(ActionState.NORMAL, vpnConnection);
        return ModelUtils.newModel(NbiVpnConnection.class, vpnConnection.getId());
    }

    @Override
    public NbiVpnConnection delete(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        ModelServiceUtil.updateActionState(ActionState.DELETE_EXCEPTION, uuid, NbiVpnConnection.class);
        distributer.delete(uuid, NbiVpnConnection.class);

        dao.delete(uuid, NbiVpnConnection.class);

        List<NbiConnectionRelation> relations =
                VpnRelationUtil.queryConnectionRelations(ModelUtils.newModel(NbiVpnConnection.class, uuid));
        if(CollectionUtils.isNotEmpty(relations)) {
            new BaseDao<NbiConnectionRelation>().delete(ModelServiceUtil.getUuidList(relations),
                    NbiConnectionRelation.class);
        }
        return ModelUtils.newModel(NbiVpnConnection.class, uuid);
    }

    @Override
    public NbiVpnConnection update(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid,
            NbiVpnConnection vpnConnection) throws ServiceException {
        return dao.update(uuid, vpnConnection);
    }

    @Override
    public NbiVpnConnection query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        return dao.query(uuid, NbiVpnConnection.class, true);
    }

    @Override
    public BatchQueryResult<NbiVpnConnection> query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            NbiVpnConnection condition, BatchQueryParam param) throws ServiceException {
        boolean isRelatedQuery = StringUtils.isNotEmpty(condition.getaEndVpnGatewayId())
                || StringUtils.isNotEmpty(condition.getzEndVpnGatewayId());
        return dao.query(condition, param, isRelatedQuery);
    }

    @Override
    public NbiVpnConnection deploy(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        distributer.deploy(uuid, NbiVpnConnection.class);
        NbiVpnConnection model = ModelUtils.newModel(NbiVpnConnection.class, uuid);
        ModelServiceUtil.updateDeployState(DeployState.DEPLOY, Arrays.asList(model));
        return model;
    }

    @Override
    public NbiVpnConnection undeploy(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        distributer.undeploy(uuid, NbiVpnConnection.class);
        NbiVpnConnection model = ModelUtils.newModel(NbiVpnConnection.class, uuid);
        ModelServiceUtil.updateDeployState(DeployState.UNDEPLOY, Arrays.asList(model));
        return model;
    }

}
