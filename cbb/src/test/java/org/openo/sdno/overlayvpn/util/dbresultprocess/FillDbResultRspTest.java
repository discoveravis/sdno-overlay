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

package org.openo.sdno.overlayvpn.util.dbresultprocess;

import static org.junit.Assert.assertEquals;
import mockit.Mock;
import mockit.MockUp;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.dbresultprocess.FillDbResultRsp;
import org.openo.sdno.resource.ResourceUtil;

/**
 * FillDbResultRsp test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class FillDbResultRspTest {

    @Before
    public void setUp() throws Exception {
        new MockUp<ResourceUtil>() {

            @Mock
            public String getMessage(String str) {
                return "FillDbResultRspTest Message";
            }
        };
    }

    @Test
    public void testFillQueryResultRsp() {
        ResultRsp<OverlayVpn> retRsp = new ResultRsp<>(ErrorCode.OVERLAYVPN_DATABASE_QUERY_FAILED);
        FillDbResultRsp.fillQueryResultRsp(retRsp, "[UUID] NULL");
        assertEquals(retRsp.getDetailArg(), "[UUID] NULL FillDbResultRspTest Message FillDbResultRspTest Message");
    }

    @Test
    public void testFillInsertResultRsp() {
        ResultRsp<OverlayVpn> retRsp = new ResultRsp<>(ErrorCode.OVERLAYVPN_DATABASE_INSERT_FAILED);
        FillDbResultRsp.fillInsertResultRsp(retRsp, "[UUID] NULL");
        assertEquals(retRsp.getDetailArg(), "[UUID] NULL FillDbResultRspTest Message FillDbResultRspTest Message");
    }

    @Test
    public void testFillDeleteResultRsp() {
        ResultRsp<OverlayVpn> retRsp = new ResultRsp<>(ErrorCode.OVERLAYVPN_DATABASE_DELETE_FAILED);
        FillDbResultRsp.fillDeleteResultRsp(retRsp, "[UUID] NULL");
        assertEquals(retRsp.getDetailArg(), "[UUID] NULL FillDbResultRspTest Message FillDbResultRspTest Message");
    }

    @Test
    public void testFillUpdateResultRsp() {
        ResultRsp<OverlayVpn> retRsp = new ResultRsp<>(ErrorCode.OVERLAYVPN_DATABASE_UPDATE_FAILED);
        FillDbResultRsp.fillUpdateResultRsp(retRsp, "[UUID] NULL");
        assertEquals(retRsp.getDetailArg(), "[UUID] NULL FillDbResultRspTest Message FillDbResultRspTest Message");
    }
}
