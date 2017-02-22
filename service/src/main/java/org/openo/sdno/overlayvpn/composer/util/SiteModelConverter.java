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

package org.openo.sdno.overlayvpn.composer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.composer.model.ConnectionTopology;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseSite;
import org.openo.sdno.overlayvpn.servicemodel.base.Vpc;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.servicemodel.db.InternalVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.enums.ConnectionType;
import org.openo.sdno.overlayvpn.servicemodel.enums.CpeRole;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convert Nbi model to ne connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 23, 2017
 */
public class SiteModelConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteModelConverter.class);

    private SiteModelConverter() {
        super();
    }

    /**
     * Convert connection to ne connection list.<br>
     * 
     * @param connection The Nbi connection,maybe is vpn connection or internal connection
     * @return The converted ne connection list
     * @throws ServiceException when convert connection failed
     * @since SDNO 0.5
     */
    public static List<NeConnection> convert(NbiServiceConnection connection) throws ServiceException {
        if(NbiVpnConnection.class.isAssignableFrom(connection.getClass())) {
            return convert((NbiVpnConnection)connection);
        } else if(InternalVpnConnection.class.isAssignableFrom(connection.getClass())) {
            return convert((InternalVpnConnection)connection);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Convert vpn connection to ne connection list.<br>
     * 
     * @param connection The Nbi vpn connection
     * @return The converted ne connection list
     * @throws ServiceException when convert connection failed
     * @since SDNO 0.5
     */
    public static List<NeConnection> convert(NbiVpnConnection connection) throws ServiceException {
        List<NeConnection> neConnections = new ArrayList<>();

        NbiVpnGateway aEndVpnGateway = getVpnGateway(connection.getaEndVpnGatewayId());
        BaseSite aEndSite = getBaseSite(aEndVpnGateway);

        NbiVpnGateway zEndVpnGateway = getVpnGateway(connection.getzEndVpnGatewayId());
        BaseSite zEndSite = getBaseSite(zEndVpnGateway);

        Template[] templates = VpnTemplateUtil.getTemplate(connection, aEndSite, zEndSite);
        NeConnection res = new NeConnection(connection);
        res.setSrc(null, null, aEndSite, aEndVpnGateway, templates[0]);
        res.setDest(null, null, zEndSite, zEndVpnGateway, templates[1]);

        ConnectionTopology connectionEnds = getTopology(aEndVpnGateway, zEndVpnGateway, templates[0]);
        if(ConnectionType.SITE2DC.equals(connectionEnds.getType())) {
            neConnections.addAll(buildNeConnectionDc(res, connectionEnds));
        }
        return neConnections;
    }

    /**
     * Convert internal connection to ne connection list.<br>
     * 
     * @param connection The Nbi internal connection
     * @return The converted ne connection list
     * @throws ServiceException when convert connection failed
     * @since SDNO 0.5
     */
    public static List<NeConnection> convert(InternalVpnConnection connection) throws ServiceException {
        List<NeConnection> neConnections = new ArrayList<>();
        Template template = VpnTemplateUtil.getTemplate(connection);
        VpnSite site = VpnSiteUtil.getVpnSite(connection.getSiteId());
        NbiVpnGateway gateway = new NbiVpnGateway();
        gateway.setSiteId(site.getId());
        String srcNeRole = getCpeRole(connection.getSrcNeId(), site).getRole();
        String destNeRole = getCpeRole(connection.getDestNeId(), site).getRole();
        LOGGER.info("srcNeRole:" + srcNeRole + ",destNeRole:" + destNeRole);
        NeConnection neConnection = new NeConnection(connection);
        neConnection.setSrc(connection.getSrcNeId(), srcNeRole, site, gateway, template);
        neConnection.setDest(connection.getDestNeId(), destNeRole, site, gateway, template);
        if((CpeRole.LOCALCPE.getRole().equals(srcNeRole)) && (CpeRole.CLOUDCPE.getRole().equals(destNeRole))) {
            LOGGER.info("Connection need to reverse.");
            neConnection.reverse();
        }
        neConnections.add(neConnection);
        return neConnections;
    }

    private static CpeRole getCpeRole(String neId, VpnSite site) throws ServiceException {
        if(CollectionUtils.isNotEmpty(site.getLocalCpes())) {
            for(NetworkElementMO localCpe : site.getLocalCpes()) {
                if(neId.equals(localCpe.getId())) {
                    return CpeRole.LOCALCPE;
                }
            }
        }
        if(CollectionUtils.isNotEmpty(site.getCloudCpes())) {
            for(NetworkElementMO cloudCpe : site.getCloudCpes()) {
                if(neId.equals(cloudCpe.getId())) {
                    return CpeRole.CLOUDCPE;
                }
            }
        }
        LOGGER.error("BaseInfo.getCpeRole.error,the CpeRole is neither localCpe nor cloudCpe,siteId = " + site.getId()
                + ",neId = " + neId);
        throw new InnerErrorServiceException(
                "BaseInfo.getCpeRole.error,the CpeRole is neither localCpe nor cloudCpe,siteId = " + site.getId()
                        + ",neId = " + neId);
    }

    private static ConnectionTopology getTopology(NbiVpnGateway aEndGw, NbiVpnGateway zEndGw, Template template)
            throws ServiceException {
        ConnectionTopology ce = new ConnectionTopology();

        CpeRole dcGw = CpeRole.getCpeRole(template.getGwPolicy().get(ParamConsts.SITE_DC_VPNGW));
        if(dcGw == null) {
            dcGw = CpeRole.CLOUDCPE;
        }
        if((StringUtils.isNotEmpty(aEndGw.getVpcId()) && StringUtils.isNotEmpty(zEndGw.getSiteId()))
                || (StringUtils.isNotEmpty(zEndGw.getVpcId()) && StringUtils.isNotEmpty(aEndGw.getSiteId()))) {
            ce.setSrcRole(dcGw);
            ce.setDestRole(CpeRole.VPC);
            ce.setType(ConnectionType.SITE2DC);
            return ce;
        }
        LOGGER.error("vpnsite.getTopology.error,VpnGateway do not match site2dc,uuid1=" + aEndGw.getId() + ",uuid2="
                + zEndGw.getId());
        throw new InnerErrorServiceException("vpnsite.getTopology.error,VpnGateway do not match site2dc,uuid1="
                + aEndGw.getId() + ",uuid2=" + zEndGw.getId());
    }

    private static List<NeConnection> buildNeConnectionDc(NeConnection res, ConnectionTopology connectionEnds)
            throws ServiceException {
        boolean src = VpnSite.class.isAssignableFrom(res.getSrcSite().getClass());
        BaseSite cpeSite = src ? res.getSrcSite() : res.getDestSite();
        NbiVpnGateway cpeGateway = src ? res.getSrcGateway() : res.getDestGateway();
        BaseSite vpcSite = src ? res.getDestSite() : res.getSrcSite();
        NbiVpnGateway vpcGateway = src ? res.getDestGateway() : res.getSrcGateway();

        NbiServiceConnection connection = res.getParentConnection();
        Template template = res.getSrcTemplate();
        String cpeId = getVpnGatewayCPE(cpeSite, connectionEnds.getSrcRole());
        String vpcId = getVpnGatewayCPE(vpcSite, connectionEnds.getDestRole());

        List<NeConnection> neConnections = new ArrayList<>();

        NeConnection neConnection = new NeConnection(connection);
        neConnection.setSrc(cpeId, connectionEnds.getSrcRole().getRole(), cpeSite, cpeGateway, template);
        neConnection.setDest(vpcId, connectionEnds.getDestRole().getRole(), vpcSite, vpcGateway, template);
        neConnections.add(neConnection);

        if(CpeRole.CLOUDCPE == connectionEnds.getSrcRole()) {
            String localCPEId = getVpnGatewayCPE(cpeSite, CpeRole.LOCALCPE);
            NeConnection internetConnection = new NeConnection(connection);
            internetConnection.setSrc(cpeId, CpeRole.CLOUDCPE.getRole(), cpeSite, cpeGateway, template);
            internetConnection.setDest(localCPEId, CpeRole.LOCALCPE.getRole(), cpeSite, cpeGateway, template);
            neConnections.add(internetConnection);
        }
        return neConnections;
    }

    /**
     * Get vpn gateway neId through vpn site and neRole.<br>
     * 
     * @param site The vpn site
     * @param cpeRole The neRole
     * @return The vpn gateway neId
     * @throws ServiceException when query ne failed or no vpn gateway defined in site
     * @since SDNO 0.5
     */
    private static String getVpnGatewayCPE(BaseSite site, CpeRole cpeRole) throws ServiceException {
        if(site instanceof Vpc) {
            return site.getId();
        }

        VpnSite vpnSite = (VpnSite)site;
        List<NetworkElementMO> vpnGws = new ArrayList<>();
        if(cpeRole == CpeRole.LOCALCPE) {
            vpnGws.addAll(vpnSite.getLocalCpes());
        } else if(cpeRole == CpeRole.CLOUDCPE) {
            vpnGws.addAll(vpnSite.getCloudCpes());
        }

        if(CollectionUtils.isEmpty(vpnGws)) {
            LOGGER.error("Vpnsite.getGateway.error,no vpn gateway defined in site,siteId = " + site.getId());
            throw new InnerErrorServiceException(
                    "Vpnsite.getGateway.error,no vpn gateway defined in site,siteId = " + site.getId());
        }
        return vpnGws.get(0).getId();
    }

    /**
     * Get vpn gateway.<br>
     * 
     * @param gwId The vpn gateway uuid
     * @return The vpn gateway model
     * @throws ServiceException when query vpn gateway failed or vpn gateway not exist
     * @since SDNO 0.5
     */
    private static NbiVpnGateway getVpnGateway(String gwId) throws ServiceException {
        NbiVpnGateway vpnGateway = new BaseDao<NbiVpnGateway>().query(gwId, NbiVpnGateway.class);
        if(vpnGateway == null) {
            LOGGER.error("Vpnsite.getGateway.error,VpnGateway not exist,uuid = " + gwId);
            throw new InnerErrorServiceException("Vpnsite.getGateway.error,VpnGateway not exist,uuid = " + gwId);
        }
        return vpnGateway;
    }

    /**
     * Get site through vpn gateway.<br>
     * 
     * @param gw The vpn gateway
     * @return The queried result,maybe is vpn site or vpc
     * @throws ServiceException when get site failed or vpn gateway don't contains vpcId and siteId
     * @since SDNO 0.5
     */
    private static BaseSite getBaseSite(NbiVpnGateway gw) throws ServiceException {
        if(StringUtils.isNotEmpty(gw.getVpcId())) {
            Vpc vpc = new Vpc();
            vpc.setId(gw.getVpcId());
            vpc.setTenantId(gw.getTenantId());
            return vpc;
        } else if(StringUtils.isNotEmpty(gw.getSiteId())) {
            return VpnSiteUtil.getVpnSite(gw.getSiteId());
        } else {
            LOGGER.error("Vpnsite.getBaseSite.error,can not get site from VpnGateway,gwId = " + gw.getId());
            throw new InnerErrorServiceException(
                    "Vpnsite.getBaseSite.error,can not get site from VpnGateway,gwId = " + gw.getId());
        }
    }
}
