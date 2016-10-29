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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.EndpointType;
import org.openo.sdno.overlayvpn.model.servicechain.ServiceChainPath;
import org.openo.sdno.overlayvpn.model.servicechain.ServiceChainSiteToDcRelation;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDc;
import org.openo.sdno.overlayvpn.model.servicemodel.Vpc;
import org.openo.sdno.overlayvpn.model.servicemodel.VpcSubNetMapping;
import org.openo.sdno.overlayvpn.osdriver.OSDriverConfigUtil;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.sbi.overlayvpn.ServiceChainService;
import org.openo.sdno.overlayvpn.security.authentication.TokenDataHolder;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.connection.ConnectionSvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.endpointgroup.EndPointGrpSvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.overlayvpn.OverlayVpnSvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.vpcsubnet.VpcSubnetImpl;
import org.openo.sdno.overlayvpn.service.inf.overlayvpn.ISiteToDC;
import org.openo.sdno.overlayvpn.util.check.CheckStrUtil;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.openo.sdno.overlayvpn.util.operation.ServiceChainDbOper;
import org.openo.sdno.overlayvpn.util.operation.VpcSubnetUtil;
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
public class SiteToDCSvcImpl implements ISiteToDC {

    @Autowired
    private OverlayVpnSvcImpl overlayVpnSvc;

    @Autowired
    private ConnectionSvcImpl connectionSvc;

    @Autowired
    private EndPointGrpSvcImpl endPointGrpSvc;

    @Autowired
    private SiteToDCOverlayVPN siteToDCOverlayVPN;

    @Autowired
    private VpcSubnetImpl vpcSubNetSvc;

    @Autowired
    private ServiceChainService serviceChainServiceSbi;

    @Autowired
    private InventoryDao<OverlayVpn> inventoryDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteToDCSvcImpl.class);

    private static final int HOP_NUMBER = 1;

    /**
     * Constructor<br>
     *
     * @since SDNO 0.5
     */
    public SiteToDCSvcImpl() {
        super();
    }

    /**
     * Site To DC create operation<br>
     *
     * @param httpContext - HTTP context for security token
     * @param siteToDC - Overlay VPN information
     * @return Success or Failure and SiteToDC information
     * @throws ServiceException - when deploy the service fails or update database fails
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<SiteToDc> create(HttpServletRequest req, HttpServletResponse resp, SiteToDc siteToDc)
            throws ServiceException {

        long infterEnterTime = System.currentTimeMillis();

        // 1.Create overlay VPN and store in database in CREATING state.
        OverlayVpn overlayVpn = siteToDCOverlayVPN.createOverlayVpn(req, resp, siteToDc);

        // 2. Create VxLan Connection
        Connection connection = siteToDCOverlayVPN.createVxLanVpnConnection(req, resp, overlayVpn);
        List<EndpointGroup> vxLanEpgList = siteToDCOverlayVPN.createEpgForVxLan(req, resp, siteToDc, overlayVpn);

        @SuppressWarnings("unchecked")
        List<String> vxLanEpgIdList = new ArrayList<String>(CollectionUtils.collect(vxLanEpgList, new Transformer() {

            @Override
            public Object transform(Object arg0) {
                return ((EndpointGroup)arg0).getUuid();
            }
        }));

        connection.setEpgIds(vxLanEpgIdList);
        connection.setEndpointGroups(vxLanEpgList);

        // 3.Load OSDriver Info
        OSDriverConfigUtil.loadOSDriverConfigData();
        siteToDc.getVpc().setOsControllerId(OSDriverConfigUtil.getOSControllerId());

        // 4. Create VPC & VPC Subnet
        Vpc vpcNetwork = vpcSubNetSvc.create(req, resp, siteToDc);

        // 5. Create IpSec Connection
        Connection ipSecConnection = siteToDCOverlayVPN.createIpSecConnection(req, resp, overlayVpn);
        List<EndpointGroup> ipSecEpgList =
                siteToDCOverlayVPN.createEpgForIpSec(req, resp, siteToDc, overlayVpn, vpcNetwork);

        @SuppressWarnings("unchecked")
        List<String> ipSecEpgIdList = new ArrayList<String>(CollectionUtils.collect(ipSecEpgList, new Transformer() {

            @Override
            public Object transform(Object arg0) {
                return ((EndpointGroup)arg0).getUuid();
            }
        }));

        ipSecConnection.setEpgIds(ipSecEpgIdList);
        ipSecConnection.setEndpointGroups(ipSecEpgList);

        // 6. Update and deploy OverlayVPN for Site To DC for VxLAN &IPsec service
        ResultRsp<OverlayVpn> resultRsp = overlayVpnSvc.deploy(req, resp, overlayVpn);
        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        // 7. Save data and Send data to serviceChain
        ServiceChainPath scp = new ServiceChainPath();
        scp.setUuid(UuidUtils.createUuid());
        scp.setName(vpcNetwork.getAttributes().getRouterId());
        scp.setServicePathHops(siteToDc.getSfp().getServicePathHops());
        scp.setScfNeId(siteToDc.getSfp().getScfNeId());
        ServiceChainSiteToDcRelation serviceChainnSiteToDcRelation = new ServiceChainSiteToDcRelation();
        serviceChainnSiteToDcRelation.setUuid(scp.getUuid());
        serviceChainnSiteToDcRelation.setVpnId(siteToDc.getUuid());
        serviceChainnSiteToDcRelation.setData(JsonUtil.toJson(scp));
        ServiceChainDbOper.create(serviceChainnSiteToDcRelation);

        serviceChainServiceSbi.create(req, scp);

        // 8. Update OverlayVpn Status
        overlayVpn.setAdminStatus(AdminStatus.ACTIVE.getName());
        overlayVpn.setActionState(ActionStatus.NORMAL.getName());

        // 9. Update database
        ResultRsp<OverlayVpn> resultDbRsp = inventoryDao.update(overlayVpn, null);
        ThrowOverlayVpnExcpt.checkRspThrowException(resultDbRsp);

        LOGGER.info("Exit query Site2DC method. Cost time = " + (System.currentTimeMillis() - infterEnterTime));

        return new ResultRsp<SiteToDc>(ErrorCode.OVERLAYVPN_SUCCESS, siteToDc);
    }

    @Override
    public ResultRsp<List<SiteToDc>> batchQuery(HttpServletRequest req, HttpServletResponse resp, String tenantId,
            String filter) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultRsp<SiteToDc> deploy(HttpServletRequest req, HttpServletResponse resp, SiteToDc request)
            throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResultRsp<SiteToDc> undeploy(HttpServletRequest req, HttpServletResponse resp, SiteToDc request)
            throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Query Overlay VPN information for Site to DC scenario<br>
     *
     * @param httpContext - HTTP Context to get the tenant ID
     * @param uuid - UUID of the overlay VPN
     * @param tenantId - Tenant ID
     * @return - SiteToDC information
     * @throws ServiceException- when resource do not exist or fetching from database fails
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<SiteToDc> query(HttpServletRequest req, HttpServletResponse resp, String uuid, String tenantId)
            throws ServiceException {

        long infterEnterTime = System.currentTimeMillis();
        LOGGER.info("Enter query Site2DC method. Begin time = " + infterEnterTime);

        SiteToDc oSite2Dc = new SiteToDc("");

        // 1. Query the Overlay VPN from database - Query common information
        ResultRsp<OverlayVpn> overlayVpnRsp = overlayVpnSvc.query(req, resp, uuid, tenantId);
        OverlayVpn oSite2DcVpn = overlayVpnRsp.getData();
        if(null == oSite2DcVpn) {
            LOGGER.error("Query OverlayVpn " + uuid + " not exist");
            return new ResultRsp<SiteToDc>();
        }

        oSite2Dc.setUuid(oSite2DcVpn.getUuid());
        oSite2Dc.setName(oSite2DcVpn.getName());
        oSite2Dc.setDescription(oSite2DcVpn.getDescription());

        // 2. Query Site information
        for(String connectionId : oSite2DcVpn.getConnectionIds()) {

            // 2.1. Validate the UUID
            CheckStrUtil.checkUuidStr(connectionId);

            // 2.2. Query the Connection from database
            ResultRsp<Connection> vpnConnRsp = connectionSvc.query(req, resp, connectionId, tenantId);
            Connection queryedVpnConn = vpnConnRsp.getData();
            if(null == queryedVpnConn) {
                continue;
            }

            // 2.3. Query EPG and gateway information
            for(String epgId : queryedVpnConn.getEpgIds()) {

                // 2.3.1 Query the end point group from database
                ResultRsp<EndpointGroup> endpointGrpRsp = endPointGrpSvc.query(req, resp, epgId, tenantId);
                EndpointGroup invEpg = endpointGrpRsp.getData();
                if(null == invEpg) {
                    continue;
                }

                // 2.3.3 Query Port/VLAN information from end point list
                if(null != invEpg.getGatewayId()) {
                    oSite2Dc.getSite().setSiteGatewayId(invEpg.getGatewayId());
                }

                if(EndpointType.CIDR.getName().equals(invEpg.getType())) {
                    oSite2Dc.getSite().setSiteTypeAddress(invEpg.getEndpoints());
                }

                if(EndpointType.PORT_VLAN.getName().equals(invEpg.getType())) {
                    oSite2Dc.getSite().setPortAndVlan(invEpg.getEndpoints());
                }
            }
        }

        // 3. Query VPC/Subnet information
        VpcSubNetMapping vsMapping = vpcSubNetSvc.query(req, resp, oSite2Dc.getUuid());
        if(null == vsMapping) {
            LOGGER.error("Query vpc " + uuid + " not exist");
            return new ResultRsp<SiteToDc>();
        }

        VpcSubnetUtil.setSiteToDcByVpcMapping(oSite2Dc, vsMapping);

        // 4. Query ServiceChain
        ServiceChainSiteToDcRelation relation = ServiceChainDbOper.query(oSite2Dc.getUuid());
        if((null == relation) || (null == relation.getData())) {
            LOGGER.error("Query service chain " + uuid + " not exist");
            return new ResultRsp<SiteToDc>();
        }

        ServiceChainPath scPath = JsonUtil.fromJson(relation.getData(), ServiceChainPath.class);
        oSite2Dc.getSfp().setScfNeId(scPath.getScfNeId());
        oSite2Dc.getSfp().setServicePathHops(scPath.getServicePathHops());

        LOGGER.info("Exit query Site2DC method. Cost time = " + (System.currentTimeMillis() - infterEnterTime));

        return new ResultRsp<SiteToDc>(ErrorCode.OVERLAYVPN_SUCCESS, oSite2Dc);
    }

    /**
     * Update Overlay VPN Name or Description<br>
     *
     * @param httpContext - HTTP Context for security token
     * @param newReq - Site2DC information with update name or description
     * @param oldReq - Old Site2DC information
     * @return - Updated Site2DC information
     * @throws ServiceException - when updating the name or description in database fails
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<SiteToDc> update(HttpServletRequest req, HttpServletResponse resp, SiteToDc newReq,
            SiteToDc oldReq) throws ServiceException {

        String tenantIdFromToken = TokenDataHolder.getTenantID();

        // 1. Query old overlay VPN
        ResultRsp<OverlayVpn> overlayVpnRsp = overlayVpnSvc.query(req, resp, oldReq.getUuid(), tenantIdFromToken);
        OverlayVpn oldBasicCloudVpn = overlayVpnRsp.getData();
        if(null == oldBasicCloudVpn) {
            LOGGER.error("This OverlayVpn not exist!!");
            ThrowOverlayVpnExcpt.throwResNotExistAsNotFound("Overlay VPN", oldReq.getUuid());
        }

        // 2. Prepare new overlay VPN
        OverlayVpn newOverlayVpn = new OverlayVpn();
        newOverlayVpn.copyBasicData(oldBasicCloudVpn);

        // 3.1 Copy name to new overlay VPN
        String name = newReq.getName();
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }
            newOverlayVpn.setName(name);
        }

        // 3.2 Copy description to new overlay VPN
        String description = newReq.getDescription();
        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }
            newOverlayVpn.setDescription(description);
        }

        // 4. Update overlay VPN to database
        ResultRsp<OverlayVpn> resultRsp = overlayVpnSvc.update(req, resp, newOverlayVpn, oldBasicCloudVpn);

        ThrowOverlayVpnExcpt.checkRspThrowException(resultRsp);

        return new ResultRsp<SiteToDc>(ErrorCode.OVERLAYVPN_SUCCESS, newReq);
    }

    /**
     * Undeploy and delete the Overlay VPN for Site to DC Scenario<br>
     *
     * @param httpContext - HTTP Context to get the tenant id
     * @param siteToDc - Contains the Site and DC information
     * @return - Success or Failure Code
     * @throws ServiceException - when undeploy or deleting the VPN from database fails
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<String> delete(HttpServletRequest req, HttpServletResponse resp, SiteToDc siteToDc)
            throws ServiceException {

        long infterEnterTime = System.currentTimeMillis();
        LOGGER.info("Enter delete Site2DC method. Begin time = " + infterEnterTime);

        // 1. UnDeploy the VXLAN service on adapter/controller and update the VPN Status to
        // inactive.It also gets all the connection IDs for the overlayVPN from database.
        ResultRsp<OverlayVpn> overlayVpn = siteToDCOverlayVPN.updateVpnStatus(req, resp, siteToDc);
        if(null == overlayVpn.getData()) {
            ThrowOverlayVpnExcpt.throwResNotExistAsNotFound("Overlay VPN", siteToDc.getUuid());
        }

        // 2. Delete all EPGIDs in the connection and then delete connection
        for(String connectionId : overlayVpn.getData().getConnectionIds()) {

            // 2.1. Get Tenant ID
            String tenantId = TokenDataHolder.getTenantID();

            // 2.2. Validate the UUID
            CheckStrUtil.checkUuidStr(connectionId);

            // 2.3. Query the EPG id's from database by connection ID filter
            ResultRsp<Connection> vpnConnRsp = connectionSvc.query(req, resp, connectionId, tenantId);
            Connection queryedVpnConn = vpnConnRsp.getData();
            if(null == queryedVpnConn) {
                continue;
            }

            // 2.4. Delete the EPG id's from database and adapter database
            for(String epgId : queryedVpnConn.getEpgIds()) {
                siteToDCOverlayVPN.deleteEpg(req, resp, epgId);
            }

            // 2.5 Delete the connection from database
            siteToDCOverlayVPN.deleteConnection(req, resp, connectionId);
        }

        // 3. Delete VPC and SubNet
        vpcSubNetSvc.delete(req, resp, siteToDc.getUuid());

        // 4. Delete service chain
        ServiceChainSiteToDcRelation serviceChainDbData = ServiceChainDbOper.query(siteToDc.getUuid());
        if(null != serviceChainDbData) {
            serviceChainServiceSbi.delete(req, serviceChainDbData.getUuid());
            ServiceChainDbOper.delete(serviceChainDbData.getUuid());
        }

        // 5. Delete OverlayVPN from database
        ResultRsp<String> resultRsp = siteToDCOverlayVPN.deleteOverlayVpn(req, resp, siteToDc.getUuid());

        LOGGER.info("Exit delete Site2DC method. cost time = " + (System.currentTimeMillis() - infterEnterTime));

        return resultRsp;
    }

    public void setOverlayVpnSvc(OverlayVpnSvcImpl overlayVpnSvc) {
        this.overlayVpnSvc = overlayVpnSvc;
    }

    public void setConnectionSvc(ConnectionSvcImpl connectionSvc) {
        this.connectionSvc = connectionSvc;
    }

    public void setEndPointGrpSvc(EndPointGrpSvcImpl endPointGrpSvc) {
        this.endPointGrpSvc = endPointGrpSvc;
    }

    public void setSiteToDCOverlayVPN(SiteToDCOverlayVPN siteToDCOverlayVPN) {
        this.siteToDCOverlayVPN = siteToDCOverlayVPN;
    }

    public void setVpcSubNetSvc(VpcSubnetImpl vpcSubNetSvc) {
        this.vpcSubNetSvc = vpcSubNetSvc;
    }

    public void setServiceChainServiceSbi(ServiceChainService serviceChainServiceSbi) {
        this.serviceChainServiceSbi = serviceChainServiceSbi;
    }
}
