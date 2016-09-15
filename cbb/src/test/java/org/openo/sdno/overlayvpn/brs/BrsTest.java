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

package org.openo.sdno.overlayvpn.brs;

import java.util.HashMap;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.brs.enums.SiteType;
import org.openo.sdno.overlayvpn.brs.invdao.CommParamDao;
import org.openo.sdno.overlayvpn.brs.invdao.ControllerDao;
import org.openo.sdno.overlayvpn.brs.invdao.LogicalTernminationPointInvDao;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.invdao.SiteInvDao;
import org.openo.sdno.overlayvpn.brs.model.AuthInfo;
import org.openo.sdno.overlayvpn.brs.model.CommParamMO;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;
import org.openo.sdno.overlayvpn.brs.model.LogicalTernminationPointMO;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.brs.model.SiteMO;

import junit.framework.TestCase;

public class BrsTest extends TestCase {

    LogicalTernminationPointInvDao locTermPt = new LogicalTernminationPointInvDao();

    NetworkElementInvDao netDao = new NetworkElementInvDao();

    SiteInvDao siteInvDao = new SiteInvDao();

    AuthInfo auth = new AuthInfo();

    CommParamMO commP = new CommParamMO();

    ControllerMO cntrl = new ControllerMO();

    LogicalTernminationPointMO logTerm = new LogicalTernminationPointMO();

    NetworkElementMO netMo = new NetworkElementMO();

    SiteMO siteMo = new SiteMO();

    public void testBrsSiteTypeNetworkAndTenant() {
        SiteType siteNet = SiteType.NETWORK_SITE;
        SiteType siteTenant = SiteType.TENANT_SITE;

        assertEquals("network_site", siteNet.getName());
        assertEquals("tenant_site", siteTenant.getName());

    }

    public void testBrsControllerEmptyCntrlIDInventoryDao() {
        ControllerDao cntrlDao = new ControllerDao();
        try {
            cntrlDao.getController("");
            fail("No exception for invalid controller id");
        } catch(ServiceException ex) {
            assertTrue(true);
        }

        try {
            cntrlDao.getControllerByNeId("neid");
        } catch(ServiceException e) {
            assertTrue(true);
        }
        try {
            cntrlDao.getControllerBySiteId("neid");
        } catch(ServiceException e) {
            assertTrue(true);
        }

    }

    public void testBrsControllerInvalidCntrlIdInventoryDao() {
        ControllerDao cntrlDao = new ControllerDao();
        try {
            cntrlDao.getController("0");
            fail("No exception for invalid controller id");
        } catch(ServiceException ex) {
            assertTrue(true);
        }
    }

    public void testBrsControllerCommonInventoryDao() {
        CommParamDao commDao = new CommParamDao();

        try {
            commDao.getCommParam("0");
            fail("No exception for invalid controller id");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    public void testLogicalTesrminationPnt() {
        LogicalTernminationPointInvDao cntrlDao = new LogicalTernminationPointInvDao();
        LogicalTernminationPointMO curMO = new LogicalTernminationPointMO();
        try {
            cntrlDao.addMO(curMO);
            fail("No validation Exception thrown for missing mandatory fields");
        } catch(ServiceException e) {
            assertTrue(true);
        }

        try {
            cntrlDao.deleteMO("uuid");
            fail("No exception thrown for invalid id");
        } catch(ServiceException e) {
            assertTrue(true);
        }
        try {
            assertTrue(cntrlDao.getAllMO().size() == 0);
        } catch(ServiceException e) {
            assertTrue(true);
        }
        try {
            Map inp = new HashMap<String, String>();
            inp.put("contrl", "value");
            assertTrue(cntrlDao.query(inp).size() == 0);
        } catch(ServiceException e) {
            assertTrue(true);
        }

        try {
            cntrlDao.query("inp");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    public void testLogicalTestNetworkelem() {
        NetworkElementInvDao cntrlDao = new NetworkElementInvDao();
        NetworkElementMO curMO = new NetworkElementMO();
        try {

            cntrlDao.addMO(curMO);
            fail("No validation Exception thrown for missing mandatory fields");
        } catch(ServiceException e) {
            assertTrue(true);
        }

        try {
            cntrlDao.deleteMO("uuid");
            fail("No exception thrown for invalid id");
        } catch(ServiceException e) {
            assertTrue(true);
        }
        try {
            assertTrue(cntrlDao.getAllMO().size() == 0);
        } catch(ServiceException e) {
            assertTrue(true);
        }
        try {
            Map inp = new HashMap<String, String>();
            inp.put("contrl", "value");
            assertTrue(cntrlDao.query(inp).size() == 0);
        } catch(ServiceException e) {
            assertTrue(true);
        }

        try {
            cntrlDao.query("inp");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    public void testSiteInvDao() {
        SiteInvDao cntrlDao = new SiteInvDao();

        SiteMO curMO = new SiteMO();
        try {

            cntrlDao.addMO(curMO);
            fail("No validation Exception thrown for missing mandatory fields");
        } catch(ServiceException e) {
            assertTrue(true);
        }

        try {
            cntrlDao.deleteMO("uuid");
            fail("No exception thrown for invalid id");
        } catch(ServiceException e) {
            assertTrue(true);
        }
        try {
            assertTrue(cntrlDao.getAllMO().size() == 0);
        } catch(ServiceException e) {
            assertTrue(true);
        }
        try {
            Map inp = new HashMap<String, String>();
            inp.put("contrl", "value");
            assertTrue(cntrlDao.query(inp).size() == 0);
        } catch(ServiceException e) {
            assertTrue(true);
        }

        try {
            cntrlDao.query("inp");
        } catch(ServiceException e) {
            assertTrue(true);
        }
        try {
            cntrlDao.getSiteByName("inp");
        } catch(ServiceException e) {
            assertTrue(true);
        }
        try {
            cntrlDao.getSiteByTenantId("tenantId");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }
}
