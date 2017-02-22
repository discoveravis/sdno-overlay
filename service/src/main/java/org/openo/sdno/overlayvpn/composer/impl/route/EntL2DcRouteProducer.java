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

package org.openo.sdno.overlayvpn.composer.impl.route;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.composer.VpnModelConverter;
import org.openo.sdno.overlayvpn.composer.inf.RouteProducer;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.composer.model.RouteGroup;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The site to dc route producer.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 11, 2017
 */
@Service
public class EntL2DcRouteProducer implements RouteProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntL2DcRouteProducer.class);

    /**
     * Constructor,register the site to dc route producer to routeProducers.<br>
     * 
     * @since SDNO 0.5
     */
    public EntL2DcRouteProducer() {
        super();
        VpnModelConverter.registerRouteProducer(ParamConsts.ROUTE_ENT_L2_DC, this);
    }

    @Override
    public void buildNetRoute(RouteGroup routes, NbiNetConnection netConnection, NeConnection neConnection)
            throws ServiceException {
        LOGGER.info("Start to build route for connection,type=" + netConnection.getType() + ",uuid="
                + netConnection.getId());
        // Has created the route when creating localcpe to cloudcpe.
        LOGGER.info("Finish build route for connection,routes=" + JsonUtils.toJson(routes));
    }

}
