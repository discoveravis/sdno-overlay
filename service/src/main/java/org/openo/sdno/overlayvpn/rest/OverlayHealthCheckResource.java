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

package org.openo.sdno.overlayvpn.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpStatus;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.service.IResource;
import org.springframework.stereotype.Service;

/**
 * The health check rest interface of overlay.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 14, 2017
 */
@Service
@Path("/sdnooverlay/v1/healthcheck")
public class OverlayHealthCheckResource extends IResource {

    @Override
    public String getResUri() {
        return "/sdnooverlay/v1/healthcheck";
    }

    /**
     * Health check api.<br/>
     * 
     * @param req Http request
     * @param resp Http response
     * @throws ServiceException when health check failed
     * @since SDNO 0.5
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void healthCheck(@Context HttpServletRequest req, @Context HttpServletResponse resp)
            throws ServiceException {
        resp.setStatus(HttpStatus.SC_OK);
    }

}
