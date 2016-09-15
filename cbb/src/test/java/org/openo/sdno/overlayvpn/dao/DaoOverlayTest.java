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

package org.openo.sdno.overlayvpn.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.dao.overlayvpn.MappingPolicyUtil;
import org.openo.sdno.overlayvpn.dao.overlayvpn.OverlayVpnComplexDaoUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.PuerInvDAOImpl;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.PuerInvServicesImpl;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.nbi.PuerInvServiceNbiBean;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.BaseMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;

import mockit.Mock;
import mockit.MockUp;

public class DaoOverlayTest {

    @Test
    public void testBuildUUIDMappingPolicyUtil() {

        try {
            BaseMappingPolicy baseMapping = new BaseMappingPolicy();
            baseMapping.allocateUuid();
            baseMapping.setDescription("TestBase Policy");
            List<BaseMappingPolicy> totalMappingPolicies = new ArrayList<BaseMappingPolicy>();
            totalMappingPolicies.add(baseMapping);
            Map<String, BaseMappingPolicy> policyMap = MappingPolicyUtil.buildUuidToMappingPolicyMap(totalMappingPolicies);
            assertEquals(1, policyMap.size());
        } catch(Exception ex) {
            fail("Exception while building the UUID to MappingPolicy");
        }
    }

    @Test
    public void testBuildComplexMappingPolicyUtil() {

        new MockUp<InventoryDao>() {

            @Mock
            public ResultRsp batchQuery(Class clazz, String filter) throws ServiceException {
                if(VxlanMappingPolicy.class.equals(clazz)) {
                    return new ResultRsp<List<VxlanMappingPolicy>>(ErrorCode.OVERLAYVPN_SUCCESS,
                            Arrays.asList(new VxlanMappingPolicy()));
                } else if(IpsecMappingPolicy.class.equals(clazz)) {
                    return new ResultRsp<List<IpsecMappingPolicy>>(ErrorCode.OVERLAYVPN_SUCCESS,
                            Arrays.asList(new IpsecMappingPolicy()));
                }
                return null;
            }
        };

        try {
            @SuppressWarnings("rawtypes")
            InventoryDao invDao = new InventoryDao();
            @SuppressWarnings("rawtypes")
            PuerInvDAOImpl pinvDao = new PuerInvDAOImpl();
            PuerInvServiceNbiBean puerObjInvService = new PuerInvServiceNbiBean();
            List<Object> listMapValue = new ArrayList<Object>();
            listMapValue.add("TestObj");
            listMapValue.add("TestNE");
            pinvDao.setPuerObjInvService(puerObjInvService);
            invDao.setInvDao(pinvDao);
            PuerInvServicesImpl invService = new PuerInvServicesImpl();
            List moList = new ArrayList<>();
            moList.add("MO1");
            invDao.setInvService(invService);
            List<Connection> totalMappingPolicies = new ArrayList<Connection>();
            Connection baseconn = new Connection();
            baseconn.allocateUuid();
            baseconn.setDescription("TestBase Policy");
            baseconn.setMappingPolicyId("PolicyID");
            baseconn.setTechnology("vxlan");
            totalMappingPolicies.add(baseconn);
            baseconn = new Connection();
            baseconn.allocateUuid();
            baseconn.setDescription("TestBase Policy");
            baseconn.setMappingPolicyId("PolicyID");
            baseconn.setTechnology("ipsec");
            totalMappingPolicies.add(baseconn);
            baseconn = new Connection();
            baseconn.allocateUuid();
            baseconn.setDescription("TestBase Policy");
            baseconn.setMappingPolicyId("PolicyID");
            baseconn.setTechnology("gre");
            totalMappingPolicies.add(baseconn);

            List<BaseMappingPolicy> mappingPolicies = MappingPolicyUtil.getTotalMappingPoliciesFromConn(totalMappingPolicies, invDao);
            assertEquals(2, mappingPolicies.size());
        } catch(ServiceException e) {
            fail("Exception while building the complex to MappingPolicy");
        }
    }

    @Test
    public void testOverlayVpnComplexDaoUtilForInvalidInput() {

        List<OverlayVpn> totalVpns = new ArrayList<OverlayVpn>();
        List<Connection> connList = new ArrayList<Connection>();
        List<EndpointGroup> epgList = new ArrayList<EndpointGroup>();
        boolean isNeedMappingPolicy = false;
        InventoryDao inventoryDao = new InventoryDao();

        try {
            OverlayVpnComplexDaoUtil.buildComplexOverlayVpn(totalVpns, connList, epgList, isNeedMappingPolicy,
                    inventoryDao);
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testThrowCloudVpnDataMissExptForInvalidInput() {
        String resource = "";
        try {
            OverlayVpnComplexDaoUtil.throwCloudVpnDataMissExpt(resource);
            fail("Should not be here ");
        } catch(ServiceException e) {
            assertTrue(true);
        }

    }
}
