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

package org.openo.sdno.overlayvpn.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;

public class ThrowOverlayVpnExcptTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = ServiceException.class)
    public void testCheckRspThrowException_nullResult() throws ServiceException {
        ThrowOverlayVpnExcpt.checkRspThrowException(null);
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public void testCheckRspThrowException_failedResult() throws ServiceException {

        ResultRsp rs = new ResultRsp<>();
        rs.setErrorCode(ErrorCode.DB_OPERATION_ERROR);
        ThrowOverlayVpnExcpt.checkRspThrowException(null);
        fail("Exception not occured");
    }

    @Test
    public void testThrowResNotExistAsBadReq() {
        try {
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq("TestDB", "Testing");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "[TestDB]Testing resource.not_exist.reason");
        }
    }

    @Test
    public void testThrowResNotExistAsNotFound() {
        try {
            ThrowOverlayVpnExcpt.throwResNotExistAsNotFound("TestDB", "Testing");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "[TestDB]Testing resource.not_exist.reason");
        }
    }

    @Test
    public void testThrowTenantIdInvalid() {
        try {
            ThrowOverlayVpnExcpt.throwTenantIdInvalid("123", "321");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "tenantid.invalid.reason");
        }
    }

    @Test
    public void testThrowTenantIdMissing() {
        try {
            ThrowOverlayVpnExcpt.throwTenantIdMissing("123");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "tenantid.data.missing.reason");
        }
    }

    @Test
    public void testThrowUpdateEpgInvalid() {
        try {
            ThrowOverlayVpnExcpt.throwUpdateEpgInvalid();
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getId(), "overlayvpn.operation.paramter_invalid");
        }

    }

    @Test
    public void testThrowHavingPolicyIdAsParmaterInvalid() {
        try {
            ThrowOverlayVpnExcpt.throwHavingPolicyIdAsParmaterInvalid("conn-1");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getId(), "overlayvpn.operation.paramter_invalid");
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "gre.mappingpolicy.having.reason");
        }
    }

    @Test
    public void testThrowParmaterInvalid() {
        try {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("testParam", "/../");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getId(), "overlayvpn.operation.paramter_invalid");
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "[testParam]/../ parameter.invalid.reason");
        }
    }

    @Test
    public void testThrowNotHavingPolicyIdAsParmaterInvalid() {
        try {
            ThrowOverlayVpnExcpt.throwNotHavingPolicyIdAsParmaterInvalid("conn-1");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getId(), "overlayvpn.operation.paramter_invalid");
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "connection.mappingpolicy.nohaving.reason");
        }
    }

    @Test
    public void testThrowPortConflict() {
        try {
            ThrowOverlayVpnExcpt.throwPortConflict("ne-1", "port-1");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getId(), "overlayvpn.operation.paramter_invalid");
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "svc.port.conflict.reason svc.port.conflict");
        }
    }

    @Test
    public void testThrowVlanConflict() {
        try {
            ThrowOverlayVpnExcpt.throwVlanConflict("ne-1", "port-1", "vlan-1");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getId(), "overlayvpn.operation.paramter_invalid");
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "svc.vlan.conflict.reason svc.vlan.conflict");
        }
    }

    @Test
    public void testThrowTpAlreadyExists() {
        try {
            ThrowOverlayVpnExcpt.throwTpAlreadyExists("ne-1", "port-1", "vlan-1");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getId(), "overlayvpn.operation.paramter_invalid");
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "svc.tp.already_exist.reason svc.tp.already_exist");
        }
    }

    @Test
    public void testThrowResourceIsInUsedExpt() {
        try {
            ThrowOverlayVpnExcpt.throwResourceIsInUsedExpt("res-1", "user-1");
            fail("Exception not thrown");
        } catch(ServiceException e) {
            assertEquals(e.getId(), "overlayvpn.operation.paramter_invalid");
            assertEquals(e.getExceptionArgs().getDetailArgs()[0], "resource.in.using.reason");
        }
    }

    @Test(expected = ServiceException.class)
    public void testThrowExceptionWithIdDesc() throws ServiceException {
        ThrowOverlayVpnExcpt.throwExceptionWithIdDesc("123", "desc");
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public void testThrowOverlayVpnDataMissExpt() throws ServiceException {
        ThrowOverlayVpnExcpt.throwOverlayVpnDataMissExpt("res", "resDesc");
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public void TestThrowQueryWanSubInfExpt() throws ServiceException {
        ThrowOverlayVpnExcpt.throwQueryWanSubInfExpt("ne-1", "type-1");
        fail("Exception not occured");
    }

    @Test(expected = ServiceException.class)
    public void throwConnectionIdDisaccord() throws ServiceException {
        ThrowOverlayVpnExcpt.throwConnectionIdDisaccord("conn01", "conn02");
        fail("Exception not occured");
    }
}
