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

package org.openo.sdno.overlayvpn.inventory.sdk.util;

import static org.junit.Assert.assertEquals;

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

public class ModelEnumTest {

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
        assertEquals("Create_Excepion", siteNet.getName());
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
}
