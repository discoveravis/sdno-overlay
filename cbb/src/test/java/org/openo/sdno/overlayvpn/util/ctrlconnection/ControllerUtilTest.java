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

package org.openo.sdno.overlayvpn.util.ctrlconnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.ControllerDao;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.ctrlconnection.ControllerUtil;
import org.openo.sdno.overlayvpn.util.ctrlconnection.IpReachableUtil;

/**
 * ControllerUtil Test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class ControllerUtilTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testTestCtrlConnection() {
        new MockUp<ControllerDao>() {

            @Mock
            public List<ControllerMO> getControllerByNeId(String neId) throws ServiceException {
                ControllerMO mo = new ControllerMO();
                mo.setName("ControllerMO" + neId);
                mo.setHostName("1.1.1.1");
                List<ControllerMO> list = new ArrayList<>();
                list.add(mo);
                return list;
            }

        };

        new MockUp<IpReachableUtil>() {

            @Mock
            public boolean ping(String ip) {

                return true;
            }

        };

        List<String> neUuids = new ArrayList<String>();
        neUuids.add("001");
        neUuids.add("002");

        try {
            ControllerUtil.testCtrlConnection(neUuids);
        } catch(ServiceException e) {
            fail("should not get ServiceException, " + e.getId());
        }

    }

    @Test
    public void testTestCtrlConnection2() {
        new MockUp<ControllerDao>() {

            @Mock
            public List<ControllerMO> getControllerByNeId(String neId) throws ServiceException {
                ControllerMO mo = new ControllerMO();
                mo.setName("ControllerMO" + neId);
                mo.setHostName("1.1.1.1");
                List<ControllerMO> list = new ArrayList<>();
                list.add(mo);
                return list;
            }

        };

        new MockUp<IpReachableUtil>() {

            @Mock
            public boolean ping(String ip) {

                return false;
            }

        };

        List<String> neUuids = new ArrayList<String>();
        neUuids.add("001");
        neUuids.add("002");

        try {
            ControllerUtil.testCtrlConnection(neUuids);
        } catch(ServiceException e) {
            assertTrue(e.getMessage().contains("test ctrl connection failed"));
        }
    }

    @Test
    public void testQueryControllerMoMapByNeUuids() {
        new MockUp<ControllerDao>() {

            @Mock
            public List<ControllerMO> getControllerByNeId(String neId) throws ServiceException {
                ControllerMO mo = new ControllerMO();
                mo.setName("ControllerMO" + neId);
                mo.setHostName("1.1.1.1");
                List<ControllerMO> list = new ArrayList<>();
                list.add(mo);
                return list;
            }

        };

        List<String> neUuids = new ArrayList<String>();
        neUuids.add("001");
        neUuids.add("002");

        ResultRsp rsp = ControllerUtil.queryControllerMoMapByNeUuids(neUuids);
        assertEquals(ErrorCode.OVERLAYVPN_SUCCESS, rsp.getErrorCode());
    }

    @Test
    public void testQueryControllerMoMapByNeUuids2() {
        new MockUp<ControllerDao>() {

            @Mock
            public List<ControllerMO> getControllerByNeId(String neId) throws ServiceException {
                throw new ServiceException("test ERR");
            }

        };

        List<String> neUuids = new ArrayList<String>();
        neUuids.add("001");
        neUuids.add("002");

        ResultRsp rsp = ControllerUtil.queryControllerMoMapByNeUuids(neUuids);
        assertEquals(ErrorCode.OVERLAYVPN_DATABASE_QUERY_FAILED, rsp.getErrorCode());
    }

}
