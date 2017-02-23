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

package org.openo.sdno.overlayvpn.site2dc.service.inf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.service.IService;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;

/**
 * The interface to deal with reaource.<br>
 * 
 * @param <T> The resource type
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public interface BaseService<T> extends IService {

    /**
     * Create resource.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param data The data to be created
     * @return The create result
     * @throws ServiceException when creating failed
     * @since SDNO 0.5
     */
    T create(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, T data) throws ServiceException;

    /**
     * Delete resource by uuid.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The uuid of resource to be deleted
     * @return The delete result
     * @throws ServiceException when deleting failed
     * @since SDNO 0.5
     */
    T delete(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid) throws ServiceException;

    /**
     * Update resource.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The uuid of resource to be updated
     * @param data The data to be updated
     * @return The update result
     * @throws ServiceException when updating failed
     * @since SDNO 0.5
     */
    T update(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid, T data)
            throws ServiceException;

    /**
     * Query resource by uuid.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The uuid of resource to be queried
     * @return The query result
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    T query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid) throws ServiceException;

    /**
     * Batch query resource.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param condition The query condition
     * @param param The query parameters
     * @return The query result
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    BatchQueryResult<T> query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, T condition,
            BatchQueryParam param) throws ServiceException;

    /**
     * Deploy resource.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The uuid of resource to be deployed
     * @return The deploy result
     * @throws ServiceException when deploying failed
     * @since SDNO 0.5
     */
    T deploy(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid) throws ServiceException;

    /**
     * Undeploy resource.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The uuid of resource to be undeployed
     * @return The undeploy result
     * @throws ServiceException when undeploying failed
     * @since SDNO 0.5
     */
    T undeploy(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, String uuid) throws ServiceException;
}
