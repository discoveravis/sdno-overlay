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
 * The route type.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 11, 2017
 */
public enum RouteType {
    STATIC("static", "staticroute"), POLICY("policy", "policyroute");

    private String type;

    private String fullName;

    private RouteType(String type, String fullName) {
        this.setType(type);
        this.setFullName(fullName);
    }

    /**
     * Get RouteType by type name.<br>
     * 
     * @param type The type name
     * @return The RouteType object
     * @since SDNO 0.5
     */
    public static RouteType getRouteType(String type) {
        for(RouteType route : RouteType.values()) {
            if(route.getType().equalsIgnoreCase(type) || route.name().equalsIgnoreCase(type)
                    || route.getFullName().equalsIgnoreCase(type)) {
                return route;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
