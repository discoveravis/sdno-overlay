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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VlanRangeComparatorTest {

    private static String vlanRange1 = "1-2";

    private static String vlanRange2 = "3-4";

    private static String vlanRange3 = "1-3";

    @Test
    public void testCompare1() {
        VlanRangeComparator comparator = new VlanRangeComparator();
        assertTrue(comparator.compare(vlanRange1, vlanRange2) == -1);
    }

    @Test
    public void testCompare2() {
        VlanRangeComparator comparator = new VlanRangeComparator();
        assertTrue(comparator.compare(vlanRange2, vlanRange1) == 1);
    }

    @Test
    public void testCompare3() {
        VlanRangeComparator comparator = new VlanRangeComparator();
        assertTrue(comparator.compare(vlanRange1, vlanRange3) == 0);
    }

}
