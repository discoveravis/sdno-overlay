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

package org.openo.sdno.overlayvpn.util.check;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.resource.ResourceUtil;

import mockit.Mock;
import mockit.MockUp;

public class CheckGatewayUtilTest {

    @Before
    public void setUp() throws Exception {
        new MockUp<ValidationUtil>() {

            @Mock
            public void validateModel(Object obj) throws ServiceException {
            }
        };

        new MockUp<ResourceUtil>() {

            @Mock
            public String getMessage(String str) {
                return "Test_{connectionId}";
            }
        };
    }

    @Test(expected = ServiceException.class)
    public void testCheckNegtive() throws ServiceException {
        new MockUp<NetworkElementInvDao>() {

            @Mock
            public NetworkElementMO query(String id) throws ServiceException {
                return null;
            }
        };
        CheckGatewayUtil.check(new Gateway());
        fail("Exception not occured");
    }

    @Test
    public void testCheck() throws ServiceException {
        new MockUp<NetworkElementInvDao>() {

            @Mock
            public NetworkElementMO query(String id) throws ServiceException {
                return new NetworkElementMO();
            }
        };
        try {
            CheckGatewayUtil.check(new Gateway());
            assertTrue(true);
        } catch(Exception e) {
            fail("Exception occured");
        }
    }

}
