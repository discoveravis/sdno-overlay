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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.FalsePredicate;

/**
 * VLAN usage Predicate class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public final class VlanUsagePredicate implements Predicate {

    /**
     * <p>
     * ["1-2,4","3"]
     * </p>
     */
    private final List<String> vlanList;

    private VlanUsagePredicate(List<String> vlansLst) {
        this.vlanList = vlansLst;
    }

    /**
     * Get Predicate Instance.<br>
     * 
     * @param vlanList list of VLAN
     * @return Predicate Instance
     * @since SDNO 0.5
     */
    public static Predicate getInstance(List<String> vlanList) {
        if(CollectionUtils.isEmpty(vlanList)) {
            return FalsePredicate.INSTANCE;
        }

        return new VlanUsagePredicate(vlanList);
    }

    public Object getValue() {
        return vlanList;
    }

    /**
     * Evaluate whether the target VLAN overlap with any of the existing VLANs<br>
     * 
     * @param targetVlans - VLAN to be evaluated, ["1-2","3"]
     * @return true- target VLAN overlap with existing VLANs, else return false
     * @since SDNO 0.5
     */
    @Override
    public boolean evaluate(Object targetVlans) {
        if(CollectionUtils.isEmpty(vlanList) || (null == targetVlans) || (!(targetVlans instanceof String))) {
            return false;
        }

        // VLAN range ["1-2","3"]
        String[] targetVlanRanges = String.valueOf(targetVlans).split(",");
        for(String vlans : vlanList) {
            List<String> srcVlanRanges = Arrays.asList(vlans.split(","));
            Collections.sort(srcVlanRanges, new VlanRangeComparator());

            for(String targetVlanRange : targetVlanRanges) {
                if(Collections.binarySearch(srcVlanRanges, targetVlanRange, new VlanRangeComparator()) >= 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
