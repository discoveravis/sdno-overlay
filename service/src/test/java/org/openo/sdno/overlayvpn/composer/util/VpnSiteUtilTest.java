/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.composer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.servicemodel.base.IP;
import org.openo.sdno.overlayvpn.servicemodel.base.InternetGateway;
import org.openo.sdno.overlayvpn.servicemodel.base.Vpc;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSubnet;

public class VpnSiteUtilTest {

    @Test
    public void testGetSiteCidrsVpc() {
        Vpc vpc = new Vpc();
        assertTrue(CollectionUtils.isEmpty(VpnSiteUtil.getSiteCidrs(vpc)));
    }

    @Test
    public void testGetSiteCidrs1() {
        NeConnection connection = new NeConnection();
        VpnSite site = new VpnSite();

        InternetGateway internetGateway = new InternetGateway();
        List<String> sourceSubnets = new ArrayList<>();
        sourceSubnets.add("0.0.0.0");
        internetGateway.setSourceSubnets(sourceSubnets);

        site.setInternetGateway(internetGateway);

        assertTrue(CollectionUtils.isEmpty(VpnSiteUtil.getSiteCidrs(connection, site)));
    }

    @Test
    public void testGetSiteCidrs2() {
        NeConnection connection = new NeConnection();
        VpnSite site = new VpnSite();

        InternetGateway internetGateway = new InternetGateway();
        List<String> sourceSubnets = new ArrayList<>();
        internetGateway.setSourceSubnets(sourceSubnets);

        site.setInternetGateway(internetGateway);

        assertTrue(CollectionUtils.isEmpty(VpnSiteUtil.getSiteCidrs(connection, site)));
    }

    @Test
    public void testGetSiteCidrs3() {
        NeConnection connection = new NeConnection();
        VpnSite site = new VpnSite();

        InternetGateway internetGateway = new InternetGateway();
        List<String> sourceSubnets = new ArrayList<>();
        sourceSubnets.add("0.0.0.0");
        internetGateway.setSourceSubnets(sourceSubnets);

        List<VpnSubnet> subnets = new ArrayList<>();
        VpnSubnet subnet = new VpnSubnet();
        subnet.setName("subnet");
        subnet.setId("test_uuid");
        subnets.add(subnet);

        site.setInternetGateway(internetGateway);
        site.setSubnets(subnets);

        assertTrue(CollectionUtils.isEmpty(VpnSiteUtil.getSiteCidrs(connection, site)));
    }

    @Test
    public void testGetSiteCidrs4() {
        NeConnection connection = new NeConnection();
        connection.setSrcNeRole("vpc");
        VpnSite site = new VpnSite();

        InternetGateway internetGateway = new InternetGateway();
        List<String> sourceSubnets = new ArrayList<>();
        sourceSubnets.add("0.0.0.0");
        internetGateway.setSourceSubnets(sourceSubnets);

        List<VpnSubnet> subnets = new ArrayList<>();
        VpnSubnet subnet = new VpnSubnet();
        subnet.setName("subnet");
        subnet.setId("test_uuid");
        subnets.add(subnet);

        site.setInternetGateway(internetGateway);
        site.setSubnets(subnets);

        assertTrue(VpnSiteUtil.getSiteCidrs(connection, site).get(0) == null);
    }

    @Test
    public void testGetSiteCidrs5() {
        NeConnection connection = new NeConnection();
        connection.setDestNeRole("vpc");
        VpnSite site = new VpnSite();

        InternetGateway internetGateway = new InternetGateway();
        List<String> sourceSubnets = new ArrayList<>();
        sourceSubnets.add("0.0.0.0");
        internetGateway.setSourceSubnets(sourceSubnets);

        List<VpnSubnet> subnets = new ArrayList<>();
        VpnSubnet subnet = new VpnSubnet();
        subnet.setName("subnet");
        subnet.setId("test_uuid");
        subnets.add(subnet);

        site.setInternetGateway(internetGateway);
        site.setSubnets(subnets);

        assertTrue(VpnSiteUtil.getSiteCidrs(connection, site).get(0) == null);
    }

    @Test
    public void testGetSiteCidrs6() {
        NeConnection connection = new NeConnection();
        connection.setDestNeRole("vpc");
        VpnSite site = new VpnSite();

        InternetGateway internetGateway = new InternetGateway();
        List<String> sourceSubnets = new ArrayList<>();
        sourceSubnets.add("0.0.0.0");
        internetGateway.setSourceSubnets(sourceSubnets);

        List<VpnSubnet> subnets = new ArrayList<>();
        VpnSubnet subnet = new VpnSubnet();
        subnet.setName("subnet");
        subnet.setId("test_uuid");
        subnet.setCidrBlock("0.0.0.0/24");
        subnets.add(subnet);

        site.setInternetGateway(internetGateway);
        site.setSubnets(subnets);

        IP ip = IP.buildIPv4("0.0.0.0/24");
        assertEquals(ip.getIpv4(), VpnSiteUtil.getSiteCidrs(connection, site).get(0).getIpv4());
        assertEquals(ip.getIpMask(), VpnSiteUtil.getSiteCidrs(connection, site).get(0).getIpMask());
    }

}
