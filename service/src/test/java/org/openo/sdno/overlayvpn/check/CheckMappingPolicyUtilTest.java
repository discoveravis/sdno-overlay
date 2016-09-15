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

package org.openo.sdno.overlayvpn.check;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.common.enums.MappingPolicyType;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.GreMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.CheckGatewayUtil;
import org.openo.sdno.overlayvpn.util.check.CheckMappingPolicyUtil;
import org.openo.sdno.overlayvpn.util.check.ValidationUtil;
import org.openo.sdno.resource.ResourceUtil;

import mockit.Mock;
import mockit.MockUp;

public class CheckMappingPolicyUtilTest {

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
    public <T> void testCheckPolicyTypeNegtive() throws ServiceException {
        GreMappingPolicy mappingPolicy = new GreMappingPolicy();
        mappingPolicy.setIkePolicyId("ikePolicyId1");
        mappingPolicy.setType(MappingPolicyType.IPSEC.getName());
        CheckMappingPolicyUtil.check(mappingPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckQueryIkePolicyNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
            }
        };

        GreMappingPolicy mappingPolicy = new GreMappingPolicy();
        mappingPolicy.setIkePolicyId("ikePolicyId1");
        mappingPolicy.setIpsecPolicyId("ipsecPolicyId1");
        mappingPolicy.setType(MappingPolicyType.GRE_OVER_IPSEC.getName());
        CheckMappingPolicyUtil.check(mappingPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckGetIkePolicyDataNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
            }
        };

        IpsecMappingPolicy mappingPolicy = new IpsecMappingPolicy();
        mappingPolicy.setIkePolicyId("ikePolicyId1");
        mappingPolicy.setType(MappingPolicyType.IPSEC.getName());
        CheckMappingPolicyUtil.check(mappingPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckQueryIpsecPolicyNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
            }
        };

        VxlanMappingPolicy mappingPolicy = new VxlanMappingPolicy();
        mappingPolicy.setIpsecPolicyId("ipsecPolicyId1");
        mappingPolicy.setType(MappingPolicyType.VXLAN.getName());
        CheckMappingPolicyUtil.check(mappingPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckGetIpsecPolicyDataNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<T> query(Class clazz, String uuid, String tenantId) throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
            }
        };

        IpsecMappingPolicy mappingPolicy = new IpsecMappingPolicy();
        mappingPolicy.setIpsecPolicyId("ipsecPolicyId1");
        mappingPolicy.setType(MappingPolicyType.IPSEC.getName());
        CheckMappingPolicyUtil.check(mappingPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIkePolicyNegtive() throws ServiceException {
        IpsecMappingPolicy mappingPolicy = new IpsecMappingPolicy();
        mappingPolicy.setType(MappingPolicyType.IPSEC.getName());
        mappingPolicy.setIkePolicy(new IkePolicy());
        CheckMappingPolicyUtil.check(mappingPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIpsecPolicyNegtive() throws ServiceException {
        IpsecMappingPolicy mappingPolicy = new IpsecMappingPolicy();
        mappingPolicy.setType(MappingPolicyType.IPSEC.getName());
        mappingPolicy.setIpSecPolicy(new IpSecPolicy());
        CheckMappingPolicyUtil.check(mappingPolicy);
        assertTrue(MappingPolicyType.isIpsec("ipsec"));
        assertTrue(MappingPolicyType.isVxlanRelated("vxlan"));
    }

    @Test
    public <T> void testCheck() throws ServiceException {
        IpsecMappingPolicy mappingPolicy = new IpsecMappingPolicy();
        mappingPolicy.setType(MappingPolicyType.IPSEC.getName());

        try {
            CheckMappingPolicyUtil.check(mappingPolicy);
            assertTrue(true);
        } catch(Exception e) {
            fail("Exception occured");
        }
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckMappingPolicyIsUsedNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> queryByFilter(Class clazz, String filter, String queryResultFields)
                    throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
            }
        };

        IpsecMappingPolicy mappingPolicy = new IpsecMappingPolicy();
        mappingPolicy.setUuid("uuid1");
        CheckMappingPolicyUtil.checkMappingPolicyIsUsed(mappingPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckUseQueryDataNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> queryByFilter(Class clazz, String filter, String queryResultFields)
                    throws ServiceException {
                List<Connection> connList = new ArrayList<Connection>();
                connList.add(new Connection());
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, connList);
            }
        };

        IpsecMappingPolicy mappingPolicy = new IpsecMappingPolicy();
        mappingPolicy.setUuid("uuid1");
        CheckMappingPolicyUtil.checkMappingPolicyIsUsed(mappingPolicy);
        fail("Exception not occured");
    }

    @Test
    public <T> void testCheckMappingPolicyIsUsed() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> queryByFilter(Class clazz, String filter, String queryResultFields)
                    throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, new ArrayList<Connection>());
            }
        };

        IpsecMappingPolicy mappingPolicy = new IpsecMappingPolicy();
        mappingPolicy.setUuid("uuid1");
        try {
            CheckMappingPolicyUtil.checkMappingPolicyIsUsed(mappingPolicy);
            assertTrue(true);
        } catch(Exception e) {
            fail("Exception occured");
        }
    }
}
