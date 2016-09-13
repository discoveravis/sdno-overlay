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

package org.openo.sdno.overlayvpn.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.exception.FillOverlayVpnResRsp;
import org.openo.sdno.resource.ResourceUtil;

import mockit.Mock;
import mockit.MockUp;

/**
 * FillOverlayVpnResRsp test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-12
 */
public class FillOverlayVpnResRspTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCommConfigNotExist() {

        new MockUp<ResourceUtil>() {

            @Mock
            public String getMessage(String str) {
                return "Test_{param}";
            }
        };

        ResultRsp<?> rsp = FillOverlayVpnResRsp.commConfigNotExist("test");
        assertEquals(ErrorCode.COMMON_CONFIG_NOT_EXIST, rsp.getErrorCode());
        assertEquals("Test_test", rsp.getDetailArg());
    }

    @Test
    public void testInventoryResNotExist() {
        new MockUp<ResourceUtil>() {

            @Mock
            public String getMessage(String str) {
                return "Test_{resname}_{resdesc}";
            }
        };

        ResultRsp<?> rsp = FillOverlayVpnResRsp.inventoryResNotExist("TestResName", "TestResDesc");
        assertEquals(ErrorCode.OVERLAYVPN_RESOURCE_NOT_EXIST, rsp.getErrorCode());
        assertEquals("Test_TestResName_TestResDesc", rsp.getDetailArg());
    }

    @Test
    public void testAllocResFailed() {
        new MockUp<ResourceUtil>() {

            @Mock
            public String getMessage(String str) {
                return "Test_{restype}_{num}";
            }
        };

        ResultRsp<?> rsp = FillOverlayVpnResRsp.allocResFailed("TestResType", 1);
        assertEquals(ErrorCode.RESOURCE_ALLOC_FAILED, rsp.getErrorCode());
        assertEquals("Test_TestResType_1", rsp.getDetailArg());
    }
}
