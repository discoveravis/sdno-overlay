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

package org.openo.sdno.overlayvpn.service.inf.overlayvpn;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.inf.ICommService;

/**
 * Interface for the EndpointGroup Service.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public interface IEndPointGrp extends ICommService<EndpointGroup> {

    /**
     * Batch Create Interface.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param egpList List of EndpointGroup need to create
     * @return List of EndpointGroup created
     * @throws ServiceException ServiceException throws in execution
     * @since SDNO 0.5
     */
    ResultRsp<List<EndpointGroup>> batchCreate(HttpServletRequest req, HttpServletResponse resp,
            List<EndpointGroup> egpList) throws ServiceException;
}
