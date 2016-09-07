/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.util.check;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * EnumsUtils test class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-14
 */
public class EnumsUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testIsAdminStatusValid() {
        assertTrue(EnumsUtils.isAdminStatusValid("active"));
        assertTrue(EnumsUtils.isAdminStatusValid("inactive"));
    }

    @Test
    public void testIsAdminStatusValid2() {
        assertFalse(EnumsUtils.isAdminStatusValid("invalid"));
    }

    @Test
    public void testIsOperStatusValid() {
        assertTrue(EnumsUtils.isOperStatusValid("up"));
        assertTrue(EnumsUtils.isOperStatusValid("down"));
    }

    @Test
    public void testIsOperStatusValid2() {
        assertFalse(EnumsUtils.isOperStatusValid("invalid"));
    }

    @Test
    public void testIsEpgTypeValid() {
        assertTrue(EnumsUtils.isEpgTypeValid("subnet"));
        assertTrue(EnumsUtils.isEpgTypeValid("cidr"));
        assertTrue(EnumsUtils.isEpgTypeValid("network"));
        assertTrue(EnumsUtils.isEpgTypeValid("router"));
        assertTrue(EnumsUtils.isEpgTypeValid("port"));
        assertTrue(EnumsUtils.isEpgTypeValid("port-and-vlan"));
        assertTrue(EnumsUtils.isEpgTypeValid("vni"));
        assertTrue(EnumsUtils.isEpgTypeValid("vlan"));
    }

    @Test
    public void testIsEpgTypeValid2() {
        assertFalse(EnumsUtils.isEpgTypeValid("invalid"));
    }

    @Test
    public void testIsTopologyValid() {
        assertTrue(EnumsUtils.isTopologyValid("point_to_point"));
        assertTrue(EnumsUtils.isTopologyValid("full_mesh"));
        assertTrue(EnumsUtils.isTopologyValid("hub_spoke"));
    }

    @Test
    public void testIsTopologyValid2() {
        assertFalse(EnumsUtils.isTopologyValid("invalid"));
    }

    @Test
    public void testIsTopologyRoleValid() {
        assertTrue(EnumsUtils.isTopologyRoleValid("none"));
        assertTrue(EnumsUtils.isTopologyRoleValid("hub"));
        assertTrue(EnumsUtils.isTopologyRoleValid("spoke"));
    }

    @Test
    public void testIsTopologyRoleValid2() {
        assertFalse(EnumsUtils.isTopologyRoleValid("invalid"));
    }

    @Test
    public void testIsAuthModeValid() {
        assertTrue(EnumsUtils.isAuthModeValid("psk"));
    }

    @Test
    public void testIsAuthModeValid2() {
        assertFalse(EnumsUtils.isAuthModeValid("invalid"));
    }

    @Test
    public void testIsRouteModevalid() {
        assertTrue(EnumsUtils.isRouteModevalid("static"));
    }

    @Test
    public void testIsRouteModevalid2() {
        assertFalse(EnumsUtils.isRouteModevalid("invalid"));
    }

    @Test
    public void testIsBooleanStrValid() {
        assertTrue(EnumsUtils.isBooleanStrValid("true"));
        assertTrue(EnumsUtils.isBooleanStrValid("false"));
    }

    @Test
    public void testIsBooleanStrValid2() {
        assertFalse(EnumsUtils.isBooleanStrValid("invalid"));
    }
}
