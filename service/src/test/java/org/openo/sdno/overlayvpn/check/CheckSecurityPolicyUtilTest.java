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
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.GreMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.check.CheckGatewayUtil;
import org.openo.sdno.overlayvpn.util.check.CheckSecurityPolicyUtil;
import org.openo.sdno.overlayvpn.util.check.ValidationUtil;
import org.openo.sdno.resource.ResourceUtil;

import mockit.Mock;
import mockit.MockUp;

public class CheckSecurityPolicyUtilTest {

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

    @Test
    public void testCheck() throws ServiceException {
        CheckSecurityPolicyUtil.check(new IkePolicy());
        try {
            CheckSecurityPolicyUtil.check(new IkePolicy());
            assertTrue(true);
        } catch(Exception e) {
            fail("Exception occured");
        }
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIpsecQueryGreNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> batchQuery(Class clazz, String filter) throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
            }
        };

        IpSecPolicy ipsecPolicy = new IpSecPolicy();
        ipsecPolicy.setUuid("uuid1");
        CheckSecurityPolicyUtil.checkIpsecPolicyIsUsed(ipsecPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIpsecQueryGreDataNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> batchQuery(Class clazz, String filter) throws ServiceException {
                List<GreMappingPolicy> greMappingList = new ArrayList<GreMappingPolicy>();
                greMappingList.add(new GreMappingPolicy());
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, greMappingList);
            }
        };

        IpSecPolicy ipsecPolicy = new IpSecPolicy();
        ipsecPolicy.setUuid("uuid1");
        CheckSecurityPolicyUtil.checkIpsecPolicyIsUsed(ipsecPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIpsecQueryIpsecNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> batchQuery(Class clazz, String filter) throws ServiceException {
                if(clazz.isAssignableFrom(GreMappingPolicy.class)) {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
                } else {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
                }
            }
        };

        IpSecPolicy ipsecPolicy = new IpSecPolicy();
        ipsecPolicy.setUuid("uuid1");
        CheckSecurityPolicyUtil.checkIpsecPolicyIsUsed(ipsecPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIpsecQueryIpsecDataNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> batchQuery(Class clazz, String filter) throws ServiceException {
                if(clazz.isAssignableFrom(GreMappingPolicy.class)) {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
                } else {
                    List<IpsecMappingPolicy> ipsecMappingList = new ArrayList<IpsecMappingPolicy>();
                    ipsecMappingList.add(new IpsecMappingPolicy());
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, ipsecMappingList);
                }
            }
        };

        IpSecPolicy ipsecPolicy = new IpSecPolicy();
        ipsecPolicy.setUuid("uuid1");
        CheckSecurityPolicyUtil.checkIpsecPolicyIsUsed(ipsecPolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIkeQueryGreNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> batchQuery(Class clazz, String filter) throws ServiceException {
                return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
            }
        };

        IkePolicy ikePolicy = new IkePolicy();
        ikePolicy.setUuid("uuid1");
        CheckSecurityPolicyUtil.checkIkePolicyIsUsed(ikePolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIkeQueryGreDataNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> batchQuery(Class clazz, String filter) throws ServiceException {
                List<GreMappingPolicy> greMappingList = new ArrayList<GreMappingPolicy>();
                greMappingList.add(new GreMappingPolicy());
                return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, greMappingList);
            }
        };

        IkePolicy ikePolicy = new IkePolicy();
        ikePolicy.setUuid("uuid1");
        CheckSecurityPolicyUtil.checkIkePolicyIsUsed(ikePolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIkeQueryIpsecNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> batchQuery(Class clazz, String filter) throws ServiceException {
                if(clazz.isAssignableFrom(GreMappingPolicy.class)) {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
                } else {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
                }
            }
        };

        IkePolicy ikePolicy = new IkePolicy();
        ikePolicy.setUuid("uuid1");
        CheckSecurityPolicyUtil.checkIkePolicyIsUsed(ikePolicy);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public <T> void testCheckIkeQueryIpsecDataNegtive() throws ServiceException {
        new MockUp<InventoryDao<T>>() {

            @Mock
            public ResultRsp<List<T>> batchQuery(Class clazz, String filter) throws ServiceException {
                if(clazz.isAssignableFrom(GreMappingPolicy.class)) {
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);
                } else {
                    List<IpsecMappingPolicy> ipsecMappingList = new ArrayList<IpsecMappingPolicy>();
                    ipsecMappingList.add(new IpsecMappingPolicy());
                    return new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS, ipsecMappingList);
                }
            }
        };

        IkePolicy ikePolicy = new IkePolicy();
        ikePolicy.setUuid("uuid1");
        CheckSecurityPolicyUtil.checkIkePolicyIsUsed(ikePolicy);
        fail("Exception not occured");
    }
}
