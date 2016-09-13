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

package org.openo.sdno.overlayvpn.util.check;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.brs.invdao.LogicalTernminationPointInvDao;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.LogicalTernminationPointMO;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.EndpointType;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyRole;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.resource.ResourceUtil;

import mockit.Mock;
import mockit.MockUp;

public class CheckEndPointGrpUtilTest {

    private String endpointsJsonNegtive = JsonUtil.toJson(Arrays.asList("endpoint1", "endpoint2"));

    private String endpointsJsonNegtive2 = JsonUtil.toJson(Arrays.asList("99.99/8/16", "99.99/8/16"));

    private String endpointsJsonNegtive3 = JsonUtil.toJson(Arrays.asList("99.99/16", "99.99/16"));

    private String endpointsJsonNegtive4 = JsonUtil.toJson(Arrays.asList("aaaaaaaa/aa", "bbbbbbbb/bb"));

    private String endpointsJsonNegtive5 = JsonUtil.toJson(Arrays.asList("aaaaaaaa/16", "bbbbbbbb/16"));

    private String endpointsJsonCidr = JsonUtil.toJson(Arrays.asList("1.1.1.1/16", "2.2.2.2/16"));

    @Before
    public void setUp() throws Exception {
        new MockUp<ValidationUtil>() {

            @Mock
            public void validateModel(Object obj) throws ServiceException {
            }
        };

        new MockUp<ResourceUtil>() {

            @Mock
            public String getMessage(String str) {
                return "Test_{connectionId}";
            }
        };
    }

    @Test(expected = ServiceException.class)
    public void testCheckEndpointsJsonNegtive() throws ServiceException {
        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints("");
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public void testCheckEndpointsEmptyNegtive() throws ServiceException {
        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints("[]");
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public void testCheckCidrNegtive() throws ServiceException {
        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonNegtive);
        epg.setType(EndpointType.CIDR.getName());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public void testCheckPortVlanUuidNumNegtive() throws ServiceException {
        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonNegtive2);
        epg.setType(EndpointType.PORT_VLAN.getName());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public void testCheckPortUuidNegtive() throws ServiceException {
        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonNegtive3);
        epg.setType(EndpointType.PORT_VLAN.getName());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public void testCheckVlanRegexNegtive() throws ServiceException {
        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonNegtive4);
        epg.setType(EndpointType.PORT_VLAN.getName());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public void testCheckTpListNullNegtive() throws ServiceException {
        new MockUp<LogicalTernminationPointInvDao>() {

            @Mock
            public List<LogicalTernminationPointMO> getAllMO() throws ServiceException {
                return null;
            }
        };

        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonNegtive5);
        epg.setType(EndpointType.PORT_VLAN.getName());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public void testCheckPortNotExistNegtive() throws ServiceException {
        new MockUp<LogicalTernminationPointInvDao>() {

            @Mock
            public List<LogicalTernminationPointMO> getAllMO() throws ServiceException {
                return new ArrayList<LogicalTernminationPointMO>();
            }
        };

        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonNegtive5);
        epg.setType(EndpointType.PORT_VLAN.getName());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public void testCheckEpgTypeNegtive() throws ServiceException {
        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonNegtive5);
        epg.setType(EndpointType.VNI.getName());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public void testCheckGwNotNullNegtive() throws ServiceException {
        new MockUp<LogicalTernminationPointInvDao>() {

            @Mock
            public List<LogicalTernminationPointMO> getAllMO() throws ServiceException {
                List<LogicalTernminationPointMO> tpList = new ArrayList<LogicalTernminationPointMO>();
                LogicalTernminationPointMO tp1 = new LogicalTernminationPointMO();
                tp1.setId("aaaaaaaa");
                LogicalTernminationPointMO tp2 = new LogicalTernminationPointMO();
                tp2.setId("bbbbbbbb");
                tpList.add(tp1);
                tpList.add(tp2);
                return tpList;
            }
        };

        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonNegtive5);
        epg.setType(EndpointType.PORT_VLAN.getName());
        epg.setGateway(new Gateway());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckDeviceIdNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, new Connection());
            }
        };

        new MockUp<NetworkElementInvDao>() {

            @Mock
            public NetworkElementMO query(String id) throws ServiceException {
                return new NetworkElementMO();
            }
        };

        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonCidr);
        epg.setType(EndpointType.CIDR.getName());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckGatewayIdNotExistNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                if(clazz.isAssignableFrom(Gateway.class)) {
                    return new ResultRsp(ErrorCode.DB_RETURN_ERROR);
                } else {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, new Connection());
                }
            }
        };

        new MockUp<NetworkElementInvDao>() {

            @Mock
            public NetworkElementMO query(String id) throws ServiceException {
                NetworkElementMO tempNe = new NetworkElementMO();
                tempNe.setNativeID("deviceId1");
                return tempNe;
            }
        };

        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonCidr);
        epg.setType(EndpointType.CIDR.getName());
        epg.setGatewayId("gatewayId1");
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test
    public <T> void testCheckQueryByFilterNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, new Connection());
            }

            @Mock
            public ResultRsp<List<T>> queryByFilter(Class clazz, String filter, String queryResultFields)
                    throws ServiceException {
                return new ResultRsp(ErrorCode.DB_RETURN_ERROR);
            }
        };

        new MockUp<NetworkElementInvDao>() {

            @Mock
            public NetworkElementMO query(String id) throws ServiceException {
                NetworkElementMO tempNe = new NetworkElementMO();
                tempNe.setNativeID("deviceId1");
                return tempNe;
            }
        };

        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonCidr);
        epg.setType(EndpointType.CIDR.getName());
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.DB_RETURN_ERROR));
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckEpgSameInDbNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, new Connection());
            }

            @Mock
            public ResultRsp<List<T>> queryByFilter(Class clazz, String filter, String queryResultFields)
                    throws ServiceException {
                EndpointGroup epg = new EndpointGroup();
                epg.setNeId("neId1");
                List<EndpointGroup> invEpgs = new ArrayList<EndpointGroup>();
                invEpgs.add(epg);
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, invEpgs);
            }
        };

        new MockUp<NetworkElementInvDao>() {

            @Mock
            public NetworkElementMO query(String id) throws ServiceException {
                NetworkElementMO tempNe = new NetworkElementMO();
                tempNe.setNativeID("deviceId1");
                return tempNe;
            }
        };

        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonCidr);
        epg.setType(EndpointType.CIDR.getName());
        epg.setNeId("neId1");
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckFullMeshNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                Connection connection = new Connection();
                connection.setTopology(TopologyType.FULL_MESH.getName());
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, connection);
            }

            @Mock
            public ResultRsp<List<T>> queryByFilter(Class clazz, String filter, String queryResultFields)
                    throws ServiceException {
                EndpointGroup epg = new EndpointGroup();
                epg.setNeId("neId2");
                List<EndpointGroup> invEpgs = new ArrayList<EndpointGroup>();
                invEpgs.add(epg);
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, invEpgs);
            }
        };

        new MockUp<NetworkElementInvDao>() {

            @Mock
            public NetworkElementMO query(String id) throws ServiceException {
                NetworkElementMO tempNe = new NetworkElementMO();
                tempNe.setNativeID("deviceId1");
                return tempNe;
            }
        };

        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonCidr);
        epg.setType(EndpointType.CIDR.getName());
        epg.setNeId("neId1");
        epg.setTopologyRole(TopologyRole.SPOKE.getName());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckHubSpokeNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                Connection connection = new Connection();
                connection.setTopology(TopologyType.HUB_SPOKE.getName());
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, connection);
            }

            @Mock
            public ResultRsp<List<T>> queryByFilter(Class clazz, String filter, String queryResultFields)
                    throws ServiceException {
                EndpointGroup epg = new EndpointGroup();
                epg.setNeId("neId2");
                epg.setTopologyRole(TopologyRole.HUB.getName());
                List<EndpointGroup> invEpgs = new ArrayList<EndpointGroup>();
                invEpgs.add(epg);
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, invEpgs);
            }
        };

        new MockUp<NetworkElementInvDao>() {

            @Mock
            public NetworkElementMO query(String id) throws ServiceException {
                NetworkElementMO tempNe = new NetworkElementMO();
                tempNe.setNativeID("deviceId1");
                return tempNe;
            }
        };

        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonCidr);
        epg.setType(EndpointType.CIDR.getName());
        epg.setNeId("neId1");
        epg.setTopologyRole(TopologyRole.HUB.getName());
        CheckEndPointGrpUtil.check(epg, "tenantId1");
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_TENANT_INVALID));
    }

    @Test
    public <T> void testCheck() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                Connection connection = new Connection();
                connection.setTopology(TopologyType.HUB_SPOKE.getName());
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, connection);
            }

            @Mock
            public ResultRsp<List<T>> queryByFilter(Class clazz, String filter, String queryResultFields)
                    throws ServiceException {
                EndpointGroup epg = new EndpointGroup();
                epg.setNeId("neId2");
                epg.setTopologyRole(TopologyRole.HUB.getName());
                List<EndpointGroup> invEpgs = new ArrayList<EndpointGroup>();
                invEpgs.add(epg);
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, invEpgs);
            }
        };

        new MockUp<NetworkElementInvDao>() {

            @Mock
            public NetworkElementMO query(String id) throws ServiceException {
                NetworkElementMO tempNe = new NetworkElementMO();
                tempNe.setNativeID("deviceId1");
                return tempNe;
            }
        };

        EndpointGroup epg = new EndpointGroup();
        epg.setEndpoints(endpointsJsonCidr);
        epg.setType(EndpointType.CIDR.getName());
        epg.setNeId("neId1");
        epg.setTopologyRole(TopologyRole.SPOKE.getName());
        assertTrue(CheckEndPointGrpUtil.check(epg, "tenantId1").getErrorCode().equals(ErrorCode.OVERLAYVPN_SUCCESS));
    }
}
