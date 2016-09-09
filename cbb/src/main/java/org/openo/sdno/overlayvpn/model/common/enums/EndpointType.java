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

package org.openo.sdno.overlayvpn.model.common.enums;

/**
 * The class of EndPoint type. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public enum EndpointType {
    SUBNET(1), CIDR(2), NETWORK(3), ROUTER(4), PORT(5), PORT_VLAN(6), VNI(7), VLAN(8), VPC(9);

    private static final String[] NAME_LIST =
            new String[] {"subnet", "cidr", "network", "router", "port", "port-and-vlan", "vni", "vlan", "vpc"};

    private int value;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value The endpoint type
     */
    EndpointType(int value) {
        this.value = value;
    }

    /**
     * It is used to get endpoint type name. <br>
     * 
     * @return The endpoint type name.
     * @since SDNO 0.5
     */
    public String getName() {
        if(value >= 1 && value <= 9) {
            return NAME_LIST[value - 1];
        }

        return "";
    }

}
