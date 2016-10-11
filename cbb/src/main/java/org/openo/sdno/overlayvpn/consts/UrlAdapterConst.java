/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.consts;

/**
 * Adapter Service URL Definition Class.<br>
 * <p>
 * Constants class with the URLs used by the REST services<br>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 1, 2016
 */
public class UrlAdapterConst {

    /**
     * Prefix URL of IPSec Adapter Service
     */
    public static final String IPSEC_ADAPTER_BASE_URL = "/openoapi/sbi-ipsec";

    /**
     * Prefix URL of IPSec Adapter Service
     */
    public static final String VXLAN_ADAPTER_BASE_URL = "/openoapi/sbi-vxlan";

    /**
     * Prefix URL of IPSec Adapter Service
     */
    public static final String WAN_INTERFACE_ADAPTER_BASE_URL = "/openoapi/sbi-waninterface";

    /**
     * Prefix URL of Catalog Adapter Service
     */
    public static final String CATALOG_ADAPTER_BASE_URL = "/openoapi/catalog";

    public static final String OVERLAY_BASE_URL = "/openoapi/sdnooverlay";

    public static final String L3VPN_BASE_URL = "/openoapi/sdnol3vpn";

    public static final String L2VPN_BASE_URL = "/openoapi/sdnol2vpn";

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
     * URL of Query VTEP
     */
    public static final String QUERY_VTEP = "/v1/vxlan/device/{0}/vtep";

    /**
     * URL of Query Template Information
     */
    public static final String QUERY_TEMPLATE_INFORMATION = "/v1/servicetemplates/{0}";

    public static final String CREATE_OVERLAY = "/v1/site2dc-vpn";

    public static final String DELETE_OVERLAY = "/v1/site2dc-vpn/{0}";

    public static final String CREATE_L3VPN = "/v1/l3vpns";

    public static final String DELETE_L3VPN = "/v1/l3vpns/{0}";

    public static final String CREATE_L2VPN = "/v1/l2vpns";

    public static final String DELETE_L2VPN = "/v1/l2vpns/{0}";

    private UrlAdapterConst() {

    }
}
