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
 * The vxlan access type.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public enum VxlanAccessType {
    PORT("port"), VLAN("vlan"), PORTVLAN("portvlan");

    private String type;

    private VxlanAccessType(String type) {
        this.setType(type);
    }

    /**
     * Get VxlanAccessType by type name.<br>
     * 
     * @param type The type name
     * @return The VxlanAccessType object
     * @since SDNO 0.5
     */
    public static VxlanAccessType getAccessType(String type) {
        for(VxlanAccessType vxlanType : VxlanAccessType.values()) {
            if(vxlanType.name().equalsIgnoreCase(type) || vxlanType.getType().equalsIgnoreCase(type)) {
                return vxlanType;
            }
        }
        return VxlanAccessType.VLAN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
