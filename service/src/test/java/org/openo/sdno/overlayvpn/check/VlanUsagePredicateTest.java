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

package org.openo.sdno.overlayvpn.check;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.sdno.overlayvpn.util.check.VlanUsagePredicate;

public class VlanUsagePredicateTest {

    private static List<String> vlanList;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        vlanList = new ArrayList<String>();
        vlanList.add("1-2");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        vlanList.clear();
    }

    @Test
    public void testGetValue() {
        VlanUsagePredicate vlanUsagePredicate = (VlanUsagePredicate)VlanUsagePredicate.getInstance(vlanList);
        assertEquals(vlanList, vlanUsagePredicate.getValue());
    }

    @Test
    public void testEvaluateNegtive() {
        VlanUsagePredicate vlanUsagePredicate = (VlanUsagePredicate)VlanUsagePredicate.getInstance(vlanList);
        assertEquals(false, vlanUsagePredicate.evaluate(null));
    }

    @Test
    public void testEvaluate() {
        VlanUsagePredicate vlanUsagePredicate = (VlanUsagePredicate)VlanUsagePredicate.getInstance(vlanList);
        assertEquals(true, vlanUsagePredicate.evaluate("1-2"));
    }

}
