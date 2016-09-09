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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.result.ResultRsp;

public class PuerInvDAOUtilTest {

    @Test
    public void testBuildQueryResult() throws ServiceException {
        List<Map<String, Object>> rsp = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        Gateway gw = new Gateway();
        gw.setUuid("12345");
        gw.setName("gateway");
        map.put("uuid", gw);
        rsp.add(map);
        ResultRsp<List<Gateway>> resultList = PuerInvDAOUtil.buildQueryResult(Gateway.class, rsp);
        assertTrue(resultList.isSuccess());
    }

}
