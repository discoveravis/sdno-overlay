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
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnRelationUtil;
import org.openo.sdno.overlayvpn.distribute.Distributer;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiConnectionRelation;
import org.openo.sdno.overlayvpn.servicemodel.db.InternalVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;
import org.openo.sdno.overlayvpn.site2dc.service.inf.BaseService;
import org.openo.sdno.overlayvpn.util.ModelUtils;

/**
 * The implements of InternalVpnConnectionService interface.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class InternalVpnConnectionServiceImpl implements BaseService<InternalVpnConnection> {

    private BaseDao<InternalVpnConnection> dao = new BaseDao<>();

    private Distributer<InternalVpnConnection> distributer = new Distributer<>();

    @Override
    public InternalVpnConnection create(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            InternalVpnConnection connection) throws ServiceException {
        dao.insert(connection);
        ModelServiceUtil.updateActionState(ActionState.CREATE_EXCEPTION, connection);

        distributer.create(connection);
        ModelServiceUtil.updateActionState(ActionState.NORMAL, connection);
        return ModelUtils.newModel(InternalVpnConnection.class, connection.getId());
    }

    @Override
    public InternalVpnConnection delete(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        ModelServiceUtil.updateActionState(ActionState.DELETE_EXCEPTION, uuid, InternalVpnConnection.class);
        distributer.delete(uuid, InternalVpnConnection.class);
        dao.delete(Arrays.asList(uuid), InternalVpnConnection.class);

        List<NbiConnectionRelation> relations =
                VpnRelationUtil.queryConnectionRelations(ModelUtils.newModel(InternalVpnConnection.class, uuid));
        if(CollectionUtils.isNotEmpty(relations)) {
            new BaseDao<NbiConnectionRelation>().delete(ModelServiceUtil.getUuidList(relations),
                    NbiConnectionRelation.class);
        }
        return ModelUtils.newModel(InternalVpnConnection.class, uuid);
    }

    @Override
    public InternalVpnConnection update(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid,
            InternalVpnConnection connection) throws ServiceException {
        return dao.update(uuid, connection);
    }

    @Override
    public InternalVpnConnection query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        return dao.query(uuid, InternalVpnConnection.class, true);
    }

    @Override
    public BatchQueryResult<InternalVpnConnection> query(HttpServletRequest httpContext,
            HttpServletResponse httpContextRsp, InternalVpnConnection condition, BatchQueryParam param)
            throws ServiceException {
        return dao.query(condition, param);
    }

    @Override
    public InternalVpnConnection deploy(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        InternalVpnConnection model = ModelUtils.newModel(InternalVpnConnection.class, uuid);
        ModelServiceUtil.updateDeployState(DeployState.DEPLOY, Arrays.asList(model));
        distributer.deploy(uuid, InternalVpnConnection.class);
        return model;
    }

    @Override
    public InternalVpnConnection undeploy(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            String uuid) throws ServiceException {
        InternalVpnConnection model = ModelUtils.newModel(InternalVpnConnection.class, uuid);
        ModelServiceUtil.updateDeployState(DeployState.UNDEPLOY, Arrays.asList(model));
        distributer.undeploy(uuid, InternalVpnConnection.class);
        return model;
    }

}
