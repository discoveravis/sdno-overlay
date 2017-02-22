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
 * The tunnel type.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public enum TunnelType {
    VXLAN("vxlan"), GRE("gre"), IPSEC("ipsec"), VLAN("vlan");

    private String technology;

    private TunnelType(String technology) {
        this.setTechnology(technology);
    }

    /**
     * Get TunnelType by type name.<br>
     * 
     * @param name The type name
     * @return The TunnelType object
     * @since SDNO 0.5
     */
    public static TunnelType getTunnelType(String name) {
        for(TunnelType type : TunnelType.values()) {
            if(type.name().equalsIgnoreCase(name) || type.getTechnology().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

}
