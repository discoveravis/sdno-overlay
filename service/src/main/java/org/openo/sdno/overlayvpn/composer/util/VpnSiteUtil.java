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
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseSite;
import org.openo.sdno.overlayvpn.servicemodel.base.IP;
import org.openo.sdno.overlayvpn.servicemodel.base.Vpc;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSubnet;
import org.openo.sdno.overlayvpn.servicemodel.enums.CpeRole;
import org.openo.sdno.overlayvpn.site2dc.sbi.impl.VpnSiteSbiService;
import org.openo.sdno.overlayvpn.util.rsp.ResponseUtils;
import org.openo.sdno.rest.RequestUtils;
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

    public static final String QUERY_SUBNET_URL = "/openoapi/sdnovpc/v1/subnets";

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
     * @throws ServiceException when query subnet failed
     * @since SDNO 0.5
     */
    public static List<IP> getSiteCidrs(NeConnection connection, BaseSite site) throws ServiceException {
        List<IP> ips = new ArrayList<>();
        if(site instanceof Vpc) {
            if(connection.getSrcNeId().equals(site.getId())
                    && CpeRole.VPC.getRole().equals(connection.getSrcNeRole())) {
                Subnet subnet = querySubnetById(connection.getSrcGateway().getSubnets().get(0));
                ips.add(IP.buildIPv4(subnet.getCidr()));
            } else if(connection.getDestNeId().equals(site.getId())
                    && CpeRole.VPC.getRole().equals(connection.getDestNeRole())) {
                Subnet subnet = querySubnetById(connection.getDestGateway().getSubnets().get(0));
                ips.add(IP.buildIPv4(subnet.getCidr()));
            }
            LOGGER.info("VPC site all cidrs added,uuid=" + site.getId());
        } else {
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
                        ips.add(IP.buildIPv4(subnet.getCidrBlock()));
                    }
                }
            }
        }
        return ips;
    }

    /**
     * Query subnets by vpc id.<br>
     * 
     * @param vpcId Vpc Id
     * @return List of subnets queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    private static Subnet querySubnetById(String subnetId) throws ServiceException {
        if(StringUtils.isEmpty(subnetId)) {
            LOGGER.error("Query subnet fail, subnetId is invalid");
            throw new ServiceException("Query subnet fail, subnetId is invalid");
        }

        String url = new StringBuilder(QUERY_SUBNET_URL).append('/').append(subnetId).toString();
        RestfulParametes restfulParametes = RequestUtils.constructRestParameters(null);

        LOGGER.info("Do query :" + url + ": Site :" + restfulParametes.getRawData());
        RestfulResponse response = RestfulProxy.get(url, restfulParametes);
        LOGGER.info("Do query : status=" + response.getStatus() + ",response=" + response.getResponseContent());

        ResponseUtils.checkResponseAndThrowException(response);
        return JsonUtil.fromJson(response.getResponseContent(), Subnet.class);
    }
}
