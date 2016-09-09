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

package org.openo.sdno.overlayvpn.errorcode;

/**
 * Class with common error codes<br>
 * 
 * @author
 * @version SDNO 0.5 30-May-2016
 */
public class ErrorCode {

    /**
     * Success.
     */
    public static final String OVERLAYVPN_SUCCESS = "overlayvpn.operation.success";

    /**
     * Part Success.
     */
    public static final String OVERLAYVPN_PART_SUCCESS = "overlayvpn.operation.part_success";

    /**
     * Fail.
     */
    public static final String OVERLAYVPN_FAILED = "overlayvpn.operation.failed";

    /**
     * Parameter invalid.
     */
    public static final String OVERLAYVPN_PARAMETER_INVALID = "overlayvpn.operation.paramter_invalid";

    /**
     * Response Fail.
     */
    public static final String OVERLAYVPN_DECODE_RESPONSE_FAIL = "overlayvpn.decode.response.fail";

    /**
     * Resource not exist.
     */
    public static final String OVERLAYVPN_RESOURCE_NOT_EXIST = "overlayvpn.resource.not_exist";

    /**
     * Tenant invalid.
     */
    public static final String OVERLAYVPN_TENANT_INVALID = "overlayvpn.tenant.id_invalid";

    /**
     * Read security file failed.
     */
    public static final String OVERLAYVPN_READ_SECURITY_FILE_FAILED = "overlayvpn.read.security.file.failed";

    /**
     * Insert failed.
     */
    public static final String OVERLAYVPN_DATABASE_INSERT_FAILED = "overlayvpn.database.insert_fail";

    /**
     * Delete failed.
     */
    public static final String OVERLAYVPN_DATABASE_DELETE_FAILED = "overlayvpn.database.delete_fail";

    /**
     * Update failed
     */
    public static final String OVERLAYVPN_DATABASE_UPDATE_FAILED = "overlayvpn.database.update_fail";

    /**
     * Query failed.
     */
    public static final String OVERLAYVPN_DATABASE_QUERY_FAILED = "overlayvpn.database.query_fail";

    /**
     * Resource allocate failed.
     */
    public static final String RESOURCE_ALLOC_FAILED = "overlayvpn.resource.alloc_failed";

    /**
     * Restful communication failed.
     */
    public static final String RESTFUL_COMMUNICATION_FAILED = "overlayvpn.restful.communication_failed";

    /**
     * OverlayVpn not inactive.
     */
    public static final String VPN_STATUS_NOT_INACTIVE = "overlayvpn.vpn.status_not_inactive";

    /**
     * OverlayVpn do not contain hub.
     */
    public static final String VPN_NOT_CONTAIN_HUB_EPG = "overlayvpn.deploy.not_contain_hub";

    /**
     * Connection not complete.
     */
    public static final String OVERLAYVPNSVC_CONN_NOT_COMPLETE = "overlayvpn.vpnconnection.not_complete";

    /**
     * EndpointGroup not complete.
     */
    public static final String OVERLAYVPNSVC_EPG_NOT_COMPLETE = "overlayvpn.endpointgroup.not_complete";

    /**
     * TerminationPoint not complete.
     */
    public static final String OVERLAYVPNSVC_TP_NOT_COMPLETE = "overlayvpn.terminationpoint.not_complete";

    /**
     * Database Operation Error.
     */
    public static final String DB_OPERATION_ERROR = "overlayvpn.db.operation.error";

    /**
     * Database Return Error.
     */
    public static final String DB_RETURN_ERROR = "overlayvpn.db.return.error";

    /**
     * Filter not support.
     */
    public static final String DB_FILLTER_NOT_SUPPORT = "overlayvpn.db.fillter.error";

    /**
     * Connection response fail.
     */
    public static final String ADAPTER_CONNECTOR_RESPONSE_FAIL = "overlay.vpn.apater.connctor.response.fail";

    /**
     * Router Response Fail.
     */
    public static final String ADAPTER_ROUTER_RESPONSE_FAIL = "overlay.vpn.apater.router.response.fail";

    /**
     * Router Response fail.
     */
    public static final String CONNCTOR_ROUTER_RESPONSE_FAIL = "overlay.vpn.connector.router.response.fail";

    /**
     * Common Parameter Configuration not exist.
     */
    public static final String COMMON_CONFIG_NOT_EXIST = "compositevpn.commcfg.not_exist";

    /**
     * Resource not exist
     */
    public static final String RESOURCE_NOT_EXIST = "vxlansvc.resource.not_exist";

    /**
     * NE resources do not exist.
     */
    public static final String RESOURCE_NETWORKELEMENT_NOT_EXIST = "vxlansvc.networkelement.not_exist";

    public static final String RESOURCE_TENANT_NOT_EXIST = "vxlansvc.tenant.not_exist";

    public static final String RESOURCE_SITE_NOT_EXIST = "vxlansvc.site.not_exist";

    public static final String RESOURCE_PORT_NOT_EXIST = "vxlansvc.port.not_exist";

    public static final String RESOURCE_NETWORKELEMENT_NOT_COTROLLER_EXIST =
            "vxlansvc.networkelement.nocontroller_exist";

    // Network Element
    public static final String OVERLAYVPN_NETWORK_ELEMENT_NOT_EXIST = "overlayvpn.network.element.not.exist";

    private ErrorCode() {
    }
}
