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

package org.openo.sdno.overlayvpn.composer.inf;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.model.ConnectionGroup;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.db.InternalVpnConnection;

/**
 * The interface of common producer for vpn connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
public interface ConnectionProducer {

    /**
     * Build connection base on NBI vpn connection model.<br>
     * 
     * @param connection The NBI vpn connection model
     * @return The connection group
     * @throws ServiceException when build connection failed
     * @since SDNO 0.5
     */
    ConnectionGroup buildNetConnection(NbiVpnConnection connection) throws ServiceException;

    /**
     * Build connection base on NBI internal vpn connection model.<br>
     * 
     * @param connection The NBI internal vpn connection model
     * @return The connection group
     * @throws ServiceException when build connection failed
     * @since SDNO 0.5
     */
    ConnectionGroup buildNetConnection(InternalVpnConnection connection) throws ServiceException;
}
