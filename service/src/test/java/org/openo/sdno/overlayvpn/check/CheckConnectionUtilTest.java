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

package org.openo.sdno.overlayvpn.check;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.TechnologyType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.CheckConnectionUtil;
import org.openo.sdno.overlayvpn.util.check.ValidationUtil;
import org.openo.sdno.resource.ResourceUtil;

import mockit.Mock;
import mockit.MockUp;

public class CheckConnectionUtilTest {

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
    public void testCheckEpgIdsNegtive() throws ServiceException {
        List<String> epgIds = new ArrayList<String>();
        epgIds.add("epgId1");
        Connection connection = new Connection();
        connection.setEpgIds(epgIds);
        CheckConnectionUtil.check(connection);
    }

    @Test(expected = ServiceException.class)
    public void testCheckEndpointGroupsNegtive() throws ServiceException {
        List<EndpointGroup> epgGroups = new ArrayList<EndpointGroup>();
        epgGroups.add(new EndpointGroup());
        Connection connection = new Connection();
        connection.setEndpointGroups(epgGroups);
        CheckConnectionUtil.check(connection);
    }

    @Test(expected = ServiceException.class)
    public void testCheckMappingPolicyNotNullNegtive() throws ServiceException {
        Connection connection = new Connection();
        connection.setName("name1");
        connection.setIpsecMappingPolicy(new IpsecMappingPolicy());
        CheckConnectionUtil.check(connection);
    }

    @Test(expected = ServiceException.class)
    public void testCheckMappingPolicyMatchNegtive() throws ServiceException {
        Connection connection = new Connection();
        connection.setName("name1");
        connection.setTechnology(TechnologyType.GRE_OVER_IPSEC.getName());
        CheckConnectionUtil.check(connection);
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckQueryVpnNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {

                return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
            }
        };

        Connection connection = new Connection();
        connection.setName("name1");
        connection.setMappingPolicyId("mappingPolicyId1");
        CheckConnectionUtil.check(connection);
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIpsecMappingPolicyNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                if(clazz.isAssignableFrom(OverlayVpn.class)) {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
                } else {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
                }
            }
        };

        Connection connection = new Connection();
        connection.setName("name1");
        connection.setTechnology(TechnologyType.IPSEC.getName());
        connection.setMappingPolicyId("mappingPolicyId1");
        CheckConnectionUtil.check(connection);
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckVxlanMappingPolicyNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                if(clazz.isAssignableFrom(OverlayVpn.class)) {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
                } else {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
                }
            }
        };

        Connection connection = new Connection();
        connection.setName("name1");
        connection.setTechnology(TechnologyType.VXLAN.getName());
        connection.setMappingPolicyId("mappingPolicyId1");
        CheckConnectionUtil.check(connection);
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckMappingPolicyTypeNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                VxlanMappingPolicy vxlanMappingPolicy = new VxlanMappingPolicy();
                vxlanMappingPolicy.setType(TechnologyType.IPSEC.getName());
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, vxlanMappingPolicy);
            }
        };

        Connection connection = new Connection();
        connection.setName("name1");
        connection.setTechnology(TechnologyType.VXLAN.getName());
        connection.setMappingPolicyId("mappingPolicyId1");

        CheckConnectionUtil.check(connection);
    }

    @Test
    public <T> void testCheck() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                VxlanMappingPolicy vxlanMappingPolicy = new VxlanMappingPolicy();
                vxlanMappingPolicy.setType(TechnologyType.VXLAN.getName());
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, vxlanMappingPolicy);
            }
        };

        Connection connection = new Connection();
        connection.setName("name1");
        connection.setTechnology(TechnologyType.VXLAN.getName());
        connection.setMappingPolicyId("mappingPolicyId1");

        CheckConnectionUtil.check(connection);
    }
}
