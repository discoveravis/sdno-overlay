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

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiConnectionRelation;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;

public class VpnTunnelUtilTest {

    @Test
    public void testBuildConnectionRelationEmpty1() {
        NeConnection neConnection = new NeConnection();
        assertTrue(CollectionUtils.isEmpty(VpnTunnelUtil.buildConnectionRelation(neConnection, null)));
    }

    @Test
    public void testBuildConnectionRelationEmpty2() {
        NbiServiceConnection neConnection = new NbiServiceConnection();
        assertTrue(CollectionUtils.isEmpty(VpnTunnelUtil.buildConnectionRelation(neConnection, null)));
    }

    @Test
    public void testBuildConnectionRelation() {
        NeConnection neConnection = new NeConnection();
        neConnection.setConnectionId("test");
        neConnection.setConnectionType("InternalConnection");
        List<NbiNetConnection> netConnections = new ArrayList<>();
        NbiNetConnection connection = new NbiNetConnection();
        connection.setId("test_id");
        connection.setType("vxlan");
        netConnections.add(connection);
        NbiConnectionRelation relation = VpnTunnelUtil.buildConnectionRelation(neConnection, netConnections).get(0);
        assertEquals("test", relation.getVpnConnectionId());
        assertEquals("InternalConnection", relation.getVpnConnectionType());
        assertEquals("test_id", relation.getNetConnectionId());
        assertEquals("vxlan", relation.getNetConnectionType());
    }
}
