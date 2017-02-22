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

package org.openo.sdno.overlayvpn.servicemodel.enums;

/**
 * The deploy position.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 13, 2017
 */
public enum DeployPosition {
    LOCALFIRST, CLOUDFIRST;

    private DeployPosition() {

    }

    /**
     * Get deploy position.<br>
     * 
     * @param position The deploy position name
     * @return The deploy position
     * @since SDNO 0.5
     */
    public static DeployPosition getDeployPosition(String position) {
        for(DeployPosition deployPosition : DeployPosition.values()) {
            if(deployPosition.name().equalsIgnoreCase(position)) {
                return deployPosition;
            }
        }
        return null;
    }

    /**
     * Get cpe role by deploy position.<br>
     * 
     * @param position The deploy position name
     * @return The cpe role
     * @since SDNO 0.5
     */
    public static CpeRole getCpeRole(String position) {
        CpeRole role = null;
        DeployPosition deployPosition = getDeployPosition(position);
        if(deployPosition == null) {
            return role;
        }
        switch(deployPosition) {
            case LOCALFIRST:
                role = CpeRole.LOCALCPE;
                break;
            case CLOUDFIRST:
                role = CpeRole.CLOUDCPE;
                break;
            default:
                role = null;
                break;
        }
        return role;
    }
}
