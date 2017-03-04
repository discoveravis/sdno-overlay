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

package org.openo.sdno.overlayvpn.servicemodel.sbi;

import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseRoute;
import org.openo.sdno.overlayvpn.servicemodel.base.IP;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;

/**
 * The static route model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
@MOResType(infoModelName = "net_route")
public class NetRoute extends BaseRoute {

    private String destIp;

    private String enableDhcp;

    private String nextHop;

    private String outInterfaceName;

    private String priority;

    private String nqa;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public NetRoute() {
        super();
    }

    /**
     * Build static route.<br>
     * 
     * @param connection The connection contains field to set
     * @param destIp The destination IP to set
     * @param nextHop The nextHop to set
     * @param outInterfaceName The outInterfaceName to set
     * @param priority The priority to set
     * @param enableDhcp The enableDhcp to set
     * @return The built static route object
     * @throws ServiceException when translating json failed
     * @since SDNO 0.5
     */
    public static NetRoute buildStaticRoute(NbiNetConnection connection, String destIp, String nextHop,
            String outInterfaceName, Integer priority, boolean enableDhcp) throws ServiceException {
        NetRoute route = new NetRoute();
        route.allocateUuid();
        route.setName(UuidUtils.createBase64Uuid());
        route.setConnectionId(connection.getId());
        route.setTenantId(connection.getTenantId());
        route.setType("static");
        route.setDeployStatus(DeployState.DEPLOY.getState());
        route.setDestIp(StringUtils.isNotEmpty(destIp) ? JsonUtils.toJson(IP.buildIPv4(destIp)) : null);
        route.setNextHop(StringUtils.isNotEmpty(nextHop) ? JsonUtils.toJson(IP.buildIPv4(nextHop)) : null);
        route.setOutInterfaceName(outInterfaceName);
        route.setPriority(priority != null ? String.valueOf(priority) : "60");
        route.setEnableDhcp(String.valueOf(enableDhcp));
        return route;
    }

    /**
     * Build static route.<br>
     * 
     * @param connection The connection contains field to set
     * @param destIp The destination IP to set
     * @param nextHop The nextHop to set
     * @param outInterfaceName The outInterfaceName to set
     * @param priority The priority to set
     * @param enableDhcp The enableDhcp to set
     * @return The built static route object
     * @throws ServiceException when translating json failed
     * @since SDNO 0.5
     */
    public static NetRoute buildStaticRoute(NbiNetConnection connection, IP destIp, IP nextHop, String outInterfaceName,
            Integer priority, boolean enableDhcp) throws ServiceException {
        NetRoute route = new NetRoute();
        route.allocateUuid();
        route.setName(UuidUtils.createBase64Uuid());
        route.setConnectionId(connection.getId());
        route.setTenantId(connection.getTenantId());
        route.setType("static");
        route.setDeployStatus(DeployState.DEPLOY.getState());
        route.setDestIp(JsonUtils.toJson(destIp));
        route.setNextHop(JsonUtils.toJson(nextHop));
        route.setOutInterfaceName(outInterfaceName);
        route.setPriority(priority != null ? String.valueOf(priority) : "60");
        route.setEnableDhcp(String.valueOf(enableDhcp));
        return route;
    }

    /**
     * Build route key.<br>
     * 
     * @return The route key as string
     * @since SDNO 0.5
     */
    public String toRouteKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSrcNeId());
        sb.append(':');
        sb.append(getType());
        sb.append(':');
        sb.append(destIp);
        sb.append(':');
        sb.append(nextHop);
        sb.append(':');
        sb.append(outInterfaceName);
        return sb.toString();
    }

    public String getDestIp() {
        return destIp;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }

    public String getEnableDhcp() {
        return enableDhcp;
    }

    public void setEnableDhcp(String enableDhcp) {
        this.enableDhcp = enableDhcp;
    }

    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }

    public String getOutInterfaceName() {
        return outInterfaceName;
    }

    public void setOutInterfaceName(String outInterfaceName) {
        this.outInterfaceName = outInterfaceName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNqa() {
        return nqa;
    }

    public void setNqa(String nqa) {
        this.nqa = nqa;
    }

}
