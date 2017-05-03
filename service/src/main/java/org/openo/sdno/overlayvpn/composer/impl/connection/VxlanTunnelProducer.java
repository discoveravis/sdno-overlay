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

package org.openo.sdno.overlayvpn.composer.impl.connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.composer.inf.TunnelProducer;
import org.openo.sdno.overlayvpn.composer.model.ConnectionGroup;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.composer.util.VpnCpeUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnTunnelUtil;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseSite;
import org.openo.sdno.overlayvpn.servicemodel.base.PortVlan;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSubnet;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnVlan;
import org.openo.sdno.overlayvpn.servicemodel.enums.CpeRole;
import org.openo.sdno.overlayvpn.servicemodel.enums.TunnelType;
import org.openo.sdno.overlayvpn.servicemodel.enums.VxlanAccessType;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetVxlanConnection;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateConnection;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The vxlan tunnel producer.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
@Service
public class VxlanTunnelProducer implements TunnelProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(VxlanTunnelProducer.class);

    /**
     * Constructor,register the vxlan tunnel producer to tunnelProducers.<br>
     * 
     * @since SDNO 0.5
     */
    public VxlanTunnelProducer() {
        super();
        ConnectionCommonProducer.registeTunnelProducer(this);
    }

    @Override
    public List<String> getTechnology() {
        return Arrays.asList(TunnelType.VXLAN.getTechnology());
    }

    @Override
    public void buildNetConnection(ConnectionGroup group, NeConnection neConnection,
            TemplateConnection connectionTemplate) throws ServiceException {
        LOGGER.info("Start to compose NeConnection,type = " + getTechnology());

        NetVxlanConnection netVxlanConnection = new NetVxlanConnection();

        ModelUtils.copyAttributes(neConnection, netVxlanConnection);

        netVxlanConnection.setType(TunnelType.getTunnelType(connectionTemplate.getTechnology()).getTechnology());

        String srcInterface = VpnCpeUtil.getCpeInterface(neConnection.getSrcTemplate(), neConnection.getSrcNeId(),
                connectionTemplate.getSaps().get(0).getInterFace());
        String destInterface = VpnCpeUtil.getCpeInterface(neConnection.getDestTemplate(), neConnection.getDestNeId(),
                connectionTemplate.getSaps().get(1).getInterFace());

        netVxlanConnection.setSrcPortName(srcInterface);
        netVxlanConnection.setDestPortName(destInterface);

        Map<String, Set<PortVlan>> vniPortVlan = getVniPortVlan(neConnection, group.getNeConnections(),
                VxlanAccessType.getAccessType(connectionTemplate.getAccessType()));
        for(Entry<String, Set<PortVlan>> entry : vniPortVlan.entrySet()) {
            String vni = entry.getKey();
            Set<PortVlan> portVlan = entry.getValue();
            NetVxlanConnection vxlanTunnel = new NetVxlanConnection();
            ModelUtils.copyAttributes(netVxlanConnection, vxlanTunnel);
            vxlanTunnel.allocateUuid();
            vxlanTunnel.setVni(vni);
            vxlanTunnel.setPortVlanList(JsonUtils.toJson(portVlan));

            NbiNetConnection dbVxlan = VpnTunnelUtil.checkNetConnection(group, vxlanTunnel);
            netVxlanConnection.setName(UuidUtils.createBase64Uuid());

            group.append(processConnection(neConnection, vxlanTunnel, dbVxlan));
            LOGGER.info("Finish composed NeConnection,type=" + getTechnology() + ",uuid=" + vxlanTunnel.getId());
        }
    }

    @Override
    public void buildDeleteNetConnection(ConnectionGroup connections, NeConnection neConnection,
            NbiNetConnection netConnection) throws ServiceException {
        // TODO Auto-generated method stub

    }

    private Map<String, Set<PortVlan>> getVniPortVlan(NeConnection neConnection, List<NeConnection> neConnections,
            VxlanAccessType accessType) throws ServiceException {
        Map<String, Set<PortVlan>> vniMap = new HashMap<String, Set<PortVlan>>();
        Object gwVni = neConnection.getExtAttrs().get(NeConnection.KEY_VNI);
        if(gwVni != null && !"".equals(gwVni)) {
            Set<PortVlan> portVlans = new HashSet<PortVlan>();
            if(CpeRole.LOCALCPE.getRole().equalsIgnoreCase(neConnection.getSrcNeRole())) {
                LOGGER.info("Query vni and PortVlan from Vpn,neId = " + neConnection.getSrcNeId());
                portVlans.addAll(getPortVlan(neConnection.getSrcNeId(), neConnection.getSrcGateway(),
                        neConnection.getSrcSite()));
            }
            if(CpeRole.LOCALCPE.getRole().equalsIgnoreCase(neConnection.getDestNeRole())) {
                LOGGER.info("Query vni and PortVlan from Vpn,neId = " + neConnection.getDestNeId());
                portVlans.addAll(getPortVlan(neConnection.getDestNeId(), neConnection.getDestGateway(),
                        neConnection.getDestSite()));
            }
            vniMap.put(String.valueOf(gwVni), portVlans);
            LOGGER.info("Query vni and PortVlan from Vpn,data = " + JsonUtils.toJson(vniMap));
            return vniMap;
        }
        if(neConnection.getSrcSite().getId().equals(neConnection.getDestSite().getId())) {
            vniMap.putAll(getVniPortVlan(neConnection, neConnections, neConnection.getSrcSite(), accessType));
        } else {
            Map<String, Set<PortVlan>> mergePortVlan =
                    mergePortVlan(getVniPortVlan(neConnection, neConnections, neConnection.getSrcSite(), accessType),
                            getVniPortVlan(neConnection, neConnections, neConnection.getDestSite(), accessType));
            vniMap.putAll(mergePortVlan);
        }
        if(MapUtils.isEmpty(vniMap)) {
            LOGGER.error("vpnconnection.getvni,error,vni is empty,netConnection uuid = " + neConnection.getId());
            throw new InnerErrorServiceException(
                    "vpnconnection.getvni,error,vni is empty,netConnection uuid = " + neConnection.getId());
        }
        LOGGER.info("Query vni and PortVlan from site,data = " + JsonUtils.toJson(vniMap));
        return vniMap;
    }

    private ConnectionGroup processConnection(NeConnection neConnection, NbiNetConnection connection,
            NbiNetConnection dbConnection) {
        LOGGER.info("Start to check NetConnection,type=" + getTechnology() + ",uuid=" + connection.getId());
        ConnectionGroup group = new ConnectionGroup();
        if(dbConnection == null) {
            LOGGER.info("NetConnection is new,type=" + getTechnology() + ",uuid=" + connection.getId());
            group.getCreate().add(connection);
        } else {
            LOGGER.info("NetConnection is exist,type=" + getTechnology() + ",uuid=" + dbConnection.getId());
            group.getRelation()
                    .addAll(VpnTunnelUtil.buildConnectionRelation(neConnection, Arrays.asList(dbConnection)));
        }
        return group;
    }

    private Map<String, Set<PortVlan>> mergePortVlan(Map<String, Set<PortVlan>> srcMap,
            Map<String, Set<PortVlan>> destMap) {
        Map<String, Set<PortVlan>> result = new HashMap<String, Set<PortVlan>>();
        result.putAll(srcMap);
        for(Entry<String, Set<PortVlan>> entry : destMap.entrySet()) {
            if(result.containsKey(entry.getKey())) {
                result.get(entry.getKey()).addAll(entry.getValue());
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * Get portVlan.<br>
     * 
     * @param neId The ne uuid
     * @param gateway The vpn gateway
     * @param site The site
     * @return The portVlan set
     * @since SDNO 0.5
     */
    private Set<PortVlan> getPortVlan(String neId, NbiVpnGateway gateway, BaseSite site) {
        Set<PortVlan> portVlans = new HashSet<PortVlan>();
        if(!VpnSite.class.isAssignableFrom(site.getClass())) {
            return portVlans;
        }
        VpnSite vpnSite = (VpnSite)site;
        for(String portId : gateway.getPorts()) {
            PortVlan portVlan = new PortVlan();
            portVlan.setNeId(neId);
            portVlan.setPort(portId);

            int downVlanId = Integer.parseInt(vpnSite.getVlans().get(0).getVlanId());
            int upVlanId = downVlanId + 1;
            StringBuffer vlan = new StringBuffer();
            vlan.append(String.valueOf(downVlanId)).append('-').append(String.valueOf(upVlanId));
            portVlan.setVlan(vlan.toString());

            portVlans.add(portVlan);
        }
        return portVlans;
    }

    /**
     * Get VNI portVlan.<br>
     * 
     * @param neConnection The ne connection
     * @param neConnections The ne connection list
     * @param site The site
     * @param accessType The accessType value
     * @return The map of VNI portVlan,key is vni,value is portVlan set
     * @since SDNO 0.5
     */
    private Map<String, Set<PortVlan>> getVniPortVlan(NeConnection neConnection, List<NeConnection> neConnections,
            BaseSite site, VxlanAccessType accessType) {
        Map<String, Set<PortVlan>> vniMap = new HashMap<String, Set<PortVlan>>();
        if(!VpnSite.class.isAssignableFrom(site.getClass())) {
            return vniMap;
        }
        VpnSite vpnSite = (VpnSite)site;

        boolean isCommon = neConnection == null || vpnSite.getInternetGateway() == null
                || CollectionUtils.isEmpty(vpnSite.getInternetGateway().getSourceSubnets());
        boolean isS2D = isSite2Dc(neConnections);
        List<String> sourceSubnets =
                (vpnSite.getInternetGateway() != null && vpnSite.getInternetGateway().getSourceSubnets() != null)
                        ? vpnSite.getInternetGateway().getSourceSubnets() : new ArrayList<String>();
        for(VpnSubnet subnet : vpnSite.getSubnets()) {
            if(!site.getId().equals(subnet.getSiteId()) || StringUtils.isEmpty(subnet.getVni())
                    || !(isCommon || (isS2D && !sourceSubnets.contains(subnet.getId())))) {
                continue;
            }
            if(CollectionUtils.isEmpty(subnet.getPorts())) {
                vniMap.put(subnet.getVni(), getPortVlan(vpnSite, subnet.getVlanId(), vpnSite.getVlans(), accessType));
            } else {
                vniMap.put(subnet.getVni(), getPortVlan(vpnSite, subnet));
            }
        }
        return vniMap;
    }

    private static boolean isSite2Dc(List<NeConnection> neConnections) {
        if(CollectionUtils.isEmpty(neConnections)) {
            return false;
        }
        for(NeConnection connection : neConnections) {
            if(CpeRole.VPC.getRole().equalsIgnoreCase(connection.getSrcNeRole())
                    || CpeRole.VPC.getRole().equalsIgnoreCase(connection.getDestNeRole())) {
                return true;
            }
        }
        return false;
    }

    private static Set<PortVlan> getPortVlan(VpnSite vpnSite, String vlanId, List<VpnVlan> vlanList,
            VxlanAccessType accessType) {
        String neId = CollectionUtils.isNotEmpty(vpnSite.getLocalCpes()) ? vpnSite.getLocalCpes().get(0).getId() : null;
        Set<PortVlan> portVlans = new HashSet<PortVlan>();
        if(StringUtils.isEmpty(neId) || StringUtils.isEmpty(vlanId)) {
            return portVlans;
        }
        if(VxlanAccessType.VLAN == accessType) {
            PortVlan portVlan = new PortVlan();
            portVlan.setNeId(neId);
            portVlan.setVlan(String.valueOf(vlanId));
            portVlans.add(portVlan);
            return portVlans;
        }
        for(VpnVlan vlan : vlanList) {
            if(CollectionUtils.isEmpty(vlan.getPorts())) {
                continue;
            }
            if(vlan.getSiteId().equals(vpnSite.getId()) && vlanId.equals(vlan.getVlanId())) {
                for(String portId : vlan.getPorts()) {
                    PortVlan portVlan = new PortVlan();
                    portVlan.setNeId(neId);
                    portVlan.setPort(portId);
                    if(VxlanAccessType.PORTVLAN == accessType) {
                        portVlan.setVlan(String.valueOf(vlanId));
                    }
                    portVlans.add(portVlan);
                }
            }
        }
        return portVlans;
    }

    private static Set<PortVlan> getPortVlan(VpnSite vpnSite, VpnSubnet subnet) {
        String neId = CollectionUtils.isNotEmpty(vpnSite.getLocalCpes()) ? vpnSite.getLocalCpes().get(0).getId() : null;
        Set<PortVlan> portVlans = new HashSet<PortVlan>();
        if(StringUtils.isEmpty(neId) || CollectionUtils.isEmpty(subnet.getPorts())) {
            return portVlans;
        }
        for(String portId : subnet.getPorts()) {
            PortVlan portVlan = new PortVlan();
            portVlan.setNeId(neId);
            portVlan.setPort(portId);
            portVlans.add(portVlan);
        }
        return portVlans;
    }
}
