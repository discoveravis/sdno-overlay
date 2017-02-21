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

package org.openo.sdno.overlayvpn.frame.dao.model;

/**
 * The enumeration class of action state.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
public enum ActionState {
    UNKNOWN(0, "None"),

    NORMAL(1, "Normal"),

    CREATING(2, "Creating"),

    DELETING(3, "Deleting"),

    UPDATING(4, "Updating"),

    DEPLOYING(5, "Deploying"),

    UNDEPLOYING(6, "Undeploying"),

    CREATE_EXCEPTION(7, "Create_Exception"),

    UPDATE_EXCEPTION(8, "Update_Exception"),

    DELETE_EXCEPTION(9, "Delete_Exception"),

    DEPLOY_EXCEPTION(10, "Deploy_Exception"),

    UNDEPLOY_EXCEPTION(11, "Undeploy_Exception"),

    CHECK_EXCEPTION(12, "Check_Exception");

    private int value;

    private String state;

    ActionState(int value, String state) {
        this.setValue(value);
        this.setState(state);
    }

    /**
     * Get action state from value.<br>
     * 
     * @param value The value
     * @return The action state
     * @since SDNO 0.5
     */
    public static ActionState fromValue(int value) {
        for(ActionState action : ActionState.values()) {
            if(value == action.getValue()) {
                return action;
            }
        }
        return ActionState.UNKNOWN;
    }

    /**
     * Get action state from state.<br>
     * 
     * @param state The state
     * @return The action state
     * @since SDNO 0.5
     */
    public static ActionState fromState(String state) {
        for(ActionState action : ActionState.values()) {
            if(action.getState().equalsIgnoreCase(state)) {
                return action;
            }
        }
        return ActionState.UNKNOWN;
    }

    /**
     * Whether is on doing.<br>
     * 
     * @return true when name end with ING,otherwise is false
     * @since SDNO 0.5
     */
    public boolean isDoing() {
        return this.name().endsWith("ING");
    }

    /**
     * Whether is exception.<br>
     * 
     * @return true when name end with EXCEPTION,otherwise is false
     * @since SDNO 0.5
     */
    public boolean isException() {
        return this.name().endsWith("EXCEPTION");
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
