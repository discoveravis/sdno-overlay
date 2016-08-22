/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.model.common.enums.topo;

/**
 * The class of topology type. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public enum TopologyType {
    NONE(0), POINT_TO_POINT(1), FULL_MESH(2), HUB_SPOKE(3);

    private int value;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param value The topology type.
     */
    TopologyType(int value) {
        this.value = value;
    }

    /**
     * It is used to get topology type name. <br/>
     * 
     * @return The topology type name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "none";
            case 1:
                return "point_to_point";
            case 2:
                return "full_mesh";
            case 3:
                return "hub_spoke";
            default:
                return "";
        }
    }

}
