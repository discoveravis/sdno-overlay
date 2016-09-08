/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.check;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.util.check.CheckOverlayVpnUtil;
import org.openo.sdno.overlayvpn.util.check.ValidationUtil;
import org.openo.sdno.resource.ResourceUtil;

public class CheckOverlayVpnUtilTest {

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
    public void testCheckConnectionIdsNotNullNegtive() throws ServiceException {
        OverlayVpn overlayVpn = new OverlayVpn();
        List<String> connectionIdList = new ArrayList<String>();
        connectionIdList.add("id1");
        overlayVpn.setConnectionIds(connectionIdList);
        CheckOverlayVpnUtil.check(overlayVpn);
    }

    @Test(expected = ServiceException.class)
    public void testCheckVpnConnectionsNotNullNegtive() throws ServiceException {
        OverlayVpn overlayVpn = new OverlayVpn();
        List<Connection> connectionList = new ArrayList<Connection>();
        connectionList.add(new Connection());
        overlayVpn.setVpnConnections(connectionList);
        CheckOverlayVpnUtil.check(overlayVpn);
    }

    @Test
    public void testCheck() throws ServiceException {
        CheckOverlayVpnUtil.check(new OverlayVpn());
    }

}
