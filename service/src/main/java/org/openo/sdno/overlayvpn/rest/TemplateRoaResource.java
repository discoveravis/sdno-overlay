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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.service.IResource;
import org.openo.sdno.overlayvpn.site2dc.service.inf.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resource oriented class for template.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 16, 2017
 */
@Path("/sdnooverlay/v1/template")
public class TemplateRoaResource extends IResource<TemplateService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateRoaResource.class);

    @Override
    public String getResUri() {
        return "/sdnooverlay/v1/template";
    }

    /**
     * Query template policy through template name and policy name.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param templateName The template name
     * @param policyName The policy name
     * @return The policy in template queried by templateName and policyName
     * @throws ServiceException when get policy failed
     * @since SDNO 0.5
     */
    @GET
    @Path("/policy/{templatename}/{policyname}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String query(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            @PathParam(value = "templatename") String templateName, @PathParam(value = "policyname") String policyName)
            throws ServiceException {
        LOGGER.info("Get Template,templateName=" + templateName + ",policyName = " + policyName);
        return service.getTemplateSpec(templateName, policyName);
    }

}
