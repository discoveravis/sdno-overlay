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

package org.openo.sdno.overlayvpn.service.inf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.service.IService;
import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * Resource Basic Operation Interface Class.<br/>
 * 
 * @param <T> Resource Class
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public interface IBasicService<T> extends IService {

    /**
     * Resource Create Interface.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param res resource name
     * @return result of create operation
     * @throws ServiceException ServiceException throws in execution
     * @since SDNO 0.5
     */
    ResultRsp<T> create(HttpServletRequest req, HttpServletResponse resp, T res) throws ServiceException;

    /**
     * Resource Query Interface.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param uuid Key id of Resource which need to query
     * @param tenantId tenant id
     * @return resource object returned
     * @throws ServiceException ServiceException throws in execution
     * @since SDNO 0.5
     */
    ResultRsp<T> query(HttpServletRequest req, HttpServletResponse resp, String uuid, String tenantId)
            throws ServiceException;

    /**
     * Resource Batch Query Interface.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param tenantId tenant id
     * @param filter filter condition
     * @return list of resource object returned
     * @throws ServiceException ServiceException throws in execution
     * @since SDNO 0.5
     */
    ResultRsp<List<T>> batchQuery(HttpServletRequest req, HttpServletResponse resp, String tenantId, String filter)
            throws ServiceException;

    /**
     * Resource Update Interface.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param newRes resource object after updated
     * @param oldRes resource object before updated
     * @return result of update operation
     * @throws ServiceException ServiceException throws in execution
     * @since SDNO 0.5
     */
    ResultRsp<T> update(HttpServletRequest req, HttpServletResponse resp, T newRes, T oldRes) throws ServiceException;

    /**
     * Resource Delete Interface.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param res resource need to delete
     * @return result of delete operation
     * @throws ServiceException ServiceException throws in execution
     * @since SDNO 0.5
     */
    ResultRsp<String> delete(HttpServletRequest req, HttpServletResponse resp, T res) throws ServiceException;
}
