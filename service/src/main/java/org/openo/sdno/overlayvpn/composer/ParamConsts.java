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

/**
 * The constant of parameters.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
public class ParamConsts {

    /**
     * Common template.
     */
    public static final String TEMPLATE_COMMON = "template_common";

    /**
     * The position of gateway in site to dc.
     */
    public static final String SITE_DC_VPNGW = "site2dcGwPosition";

    /**
     * The cloudcpe information.
     */
    public static final String CLOUDCPEINFO = "cloudCpeInfo";

    /**
     * The thincpe information.
     */
    public static final String LOCALCPEINFO = "localCpeInfo";

    /**
     * The connections
     */
    public static final String CONNECTIONS = "connections";

    /**
     * The ipsec policy.
     */
    public static final String IPSEC_POLICY = "ipsecPolicy";

    /**
     * The ike policy.
     */
    public static final String IKE_POLICY = "ikePolicy";

    /**
     * The gateway policy.
     */
    public static final String GW_POLICY = "gwPolicy";

    /**
     * Nqa protect policy.
     */
    public static final String POLICY_NQA = "nqa";

    /**
     * Cloud basic route, and binded vCPE.
     */
    public static final String BASIC_ROUTE = "BASIC_ROUTE";

    /**
     * L2CPE site to dc route policy.
     */
    public static final String ROUTE_ENT_L2_DC = "enterprise_l2cpe_dc";

    /**
     * The route policy between localCpe and cloudCpe.
     */
    public static final String ROUTE_ENT_L2_INTERNET = "enterprise_l2cpe_internet";

    /**
     * Source IP
     */
    public static final String KEY_SRCIP = "srcIp";

    /**
     * Destination IP
     */
    public static final String KEY_DSTIP = "dstIp";

    /**
     * Source port
     */
    public static final String KEY_SRCPORT = "srcPort";

    /**
     * Remote
     */
    public static final String TYPE_REMOTE = "remote";

    /**
     * IP regular expression
     */
    public static final String REGX_IP_IP = "ip:[0-9]+.[0-9]+.[0-9]+.[0-9]+";

    /**
     * Interface regular expression
     */
    public static final String REGX_IP_INTERFACE = "interface:((remote)|(local)):[^:]+";

    /**
     * Ne uuid
     */
    public static final String ME_PARAM = "meID";

    /**
     * name
     */
    public static final String NAME_PARAM = "name";

    /**
     * Uuid
     */
    public static final String ATTR_ID = "id";

    /**
     * Cidr zero
     */
    public static final String CIDR_ZERO = "0.0.0.0/0";

    /**
     * Max doing seconds
     */
    public static final Integer MAX_DOING_SECONDS = 600;

    private ParamConsts() {

    }
}
