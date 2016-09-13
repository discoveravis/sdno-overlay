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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDc;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDc.Site;
import org.openo.sdno.overlayvpn.model.servicemodel.Vpc;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.connection.ConnectionSvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.endpointgroup.EndPointGrpSvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.gateway.GatewaySvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mappingpolicy.MappingPolicySvcImpl;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.overlayvpn.OverlayVpnSvcImpl;

import mockit.Mocked;

public class SiteToDCOverlayVPNTest {

    @Mocked
    OverlayVpnSvcImpl overlayVpnSvc;

    @Mocked
    ConnectionSvcImpl connectionSvc;

    @Mocked
    EndPointGrpSvcImpl endpointGroupSvc;

    @Mocked
    GatewaySvcImpl gatewaySvc;

    @Mocked
    MappingPolicySvcImpl service;

    @Mocked
    InventoryDao inventoryDao;

    static SiteToDCOverlayVPN vpn = new SiteToDCOverlayVPN();
    static {
        vpn.setOverlayVpnSvc(new OverlayVpnSvcImpl());
        vpn.setConnectionSvc(new ConnectionSvcImpl());
        vpn.setEndpointGroupSvc(new EndPointGrpSvcImpl());
    }

    @Test
    public void testCreateOverlayVpn() throws ServiceException {
        SiteToDc siteToDc = new SiteToDc();
        OverlayVpn overlay = vpn.createOverlayVpn(null, null, siteToDc);
        assertTrue(overlay != null);
    }

    @Test(expected = ServiceException.class)
    public void testCreateEpgForSite() throws ServiceException {
        SiteToDc siteToDc = new SiteToDc();
        siteToDc.setTenantId("12345");
        Site site = siteToDc.new Site();
        site.setSitevCPE("sitevCPE");
        site.setSiteTypeAddress("192.1.1.1");
        siteToDc.setSite(site);

        OverlayVpn overlayVpn = new OverlayVpn();
        List<String> connectionIds = new ArrayList<String>();
        connectionIds.add("12345");
        connectionIds.add("12346");
        overlayVpn.setConnectionIds(connectionIds);
        Gateway gw = new Gateway();
        Vpc vpc = new Vpc();
        List<EndpointGroup> epg = vpn.createEpgForIpSec(null, null, siteToDc, overlayVpn, vpc);
        fail("Exception occured");
    }

    @Test(expected = Exception.class)
    public void testCreateVxLanPolicy() throws ServiceException {
        OverlayVpn overlayVpn = new OverlayVpn();
        overlayVpn.setTenantId("12345");
        Connection vxlanMapPolicy = vpn.createVxLanVpnConnection(null, null, overlayVpn);
        fail("Exception occured");
    }

    @Test(expected = ServiceException.class)
    public void testCreateVxLanVpnConnection() throws ServiceException {
        OverlayVpn overlayVpn = new OverlayVpn();
        VxlanMappingPolicy vxlanMappingPolicy = new VxlanMappingPolicy();
        Connection con = vpn.createVxLanVpnConnection(null, null, overlayVpn);
        fail("Exception occured");
    }

    @Test(expected = ServiceException.class)
    public void testupdateVpnStatusException() throws ServiceException {
        SiteToDc siteToDc = new SiteToDc();
        siteToDc.setUuid("uuid123");
        ResultRsp<OverlayVpn> res = vpn.updateVpnStatus(null, null, siteToDc);
        fail("Exception occured");
    }

    @Test(expected = ServiceException.class)
    public void testDeleteOverlayVpnException() throws ServiceException {
        ResultRsp<String> resp = vpn.deleteOverlayVpn(null, null, "12345");
        fail("Exception occured");
    }

    @Test(expected = ServiceException.class)
    public void testDeleteConnectionException() throws ServiceException {
        ResultRsp<String> resp = vpn.deleteConnection(null, null, "12345");
        fail("Exception occured");
    }

    @Test(expected = ServiceException.class)
    public void testDeleteEpgException() throws ServiceException {
        ResultRsp<String> resp = vpn.deleteEpg(null, null, "12345");
        fail("Exception occured");
    }

    @Test(expected = ServiceException.class)
    public void testDeleteGatewayException() throws ServiceException {
        ResultRsp<String> resp = vpn.deleteConnection(null, null, "12345");
        fail("Exception occured");
    }

    @Test(expected = ServiceException.class)
    public void testCreateIpSecMappingPolicyException() throws ServiceException {
        OverlayVpn overlayVpn = new OverlayVpn();
        overlayVpn.setUuid("1234");
        Connection policy = vpn.createIpSecConnection(null, null, overlayVpn);
        fail("Exception occured");
    }

    @Test(expected = ServiceException.class)
    public void testCreateIpSecConnectionException() throws ServiceException {
        OverlayVpn overlayVpn = new OverlayVpn();
        overlayVpn.setUuid("1234");
        IpsecMappingPolicy ipsecPolicy = new IpsecMappingPolicy();
        ipsecPolicy.setUuid("1234");
        Connection conn = vpn.createIpSecConnection(null, null, overlayVpn);
        fail("Exception occured");
    }

    @Test(expected = ServiceException.class)
    public void testCreateEpgForDC() throws ServiceException {
        OverlayVpn overlayVpn = new OverlayVpn();
        overlayVpn.setUuid("1234");
        List<String> list = new ArrayList<String>();
        list.add("12345");
        list.add("12346");
        overlayVpn.setConnectionIds(list);
        SiteToDc siteToDc = new SiteToDc();
        Site site = siteToDc.new Site();
        site.setSitevCPE("sitevCPE");
        siteToDc.setSite(site);
        siteToDc.setUuid("1234");
        Gateway gw = new Gateway();
        gw.setUuid("1234");
        Vpc vpc = new Vpc();
        List<EndpointGroup> epg = vpn.createEpgForIpSec(null, null, siteToDc, overlayVpn, vpc);
        assertTrue(epg != null);
    }
}
