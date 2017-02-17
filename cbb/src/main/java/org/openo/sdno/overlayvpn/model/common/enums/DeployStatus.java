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

package org.openo.sdno.overlayvpn.model.common.enums;

/**
 * The class of deploy status. <br>
 * 
 * @author
 * @version SDNO 0.5 2017-2-16
 */
public enum DeployStatus {
    DEPLOY(0), UNDEPLOY(1), PART_DEPLOY(2);

    private int value;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value The deploy status.
     */
    DeployStatus(int value) {
        this.value = value;
    }

    /**
     * It is used to get deploy status name. <br>
     * 
     * @return The deploy status name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "deploy";
            case 1:
                return "undeploy";
            case 2:
                return "partdeploy";
            default:
                return "";
        }
    }

}
