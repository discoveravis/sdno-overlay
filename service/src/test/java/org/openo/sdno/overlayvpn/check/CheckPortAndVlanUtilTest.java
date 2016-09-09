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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.PortAndVlanUsed;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.CheckPortAndVlanUtil;

import mockit.Mock;
import mockit.MockUp;

public class CheckPortAndVlanUtilTest {

    private static List<String> portUuids = new ArrayList<String>();

    private static Map<String, String> portToVlanMap = new HashMap<String, String>();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        portUuids.add("portUuid1");
        portToVlanMap.put("1", "99");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        portUuids.clear();
        portToVlanMap.clear();
    }

    @Test
    public <T> void testCheck() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> batchQuery(Class clazz, String filter) throws ServiceException {
                PortAndVlanUsed portVlanUsed = new PortAndVlanUsed();
                portVlanUsed.setPortId("1");
                portVlanUsed.setVlan("98");
                List<PortAndVlanUsed> portVlanList = new ArrayList<PortAndVlanUsed>();
                portVlanList.add(portVlanUsed);
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, portVlanList);
            }
        };

        CheckPortAndVlanUtil.check(portUuids, portToVlanMap);
    }

}
