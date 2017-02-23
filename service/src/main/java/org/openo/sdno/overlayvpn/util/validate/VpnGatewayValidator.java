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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.LogicalTernminationPointInvDao;
import org.openo.sdno.overlayvpn.brs.model.LogicalTernminationPointMO;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnSiteUtil;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpn;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The validator of the vpn gateway.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 13, 2017
 */
public class VpnGatewayValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnGatewayValidator.class);

    private static BaseDao<NbiVpnGateway> vpnGatewayDao = new BaseDao<NbiVpnGateway>();

    private static BaseDao<NbiVpnConnection> vpnConnectionDao = new BaseDao<NbiVpnConnection>();

    private static BaseDao<NbiVpn> vpnDao = new BaseDao<NbiVpn>();

    private static LogicalTernminationPointInvDao portDao = new LogicalTernminationPointInvDao();

    /**
     * The validator is used when creating vpn gateway.<br>
     * 
     * @param vpnGateway The vpn gateway data
     * @throws ServiceException when the vpn,ports or site is not exist,or the siteId and vpcId are
     *             empty or coexist
     * @since SDNO 0.5
     */
    public void validateCreate(NbiVpnGateway vpnGateway) throws ServiceException {
        LOGGER.info("Start validate the creating VpnGateway :" + JsonUtils.toJson(vpnGateway));
        if(StringUtils.isEmpty(vpnGateway.getId())) {
            ModelServiceUtil.initUuid(vpnGateway);
        }
        if(!StringUtils.isEmpty(vpnGateway.getRegionId())) {
            checkVpnGatewayVpnId(vpnGateway);
        } else {
            if(StringUtils.isEmpty(vpnGateway.getSiteId()) && StringUtils.isEmpty(vpnGateway.getVpcId())) {
                LOGGER.error("VpnGateway.create.error,siteId and vpcId are empty.");
                throw new InnerErrorServiceException("VpnGateway.create.error,siteId and vpcId are empty.");
            }
            if(StringUtils.isNotEmpty(vpnGateway.getSiteId()) && StringUtils.isNotEmpty(vpnGateway.getVpcId())) {
                LOGGER.error("VpnGateway.create.error,siteId and vpcId all exist.");
                throw new InnerErrorServiceException("VpnGateway.create.error,siteId and vpcId all exist.");
            }

            checkVpnGatewaySiteId(vpnGateway);

            checkVpnGatewayVpnId(vpnGateway);
        }

    }

    /**
     * The validator is used when updating vpn gateway.<br>
     * 
     * @param uuid The uuid of vpn gateway to be updated
     * @param data The vpn gateway data to be updated
     * @throws ServiceException when the vpn gateway is not in DB or cannot be update
     * @since SDNO 0.5
     */
    public void validateUpdate(String uuid, NbiVpnGateway data) throws ServiceException {
        NbiVpnGateway dbData = vpnGatewayDao.query(uuid, NbiVpnGateway.class);
        if(dbData == null) {
            LOGGER.error("VpnGateway.update.error,VpnGateway does not exist,uuid=" + uuid);
            throw new InnerErrorServiceException("VpnGateway.update.error,VpnGateway does not exist,uuid=" + uuid);
        }
        ActionState status = ActionState.fromState(dbData.getActionState());
        if(ActionState.NORMAL != status) {
            LOGGER.error("VpnGateway.update.error,VpnGateway cannot be update,unsupport action state,uuid=" + uuid
                    + ",state=" + status.getState());
            throw new InnerErrorServiceException(
                    "Connection.update.error,vpnConnection cannot be update,unsupport action state,uuid=" + uuid
                            + ",state=" + status.getState());
        }

        ModelServiceUtil.hidUneditableField(data);
    }

    /**
     * The validator is used when deleting vpn gateway.<br>
     * 
     * @param uuid The uuid of vpn gateway to be deleted
     * @return true when the vpn gateway is exist,false when the vpn gateway is not exist
     * @throws ServiceException when the vpn gateway is used in vpnConnection
     * @since SDNO 0.5
     */
    public boolean validateDelete(String uuid) throws ServiceException {
        NbiVpnGateway dbData = vpnGatewayDao.query(uuid, NbiVpnGateway.class);
        if(dbData == null) {
            LOGGER.warn("VpnGateway.delete.error,VpnGateway does not exist,uuid=" + uuid);
            return false;
        }
        List<NbiVpnConnection> vpnConnections = new ArrayList<>();

        NbiVpnConnection vpnConnectionA = new NbiVpnConnection();
        vpnConnectionA.setaEndVpnGatewayId(uuid);
        vpnConnections.addAll(vpnConnectionDao.query(vpnConnectionA));

        NbiVpnConnection vpnConnectionZ = new NbiVpnConnection();
        vpnConnectionZ.setzEndVpnGatewayId(uuid);
        vpnConnections.addAll(vpnConnectionDao.query(vpnConnectionZ));

        if(CollectionUtils.isNotEmpty(vpnConnections)) {
            LOGGER.error("VpnGateway.delete.error,VpnGateway is used in vpnConnection,uuid=" + uuid);
            throw new InnerErrorServiceException(
                    "VpnGateway.delete.error,VpnGateway is used in vpnConnection,uuid=" + uuid);
        }
        return true;
    }

    private void checkVpnGatewayVpnId(NbiVpnGateway vpnGateway) throws ServiceException {
        if(StringUtils.isNotEmpty(vpnGateway.getVpnId())) {
            NbiVpn vpn = vpnDao.query(vpnGateway.getVpnId(), NbiVpn.class);
            if(vpn == null) {
                LOGGER.error("VpnGateway.create.error,vpn does not exist,vpnId=" + vpnGateway.getVpnId());
                throw new InnerErrorServiceException(
                        "VpnGateway.create.error,vpn does not exist,vpnId=" + vpnGateway.getVpnId());
            }
        }
    }

    private void checkVpnGatewaySiteId(NbiVpnGateway vpnGateway) throws ServiceException {
        if(StringUtils.isNotEmpty(vpnGateway.getSiteId())) {
            VpnSite site = VpnSiteUtil.getVpnSite(vpnGateway.getSiteId());
            checkVpnGatewayPorts(vpnGateway, site);
        }
    }

    private void checkVpnGatewayPorts(NbiVpnGateway vpnGateway, VpnSite site) throws ServiceException {
        List<String> ports = vpnGateway.getPorts() == null ? new ArrayList<String>() : vpnGateway.getPorts();
        List<String> portNames =
                vpnGateway.getPortNames() == null ? new ArrayList<String>() : vpnGateway.getPortNames();
        if(CollectionUtils.isEmpty(ports) && CollectionUtils.isEmpty(portNames)) {
            return;
        }
        String neId = CollectionUtils.isEmpty(site.getLocalCpes()) ? null : site.getLocalCpes().get(0).getId();
        if(StringUtils.isEmpty(neId)) {
            LOGGER.error("VpnGateway.create.error,localCpe does not exist,siteId=" + vpnGateway.getSiteId());
            throw new InnerErrorServiceException(
                    "VpnGateway.create.error,localCpe does not exist,siteId=" + vpnGateway.getSiteId());
        }

        Map<String, String> condition = new HashMap<String, String>();
        condition.put(ParamConsts.ME_PARAM, neId);
        List<LogicalTernminationPointMO> intfs = portDao.query(condition);
        if(CollectionUtils.isEmpty(intfs)) {
            LOGGER.error("VpnGateway.create.error,ports does not exist,siteId=" + vpnGateway.getSiteId());
            throw new InnerErrorServiceException(
                    "VpnGateway.create.error,ports does not exist,siteId=" + vpnGateway.getSiteId());
        }

        Set<String> portSet = new HashSet<String>(ports);
        Set<String> portNameSet = new HashSet<String>(portNames);
        Set<String> portSetBrs = new HashSet<String>();
        Set<String> portNameSetBrs = new HashSet<String>();

        for(LogicalTernminationPointMO intf : intfs) {
            portSetBrs.add(intf.getId());
            portNameSetBrs.add(intf.getName());
            if(portSet.contains(intf.getId())) {
                portNameSet.add(intf.getName());
            } else if(portNameSet.contains(intf.getName())) {
                portSet.add(intf.getId());
            }
        }

        for(String portId : portSet) {
            if(!portSetBrs.contains(portId)) {
                LOGGER.error("portId does not exist,neId=" + neId + ",portId=" + portId);
                throw new InnerErrorServiceException("VpnGateway.create.error,port does not exist,portId=" + portId);
            }
        }
        for(String portName : portNameSet) {
            if(!portNameSetBrs.contains(portName)) {
                LOGGER.error("portName does not exist,neId=" + neId + ",portName=" + portName);
                throw new InnerErrorServiceException(
                        "VpnGateway.create.error,port does not exist,portName=" + portName);
            }
        }
        vpnGateway.setPorts(new ArrayList<>(portSet));
        vpnGateway.setPortNames(new ArrayList<>(portNameSet));
    }

}
