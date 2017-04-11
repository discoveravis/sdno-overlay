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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulOptions;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.invdao.PopInvDao;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.brs.model.PopMO;
import org.openo.sdno.overlayvpn.composer.TemplateManager;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.netmodel.vpc.Subnet;
import org.openo.sdno.overlayvpn.model.v2.overlay.BaseModel;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiConnectionRelation;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpn;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetIpsecConnection;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetRoute;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetVxlanConnection;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateObject;
import org.openo.sdno.overlayvpn.util.toolkit.FileUtils;
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
public class VpnConnectionRoaResourceTest {

    private static final String VXLAN_BASIC_PATH = "/openoapi/sdnovxlan/v1/vxlan";

    private static final String IPSEC_BASIC_PATH = "/openoapi/sdnoipsec/v1/ipsec";

    private static final String ROUTE_BASIC_PATH = "/openoapi/sdnoroute/v1/route";

    private static final String SITE_BASIC_PATH = "/openoapi/sdnolocalsite/v1/sites";

    @Mocked
    HttpServletRequest request;

    @Mocked
    HttpServletResponse response;

    @Autowired
    VpnConnectionRoaResource vpnConnectionRoaResource;

    @Before
    public void setUp() throws Exception {
        new MockRestfulProxy();
        new MockTemplateManager();
        new MockNeDao();
        new MockPopDao();
    }

    @Test
    public void testCreateSuccess() throws ServiceException {
        File file = new File("src/test/resources/vpnconnection.json");
        NbiVpnConnection internalConnection = JsonUtil.fromJson(FileUtils.readFile(file), NbiVpnConnection.class);
        Map<String, Object> resultRsp =
                (Map<String, Object>)vpnConnectionRoaResource.create(request, response, internalConnection);
        assertTrue(MapUtils.isNotEmpty(resultRsp));
    }

    @Test
    public void testDeleteSuccess() throws ServiceException {
        String resultRsp = (String)vpnConnectionRoaResource.delete(request, response, "test_uuid");
        NbiVpnConnection result = JsonUtil.fromJson(resultRsp, NbiVpnConnection.class);
        assertTrue(StringUtils.isNotEmpty(result.getId()));
    }

    private final class MockRestfulProxy extends MockUp<RestfulProxy> {

        @Mock
        RestfulResponse get(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            response.setStatus(HttpStatus.SC_OK);
            if(uri.startsWith(SITE_BASIC_PATH)) {
                File file = new File("src/test/resources/querysite.json");
                VpnSite vpnSite = JsonUtil.fromJson(FileUtils.readFile(file), VpnSite.class);

                response.setResponseJson(JsonUtil.toJson(vpnSite));
            } else if(uri.contains("gateway_id")) {
                File file = new File("src/test/resources/gateway1.json");

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", FileUtils.readFile(file));
                response.setResponseJson(JsonUtil.toJson(responseMap));
            } else if(uri.contains("gateway_uuid")) {
                File file = new File("src/test/resources/gateway2.json");

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", FileUtils.readFile(file));
                response.setResponseJson(JsonUtil.toJson(responseMap));
            } else if(uri.contains("vpnId")) {
                File file = new File("src/test/resources/vpn.json");
                NbiVpn vpn = JsonUtil.fromJson(FileUtils.readFile(file), NbiVpn.class);
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", vpn);
                response.setResponseJson(JsonUtil.toJson(responseMap));
            } else if(uri.contains("vpnconnection")) {
                File file = new File("src/test/resources/vpnconnection.json");
                NbiVpnConnection vpnConnection = JsonUtil.fromJson(FileUtils.readFile(file), NbiVpnConnection.class);
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", vpnConnection);
                response.setResponseJson(JsonUtil.toJson(responseMap));
            } else if(uri.contains("connectionrelation")) {
                List<NbiConnectionRelation> relations = new ArrayList<>();
                NbiConnectionRelation connectionRelationVxlan = new NbiConnectionRelation();
                connectionRelationVxlan.setId("relationId");
                connectionRelationVxlan.setNetConnectionId("vxlanId");
                connectionRelationVxlan.setNetConnectionType("vxlan");
                connectionRelationVxlan.setVpnConnectionId("connectionId");
                connectionRelationVxlan.setVpnConnectionType("VpnConnection");
                relations.add(connectionRelationVxlan);

                NbiConnectionRelation connectionRelationIpsec = new NbiConnectionRelation();
                connectionRelationIpsec.setId("relationId");
                connectionRelationIpsec.setNetConnectionId("ipsecId");
                connectionRelationIpsec.setNetConnectionType("ipsec");
                connectionRelationIpsec.setVpnConnectionId("connectionId");
                connectionRelationIpsec.setVpnConnectionType("VpnConnection");
                relations.add(connectionRelationIpsec);
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("objects", relations);
                response.setResponseJson(JsonUtil.toJson(responseMap));
            } else if(uri.contains("vxlanconnection")) {
                NetVxlanConnection connectionVxlan = new NetVxlanConnection();
                connectionVxlan.setId("vxlan_uuid");
                connectionVxlan.setName("vxlan");
                connectionVxlan.setType("vxlan");
                connectionVxlan.setSrcNeId("NeId1");
                connectionVxlan.setDestNeId("NeId2");
                connectionVxlan.setSrcNeRole("localCpe");
                connectionVxlan.setDestNeRole("cloudCpe");
                connectionVxlan.setVni("25");

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", connectionVxlan);
                response.setResponseJson(JsonUtil.toJson(responseMap));
            } else if(uri.contains("ipsecconnection")) {
                NetIpsecConnection connectionIpsec = new NetIpsecConnection();
                connectionIpsec.setId("ipsec_uuid");
                connectionIpsec.setName("ipsec");
                connectionIpsec.setType("ipsec");
                connectionIpsec.setSrcNeId("NeId2");
                connectionIpsec.setDestNeId("test_uuid");
                connectionIpsec.setSrcNeRole("cloudCpe");
                connectionIpsec.setDestNeRole("vpc");

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", connectionIpsec);
                response.setResponseJson(JsonUtil.toJson(responseMap));
            } else if(uri.contains("subnets")) {
                Subnet subnet = new Subnet();
                subnet.setCidr("10.21.3.0/24");
                response.setResponseJson(JsonUtil.toJson(subnet));
            } else {
                BaseModel model = new BaseModel();
                model.setId("test_uuid");
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("object", model);
                response.setResponseJson(JsonUtil.toJson(responseMap));
            }

            return response;
        }

        @Mock
        RestfulResponse get(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            response.setStatus(HttpStatus.SC_OK);
            if(uri.startsWith(SITE_BASIC_PATH)) {
                File file = new File("src/test/resources/querysite.json");
                VpnSite vpnSite = JsonUtil.fromJson(FileUtils.readFile(file), VpnSite.class);

                response.setResponseJson(JsonUtil.toJson(vpnSite));
            }

            return response;
        }

        @Mock
        RestfulResponse post(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            response.setStatus(HttpStatus.SC_OK);
            if(uri.startsWith(VXLAN_BASIC_PATH)) {
                NetVxlanConnection vxlan = new NetVxlanConnection();
                response.setResponseJson(JsonUtil.toJson(vxlan));
            } else if(uri.startsWith(IPSEC_BASIC_PATH)) {
                NetIpsecConnection ipsec = new NetIpsecConnection();
                response.setResponseJson(JsonUtil.toJson(ipsec));
            } else if(uri.startsWith(ROUTE_BASIC_PATH)) {
                NetRoute route = new NetRoute();
                response.setResponseJson(JsonUtil.toJson(route));
            }
            return response;
        }

        @Mock
        RestfulResponse post(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            RestfulResponse response = new RestfulResponse();
            response.setStatus(HttpStatus.SC_OK);
            if(uri.startsWith(VXLAN_BASIC_PATH)) {
                List<NetVxlanConnection> list = new ArrayList<>();
                response.setResponseJson(JsonUtil.toJson(list));
            } else if(uri.startsWith(IPSEC_BASIC_PATH)) {
                List<NetIpsecConnection> list = new ArrayList<>();
                response.setResponseJson(JsonUtil.toJson(list));
            } else if(uri.startsWith(ROUTE_BASIC_PATH)) {
                List<NetRoute> list = new ArrayList<>();
                response.setResponseJson(JsonUtil.toJson(list));
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

        @Mock
        RestfulResponse delete(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            ResultRsp<String> sbiRsp = new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS);
            response.setStatus(HttpStatus.SC_OK);
            response.setResponseJson(JsonUtil.toJson(sbiRsp));

            return response;
        }

        @Mock
        RestfulResponse put(String uri, RestfulParametes restParametes) throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            ResultRsp<String> sbiRsp = new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS);
            response.setStatus(HttpStatus.SC_OK);
            response.setResponseJson(JsonUtil.toJson(sbiRsp));

            return response;
        }

        @Mock
        RestfulResponse put(String uri, RestfulParametes restParametes, RestfulOptions restOptions)
                throws ServiceException {
            RestfulResponse response = new RestfulResponse();

            ResultRsp<String> sbiRsp = new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS);
            response.setStatus(HttpStatus.SC_OK);
            response.setResponseJson(JsonUtil.toJson(sbiRsp));

            return response;
        }
    }

    private class MockTemplateManager extends MockUp<TemplateManager> {

        @Mock
        public Template getTemplate(String templateName) throws ServiceException {
            File file = new File("src/test/resources/IpSec_EthernetNetwork.json");
            TemplateObject templateObject = JsonUtil.fromJson(FileUtils.readFile(file), TemplateObject.class);

            Template template = new Template(templateObject);
            return template;
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
            ne.setProductName("AR161FGW-L");
            return ne;
        }

        @Mock
        public void updateMO(NetworkElementMO curNeMO) throws ServiceException {
            return;
        }

    }

    private class MockPopDao extends MockUp<PopInvDao> {

        @Mock
        public PopMO query(String uuid) throws ServiceException {
            PopMO pop = new PopMO();
            pop.setId(uuid);
            pop.setInternetGW("0.0.0.0");
            return pop;
        }

    }
}
