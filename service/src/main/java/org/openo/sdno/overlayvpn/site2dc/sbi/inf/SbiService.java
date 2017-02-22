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

package org.openo.sdno.overlayvpn.site2dc.sbi.inf;

import java.util.List;
import java.util.Set;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;

/**
 * The interface of sbi service.<br>
 * 
 * @param <MO>
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public interface SbiService<MO extends ServiceModel> {

    /**
     * Do creating task.<br>
     * 
     * @param mo The resource data list to created
     * @throws ServiceException when creating failed
     * @since SDNO 0.5
     */
    void create(List<MO> mo) throws ServiceException;

    /**
     * Do deleting task.<br>
     * 
     * @param uuid The uuid of resource to be deleted
     * @throws ServiceException when deleting failed
     * @since SDNO 0.5
     */
    void delete(String uuid) throws ServiceException;

    /**
     * Do updating task.<br>
     * 
     * @param mos The resource data list to updated
     * @throws ServiceException when updating failed
     * @since SDNO 0.5
     */
    void update(List<MO> mos) throws ServiceException;

    /**
     * Do deploying task. <br>
     * 
     * @param uuidList The uuid list of resource to be deployed
     * @throws ServiceException when deploying failed
     * @since SDNO 0.5
     */
    void deploy(List<String> uuidList) throws ServiceException;

    /**
     * Do undeploying task.<br>
     * 
     * @param uuidList The uuid list of resource to be undeployed
     * @throws ServiceException when undeploying failed
     * @since SDNO 0.5
     */
    void undeploy(List<String> uuidList) throws ServiceException;

    /**
     * Do querying remote data task.<br>
     * 
     * @param uuids The uuid list of resource to be queried
     * @return The result of querying
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    List<MO> queryRemote(Set<String> uuids) throws ServiceException;

}
