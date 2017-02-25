/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.composer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;

public class SiteConnectionUtilTest {

    @Test
    public void testGetNeConnectionNull1() {
        assertTrue(SiteConnectionUtil.getNeConnection(null, null) == null);
    }

    @Test
    public void testGetNeConnectionNull2() {
        NbiNetConnection netConnection = new NbiNetConnection();
        assertTrue(SiteConnectionUtil.getNeConnection(netConnection, null) == null);
    }

    @Test
    public void testGetNeConnection() {
        NbiNetConnection netConnection = new NbiNetConnection();
        netConnection.setSrcNeId("src_uuid");
        netConnection.setDestNeId("dest_uuid");

        List<NeConnection> neConnections = new ArrayList<>();
        NeConnection neConnection = new NeConnection();
        neConnection.setSrcNeId("dest_uuid");
        neConnection.setDestNeId("src_uuid");
        neConnections.add(neConnection);

        assertEquals(neConnection, SiteConnectionUtil.getNeConnection(netConnection, neConnections));
    }

}
