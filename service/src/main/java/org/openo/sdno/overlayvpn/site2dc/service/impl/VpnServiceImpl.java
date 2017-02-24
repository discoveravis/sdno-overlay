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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpn;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;
import org.openo.sdno.overlayvpn.site2dc.service.inf.BaseService;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.openo.sdno.overlayvpn.util.validate.VpnConnectionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implements of VpnService interface.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 13, 2017
 */
public class VpnServiceImpl implements BaseService<NbiVpn> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnServiceImpl.class);

    private VpnConnectionServiceImpl vpnConnectionService = new VpnConnectionServiceImpl();

    private BaseDao<NbiVpn> vpnDao = new BaseDao<>();

    private BaseDao<NbiVpnConnection> vpnConnectionDao = new BaseDao<>();

    private BaseDao<NbiVpnGateway> vpnGatewayDao = new BaseDao<>();

    private VpnConnectionValidator vpnConnectionValidator = new VpnConnectionValidator();

    @Override
    public NbiVpn create(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, NbiVpn data)
            throws ServiceException {
        vpnDao.insert(data);
        ModelServiceUtil.updateActionState(ActionState.NORMAL, data);
        return data;
    }

    @Override
    public NbiVpn delete(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        vpnDao.delete(uuid, NbiVpn.class);
        return ModelUtils.newModel(NbiVpn.class, uuid);
    }

    @Override
    public NbiVpn update(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid, NbiVpn data)
            throws ServiceException {
        return vpnDao.update(uuid, data);
    }

    @Override
    public NbiVpn query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        NbiVpn vpn = vpnDao.query(uuid, NbiVpn.class, true);
        if(vpn != null) {
            loadSiteList(Arrays.asList(vpn));
        }
        return vpn;
    }

    @Override
    public BatchQueryResult<NbiVpn> query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            NbiVpn condition, BatchQueryParam param) throws ServiceException {
        Map<String, String[]> paramMap = httpContext.getParameterMap();

        if(paramMap.containsKey("vpnConnectionId")) {
            String vpnConnectionId = httpContext.getParameter("vpnConnectionId");
            NbiVpnConnection dbVpnConnection = vpnConnectionDao.query(vpnConnectionId, NbiVpnConnection.class);
            if(null == dbVpnConnection || StringUtils.isEmpty(dbVpnConnection.getVpnId())) {
                LOGGER.warn(
                        "Can not get vpnConnection or vpnId by vpnConnectionId,vpnConnectionId =" + vpnConnectionId);
                return new BatchQueryResult<NbiVpn>();
            }
            condition.setId(dbVpnConnection.getVpnId());
        }
        if(paramMap.containsKey("vpnGatewayId")) {
            String vpnGatewayId = httpContext.getParameter("vpnGatewayId");
            NbiVpnGateway dbVpnGateway = vpnGatewayDao.query(vpnGatewayId, NbiVpnGateway.class);
            if(null == dbVpnGateway) {
                LOGGER.warn("Query VpnGateway by vpnGatewayId,data is null,vpnGatewayId =" + vpnGatewayId);
                return new BatchQueryResult<NbiVpn>();
            } else {
                String vpnId = dbVpnGateway.getVpnId();
                if(StringUtils.isEmpty(vpnId)
                        || (StringUtils.isNotEmpty(condition.getId()) && (!condition.getId().equals(vpnId)))) {
                    LOGGER.warn(
                            "Query VpnGateway by vpnGatewayId,but vpnId is null or different with query by connectionId,vpnGatewayId ="
                                    + vpnGatewayId);
                    return new BatchQueryResult<NbiVpn>();
                }
                condition.setId(vpnId);
            }
        }
        BatchQueryResult<NbiVpn> vpn = vpnDao.query(condition, param);
        loadSiteList(vpn.getObjects());
        return vpn;
    }

    @Override
    public NbiVpn deploy(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        NbiVpn dbVpn = vpnDao.query(uuid, NbiVpn.class, true);

        deployConnectionBelongVpn(httpContext, httpContextRsp, dbVpn);

        NbiVpn model = ModelUtils.newModel(NbiVpn.class, uuid);

        if(!DeployState.DEPLOY.getState().equalsIgnoreCase(dbVpn.getDeployStatus())) {
            ModelServiceUtil.updateDeployState(DeployState.DEPLOY, Arrays.asList(model));
        }
        return model;
    }

    @Override
    public NbiVpn undeploy(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid)
            throws ServiceException {
        NbiVpn dbVpn = vpnDao.query(uuid, NbiVpn.class, true);

        undeployConnectionBelongVpn(httpContext, httpContextRsp, dbVpn);

        NbiVpn model = ModelUtils.newModel(NbiVpn.class, uuid);

        if(!DeployState.UNDEPLOY.getState().equalsIgnoreCase(dbVpn.getDeployStatus())) {
            ModelServiceUtil.updateDeployState(DeployState.UNDEPLOY, Arrays.asList(model));
        }
        return model;
    }

    private void loadSiteList(List<NbiVpn> vpnList) throws ServiceException {
        if(CollectionUtils.isEmpty(vpnList)) {
            return;
        }
        List<NbiVpnGateway> gateways = new ArrayList<>();
        for(NbiVpn vpn : vpnList) {
            NbiVpnGateway cond = new NbiVpnGateway();
            cond.setVpnId(vpn.getId());
            gateways.addAll(vpnGatewayDao.query(cond));
        }
        if(CollectionUtils.isEmpty(gateways)) {
            return;
        }
        Map<String, List<NbiVpnGateway>> gatewayMap = splitGateways(gateways);
        for(NbiVpn vpn : vpnList) {
            List<NbiVpnGateway> gatewayList = gatewayMap.get(vpn.getId());
            if(CollectionUtils.isEmpty(gatewayList)) {
                continue;
            }
            vpn.setSiteList(new HashSet<String>());
            vpn.setVpcList(new HashSet<String>());
            for(NbiVpnGateway gateway : gatewayList) {
                if(StringUtils.isNotEmpty(gateway.getSiteId())) {
                    vpn.getSiteList().add(gateway.getSiteId());
                }
                if(StringUtils.isNotEmpty(gateway.getVpcId())) {
                    vpn.getVpcList().add(gateway.getVpcId());
                }
            }
        }
    }

    private Map<String, List<NbiVpnGateway>> splitGateways(List<NbiVpnGateway> gateways) {
        Map<String, List<NbiVpnGateway>> map = new HashMap<String, List<NbiVpnGateway>>();
        for(NbiVpnGateway gateway : gateways) {
            if(!map.containsKey(gateway.getVpnId())) {
                map.put(gateway.getVpnId(), new ArrayList<NbiVpnGateway>());
            }
            map.get(gateway.getVpnId()).add(gateway);
        }
        return map;
    }

    private void deployConnectionBelongVpn(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            NbiVpn dbVpn) throws ServiceException {
        if(CollectionUtils.isNotEmpty(dbVpn.getVpnConnections())) {
            for(NbiVpnConnection vpnConnection : dbVpn.getVpnConnections()) {
                if(!DeployState.DEPLOY.getState().equalsIgnoreCase(vpnConnection.getDeployStatus().trim())) {
                    boolean validateResult = false;
                    try {
                        validateResult = vpnConnectionValidator.validateDeploy(vpnConnection.getId());
                    } catch(ServiceException e) {
                        LOGGER.warn("This connection can not be deploy,connection uuid=" + vpnConnection.getId());
                        continue;
                    }
                    if(validateResult) {
                        vpnConnectionService.deploy(httpContext, httpContextRsp, vpnConnection.getId());
                    }
                }
            }
        }
    }

    private void undeployConnectionBelongVpn(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            NbiVpn dbVpn) throws ServiceException {
        if(CollectionUtils.isNotEmpty(dbVpn.getVpnConnections())) {
            for(NbiVpnConnection vpnConnection : dbVpn.getVpnConnections()) {
                if(!DeployState.UNDEPLOY.getState().equalsIgnoreCase(vpnConnection.getDeployStatus().trim())) {
                    boolean validateResult = false;
                    try {
                        validateResult = vpnConnectionValidator.validateUndeploy(vpnConnection.getId());
                    } catch(ServiceException e) {
                        LOGGER.warn("This connection can not be undeploy,connection uuid=" + vpnConnection.getId());
                        continue;
                    }
                    if(validateResult) {
                        vpnConnectionService.undeploy(httpContext, httpContextRsp, vpnConnection.getId());
                    }
                }
            }
        }
    }
}
