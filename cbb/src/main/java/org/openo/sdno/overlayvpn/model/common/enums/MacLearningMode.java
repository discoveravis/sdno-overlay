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
 * The class of mac learning mode. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public enum MacLearningMode {
    DATA_PLANE(0), CONTROL_PLANE(1);

    private int value;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value The mac learning mode.
     */
    MacLearningMode(int value) {
        this.value = value;
    }

    /**
     * It is used to get mac learning mode name. <br>
     * 
     * @return The mac learning mode name.
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "data-plane";
            case 1:
                return "control-plane";
            default:
                return "";
        }
    }
}
