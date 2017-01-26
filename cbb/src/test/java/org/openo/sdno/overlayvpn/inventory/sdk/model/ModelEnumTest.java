/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.inventory.sdk.model;

import org.junit.Test;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.ArpSuppressMode;
import org.openo.sdno.overlayvpn.model.common.enums.BoolStatus;
import org.openo.sdno.overlayvpn.model.common.enums.EndpointType;
import org.openo.sdno.overlayvpn.model.common.enums.IPVersion;
import org.openo.sdno.overlayvpn.model.common.enums.MacLearningMode;
import org.openo.sdno.overlayvpn.model.common.enums.MappingPolicyType;
import org.openo.sdno.overlayvpn.model.common.enums.ModifyMaskType;
import org.openo.sdno.overlayvpn.model.common.enums.MplsTunnelType;
import org.openo.sdno.overlayvpn.model.common.enums.MplsVpnType;
import org.openo.sdno.overlayvpn.model.common.enums.OperStatus;
import org.openo.sdno.overlayvpn.model.common.enums.PfsType;
import org.openo.sdno.overlayvpn.model.common.enums.Phase1NegotiationModeType;
import org.openo.sdno.overlayvpn.model.common.enums.ServiceType;
import org.openo.sdno.overlayvpn.model.common.enums.SourceType;
import org.openo.sdno.overlayvpn.model.common.enums.TechnologyType;
import org.openo.sdno.overlayvpn.model.common.enums.TenantServiceTpAccessType;
import org.openo.sdno.overlayvpn.model.common.enums.TransformProtocolType;
import org.openo.sdno.overlayvpn.model.common.enums.VxlanServiceTpAccessType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.AuthAlgorithmType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.AuthModeType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.EncapsulationModeType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.EncryptionAlgorithmType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.IKEVersion;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.InitiatorType;
import org.openo.sdno.overlayvpn.model.common.enums.vxlan.VxlanAccessType;

import junit.framework.TestCase;

public class ModelEnumTest extends TestCase {

    @Test
    public void testActionStatus() {
        ActionStatus siteNet = ActionStatus.NONE;
        assertEquals("None", siteNet.getName());
        siteNet = ActionStatus.NORMAL;
        assertEquals("Normal", siteNet.getName());
        siteNet = ActionStatus.CREATING;
        assertEquals("Creating", siteNet.getName());
        siteNet = ActionStatus.DELETING;
        assertEquals("Deleting", siteNet.getName());
        siteNet = ActionStatus.UPDATING;
        assertEquals("Updating", siteNet.getName());
        siteNet = ActionStatus.CREATE_EXCEPTION;
        assertEquals("Create_Exception", siteNet.getName());
        siteNet = ActionStatus.DELETE_EXCEPTION;
        assertEquals("Delete_Exception", siteNet.getName());
        siteNet = ActionStatus.UPDATE_EXCEPTION;
        assertEquals("Update_Exception", siteNet.getName());
    }

    @Test
    public void testAdminStatus() {
        AdminStatus siteNet = AdminStatus.NONE;
        assertEquals("none", siteNet.getName());
        siteNet = AdminStatus.ACTIVE;
        assertEquals("active", siteNet.getName());
        siteNet = AdminStatus.INACTIVE;
        assertEquals("inactive", siteNet.getName());
        siteNet = AdminStatus.PARTIALLYINACTIVE;
        assertEquals("partially inactive", siteNet.getName());
    }

    @Test
    public void testArpSuppressMode() {
        ArpSuppressMode siteNet = ArpSuppressMode.NONE;
        assertEquals("none", siteNet.getName());
        siteNet = ArpSuppressMode.PROXY_REPLY;
        assertEquals("proxy_reply", siteNet.getName());
    }

    @Test
    public void testBoolStatus() {
        BoolStatus siteNet = BoolStatus.FALSE;
        assertEquals("false", siteNet.getName());
        siteNet = BoolStatus.TRUE;
        assertEquals("true", siteNet.getName());
    }

    @Test
    public void testEndPointType() {
        EndpointType type;
        type = EndpointType.SUBNET;
        assertEquals(type.getName(), "subnet");
        type = EndpointType.CIDR;
        assertEquals(type.getName(), "cidr");
        type = EndpointType.NETWORK;
        assertEquals(type.getName(), "network");
        type = EndpointType.ROUTER;
        assertEquals(type.getName(), "router");
        type = EndpointType.PORT;
        assertEquals(type.getName(), "port");
        type = EndpointType.PORT_VLAN;
        assertEquals(type.getName(), "port-and-vlan");
        type = EndpointType.VNI;
        assertEquals(type.getName(), "vni");
        type = EndpointType.VLAN;
        assertEquals(type.getName(), "vlan");
        type = EndpointType.VPC;
        assertEquals(type.getName(), "vpc");
    }

    @Test
    public void testIPVersion() {
        IPVersion type;
        type = IPVersion.IPV4;
        assertEquals(type.getName(), "ipv4");
        type = IPVersion.IPV6;
        assertEquals(type.getName(), "ipv6");
    }

    @Test
    public void testMacLearningMode() {
        MacLearningMode type;
        type = MacLearningMode.DATA_PLANE;
        assertEquals(type.getName(), "data-plane");
        type = MacLearningMode.CONTROL_PLANE;
        assertEquals(type.getName(), "control-plane");
    }

    @Test
    public void testMappingPolicyType() {
        MappingPolicyType type;
        type = MappingPolicyType.GRE;
        assertEquals(type.getName(), "gre");
        type = MappingPolicyType.IPSEC;
        assertEquals(type.getName(), "ipsec");
        type = MappingPolicyType.VXLAN;
        assertEquals(type.getName(), "vxlan");
        type = MappingPolicyType.GRE_OVER_IPSEC;
        assertEquals(type.getName(), "gre_over_ipsec");
        type = MappingPolicyType.VXLAN_OVER_IPSEC;
        assertEquals(type.getName(), "vxlan_over_ipsec");
        assertTrue(MappingPolicyType.validateName("gre"));
        assertFalse(MappingPolicyType.validateName("gr"));
    }

    @Test
    public void testModifyMaskType() {
        ModifyMaskType type;
        type = ModifyMaskType.NOMODIFY;
        assertEquals(type.getName(), "NOMODIFY");
        type = ModifyMaskType.ADD;
        assertEquals(type.getName(), "ADD");
        type = ModifyMaskType.DELETE;
        assertEquals(type.getName(), "DELETE");
        type = ModifyMaskType.MODIFY;
        assertEquals(type.getName(), "MODIFY");
        type = ModifyMaskType.MIDDLECOMPARE;
        assertEquals(type.getName(), "MIDDLECOMPARE");
        type = ModifyMaskType.DEPLOY;
        assertEquals(type.getName(), "DEPLOY");
        type = ModifyMaskType.UNDEPLOY;
        assertEquals(type.getName(), "UNDEPLOY");
    }

    @Test
    public void testMplsTunnelType() {
        MplsTunnelType type = MplsTunnelType.NONE;
        assertEquals(type.getName(), "none");
        type = MplsTunnelType.LDP_LSP;
        assertEquals(type.getName(), "ldp_lsp");
        type = MplsTunnelType.MPLS_TE;
        assertEquals(type.getName(), "mpls_te");
        type = MplsTunnelType.VXLAN;
        assertEquals(type.getName(), "VxLAN");
    }

    @Test
    public void testMplsVpnType() {
        MplsVpnType type = MplsVpnType.NONE;
        assertEquals(type.getName(), "none");
        type = MplsVpnType.BGP_MPLS_VPN;
        assertEquals(type.getName(), "bgp_mpls_vpn");
        type = MplsVpnType.VLL;
        assertEquals(type.getName(), "vll");
    }

    @Test
    public void testOperStatus() {
        OperStatus type = OperStatus.NONE;
        assertEquals(type.getName(), "none");
        type = OperStatus.UP;
        assertEquals(type.getName(), "up");
        type = OperStatus.DOWN;
        assertEquals(type.getName(), "down");
        type = OperStatus.PARTIALLYDOWN;
        assertEquals(type.getName(), "partially down");
    }

    @Test
    public void testPfsType() {
        PfsType type = PfsType.GROUP2;
        assertEquals(type.getName(), "group2");
        type = PfsType.GROUP5;
        assertEquals(type.getName(), "group5");
        type = PfsType.GROUP14;
        assertEquals(type.getName(), "group14");
        assertTrue(PfsType.validateName("group2"));
        assertFalse(PfsType.validateName("group3"));
    }

    @Test
    public void testPhase1NegotiationModeType() {
        Phase1NegotiationModeType type = Phase1NegotiationModeType.MAINMODE;
        assertEquals(type.getName(), "Main Mode");
    }

    @Test
    public void testServiceType() {
        ServiceType type = ServiceType.EVPN;
        assertEquals(type.getName(), "evpn");
        type = ServiceType.L3VPN;
        assertEquals(type.getName(), "l3vpn");
    }

    @Test
    public void testSourceType() {
        SourceType type = SourceType.NONE;
        assertEquals(type.getName(), "None");
        type = SourceType.USER;
        assertEquals(type.getName(), "User");
        type = SourceType.DISCOVERY;
        assertEquals(type.getName(), "Discovery");
    }

    @Test
    public void testTechnologyType() {
        TechnologyType type = TechnologyType.IPSEC;
        assertEquals(type.getName(), "ipsec");
        type = TechnologyType.GRE;
        assertEquals(type.getName(), "gre");
        type = TechnologyType.VXLAN;
        assertEquals(type.getName(), "vxlan");
        type = TechnologyType.GRE_OVER_IPSEC;
        assertEquals(type.getName(), "gre_over_ipsec");
        type = TechnologyType.VXLAN_OVER_IPSEC;
        assertEquals(type.getName(), "vxlan_over_ipsec");
        assertTrue(TechnologyType.validateName("ipsec"));
        assertTrue(TechnologyType.validateName("gre"));
        assertTrue(TechnologyType.validateName("vxlan"));
        assertTrue(TechnologyType.validateName("gre_over_ipsec"));
        assertTrue(TechnologyType.validateName("vxlan_over_ipsec"));
        assertFalse(TechnologyType.validateName("gre_ipsec"));
        assertFalse(TechnologyType.isVxlanRelated("gre"));
        assertTrue(TechnologyType.isVxlanRelated("vxlan"));
        assertFalse(TechnologyType.isIpsec("gre"));
        assertTrue(TechnologyType.isIpsec("ipsec"));
    }

    @Test
    public void testTenantServiceTpAccessType() {
        TenantServiceTpAccessType type = TenantServiceTpAccessType.NONE;
        assertEquals(type.getName(), "none");
        type = TenantServiceTpAccessType.VLAN;
        assertEquals(type.getName(), "vlan");
        type = TenantServiceTpAccessType.VXLAN;
        assertEquals(type.getName(), "vxlan");
        type = TenantServiceTpAccessType.IP;
        assertEquals(type.getName(), "ip");
        type = TenantServiceTpAccessType.PORT;
        assertEquals(type.getName(), "port");
    }

    @Test
    public void testTransformProtocolType() {
        TransformProtocolType type = TransformProtocolType.ESP;
        assertEquals(type.getName(), "esp");
        type = TransformProtocolType.AH;
        assertEquals(type.getName(), "ah");
        type = TransformProtocolType.AHESP;
        assertEquals(type.getName(), "ah-esp");
        assertFalse(TransformProtocolType.validateName("sha1"));
        assertTrue(TransformProtocolType.validateName("esp"));
    }

    @Test
    public void testVxlanServiceTpAccessType() {
        VxlanServiceTpAccessType type = VxlanServiceTpAccessType.UNTAG;
        assertEquals(type.getName(), "untag");
        type = VxlanServiceTpAccessType.DOT1Q;
        assertEquals(type.getName(), "dot1q");
        type = VxlanServiceTpAccessType.QINQ;
        assertEquals(type.getName(), "qinq");
        type = VxlanServiceTpAccessType.TRANSPORT;
        assertEquals(type.getName(), "transport");
        type = VxlanServiceTpAccessType.GLOBAL_VLAN;
        assertEquals(type.getName(), "global-vlan");
        type = VxlanServiceTpAccessType.VXLAN;
        assertEquals(type.getName(), "vxlan");
    }

    @Test
    public void testAuthAlgorithmType() {
        AuthAlgorithmType type = AuthAlgorithmType.SHA1;
        assertEquals(type.getName(), "sha1");
        assertTrue(AuthAlgorithmType.validateName("sha1"));
    }

    @Test
    public void testAuthModeType() {
        AuthModeType type = AuthModeType.PSK;
        assertEquals(type.getName(), "PSK");
        type = AuthModeType.CERTIFICATE;
        assertEquals(type.getName(), "certificate");
    }

    @Test
    public void testEncapsulationModeType() {
        EncapsulationModeType type = EncapsulationModeType.TUNNEL;
        assertEquals(type.getName(), "tunnel");
        type = EncapsulationModeType.TRANSPORT;
        assertEquals(type.getName(), "transport");
        assertTrue(EncapsulationModeType.validateName("tunnel"));
        assertFalse(EncapsulationModeType.validateName("train"));
    }

    @Test
    public void testEncryptionAlgorithmType() {
        EncryptionAlgorithmType type = EncryptionAlgorithmType.DES;
        assertEquals(type.getName(), "3des");
        type = EncryptionAlgorithmType.AES128;
        assertEquals(type.getName(), "aes-128");
        type = EncryptionAlgorithmType.AES256;
        assertEquals(type.getName(), "aes-256");
        type = EncryptionAlgorithmType.AES192;
        assertEquals(type.getName(), "aes-192");
    }

    @Test
    public void testInitiatorType() {
        InitiatorType type = InitiatorType.BIDIRECTIONAL;
        assertEquals(type.getName(), "bi-directional");
        type = InitiatorType.RESPONSEONLY;
        assertEquals(type.getName(), "response-only");
    }

    @Test
    public void testIKEVersion() {
        IKEVersion type = IKEVersion.V1;
        assertEquals(type.getName(), "v1");
        type = IKEVersion.V2;
        assertEquals(type.getName(), "v2");
        assertTrue(IKEVersion.validateName("v1"));
        assertTrue(IKEVersion.validateName("v2"));
        assertFalse(IKEVersion.validateName("v3"));
    }

    @Test
    public void testVxlanAccessType() {
        VxlanAccessType type = VxlanAccessType.PORT;
        assertEquals(type.getName(), "port");
        type = VxlanAccessType.DOT1Q;
        assertEquals(type.getName(), "dot1q");
    }
}
