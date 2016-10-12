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
 * Common Constants Class.<br>
 * <p>
 * This class defines some Constants for other modules.
 * </p>
 * 
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
public class CommConst {

    /**
     * Chinese environment
     */
    public static final String LANGUAGE_ZH_CN = "zh_CN";

    /**
     * Default separator
     */
    public static final String DEFAULT_STR_SEPARATOR = ",";

    /**
     * max GRE tunnel key.
     */
    public static final Long GRE_TUNNEL_KEY_MAX = 4294967295L;

    /**
     * max length of PSK
     */
    public static final int PSK_MAX_LENGTH = 127;

    /**
     * max length of name
     */
    public static final int NAME_MAX_LENGTH = 128;

    /**
     * max length of description
     */
    public static final int DESCRIPTION_MAX_LENGTH = 256;

    /**
     * VPN Technology type:Vxlan
     */
    public static final String VPN_TEC_VXLAN = "VpnTechnology_VXLAN";

    /**
     * VPN Technology type:ipsec
     */
    public static final String VPN_TEC_IPSEC = "VpnTechnology_IPSEC";

    public static final String BANDWITH_TOTAL_MASK = "totalBandwidth";

    public static final String BANDWITH_UP_RATIO_MASK = "upstreamRatio";

    public static final long BANDWITH_TOTAL_MAX = 4294967L;

    public static final long BANDWITH_TOTAL_MIN = 0;

    public static final int BANDWITH_UP_RATIO_MAX = 100;

    public static final int BANDWITH_UP_RATIO_MIN = 0;

    public static final String STATIC_ROUTE_DES_MASK = "destination";

    public static final String STATIC_ROUTE_NXT_MASK = "nexthop";

    public static final String BGP_PEER_AS_MASK = "peerAsNumber";

    public static final String BGP_PEER_IP_MASK = "peerIp";

    public static final int BGP_PEER_AS_MAX = 65535;

    public static final int BGP_PEER_AS_MIN = 1;

    public static final String DELETE_PARAM_INTERNETACCESS = "delInternetAccess";

    public static final String DELETE_PARAM_PUBLICIPPOOL = "delPublicIpPool";

    public static final String DELETE_PARAM_LOCALNETWORK = "delLocalNetwork";

    public static final String MODIFY_MASK = "modfiyMask";

    public static final String MODIFY_MASK_ADD = "add";

    public static final String MODIFY_MASK_DELETE = "delete";

    public static final String MODIFY_MASK_MODIFY = "modify";

    /**
     * Inventory Resource in used
     */
    public static final int INVENTORY_RESOURCE_IS_USED = 1;

    /**
     * Inventory Resource is not used
     */
    public static final int INVENTORY_RESOURCE_IS_NOT_USED = 0;

    /**
     * Verify Type:Port
     */
    public static final String VERIFY_SRC_TYPE_PORT = "port";

    /**
     * Verify Type:Network Element
     */
    public static final String VERIFY_SRC_TYPE_NVE = "networkelement";

    /**
     * Verify Type:Link
     */
    public static final String VERIFY_SRC_TYPE_LINK = "link";

    /**
     * Verify Type:Site
     */
    public static final String VERIFY_SRC_TYPE_SITE = "site";

    /**
     * Verify Type:Tenant
     */
    public static final String VERIFY_SRC_TYPE_TENANT = "tenant";

    /**
     * Verify Type:Controller
     */
    public static final String VERIFY_SRC_TYPE_CONTROLLER = "controller";

    /**
     * IP ResPool name of GRE
     */
    public static final String RES_IP_POOLNAME_GRE = "overlayvpnGreIP";

    /**
     * VNI ResPool name of VxLan
     */
    public static final String RES_VNI_POOLNAME_VXLAN = "overlayvpnVxlanVni";

    public static final String RES_VXALN_USER_LABEL = "org.openo.sdno.vxlanservice";

    /**
     * Maximum VNI value
     */
    public static final Long VXLAN_VNI_MAX = 16777215L;

    /**
     * Minimum VNI value
     */
    public static final Long VXLAN_VNI_MIN = 1L;

    /**
     * Maximum VLAN ID
     */
    public static final Long VLAN_MAX = 4094L;

    /**
     * Minimum VLAN ID
     */
    public static final Long VLAN_MIN = 1L;

    /**
     * CPE import and export
     */
    public static final int IMPORT_MAX_RECORD_LENGTH = 1000;

    public static final String CPE_FILE = "cpe_file";

    public static final int CPE_FILE_MAX_NUM = 5;

    /**
     * tunnel type of GRE
     */
    public static final String TUNNEL_TYPE_GRE = "gre";

    /**
     * network element type: hub
     */
    public static final String CONST_DEVICE_TYPE_HUB = "hub";

    /**
     * network element type: spoke
     */
    public static final String CONST_DEVICE_TYPE_SPOKE = "spoke";

    /**
     * tunnel prefix
     */
    public static final String CONST_TUNNEL_NAME_PRE = "tunnel 0/0/";

    /**
     * max id of tunnel FlowId
     */
    public static final Integer CONST_TUNNEL_FLOWID_MAX = 31;

    public static final String CREATE_OK_TYPE_TUNNEL = "greTunnel";

    public static final String CREATE_OK_TYPE_IPSEC = "ipsec";

    /**
     * Interval(milliseconds) of Query WanSubInf when IP not obtained
     */
    public static final int GET_WAN_IP_WAIT_TIME = 500;

    /**
     * Device Create Parameter
     */
    public static final String CREATE_DEVICE_PARAMETER = "deviceList";

    /**
     * Device Delete Parameter
     */
    public static final String DELETE_DEVICE_PARAMETER = "deviceIdList";

    /**
     * Device Network Delete Parameter
     */
    public static final String DELETE_DEVICE_NETWORK_PARAMETER = "ids";

    /**
     * Device Query Filter Key:esn
     */
    public static final String QUERY_DEVICE_KEY_ESN = "esn";

    /**
     * Device id Parameter
     */
    public static final String DEVICE_ID_PARAMETER = "deviceid";

    /**
     * Device Parameter
     */
    public static final String DEVICE_PARAMETER = "device";

    /**
     * VxLan id Parameter
     */
    public static final String VXLAN_ID_PARAMETER = "vxlanid";

    /**
     * VLAN Parameter
     */
    public static final String VLAN_PARAMETER = "vlan";

    /**
     * VNI Id parameter
     */
    public static final String VNI_ID_PARAMETER = "vniid";

    /**
     * Device WanSubInf Type Parameter
     */
    public static final String DEVICE_WAN_SUB_INTERFACE_TYPE_PARAMETER = "type";

    /**
     * Device Reboot Parameter
     */
    public static final String REBOOT_DEVICE_PARAMETER = "parameter";

    /**
     * Network Configuration List
     */
    public static final String NETWORK_CONFIG_LIST = "networkConfigList";

    /**
     * GRE Tunnel List
     */
    public static final String GRE_TUNNEL_LIST = "tunnelList";

    /**
     * VxLan List
     */
    public static final String VXLAN_LIST = "vxlanList";

    /**
     * IpSec List
     */
    public static final String IP_SEC_LIST = "ipsecList";

    /**
     * QOS Policy List
     */
    public static final String QOS_POLICY_LIST = "trafficPolicyList";

    /**
     * QOS Car List
     */
    public static final String QOS_CAR_LIST = "trafficQosListDto";

    /**
     * Network id Parameter of PublicIp
     */
    public static final String NETWORK_ID_PARAMETER = "networkid";

    /**
     * IP Address Parameter
     */
    public static final String IP_ADDRESS_PARAMETER = "ipaddr";

    /**
     * IP Mask Parameter
     */
    public static final String IP_MASK_PARAMETER = "ipmask";

    /**
     * Next Hop Parameter
     */
    public static final String NEXT_HOP_PARAMETER = "nexthop";

    /**
     * PostFix of QOS Stream name
     */
    public static final String QOS_AF = "_af";

    public static final String QOS_EF = "_ef";

    public static final String QOS_BE = "_be";

    private CommConst() {

    }
}
