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
import org.openo.sdno.overlayvpn.composer.util.ModelServiceUtil;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.servicemodel.enums.VpnLinksType;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetIpsecConnection;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetVxlanConnection;
import org.openo.sdno.overlayvpn.site2dc.service.inf.VpnLinksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resource oriented class for vpn links.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 16, 2017
 */
@Path("/sdnooverlay/v1/vpn-links")
public class VpnLinksRoaResource extends IResource<VpnLinksService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnLinksRoaResource.class);

    @Override
    public String getResUri() {
        return "/sdnooverlay/v1/vpn-links";
    }

    /**
     * Query tunnel connection through technology.<br>
     * 
     * @param httpContext HttpServletRequest Object
     * @param httpContextRsp HttpServletResponse Object
     * @param type The technology,is ipsec or vxlan
     * @return Operation result with tunnel connection information wrapped
     * @throws ServiceException throws serviceException when input data is erroneous or operation on
     *             database fails
     * @since SDNO 0.5
     */
    @GET
    @Path("/{type}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public String query(@Context HttpServletRequest httpContext, @Context HttpServletResponse httpContextRsp,
            @PathParam(value = "type") String type) throws ServiceException {
        LOGGER.info("Get vpn Links begin.");
        VpnLinksType vpnLinksType = VpnLinksType.fromType(type);
        if(VpnLinksType.IPSEC.equals(vpnLinksType)) {
            NetIpsecConnection connectionIpsec =
                    ModelServiceUtil.constructQueryCondition(httpContext, NetIpsecConnection.class);
            return query(httpContext, httpContextRsp, connectionIpsec);
        } else if(VpnLinksType.VXLAN.equals(vpnLinksType)) {
            NetVxlanConnection connectionVxlan =
                    ModelServiceUtil.constructQueryCondition(httpContext, NetVxlanConnection.class);
            return query(httpContext, httpContextRsp, connectionVxlan);
        } else {
            return null;
        }
    }

    private <T> String query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp, T condition)
            throws ServiceException {
        BatchQueryParam param = new BatchQueryParam(httpContext);
        BatchQueryResult<T> response =
                (BatchQueryResult<T>)service.query(httpContext, httpContextRsp, condition, param);
        return ModelServiceUtil.processRestResponse(response);
    }
}
