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

package org.openo.sdno.overlayvpn.composer.impl.route;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.composer.VpnModelConverter;
import org.openo.sdno.overlayvpn.composer.inf.RouteProducer;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.composer.model.RouteGroup;
import org.openo.sdno.overlayvpn.composer.util.VpnSiteUtil;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.servicemodel.base.IP;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSubnet;
import org.openo.sdno.overlayvpn.servicemodel.enums.CpeRole;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetRoute;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetVxlanConnection;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The route producer between localCpe and cloudCpe.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 11, 2017
 */
@Service
public class EntL2InternetRouteProducer implements RouteProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntL2InternetRouteProducer.class);

    /**
     * Constructor,register the route producer between localCpe and cloudCpe to routeProducers.<br>
     * 
     * @since SDNO 0.5
     */
    public EntL2InternetRouteProducer() {
        super();
        VpnModelConverter.registerRouteProducer(ParamConsts.ROUTE_ENT_L2_INTERNET, this);
    }

    @Override
    public void buildNetRoute(RouteGroup routes, NbiNetConnection netConnection, NeConnection neConnection)
            throws ServiceException {
        LOGGER.info("Start to build route for connection,type=" + netConnection.getType() + ",uuid="
                + netConnection.getId());
        CpeRole srcRole = CpeRole.getCpeRole(neConnection.getSrcNeRole());
        CpeRole destRole = CpeRole.getCpeRole(neConnection.getDestNeRole());

        if(netConnection instanceof NetVxlanConnection) {
            NetVxlanConnection vxlanConnection = (NetVxlanConnection)netConnection;

            if((CpeRole.CLOUDCPE == srcRole && CpeRole.LOCALCPE == destRole)
                    || (CpeRole.LOCALCPE == srcRole && CpeRole.CLOUDCPE == destRole)) {
                List<IP> subnets = VpnSiteUtil.getSiteCidrs(neConnection.getDestSite());
                String vbdIf = getVbdIf(neConnection, vxlanConnection);
                for(IP ip : subnets) {
                    NetRoute routeToSite = NetRoute.buildStaticRoute(netConnection, ip, null, vbdIf, null, false);
                    routeToSite.setSrcNeId(getCpe(netConnection, CpeRole.CLOUDCPE));
                    routeToSite.setSrcNeRole(CpeRole.CLOUDCPE.getRole());
                    routes.getCreate().add(routeToSite);
                }
            }
        }

        LOGGER.info("Finish build route for connection,routes=" + JsonUtils.toJson(routes));
    }

    private String getVbdIf(NeConnection neConnection, NetVxlanConnection vxlanConnection) throws ServiceException {
        VpnSite site = (VpnSite)neConnection.getSrcSite();
        List<VpnSubnet> subnets = site.getSubnets();
        if(CollectionUtils.isEmpty(subnets)) {
            LOGGER.warn("Get vbdif failed,subnet is empty,siteId=" + site.getId());
            return null;
        }
        for(VpnSubnet subnet : subnets) {
            if(vxlanConnection.getVni().equals(subnet.getVni())) {
                String gwIntf = subnet.getGatewayInterface();
                LOGGER.info("Get vbdIf finish,siteId=" + site.getId() + ",intf=" + gwIntf);
                if(StringUtils.isEmpty(gwIntf)) {
                    LOGGER.warn("Get vbdIf from subnet failed,generate vbdif,siteId=" + site.getId());
                    gwIntf = "vbdif" + vxlanConnection.getVni();
                }
                return gwIntf;
            }
        }
        LOGGER.warn("Get vbdIf failed,no match vni,siteId=" + site.getId());
        return null;
    }

    /**
     * Get the connection neId that the ne role matches the role.<br>
     * 
     * @param connection The vpn connection
     * @param role The cpeRole
     * @return The connection neId that the ne role matches the role
     * @since SDNO 0.5
     */
    private String getCpe(NbiNetConnection connection, CpeRole role) {
        if(role == CpeRole.getCpeRole(connection.getSrcNeRole())) {
            return connection.getSrcNeId();
        } else if(role == CpeRole.getCpeRole(connection.getDestNeRole())) {
            return connection.getDestNeId();
        } else {
            return null;
        }
    }

}
