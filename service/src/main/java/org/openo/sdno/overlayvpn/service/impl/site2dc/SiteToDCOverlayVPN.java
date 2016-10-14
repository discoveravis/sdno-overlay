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

package org.openo.sdno.overlayvpn.service.impl.site2dc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.EndpointType;
import org.openo.sdno.overlayvpn.model.common.enums.TechnologyType;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDc;
import org.openo.sdno.overlayvpn.model.servicemodel.Vpc;
import org.openo.sdno.overlayvpn.osdriver.OSDriverConfigUtil;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.security.authentication.TokenDataHolder;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.connection.ConnectionSvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.endpointgroup.EndPointGrpSvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.overlayvpn.OverlayVpnSvcImpl;
import org.openo.sdno.overlayvpn.util.check.CheckStrUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.openo.sdno.overlayvpn.util.objreflectoper.UuidAllocUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Site To DC service implementation<br>
 * <p>
 * Implements Site to DC between enterprise Site to Tenant VPC in DC or vDC.
 * </p>
 *
 * @author
 * @version SDNO 0.5 May 24, 2016
 */
@Service
public class SiteToDCOverlayVPN {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteToDCOverlayVPN.class);

    @Autowired
    private OverlayVpnSvcImpl overlayVpnSvc;

    @Autowired
    private ConnectionSvcImpl connectionSvc;

    @Autowired
    private EndPointGrpSvcImpl endpointGroupSvc;

    /**
     * Create OverlayVpn for Site To DC.<br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param siteToDc - SiteToDc to get request information
     * @return OverlayVpn - Returns created overlay VPN for service
     * @throws ServiceException - when updating the name or description in database fails
     * @since SDNO 0.5
     */
    public OverlayVpn createOverlayVpn(HttpServletRequest req, HttpServletResponse resp, SiteToDc siteToDc)
            throws ServiceException {

        // Get Tenant token
        String tenantIdFromToken = TokenDataHolder.getTenantID();
        if(!StringUtils.hasLength(tenantIdFromToken)) {
            ThrowOverlayVpnExcpt.throwTenantIdMissing(tenantIdFromToken);
        }

        /*
         * UUID should be null for overlay VPN create.
         */
        OverlayVpn overlayVpn = new OverlayVpn(siteToDc.getUuid(), tenantIdFromToken);
        overlayVpn.setName(siteToDc.getName());
        overlayVpn.setDescription(siteToDc.getDescription());

        /*
         * Create overlay VPN object in DB with state set to INACTIVE.
         */
        OverlayVpn resultOverlayVpn = overlayVpnSvc.create(req, resp, overlayVpn).getData();

        /*
         * Sync SiteToDC uuid with overlayVPN uuid.
         */
        siteToDc.setUuid(resultOverlayVpn.getUuid());

        return resultOverlayVpn;
    }

    /**
     * Create EPG's for VxLAN Connection.<br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param siteToDc - Site information
     * @param overlayVpn - Overlay VPN information
     * @return - Returns the created EPG information
     * @throws ServiceException - when insert in database fails
     * @since SDNO 0.5
     */
    public List<EndpointGroup> createEpgForVxLan(HttpServletRequest req, HttpServletResponse resp, SiteToDc siteToDc,
            OverlayVpn overlayVpn) throws ServiceException {

        EndpointGroup thinCpeEpg = new EndpointGroup();
        UuidAllocUtil.allocUuid(thinCpeEpg);
        thinCpeEpg.setTenantId(overlayVpn.getTenantId());
        thinCpeEpg.setName(overlayVpn.getName() + "_" + "VxLANThinCpeEpg");
        thinCpeEpg.setDescription(overlayVpn.getDescription());

        thinCpeEpg.setNeId(siteToDc.getSite().getSiteThinCPE());
        thinCpeEpg.setType(EndpointType.PORT_VLAN.getName());
        String thinCPEEndPoints = JsonUtil.toJson(Arrays.asList(siteToDc.getSite().getPortAndVlan()));
        thinCpeEpg.setEndpoints(thinCPEEndPoints);
        thinCpeEpg.setConnectionId(overlayVpn.getConnectionIds().get(0));

        ResultRsp<EndpointGroup> thinCPEEpgRsp = endpointGroupSvc.create(req, resp, thinCpeEpg);

        ThrowOverlayVpnExcpt.checkRspThrowException(thinCPEEpgRsp);

        EndpointGroup vCpeEpg = new EndpointGroup();
        UuidAllocUtil.allocUuid(vCpeEpg);
        vCpeEpg.setTenantId(overlayVpn.getTenantId());
        vCpeEpg.setName(overlayVpn.getName() + "_" + "VxLANvCpeEpg");
        vCpeEpg.setDescription(overlayVpn.getDescription());

        vCpeEpg.setNeId(siteToDc.getSite().getSitevCPE());
        vCpeEpg.setType(EndpointType.PORT_VLAN.getName());
        String vCPEEndPoints = JsonUtil.toJson(Arrays.asList(siteToDc.getSite().getPortAndVlan()));
        vCpeEpg.setEndpoints(vCPEEndPoints);
        vCpeEpg.setConnectionId(overlayVpn.getConnectionIds().get(0));

        ResultRsp<EndpointGroup> vCpeEpgRsp = endpointGroupSvc.create(req, resp, vCpeEpg);

        ThrowOverlayVpnExcpt.checkRspThrowException(vCpeEpgRsp);

        List<EndpointGroup> epgList = new ArrayList<EndpointGroup>();
        epgList.add(thinCPEEpgRsp.getData());
        epgList.add(vCpeEpgRsp.getData());

        return epgList;
    }

    /**
     * Create connection for VxLAN.<br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param overlayVpn - OverlayVpn for which this connection is being created
     * @return Connection
     * @throws ServiceException when create connection failed
     * @since SDNO 0.5
     */
    public Connection createVxLanVpnConnection(HttpServletRequest req, HttpServletResponse resp, OverlayVpn overlayVpn)
            throws ServiceException {

        // Prepare connection data and insert into database
        Connection connection = new Connection();

        UuidAllocUtil.allocUuid(connection);
        connection.setName(overlayVpn.getName() + "_" + "VxLANConnection");
        connection.setTopology(TopologyType.POINT_TO_POINT.getName());
        connection.setTenantId(overlayVpn.getTenantId());
        connection.setTechnology(TechnologyType.VXLAN.getName());
        connection.setCompositeVpnId(overlayVpn.getUuid());
        connection.setAdminStatus(overlayVpn.getAdminStatus());

        // Persist the information in database
        ResultRsp<Connection> resultRsp = connectionSvc.create(req, resp, connection);

        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        // Set connection information to OverlayVpn
        overlayVpn.setVpnConnections(new ArrayList<>(Arrays.asList(connection)));
        overlayVpn.setConnectionIds(new ArrayList<>(Arrays.asList(connection.getUuid())));

        return resultRsp.getData();
    }

    /**
     * Update OverlayVpn status.<br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param siteToDc SiteToDc instance
     * @return OverlayVpn updated
     * @throws ServiceException when update status failed
     * @since SDNO 0.5
     */
    public ResultRsp<OverlayVpn> updateVpnStatus(HttpServletRequest req, HttpServletResponse resp, SiteToDc siteToDc)
            throws ServiceException {

        // 1. Get Tenant ID
        String tenantIdFromToken = TokenDataHolder.getTenantID();

        // 2. Validate the UUID
        CheckStrUtil.checkUuidStr(siteToDc.getUuid());

        // 3. Query the Overlay VPN from database
        ResultRsp<OverlayVpn> overlayVpnRsp = overlayVpnSvc.query(req, resp, siteToDc.getUuid(), tenantIdFromToken);
        OverlayVpn oOldSite2DcVpn = overlayVpnRsp.getData();
        if(null == oOldSite2DcVpn) {
            LOGGER.error("updateVpnStatus oOldSite2DcVpn is null for siteToDc.getUuid(): " + siteToDc.getUuid());
            ThrowOverlayVpnExcpt.throwResNotExistAsNotFound("Overlay VPN", siteToDc.getUuid());
            return new ResultRsp<OverlayVpn>();
        }

        // 4.Build New VPN Data with Inactive Status
        OverlayVpn newSite2DcVpn = new OverlayVpn();
        newSite2DcVpn.copyBasicData(oOldSite2DcVpn);
        newSite2DcVpn.setConnectionIds(oOldSite2DcVpn.getConnectionIds());
        newSite2DcVpn.setAdminStatus(AdminStatus.INACTIVE.getName());

        // 5.Update the Overlay VPN service
        ResultRsp<OverlayVpn> resultRsp = overlayVpnSvc.update(req, resp, newSite2DcVpn, oOldSite2DcVpn);

        // 6.Throw exception if update / undeploy fails
        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        return resultRsp;

    }

    /**
     * Delete OverlayVpn.<br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param overlayVpnId OverlayVpn id
     * @return operation result
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public ResultRsp<String> deleteOverlayVpn(HttpServletRequest req, HttpServletResponse resp, String overlayVpnId)
            throws ServiceException {

        // 1. Get Tenant ID
        String tenantId = TokenDataHolder.getTenantID();

        // 2. Validate the UUID
        CheckStrUtil.checkUuidStr(overlayVpnId);

        // 3. Query the Connection from database
        ResultRsp<OverlayVpn> vpnConnRsp = overlayVpnSvc.query(req, resp, overlayVpnId, tenantId);
        OverlayVpn queryedVpnConn = vpnConnRsp.getData();
        if(null == queryedVpnConn) {
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        // 4. Delete VPN Connection
        ResultRsp<String> resultRsp = overlayVpnSvc.delete(req, resp, queryedVpnConn);

        // 5. Throw exception if any error
        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        return resultRsp;
    }

    /**
     * Delete Connection.<br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param strConnectionId Connection Id
     * @return operation result
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public ResultRsp<String> deleteConnection(HttpServletRequest req, HttpServletResponse resp, String strConnectionId)
            throws ServiceException {

        // 1. Get Tenant ID
        String tenantId = TokenDataHolder.getTenantID();

        // 2. Validate the UUID
        CheckStrUtil.checkUuidStr(strConnectionId);

        // 3. Query the Connection from database
        ResultRsp<Connection> vpnConnRsp = connectionSvc.query(req, resp, strConnectionId, tenantId);
        Connection queryedVpnConn = vpnConnRsp.getData();
        if(null == queryedVpnConn) {
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        // 4. Delete VPN Connection
        ResultRsp<String> resultRsp = connectionSvc.delete(req, resp, queryedVpnConn);

        // 5. Throw exception if any error
        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        return new ResultRsp<>(resultRsp);
    }

    /**
     * Delete EndPoingGroup.<br>
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param strEpgId EndPoingGroup id
     * @return operation result
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public ResultRsp<String> deleteEpg(HttpServletRequest req, HttpServletResponse resp, String strEpgId)
            throws ServiceException {

        // 1. Get Tenant ID
        String tenantId = TokenDataHolder.getTenantID();

        // 2. Validate the UUID
        CheckStrUtil.checkUuidStr(strEpgId);

        // 3. Query the Endpoint group from database
        ResultRsp<EndpointGroup> endpointGrpRsp = endpointGroupSvc.query(req, resp, strEpgId, tenantId);
        EndpointGroup invEpg = endpointGrpRsp.getData();
        if(null == invEpg) {
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        // 4. Delete EPG id
        ResultRsp<String> resultRsp = endpointGroupSvc.delete(req, resp, invEpg);

        // 5. Throw exception if any error
        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);
        return resultRsp;
    }

    public void setOverlayVpnSvc(OverlayVpnSvcImpl overlayVpnSvc) {
        this.overlayVpnSvc = overlayVpnSvc;
    }

    public void setConnectionSvc(ConnectionSvcImpl connectionSvc) {
        this.connectionSvc = connectionSvc;
    }

    public void setEndpointGroupSvc(EndPointGrpSvcImpl endpointGroupSvc) {
        this.endpointGroupSvc = endpointGroupSvc;
    }

    /**
     * Create Connection for IPSec service
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param overlayVpn OverlayVpn data
     * @return Connection created
     * @throws ServiceException throws when create connection failed
     */
    public Connection createIpSecConnection(HttpServletRequest req, HttpServletResponse resp, OverlayVpn overlayVpn)
            throws ServiceException {
        // Prepare connection data and insert into database
        Connection connection = new Connection();

        UuidAllocUtil.allocUuid(connection);
        connection.setName(overlayVpn.getName() + "_" + "IpSecConnection");
        connection.setTenantId(overlayVpn.getTenantId());
        connection.setTopology(TopologyType.POINT_TO_POINT.getName());
        connection.setTechnology(TechnologyType.IPSEC.getName());
        connection.setCompositeVpnId(overlayVpn.getUuid());
        connection.setAdminStatus(overlayVpn.getAdminStatus());

        // Persist the information in database
        ResultRsp<Connection> resultRsp = connectionSvc.create(req, resp, connection);

        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        // Set connection information to overlay VPN
        if(overlayVpn.getVpnConnections() != null) {
            overlayVpn.getVpnConnections().addAll(Arrays.asList(connection));
            overlayVpn.getConnectionIds().addAll(Arrays.asList(connection.getUuid()));
        } else {
            overlayVpn.setVpnConnections(Arrays.asList(connection));
            overlayVpn.setConnectionIds(Arrays.asList(connection.getUuid()));
        }

        return resultRsp.getData();
    }

    /**
     * Create EPG object for IpSec Connection
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param siteToDc SiteToDc data
     * @param overlayVpn OverlayVpn data
     * @param vpc Vpc network info
     * @return Epg for IpSec
     * @throws ServiceException throws when create failed
     */
    public List<EndpointGroup> createEpgForIpSec(HttpServletRequest req, HttpServletResponse resp, SiteToDc siteToDc,
            OverlayVpn overlayVpn, Vpc vpc) throws ServiceException {

        EndpointGroup vCpeEpg = new EndpointGroup();
        UuidAllocUtil.allocUuid(vCpeEpg);

        vCpeEpg.setTenantId(overlayVpn.getTenantId());
        vCpeEpg.setName(overlayVpn.getName() + "_" + "IpSecvCPEEpg");
        vCpeEpg.setDescription(overlayVpn.getDescription());

        vCpeEpg.setConnectionId(overlayVpn.getConnectionIds().get(1));
        vCpeEpg.setNeId(siteToDc.getSite().getSitevCPE());
        vCpeEpg.setType(EndpointType.CIDR.getName());
        vCpeEpg.setEndpoints(JsonUtil.toJson(Arrays.asList(siteToDc.getSite().getSiteTypeAddress())));

        ResultRsp<EndpointGroup> vCpeEpgRsp = endpointGroupSvc.create(req, resp, vCpeEpg);

        ThrowOverlayVpnExcpt.checkRspThrowException(vCpeEpgRsp);

        // Create EndPointGroup For DC
        EndpointGroup dcEpg = new EndpointGroup();
        UuidAllocUtil.allocUuid(dcEpg);

        dcEpg.setTenantId(overlayVpn.getTenantId());
        dcEpg.setName(overlayVpn.getName() + "_" + "IpSecDcEpg");
        dcEpg.setDescription(overlayVpn.getDescription());

        dcEpg.setConnectionId(overlayVpn.getConnectionIds().get(1));
        dcEpg.setNeId(OSDriverConfigUtil.getOSDriverNeId());
        dcEpg.setType(EndpointType.VPC.getName());
        dcEpg.setEndpoints(JsonUtil.toJson(Arrays.asList(createVpcEndpointInfo(vpc))));

        ResultRsp<EndpointGroup> dcEpgRsp = endpointGroupSvc.create(req, resp, dcEpg);
        ThrowOverlayVpnExcpt.checkRspThrowException(dcEpgRsp);

        List<EndpointGroup> epgList = new ArrayList<EndpointGroup>();
        epgList.add(vCpeEpg);
        epgList.add(dcEpg);

        return epgList;
    }

    private String createVpcEndpointInfo(Vpc vpc) {

        StringBuilder endPointInfo = new StringBuilder();
        endPointInfo.append("cidr|").append(vpc.getUuid()).append('|').append(vpc.getAttributes().getRouterId())
                .append('|').append(vpc.getExternalIp()).append('|').append(vpc.getSubnet().getUuid());

        return endPointInfo.toString();
    }
}
