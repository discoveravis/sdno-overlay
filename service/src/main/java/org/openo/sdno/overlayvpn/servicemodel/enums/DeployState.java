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
 * The DeployState.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 10, 2017
 */
public enum DeployState {
    UNKNOWN(0, "unknown"), DEPLOY(1, "deploy"), UNDEPLOY(2, "undeploy");

    private int value;

    private String state;

    private DeployState(int value, String state) {
        this.value = value;
        this.state = state;
    }

    /**
     * Get DeployState from deploy state value.<br>
     * 
     * @param value The deploy state value
     * @return The DeployState
     * @since SDNO 0.5
     */
    public static DeployState fromValue(int value) {
        for(DeployState admin : DeployState.values()) {
            if(value == admin.value) {
                return admin;
            }
        }
        return DeployState.UNKNOWN;
    }

    /**
     * Get DeployState from deploy state.<br>
     * 
     * @param state The deploy state
     * @return The DeployState
     * @since SDNO 0.5
     */
    public static DeployState fromState(String state) {
        for(DeployState admin : DeployState.values()) {
            if(admin.state.equalsIgnoreCase(state)) {
                return admin;
            }
        }
        return DeployState.UNKNOWN;
    }

    public String getState() {
        return state;
    }
}
