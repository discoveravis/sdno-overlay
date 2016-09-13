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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.result.ResultRsp;

public class GatewayUtilTest {

    @Test
    public void testBuildBatchQueryFilterNormal() throws ServiceException {
        String filter = GatewayUtil.buildBatchQueryFilter("tenantId", "name", "description", "1.1.1.1", "1234");
        assertTrue(filter.indexOf("1.1.1.1") > -1);
    }

    @Test
    public void testBuildNewGatewayByOldAndInputDataNormal() throws ServiceException {
        Gateway queryedOldGateway = new Gateway();

        Gateway newGateway = new Gateway();
        newGateway.setName("test123");
        newGateway.setDescription("test 123");
        newGateway.setIpAddress("1.1.1.1");

        String inputJsonStr = JsonUtil.toJson(newGateway);
        ResultRsp<Gateway> res = GatewayUtil.buildNewGatewayByOldAndInputData(queryedOldGateway, inputJsonStr);

        assertTrue(res.isSuccess());
    }

    @Test(expected = ServiceException.class)
    public void testBuildNewGatewayByOldAndInputDataException() throws ServiceException {
        Gateway queryedOldGateway = new Gateway();
        String inputJsonStr = "";
        ResultRsp<Gateway> res = GatewayUtil.buildNewGatewayByOldAndInputData(queryedOldGateway, inputJsonStr);

        assertTrue(res.isSuccess());
    }

    @Test(expected = ServiceException.class)
    public void testBuildNewGatewayByOldAndInputDataException2() throws ServiceException {
        Gateway queryedOldGateway = new Gateway();
        String inputJsonStr = JsonUtil.toJson(null);
        ResultRsp<Gateway> res = GatewayUtil.buildNewGatewayByOldAndInputData(queryedOldGateway, inputJsonStr);

        assertTrue(res.isSuccess());
    }

}
