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

package org.openo.sdno.overlayvpn.composer.impl.connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.composer.VpnModelConverter;
import org.openo.sdno.overlayvpn.composer.inf.ConnectionProducer;
import org.openo.sdno.overlayvpn.composer.inf.TunnelProducer;
import org.openo.sdno.overlayvpn.composer.model.ConnectionGroup;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.composer.util.SiteModelConverter;
import org.openo.sdno.overlayvpn.composer.util.VpnTemplateUtil;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.db.InternalVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateConnection;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The common producer of vpn connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
@Service
public class ConnectionCommonProducer implements ConnectionProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionCommonProducer.class);

    /**
     * Tunnel producer map,save as map<technology,tunnelProducer>.
     */
    private static Map<String, TunnelProducer> tunnelProducers = new HashMap<String, TunnelProducer>();

    public ConnectionCommonProducer() {
        super();
        VpnModelConverter.registerConnectionProducer(ParamConsts.TEMPLATE_COMMON, this);
    }

    /**
     * Register tunnel producer to tunnelProducers.<br>
     * 
     * @param producer The tunnel producer
     * @since SDNO 0.5
     */
    public static void registeTunnelProducer(TunnelProducer producer) {
        for(String technology : producer.getTechnology()) {
            tunnelProducers.put(technology, producer);
        }
    }

    /**
     * Get tunnel producer by technology.<br>
     * 
     * @param technology The technology of connection
     * @return The needed tunnel producer
     * @since SDNO 0.5
     */
    public static TunnelProducer getTunnelProducer(String technology) {
        return tunnelProducers.get(technology);
    }

    @Override
    public ConnectionGroup buildNetConnection(NbiVpnConnection connection) throws ServiceException {
        ConnectionGroup group = new ConnectionGroup();
        List<NeConnection> neConnections = SiteModelConverter.convert(connection);
        group.getNeConnections().addAll(neConnections);
        for(NeConnection neConnection : neConnections) {
            buildNetConnection(group, neConnection);
            LOGGER.info("Build netconnection finished.the connection group is :" + JsonUtils.toJson(group));
        }
        return group;
    }

    @Override
    public ConnectionGroup buildNetConnection(InternalVpnConnection connection) throws ServiceException {
        ConnectionGroup group = new ConnectionGroup();
        List<NeConnection> neConnections = SiteModelConverter.convert(connection);
        group.getNeConnections().addAll(neConnections);
        for(NeConnection neConnection : neConnections) {
            buildNetConnection(group, neConnection);
            LOGGER.info("Build netconnection finished.the connection group is :" + JsonUtils.toJson(group));
        }
        return group;
    }

    private void buildNetConnection(ConnectionGroup group, NeConnection connection) throws ServiceException {
        LOGGER.info("Query connection template,neConnection={}", JsonUtils.toJson(connection));
        List<TemplateConnection> connectionTemplates = VpnTemplateUtil.getConnectionTemplate(
                connection.getSrcTemplate().getName(), connection.getSrcNeRole(), connection.getDestNeRole(), null);
        for(TemplateConnection connectionTemplate : connectionTemplates) {
            LOGGER.info("Apply connection template,name = {}", connectionTemplate.getName());
            String technology = connectionTemplate.getTechnology();
            TunnelProducer tunnelProducer = getTunnelProducer(technology);
            if(tunnelProducer == null) {
                LOGGER.error(
                        "Connection.buildNetConnection.error,No producer for this technology,please check template,technology="
                                + technology);
                throw new InnerErrorServiceException(
                        "Connection.buildNetConnection.error,No producer for this technology,please check template,technology="
                                + technology);
            }
            tunnelProducer.buildNetConnection(group, connection, connectionTemplate);
            for(NbiNetConnection netConnection : group.getCreate()) {
                if((connection.getSrcNeId().equals(netConnection.getSrcNeId()))
                        && (connection.getDestNeId().equals(netConnection.getDestNeId()))) {
                    if(connection.getSrcSite() != null) {
                        netConnection.setSrcSiteId(connection.getSrcSite().getId());
                    }
                    if(connection.getDestSite() != null) {
                        netConnection.setDestSiteId(connection.getDestSite().getId());
                    }
                }
            }
        }
    }
}
