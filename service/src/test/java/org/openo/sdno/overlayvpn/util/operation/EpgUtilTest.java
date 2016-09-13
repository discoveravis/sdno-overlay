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

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.result.ResultRsp;

public class EpgUtilTest {

    @Test
    public void testBuildQueryFilterMapNormal() throws ServiceException {

        Map<String, Object> map =
                EpgUtil.buildQueryFilterMap("name123", "port", "tenantIdFromToken", "gatewayId", "hub", "connectionId");
        List<String> list = (List<String>)map.get("tenantId");
        assertTrue(list.get(0).equals("tenantIdFromToken"));
    }

    @Test(expected = ServiceException.class)
    public void testMergeEpgNormal() throws ServiceException {
        EndpointGroup queryedOldEpg = new EndpointGroup();
        queryedOldEpg.setUuid("uuid123");
        queryedOldEpg.setActionState("active");

        EndpointGroup queryedNewEpg = new EndpointGroup();
        queryedNewEpg.setName("name123");
        queryedNewEpg.setDescription("description here");
        queryedNewEpg.setAdminStatus("active");

        String inputJsonStr = JsonUtil.toJson(queryedNewEpg);
        ResultRsp<EndpointGroup> resp = EpgUtil.mergeEpg(queryedOldEpg, inputJsonStr);
        assertTrue(resp.isSuccess());
    }

    @Test(expected = ServiceException.class)
    public void testMergeEpgException() throws ServiceException {
        EndpointGroup queryedOldEpg = new EndpointGroup();
        queryedOldEpg.setUuid("uuid123");
        queryedOldEpg.setActionState("active");

        String inputJsonStr = JsonUtil.toJson(null);
        ResultRsp<EndpointGroup> resp = EpgUtil.mergeEpg(queryedOldEpg, inputJsonStr);
        assertTrue(resp.isSuccess());
    }

    @Test(expected = ServiceException.class)
    public void testMergeEpgException2() throws ServiceException {
        EndpointGroup queryedOldEpg = new EndpointGroup();
        queryedOldEpg.setUuid("uuid123");
        queryedOldEpg.setActionState("active");

        String inputJsonStr = "";
        ResultRsp<EndpointGroup> resp = EpgUtil.mergeEpg(queryedOldEpg, inputJsonStr);
        assertTrue(resp.isSuccess());
    }

}
