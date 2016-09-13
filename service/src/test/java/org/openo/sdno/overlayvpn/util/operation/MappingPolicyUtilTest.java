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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.BaseMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.GreMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;

public class MappingPolicyUtilTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testBuildQueryFilterMap() {

        try {
            OverlayVpn overlayVpn = new OverlayVpn();
            VxlanMappingPolicy vxlanMappingPolicy = new VxlanMappingPolicy();
            Map<String, List<String>> result = MappingPolicyUtil.buildQueryFilterMap(overlayVpn.getTenantId(), "name",
                    "description", vxlanMappingPolicy.getType());
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Not yet implemented");
        }
    }

    @Test(expected = ServiceException.class)
    public void testBuildQueryFilterMap_nameInvalid() throws ServiceException {

        OverlayVpn overlayVpn = new OverlayVpn();
        VxlanMappingPolicy vxlanMappingPolicy = new VxlanMappingPolicy();
        Map<String, List<String>> result = MappingPolicyUtil.buildQueryFilterMap(overlayVpn.getTenantId(),
                "name1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
                "description", vxlanMappingPolicy.getType());
        fail("Not yet implemented");
    }

    @Test(expected = ServiceException.class)
    public void testBuildQueryFilterMap_descriptionInvalid() throws ServiceException {

        OverlayVpn overlayVpn = new OverlayVpn();
        VxlanMappingPolicy vxlanMappingPolicy = new VxlanMappingPolicy();
        Map<String, List<String>> result = MappingPolicyUtil.buildQueryFilterMap(overlayVpn.getTenantId(), "name",
                "name111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111114444444444444444444111111114444444444444444444444444444444444444444444444444444444",
                vxlanMappingPolicy.getType());
        fail("Not yet implemented");
    }

    @Test(expected = ServiceException.class)
    public void testBuildQueryFilterMap_typeInvalid() throws ServiceException {

        OverlayVpn overlayVpn = new OverlayVpn();
        VxlanMappingPolicy vxlanMappingPolicy = new VxlanMappingPolicy();
        Map<String, List<String>> result =
                MappingPolicyUtil.buildQueryFilterMap(overlayVpn.getTenantId(), "name", "description", "xyz");
        fail("Not yet implemented");
    }

    @Test(expected = ServiceException.class)
    public void testMergeMappingPolicy() throws ServiceException {

        GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
        oldMappingPolicy.setType("gre");

        BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                "{\"name\":\"name\",\"description\":\"description\"}");
        fail("Not yet implemented");
    }

    @Test(expected = ServiceException.class)
    public void testMergeMappingPolicy_nameInvalid() throws ServiceException {

        GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
        oldMappingPolicy.setType("gre");

        BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                "{\"name\":\"1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111\",\"description\":\"description\"}");
        fail("Not yet implemented");
    }

    @Test(expected = ServiceException.class)
    public void testMergeMappingPolicy_descriptionInvalid() throws ServiceException {

        GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
        oldMappingPolicy.setType("gre");

        BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                "{\"name\":\"name\",\"description\":\"11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111\"}");
        fail("Not yet implemented");
    }

    @Test(expected = ServiceException.class)
    public void testMergeMappingPolicy_typeInvalid() throws ServiceException {

        GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
        oldMappingPolicy.setType("xyz");

        BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                "{\"name\":\"name\",\"description\":\"description\"}");
        fail("Not yet implemented");
    }

    @Test
    public void testMergeMappingPolicyGreTunnelKey1() {

        try {
            GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
            oldMappingPolicy.setType("gre");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"greTunnelKey\":\"greTunnelKey\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void testMappingPolicyGreTunnelKey2() {

        try {
            GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
            oldMappingPolicy.setType("gre");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"greTunnelKey\":\"234565\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void testMappingPolicyGreTunnelKey2_negative() {

        try {
            GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
            oldMappingPolicy.setType("gre");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"greTunnelKey\":\"-23456\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }

    }

    @Test
    public void testMappingPolicyGreTunnelKey2_max() {

        try {
            GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
            oldMappingPolicy.setType("gre");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"greTunnelKey\":\"4294967295L\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }
    }

    @Test(expected = ServiceException.class)
    public void testMappingPolicyPsk() throws ServiceException {

        GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
        oldMappingPolicy.setType("gre");

        BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                "{\"name\":\"name\",\"description\":\"description\",\"psk\":\"256\"}");
        fail("Not yet implemented");
    }

    @Test
    public void testMappingPolicyAuth() {

        try {
            GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
            oldMappingPolicy.setType("gre");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"authMode\":\"\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }

    }

    @Test
    public void testMappingPolicyAuth_valid() {

        try {
            GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
            oldMappingPolicy.setType("gre");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"authMode\":\"psk\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }

    }

    @Test
    public void testMappingPolicyAuth_max() {

        try {
            GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
            oldMappingPolicy.setType("gre");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"authMode\":\"pskdhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhbdjsbjdbskkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }

    }

    @Test
    public void testMappingPolicyAuth_invalid() {

        try {
            GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
            oldMappingPolicy.setType("gre");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"authMode\":\"jsk\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }

    }

    @Test
    public void testMergeMappingPolicyFailed() {

        try {
            GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
            oldMappingPolicy.setType("gre");
            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy, "");
            assertTrue(true);
        } catch(ServiceException e) {
        }

    }

    @Test
    public void testMergeMappingPolicy1() {

        try {
            IpsecMappingPolicy oldMappingPolicy = new IpsecMappingPolicy();
            oldMappingPolicy.setType("ipsec");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\"}");
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Not yet implemented");
        }

    }

    @Test
    public void testMappingPolicyIpSec() {

        try {
            IpsecMappingPolicy oldMappingPolicy = new IpsecMappingPolicy();
            oldMappingPolicy.setType("ipsec");

            BaseMappingPolicy result =
                    MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy, "{\"routeMode\":\"wrongValue\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }

    }

    @Test
    public void testMappingPolicyIpSec_positive() {

        try {
            IpsecMappingPolicy oldMappingPolicy = new IpsecMappingPolicy();
            oldMappingPolicy.setType("ipsec");

            BaseMappingPolicy result =
                    MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy, "{\"routeMode\":\"static\"}");
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Not yet implemented");
        }

    }

    @Test
    public void testMappingPolicyIpSecAuthMode() {

        try {
            IpsecMappingPolicy oldMappingPolicy = new IpsecMappingPolicy();
            oldMappingPolicy.setType("ipsec");

            BaseMappingPolicy result =
                    MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy, "{\"authMode\":\"NotValid\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }

    }

    @Test
    public void testMappingPolicyIpSecAuthMode_positive() {

        try {
            IpsecMappingPolicy oldMappingPolicy = new IpsecMappingPolicy();
            oldMappingPolicy.setType("ipsec");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy, "{\"authMode\":\"psk\"}");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Not yet implemented");
        }

    }

    @Test
    public void testMappingPolicyIpSecPsk() {

        try {
            IpsecMappingPolicy oldMappingPolicy = new IpsecMappingPolicy();
            oldMappingPolicy.setType("ipsec");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy, "{\"psk\":\"3567\"}");
            assertTrue(result != null);
        } catch(ServiceException e) {
            fail("Not yet implemented");
        }

    }

    @Test
    public void testMappingPolicyIpSecPsk_invalid() {

        try {
            IpsecMappingPolicy oldMappingPolicy = new IpsecMappingPolicy();
            oldMappingPolicy.setType("ipsec");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"psk\":\"35673333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }

    }

    @Test
    public void testMergeMappingPolicy2() {

        try {
            VxlanMappingPolicy oldMappingPolicy = new VxlanMappingPolicy();
            oldMappingPolicy.setType("vxlan");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\"}");
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Not yet implemented");
        }

    }

    @Test
    public void testMergeMappingPolicyArpProxy() {

        try {
            VxlanMappingPolicy oldMappingPolicy = new VxlanMappingPolicy();
            oldMappingPolicy.setType("vxlan");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"arpProxy\":\"true\",\"arpBroadcastSuppress\":\"true\"}");
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Not yet implemented");
        }

    }

    @Test
    public void testMergeMappingPolicyArpProxyWrong() {

        try {
            VxlanMappingPolicy oldMappingPolicy = new VxlanMappingPolicy();
            oldMappingPolicy.setType("vxlan");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"arpProxy\":\"arpProxyWrongValue\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }
    }

    @Test
    public void testMergeMappingPolicyArpBroadcastWrong() {

        try {
            VxlanMappingPolicy oldMappingPolicy = new VxlanMappingPolicy();
            oldMappingPolicy.setType("vxlan");

            BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                    "{\"name\":\"name\",\"description\":\"description\",\"arpBroadcastSuppress\":\"wrongValue\"}");
            assertTrue(true);
        } catch(ServiceException e) {
        }
    }

    @Test(expected = ServiceException.class)
    public void testMergeMappingPolicy3() throws ServiceException {

        GreMappingPolicy oldMappingPolicy = new GreMappingPolicy();
        oldMappingPolicy.setType("gre_over_ipsec");

        BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                "{\"name\":\"name\",\"description\":\"description\"}");
        fail("Not yet implemented");
    }

    @Test(expected = ServiceException.class)
    public void testMergeMappingPolicy4() throws ServiceException {

        VxlanMappingPolicy oldMappingPolicy = new VxlanMappingPolicy();
        oldMappingPolicy.setType("vxlan_over_ipsec");

        BaseMappingPolicy result = MappingPolicyUtil.mergeMappingPolicy(oldMappingPolicy,
                "{\"name\":\"name\",\"description\":\"description\"}");
        fail("Not yet implemented");
    }
}
