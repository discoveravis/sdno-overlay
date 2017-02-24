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
import org.openo.sdno.overlayvpn.composer.util.VpnConnectionUtil;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.site2dc.service.inf.BaseService;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.openo.sdno.overlayvpn.util.check.ValidationUtil;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.overlayvpn.util.validate.VpnConnectionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resource oriented class for vpn connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 16, 2017
 */
@Path("/sdnooverlay/v1/vpn-connections")
public class VpnConnectionRoaResource extends IResource<BaseService<NbiVpnConnection>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnConnectionRoaResource.class);

    private VpnConnectionValidator validator = new VpnConnectionValidator();

    @Override
    public String getResUri() {
        return "/sdnooverlay/v1/vpn-connections";
    }

    /**
     * Rest interface to perform create a vpn connection operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param reqData Vpn connection object to be created
     * @return Operation result with vpn connection information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Object create(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            NbiVpnConnection reqData) throws ServiceException {
        LOGGER.info("Begin to create VpnConnection.");
        ValidationUtil.validateModel(reqData);

        validator.validateCreate(reqData);

        return VpnConnectionUtil.buildRsponse(service.create(httpContext, httpContextRsp, reqData));
    }

    /**
     * Rest interface to perform delete a vpn connection operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The vpn connection uuid
     * @return Operation result with vpn connection information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @DELETE
    @Path("/{uuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String delete(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            @PathParam(value = "uuid") String uuid) throws ServiceException {
        LOGGER.info("Delete VpnConnection,uuid=" + uuid);
        if(VpnConnectionUtil.waitDelete(uuid)) {
            NbiVpnConnection response = service.delete(httpContext, httpContextRsp, uuid);
            LOGGER.info("delete VpnConnection end.");
            return ModelServiceUtil.processRestResponse(response);
        }
        return ModelServiceUtil.processRestResponse(ModelUtils.newModel(NbiVpnConnection.class, uuid));
    }

    /**
     * Rest interface to perform update a vpn connection operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The vpn connection uuid
     * @param reqData Vpn connection object to be updated
     * @return Operation result with vpn connection information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @PUT
    @Path("/{uuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String update(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            @PathParam(value = "uuid") String uuid, NbiVpnConnection reqData) throws ServiceException {
        LOGGER.info("Modify VpnConnection begin,uuid=" + uuid);
        ValidationUtil.validateModel(reqData);

        validator.validateUpdate(uuid, reqData);
        NbiVpnConnection response = service.update(httpContext, httpContextRsp, uuid, reqData);

        LOGGER.info("Modify VpnConnection end.");
        return ModelServiceUtil.processRestResponse(response);
    }

    /**
     * Rest interface to perform query a vpn connection operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The vpn connection uuid
     * @return Operation result with vpn connection information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @GET
    @Path("/{uuid}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String query(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            @PathParam(value = "uuid") String uuid) throws ServiceException {
        LOGGER.info("Get VpnConnection,uuid=" + uuid);

        NbiVpnConnection response = service.query(httpContext, httpContextRsp, uuid);
        LOGGER.info("Query VpnConnection end.");

        return ModelServiceUtil.processRestResponse(response);
    }

    /**
     * Rest interface to perform batch query vpn connection operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @return Operation result with vpn connection information wrapped
     * @throws ServiceException throws serviceException when operation on database fails
     * @since SDNO 0.5
     */
    @GET
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String query(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp)
            throws ServiceException {
        LOGGER.info("Query VpnConnections begin.");

        NbiVpnConnection condition = ModelServiceUtil.constructQueryCondition(httpContext, NbiVpnConnection.class);
        BatchQueryParam param = new BatchQueryParam(httpContext);
        BatchQueryResult<NbiVpnConnection> response = service.query(httpContext, httpContextRsp, condition, param);
        LOGGER.info("Query VpnConnections end.");

        return ModelServiceUtil.processRestResponse(response);
    }

    /**
     * Rest interface to perform deploy a vpn connection operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The vpn connection uuid
     * @return Operation result with vpn connection information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @POST
    @Path("/{uuid}/action/deploy")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String deploy(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            @PathParam(value = "uuid") String uuid) throws ServiceException {
        LOGGER.info("Deploy VpnConnection,uuid=" + uuid);

        if(validator.validateDeploy(uuid)) {
            NbiVpnConnection response = service.deploy(httpContext, httpContextRsp, uuid);
            LOGGER.info("Deploy VpnConnection end.");

            return ModelServiceUtil.processRestResponse(response);
        }

        return JsonUtils.toJson(ModelUtils.newModel(NbiVpnConnection.class, uuid));
    }

    /**
     * Rest interface to perform undeploy a vpn connection operation.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param uuid The vpn connection uuid
     * @return Operation result with vpn connection information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @POST
    @Path("/{uuid}/action/undeploy")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String undeploy(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            @PathParam(value = "uuid") String uuid) throws ServiceException {
        LOGGER.info("Undeploy VpnConnection,uuid=" + uuid);

        if(validator.validateUndeploy(uuid)) {
            NbiVpnConnection response = service.undeploy(httpContext, httpContextRsp, uuid);
            LOGGER.info("Undeploy VpnConnection end.");

            return ModelServiceUtil.processRestResponse(response);
        }

        return JsonUtils.toJson(ModelUtils.newModel(NbiVpnConnection.class, uuid));
    }
}
