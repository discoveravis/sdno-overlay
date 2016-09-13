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

package org.openo.sdno.overlayvpn.util.operation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.springframework.util.StringUtils;

public class ConnectionUtilTest {

    @Test
    public void testBuildBatchQueryFilterException() throws ServiceException {
        ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
    }

    @Test
    public void testBuildBatchQueryFilter() throws ServiceException {
        ConnectionUtil.buildBatchQueryFilter("tenansdftId", "overlayVpnId");

    }

    @Test
    public void testBuildBatchQueryFilterNormal() throws ServiceException {
        String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");

    }

    @Test
    public void testBuildBatchQueryFilterNormalDoBuildFilterMap2() {
        String description = null;
        try {
            String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Exception Occured");
        }
    }

    @Test
    public void testBuildBatchQueryFilterNormalDoBuildFilterMap3() {
        String adminStatus = null;
        try {
            String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Exception Occured");
        }
    }

    @Test
    public void testBuildBatchQueryFilterNormalDoBuildFilterMap4() {
        String operStatus = null;
        try {
            String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Exception Occured");
        }
    }

    @Test
    public void testBuildBatchQueryFilterNormalDoBuildFilterMap5() {
        String overlayVpnId = null;
        try {
            String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Exception Occured");
        }
    }

    @Test
    public void testBuildBatchQueryFilterNormalDoBuildFilterMap6() {
        String topology = null;
        try {
            String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Exception Occured");
        }
    }

    @Test
    public void testBuildBatchQueryFilterNormalDoBuildFilterMap7() {
        String name = null;
        try {
            String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Exception Occured");
        }
    }

    @Test
    public void testBuildBatchQueryFilterNormalDoBuildFilterMap8() {
        String technology = null;
        try {
            String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Exception Occured");
        }
    }

    @Test
    public void testBuildBatchQueryFilterNormalDoBuildFilterMap9() {
        String mappingPolicyId = null;
        try {
            String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Exception Occured");
        }
    }

    @Test
    public void testBuildBatchQueryFilterNormalDoBuildFilterMap1() {
        String name = null;
        try {
            String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Exception Occured");
        }

    }

    @Test
    public void testBuildBatchQueryFilterAbnormal() throws ServiceException {
        String overlayVpnId = null;
        try {
            String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
        } catch(ServiceException e) {
            assertTrue(StringUtils.hasLength(overlayVpnId) && !UuidUtil.validate(overlayVpnId));
        }
    }

    @Test
    public void testBuildBatchQueryFilterNormalWithTeidOvpnId() throws ServiceException {
        String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");

        assertTrue(result.indexOf("overlayVpnId") > -1);

    }

    @Test
    public void testBuildBatchQueryFilter2Normal() throws ServiceException {
        String result = ConnectionUtil.buildBatchQueryFilter("tenantId", "overlayVpnId");
        assertTrue(result.indexOf("overlayVpnId") > -1);
        assertTrue(result.indexOf("tenantId") > -1);
    }

    @Test
    public void testBuildNewConnByOldAndInputDataNormal() throws ServiceException {
        Connection con = new Connection();
        con.setActionState("active");
        con.setUuid("uuid");
        con.setOperStatus("up");
        con.setAdminStatus("active");
        con.setName("name123");
        con.setDescription("desc123");
        String inputJsonStr = JsonUtil.toJson(con);
        ResultRsp<Connection> resRsp = ConnectionUtil.buildNewConnByOldAndInputData(con, inputJsonStr);

        assertTrue(resRsp.isSuccess());
    }

    @Test(expected = ServiceException.class)
    public void testBuildNewConnByOldAndInputDataException() throws ServiceException {
        Connection con = new Connection();
        String inputJsonStr = "";
        ResultRsp<Connection> resRsp = ConnectionUtil.buildNewConnByOldAndInputData(con, inputJsonStr);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public void testBuildNewConnByOldAndInputDataException2() throws ServiceException {
        Connection con = new Connection();
        String inputJsonStr = JsonUtil.toJson(null);
        ResultRsp<Connection> resRsp = ConnectionUtil.buildNewConnByOldAndInputData(con, inputJsonStr);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public void testBuildNewConnByOldAndInputDataExceptionInName() throws ServiceException {
        Connection con = new Connection();
        con.setActionState("active");
        con.setUuid("uuid");
        con.setOperStatus("up");
        con.setAdminStatus("active");
        con.setName(
                "1231234123efddsfgdgertgdfgvdertgefgxfvbdrtgetrxdfvbedrtgerdfvbdfgetrgefxdcfbvdrtge4545646456456456456fdghfhrtyhgfghrftyhrghcghrthrgfcbvcbft");
        String inputJsonStr = JsonUtil.toJson(con);
        ResultRsp<Connection> resRsp = ConnectionUtil.buildNewConnByOldAndInputData(con, inputJsonStr);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public void testBuildNewConnByOldAndInputDataExceptionInDescription() throws ServiceException {
        Connection con = new Connection();
        con.setActionState("active");
        con.setUuid("uuid");
        con.setOperStatus("up");
        con.setAdminStatus("active");
        con.setDescription(
                "12344444444444444444455555555555555551234123efddsfgdgertgdfgvdertgefgxfvbdrtgetrxdfvbedrtgerdfvbdfgetrgefxdcfbvdrtge4545646456456456456fdghfhrtyhgfghrftyhrghcghrthrgfcbvcbft6656656565665656566587878909009dfgfgdfgdfgdfgdfgdfgdcfgdfgdfgdfgerdcfdfgd5454566545545434345567ghg");
        String inputJsonStr = JsonUtil.toJson(con);
        ResultRsp<Connection> resRsp = ConnectionUtil.buildNewConnByOldAndInputData(con, inputJsonStr);
        fail("Exception not occured");
    }

    @Test
    public void testIsExistHubEpgNormal() {
        Connection conn = new Connection();
        List<EndpointGroup> endpointGroups = new ArrayList<EndpointGroup>();
        EndpointGroup epg = new EndpointGroup();
        epg.setUuid("uuid");
        epg.setTopologyRole("hub");
        endpointGroups.add(epg);
        conn.setEndpointGroups(endpointGroups);
        boolean isTrue = ConnectionUtil.isExistHubEpg(conn);
        assertTrue(isTrue);
    }

}
