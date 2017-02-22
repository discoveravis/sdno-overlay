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
 * The cpe role.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public enum CpeRole {
    LOCALCPE("localcpe"), CLOUDCPE("cloudcpe"), VPC("vpc");

    private String role;

    private CpeRole(String role) {
        this.setRole(role);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Get cpe role.<br>
     * 
     * @param name The cpe role name
     * @return The cpe role
     * @since SDNO 0.5
     */
    public static CpeRole getCpeRole(String name) {
        for(CpeRole role : CpeRole.values()) {
            if(role.name().equalsIgnoreCase(name) || role.getRole().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return null;
    }
}
