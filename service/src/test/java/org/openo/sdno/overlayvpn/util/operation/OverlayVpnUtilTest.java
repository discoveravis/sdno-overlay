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
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.OperStatus;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;

public class OverlayVpnUtilTest {

    String res = null;

    String inputJsonStr;

    /**
     * @throws java.lang.Exception
     */

    @Before
    public void setUp() throws Exception {
        res = "{\"adminStatus\":[\"active\"],\"tenantId\":[\"teId\"],\"name\":[\"name\"],\"operStatus\":[\"up\"],\"description\":[\"description\"]}";
    }

    @Test
    public void testBuildBatchQueryFilter() {
        try {
            String result = OverlayVpnUtil.buildBatchQueryFilter("teId", "name", "description",
                    AdminStatus.ACTIVE.getName(), OperStatus.UP.getName());
            assertEquals(res, result);
        } catch(ServiceException e) {
            fail("Exception");
        }

    }

    @Test
    public void testBuildBatchQueryFilter_invalid() {
        try {

            String result = OverlayVpnUtil.buildBatchQueryFilter("teId", "name", "description", null, null);
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testbuildNewVpnByOldAndInputData() {
        try {
            OverlayVpn queryedOldVpn = new OverlayVpn();
            ResultRsp<OverlayVpn> result = OverlayVpnUtil.buildNewVpnByOldAndInputData(queryedOldVpn,
                    "{\"name\":\"name\", \"description\":\"description\",\"adminStatus\":\"active\"}");
            assertEquals(result.getData().getName(), "name");
        } catch(ServiceException e) {
            fail("Exception");
        }

    }

    @Test
    public void testbuildNewVpnByOldAndInputData_invalid() {
        try {
            OverlayVpn queryedOldVpn = new OverlayVpn();
            ResultRsp<OverlayVpn> result = OverlayVpnUtil.buildNewVpnByOldAndInputData(queryedOldVpn,
                    "{\"{^&}\"\"name\":\"name\", \"description\":\"description\",\"adminStatus\":\"active\"}");
            fail("Validation is not done");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }
}
