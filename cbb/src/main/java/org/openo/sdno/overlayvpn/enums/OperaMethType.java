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

package org.openo.sdno.overlayvpn.enums;

/**
 * Basic Operation type.<br>
 * 
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
public enum OperaMethType {
    CREATE(0), DELETE(1), DEPLOY(2), UNDEPLOY(3), UPDATE(4);

    private int value;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value
     */
    OperaMethType(int value) {
        this.value = value;
    }

    /**
     * Get name of Operation type.<br>
     * 
     * @return name of Operation type
     * @since SDNO 0.5
     */
    public String getName() {
        switch(value) {
            case 0:
                return "create";
            case 1:
                return "delete";
            case 2:
                return "deploy";
            case 3:
                return "unploy";
            case 4:
                return "update";
            default:
                return "";
        }
    }
}
