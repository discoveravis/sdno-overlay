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

package org.openo.sdno.overlayvpn.util.check;

/**
 * VLAN range comparator class.<br>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public class VlanRangeComparator implements RangeComparator<String> {

    /**
     * Compare whether VLAN ranges overlap.<br>
     * 
     * @param vlanRange1 - VLAN range for input1
     * @param vlanRange2- VLAN range for input2
     * @return 1,-1 when they do not overlap, 0- if they overlap
     * @since SDNO 0.5
     */
    @Override
    public int compare(String vlanRange1, String vlanRange2) {
        String[] vlans1 = vlanRange1.split("-");
        String[] vlans2 = vlanRange2.split("-");
        int minOfVlanRange1 = Integer.parseInt(vlans1[0]);
        int maxOfVlanRange1 = Integer.parseInt(vlans1[vlans1.length - 1]);
        int minOfVlanRange2 = Integer.parseInt(vlans2[0]);
        int maxOfVlanRange2 = Integer.parseInt(vlans2[vlans2.length - 1]);

        if(minOfVlanRange1 > maxOfVlanRange2) {
            return 1;
        } else if(minOfVlanRange2 > maxOfVlanRange1) {
            return -1;
        } else {
            return 0;
        }
    }
}
