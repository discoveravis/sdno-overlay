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
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseSite;
import org.openo.sdno.overlayvpn.servicemodel.base.IP;
import org.openo.sdno.overlayvpn.servicemodel.base.Vpc;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSubnet;
import org.openo.sdno.overlayvpn.servicemodel.enums.CpeRole;
import org.openo.sdno.overlayvpn.site2dc.sbi.impl.VpnSiteSbiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class that deal with vpn site.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 23, 2017
 */
public class VpnSiteUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnSiteUtil.class);

    private VpnSiteUtil() {
        super();
    }

    /**
     * Get vpn site through uuid.<br>
     * 
     * @param uuid The vpn site uuid
     * @return The vpn site model
     * @throws ServiceException when query failed or the query result is null
     * @since SDNO 0.5
     */
    public static VpnSite getVpnSite(String uuid) throws ServiceException {
        VpnSite site = VpnSiteSbiService.query(uuid);
        if(site == null) {
            LOGGER.error("The site is not exist,uuid = " + uuid);
            throw new InnerErrorServiceException("Vpnsite.getsite.error,vpn site is not exist,uuid=" + uuid);
        }
        site.setId(uuid);
        return site;
    }

    /**
     * Get site cidrs.<br>
     * 
     * @param site The site model
     * @return The IP list that is cidrs
     * @since SDNO 0.5
     */
    public static List<IP> getSiteCidrs(BaseSite site) {
        List<IP> ips = new ArrayList<>();
        if(site instanceof Vpc) {
            LOGGER.info("VPC site all cidrs added,uuid=" + site.getId());
        } else if(site instanceof VpnSite) {
            VpnSite vpnSite = (VpnSite)site;
            List<VpnSubnet> subnets = vpnSite.getSubnets();
            if(CollectionUtils.isNotEmpty(subnets)) {
                for(VpnSubnet subnet : subnets) {
                    IP ip = IP.buildIPv4(subnet.getCidrBlock());
                    ips.add(ip);
                }
            }
        }
        return ips;
    }

    /**
     * Get site cidrs.<br>
     * 
     * @param connection The ne connection
     * @param site The site model
     * @return The IP list that is cidrs
     * @since SDNO 0.5
     */
    public static List<IP> getSiteCidrs(NeConnection connection, BaseSite site) {
        List<IP> ips = new ArrayList<>();
        if(site instanceof Vpc) {
            LOGGER.info("VPC site all cidrs added,uuid=" + site.getId());
            return ips;
        }
        VpnSite vpnSite = (VpnSite)site;
        boolean isCommon = connection == null || vpnSite.getInternetGateway() == null
                || CollectionUtils.isEmpty(vpnSite.getInternetGateway().getSourceSubnets());
        if(isCommon) {
            return getSiteCidrs(site);
        }
        boolean isS2D = CpeRole.VPC.getRole().equalsIgnoreCase(connection.getSrcNeRole())
                || CpeRole.VPC.getRole().equalsIgnoreCase(connection.getDestNeRole());
        List<String> sourceSubnets = vpnSite.getInternetGateway().getSourceSubnets();
        List<VpnSubnet> subnets = vpnSite.getSubnets();
        if(CollectionUtils.isNotEmpty(subnets)) {
            for(VpnSubnet subnet : subnets) {
                if(isS2D && !sourceSubnets.contains(subnet.getId())) {
                    IP ip = IP.buildIPv4(subnet.getCidrBlock());
                    ips.add(ip);
                }
            }
        }

        return ips;
    }
}
