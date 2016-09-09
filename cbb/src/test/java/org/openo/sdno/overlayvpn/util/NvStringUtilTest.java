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

package org.openo.sdno.overlayvpn.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.model.NvString;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.util.NvStringUtil;

/**
 * NvStringUtil test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-14
 */
public class NvStringUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testToggleMdfNvValue() {
        List<NvString> nvStringList = new ArrayList<NvString>();
        NvString nvStr = new NvString("uuid01", "modfiyMask", "add", "parentUuid01");
        nvStringList.add(nvStr);
        NvStringUtil.toggleMdfNvValue(nvStringList);
        assertEquals(CommConst.MODIFY_MASK_DELETE, nvStringList.get(0).getValue());
    }

    @Test
    public void testToggleMdfNvValue2() {
        List<NvString> nvStringList = new ArrayList<NvString>();
        NvString nvStr = new NvString("uuid01", "modfiyMask", "delete", "parentUuid01");
        nvStringList.add(nvStr);
        NvStringUtil.toggleMdfNvValue(nvStringList);
        assertEquals(CommConst.MODIFY_MASK_ADD, nvStringList.get(0).getValue());
    }

    @Test
    public void testAddNvString4Model() {
        Gateway model = new Gateway();
        model.setUuid("modelUuid01");

        NvString nvStr = new NvString("uuid01", "modfiyMask", "delete", "parentUuid01");
        NvStringUtil.addNvString4Model(nvStr, model);

        assertEquals("modelUuid01", model.getAdditionalInfo().get(0).getFirstParentUuid());
    }

    @Test
    public void testAddNvString4Model2() {
        Gateway model = new Gateway();
        model.setUuid("modelUuid01");

        NvString nvStr1 = new NvString("uuid01", "modfiyMask", "delete", "parentUuid01");
        NvString nvStr2 = new NvString("uuid02", "modfiyMask", "delete", "parentUuid02");
        List<NvString> nvStringList = new ArrayList<NvString>();
        nvStringList.add(nvStr2);
        model.setAdditionalInfo(nvStringList);

        NvStringUtil.addNvString4Model(nvStr1, model);

        assertEquals("modelUuid01", model.getAdditionalInfo().get(0).getFirstParentUuid());
    }
}
