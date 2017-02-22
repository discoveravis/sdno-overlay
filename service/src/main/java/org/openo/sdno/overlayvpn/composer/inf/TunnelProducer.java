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

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.model.ConnectionGroup;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateConnection;

/**
 * The interface of tunnel producer.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
public interface TunnelProducer {

    /**
     * Get technology.<br>
     * 
     * @return The tunnel technology
     * @since SDNO 0.5
     */
    List<String> getTechnology();

    /**
     * Build connection.<br>
     * 
     * @param connections Connection group
     * @param neConnection The ne connection
     * @param connectionTemplate Connection template
     * @throws ServiceException When build connection failed
     * @since SDNO 0.5
     */
    void buildNetConnection(ConnectionGroup connections, NeConnection neConnection,
            TemplateConnection connectionTemplate) throws ServiceException;

    /**
     * Build delete connection.<br>
     * 
     * @param connections Connection group
     * @param neConnection The ne connection
     * @param netConnection vpn connection
     * @throws ServiceException When build delete connection failed
     * @since SDNO 0.5
     */
    void buildDeleteNetConnection(ConnectionGroup connections, NeConnection neConnection, NbiNetConnection netConnection)
            throws ServiceException;
}
