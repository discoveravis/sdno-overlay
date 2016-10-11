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
 * Enumeration class of Transport Type.<br>
 * 
 * @author
 * @version SDNO 0.5 August 19, 2016
 */
public enum TransportType {
    VXLAN_GPE(0);

    private int value;

    TransportType(int value) {
        this.value = value;
    }

    public String getName() {
        switch(this.value) {
            case 0:
                return "vxlan_gpe";

            default:
                return "";
        }
    }
}
