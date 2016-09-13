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

package org.openo.sdno.overlayvpn.util.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDc;
import org.openo.sdno.overlayvpn.result.ResultRsp;

public class SiteToDCUtilTest {

    SiteToDc siteTodc = null;

    String inputJsonStr = null;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        siteTodc = new SiteToDc();

    }

    @Test(expected = ServiceException.class)
    public void testBuildNewVpnByOldAndInputData() throws ServiceException {
        inputJsonStr =
                "{\"name\":\"hw-szc\",\"site\":{\"cidr\":\"10.12.13.14/16\",\"cpe\":\"5271222f-8b22-47f4-8acb-abbd5337d0b0\",\"portVlan\":\"1\"},\"vpc\":{\"name\":\"vpc-huawei\",\"subnet\":{\"name\":\"subnet1\",\"cidr\":\"10.0.0.16/32\"}}}";
        ResultRsp<SiteToDc> result = SiteToDcUtil.buildNewVpnByOldAndInputData(inputJsonStr);
        assertEquals(result.getData().getName(), "hw-szc");

    }

    @Test
    public void testBuildNewVpnByOldAndInputData_invalid() {
        try {
            inputJsonStr = "{^&}";
            ResultRsp<SiteToDc> result = SiteToDcUtil.buildNewVpnByOldAndInputData(inputJsonStr);
            fail("Validation is not done");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }
}
