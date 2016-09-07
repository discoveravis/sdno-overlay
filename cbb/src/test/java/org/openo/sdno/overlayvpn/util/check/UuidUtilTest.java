/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.util.check;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;

/**
 * UuidUtil test class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class UuidUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testValidate() {
        assertTrue(UuidUtil.validate("123456"));
        assertFalse(UuidUtil.validate("123456++++"));
    }

    @Test
    public void testValidate2() {
        List<String> uuids = new ArrayList<String>();
        uuids.add("123456");
        uuids.add("456789");
        assertTrue(UuidUtil.validate(uuids));

    }

    @Test
    public void testValidate3() {
        List<String> uuids = new ArrayList<String>();

        assertTrue(UuidUtil.validate(uuids));
    }

    @Test
    public void testValidate4() {
        List<String> uuids = new ArrayList<String>();
        uuids.add("123456");
        uuids.add("456789");
        uuids.add("123+++");
        assertFalse(UuidUtil.validate(uuids));
    }
}
