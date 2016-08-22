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

package org.openo.sdno.overlayvpn.sbi;

import javax.servlet.http.HttpServletRequest;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * Interface for the Micro services with CRUD operations<br/>
 * 
 * @param <T> Resource Class
 * @author
 * @version SDNO 0.5 01-Jun-2016
 */
public interface IMicroSvcBasicOper<T> {

    /**
     * Create Micro Service<br/>
     * 
     * @param req HttpServletRequest Object
     * @param req service request template for the creation
     * @param request request service request template to create
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if the input is invalid or operation fails
     * @since SDNO 0.5
     */
    ResultRsp<T> create(HttpServletRequest req, T request) throws ServiceException;

    /**
     * Query Micro Service<br/>
     * 
     * @param req HttpServletRequest Object
     * @param uuid UUID of the service to be queried
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if the input is invalid or operation fails
     * @since SDNO 0.5
     */
    ResultRsp<T> query(HttpServletRequest req, String uuid) throws ServiceException;

    /**
     * Update Micro service<br/>
     * 
     * @param req HttpServletRequest Object
     * @param request service request template for the update
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if the input is invalid or operation fails
     * @since SDNO 0.5
     */
    ResultRsp<T> update(HttpServletRequest req, T request) throws ServiceException;

    /**
     * Delete Micro service<br/>
     * 
     * @param req HttpServletRequest Object
     * @param uuid UUID of the service to be deleted
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if the input is invalid or operation fails
     * @since SDNO 0.5
     */
    ResultRsp<T> delete(HttpServletRequest req, String uuid) throws ServiceException;

    /**
     * Deploy Micro service<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param request service request template to deploy
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if the input is invalid or operation fails
     * @since SDNO 0.5
     */
    ResultRsp<T> deploy(HttpServletRequest req, T request) throws ServiceException;

    /**
     * Undeploy Micro service<br/>
     * 
     * @param req HttpServletRequest Object
     * @param request service request template to undeploy
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if the input is invalid or operation fails
     * @since SDNO 0.5
     */
    ResultRsp<T> undeploy(HttpServletRequest req, T request) throws ServiceException;

}
