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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.model.ConnectionPackage;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.model.v2.overlay.BaseModel;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiConnectionRelation;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.db.InternalVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetIpsecConnection;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetVxlanConnection;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class to deal with vpn connection relation.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 23, 2017
 */
public class VpnRelationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnRelationUtil.class);

    private static BaseDao<NbiVpnConnection> vpnDao = new BaseDao<NbiVpnConnection>();

    private static BaseDao<InternalVpnConnection> internalVpnDao = new BaseDao<InternalVpnConnection>();

    private static BaseDao<NbiConnectionRelation> relationDao = new BaseDao<NbiConnectionRelation>();

    private static BaseDao<NetVxlanConnection> vxlanDao = new BaseDao<NetVxlanConnection>();

    private static BaseDao<NetIpsecConnection> ipsecDao = new BaseDao<NetIpsecConnection>();

    private VpnRelationUtil() {

    }

    /**
     * Process relation list that the relation is not in DB.<br>
     * 
     * @param relations All the relation list
     * @return The relation list that the relation is in relations and not in DB
     * @throws ServiceException when query relation from DB failed
     * @since SDNO 0.5
     */
    public static List<NbiConnectionRelation> processRelations(List<NbiConnectionRelation> relations)
            throws ServiceException {
        List<NbiConnectionRelation> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(relations)) {
            return result;
        }
        List<NbiConnectionRelation> queryData = new ArrayList<>();
        for(NbiConnectionRelation relation : relations) {
            NbiConnectionRelation cond = new NbiConnectionRelation();
            cond.setVpnConnectionId(relation.getVpnConnectionId());
            cond.setNetConnectionId(relation.getNetConnectionId());
            queryData.addAll(relationDao.query(cond));
        }
        Set<String> resKeys = new HashSet<String>();
        for(NbiConnectionRelation data : queryData) {
            resKeys.add(data.toResKey());
        }
        for(NbiConnectionRelation relation : relations) {
            if(!resKeys.contains(relation.toResKey())) {
                result.add(relation);
            }
        }
        return result;
    }

    /**
     * Query connection from DB through NBI connection uuid.<br>
     * 
     * @param serviceConnectionId The NBI connection uuid
     * @return The connection queried from DB
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static List<NbiNetConnection> queryNetConnections(String serviceConnectionId) throws ServiceException {
        NbiServiceConnection vpnConnection = new NbiServiceConnection();
        vpnConnection.setId(serviceConnectionId);
        List<NbiConnectionRelation> relations = queryConnectionRelations(vpnConnection);
        List<NbiNetConnection> relatedConnections = buildRelatedConnections(relations);
        return queryNetConnections(relatedConnections);
    }

    /**
     * Query the connection relation table in DB.<br>
     * 
     * @param vpnConnection The queried condition model
     * @return The queried the connection relation from DB
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static List<NbiConnectionRelation> queryConnectionRelations(NbiServiceConnection vpnConnection)
            throws ServiceException {
        NbiConnectionRelation cond = new NbiConnectionRelation();
        cond.setVpnConnectionId(vpnConnection.getId());
        return relationDao.query(cond);
    }

    /**
     * Query the connection relation table in DB through conditions.<br>
     * 
     * @param netConnections The queried conditions
     * @return The queried the connection relation from DB
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static List<NbiConnectionRelation> queryConnectionRelations(List<NbiNetConnection> netConnections)
            throws ServiceException {
        LOGGER.info("To query connection relations,the connection is:" + JsonUtils.toJson(netConnections));
        List<NbiConnectionRelation> relationList = new ArrayList<>();
        for(NbiNetConnection netConnection : netConnections) {
            NbiConnectionRelation cond = new NbiConnectionRelation();
            cond.setNetConnectionId(netConnection.getId());
            relationList.addAll(relationDao.query(cond));
        }
        return relationList;
    }

    /**
     * Query the connection in DB that need to remove.<br>
     * 
     * @param vpnConnection The connection,maybe is vpn connection or internal connection
     * @param deployState The deploy status
     * @return The queried connection in DB that need to remove
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static List<ConnectionPackage> queryRemovableConnections(NbiServiceConnection vpnConnection,
            DeployState deployState) throws ServiceException {
        List<ConnectionPackage> packages = new ArrayList<>();
        List<NbiConnectionRelation> relations = queryConnectionRelations(vpnConnection);
        LOGGER.info("Query the connection relations success,the relation is:" + JsonUtils.toJson(relations));
        if(CollectionUtils.isEmpty(relations)) {
            return packages;
        }
        List<NbiNetConnection> relatedConnections = buildRelatedConnections(relations);
        List<NbiNetConnection> relatedConnectionsDb = queryNetConnections(relatedConnections);

        List<NbiNetConnection> removableConnections =
                buildRemovableConnections(vpnConnection, deployState, relatedConnectionsDb);
        LOGGER.info("Splite the removable connections.");
        return VpnConnectionUtil.splitConnections(removableConnections);
    }

    /**
     * Query connection from DB through conditions.<br>
     * 
     * @param conds The queried conditions
     * @return The connection queried from DB
     * @throws ServiceException when query DB failed
     * @since SDNO 0.5
     */
    public static List<NbiNetConnection> queryNetConnections(List<NbiNetConnection> conds) throws ServiceException {
        LOGGER.info("Start to query netconnections,the conditions is :" + JsonUtils.toJson(conds));
        List<NbiNetConnection> connections = new ArrayList<>();
        if(CollectionUtils.isEmpty(conds)) {
            LOGGER.info("The condition is empty.");
            return connections;
        }
        List<ConnectionPackage> packages = VpnConnectionUtil.splitConnections(conds);
        for(ConnectionPackage connectionPkg : packages) {
            List<String> uuids = ModelServiceUtil.getUuidList(connectionPkg.getConnections());
            LOGGER.info("Start query local connections,the uuid is :" + JsonUtils.toJson(uuids) + ",type is:"
                    + connectionPkg.getType());

            if("vxlan".equals(connectionPkg.getType())) {
                connections.addAll(vxlanDao.query(uuids, NetVxlanConnection.class));
            } else if("ipsec".equals(connectionPkg.getType())) {
                connections.addAll(ipsecDao.query(uuids, NetIpsecConnection.class));
            } else {
                continue;
            }
        }
        LOGGER.info("Query local connections success,the result is :" + JsonUtils.toJson(connections));
        return connections;
    }

    private static List<NbiNetConnection> buildRelatedConnections(List<NbiConnectionRelation> relations) {
        if(CollectionUtils.isEmpty(relations)) {
            return new ArrayList<>();
        }
        Map<String, NbiNetConnection> netConnections = new HashMap<String, NbiNetConnection>();
        for(NbiConnectionRelation relation : relations) {
            NbiNetConnection netConnection = new NbiNetConnection();
            netConnection.setId(relation.getNetConnectionId());
            netConnection.setType(relation.getNetConnectionType());
            netConnections.put(netConnection.getId(), netConnection);
        }
        LOGGER.info("Build related connections success.");
        return new ArrayList<>(netConnections.values());
    }

    private static List<NbiNetConnection> buildRemovableConnections(NbiServiceConnection vpnConnection,
            DeployState deployState, List<NbiNetConnection> netConnections) throws ServiceException {
        List<NbiNetConnection> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(netConnections)) {
            return result;
        }
        Set<String> netConnectionIds = new HashSet<String>(ModelServiceUtil.getUuidList(netConnections));
        List<NbiConnectionRelation> dbRelations = queryConnectionRelations(netConnections);
        List<NbiServiceConnection> serviceConnections = queryServiceConnections(dbRelations);
        for(NbiConnectionRelation dbRelation : dbRelations) {
            if(!netConnectionIds.contains(dbRelation.getNetConnectionId())) {
                continue;
            }
            if(!vpnConnection.getId().equals(dbRelation.getVpnConnectionId())) {
                NbiServiceConnection serviceConnection = getModel(serviceConnections, dbRelation.getVpnConnectionId());
                if(serviceConnection == null) {
                    continue;
                }
                LOGGER.info("The service connection is :" + JsonUtils.toJson(serviceConnection));
                DeployState dbDeployState = DeployState.fromState(serviceConnection.getDeployStatus());
                if(dbDeployState == null || !deployState.equals(dbDeployState)) {
                    netConnectionIds.remove(dbRelation.getNetConnectionId());
                }
            }
        }
        for(String uuid : netConnectionIds) {
            NbiNetConnection model = getModel(netConnections, uuid);
            if(model != null) {
                result.add(model);
            }
        }
        return result;
    }

    private static List<NbiServiceConnection> queryServiceConnections(List<NbiConnectionRelation> relations)
            throws ServiceException {
        List<NbiServiceConnection> connections = new ArrayList<>();
        Map<String, List<String>> vpnConnectionMap = new HashMap<String, List<String>>();
        for(NbiConnectionRelation relation : relations) {
            if(!vpnConnectionMap.containsKey(relation.getVpnConnectionType())) {
                vpnConnectionMap.put(relation.getVpnConnectionType(), new ArrayList<String>());
            }
            vpnConnectionMap.get(relation.getVpnConnectionType()).add(relation.getVpnConnectionId());
        }

        LOGGER.info("The vpn connection map is :" + JsonUtils.toJson(vpnConnectionMap));
        for(Entry<String, List<String>> entry : vpnConnectionMap.entrySet()) {
            String type = entry.getKey();
            List<String> uuids = entry.getValue();
            if(NbiVpnConnection.class.getSimpleName().equals(type)) {
                connections.addAll(vpnDao.query(uuids, NbiVpnConnection.class));
            } else if(InternalVpnConnection.class.getSimpleName().equals(type)) {
                connections.addAll(internalVpnDao.query(uuids, InternalVpnConnection.class));
            }
        }
        return connections;
    }

    private static <T extends BaseModel> T getModel(List<T> models, String uuid) {
        if(StringUtils.isEmpty(uuid) || CollectionUtils.isEmpty(models)) {
            return null;
        }
        for(T model : models) {
            if(uuid.equals(model.getId())) {
                return model;
            }
        }
        return null;
    }
}
