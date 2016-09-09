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
 * The class of modify mask type. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public enum ModifyMaskType {
    NOMODIFY(0), ADD(1), DELETE(2), MODIFY(3), MIDDLECOMPARE(4), DEPLOY(5), UNDEPLOY(6);

    private static final String[] NAME_LIST =
            new String[] {"NOMODIFY", "ADD", "DELETE", "MODIFY", "MIDDLECOMPARE", "DEPLOY", "UNDEPLOY"};

    private int value;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param value
     *            The modify mask type
     */
    ModifyMaskType(int value) {
        this.value = value;
    }

    /**
     * It is used to get modify mask type name. <br>
     * 
     * @return The mask type name.
     * @since SDNO 0.5
     */
    public String getName() {

        if(value >= 0 && value < NAME_LIST.length) {
            return NAME_LIST[value];
        }

        return "";
    }
}
