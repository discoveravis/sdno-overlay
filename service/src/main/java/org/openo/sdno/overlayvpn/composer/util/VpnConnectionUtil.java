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
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.composer.model.ConnectionPackage;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.enums.TunnelType;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class to deal with vpn connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 22, 2017
 */
public class VpnConnectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnConnectionUtil.class);

    private static final Integer MAX_RETRY = 50;

    private VpnConnectionUtil() {
        super();
    }

    /**
     * Split connection through the technology.<br>
     * 
     * @param connection The connection used to set as parent connection
     * @param netConnections The vpn connection used to split
     * @return The split connection package
     * @since SDNO 0.5
     */
    public static List<ConnectionPackage> splitConnections(NbiServiceConnection connection,
            List<NbiNetConnection> netConnections) {
        List<ConnectionPackage> packages = new ArrayList<>();
        if(CollectionUtils.isEmpty(netConnections)) {
            return packages;
        }
        Map<String, List<NbiNetConnection>> connectionMap = new HashMap<String, List<NbiNetConnection>>();
        for(NbiNetConnection netConnection : netConnections) {
            String type = netConnection.getType();
            if(!connectionMap.containsKey(type)) {
                connectionMap.put(type, new ArrayList<NbiNetConnection>());
            }
            connectionMap.get(type).add(netConnection);
        }
        for(TunnelType tunnelType : TunnelType.values()) {
            String type = tunnelType.getTechnology();
            if(connectionMap.containsKey(type)) {
                ConnectionPackage cpkg = new ConnectionPackage(type);
                cpkg.setConnections(connectionMap.get(type));
                cpkg.setParentConnection(connection);
                packages.add(cpkg);
            }
        }
        return packages;
    }

    /**
     * Split connection through the technology without parent connection.<br>
     * 
     * @param netConnections The vpn connection used to split
     * @return The split connection package
     * @since SDNO 0.5
     */
    public static List<ConnectionPackage> splitConnections(List<NbiNetConnection> netConnections) {
        return splitConnections(null, netConnections);
    }

    /**
     * Build response object of vpn connection creating interface.<br>
     * 
     * @param vpnConnection The NBI model of vpn connection
     * @return The response object
     * @since SDNO 0.5
     */
    public static Object buildRsponse(NbiVpnConnection vpnConnection) {
        String callbackUrl = "/openoapi/sdnooverlay/v1/vpn-connections/" + vpnConnection.getId() + "status";
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("location", callbackUrl);
        result.put("id", vpnConnection.getId());
        return result;
    }

    /**
     * Initialize the qosClassify.<br>
     * 
     * @param netConnection The vpn connection
     * @param connectionTemplate The connection template
     * @since SDNO 0.5
     */
    public static void initQosPreClassify(NbiNetConnection netConnection, TemplateConnection connectionTemplate) {
        Boolean qosClassify = Boolean.valueOf(connectionTemplate.getQosPreClassify());
        netConnection.setQosPreClassify(qosClassify.toString());
    }

    /**
     * Whether the resource can be deleted,the resource maybe vpn or vpn connection.<br>
     * 
     * @param uuid The uuid of resource
     * @return true when the resource can be deleted,otherwise is false
     * @throws ServiceException when the connection is on going or the state is exception,or delete
     *             is timeout
     * @since SDNO 0.5
     */
    public static boolean waitDelete(String uuid) throws ServiceException {
        if(StringUtils.isEmpty(uuid)) {
            return false;
        }
        for(int i = 0; i < MAX_RETRY; i++) {
            NbiVpnConnection queryData = new BaseDao<NbiVpnConnection>().query(uuid, NbiVpnConnection.class);
            if(queryData == null) {
                return false;
            }
            ActionState state = ActionState.fromState(queryData.getActionState());
            if(ModelServiceUtil.isOnGoing(queryData)) {
                LOGGER.error("vpn connection is on going,uuid=" + uuid);
                throw new InnerErrorServiceException("vpn connection is on going,uuid=" + uuid);
            } else if(i == 0) {
                return true;
            } else if(state.isException()) {
                LOGGER.error("vpn connection is second delete,and the first delete failed,uuid=" + uuid);
                throw new InnerErrorServiceException(
                        "vpn connection is second delete,and the first delete failed,uuid=" + uuid);
            }
        }
        LOGGER.error("delete vpn connection is wait timeout.");
        throw new InnerErrorServiceException("delete vpn connection is wait timeout.");
    }

}
