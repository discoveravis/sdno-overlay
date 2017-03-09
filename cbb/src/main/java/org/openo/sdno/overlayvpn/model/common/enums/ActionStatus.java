/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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
 * The class of action status. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public enum ActionStatus {
    NONE(0), NORMAL(1), CREATING(2), DELETING(3), UPDATING(4), CREATE_EXCEPTION(5), DELETE_EXCEPTION(6),
    UPDATE_EXCEPTION(7);

    private static final String[] NAME_LIST = new String[] {"none", "normal", "creating", "deleting", "updating",
                    "create_exception", "delete_exception", "update_exception"};

    private int value;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value The action status.
     */
    ActionStatus(int value) {
        this.value = value;
    }

    /**
     * It is used to get action status name. <br>
     * 
     * @return The action status name.
     * @since SDNO 0.5
     */
    public String getName() {

        if(value >= 0 && value < NAME_LIST.length) {
            return NAME_LIST[value];
        }

        return "";
    }

}
