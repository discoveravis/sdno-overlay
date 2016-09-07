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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.openo.sdno.framework.base.threadpool.ThreadPool;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Class of OverlayVpn Task Service.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
@Service
public class OverlayVpnTaskSvc {

    private static final Logger LOGGER = LoggerFactory.getLogger(OverlayVpnTaskSvc.class);

    /**
     * Create OverlayVpn Task.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param data OverlayVpn need to deploy
     * @param tecVpnToDeployedMap Map used to record deploy result
     * @return result of deployment
     * @since SDNO 0.5
     */
    public ResultRsp<Map<String, OverlayVpn>> creatVpnByTec(HttpServletRequest req, HttpServletResponse resp,
            Map<String, OverlayVpn> data, Map<String, Boolean> tecVpnToDeployedMap) {
        if(MapUtils.isEmpty(data)) {
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }
        // Loop for all data
        List<CreateCloudVpnTask> creatVpnTaskList = new ArrayList<CreateCloudVpnTask>();
        for(Entry<String, OverlayVpn> tempEntry : data.entrySet()) {
            String vpnTec = tempEntry.getKey();
            CreateCloudVpnTask tempTask = new CreateCloudVpnTask(req, resp, vpnTec, tempEntry.getValue());
            ThreadPool.getInstance().execute(tempTask);
            creatVpnTaskList.add(tempTask);
        }
        return doDeployVpnByTec(creatVpnTaskList, data, tecVpnToDeployedMap);
    }

    /**
     * Deploy OverlayVpn Task.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param data OverlayVpn need to deploy
     * @param tecVpnToDeployedMap Map used to record deploy result
     * @return result of deployment
     * @since SDNO 0.5
     */
    public ResultRsp<Map<String, OverlayVpn>> deployVpnByTec(HttpServletRequest req, HttpServletResponse resp,
            Map<String, OverlayVpn> data, Map<String, Boolean> tecVpnToDeployedMap) {
        // Loop for all data
        List<DeployCloudVpnTask> deployVpnTaskList = new ArrayList<DeployCloudVpnTask>();
        for(Entry<String, OverlayVpn> tempEntry : data.entrySet()) {
            String vpnTec = tempEntry.getKey();
            DeployCloudVpnTask tempTask = new DeployCloudVpnTask(req, resp, vpnTec, tempEntry.getValue());
            ThreadPool.getInstance().execute(tempTask);
            deployVpnTaskList.add(tempTask);
        }
        return doDeployVpnByTec(deployVpnTaskList, data, tecVpnToDeployedMap);
    }

    /**
     * Deploy OverlayVpn by Technology type.<br/>
     * 
     * @param deployVpnTaskList List of OverlayVpnTask need to deploy
     * @param data OverlayVpn need to deploy
     * @param tecVpnToDeployedMap Map used to record deploy result
     * @return result of deployment
     * @since SDNO 0.5
     */
    public ResultRsp<Map<String, OverlayVpn>> doDeployVpnByTec(List<? extends AbstCloudVpnTask> deployVpnTaskList,
            Map<String, OverlayVpn> data, Map<String, Boolean> tecVpnToDeployedMap) {
        if(CollectionUtils.isEmpty(deployVpnTaskList)) {
            LOGGER.info("no data, do not need doUndeployVpnByTec.");
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }
        ResultRsp<Map<String, OverlayVpn>> rollBackRet = new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        Map<String, OverlayVpn> createOkMap = new HashMap<String, OverlayVpn>();
        Map<String, OverlayVpn> createFailedMap = new HashMap<String, OverlayVpn>();

        // check all task are finished
        Map<String, ResultRsp<OverlayVpn>> creatTecToResultMap = new HashMap<>();
        CheckIsTaskFinish.checkTaskIsFinished(createOkMap, createFailedMap, creatTecToResultMap, deployVpnTaskList);

        // record deploy status
        for(Entry<String, ResultRsp<OverlayVpn>> tempEntry : creatTecToResultMap.entrySet()) {
            tecVpnToDeployedMap.put(tempEntry.getKey(), tempEntry.getValue().isSuccess());
        }

        // TODO: need to roll back if deploy failed

        // package result data
        return packageResultRsp(data, rollBackRet, creatTecToResultMap);
    }

    /**
     * Create Delete OverlayVpn Task.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param data OverlayVpn need to deploy
     * @param tecVpnToDeployedMap Map used to record deploy result
     * @return result of delete operation
     * @since SDNO 0.5
     */
    public ResultRsp<Map<String, OverlayVpn>> deleteVpnByTec(HttpServletRequest req, HttpServletResponse resp,
            Map<String, OverlayVpn> data, Map<String, Boolean> tecVpnToDeployedMap) {
        // Loop for all data
        List<DeleteCloudVpnTask> deleteVpnTaskList = new ArrayList<DeleteCloudVpnTask>();
        for(Entry<String, OverlayVpn> tempEntry : data.entrySet()) {
            String vpnTec = tempEntry.getKey();
            DeleteCloudVpnTask tempTask = new DeleteCloudVpnTask(req, resp, vpnTec, tempEntry.getValue());
            ThreadPool.getInstance().execute(tempTask);
            deleteVpnTaskList.add(tempTask);
        }
        return doUndeployVpnByTec(deleteVpnTaskList, data, tecVpnToDeployedMap);
    }

    /**
     * Create UnDeploy OverlayVpn Task.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param data OverlayVpn need to deploy
     * @param tecVpnToDeployedMap Map used to record deploy result
     * @return result of UnDeploy Operation
     * @since SDNO 0.5
     */
    public ResultRsp<Map<String, OverlayVpn>> undeployVpnByTec(HttpServletRequest req, HttpServletResponse resp,
            Map<String, OverlayVpn> data, Map<String, Boolean> tecVpnToDeployedMap) {
        // Loop for all data
        List<UndeployCloudVpnTask> undeployVpnTaskList = new ArrayList<UndeployCloudVpnTask>();
        for(Entry<String, OverlayVpn> tempEntry : data.entrySet()) {
            String vpnTec = tempEntry.getKey();
            UndeployCloudVpnTask tempTask = new UndeployCloudVpnTask(req, resp, vpnTec, tempEntry.getValue());
            ThreadPool.getInstance().execute(tempTask);
            undeployVpnTaskList.add(tempTask);
        }
        return doUndeployVpnByTec(undeployVpnTaskList, data, tecVpnToDeployedMap);
    }

    /**
     * UnDeploy OverlayVpn By Technology Type.<br/>
     * 
     * @param undeployVpnTaskList List of OverlayVpnTask need to deploy
     * @param data OverlayVpn need to UnDeploy
     * @param tecVpnToDeployedMap Map used to record deploy result
     * @return result of UnDeploy operation
     * @since SDNO 0.5
     */
    public ResultRsp<Map<String, OverlayVpn>> doUndeployVpnByTec(List<? extends AbstCloudVpnTask> undeployVpnTaskList,
            Map<String, OverlayVpn> data, Map<String, Boolean> tecVpnToDeployedMap) {
        if(CollectionUtils.isEmpty(undeployVpnTaskList)) {
            LOGGER.info("no data, do not need doUndeployVpnByTec.");
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        Map<String, OverlayVpn> undeployFailedMap = new HashMap<String, OverlayVpn>();

        // Check if all Tasks are finished
        Map<String, ResultRsp<OverlayVpn>> deleteTecToResultMap = new HashMap<>();
        CheckIsTaskFinish.checkTaskIsFinished(null, undeployFailedMap, deleteTecToResultMap, undeployVpnTaskList);

        // Record the deployment status
        for(Entry<String, ResultRsp<OverlayVpn>> tempEntry : deleteTecToResultMap.entrySet()) {
            tecVpnToDeployedMap.put(tempEntry.getKey(), !tempEntry.getValue().isSuccess());
        }
        if(!undeployFailedMap.isEmpty()) {
            LOGGER.error("delete cloudvpn task contains failed.");
        }

        // package result data
        return packageResultRsp(data, null, deleteTecToResultMap);
    }

    /**
     * Update OverlayVpn.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param modifyData OverlayVpn need to update
     * @param tecVpnToUpdateOkMap Map used to record update result
     * @return result of Update operation
     * @since SDNO 0.5
     */
    public ResultRsp<Map<String, OverlayVpn>> updateVpnByTec(HttpServletRequest req, HttpServletResponse resp,
            Map<String, OverlayVpn> modifyData, Map<String, Boolean> tecVpnToUpdateOkMap) {
        if(MapUtils.isEmpty(modifyData)) {
            LOGGER.info("no data, do not need undate.");
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }
        ResultRsp<Map<String, OverlayVpn>> rollBackRet = new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        Map<String, OverlayVpn> updateOkMap = new HashMap<String, OverlayVpn>();
        Map<String, OverlayVpn> updateFailedMap = new HashMap<String, OverlayVpn>();

        // Loop for all data
        List<UpdateCloudVpnTask> updateVpnTaskList = new ArrayList<UpdateCloudVpnTask>();
        for(Entry<String, OverlayVpn> tempEntry : modifyData.entrySet()) {
            String vpnTec = tempEntry.getKey();
            UpdateCloudVpnTask tempTask = new UpdateCloudVpnTask(req, resp, vpnTec, tempEntry.getValue());
            ThreadPool.getInstance().execute(tempTask);
            updateVpnTaskList.add(tempTask);
        }

        // Check if all tasks are finished
        Map<String, ResultRsp<OverlayVpn>> updateTecToResultMap = new HashMap<>();
        CheckIsTaskFinish.checkTaskIsFinished(updateOkMap, updateFailedMap, updateTecToResultMap, updateVpnTaskList);
        // Record the Operation status
        for(Entry<String, ResultRsp<OverlayVpn>> tempEntry : updateTecToResultMap.entrySet()) {
            tecVpnToUpdateOkMap.put(tempEntry.getKey(), tempEntry.getValue().isSuccess());
        }

        // TODO: roll back is needed if operation failed

        // package result data
        return packageResultRsp(modifyData, rollBackRet, updateTecToResultMap);
    }

    /**
     * Package Result Response Data.<br/>
     * 
     * @param data OverlayVpn Object
     * @param rollBackRet Result of roll Back
     * @param creatTecToResultMap ResultRsp Object
     * @return Packaging ResultRsp Object
     * @since SDNO 0.5
     */
    private ResultRsp<Map<String, OverlayVpn>> packageResultRsp(Map<String, OverlayVpn> data,
            ResultRsp<Map<String, OverlayVpn>> rollBackRet, Map<String, ResultRsp<OverlayVpn>> creatTecToResultMap) {
        String errorCode = ErrorCode.OVERLAYVPN_SUCCESS;
        StringBuffer strBuf = new StringBuffer();
        for(Entry<String, ResultRsp<OverlayVpn>> tempEntry : creatTecToResultMap.entrySet()) {
            ResultRsp<OverlayVpn> tempResult = tempEntry.getValue();
            if(!tempResult.isSuccess()) {
                LOGGER.error("Create ErrInfo: " + tempResult.toString());
                errorCode = tempResult.getErrorCode();
                strBuf.append(packTecHeader(tempEntry.getKey())).append(tempResult.getReasonArg()).append(' ');
            }
        }
        if((null != rollBackRet) && !rollBackRet.isSuccess()) {
            LOGGER.error("Rollback ErrInfo: " + rollBackRet.toString());
            strBuf.append(". Rollback error: ").append(rollBackRet.getReasonArg());
        }

        // Add Error argument and description
        ResultRsp<Map<String, OverlayVpn>> retResultRsp = new ResultRsp<>(errorCode, data);
        retResultRsp.setDescArg(strBuf.toString());
        retResultRsp.setReasonArg(strBuf.toString());
        return retResultRsp;
    }

    /**
     * Add Info to Header.<br/>
     * 
     * @param tec info
     * @return Info after adding header
     * @since SDNO 0.5
     */
    private String packTecHeader(String tec) {
        return "[" + tec + "]";
    }

}
