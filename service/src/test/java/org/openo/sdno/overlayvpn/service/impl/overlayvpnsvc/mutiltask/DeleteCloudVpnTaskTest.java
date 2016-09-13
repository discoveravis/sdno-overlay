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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;

public class DeleteCloudVpnTaskTest {

    @Test
    public void deleteCloudVpnTaskSuccessTest() {
        OverlayVpn overlayVpn = new OverlayVpn();
        List<String> connectionIds = new ArrayList<>();
        connectionIds.add("123");
        connectionIds.add("45");
        overlayVpn.setConnectionIds(connectionIds);
        List<Connection> vpnConnections = new ArrayList<>();
        Connection conn = new Connection();
        vpnConnections.add(conn);
        overlayVpn.setVpnConnections(vpnConnections);
        DeleteCloudVpnTask deleteCloudVp1 = new DeleteCloudVpnTask(null, null, "VpnTechnology_VXLAN", overlayVpn);
        deleteCloudVp1.doTask();
        String vpnTechnpology1 = deleteCloudVp1.getTaskName();
        assertEquals("Overlayvpn service delete CloudVpn Task. uuid: null, tec: VpnTechnology_VXLAN", vpnTechnpology1);

        DeleteCloudVpnTask deleteCloudVp2 = new DeleteCloudVpnTask(null, null, "VpnTechnology_IPSEC", overlayVpn);
        deleteCloudVp2.doTask();
        String vpnTechnpology2 = deleteCloudVp2.getTaskName();
        assertEquals("Overlayvpn service delete CloudVpn Task. uuid: null, tec: VpnTechnology_IPSEC", vpnTechnpology2);

    }

    @Test
    public void deleteCloudVpnTaskFailureTest() {
        OverlayVpn overlayVpn = new OverlayVpn();
        DeleteCloudVpnTask deleteCloudVpn = new DeleteCloudVpnTask(null, null, "VpnTechnology_Unknown", overlayVpn);
        deleteCloudVpn.doTask();
        String vpnUnknown = deleteCloudVpn.getTaskName();
        assertEquals("Overlayvpn service delete CloudVpn Task. uuid: null, tec: VpnTechnology_Unknown", vpnUnknown);
    }

}
