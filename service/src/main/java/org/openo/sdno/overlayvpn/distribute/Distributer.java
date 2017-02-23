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

package org.openo.sdno.overlayvpn.distribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.VpnModelConverter;
import org.openo.sdno.overlayvpn.composer.impl.connection.ConnectionCommonProducer;
import org.openo.sdno.overlayvpn.composer.inf.TunnelProducer;
import org.openo.sdno.overlayvpn.composer.model.ConnectionGroup;
import org.openo.sdno.overlayvpn.composer.model.ConnectionPackage;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.composer.model.RouteGroup;
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.composer.util.SiteConnectionUtil;
import org.openo.sdno.overlayvpn.composer.util.SiteModelConverter;
import org.openo.sdno.overlayvpn.composer.util.VpnConnectionUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnPolicyRouteUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnRelationUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnReliabilityUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnRouteUtil;
import org.openo.sdno.overlayvpn.composer.util.VpnTunnelUtil;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiConnectionRelation;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseRoute;
import org.openo.sdno.overlayvpn.servicemodel.enums.CpeRole;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;
import org.openo.sdno.overlayvpn.servicemodel.enums.RouteType;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetIpsecConnection;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetRoute;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetVxlanConnection;
import org.openo.sdno.overlayvpn.servicemodel.sbi.PolicyRoute;
import org.openo.sdno.overlayvpn.site2dc.sbi.impl.IpsecSbiService;
import org.openo.sdno.overlayvpn.site2dc.sbi.impl.PolicyRouteSbiService;
import org.openo.sdno.overlayvpn.site2dc.sbi.impl.RouteSbiService;
import org.openo.sdno.overlayvpn.site2dc.sbi.impl.VxlanSbiService;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Distribute the connections to SBI through topology ,technology and template.<br>
 * 
 * @param <T> The connection type,connections contain internal vpn connection and vpn connection
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
@Service("ConnectionDistributer")
public class Distributer<T extends NbiServiceConnection> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Distributer.class);

    private BaseDao<NbiConnectionRelation> relationDao = new BaseDao<NbiConnectionRelation>();

    private BaseDao<T> vpnDao = new BaseDao<T>();

    private IpsecSbiService ipsecSbiService = new IpsecSbiService();

    private VxlanSbiService vxlanSbiService = new VxlanSbiService();

    private PolicyRouteSbiService policyRouteSbiService = new PolicyRouteSbiService();

    private RouteSbiService routeSbiService = new RouteSbiService();

    /**
     * Do distributing when creating connection.<br>
     * 
     * @param connection The NBI connection
     * @throws ServiceException when doing distributing failed
     * @since SDNO 0.5
     */
    public void create(T connection) throws ServiceException {
        LOGGER.info("Start creating the connection.");
        ConnectionGroup connectionGroup = VpnModelConverter.convertConnection(connection);

        List<NbiNetConnection> connectionsCreate = connectionGroup.getCreate();
        List<NbiConnectionRelation> relations = connectionGroup.getRelation();

        relationDao.insert(VpnRelationUtil.processRelations(relations));

        LOGGER.info("Split connection through the technology.");
        List<ConnectionPackage> connectionPackages = VpnConnectionUtil.splitConnections(connection, connectionsCreate);

        LOGGER.info("Create connections.");
        createConnections(connectionPackages);

        updateConnections(connectionGroup);

        if(isDeployed(connection)) {
            LOGGER.info("Deploy the connection.");
            deploy(connection, connectionGroup);
        }
    }

    /**
     * Do distributing when deleting connection.<br>
     * 
     * @param uuid The uuid of the connection
     * @param resType The resource type of the connection
     * @throws ServiceException when doing distributing failed
     * @since SDNO 0.5
     */
    public void delete(String uuid, Class<T> resType) throws ServiceException {
        T connection = vpnDao.query(uuid, resType);
        LOGGER.info("Query connections success,the result is:" + JsonUtils.toJson(connection));

        List<ConnectionPackage> removeConnections = VpnRelationUtil.queryRemovableConnections(connection, null);

        undeployRoutes(connection, removeConnections);

        deleteConnections(removeConnections);

        processDeleteUpdate(uuid, resType, connection);
        VpnReliabilityUtil.deleteNqa(uuid);

    }

    /**
     * Do distributing when deploying connection.<br>
     * 
     * @param uuid The uuid of the connection
     * @param resType The resource type of the connection
     * @throws ServiceException when doing distributing failed
     * @since SDNO 0.5
     */
    public void deploy(String uuid, Class<T> resType) throws ServiceException {
        T connection = vpnDao.query(uuid, resType);
        LOGGER.info("Query connection result is :" + JsonUtils.toJson(connection));
        deploy(connection, null);
    }

    /**
     * Do distributing when undeploying connection.<br>
     * 
     * @param uuid The uuid of the connection
     * @param resType The resource type of the connection
     * @throws ServiceException when doing distributing failed
     * @since SDNO 0.5
     */
    public void undeploy(String uuid, Class<T> resType) throws ServiceException {
        T connection = vpnDao.query(uuid, resType);
        List<ConnectionPackage> undeployConnections =
                VpnRelationUtil.queryRemovableConnections(connection, DeployState.UNDEPLOY);
        undeployRoutes(connection, undeployConnections);

        undeployConnections(undeployConnections);
    }

    private boolean isDeployed(T model) {
        if(model == null) {
            return false;
        }
        return DeployState.DEPLOY.getState().equalsIgnoreCase(model.getDeployStatus());
    }

    private void deploy(T connection, ConnectionGroup connectionGroup) throws ServiceException {
        String connectionUuid = connection.getId();
        LOGGER.info("Deploy connection,uuid =" + connectionUuid + ",type=" + connection.getClass());
        List<NbiNetConnection> dbConnectionList = VpnRelationUtil.queryNetConnections(connectionUuid);
        if(CollectionUtils.isEmpty(dbConnectionList)) {
            LOGGER.warn("Deploy connection finished, connection is empty,uuid =" + connectionUuid + ",type="
                    + connection.getClass());
            return;
        }
        List<ConnectionPackage> connectionPackages = VpnConnectionUtil.splitConnections(dbConnectionList);
        List<NbiNetConnection> connectionList = new ArrayList<>();
        for(ConnectionPackage connectionPackage : connectionPackages) {
            connectionList.addAll(connectionPackage.getConnections());
        }

        deployConnections(connectionPackages);

        List<NbiNetConnection> refreshConnectionList = VpnRelationUtil.queryNetConnections(connectionList);
        List<NbiNetConnection> sortedList = sortConnection(refreshConnectionList);

        List<NeConnection> neConnections =
                connectionGroup != null ? connectionGroup.getNeConnections() : SiteModelConverter.convert(connection);
        RouteGroup routes = new RouteGroup();
        VpnModelConverter.buildRoute(routes, connection, neConnections, sortedList);

        deployRoutes(routes, sortedList);
    }

    @SuppressWarnings("unchecked")
    private void createConnections(List<ConnectionPackage> connectionPackages) throws ServiceException {
        for(ConnectionPackage connectionPackage : connectionPackages) {
            String type = connectionPackage.getType();
            List<NbiNetConnection> connectionList = connectionPackage.getConnections();
            if(CollectionUtils.isNotEmpty(connectionList)) {
                List<NbiConnectionRelation> relationList =
                        VpnTunnelUtil.buildConnectionRelation(connectionPackage.getParentConnection(), connectionList);
                relationDao.insert(relationList);

                LOGGER.info("Call sbi interface to create connection,the technology is :" + type);
                if("vxlan".equals(type)) {
                    vxlanSbiService.create((List<NetVxlanConnection>)(List<?>)connectionList);
                } else if("ipsec".equals(type)) {
                    ipsecSbiService.create((List<NetIpsecConnection>)(List<?>)connectionList);
                } else {
                    continue;
                }

            }
        }
    }

    private void deleteConnections(List<ConnectionPackage> removeConnections) throws ServiceException {
        LOGGER.info("Start delete connections.The removeConnections is :" + JsonUtils.toJson(removeConnections));
        for(ConnectionPackage connectionPackage : removeConnections) {
            String technology = connectionPackage.getType();
            List<NbiNetConnection> connectionList = connectionPackage.getConnections();
            List<String> uuidList = ModelServiceUtil.getUuidList(connectionList);
            LOGGER.info("Call sbi interface to undeploy connection,the technology is :" + technology);
            if("vxlan".equals(technology)) {
                vxlanSbiService.undeploy(uuidList);
            } else if("ipsec".equals(technology)) {
                ipsecSbiService.undeploy(uuidList);
            } else {
                continue;
            }

            LOGGER.info("Call sbi interface to delete connection,the technology is :" + technology);
            for(NbiNetConnection netConnection : connectionList) {
                if("vxlan".equals(technology)) {
                    vxlanSbiService.delete(netConnection.getId());
                } else if("ipsec".equals(technology)) {
                    ipsecSbiService.delete(netConnection.getId());
                } else {
                    continue;
                }
                List<NbiConnectionRelation> relationList =
                        VpnRelationUtil.queryConnectionRelations(Arrays.asList(netConnection));
                if(CollectionUtils.isNotEmpty(relationList)) {
                    relationDao.delete(ModelServiceUtil.getUuidList(relationList), NbiConnectionRelation.class);
                }
            }
            VpnReliabilityUtil.deleteNqa(uuidList);
        }
    }

    @SuppressWarnings("unchecked")
    private void updateConnections(ConnectionGroup group) throws ServiceException {
        List<NbiNetConnection> updateConnections = group.getUpdate();
        if(CollectionUtils.isEmpty(updateConnections)) {
            return;
        }

        List<ConnectionPackage> updatePackages = VpnConnectionUtil.splitConnections(updateConnections);
        for(ConnectionPackage connectionPackage : updatePackages) {
            String type = connectionPackage.getType();
            List<NbiNetConnection> connectionList = connectionPackage.getConnections();
            if(CollectionUtils.isEmpty(connectionList)) {
                continue;
            }

            LOGGER.info("Call sbi interface to update connection,the technology is :" + type);
            if("vxlan".equals(type)) {
                vxlanSbiService.update((List<NetVxlanConnection>)(List<?>)connectionList);
            } else if("ipsec".equals(type)) {
                ipsecSbiService.update((List<NetIpsecConnection>)(List<?>)connectionList);
            } else {
                continue;
            }
        }
    }

    private void deployConnections(List<ConnectionPackage> connectionPackages) throws ServiceException {
        for(ConnectionPackage connectionPackage : connectionPackages) {
            List<String> deployUuids = new ArrayList<>();
            String technology = connectionPackage.getType();
            List<NbiNetConnection> connectionList = connectionPackage.getConnections();

            for(NbiNetConnection netConnection : connectionList) {
                if(!DeployState.DEPLOY.getState().equalsIgnoreCase(netConnection.getDeployStatus())) {
                    deployUuids.add(netConnection.getId());
                }
            }
            if(CollectionUtils.isNotEmpty(deployUuids)) {
                LOGGER.info("Deploy netConnection,uuids = " + deployUuids + ",type = " + technology);
                if("vxlan".equals(technology)) {
                    vxlanSbiService.deploy(deployUuids);
                } else if("ipsec".equals(technology)) {
                    ipsecSbiService.deploy(deployUuids);
                } else {
                    continue;
                }
            }
        }
    }

    private void undeployConnections(List<ConnectionPackage> undeployConnections) throws ServiceException {
        for(ConnectionPackage connectionPackage : undeployConnections) {
            String technology = connectionPackage.getType();

            List<String> uuidList = ModelServiceUtil.getUuidList(connectionPackage.getConnections());

            LOGGER.info("Call sbi interface to undeploy connection,the technology is :" + technology);
            if("vxlan".equals(technology)) {
                vxlanSbiService.undeploy(uuidList);
            } else if("ipsec".equals(technology)) {
                ipsecSbiService.undeploy(uuidList);
            } else {
                continue;
            }
        }
    }

    private void deployRoutes(RouteGroup routes, List<NbiNetConnection> connectionList) throws ServiceException {
        List<BaseRoute> createRoutes = routes.getCreate();
        List<NetRoute> createStaticRoutes = RouteGroup.getRoutes(NetRoute.class, createRoutes);
        List<NetRoute> newStaticRoutes = VpnRouteUtil.processRoute(createStaticRoutes);
        LOGGER.info("Deploy static routes,routes = " + JsonUtils.toJson(newStaticRoutes));
        routeSbiService.create(newStaticRoutes);

        List<PolicyRoute> createPolicyRoutes = RouteGroup.getRoutes(PolicyRoute.class, createRoutes);
        List<PolicyRoute> updatePolicyRoutes = RouteGroup.getRoutes(PolicyRoute.class, routes.getUpdate());
        LOGGER.info("Deploy policy routes,routes = " + JsonUtils.toJson(createPolicyRoutes));
        policyRouteSbiService.create(createPolicyRoutes);

        LOGGER.info("Update policy routes,routes = " + JsonUtils.toJson(updatePolicyRoutes));
        List<String> uuidList = ModelServiceUtil.getUuidList(updatePolicyRoutes);
        if(VpnPolicyRouteUtil.waitDone(uuidList)) {
            policyRouteSbiService.update(updatePolicyRoutes);
        }
    }

    private void undeployRoutes(T connection, List<ConnectionPackage> undeployConnections) throws ServiceException {
        LOGGER.info("Start undeploy routes.");
        if(CollectionUtils.isEmpty(undeployConnections)) {
            return;
        }
        List<NbiNetConnection> netConnections = new ArrayList<>();
        for(ConnectionPackage pkg : undeployConnections) {
            netConnections.addAll(pkg.getConnections());
        }
        for(NbiNetConnection netConnection : netConnections) {
            List<BaseRoute> routes = VpnRouteUtil.queryDeleteRoute(netConnection.getId());
            List<NetRoute> staticRoutes = RouteGroup.getRoutes(NetRoute.class, routes);
            List<PolicyRoute> policyRoutes = RouteGroup.getRoutes(PolicyRoute.class, routes);
            if(CollectionUtils.isNotEmpty(staticRoutes)) {
                LOGGER.info("Get sbi interface ,the technology is :" + RouteType.STATIC);
                routeSbiService.undeploy(ModelServiceUtil.getUuidList(staticRoutes));
                for(NetRoute route : staticRoutes) {
                    routeSbiService.delete(route.getId());
                }
            }
            if(CollectionUtils.isNotEmpty(policyRoutes)) {
                LOGGER.info("Get sbi interface ,the technology is :" + RouteType.POLICY);
                policyRouteSbiService.undeploy(ModelServiceUtil.getUuidList(policyRoutes));
                for(PolicyRoute route : policyRoutes) {
                    policyRouteSbiService.delete(route.getId());
                }
            }
        }
    }

    private List<NbiNetConnection> sortConnection(List<NbiNetConnection> connections) {
        List<NbiNetConnection> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(connections)) {
            return result;
        }
        List<NbiNetConnection> internetList = new ArrayList<>();
        List<NbiNetConnection> dcList = new ArrayList<>();
        for(NbiNetConnection data : connections) {
            CpeRole srcRole = CpeRole.getCpeRole(data.getSrcNeRole());
            CpeRole destRole = CpeRole.getCpeRole(data.getDestNeRole());
            if((CpeRole.CLOUDCPE == srcRole && CpeRole.LOCALCPE == destRole)
                    || (CpeRole.CLOUDCPE == destRole && CpeRole.LOCALCPE == srcRole)) {
                internetList.add(data);
            } else {
                dcList.add(data);
            }
        }
        result.addAll(internetList);
        result.addAll(dcList);
        return result;
    }

    private void processDeleteUpdate(String uuid, Class<T> resType, T connection) throws ServiceException {
        LOGGER.info("Start to update the tunnel.");
        if(!SiteConnectionUtil.isSite2Dc(connection)) {
            LOGGER.info("The connection is not site to dc.");
            return;
        }
        List<NbiNetConnection> relatedConnections = VpnRelationUtil.queryNetConnections(uuid);
        if(CollectionUtils.isEmpty(relatedConnections)) {
            return;
        }
        List<NeConnection> neConnections = SiteModelConverter.convert(connection);
        List<ConnectionPackage> connectionPkgs = VpnConnectionUtil.splitConnections(relatedConnections);
        ConnectionGroup group = new ConnectionGroup();
        group.setConnectionType(resType.getSimpleName());
        group.setNeConnections(neConnections);
        for(ConnectionPackage connectionPkg : connectionPkgs) {
            TunnelProducer tunnelProducer = ConnectionCommonProducer.getTunnelProducer(connectionPkg.getType());
            if(tunnelProducer == null) {
                LOGGER.warn("Connection producer is not exist,type = " + connectionPkg.getType());
                continue;
            }
            for(NbiNetConnection netConnection : connectionPkg.getConnections()) {
                NeConnection neConnection = SiteConnectionUtil.getNeConnection(netConnection, neConnections);
                if(neConnection == null) {
                    LOGGER.warn("NeConnection is not exist,uuid=" + netConnection.getId() + ",type="
                            + netConnection.getType());
                    continue;
                }
                tunnelProducer.buildDeleteNetConnection(group, neConnection, netConnection);
            }
        }
        updateConnections(group);
    }

}
