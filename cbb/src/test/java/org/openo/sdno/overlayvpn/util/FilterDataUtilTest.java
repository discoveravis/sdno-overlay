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

package org.openo.sdno.overlayvpn.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.overlayvpn.model.common.enums.ModifyMaskType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.util.FilterDataUtil;

/**
 * FilterDataUtil test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-13
 */
public class FilterDataUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetFilterData() {
        assertEquals("", FilterDataUtil.getFilterData("test", null));
    }

    @Test
    public void testGetFilterData2() {
        assertEquals("", FilterDataUtil.getFilterData("test", ""));
    }

    @Test
    public void testGetFilterData3() {
        List<String> list = new ArrayList<String>();
        assertEquals("", FilterDataUtil.getFilterData("test", list));
    }

    @Test
    public void testGetFilterData4() {
        assertEquals("{\"test\":\"value\"}", FilterDataUtil.getFilterData("test", "value"));
    }

    @Test
    public void testGetLocalNeFilterFromOverlayVpn() {
        OverlayVpn vpn = new OverlayVpn();
        vpn.setUuid("uuid001");

        List<String> connectionIds = new ArrayList<String>();
        connectionIds.add("con001");
        vpn.setConnectionIds(connectionIds);

        Connection connection = new Connection();
        connection.setUuid("con001");
        EndpointGroup edg = new EndpointGroup();
        edg.setUuid("edg001");
        edg.setModifyMask("ADD");
        edg.setDeviceId("dev001");
        List<EndpointGroup> edgs = new ArrayList<EndpointGroup>();
        edgs.add(edg);
        connection.setEndpointGroups(edgs);

        List<Connection> vpnConnections = new ArrayList<Connection>();
        vpnConnections.add(connection);
        vpn.setVpnConnections(vpnConnections);

        String result = FilterDataUtil.getLocalNeFilterFromOverlayVpn(vpn, "123", ModifyMaskType.ADD);
        assertEquals("{\"neId\":[\"dev001\"],\"tenantId\":[\"123\"],\"connectionServiceId\":[\"con001\"]}", result);
    }

    @Test
    public void testGetPeerNeFilterFromOverlayVpn() {
        OverlayVpn vpn = new OverlayVpn();
        vpn.setUuid("uuid001");

        List<String> connectionIds = new ArrayList<String>();
        connectionIds.add("con001");
        vpn.setConnectionIds(connectionIds);

        Connection connection = new Connection();
        connection.setUuid("con001");
        EndpointGroup edg = new EndpointGroup();
        edg.setUuid("edg001");
        edg.setModifyMask("ADD");
        edg.setDeviceId("dev001");
        List<EndpointGroup> edgs = new ArrayList<EndpointGroup>();
        edgs.add(edg);
        connection.setEndpointGroups(edgs);

        List<Connection> vpnConnections = new ArrayList<Connection>();
        vpnConnections.add(connection);
        vpn.setVpnConnections(vpnConnections);

        String result = FilterDataUtil.getPeerNeFilterFromOverlayVpn(vpn, "123", ModifyMaskType.ADD);
        assertEquals("{\"tenantId\":[\"123\"],\"peerNeId\":[\"dev001\"],\"connectionServiceId\":[\"con001\"]}", result);
    }

    @Test
    public void testGetPeerNeFilterFromOverlayVpn2() {
        OverlayVpn vpn = new OverlayVpn();
        vpn.setUuid("uuid001");

        List<String> connectionIds = new ArrayList<String>();
        connectionIds.add("con001");
        vpn.setConnectionIds(connectionIds);

        Connection connection = new Connection();
        connection.setUuid("con001");
        List<EndpointGroup> edgs = new ArrayList<EndpointGroup>();
        connection.setEndpointGroups(edgs);

        List<Connection> vpnConnections = new ArrayList<Connection>();
        vpnConnections.add(connection);
        vpn.setVpnConnections(vpnConnections);

        assertNull(FilterDataUtil.getPeerNeFilterFromOverlayVpn(vpn, "123", ModifyMaskType.ADD));
    }
}
