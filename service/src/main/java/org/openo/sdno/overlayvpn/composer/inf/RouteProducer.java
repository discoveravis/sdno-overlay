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
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.composer.model.RouteGroup;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;

/**
 * The interface of route producer.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
public interface RouteProducer {

    /**
     * Build route group.<br>
     * 
     * @param routes The route group to be built
     * @param connection The vpn connection
     * @param neConnection The ne connection
     * @throws ServiceException When build route group failed
     * @since SDNO 0.5
     */
    void buildNetRoute(RouteGroup routes, NbiNetConnection connection, NeConnection neConnection) throws ServiceException;
}
