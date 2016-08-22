/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.consts;

/**
 * Adapter Service URL Definition Class.<br/>
 * <p>
 * Constants class with the URLs used by the REST services<br/>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public class UrlAdapterConst {

    /**
     * URL of Query of WanSubInf
     */
    public static final String QUERY_WAN_SUB_INTERFACE_WITH_IP = "/rest/svc/overlay-vpn/v1/localsitemgr/waninterface";

    /**
     * Prefix URL of Adapter Service
     */
    public static final String ADAPTER_BASE_URL = "/rest/svc/sbiadp/controller/";

    /**
     * URL of Create PublicIP
     */
    public static final String CREATE_PUBLIC_IP_URL = "/overlay/v1/publicip";

    /**
     * URL of Delete PublicIP
     */
    public static final String DELETE_PUBLIC_IP_URL = "/overlay/v1/deviceid/{0}/publicip";

    /**
     * URL of Query WanSubInf from adapter
     */
    public static final String QUERY_WAN_INTERFACE = "/v1/overlay/device/{0}/wan-sub-interfaces";

    /**
     * URL of Enable WanSubInf
     */
    public static final String ENABLE_WAN_INTERFACE = "/v1/overlay/device/{0}/wan-sub-interfaces/enable-dhcp";

    /**
     * URL of Create GRE Tunnel
     */
    public static final String CREATE_GRE_TUNNEL = "/v1/gre/batch-create-tunnels";

    /**
     * URL of Query GRE Tunnel
     */
    public static final String QUERY_GRE_TUNNEL = "/v1/gre/device/{0}/tunnels/{1}";

    /**
     * URL of Delete Tunnel
     */
    public static final String DELETE_GRE_TUNNEL_URL = "/v1/gre/device/{0}/tunnels/{1}";

    /**
     * URL of Create IPSEC connection in AC Branch
     */
    public static final String CREATE_AC_IPSEC_CONNECTION = "/v1/overlay/batch-create-ipsecs";

    /**
     * URL of Create IPSEC connection in DC gateway
     */
    public static final String CREATE_DCGW_IPSEC_CONNECTION = "/v1/dc-gateway/batch-create-ipsecs";

    /**
     * URL of Create Static Route
     */
    public static final String CREATE_STATIC_ROUTE = "/v1/overlay/batch-create-static-routes";

    /**
     * URL of Delete Static Route
     */
    public static final String DELETE_STATIC_ROUTE = "/v1/overlay/device/{0}/static-routes/{1}";

    /**
     * URL of Delete IPSEC connection in AC Branch
     */
    public static final String DELETE_AC_IPSEC_CONNECTION = "/v1/overlay/ipsec/{0}";

    /**
     * URL of Delete IPSEC connection in DC gateway
     */
    public static final String DELETE_DCGW_IPSEC_CONNECTION = "/v1/dc-gateway/ipsecs/{0}";

    /**
     * URL of Create SNAT
     */
    public static final String CREATE_SNAT = "/rest/svc/nat/v1/snat";

    /**
     * URL of Delete SNAT
     */
    public static final String DELETE_SNAT = "/rest/svc/nat/v1/snat/{0}";

    /**
     * URL of Create VxLan Tunnel
     */
    public static final String CREATE_VXLAN_TUNNEL = "/overlay/v1/vxlan-vpns";

    /**
     * URL of Delete VxLan Tunnel
     */
    public static final String DELETE_VXLAN_TUNNEL_URL = "/overlay/v1/vxlan-vpns/deviceid/{0}/tunnelid/{1}";

    /**
     * URL of Create VxLan Instances
     */
    public static final String BATCH_CREATE_VXLAN_INSTANCE = "/v1/vxlan/batch-create-vxlan";

    /**
     * URL of Delete VxLan Instance
     */
    public static final String REMOVE_VXLAN_INSTANCE = "/v1/vxlan/instance/{0}";

    /**
     * URL of Query Vtep
     */
    public static final String QUERY_VTEP = "/v1/vxlan/device/{0}/vtep";

    private UrlAdapterConst() {

    }
}
