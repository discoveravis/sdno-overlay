/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

import java.util.HashMap;
import java.util.Map;

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
import javax.ws.rs.core.MediaType;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.util.RestUtils;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.consts.HttpCode;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDc;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDcNbi;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.security.authentication.TokenDataHolder;
import org.openo.sdno.overlayvpn.service.inf.overlayvpn.ISiteToDC;
import org.openo.sdno.overlayvpn.util.check.CheckStrUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.openo.sdno.overlayvpn.util.operation.SiteToDcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Resource oriented class for Site to DC scenario<br>
 * 
 * @author
 * @version SDNO 0.5 24-May-2016
 */
@Path("/sdnooverlay/v1/site2dc-vpn")
public class SiteToDcRoaResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteToDcRoaResource.class);

    private ISiteToDC service;

    /**
     * @param service The service to set.
     */
    public void setService(ISiteToDC service) {
        this.service = service;
    }

    /**
     * Rest interface to perform create a site to DC operation<br>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param s2Dc Site to DC object to be created
     * @return Operation result with site to DC information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> create(@Context HttpServletRequest req, @Context HttpServletResponse resp, String s2Dc)
            throws ServiceException {

        long infterEnterTime = System.currentTimeMillis();
        LOGGER.debug("Enter create method. begin time = " + infterEnterTime);

        SiteToDcNbi oSite2DcNbi = JsonUtil.fromJson(s2Dc, SiteToDcNbi.class);
        SiteToDc siteToDC = convertFromNbi(oSite2DcNbi);

        // Step 1. validate the input data
        // For create operation UUID should be null...
        if(StringUtils.hasLength(siteToDC.getUuid())) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("UUID", null);
        }

        // Step 2. set a UUID from the resource pool
        siteToDC.setUuid(UuidUtils.createUuid());

        // Step 3. call the service method to perform create operation
        ResultRsp<SiteToDc> resultRsp = service.create(req, resp, siteToDC);
        LOGGER.debug("Exit create method. cost time = " + (System.currentTimeMillis() - infterEnterTime));

        // Step 4. check the response for error code and throw an exception in case of failure
        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        Map<String, String> res = new HashMap<String, String>();
        res.put("vpnId", resultRsp.getData().getUuid());
        res.put("errorCode", resultRsp.getErrorCode());
        res.put("errorMsg", resultRsp.getMessage());

        // 5. well all-is-well, set the response status as success and return result
        resp.setStatus(HttpCode.CREATE_OK);
        return res;
    }

    /**
     * Query SiteToDC information for the Site to DC scenario<br>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param uuid UUID of the Site to DC
     * @return Operation result with site to DC information wrapped
     * @throws ServiceException throws ServiceException when resource do not exist or database query
     *             failed
     * @since SDNO 0.5
     */
    @GET
    @Path("/{site-to-dc-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SiteToDcNbi query(@Context HttpServletRequest req, @Context HttpServletResponse resp,
            @PathParam("site-to-dc-id") String uuid) throws ServiceException {
        long infterEnterTime = System.currentTimeMillis();
        LOGGER.debug("Enter query method. begin time = " + infterEnterTime);

        // Step 1. Validate the UUID
        CheckStrUtil.checkUuidStr(uuid);

        // Step 2. Get the tenant info from the context
        String tenantIdFromToken = TokenDataHolder.getTenantID();

        // Step 3. Call the service query
        ResultRsp<SiteToDc> resultRsp = service.query(req, resp, uuid, tenantIdFromToken);
        LOGGER.debug("Exit query method. cost time = " + (System.currentTimeMillis() - infterEnterTime));

        // Step 4. validate the response
        if(resultRsp.getData() == null) {
            ThrowOverlayVpnExcpt.throwResNotExistAsNotFound("Site to DC", uuid);
        }

        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        return convertToNbi(resultRsp.getData());
    }

    /**
     * Update the name and description of the Site to DC<br>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param uuid UUID of the site to DC service to be updated
     * @return Operation result with site to DC information wrapped
     * @throws ServiceException throws ServiceException when resource do not exist or database query
     *             fails
     * @since SDNO 0.5
     */
    @PUT
    @Path("/{site-to-dc-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> update(@Context HttpServletRequest req, @Context HttpServletResponse resp,
            @PathParam("site-to-dc-id") String uuid) throws ServiceException {
        long infterEnterTime = System.currentTimeMillis();
        LOGGER.info("Enter update method. begin time = " + infterEnterTime);

        Map<String, String> res = new HashMap<String, String>();

        // Step 1. validate the UUID input
        CheckStrUtil.checkUuidStr(uuid);

        // Step 2. read the input stream and validate the body
        String inputStr = RestUtils.getRequestBody(req);
        if(!StringUtils.hasLength(inputStr)) {
            res.put("errorCode", ErrorCode.OVERLAYVPN_SUCCESS);
            return res;
        }

        // Step 3. build the complete siteToDC object from the old and new data put together
        ResultRsp<SiteToDc> newSiteToDC = SiteToDcUtil.buildNewVpnByOldAndInputData(inputStr);

        // Step 4. call the service update
        ResultRsp<SiteToDc> updateReslutRsp = service.update(req, resp, newSiteToDC.getData(), new SiteToDc(uuid));
        LOGGER.info("Exit update method. cost time = " + (System.currentTimeMillis() - infterEnterTime));

        // Step 5. validate the response and validate the final response
        ThrowOverlayVpnExcpt.checkRspThrowException(updateReslutRsp);

        res.put("errorCode", newSiteToDC.getErrorCode());
        res.put("errorMsg", newSiteToDC.getMessage());

        return res;
    }

    /**
     * Delete a specific site to DC scenario<br>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param uuid UUID of the site to DC service to be deleted
     * @return Operation result with site to DC information wrapped
     * @throws ServiceException throws ServiceException when resource do not exist or database
     *             operation fails
     * @since SDNO 0.5
     */
    @DELETE
    @Path("/{site-to-dc-id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> delete(@Context HttpServletRequest req, @Context HttpServletResponse resp,
            @PathParam("site-to-dc-id") String uuid) throws ServiceException {

        Map<String, String> res = new HashMap<String, String>();

        long infterStartTime = System.currentTimeMillis();
        LOGGER.info("Enter delete method. begin time = " + infterStartTime);

        String tenantIdFromToken = TokenDataHolder.getTenantID();

        CheckStrUtil.checkUuidStr(uuid);

        ResultRsp<SiteToDc> overlayVpnRsp = service.query(req, resp, uuid, tenantIdFromToken);
        SiteToDc siteToDc = overlayVpnRsp.getData();
        if(null == siteToDc) {
            res.put("errorCode", ErrorCode.OVERLAYVPN_SUCCESS);
            return res;
        }

        ResultRsp<String> resultRsp = service.delete(req, resp, siteToDc);
        LOGGER.info("Exit delete method. cost time = " + (System.currentTimeMillis() - infterStartTime));

        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        res.put("errorCode", resultRsp.getErrorCode());
        res.put("errorMsg", resultRsp.getMessage());

        return res;
    }

    private SiteToDc convertFromNbi(SiteToDcNbi oSite2DcNbi) {
        SiteToDc oSiteToDc = new SiteToDc("");

        oSiteToDc.setName(oSite2DcNbi.getName());
        oSiteToDc.setDescription(oSite2DcNbi.getDescription());

        if(null != oSiteToDc.getSite()) {
            oSiteToDc.getSite().setSiteTypeAddress(oSite2DcNbi.getSite().getCidr());
            oSiteToDc.getSite().setSiteThinCPE(oSite2DcNbi.getSite().getThinCpeId());
            oSiteToDc.getSite().setSitevCPE(oSite2DcNbi.getSite().getvCPEId());
            oSiteToDc.getSite().setPortAndVlan(oSite2DcNbi.getSite().getPortAndVlan());
        }

        if(null != oSiteToDc.getVpc()) {
            oSiteToDc.getVpc().getSubnet().setName(oSite2DcNbi.getVpc().getSite().getName());
            oSiteToDc.getVpc().getSubnet().setCidr(oSite2DcNbi.getVpc().getSite().getCidr());
            oSiteToDc.getVpc().getSubnet().setVni(oSite2DcNbi.getVpc().getSite().getVni());
            oSiteToDc.getVpc().setName(oSite2DcNbi.getVpc().getName());
        }

        if(null != oSiteToDc.getSfp()) {
            oSiteToDc.getSfp().setScfNeId(oSite2DcNbi.getSfp().getScfNeId());
            oSiteToDc.getSfp().setServicePathHops(oSite2DcNbi.getSfp().getServicePathHops());
        }

        return oSiteToDc;
    }

    private SiteToDcNbi convertToNbi(SiteToDc oSite2Dc) {
        SiteToDcNbi oSiteToDcNbi = new SiteToDcNbi();

        oSiteToDcNbi.setName(oSite2Dc.getName());
        oSiteToDcNbi.setDescription(oSite2Dc.getDescription());

        if(null != oSiteToDcNbi.getSite()) {
            oSiteToDcNbi.getSite().setCidr(oSite2Dc.getSite().getSiteTypeAddress());
            oSiteToDcNbi.getSite().setThinCpeId(oSite2Dc.getSite().getSiteThinCPE());
            oSiteToDcNbi.getSite().setvCPEId(oSite2Dc.getSite().getSitevCPE());
            oSiteToDcNbi.getSite().setPortAndVlan(oSite2Dc.getSite().getPortAndVlan());
        }

        if(null != oSiteToDcNbi.getVpc()) {
            oSiteToDcNbi.getVpc().setName(oSite2Dc.getVpc().getName());
            oSiteToDcNbi.getVpc().getSite().setCidr(oSite2Dc.getVpc().getSubnet().getCidr());
            oSiteToDcNbi.getVpc().getSite().setName(oSite2Dc.getVpc().getSubnet().getName());
            oSiteToDcNbi.getVpc().getSite().setVni(oSite2Dc.getVpc().getSubnet().getVni());
        }

        if(null != oSiteToDcNbi.getSfp()) {
            oSiteToDcNbi.getSfp().setScfNeId(oSite2Dc.getSfp().getScfNeId());
            oSiteToDcNbi.getSfp().setServicePathHops(oSite2Dc.getSfp().getServicePathHops());
        }

        return oSiteToDcNbi;
    }

}
