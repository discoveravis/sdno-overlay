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
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.composer.TemplateManager;
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnSiteUtil;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.servicemodel.db.InternalVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The validator of the internal connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 13, 2017
 */
public class InternalConnectionValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalConnectionValidator.class);

    private static BaseDao<InternalVpnConnection> dao = new BaseDao<InternalVpnConnection>();

    private static NetworkElementInvDao neDao = new NetworkElementInvDao();

    /**
     * The validator is used when creating internal vpn connection.<br>
     * 
     * @param connection The internal vpn connection data
     * @throws ServiceException when the internal vpn connection is in DB ,or the cpe,site or
     *             template is not exist
     * @since SDNO 0.5
     */
    public void validateCreate(InternalVpnConnection connection) throws ServiceException {
        LOGGER.info("Start validate the creating InternalVpnConnection :" + JsonUtils.toJson(connection));
        if(StringUtils.isEmpty(connection.getId())) {
            connection.allocateUuid();
        } else {
            InternalVpnConnection dbConnection = dao.query(connection.getId(), InternalVpnConnection.class);
            if(dbConnection != null) {
                LOGGER.error(
                        "InternalVpnConnection.create.error,InternalVpnConnection exist,uuid=" + connection.getId());
                throw new InnerErrorServiceException(
                        "InternalVpnConnection.create.error,InternalVpnConnection exist,uuid=" + connection.getId());
            }
        }

        this.validCpe(connection, connection.getSrcNeId());
        this.validCpe(connection, connection.getDestNeId());
        this.validSite(connection);
        this.validTemplate(connection);
    }

    /**
     * The validator is used when deleting internal vpn connection.<br>
     * 
     * @param uuid The uuid of internal vpn connection to be deleted
     * @return true when the internal vpn connection is exist,false when the internal vpn connection
     *         is not exist
     * @throws ServiceException when the internal vpn connection is on going
     * @since SDNO 0.5
     */
    public boolean validateDelete(String uuid) throws ServiceException {
        InternalVpnConnection connection = dao.query(uuid, InternalVpnConnection.class);
        if(connection == null) {
            LOGGER.error("InternalVpnConnection does not exist,uuid=" + uuid);
            return false;
        }
        if(ModelServiceUtil.isOnGoing(connection)) {
            LOGGER.error("InternalVpnConnection.delete.error,InternalVpnConnection is on going,uuid=" + uuid);
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.delete.error,InternalVpnConnection is on going,uuid=" + uuid);
        }
        return true;
    }

    /**
     * The validator is used when updating internal vpn connection.<br>
     * 
     * @param uuid The uuid of internal vpn connection to be updated
     * @param connection The internal vpn connection data to be updated
     * @throws ServiceException when the internal vpn connection is not in DB or cannot be update
     * @since SDNO 0.5
     */
    public void validateUpdate(String uuid, InternalVpnConnection connection) throws ServiceException {
        InternalVpnConnection dbData = dao.query(uuid, InternalVpnConnection.class);
        if(dbData == null) {
            LOGGER.error("InternalVpnConnection.update.error,InternalVpnConnection does not exist,uuid=" + uuid);
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.update.error,InternalVpnConnection does not exist,uuid=" + uuid);
        }
        ActionState status = ActionState.fromState(dbData.getActionState());
        if(ActionState.CREATE_EXCEPTION != status && ActionState.NORMAL != status) {
            LOGGER.error(
                    "InternalVpnConnection.update.error,InternalVpnConnection cannot be update,unsupport action state,uuid="
                            + uuid + ",state=" + status.getState());
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.update.error,InternalVpnConnection cannot be update,unsupport action state,uuid="
                            + uuid + ",state=" + status.getState());
        }

        ModelServiceUtil.hidUneditableField(connection);
    }

    /**
     * The validator is used when deploying internal vpn connection.<br>
     * 
     * @param uuid The uuid of internal vpn connection to be deployed
     * @return true if the deploy state is not deploy, false if the deploy state is deploy
     * @throws ServiceException when the internal vpn connection is not exist in DB or is on going
     * @since SDNO 0.5
     */
    public boolean validateDeploy(String uuid) throws ServiceException {
        InternalVpnConnection dbData = dao.query(uuid, InternalVpnConnection.class);
        if(dbData == null) {
            LOGGER.error("InternalVpnConnection.deploy.error,InternalVpnConnection does not exist,uuid=" + uuid);
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.deploy.error,InternalVpnConnection does not exist,uuid=" + uuid);
        }
        if(ModelServiceUtil.isOnGoing(dbData)) {
            LOGGER.error("InternalVpnConnection.deploy.error,InternalVpnConnection is on going,uuid=" + uuid);
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.deploy.error,InternalVpnConnection is on going,uuid=" + uuid);
        }
        DeployState deployState = DeployState.fromState(dbData.getDeployStatus());
        if(DeployState.DEPLOY == deployState) {
            return false;
        }
        return true;
    }

    /**
     * The validator is used when undeploying internal vpn connection.<br>
     * 
     * @param uuid The uuid of internal vpn connection to be undeployed
     * @return true if the deploy state is not undeploy, false if the deploy state is undeploy
     * @throws ServiceException when the internal vpn connection is not exist in DB or is on going
     * @since SDNO 0.5
     */
    public boolean validateUndeploy(String uuid) throws ServiceException {
        InternalVpnConnection dbData = dao.query(uuid, InternalVpnConnection.class);
        if(dbData == null) {
            LOGGER.error("InternalVpnConnection.undeploy.error,InternalVpnConnection does not exist,uuid=" + uuid);
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.undeploy.error,InternalVpnConnection does not exist,uuid=" + uuid);
        }
        if(ModelServiceUtil.isOnGoing(dbData)) {
            LOGGER.error("InternalVpnConnection.undeploy.error,InternalVpnConnection is on going,uuid=" + uuid);
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.undeploy.error,InternalVpnConnection is on going,uuid=" + uuid);
        }
        DeployState deployState = DeployState.fromState(dbData.getDeployStatus());
        if(DeployState.UNDEPLOY == deployState) {
            return false;
        }
        return true;
    }

    private void validCpe(InternalVpnConnection connection, String neId) throws ServiceException {
        if(StringUtils.isEmpty(neId)) {
            LOGGER.error("InternalVpnConnection.create.error,neId in InternalVpnConnection is empty,uuid="
                    + connection.getId());
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.create.error,neId in InternalVpnConnection is empty,uuid="
                            + connection.getId());
        }
        NetworkElementMO ne = neDao.query(neId);
        if(ne == null) {
            LOGGER.error("InternalVpnConnection.create.error,ne is not exist,neId=" + neId);
            throw new InnerErrorServiceException("InternalVpnConnection.create.error,ne is not exist,neId=" + neId);
        }
    }

    private void validSite(InternalVpnConnection connection) throws ServiceException {
        if(StringUtils.isEmpty(connection.getSiteId())) {
            LOGGER.error("InternalVpnConnection.create.error,siteId in InternalVpnConnection is empty,uuid="
                    + connection.getId());
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.create.error,siteId in InternalVpnConnection is empty,uuid="
                            + connection.getId());
        }
        VpnSiteUtil.getVpnSite(connection.getSiteId());
    }

    private void validTemplate(InternalVpnConnection connection) throws ServiceException {
        String templateName = connection.getVpnTemplateName();
        if(StringUtils.isEmpty(templateName)) {
            LOGGER.error("InternalVpnConnection.create.error,templateName in InternalVpnConnection is empty,uuid="
                    + connection.getId());
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.create.error,templateName in InternalVpnConnection is empty,uuid="
                            + connection.getId());
        }
        Template template = TemplateManager.getInstance().getTemplate(templateName);
        if(template == null) {
            LOGGER.error("InternalVpnConnection.create.error,template does not exist,templateName=" + templateName);
            throw new InnerErrorServiceException(
                    "InternalVpnConnection.create.error,template does not exist,templateName=" + templateName);
        }
    }
}
