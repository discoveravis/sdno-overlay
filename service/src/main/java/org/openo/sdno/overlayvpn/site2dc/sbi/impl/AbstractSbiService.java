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

package org.openo.sdno.overlayvpn.site2dc.sbi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.model.v2.overlay.BaseModel;
import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;
import org.openo.sdno.overlayvpn.site2dc.sbi.inf.SbiService;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.overlayvpn.util.rsp.ResponseUtils;
import org.openo.sdno.rest.RequestUtils;
import org.openo.sdno.util.reflect.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SBI abstract class of connection and route,provides a unified way to call the VPN SBI
 * interface.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 17, 2017
 */
public abstract class AbstractSbiService<MO extends ServiceModel> implements SbiService<MO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSbiService.class);

    private static final String ACTION = "action";

    private static final String DEPLOY = "deploy";

    private static final String UNDEPLOY = "undeploy";

    private static final String BATCHQUERY = "batch-query";

    protected BaseDao<MO> dao = new BaseDao<MO>();

    /**
     * Uniform method for creating resource.<br>
     * 
     * @param uri The sbi url
     * @param classz The resource type
     * @param data The resource data list
     * @return Operation result with sbi information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation
     *             fails
     * @since SDNO 0.5
     */
    public void doCreate(String uri, Class<MO> classz, List<MO> data) throws ServiceException {
        if(CollectionUtils.isEmpty(data)) {
            return;
        }
        ModelServiceUtil.hidNonSbiField(data);
        String url = new StringBuilder().append(uri).toString();
        RestfulParametes restfulParametes = RequestUtils.constructRestParameters(data);

        LOGGER.info("Do create :" + url + " : " + classz.getSimpleName() + " : "
                + ModelServiceUtil.processRestResponse(data));
        RestfulResponse response = RestfulProxy.post(url, restfulParametes, getRestOption());
        LOGGER.info("Do create :" + url + " :status=" + response.getStatus());

        if(!ResponseUtils.validHttpStatus(response.getStatus())) {
            refreshSbiData(ModelServiceUtil.getUuidList(data));
            LOGGER.warn("Do create :" + url + " :response=" + response.getResponseContent());
        }
        ResponseUtils.checkResponseAndThrowException(response);
        List<MO> responseDatas = JsonUtils.parseJson2SimpleList(response.getResponseContent(), classz);
        refreshResponseData(responseDatas);

        LOGGER.info("Do create finish:" + url + " :response=" + ModelServiceUtil.processRestResponse(responseDatas));
    }

    /**
     * Uniform method for deleting resource.<br>
     * 
     * @param uri The sbi url
     * @param uuid The resource uuid
     * @param classz The resource type
     * @throws ServiceException throws serviceException when input data is erroneous or operation
     *             fails
     * @since SDNO 0.5
     */
    public void doDelete(String uri, String uuid, Class<MO> classz) throws ServiceException {
        String url = new StringBuilder().append(uri).append('/').append(uuid).toString();
        RestfulParametes restfulParametes = RequestUtils.constructRestParameters(null);

        LOGGER.info("Do delete :" + url + " : " + classz.getSimpleName() + " : " + restfulParametes.getRawData());
        RestfulResponse response = RestfulProxy.delete(url, restfulParametes, getRestOption());
        LOGGER.info("Do delete :" + url + " :status=" + response.getStatus() + ",response:"
                + response.getResponseContent());

        ResponseUtils.checkResponseAndThrowException(response);
    }

    /**
     * Uniform method for updating resource.<br>
     * 
     * @param uri The sbi url
     * @param classz The resource type
     * @param datas The resource data list
     * @return Operation result with sbi information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation
     *             fails
     * @since SDNO 0.5
     */
    public void doUpdate(String uri, Class<MO> classz, List<MO> datas) throws ServiceException {
        if(CollectionUtils.isEmpty(datas)) {
            return;
        }
        ModelServiceUtil.hidNonSbiField(datas);
        String url = new StringBuilder().append(uri).toString();
        RestfulParametes restfulParametes = RequestUtils.constructRestParameters(datas);

        LOGGER.info("Do update :" + url + " : " + classz.getSimpleName() + " : "
                + ModelServiceUtil.processRestResponse(datas));
        RestfulResponse response = RestfulProxy.put(url, restfulParametes, getRestOption());
        LOGGER.info("Do update :" + url + " :status=" + response.getStatus());

        if(!ResponseUtils.validHttpStatus(response.getStatus())) {
            LOGGER.warn("Do update :" + url + " :response=" + response.getResponseContent());
        }
        ResponseUtils.checkResponseAndThrowException(response);

        LOGGER.info("Do update finish:" + url + " :response=" + response.getResponseContent());
    }

    /**
     * Uniform method for querying resource.<br>
     * 
     * @param uri The sbi url
     * @param classz The resource type
     * @param uuids The resource uuid list
     * @return Operation result with sbi information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation
     *             fails
     * @since SDNO 0.5
     */
    public List<MO> doQuery(String uri, Class<MO> classz, Set<String> uuids) throws ServiceException {
        if(CollectionUtils.isEmpty(uuids)) {
            return new ArrayList<>();
        }
        String url = new StringBuilder().append(uri).append('/').append(BATCHQUERY).toString();
        RestfulParametes restfulParametes = RequestUtils.constructRestParameters(uuids);

        LOGGER.info("Do batch query :" + url + " : " + classz.getSimpleName() + " : " + restfulParametes.getRawData());
        RestfulResponse response = RestfulProxy.post(url, restfulParametes, getRestOption());
        LOGGER.info("Do batch query  :" + url + " :status=" + response.getStatus() + ",response:"
                + response.getResponseContent());

        ResponseUtils.checkResponseAndThrowException(response);

        List<MO> data = JsonUtils.parseJson2SimpleList(response.getResponseContent(), classz);

        LOGGER.info("Do batch query finish :" + url + " :status=" + response.getStatus() + ",data:"
                + JsonUtils.toJson(data));
        return data;
    }

    /**
     * Uniform method for deploying resource.<br>
     * 
     * @param uri The sbi url
     * @param classz The resource type
     * @param uuidList The resource uuid list
     * @return Operation result with sbi information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation
     *             fails
     * @since SDNO 0.5
     */
    public void doDeploy(String uri, Class<MO> classz, List<String> uuidList) throws ServiceException {
        if(CollectionUtils.isEmpty(uuidList)) {
            return;
        }
        List<MO> reqList = getConditionList(classz, ParamConsts.ATTR_ID, new HashSet<String>(uuidList));
        ModelServiceUtil.updateDeployState(DeployState.DEPLOY, reqList);

        Map<String, Object> reqData = new HashMap<String, Object>();
        reqData.put(DEPLOY, uuidList);

        String url = new StringBuilder().append(uri).append('/').append(ACTION).toString();
        RestfulParametes restfulParametes = RequestUtils.constructRestParameters(reqData);

        LOGGER.info("Do deploy :" + url + " : " + classz.getSimpleName() + " : " + restfulParametes.getRawData());
        RestfulResponse response = RestfulProxy.post(url, restfulParametes, getRestOption());
        LOGGER.info("Do deploy  :" + url + " :status=" + response.getStatus() + ",response:"
                + response.getResponseContent());
        ResponseUtils.checkResponseAndThrowException(response);
        LOGGER.info("Do deploy finish:" + url + " :response=" + response.getResponseContent());
    }

    /**
     * Uniform method for undeploying resource.<br>
     * 
     * @param uri The sbi url
     * @param classz The resource type
     * @param uuidList The resource uuid list
     * @return Operation result with sbi information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation
     *             fails
     * @since SDNO 0.5
     */
    public void doUndeploy(String uri, Class<MO> classz, List<String> uuidList) throws ServiceException {
        if(CollectionUtils.isEmpty(uuidList)) {
            return;
        }
        List<MO> reqList = getConditionList(classz, ParamConsts.ATTR_ID, new HashSet<String>(uuidList));
        ModelServiceUtil.updateDeployState(DeployState.UNDEPLOY, reqList);

        List<MO> sbiDatas = queryRemote(new HashSet<String>(uuidList));
        if(CollectionUtils.isEmpty(sbiDatas)) {
            return;
        }

        List<String> sbiUuids = ModelServiceUtil.getUuidList(sbiDatas);
        Map<String, Object> reqData = new HashMap<String, Object>();
        reqData.put(UNDEPLOY, sbiUuids);

        String url = new StringBuilder().append(uri).append('/').append(ACTION).toString();
        RestfulParametes restfulParametes = RequestUtils.constructRestParameters(reqData);

        LOGGER.info("Do undeploy :" + url + " : " + classz.getSimpleName() + " : " + restfulParametes.getRawData());
        RestfulResponse response = RestfulProxy.post(url, restfulParametes, getRestOption());
        LOGGER.info("Do undeploy  :" + url + " :status=" + response.getStatus() + ",response:"
                + response.getResponseContent());
        ResponseUtils.checkResponseAndThrowException(response);
        LOGGER.info("Do undeploy finish:" + url + " :response=" + response.getResponseContent());
    }

    private RestfulOptions getRestOption() {
        RestfulOptions restOptions = new RestfulOptions();
        restOptions.setRestTimeout(60000);
        return restOptions;
    }

    private void refreshSbiData(List<String> uuids) throws ServiceException {
        List<MO> sbiDatas = queryRemote(new HashSet<String>(uuids));
        refreshResponseData(sbiDatas);
    }

    private void refreshResponseData(List<MO> responseDatas) throws ServiceException {
        if(CollectionUtils.isEmpty(responseDatas)) {
            return;
        }
        for(MO data : responseDatas) {
            dao.update(data.getId(), data);
        }
    }

    /**
     * Get condition list to do querying.<br>
     * 
     * @param resType The resource type to be queried
     * @param attrName The name of parameter
     * @param attrValues The value of parameter
     * @return The condition list
     * @throws ServiceException When constructing model failed
     * @since SDNO 0.5
     */
    private <T extends BaseModel> List<T> getConditionList(Class<T> resType, String attrName, Set<?> attrValues)
            throws ServiceException {
        List<T> conds = new ArrayList<>();
        if(CollectionUtils.isEmpty(attrValues)) {
            return conds;
        }
        for(Object value : attrValues) {
            T cond = ModelUtils.newInstance(resType);
            ReflectionUtil.setFieldValue(cond, attrName, value);
            conds.add(cond);
        }
        return conds;
    }
}
