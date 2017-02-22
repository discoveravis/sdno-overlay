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
import org.openo.sdno.overlayvpn.composer.model.ConnectionGroup;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.frame.dao.MssInvProxy;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiConnectionRelation;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class to deal with vpn tunnel.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 23, 2017
 */
public class VpnTunnelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnTunnelUtil.class);

    private VpnTunnelUtil() {

    }

    /**
     * Build connection relation list through ne connection.<br>
     * 
     * @param neConnection The ne connection
     * @param netConnections The Net connection list
     * @return The connection relation list
     * @since SDNO 0.5
     */
    public static List<NbiConnectionRelation> buildConnectionRelation(NeConnection neConnection,
            List<NbiNetConnection> netConnections) {
        List<NbiConnectionRelation> relations = new ArrayList<>();
        if(CollectionUtils.isEmpty(netConnections)) {
            return relations;
        }
        for(NbiNetConnection netConnection : netConnections) {
            NbiConnectionRelation relation = new NbiConnectionRelation();
            relation.allocateUuid();
            relation.setVpnConnectionId(neConnection.getConnectionId());
            relation.setVpnConnectionType(neConnection.getConnectionType());
            relation.setNetConnectionId(netConnection.getId());
            relation.setNetConnectionType(netConnection.getType());
            relations.add(relation);
        }
        return relations;
    }

    /**
     * Build connection relation list through service connection.<br>
     * 
     * @param neConnection The service connection,the service connection is NBI connection,maybe is
     *            internal connection or vpn connection
     * @param netConnections The Net connection list
     * @return The connection relation list
     * @since SDNO 0.5
     */
    public static List<NbiConnectionRelation> buildConnectionRelation(NbiServiceConnection neConnection,
            List<NbiNetConnection> netConnections) {
        List<NbiConnectionRelation> relations = new ArrayList<>();
        if(CollectionUtils.isEmpty(netConnections)) {
            return relations;
        }
        for(NbiNetConnection netConnection : netConnections) {
            NbiConnectionRelation relation = new NbiConnectionRelation();
            relation.allocateUuid();
            relation.setVpnConnectionId(neConnection.getId());
            relation.setVpnConnectionType(neConnection.getClass().getSimpleName());
            relation.setNetConnectionId(netConnection.getId());
            relation.setNetConnectionType(netConnection.getType());
            relations.add(relation);
        }
        return relations;
    }

    /**
     * Check whether the connection is in DB or in group.<br>
     * 
     * @param group The connection group
     * @param netConnection The net connection
     * @return if the connection exist return the connection,otherwise return null
     * @throws ServiceException when translate json failed
     * @since SDNO 0.5
     */
    public static NbiNetConnection checkNetConnection(ConnectionGroup group, NbiNetConnection netConnection)
            throws ServiceException {
        NbiNetConnection dbConnection = queryConnection(netConnection.toQueryCondition());
        if(dbConnection == null) {
            dbConnection = queryConnection(netConnection.toReverse().toQueryCondition());
        }
        if(dbConnection != null) {
            LOGGER.info("Connection in db,uuid={},type={},deploy={},status={}", new Object[] {dbConnection.getId(),
                            dbConnection.getType(), dbConnection.getDeployStatus(), dbConnection.getActionState()});
            return dbConnection;
        }
        return checkNetConnectionGroup(group, netConnection);
    }

    private static NbiNetConnection checkNetConnectionGroup(ConnectionGroup group, NbiNetConnection netConnection)
            throws ServiceException {
        if(netConnection == null || group == null || CollectionUtils.isEmpty(group.getCreate())) {
            return null;
        }
        for(NbiNetConnection nConn : group.getCreate()) {
            String connKey1 = JsonUtils.toJson(netConnection.toQueryCondition());
            String connKey2 = JsonUtils.toJson(netConnection.toReverse().toQueryCondition());
            String connKey3 = JsonUtils.toJson(nConn.toQueryCondition());
            if(connKey1.equals(connKey3) || connKey2.equals(connKey3)) {
                return nConn;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static NbiNetConnection queryConnection(NbiNetConnection cond) throws ServiceException {
        BatchQueryResult<NbiNetConnection> queryResult = new MssInvProxy(cond.getClass()).query(cond, null);
        return CollectionUtils.isEmpty(queryResult.getObjects()) ? null : queryResult.getObjects().get(0);
    }
}
