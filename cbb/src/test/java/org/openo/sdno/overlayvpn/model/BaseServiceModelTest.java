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

package org.openo.sdno.overlayvpn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.model.ipsec.SecurityPolicy;
import org.openo.sdno.overlayvpn.model.netmodel.ipsec.DcGwIpSecConnection;
import org.openo.sdno.overlayvpn.model.netmodel.ipsec.IpSecDpd;
import org.openo.sdno.overlayvpn.model.netmodel.ipsec.NeIpSecConnection;
import org.openo.sdno.overlayvpn.model.netmodel.localsite.AdapterDeviceReplaceInfo;
import org.openo.sdno.overlayvpn.model.netmodel.localsite.DhcpConfig;
import org.openo.sdno.overlayvpn.model.netmodel.localsite.DnsServerConfig;
import org.openo.sdno.overlayvpn.model.netmodel.localsite.TimeConfig;
import org.openo.sdno.overlayvpn.model.v2.cpe.SbiDeviceCreateBasicInfo;
import org.openo.sdno.overlayvpn.model.v2.cpe.SbiDeviceInfo;

public class BaseServiceModelTest {

    @Test
    public void testNvString() {

        NvString nvStrDefault = new NvString();
        assertNull(nvStrDefault.getUuid());
        assertNull(nvStrDefault.getName());
        assertNull(nvStrDefault.getValue());
        assertNull(nvStrDefault.getFirstParentUuid());

        NvString nvStrOverloaded1 = new NvString("tempName", "tempValue");
        assertEquals("tempName", nvStrOverloaded1.getName());
        assertEquals("tempValue", nvStrOverloaded1.getValue());

        NvString nvStrOverloaded2 = new NvString("tempName", "tempValue", "parentUuid");
        assertEquals("tempName", nvStrOverloaded2.getName());
        assertEquals("tempValue", nvStrOverloaded2.getValue());
        assertEquals("parentUuid", nvStrOverloaded2.getFirstParentUuid());

        NvString nvStrOverloaded3 = new NvString("tempUuid", "tempName", "tempValue", "parentUuid");
        assertEquals("tempUuid", nvStrOverloaded3.getUuid());
        assertEquals("tempName", nvStrOverloaded3.getName());
        assertEquals("tempValue", nvStrOverloaded3.getValue());
        assertEquals("parentUuid", nvStrOverloaded3.getFirstParentUuid());

        NvString nvStrOverloaded5 = new NvString();
        nvStrOverloaded5.setFirstParentUuid("firstParentUuid");
        nvStrOverloaded5.setName("name");
        nvStrOverloaded5.setUuid("uuid");
        nvStrOverloaded5.setValue("value");

        NvString clonedNv = (NvString)nvStrOverloaded5.clone();

        Map<NvString, String> map = new HashMap<NvString, String>();
        map.put(nvStrOverloaded5, "test");
        map.put(clonedNv, "test2");

        assertTrue(map.size() == 1);

    }

    @Test
    public void testPortAndVlanUsed() {
        PortAndVlanUsed model = new PortAndVlanUsed();
        model.setEpdId("epdId");
        model.setNeId("neId");
        model.setPortId("portId");
        model.setType("type");
        model.setUuid("uuid");
        model.setVlan("vlan");

        assertEquals("epdId", model.getEpdId());
        assertEquals("neId", model.getNeId());
        assertEquals("portId", model.getPortId());
        assertEquals("type", model.getType());
        assertEquals("uuid", model.getUuid());
        assertEquals("vlan", model.getVlan());

    }

    @Test
    public void testIkePolicy() {
        IkePolicy model = new IkePolicy();
        model.setIkeVersion("v1");

        IkePolicy modelCopy = new IkePolicy();
        modelCopy.copyBasicData(model);

        assertEquals("v1", modelCopy.getIkeVersion());
    }

    @Test
    public void testIpSecPolicy() {
        IpSecPolicy policy = new IpSecPolicy();
        policy.setTransformProtocol("ah-esp");
        policy.setEncapsulationMode("tunnel");

        IpSecPolicy newPolicy = new IpSecPolicy();
        newPolicy.copyBasicData(policy);

        assertEquals("ah-esp", newPolicy.getTransformProtocol());
        assertEquals("tunnel", newPolicy.getEncapsulationMode());
    }

    @Test
    public void testSecurityPolicy() {

        SecurityPolicy policy = new SecurityPolicy();
        policy.setAuthAlgorithm("sha1");
        policy.setEncryptionAlgorithm("aes-192");
        policy.setPfs("Group2");
        policy.setLifeTime("3600");

        SecurityPolicy newPolicy = new SecurityPolicy();
        newPolicy.copyBasicData(policy);

        assertEquals("sha1", newPolicy.getAuthAlgorithm());
        assertEquals("aes-192", newPolicy.getEncryptionAlgorithm());
        assertEquals("Group2", newPolicy.getPfs());
        assertEquals("3600", newPolicy.getLifeTime());
    }

    @Test
    public void testDcGwIpSecConnection() {
        DcGwIpSecConnection model = new DcGwIpSecConnection();

        model.setSourceAddress("1.1.1.1");
        assertEquals("1.1.1.1", model.getSourceAddress());

        model.setTopoRole("topo");
        assertEquals("topo", model.getTopoRole());
        model.setPeerAddress("1.1.1.1");
        assertEquals("1.1.1.1", model.getPeerAddress());

        model.setAuthMode("PSK");
        assertEquals("PSK", model.getAuthMode());

        model.setPsk("100");
        assertEquals("100", model.getPsk());

        model.setIkePolicyId("123");
        assertEquals("123", model.getIkePolicyId());

        model.setIpsecPolicyId("1234");
        assertEquals("1234", model.getIpsecPolicyId());

        IkePolicy ike = new IkePolicy();
        ike.setUuid("test123");
        model.setIkePolicy(ike);
        assertEquals("test123", model.getIkePolicy().getUuid());

        IpSecPolicy ipsec = new IpSecPolicy();
        ipsec.setUuid("test123");
        model.setIpSecPolicy(ipsec);
        assertEquals("test123", model.getIpSecPolicy().getUuid());

        model.setSubnetId("24");
        assertEquals("24", model.getSubnetId());

        model.setRouterId("1234");
        assertEquals("1234", model.getRouterId());

        model.setPeerSubnetCidrs("test123");
        assertEquals("test123", model.getPeerSubnetCidrs());

        model.setVpcId("1234");
        assertEquals("1234", model.getVpcId());
    }

    @Test
    public void testIpSecDpd() {
        IpSecDpd dpd = new IpSecDpd();
        dpd.setAction("clear");
        dpd.setInterval("0");
        dpd.setTimeout("0");
        dpd.setUuid("12345");

        assertEquals("clear", dpd.getAction());
        assertEquals("0", dpd.getInterval());
        assertEquals("0", dpd.getTimeout());
        assertEquals("12345", dpd.getUuid());
    }

    @Test
    public void testNeIpSecConnection() {
        NeIpSecConnection con = new NeIpSecConnection();

        con.setNeId("1");
        assertEquals("1", con.getNeId());

        con.setSoureIfName("name");
        assertEquals("name", con.getSoureIfName());

        con.setSourceAddress("1.1.1.1");
        assertEquals("1.1.1.1", con.getSourceAddress());

        con.setTopoRole("hub");
        assertEquals("hub", con.getTopoRole());

        con.setPeerAddress("1.1.1.1");
        assertEquals("1.1.1.1", con.getPeerAddress());

        con.setPeerNeId("1");
        assertEquals("1", con.getPeerNeId());

        con.setAuthMode("PSK");
        assertEquals("PSK", con.getAuthMode());

        con.setPsk("test");
        assertEquals("test", con.getPsk());

        con.setIkePolicyId("123");
        assertEquals("123", con.getIkePolicyId());

        con.setIpsecPolicyId("123");
        assertEquals("123", con.getIpsecPolicyId());

        IkePolicy ike = new IkePolicy();
        ike.setUuid("test123");
        con.setIkePolicy(ike);
        assertEquals("test123", con.getIkePolicy().getUuid());

        IpSecPolicy ipsec = new IpSecPolicy();
        ipsec.setUuid("test123");
        con.setIpSecPolicy(ipsec);
        assertEquals("test123", con.getIpSecPolicy().getUuid());
    }

    // TODO: issue in equals method.
    @Test(expected = Exception.class)
    public void AdapterDeviceCreateBasicInfo() {

        SbiDeviceCreateBasicInfo info = new SbiDeviceCreateBasicInfo();

        info.setName("name123");
        assertEquals("name123", info.getName());

        info.setEsn("esn123");
        assertEquals("esn123", info.getEsn());

        info.setOrgnizationName("org");
        assertEquals("org", info.getOrgnizationName());

        info.setDescription("desc");
        assertEquals("desc", info.getDescription());

        SbiDeviceCreateBasicInfo infoNew = new SbiDeviceCreateBasicInfo();

        infoNew.setName("name123");
        infoNew.setEsn("esn123");
        infoNew.setOrgnizationName("org");
        infoNew.setDescription("desc");
        assertFalse(info.equals(infoNew));

        Map<SbiDeviceCreateBasicInfo, String> map = new HashMap<SbiDeviceCreateBasicInfo, String>();
        map.put(info, "one");
        map.put(infoNew, "two");

        assertTrue(map.size() == 1);

    }

    @Test
    public void testAdapterDeviceInfo() {
        SbiDeviceInfo info = new SbiDeviceInfo();

        info.setId("1234");
        assertEquals("1234", info.getId());

        info.setShowTenant("1234");
        assertEquals("1234", info.getShowTenant());

        info.setServiceIp("1.1.1.1");
        assertEquals("1.1.1.1", info.getServiceIp());

        info.setNeType("NeType");
        assertEquals("NeType", info.getNeType());
        info.setVersion("1.1");
        assertEquals("1.1", info.getVersion());
        info.setStatus("status");
        assertEquals("status", info.getStatus());
        info.setGisLon(10.6);
        assertEquals(new Double(10.6), info.getGisLon());
        info.setGisLat(100.9);
        assertEquals(new Double(100.9), info.getGisLat());
        info.setVendor("htipl");
        assertEquals("htipl", info.getVendor());
        info.setTenantId("10");
        assertEquals("10", info.getTenantId());
        info.setTenantName("tenantName");
        assertEquals("tenantName", info.getTenantName());
        info.setOrgnizationId("Huawei");
        assertEquals("Huawei", info.getOrgnizationId());
        info.setCreator("creator");
        assertEquals("creator", info.getCreator());
        info.setCreateTime("11:00:30");
        assertEquals("11:00:30", info.getCreateTime());
        info.setRegisterTime("11:01:01");
        assertEquals("11:01:01", info.getRegisterTime());
        info.setModifier("modifier");
        assertEquals("modifier", info.getModifier());
        info.setModifyTime("11:02:02");
        assertEquals("11:02:02", info.getModifyTime());
    }

    @Test
    public void testAdapterDeviceReplaceInfo() {
        AdapterDeviceReplaceInfo info = new AdapterDeviceReplaceInfo();

        info.setOriginalDeviceId("12");
        assertEquals("12", info.getOriginalDeviceId());

        info.setReplaceInitialFile(true);
        boolean replaceInitialFile = info.isReplaceInitialFile();
        assertTrue(replaceInitialFile);
    }

    @Test
    public void testDhcpConfig() {
        DhcpConfig dhcpConf = new DhcpConfig();

        dhcpConf.setDhcpMode("sever");
        assertEquals("sever", dhcpConf.getDhcpMode());

        dhcpConf.setMasterWins("setMasterWins");
        assertEquals("setMasterWins", dhcpConf.getMasterWins());

        dhcpConf.setSlaveWins("setSlaveWins");
        assertEquals("setSlaveWins", dhcpConf.getSlaveWins());

        DnsServerConfig dnsConfig = new DnsServerConfig();
        dnsConfig.setDnsServerMode("custom");
        dhcpConf.setDnsConfig(dnsConfig);
        assertEquals("custom", dhcpConf.getDnsConfig().getDnsServerMode());

        TimeConfig timeConfig = new TimeConfig();
        timeConfig.setUnlimit(true);
        dhcpConf.setTimeConfig(timeConfig);
        boolean unlimit = dhcpConf.getTimeConfig().getUnlimit();
        assertTrue(unlimit);

        /*
         * List<ExcludedAddress> exAddr = new ArrayList<ExcludedAddress>();
         * ExcludedAddress eAddObj = new ExcludedAddress();
         * eAddObj.setStartIpAddress("1.1.1.1");
         * exAddr.add(eAddObj);
         * dhcpConf.setExcludedAddresses(exAddr);
         * List<ExcludedAddress> exAddr1 = new ArrayList<ExcludedAddress>();
         * ExcludedAddress eAddObj1 = new ExcludedAddress();
         * exAddr1.getClass
         * assertEquals("1.1.1.1",dhcpConf.getExcludedAddresses().get(index));
         * dhcpConf.setDhcpMode("sever");
         * assertEquals("sever",dhcpConf.getDhcpMode());
         * dhcpConf.setDhcpMode("sever");
         * assertEquals("sever",dhcpConf.getDhcpMode());
         * dhcpConf.setDhcpMode("sever");
         * assertEquals("sever",dhcpConf.getDhcpMode());
         * dhcpConf.setDhcpMode("sever");
         * assertEquals("sever",dhcpConf.getDhcpMode());
         * dhcpConf.setDhcpMode("sever");
         * assertEquals("sever",dhcpConf.getDhcpMode());
         */

    }
}
