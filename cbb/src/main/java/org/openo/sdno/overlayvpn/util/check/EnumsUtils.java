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

package org.openo.sdno.overlayvpn.util.check;

import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.EndpointType;
import org.openo.sdno.overlayvpn.model.common.enums.OperStatus;
import org.openo.sdno.overlayvpn.model.common.enums.RouteModeType;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyRole;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyType;

/**
 * Enumeration class Object Validation.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 2, 2016
 */
public class EnumsUtils {

    private EnumsUtils() {
    }

    /**
     * Validate AdminStatus Data.<br>
     * 
     * @param value value need to validate
     * @return true if value is valid,false otherwise
     * @since SDNO 0.5
     */
    public static boolean isAdminStatusValid(String value) {
        return AdminStatus.ACTIVE.getName().equals(value) || AdminStatus.INACTIVE.getName().equals(value);
    }

    /**
     * Validate OperStatus Data.<br>
     * 
     * @param value value need to validate
     * @return true if value is valid,false otherwise
     * @since SDNO 0.5
     */
    public static boolean isOperStatusValid(String value) {
        return OperStatus.UP.getName().equals(value) || OperStatus.DOWN.getName().equals(value);
    }

    /**
     * Validate EndpointType Data.<br>
     * 
     * @param value value need to validate
     * @return true if value is valid,false otherwise
     * @since SDNO 0.5
     */
    public static boolean isEpgTypeValid(String value) {
        for(EndpointType epgType : EndpointType.values()) {
            if(epgType.getName().equals(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Validate TopologyType Data.<br>
     * 
     * @param topology value need to validate
     * @return true if value is valid,false otherwise
     * @since SDNO 0.5
     */
    public static boolean isTopologyValid(String topology) {
        return TopologyType.FULL_MESH.getName().equals(topology) || TopologyType.HUB_SPOKE.getName().equals(topology)
                || TopologyType.POINT_TO_POINT.getName().equals(topology);
    }

    /**
     * Validate TopologyRole Data.<br>
     * 
     * @param role value need to validate
     * @return if value is valid, false otherwise
     * @since SDNO 0.5
     */
    public static boolean isTopologyRoleValid(String role) {
        for(TopologyRole topologyRole : TopologyRole.values()) {
            if(topologyRole.getName().equals(role)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Validate authMode Data.<br>
     * only "psk" is valid
     * 
     * @param authMode data need to validate
     * @return true if value is valid, false otherwise
     * @since SDNO 0.5
     */
    public static boolean isAuthModeValid(String authMode) {
        return "psk".equals(authMode);
    }

    /**
     * Validate routeMode Data.<br>
     * only STATIC mode is valid
     * 
     * @param routeMode data need to validate
     * @return true if value is valid, false otherwise
     * @since SDNO 0.5
     */
    public static boolean isRouteModevalid(String routeMode) {
        return RouteModeType.STATIC.getName().equals(routeMode);
    }

    /**
     * Validate Boolean Data.<br>
     * 
     * @param booleanStr Boolean data
     * @return true if value is valid, false otherwise
     * @since SDNO 0.5
     */
    public static boolean isBooleanStrValid(String booleanStr) {
        return ("true".equalsIgnoreCase(booleanStr)) || ("false".equalsIgnoreCase(booleanStr));
    }
}
