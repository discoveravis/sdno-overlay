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

package org.openo.sdno.overlayvpn.util.validate;

import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpn;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The validator of the vpn connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 13, 2017
 */
public class VpnConnectionValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnConnectionValidator.class);

    private static BaseDao<NbiVpnConnection> vpnConnectionDao = new BaseDao<NbiVpnConnection>();

    private static BaseDao<NbiVpn> vpnDao = new BaseDao<NbiVpn>();

    private static BaseDao<NbiVpnGateway> vpnGatewayDao = new BaseDao<NbiVpnGateway>();

    /**
     * The validator is used when creating vpn connection.<br>
     * 
     * @param vpnConnection The vpn connection data
     * @throws ServiceException when the vpn connection is in DB ,or the vpnId or vpnGatewayId is
     *             not exist
     * @since SDNO 0.5
     */
    public void validateCreate(NbiVpnConnection vpnConnection) throws ServiceException {
        String vpnConnectionId = vpnConnection.getId();
        if(StringUtils.isEmpty(vpnConnectionId)) {
            ModelServiceUtil.initUuid(vpnConnection);
        } else {
            NbiVpnConnection dbVpnConnection = vpnConnectionDao.query(vpnConnectionId, NbiVpnConnection.class);
            if(dbVpnConnection != null) {
                LOGGER.error("Connection.create.error,vpnConnection exist,uuid=" + vpnConnectionId);
                throw new InnerErrorServiceException(
                        "Connection.create.error,vpnConnection exist,uuid=" + vpnConnectionId);
            }
        }
        this.checkVpnId(vpnConnection);
        this.checkVpnGatewayId(vpnConnection.getaEndVpnGatewayId());
        this.checkVpnGatewayId(vpnConnection.getzEndVpnGatewayId());

        initDefaultParams(vpnConnection);
    }

    /**
     * The validator is used when updating vpn connection.<br>
     * 
     * @param uuid The uuid of vpn connection to be updated
     * @param data The vpn connection data to be updated
     * @throws ServiceException when the vpn connection is not in DB or cannot be update
     * @since SDNO 0.5
     */
    public void validateUpdate(String uuid, NbiVpnConnection data) throws ServiceException {
        NbiVpnConnection dbData = vpnConnectionDao.query(uuid, NbiVpnConnection.class);
        if(dbData == null) {
            LOGGER.error("Connection.update.error,vpnConnection does not exist,uuid=" + uuid);
            throw new InnerErrorServiceException("Connection.update.error,vpnConnection does not exist,uuid=" + uuid);
        }
        ActionState status = ActionState.fromState(dbData.getActionState());
        if(ActionState.CREATE_EXCEPTION != status && ActionState.NORMAL != status) {
            LOGGER.error("Connection.update.error,vpnConnection cannot be update,unsupport action state,uuid=" + uuid
                    + ",state=" + status.getState());
            throw new InnerErrorServiceException(
                    "Connection.update.error,vpnConnection cannot be update,unsupport action state,uuid=" + uuid
                            + ",state=" + status.getState());
        }

        ModelServiceUtil.hidUneditableField(data);
    }

    /**
     * The validator is used when deploying vpn connection.<br>
     * 
     * @param uuid The uuid of vpn connection to be deployed
     * @return true if the deploy state is not deploy, false if the deploy state is deploy
     * @throws ServiceException when the vpn connection is not exist in DB or is on going
     * @since SDNO 0.5
     */
    public boolean validateDeploy(String uuid) throws ServiceException {
        NbiVpnConnection dbData = vpnConnectionDao.query(uuid, NbiVpnConnection.class);
        if(dbData == null) {
            LOGGER.error("Connection.deploy.error,vpnConnection does not exist,uuid=" + uuid);
            throw new InnerErrorServiceException("Connection.deploy.error,vpnConnection does not exist,uuid=" + uuid);
        }
        if(ModelServiceUtil.isOnGoing(dbData)) {
            LOGGER.error("Connection.deploy.error,vpnConnection is on going,uuid=" + uuid);
            throw new InnerErrorServiceException("Connection.deploy.error,vpnConnection is on going,uuid=" + uuid);
        }
        if(DeployState.DEPLOY.getState().equalsIgnoreCase(dbData.getDeployStatus().trim())) {
            return false;
        }
        return true;
    }

    /**
     * The validator is used when undeploying vpn connection.<br>
     * 
     * @param uuid The uuid of vpn connection to be undeployed
     * @return true if the deploy state is not undeploy, false if the deploy state is undeploy
     * @throws ServiceException when the vpn connection is not exist in DB or is on going
     * @since SDNO 0.5
     */
    public boolean validateUndeploy(String uuid) throws ServiceException {
        NbiVpnConnection dbData = vpnConnectionDao.query(uuid, NbiVpnConnection.class);
        if(dbData == null) {
            LOGGER.error("Connection.undeploy.error,vpnConnection does not exist,uuid=" + uuid);
            throw new InnerErrorServiceException("Connection.undeploy.error,vpnConnection does not exist,uuid=" + uuid);
        }
        if(ModelServiceUtil.isOnGoing(dbData)) {
            LOGGER.error("Connection.undeploy.error,vpnConnection is on going,uuid=" + uuid);
            throw new InnerErrorServiceException("Connection.undeploy.error,vpnConnection is on going,uuid=" + uuid);
        }
        if(DeployState.UNDEPLOY.getState().equalsIgnoreCase(dbData.getDeployStatus().trim())) {
            return false;
        }
        return true;
    }

    private void checkVpnId(NbiVpnConnection vpnConnection) throws ServiceException {
        NbiVpn vpn = vpnDao.query(vpnConnection.getVpnId(), NbiVpn.class);
        if(vpn == null) {
            LOGGER.error("Connection.create.error,vpn does not exist,uuid=" + vpnConnection.getVpnId());
            throw new InnerErrorServiceException(
                    "Connection.create.error,vpn does not exist,uuid=" + vpnConnection.getVpnId());
        }
    }

    private void checkVpnGatewayId(String vpnGatewayId) throws ServiceException {
        NbiVpnGateway vpnGateway = vpnGatewayDao.query(vpnGatewayId, NbiVpnGateway.class);
        if(vpnGateway == null) {
            LOGGER.error("Connection.create.error,vpnGateway does not exist,uuid=" + vpnGatewayId);
            throw new InnerErrorServiceException(
                    "Connection.create.error,vpnGateway does not exist,uuid=" + vpnGatewayId);
        }
    }

    private void initDefaultParams(NbiVpnConnection vpnConnection) throws ServiceException {
        vpnConnection.setActiveStatus(null);
        vpnConnection.setRunningStatus("none");
        vpnConnection.setRouteType("static");
    }
}
