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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.springframework.util.CollectionUtils;

/**
 * The tool class to deal with site connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 22, 2017
 */
public class SiteConnectionUtil {

    private SiteConnectionUtil() {
        super();
    }

    /**
     * Get ne connection that the source and destination neId meet the condition.<br>
     * 
     * @param netConnection The vpn connection
     * @param neConnections The list of ne connection
     * @return The ne connection that meeting the condition
     * @since SDNO 0.5
     */
    public static NeConnection getNeConnection(NbiNetConnection netConnection, List<NeConnection> neConnections) {
        if(netConnection == null || CollectionUtils.isEmpty(neConnections)) {
            return null;
        }
        for(NeConnection neConnection : neConnections) {
            if((netConnection.getSrcNeId().equals(neConnection.getSrcNeId())
                    && netConnection.getDestNeId().equals(neConnection.getDestNeId()))
                    || (netConnection.getSrcNeId().equals(neConnection.getDestNeId())
                            && netConnection.getDestNeId().equals(neConnection.getSrcNeId()))) {
                return neConnection;
            }
        }
        return null;
    }

    /**
     * Whether the connection is site to DC connection<br>
     * 
     * @param connection The connection,is vpn connection or internal connection
     * @return true when is site to DC connection,otherwise is false
     * @throws ServiceException when query vpn connection failed
     * @since SDNO 0.5
     */
    public static boolean isSite2Dc(NbiServiceConnection connection) throws ServiceException {
        if(connection == null || !NbiVpnConnection.class.isAssignableFrom(connection.getClass())) {
            return false;
        }
        NbiVpnConnection dbData =
                new BaseDao<NbiVpnConnection>().query(connection.getId(), NbiVpnConnection.class, true);
        if(dbData == null) {
            return false;
        }
        NbiVpnGateway aEndVpnGateway = dbData.getaEndVpnGateway();
        NbiVpnGateway zEndVpnGateway = dbData.getzEndVpnGateway();

        return isSite2Dc(aEndVpnGateway) || isSite2Dc(zEndVpnGateway);
    }

    private static boolean isSite2Dc(NbiVpnGateway gateway) {
        if(gateway == null) {
            return false;
        }
        return StringUtils.isNotEmpty(gateway.getVpcId());
    }
}
