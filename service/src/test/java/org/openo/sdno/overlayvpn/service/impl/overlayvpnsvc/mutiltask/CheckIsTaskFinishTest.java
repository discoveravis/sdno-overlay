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
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;

public class CheckIsTaskFinishTest {

    Map<String, OverlayVpn> runOkMap = new HashMap<String, OverlayVpn>();

    Map<String, OverlayVpn> runFailedMap = new HashMap<String, OverlayVpn>();

    Map<String, ResultRsp<OverlayVpn>> runResultRspMap = new HashMap<String, ResultRsp<OverlayVpn>>();

    List<CreateCloudVpnTask> cloudVpnTaskList = new ArrayList<CreateCloudVpnTask>();

    @Test
    public void testCheckTaskIsFinished() {

        OverlayVpn overlayVpn = new OverlayVpn();
        CreateCloudVpnTask uCldVpnTsk = new CreateCloudVpnTask(null, null, "VpnTechnology_VXLAN", overlayVpn);
        uCldVpnTsk.doTask();
        DeployCloudVpnTask duCldVpnTsk = new DeployCloudVpnTask(null, null, "VpnTechnology_VXLAN", overlayVpn);
        duCldVpnTsk.doTask();
        CreateCloudVpnTask uCldVpnTsk1 = new CreateCloudVpnTask(null, null, "VpnTechnology_IPSEC", overlayVpn);
        uCldVpnTsk1.doTask();
        CreateCloudVpnTask uCldVpnTsk2 = new CreateCloudVpnTask(null, null, "VpnTechnology", overlayVpn);
        uCldVpnTsk2.doTask();
        cloudVpnTaskList.add(uCldVpnTsk);
        cloudVpnTaskList.add(uCldVpnTsk1);
        cloudVpnTaskList.add(uCldVpnTsk2);
        List<DeployCloudVpnTask> cloudVpnTaskList1 = new ArrayList<DeployCloudVpnTask>();
        cloudVpnTaskList1.add(duCldVpnTsk);
        try {
            CheckIsTaskFinish.checkTaskIsFinished(runOkMap, runFailedMap, runResultRspMap, cloudVpnTaskList1);
            CheckIsTaskFinish.checkTaskIsFinished(runOkMap, runFailedMap, runResultRspMap, cloudVpnTaskList);
            assertTrue(true);
        } catch(Exception e) {
            fail("Exception occured");
        }

    }

}
