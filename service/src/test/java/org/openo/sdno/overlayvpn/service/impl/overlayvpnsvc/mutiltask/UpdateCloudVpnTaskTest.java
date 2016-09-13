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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;

public class UpdateCloudVpnTaskTest {

    @Test
    public void updateCloudVpnTaskTest() {
        OverlayVpn overlayVpn = new OverlayVpn();

        UpdateCloudVpnTask uCldVpnTsk = new UpdateCloudVpnTask(null, null, "VpnTechnology_VXLAN", overlayVpn);
        uCldVpnTsk.doTask();

        UpdateCloudVpnTask uCldVpnTsk1 = new UpdateCloudVpnTask(null, null, "VpnTechnology_IPSEC", overlayVpn);
        uCldVpnTsk1.doTask();

        UpdateCloudVpnTask uCldVpnTsk2 = new UpdateCloudVpnTask(null, null, "VpnTechnology", overlayVpn);
        uCldVpnTsk2.doTask();

        String str = uCldVpnTsk2.getTaskName();
        assertEquals("Overlayvpn service update CloudVpn Task. uuid: null, tec: VpnTechnology", str);
    }

    @Test
    public void UndeployCloudVpnTaskTest() {
        OverlayVpn overlayVpn = new OverlayVpn();
        List<String> connectionIds = new ArrayList<>();
        connectionIds.add("123");
        connectionIds.add("456");
        overlayVpn.setConnectionIds(connectionIds);
        List<Connection> vpnConnections = new ArrayList<>();
        Connection conn = new Connection();
        vpnConnections.add(conn);
        overlayVpn.setVpnConnections(vpnConnections);
        UndeployCloudVpnTask uCldVpnTsk = new UndeployCloudVpnTask(null, null, "VpnTechnology_VXLAN", overlayVpn);
        uCldVpnTsk.doTask();

        UndeployCloudVpnTask uCldVpnTsk1 = new UndeployCloudVpnTask(null, null, "VpnTechnology_IPSEC", overlayVpn);

        uCldVpnTsk1.doTask();

        UndeployCloudVpnTask uCldVpnTsk2 = new UndeployCloudVpnTask(null, null, "VpnTechnology", overlayVpn);
        uCldVpnTsk2.doTask();

        String str = uCldVpnTsk2.getTaskName();
        assertEquals("Overlayvpn service undeploy CloudVpn Task. uuid: null, tec: VpnTechnology", str);
    }

    @Test
    public void OverlayVpnTaskSvcTest() {
        OverlayVpnTaskSvc ovrlVpnTsksvc = new OverlayVpnTaskSvc();
        OverlayVpn ovrlVpn = new OverlayVpn();
        Map<String, OverlayVpn> mapValue = new HashMap<String, OverlayVpn>();
        Map<String, Boolean> tecVpnToDeployedMap = new HashMap<String, Boolean>();

        ResultRsp<Map<String, OverlayVpn>> result =
                ovrlVpnTsksvc.creatVpnByTec(null, null, mapValue, tecVpnToDeployedMap);
        assertTrue(result.isSuccess());
        mapValue.put("key1", ovrlVpn);
        ResultRsp<Map<String, OverlayVpn>> result1 = ovrlVpnTsksvc.creatVpnByTec(null, null, mapValue, tecVpnToDeployedMap);
        assertTrue(result1.isSuccess());
        ovrlVpnTsksvc.deployVpnByTec(null, null, mapValue, tecVpnToDeployedMap);
        mapValue.put("key1", ovrlVpn);
        ovrlVpnTsksvc.deployVpnByTec(null, null, mapValue, tecVpnToDeployedMap);
        ovrlVpnTsksvc.deleteVpnByTec(null, null, mapValue, tecVpnToDeployedMap);
        mapValue.put("key1", ovrlVpn);
        ovrlVpnTsksvc.deleteVpnByTec(null, null, mapValue, tecVpnToDeployedMap);
        ovrlVpnTsksvc.undeployVpnByTec(null, null, mapValue, tecVpnToDeployedMap);
        mapValue.put("key1", ovrlVpn);
        ovrlVpnTsksvc.undeployVpnByTec(null, null, mapValue, tecVpnToDeployedMap);
        ovrlVpnTsksvc.updateVpnByTec(null, null, mapValue, tecVpnToDeployedMap);
        mapValue.put("key1", ovrlVpn);
        ovrlVpnTsksvc.updateVpnByTec(null, null, mapValue, tecVpnToDeployedMap);
    }

}
