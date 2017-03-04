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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.composer.inf.TunnelProducer;
import org.openo.sdno.overlayvpn.composer.model.ConnectionGroup;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.composer.util.VpnCpeUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnReliabilityUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnSiteUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnTunnelUtil;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.servicemodel.base.IP;
import org.openo.sdno.overlayvpn.servicemodel.enums.ActionType;
import org.openo.sdno.overlayvpn.servicemodel.enums.CpeRole;
import org.openo.sdno.overlayvpn.servicemodel.enums.Topology;
import org.openo.sdno.overlayvpn.servicemodel.enums.TunnelType;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetIpsecConnection;
import org.openo.sdno.overlayvpn.servicemodel.template.Sap;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateConnection;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.overlayvpn.util.toolkit.IpAddressUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The ipsec tunnel producer.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
@Service
public class IpsecTunnelProducer implements TunnelProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpsecTunnelProducer.class);

    /**
     * Constructor,register the ipsec tunnel producer to tunnelProducers.<br>
     * 
     * @since SDNO 0.5
     */
    public IpsecTunnelProducer() {
        super();
        ConnectionCommonProducer.registeTunnelProducer(this);
    }

    @Override
    public List<String> getTechnology() {
        return Arrays.asList(TunnelType.IPSEC.getTechnology());
    }

    @Override
    public void buildNetConnection(ConnectionGroup group, NeConnection neConnection,
            TemplateConnection connectionTemplate) throws ServiceException {
        LOGGER.info("Start to composed NeConnection, type = " + getTechnology());
        NetIpsecConnection netIpsecConnection = new NetIpsecConnection();

        ModelUtils.copyAttributes(neConnection, netIpsecConnection);
        netIpsecConnection.setId(UuidUtils.createUuid());

        netIpsecConnection.setType(TunnelType.getTunnelType(connectionTemplate.getTechnology()).getTechnology());

        netIpsecConnection.setProtectionPolicy(connectionTemplate.getProtect());

        String srcInterface = VpnCpeUtil.getCpeInterface(neConnection.getSrcTemplate(), neConnection.getSrcNeId(),
                connectionTemplate.getSaps().get(0).getInterFace());
        String destInterface = VpnCpeUtil.getCpeInterface(neConnection.getDestTemplate(), neConnection.getDestNeId(),
                connectionTemplate.getSaps().get(1).getInterFace());

        netIpsecConnection.setSrcPortName(srcInterface);
        netIpsecConnection.setDestPortName(destInterface);

        netIpsecConnection.setWorkType(connectionTemplate.getWorkType());

        if(neConnection.getExtAttrs().containsKey(NeConnection.KEY_REGIONID)) {
            netIpsecConnection.setRegionId(String.valueOf(neConnection.getExtAttrs().get(NeConnection.KEY_REGIONID)));
        }

        String ipsecPolicy = JsonUtils.toJson(neConnection.getSrcTemplate().getIpsecPolicy());
        netIpsecConnection.setIpsecPolicy(ipsecPolicy);

        netIpsecConnection.setIkePolicy(JsonUtils.toJson(neConnection.getSrcTemplate().getIkePolicy()));

        initTemplateType(connectionTemplate, netIpsecConnection);

        String srcIngress = connectionTemplate.getSaps().get(0).getIngress();
        String destIngress = connectionTemplate.getSaps().get(1).getIngress();
        if(StringUtils.isNotEmpty(srcIngress)) {
            String srcIngressInterface =
                    VpnCpeUtil.getCpeInterface(neConnection.getSrcTemplate(), neConnection.getSrcNeId(), srcIngress);
            netIpsecConnection.setRuleSrcPortName(srcIngressInterface);
        }
        if(StringUtils.isNotEmpty(destIngress)) {
            String destIngressInterface =
                    VpnCpeUtil.getCpeInterface(neConnection.getDestTemplate(), neConnection.getDestNeId(), destIngress);
            netIpsecConnection.setRuleDestPortName(destIngressInterface);
        }

        List<IP> srcCidrs = VpnSiteUtil.getSiteCidrs(neConnection, neConnection.getSrcSite());
        if(CollectionUtils.isNotEmpty(srcCidrs)) {
            netIpsecConnection.setSourceLanCidrs(JsonUtils.toJson(srcCidrs));
        }

        List<IP> destCidrs = VpnSiteUtil.getSiteCidrs(neConnection, neConnection.getDestSite());
        if(CollectionUtils.isNotEmpty(destCidrs)) {
            netIpsecConnection.setDestLanCidrs(JsonUtils.toJson(destCidrs));
        }

        netIpsecConnection.setName(UuidUtils.createBase64Uuid());

        LOGGER.info("The ipsec connection is :" + JsonUtils.toJson(netIpsecConnection));

        NbiNetConnection dbConnection = VpnTunnelUtil.checkNetConnection(group, netIpsecConnection);
        ConnectionGroup processConnection = processConnection(neConnection, netIpsecConnection, dbConnection);
        initNqa(processConnection.getCreate(), neConnection, connectionTemplate);
        group.append(processConnection);
        LOGGER.info("Finish composed Neconnection,type=" + getTechnology() + ",uuid=" + netIpsecConnection.getId());
    }

    @Override
    public void buildDeleteNetConnection(ConnectionGroup group, NeConnection neConnection,
            NbiNetConnection netConnection) throws ServiceException {
        NetIpsecConnection dbIpsecConnection =
                new BaseDao<NetIpsecConnection>().query(netConnection.getId(), NetIpsecConnection.class);
        if(dbIpsecConnection == null || !isSiteToDc(dbIpsecConnection)) {
            return;
        }
        List<IP> srcDbIps = IpAddressUtil.jsonToIP(dbIpsecConnection.getSourceLanCidrs());
        List<IP> srcIps = VpnSiteUtil.getSiteCidrs(neConnection, neConnection.getSrcSite());
        if(isNeedToUpdate(srcDbIps, srcIps)) {
            NetIpsecConnection updateConnection = new NetIpsecConnection();
            updateConnection.setUpdateAction(ActionType.DELETE);
            updateConnection.setSourceLanCidrs(JsonUtils.toJson(srcIps));
            copyIpsecData(updateConnection, dbIpsecConnection);
            group.getUpdate().add(updateConnection);
        }
    }

    private ConnectionGroup processConnection(NeConnection neConnection, NetIpsecConnection netIpsecConnection,
            NbiNetConnection dbConnection) throws ServiceException {
        LOGGER.info("Start to check netConnection,type=" + getTechnology() + ",uuid=" + netIpsecConnection.getId());
        ConnectionGroup group = new ConnectionGroup();
        if(dbConnection == null) {
            LOGGER.info("NetConnection is new,type=" + getTechnology() + ",uuid=" + netIpsecConnection.getId());
            group.getCreate().add(netIpsecConnection);
        } else {
            LOGGER.info("NetConnection is exist,type=" + getTechnology() + ",uuid=" + dbConnection.getId());
            group.getRelation()
                    .addAll(VpnTunnelUtil.buildConnectionRelation(neConnection, Arrays.asList(dbConnection)));
            if(isSiteToDc(netIpsecConnection)) {
                NetIpsecConnection dbIpsecConnection = (NetIpsecConnection)dbConnection;
                List<IP> srcIps = IpAddressUtil.jsonToIP(netIpsecConnection.getSourceLanCidrs());
                List<IP> srcDbIps = IpAddressUtil.jsonToIP(dbIpsecConnection.getSourceLanCidrs());
                List<IP> srcNewIps = checkIps(srcDbIps, srcIps);
                if(CollectionUtils.isNotEmpty(srcNewIps)) {
                    NetIpsecConnection updateConnection = new NetIpsecConnection();
                    updateConnection.setUpdateAction(ActionType.CREATE);
                    updateConnection.setSourceLanCidrs(JsonUtils.toJson(srcNewIps));
                    copyIpsecData(updateConnection, dbIpsecConnection);
                    group.getUpdate().add(updateConnection);
                }
            }
        }
        return group;
    }

    private void initNqa(List<NbiNetConnection> netConnections, NeConnection neConnection,
            TemplateConnection connectionTemplate) throws ServiceException {
        if(CollectionUtils.isEmpty(netConnections)) {
            return;
        }
        for(NbiNetConnection netConnection : netConnections) {
            if(!NetIpsecConnection.class.isAssignableFrom(netConnection.getClass())) {
                continue;
            }
            NetIpsecConnection ipsec = (NetIpsecConnection)netConnection;
            if(StringUtils.isNotEmpty(ipsec.getNqa())) {
                continue;
            }
            String nqa = VpnReliabilityUtil.createNqa(ipsec, neConnection, connectionTemplate);
            if(StringUtils.isNotEmpty(nqa)) {
                ipsec.setNqa(nqa);
            }
        }
    }

    private void copyIpsecData(NetIpsecConnection updateConnection, NetIpsecConnection dbConnection) {
        updateConnection.setId(dbConnection.getId());
        updateConnection.setType(dbConnection.getType());
    }

    /**
     * Whether the connection is site to DC.<br>
     * 
     * @param ipsecConnection The ipsec connection
     * @return true when the connection is site to DC,otherwise is false
     * @since SDNO 0.5
     */
    private boolean isSiteToDc(NetIpsecConnection ipsecConnection) {
        CpeRole srcRole = CpeRole.getCpeRole(ipsecConnection.getSrcNeRole());
        CpeRole destRole = CpeRole.getCpeRole(ipsecConnection.getDestNeRole());
        return CpeRole.VPC == srcRole || CpeRole.VPC == destRole;
    }

    /**
     * Whether need to update ipsec connection.<br>
     * 
     * @param dbIps The IP list in DB
     * @param siteIps The IP list in ipsec connection
     * @return true when siteIps contains IP in dbIps,otherwise is false
     * @since SDNO 0.5
     */
    private boolean isNeedToUpdate(List<IP> dbIps, List<IP> siteIps) {
        boolean update = false;
        if(CollectionUtils.isEmpty(dbIps) || CollectionUtils.isEmpty(siteIps)) {
            return update;
        }
        Set<String> ipKey = new HashSet<String>();
        for(IP ip : siteIps) {
            ipKey.add(ip.toString());
        }
        Iterator<IP> iterator = dbIps.iterator();
        while(iterator.hasNext()) {
            IP dbIp = iterator.next();
            if(ipKey.contains(dbIp.toString())) {
                iterator.remove();
                update = true;
            }
        }
        return update;
    }

    /**
     * Check IP list,find all the request IP that not in DB.<br>
     * 
     * @param dbIps The IP list in DB
     * @param requestIps The request IP list
     * @return All the request IP that not in DB
     * @since SDNO 0.5
     */
    private List<IP> checkIps(List<IP> dbIps, List<IP> requestIps) {
        List<IP> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(requestIps)) {
            return result;
        } else if(CollectionUtils.isEmpty(dbIps)) {
            return requestIps;
        }
        Set<String> srcIpKey = new HashSet<String>();
        for(IP ip : dbIps) {
            srcIpKey.add(ip.toString());
        }
        for(IP ip : requestIps) {
            if(!srcIpKey.contains(ip.toString())) {
                result.add(ip);
            }
        }
        return result;
    }

    /**
     * Initialize template type.<br>
     * 
     * @param connectionTemplate The connection template
     * @param netIpsecConnection The ipsec connection
     * @since SDNO 0.5
     */
    private void initTemplateType(TemplateConnection connectionTemplate, NetIpsecConnection netIpsecConnection) {
        netIpsecConnection.setSrcIsTemplateType("false");
        netIpsecConnection.setDestIsTemplateType("false");
        if(Topology.HUB_SPOKER == Topology.getTopology(connectionTemplate.getTopology())) {
            List<Sap> saps = connectionTemplate.getSaps();
            for(Sap sap : saps) {
                if(netIpsecConnection.getSrcNeRole().equalsIgnoreCase(sap.getType())
                        && "hub".equalsIgnoreCase(sap.getRole())) {
                    netIpsecConnection.setSrcIsTemplateType("true");
                }
                if(netIpsecConnection.getDestNeRole().equalsIgnoreCase(sap.getType())
                        && "hub".equalsIgnoreCase(sap.getRole())) {
                    netIpsecConnection.setDestIsTemplateType("true");
                }
            }
        }
    }
}
