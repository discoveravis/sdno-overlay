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

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.composer.TemplateManager;
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpn;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.openo.sdno.overlayvpn.servicemodel.enums.Reliability;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The validator of the vpn.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 13, 2017
 */
public class VpnValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnValidator.class);

    private static BaseDao<NbiVpn> vpnDao = new BaseDao<NbiVpn>();

    private static BaseDao<NbiVpnConnection> vpnConnectionDao = new BaseDao<NbiVpnConnection>();

    private static BaseDao<NbiVpnGateway> vpnGatewayDao = new BaseDao<NbiVpnGateway>();

    /**
     * The validator is used when creating vpn.<br>
     * 
     * @param vpn The vpn data
     * @throws ServiceException when the vpn is in DB ,or VpnDescriptor is not exist
     * @since SDNO 0.5
     */
    public void validateCreate(NbiVpn vpn) throws ServiceException {
        LOGGER.info("Start validate the creating Vpn :" + JsonUtils.toJson(vpn));

        String vpnId = vpn.getId();
        if(StringUtils.isEmpty(vpnId)) {
            ModelServiceUtil.initUuid(vpn);
        } else {
            NbiVpn dbVpn = vpnDao.query(vpnId, NbiVpn.class);
            if(null != dbVpn) {
                LOGGER.error("Vpn.create.error,Vpn exist,uuid=" + vpnId);
                throw new InnerErrorServiceException("Vpn.create.error,Vpn exist,uuid=" + vpnId);
            }
        }

        Map<String, Template> templateMap = TemplateManager.getInstance().getTemplateMap();
        for(Reliability reliability : Reliability.values()) {
            String templateName = reliability.getTemplate(vpn.getVpnDescriptor());
            if(templateMap.containsKey(templateName)) {
                return;
            }
        }
        LOGGER.error("Vpn.create.error,VpnDescriptor does not exist,uuid=" + vpnId);
        throw new InnerErrorServiceException("Vpn.create.error,VpnDescriptor does not exist,uuid=" + vpnId);
    }

    /**
     * The validator is used when deleting vpn .<br>
     * 
     * @param uuid The uuid of vpn to be deleted
     * @return true when the vpn is exist,false when the vpn is not exist
     * @throws ServiceException when the vpn is been used
     * @since SDNO 0.5
     */
    public boolean validateDelete(String uuid) throws ServiceException {
        NbiVpn dbVpn = vpnDao.query(uuid, NbiVpn.class);
        if(dbVpn == null) {
            LOGGER.warn("Vpn does not exist,uuid=" + uuid);
            return false;
        }

        NbiVpnConnection vpnConnection = new NbiVpnConnection();
        vpnConnection.setVpnId(uuid);
        List<NbiVpnConnection> vpnConnections = vpnConnectionDao.query(vpnConnection);

        NbiVpnGateway vpnGateway = new NbiVpnGateway();
        vpnGateway.setVpnId(uuid);
        List<NbiVpnGateway> vpnGateways = vpnGatewayDao.query(vpnGateway);

        if(CollectionUtils.isNotEmpty(vpnConnections) || CollectionUtils.isNotEmpty(vpnGateways)) {
            LOGGER.error("Vpn.delete.error,vpn is been used,uuid=" + uuid);
            throw new InnerErrorServiceException("Vpn.delete.error,vpn is been used,uuid=" + uuid);
        }
        return true;
    }

    /**
     * The validator is used when updating vpn.<br>
     * 
     * @param uuid The uuid of vpn to be updated
     * @param vpn The vpn data to be updated
     * @throws ServiceException when the vpn is not in DB or cannot be update
     * @since SDNO 0.5
     */
    public void validateUpdate(String uuid, NbiVpn vpn) throws ServiceException {
        NbiVpn dbVpn = vpnDao.query(uuid, NbiVpn.class);
        if(dbVpn == null) {
            LOGGER.error("Vpn.update.error,Vpn does not exist,uuid=" + uuid);
            throw new InnerErrorServiceException("Vpn.update.error,Vpn does not exist,uuid=" + uuid);
        }
        ActionState status = ActionState.fromState(dbVpn.getActionState());
        if(ActionState.NORMAL != status) {
            LOGGER.error("Vpn.update.error,Vpn cannot be update,unsupport action state,uuid=" + uuid + ",state="
                    + status.getState());
            throw new InnerErrorServiceException("Vpn.update.error,Vpn cannot be update,unsupport action state,uuid="
                    + uuid + ",state=" + status.getState());
        }

        ModelServiceUtil.hidUneditableField(vpn);
    }

    /**
     * The validator is used when deploying vpn.<br>
     * 
     * @param uuid The uuid of vpn to be deployed
     * @return true if Vpn is contains vpnConnection, false if Vpn is not contains vpnConnection
     * @throws ServiceException when the vpn is not exist in DB, is on going or Vpn cannot be deploy
     * @since SDNO 0.5
     */
    public boolean validateDeploy(String uuid) throws ServiceException {
        NbiVpn dbVpn = vpnDao.query(uuid, NbiVpn.class, true);
        if(dbVpn == null) {
            LOGGER.error("Vpn.deploy.error,Vpn does not exist,uuid=" + uuid);
            throw new InnerErrorServiceException("Vpn.deploy.error,Vpn does not exist,uuid=" + uuid);
        }
        if(ModelServiceUtil.isOnGoing(dbVpn)) {
            LOGGER.error("Vpn.deploy.error,Vpn is on going,uuid=" + uuid);
            throw new InnerErrorServiceException("Vpn.deploy.error,Vpn is on going,uuid=" + uuid);
        }
        ActionState status = ActionState.fromState(dbVpn.getActionState());
        if(ActionState.DEPLOY_EXCEPTION != status && ActionState.NORMAL != status) {
            LOGGER.error("Vpn.deploy.error,Vpn cannot be deploy,unsupport action state,uuid=" + uuid + ",state="
                    + status.getState());
            throw new InnerErrorServiceException("Vpn.deploy.error,Vpn cannot be deploy,unsupport action state,uuid="
                    + uuid + ",state=" + status.getState());
        }

        if(CollectionUtils.isEmpty(dbVpn.getVpnConnections())) {
            LOGGER.info("Vpn is not contains vpnConnection,need not to deploy,uuid=" + uuid);
            return false;
        }
        return true;
    }

    /**
     * The validator is used when undeploying vpn.<br>
     * 
     * @param uuid The uuid of vpn to be undeployed
     * @return true if Vpn is contains vpnConnection, false if Vpn is not contains vpnConnection
     * @throws ServiceException when the vpn is not exist in DB, is on going or Vpn cannot be deploy
     * @since SDNO 0.5
     */
    public boolean validateUndeploy(String uuid) throws ServiceException {
        NbiVpn dbVpn = vpnDao.query(uuid, NbiVpn.class, true);
        if(dbVpn == null) {
            LOGGER.error("Vpn.undeploy.error,Vpn does not exist,uuid=" + uuid);
            throw new InnerErrorServiceException("Vpn.undeploy.error,Vpn does not exist,uuid=" + uuid);
        }
        if(ModelServiceUtil.isOnGoing(dbVpn)) {
            LOGGER.error("Vpn.undeploy.error,Vpn is on going,uuid=" + uuid);
            throw new InnerErrorServiceException("Vpn.undeploy.error,Vpn is on going,uuid=" + uuid);
        }
        ActionState status = ActionState.fromState(dbVpn.getActionState());
        if(ActionState.DEPLOY_EXCEPTION != status && ActionState.UNDEPLOY_EXCEPTION != status
                && ActionState.NORMAL != status) {
            LOGGER.error("Vpn.undeploy.error,Vpn cannot be undeploy,unsupport action state,uuid=" + uuid + ",state="
                    + status.getState());
            throw new InnerErrorServiceException(
                    "Vpn.undeploy.error,Vpn cannot be undeploy,unsupport action state,uuid=" + uuid + ",state="
                            + status.getState());
        }

        if(CollectionUtils.isEmpty(dbVpn.getVpnConnections())) {
            LOGGER.info("Vpn is not contains vpnConnection,need not to undeploy,uuid=" + uuid);
            return false;
        }
        return true;
    }

}
