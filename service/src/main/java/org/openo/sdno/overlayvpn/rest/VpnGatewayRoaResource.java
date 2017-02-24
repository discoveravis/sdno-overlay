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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.service.IResource;
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.openo.sdno.overlayvpn.site2dc.service.inf.BaseService;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.openo.sdno.overlayvpn.util.check.ValidationUtil;
import org.openo.sdno.overlayvpn.util.validate.VpnGatewayValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resource oriented class for vpn gateway.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 16, 2017
 */
@Path("/sdnooverlay/v1/vpn-gateways")
public class VpnGatewayRoaResource extends IResource<BaseService<NbiVpnGateway>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnGatewayRoaResource.class);

    private VpnGatewayValidator validator = new VpnGatewayValidator();

    @Override
    public String getResUri() {
        return "/sdnooverlay/v1/vpn-gateways";
    }

    /**
     * Rest interface to perform create a vpn gateway operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param reqData Vpn gateway object to be created
     * @return Operation result with vpn gateway information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String create(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            NbiVpnGateway reqData) throws ServiceException {
        ValidationUtil.validateModel(reqData);

        validator.validateCreate(reqData);

        return ModelServiceUtil.processRestResponse(service.create(httpContext, httpContextRsp, reqData));
    }

    /**
     * Rest interface to perform delete a vpn gateway operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The vpn gateway uuid
     * @return Operation result with vpn gateway information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @DELETE
    @Path("/{uuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String delete(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            @PathParam("uuid") String uuid) throws ServiceException {
        LOGGER.info("Delete VpnGateway,uuid=" + uuid);
        if(validator.validateDelete(uuid)) {
            NbiVpnGateway response = service.delete(httpContext, httpContextRsp, uuid);
            return ModelServiceUtil.processRestResponse(response);
        }
        return ModelServiceUtil.processRestResponse(ModelUtils.newModel(NbiVpnGateway.class, uuid));
    }

    /**
     * Rest interface to perform update a vpn gateway operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The vpn gateway uuid
     * @param reqData Vpn gateway object to be updated
     * @return Operation result with vpn gateway information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @PUT
    @Path("/{uuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String update(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            @PathParam("uuid") String uuid, NbiVpnGateway reqData) throws ServiceException {
        ValidationUtil.validateModel(reqData);

        validator.validateUpdate(uuid, reqData);

        return ModelServiceUtil.processRestResponse(service.update(httpContext, httpContextRsp, uuid, reqData));
    }

    /**
     * Rest interface to perform query a vpn gateway operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The vpn gateway uuid
     * @return Operation result with vpn gateway information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @GET
    @Path("/{uuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String query(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            @PathParam("uuid") String uuid) throws ServiceException {
        LOGGER.info("Get VpnGateway,uuid=" + uuid);
        return ModelServiceUtil.processRestResponse(service.query(httpContext, httpContextRsp, uuid));
    }

    /**
     * Rest interface to perform batch query vpn gateway operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @return Operation result with vpn gateway information wrapped
     * @throws ServiceException throws serviceException when operation on database fails
     * @since SDNO 0.5
     */
    @GET
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String query(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp)
            throws ServiceException {
        NbiVpnGateway condition = ModelServiceUtil.constructQueryCondition(httpContext, NbiVpnGateway.class);
        BatchQueryParam param = new BatchQueryParam(httpContext);
        return ModelServiceUtil.processRestResponse(service.query(httpContext, httpContextRsp, condition, param));
    }

}
