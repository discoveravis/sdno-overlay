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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Check OverlayVpnTask is finished.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 7, 2016
 */
public class CheckIsTaskFinish {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckIsTaskFinish.class);

    /**
     * thread sleep time
     */
    private static final int SLEEP_TIME_MS = 100;

    private CheckIsTaskFinish() {
    }

    /**
     * Check whether OverlayVpn Task finished.<br>
     * 
     * @param runOkMap Map of OverlayVpn finished success
     * @param runFailedMap Map of OverlayVpn finished fail
     * @param runResultRspMap Map of Technology type to ResultRsp
     * @param cloudVpnTaskList OverlayVpnTask need to check
     * @since SDNO 0.5
     */
    public static void checkTaskIsFinished(Map<String, OverlayVpn> runOkMap, Map<String, OverlayVpn> runFailedMap,
            Map<String, ResultRsp<OverlayVpn>> runResultRspMap, List<? extends AbstCloudVpnTask> cloudVpnTaskList) {
        if(CollectionUtils.isEmpty(cloudVpnTaskList)) {
            return;
        }

        while(true) {
            Iterator<? extends AbstCloudVpnTask> taskItor = cloudVpnTaskList.iterator();
            while(taskItor.hasNext()) {
                AbstCloudVpnTask tempTask = taskItor.next();
                if(tempTask.isFinish()) {
                    dealFinishedTask(runOkMap, runFailedMap, runResultRspMap, tempTask);
                    // remove task from List
                    taskItor.remove();
                }
            }

            if(CollectionUtils.isEmpty(cloudVpnTaskList)) {
                break;
            }
            sleepTimeInMs(SLEEP_TIME_MS);
        }
    }

    /**
     * Process finished task and collect result.<br>
     * 
     * @param runOkMap Map of OverlayVpn finished success
     * @param runFailedMap Map of OverlayVpn finished fail
     * @param runTecToResultMap Map of Technology type to ResultRsp
     * @param tempTask OverlayVpnTask need to deal with
     * @since SDNO 0.5
     */
    private static void dealFinishedTask(Map<String, OverlayVpn> runOkMap, Map<String, OverlayVpn> runFailedMap,
            Map<String, ResultRsp<OverlayVpn>> runTecToResultMap, AbstCloudVpnTask tempTask) {
        String vpnTechnology = tempTask.getVpnTechnology();
        OverlayVpn cloudVpn = tempTask.getOverlayVpn();
        ResultRsp<OverlayVpn> retRsp = tempTask.getExecuRet();

        if(null != retRsp) {
            if(retRsp.isSuccess() && (null != runOkMap)) {
                runOkMap.put(vpnTechnology, cloudVpn);
            } else if(!retRsp.isSuccess() && (null != runFailedMap)) {
                runFailedMap.put(vpnTechnology, cloudVpn);
            }

            if(null != runTecToResultMap) {
                runTecToResultMap.put(vpnTechnology, retRsp);
            }
        }
    }

    private static void sleepTimeInMs(int ms) {
        try {
            Thread.sleep(ms);
        } catch(InterruptedException e) {
            LOGGER.warn("sleep exception = ", e);
        }
    }

}
