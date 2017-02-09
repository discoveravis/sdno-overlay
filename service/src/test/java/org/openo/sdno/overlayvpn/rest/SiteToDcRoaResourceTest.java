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

package org.openo.sdno.overlayvpn.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.baseservice.util.RestUtils;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.esr.invdao.VimInvDao;
import org.openo.sdno.overlayvpn.esr.model.Vim;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.EndpointType;
import org.openo.sdno.overlayvpn.model.common.enums.TechnologyType;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyRole;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyType;
import org.openo.sdno.overlayvpn.model.servicechain.ServiceChainPath;
import org.openo.sdno.overlayvpn.model.servicechain.ServiceChainPathRsp;
import org.openo.sdno.overlayvpn.model.servicechain.ServiceChainSiteToDcRelation;
import org.openo.sdno.overlayvpn.model.servicechain.ServicePathHop;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.servicemodel.SfpNbi;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteNbi;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDcNbi;
import org.openo.sdno.overlayvpn.model.servicemodel.SubNet;
import org.openo.sdno.overlayvpn.model.servicemodel.SubnetNbi;
import org.openo.sdno.overlayvpn.model.servicemodel.Vpc;
import org.openo.sdno.overlayvpn.model.servicemodel.VpcNbi;
import org.openo.sdno.overlayvpn.model.servicemodel.VpcSubNetMapping;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.ctrlconnection.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext.xml",
                "classpath*:META-INF/spring/service.xml", "classpath*:spring/service.xml",
                "classpath*:spring/service-cbb.xml"})
public class SiteToDcRoaResourceTest {

    private static final String VPC_BASIC_PATH = "/openoapi/sdnovpc/v1/vpcs";

    private static final String VPC_SUBNET_BASIC_PATH = "/openoapi/sdnovpc/v1/subnets";

    private static final String SERVICE_CHAIN_BASIC_PATH = "/openoapi/sdnoservicechain/v1/paths";

    private static final String IPSEC_VPN_BASIC_PATH = "/openoapi/sdnoipsec/v1/ipsecs";

    @Mocked
    HttpServletRequest request;

    @Mocked
    HttpServletResponse response;

    @Autowired
    SiteToDcRoaResource site2DcSvc;

    @Before
    public void setUp() throws Exception {
        new MockInventoryDao();
        new MockRestfulProxy();
        new MockVimInvDao();
    }

    @Test
    public void testCreateSuccess() throws ServiceException {

        new MockNeDao();
        new MockControllerDao();

        SiteToDcNbi siteToDcNbi = buildSiteToDcNbi();
        Map<String, String> resultRsp = site2DcSvc.create(request, response, JsonUtil.toJson(siteToDcNbi));
        assertEquals(resultRsp.get("errorCode"), ErrorCode.OVERLAYVPN_SUCCESS);
    }

    @Test
    public void testUpdateSuccess() throws ServiceException {
        new MockUp<RestUtils>() {

            @Mock
            String getRequestBody(HttpServletRequest request) {

                Map<String, String> updateParamMap = new HashMap<>();
                updateParamMap.put("name", "test_name");

                return JsonUtil.toJson(updateParamMap);
            }
        };
        new MockNeDao();
        new MockControllerDao();
        Map<String, String> resultRsp = site2DcSvc.update(request, response, "test_uuid");
        assertEquals(resultRsp.get("errorCode"), ErrorCode.OVERLAYVPN_SUCCESS);
    }

    @Test
    public void testQuerySuccess() throws ServiceException {

        SiteToDcNbi resultRsp = site2DcSvc.query(request, response, "1111");
        assertTrue(resultRsp != null);
    }

    @Test
    public void testDeleteSuccess() throws ServiceException {

        Map<String, String> resultRsp = site2DcSvc.delete(request, response, "1111");
        assertEquals(resultRsp.get("errorCode"), ErrorCode.OVERLAYVPN_SUCCESS);
    }

    private final class MockInventoryDao<T> extends MockUp<InventoryDao<T>> {

        boolean isDeleteEndpointGroup = false;

        boolean isDeleteConnection = false;

        @Mock
        ResultRsp queryByFilter(Class clazz, String filter, String queryResultFields) throws ServiceException {
            if(OverlayVpn.class.equals(clazz)) {
                OverlayVpn overlayVpn = new OverlayVpn();

                overlayVpn.setUuid("000001");
                overlayVpn.setName("overlayVpn");
                overlayVpn.setAdminStatus(AdminStatus.INACTIVE.getName());

                ResultRsp<List<OverlayVpn>> resp =
                        new ResultRsp<List<OverlayVpn>>(ErrorCode.OVERLAYVPN_SUCCESS, Arrays.asList(overlayVpn));
                return resp;
            } else if(Connection.class.equals(clazz)) {
                if(isDeleteConnection == false) {
                    Connection connection = buildConnection();

                    ResultRsp<List<Connection>> resp =
                            new ResultRsp<List<Connection>>(ErrorCode.OVERLAYVPN_SUCCESS, Arrays.asList(connection));
                    return resp;
                } else {
                    ResultRsp<List<Connection>> resp = new ResultRsp<List<Connection>>(ErrorCode.OVERLAYVPN_SUCCESS);
                    return resp;
                }
            } else if(EndpointGroup.class.equals(clazz)) {
                if(isDeleteEndpointGroup == false) {
                    List<EndpointGroup> endpointGroup = buildEndpointGroup();

                    ResultRsp<List<EndpointGroup>> resp =
                            new ResultRsp<List<EndpointGroup>>(ErrorCode.OVERLAYVPN_SUCCESS, endpointGroup);
                    return resp;
                } else {
                    ResultRsp<List<EndpointGroup>> resp =
                            new ResultRsp<List<EndpointGroup>>(ErrorCode.OVERLAYVPN_SUCCESS);
                    return resp;
                }
            } else if(VpcSubNetMapping.class.equals(clazz)) {
                List<VpcSubNetMapping> vpcSubNetMapping = buildVpcSubNetMapping();

                ResultRsp<List<VpcSubNetMapping>> resp =
                        new ResultRsp<List<VpcSubNetMapping>>(ErrorCode.OVERLAYVPN_SUCCESS, vpcSubNetMapping);
                return resp;
            } else if(ServiceChainSiteToDcRelation.class.equals(clazz)) {
                List<ServiceChainSiteToDcRelation> serviceChainSiteToDcRelation = buildServiceChainSiteToDcRelation();

                ResultRsp<List<ServiceChainSiteToDcRelation>> resp = new ResultRsp<List<ServiceChainSiteToDcRelation>>(
                        ErrorCode.OVERLAYVPN_SUCCESS, serviceChainSiteToDcRelation);
                return resp;
            }

            return null;
        }

        @Mock
        ResultRsp<String> batchDelete(Class clazz, List<String> uuids) throws ServiceException {
            if(EndpointGroup.class.equals(clazz)) {
                isDeleteEndpointGroup = true;
            }
            if(Connection.class.equals(clazz)) {
                isDeleteConnection = true;
            }
            return new ResultRsp<String>();
        }

        @Mock
        public ResultRsp update(Class clazz, List oriUpdateList, String updateFieldListStr) {
            return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        @Mock
        public ResultRsp<T> insert(T data) throws ServiceException {
            if(data.getClass().isAssignableFrom(OverlayVpn.class)) {
                ResultRsp<OverlayVpn> result = new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
                result.setData(buildOverlayVpn());
                return (ResultRsp<T>)result;
            } else if(data.getClass().isAssignableFrom(Connection.class)) {
                ResultRsp<Connection> result = new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
                result.setData(buildConnection());
                return (ResultRsp<T>)result;
            }
            return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        @Mock
        public ResultRsp<List<T>> batchInsert(List<T> dataList) {
            return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
        }
    }

    private final class MockRestfulProxy extends MockUp<RestfulProxy> {

        @Mock
        RestfulResponse get(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            if(uri.startsWith(VPC_BASIC_PATH)) {
                Vpc vpc = new Vpc();
                vpc.setDescription("test_description");
                vpc.setName("name");
                response.setStatus(HttpStatus.SC_OK);
                response.setResponseJson(JsonUtil.toJson(vpc));
            }

            return response;
        }

        @Mock
        RestfulResponse post(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            if(uri.startsWith(VPC_BASIC_PATH)) {
                Vpc vpc = new Vpc();
                vpc.setDescription("test_description");
                vpc.setName("name");
                Vpc.UnderlayResources underlayResources = vpc.new UnderlayResources();
                underlayResources.setRouterId("test");
                vpc.setAttributes(underlayResources);
                response.setStatus(HttpStatus.SC_OK);
                response.setResponseJson(JsonUtil.toJson(vpc));
            } else if(uri.startsWith(VPC_SUBNET_BASIC_PATH)) {
                SubNet subNet = new SubNet();
                subNet.setDescription("test_description");
                subNet.setName("name");
                SubNet.UnderlayResources underlayResources = new SubNet.UnderlayResources();
                underlayResources.setSubnetId("test");
                subNet.setAttributes(underlayResources);
                response.setStatus(HttpStatus.SC_OK);
                response.setResponseJson(JsonUtil.toJson(subNet));
            } else if(uri.startsWith(SERVICE_CHAIN_BASIC_PATH)) {
                ServiceChainPathRsp serviceChain = new ServiceChainPathRsp();
                serviceChain.setOperationId("test");
                serviceChain.setUuid("test");
                response.setStatus(HttpStatus.SC_OK);
                response.setResponseJson(JsonUtil.toJson(serviceChain));
            } else if(uri.startsWith(IPSEC_VPN_BASIC_PATH)) {
                ResultRsp<OverlayVpn> result =
                        new ResultRsp<OverlayVpn>(ErrorCode.OVERLAYVPN_SUCCESS, buildOverlayVpn());
                response.setStatus(HttpStatus.SC_OK);
                response.setResponseJson(JsonUtil.toJson(result));
            }
            return response;
        }

        @Mock
        RestfulResponse delete(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            ResultRsp<String> sbiRsp = new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS);
            response.setStatus(HttpStatus.SC_OK);
            response.setResponseJson(JsonUtil.toJson(sbiRsp));

            return response;
        }

    }

    private class MockNeDao extends MockUp<NetworkElementInvDao> {

        @Mock
        public NetworkElementMO query(String neId) throws ServiceException {
            NetworkElementMO ne = new NetworkElementMO();

            ne.setNativeID(neId + "1");
            ne.setId(neId);
            List<String> controllerIdList = new ArrayList<String>();
            controllerIdList.add("test_controllerId");
            ne.setControllerID(controllerIdList);

            return ne;
        }

        @Mock
        public List<NetworkElementMO> getNeByName(String name) throws ServiceException {
            List<NetworkElementMO> neList = new ArrayList<NetworkElementMO>();
            NetworkElementMO ne = new NetworkElementMO();

            ne.setNativeID(name + "1");
            ne.setId("uuid");
            ne.setName(name);
            neList.add(ne);
            return neList;
        }

        @Mock
        public void updateMO(NetworkElementMO curNeMO) throws ServiceException {
            return;
        }
    }

    private class MockVimInvDao extends MockUp<VimInvDao> {

        @Mock
        public Vim queryVimByName(String vimName) throws ServiceException {
            Vim vim = new Vim();
            vim.setVimId("test_vim");

            return vim;
        }
    }

    private class MockControllerDao extends MockUp<ControllerUtil> {

        @Mock
        ResultRsp<Map<String, ControllerMO>> testCtrlConnection(List<String> neUuids) throws ServiceException {
            Map<String, ControllerMO> map = new HashMap<String, ControllerMO>();
            ControllerMO controllerMO = new ControllerMO();
            controllerMO.setObjectId(neUuids.get(0) + "8");
            map.put(neUuids.get(0), controllerMO);
            return new ResultRsp<Map<String, ControllerMO>>(ErrorCode.OVERLAYVPN_SUCCESS, map);
        }
    }

    private SiteToDcNbi buildSiteToDcNbi() {

        SiteToDcNbi vpn = new SiteToDcNbi();

        vpn.setName("hw-szc");
        vpn.setDescription("create test");

        SiteNbi site = new SiteNbi();
        site.setCidr("10.12.13.14/16");
        site.setPortAndVlan("1");
        site.setThinCpeId("5271222f-8b22-47f4-8acb-abbd5337d0b0");
        site.setvCPEId("bbbbbbb");

        VpcNbi vpc = new VpcNbi();
        vpc.setName("vpc-huawei");

        SubnetNbi subnet = new SubnetNbi();
        subnet.setCidr("10.0.0.16/32");
        subnet.setName("subnet1");
        subnet.setVni(25);
        vpc.setSite(subnet);

        SfpNbi sfp = new SfpNbi();
        sfp.setScfNeId("aaaaaaaaa");

        List<ServicePathHop> servicePathHopList = new ArrayList<ServicePathHop>();
        ServicePathHop servicePathHop = new ServicePathHop();
        servicePathHop.setHopNumber(32);
        servicePathHop.setSfgId("ddddddd");
        servicePathHop.setSfiId("ccccccc");
        servicePathHopList.add(servicePathHop);
        sfp.setServicePathHops(servicePathHopList);

        return vpn;
    }

    private OverlayVpn buildOverlayVpn() {

        OverlayVpn vpn = new OverlayVpn();

        vpn.setUuid("000001");
        vpn.setName("overlayVpn");
        vpn.setAdminStatus(AdminStatus.ACTIVE.getName());

        return vpn;
    }

    private Connection buildConnection() {

        Connection connection = new Connection();

        connection.setEndpointGroups(buildEndpointGroup());

        connection.setUuid("000002");
        connection.setName("connection");
        connection.setAdminStatus(AdminStatus.INACTIVE.getName());
        connection.setTopology(TopologyType.HUB_SPOKE.getName());
        connection.setTechnology(TechnologyType.IPSEC.getName());
        connection.setCompositeVpnId("000001");

        return connection;
    }

    private List<EndpointGroup> buildEndpointGroup() {
        List<EndpointGroup> endpointGroupList = new ArrayList<>();

        EndpointGroup endpointGroupAc = new EndpointGroup();
        EndpointGroup endpointGroupDc = new EndpointGroup();

        endpointGroupAc.setUuid("000003");
        endpointGroupAc.setName("endpointGroupAc");
        endpointGroupAc.setAdminStatus(AdminStatus.INACTIVE.getName());
        endpointGroupAc.setType(EndpointType.CIDR.getName());
        endpointGroupAc.setEndpoints("[\"10.8.1.1/24\"]");
        endpointGroupAc.setTopologyRole(TopologyRole.HUB.getName());
        endpointGroupAc.setNeId("100001");
        endpointGroupAc.setConnectionId("000002");

        endpointGroupDc.setUuid("000004");
        endpointGroupDc.setName("endpointGroupDc");
        endpointGroupDc.setAdminStatus(AdminStatus.INACTIVE.getName());
        endpointGroupDc.setType(EndpointType.VPC.getName());
        endpointGroupDc.setEndpoints("[\"10.8.1.2/24|100003|100004|10.8.1.3|100005\"]");
        endpointGroupDc.setTopologyRole(TopologyRole.SPOKE.getName());
        endpointGroupDc.setNeId("100002");
        endpointGroupDc.setConnectionId("000002");

        endpointGroupList.add(endpointGroupAc);
        endpointGroupList.add(endpointGroupDc);
        return endpointGroupList;
    }

    private List<VpcSubNetMapping> buildVpcSubNetMapping() {
        List<VpcSubNetMapping> vpcSubNetMappingList = new ArrayList<VpcSubNetMapping>();
        VpcSubNetMapping vpcSubNetMapping = new VpcSubNetMapping();

        vpcSubNetMapping.setUuid("000001");
        vpcSubNetMapping.setVpcName("test");
        vpcSubNetMapping.setVpcId("test");
        vpcSubNetMapping.setSubnetId("test");
        vpcSubNetMappingList.add(vpcSubNetMapping);
        return vpcSubNetMappingList;
    }

    private List<ServiceChainSiteToDcRelation> buildServiceChainSiteToDcRelation() {
        List<ServiceChainSiteToDcRelation> serviceChainSiteToDcRelationList =
                new ArrayList<ServiceChainSiteToDcRelation>();
        ServiceChainSiteToDcRelation serviceChainSiteToDcRelation = new ServiceChainSiteToDcRelation();

        serviceChainSiteToDcRelation.setUuid("000001");

        ServiceChainPath serviceChainPath = new ServiceChainPath();
        serviceChainPath.allocateUuid();
        serviceChainPath.setName("test");

        serviceChainSiteToDcRelation.setData(JsonUtil.toJson(serviceChainPath));
        serviceChainSiteToDcRelationList.add(serviceChainSiteToDcRelation);
        return serviceChainSiteToDcRelationList;
    }
}
