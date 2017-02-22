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

package org.openo.sdno.overlayvpn.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.composer.inf.ConnectionProducer;
import org.openo.sdno.overlayvpn.composer.inf.RouteProducer;
import org.openo.sdno.overlayvpn.composer.model.ConnectionGroup;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.composer.model.RouteGroup;
import org.openo.sdno.overlayvpn.composer.util.SiteConnectionUtil;
import org.openo.sdno.overlayvpn.composer.util.SiteModelConverter;
import org.openo.sdno.overlayvpn.composer.util.VpnTemplateUtil;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.db.InternalVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The vpn model converter,used to build connection base on NBI model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
public class VpnModelConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnModelConverter.class);

    /**
     * Route producer map,save as map<name,routeProducer>.
     */
    private static Map<String, RouteProducer> routeProducers = new HashMap<String, RouteProducer>();

    /**
     * Connection producer map,save as map<name,connectionProducer>.
     */
    private static Map<String, ConnectionProducer> connectionProducers = new HashMap<String, ConnectionProducer>();

    private VpnModelConverter() {

    }

    /**
     * Register route producer to routeProducers.<br>
     * 
     * @param name route producer name
     * @param producer route producer
     * @since SDNO 0.5
     */
    public static void registerRouteProducer(String name, RouteProducer producer) {
        LOGGER.info("Registe vpn route producer for template : " + name);
        routeProducers.put(name, producer);
    }

    /**
     * Register connection producer to routeProducers.<br>
     * 
     * @param name template name
     * @param producer connection producer
     * @since SDNO 0.5
     */
    public static void registerConnectionProducer(String name, ConnectionProducer producer) {
        LOGGER.info("Registe vpn connection producer for template : " + name);
        connectionProducers.put(name, producer);
    }

    /**
     * Build vpn connection base on NBI model.<br>
     * 
     * @param connection The NBI connection model
     * @return The group of vpn connection
     * @throws ServiceException when the connection producer is null
     * @since SDNO 0.5
     */
    public static ConnectionGroup convertConnection(NbiServiceConnection connection) throws ServiceException {
        ConnectionGroup group = new ConnectionGroup();
        LOGGER.info("Start decompose connection, data = " + JsonUtil.toJson(connection));
        group.setConnectionType(connection.getClass().getSimpleName());
        ConnectionProducer producer = connectionProducers.get(ParamConsts.TEMPLATE_COMMON);
        if(producer == null) {
            throw new InnerErrorServiceException("Connection.convert.error,Template name is not defined.");
        }
        if(connection instanceof NbiVpnConnection) {
            group.append(producer.buildNetConnection((NbiVpnConnection)connection));
        } else if(connection instanceof InternalVpnConnection) {
            group.append(producer.buildNetConnection((InternalVpnConnection)connection));
        } else {
            LOGGER.warn("Connection type not supported," + connection.getClass().getName());
        }
        return group;
    }

    /**
     * Build route between the connections through the template.<br>
     * 
     * @param routes The route group
     * @param connection The NBI connection model
     * @param connections The ne connection list
     * @param netConnections The vpn connection list
     * @throws ServiceException when producer build route
     * @since SDNO 0.5
     */
    public static void buildRoute(RouteGroup routes, NbiServiceConnection connection, List<NeConnection> connections,
            List<NbiNetConnection> netConnections) throws ServiceException {
        if(CollectionUtils.isEmpty(netConnections)) {
            return;
        }
        List<NeConnection> neConnections = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(connections)) {
            neConnections.addAll(connections);
        } else {
            neConnections.addAll(SiteModelConverter.convert(connection));
        }
        for(NbiNetConnection netConnection : netConnections) {
            NeConnection neConnection = SiteConnectionUtil.getNeConnection(netConnection, neConnections);
            if(neConnection == null) {
                continue;
            }
            Template template = neConnection.getSrcTemplate();
            String templateName = template.getName();
            String routePolicy = VpnTemplateUtil.getConnectionTemplate(templateName, netConnection).getRoute();
            if(StringUtils.isEmpty(routePolicy)) {
                continue;
            }
            RouteProducer producer = routeProducers.get(routePolicy);
            if(producer == null) {
                LOGGER.warn("Route producer not exist,policy=" + routePolicy);
                return;
            }
            producer.buildNetRoute(routes, netConnection, neConnection);
        }

    }
}
