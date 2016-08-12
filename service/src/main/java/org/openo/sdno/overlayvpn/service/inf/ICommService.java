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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * Resource Common Operation Interface.<br/>
 * 
 * @param <T> Resource Class
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public interface ICommService<T> extends IBasicService<T> {

    /**
     * Resource Deploy Interface.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param res resource need to deploy
     * @return resource object when deploy successfully, error message when deploy failed
     * @throws ServiceException ServiceException throws in execution
     * @since SDNO 0.5
     */
    ResultRsp<T> deploy(HttpServletRequest req, HttpServletResponse resp, T res) throws ServiceException;

    /**
     * Resource UnDeploy Interface.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param resresource need to UnDeploy
     * @return resource object when UnDeploy successfully, error message when UnDeploy failed
     * @throws ServiceException ServiceException throws in execution
     * @since SDNO 0.5
     */
    ResultRsp<T> undeploy(HttpServletRequest req, HttpServletResponse resp, T res) throws ServiceException;
}
