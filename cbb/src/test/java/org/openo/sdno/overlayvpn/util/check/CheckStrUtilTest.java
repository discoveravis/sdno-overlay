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

package org.openo.sdno.overlayvpn.util.check;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.util.check.CheckStrUtil;
import org.openo.sdno.resource.ResourceUtil;

/**
 * CheckStrUtil test class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-14
 */
public class CheckStrUtilTest {

    @Before
    public void setUp() throws Exception {
        new MockUp<ResourceUtil>() {

            @Mock
            public String getMessage(String str) {
                return "CheckStrUtilTest Message";
            }
        };
    }

    @Test
    public void testCheckUuidStr() throws ServiceException {

        try {
            CheckStrUtil.checkUuidStr("123456");
        } catch(ServiceException ex) {
            fail("should not get ServiceException, " + ex.getId());
        }
    }

    @Test
    public void testCheckUuidStr2() throws ServiceException {
        try {
            CheckStrUtil.checkUuidStr("+++");
            fail("should get ServiceException");
        } catch(ServiceException ex) {
            assertEquals(ex.getId(), "overlayvpn.operation.paramter_invalid");
        }
    }

    @Test
    public void testCheckUuidStrList() throws ServiceException {
        List<String> uuidList = new ArrayList<String>();
        uuidList.add("123456");
        uuidList.add("456789");

        try {
            CheckStrUtil.checkUuidStr(uuidList);
        } catch(ServiceException ex) {
            fail("should not get ServiceException, " + ex.getId());
        }
    }

    @Test
    public void testCheckUuidStrList2() {
        List<String> uuidList = new ArrayList<String>();
        uuidList.add("123456");
        uuidList.add("*****");

        try {
            CheckStrUtil.checkUuidStr(uuidList);
            fail("should get ServiceException");
        } catch(ServiceException ex) {
            assertEquals(ex.getId(), "overlayvpn.operation.paramter_invalid");
        }
    }

    @Test
    public void testCheckIsSameUuid() throws ServiceException {

        try {
            CheckStrUtil.checkIsSameUuid("123456", "123456");
        } catch(ServiceException ex) {
            fail("should not get ServiceException, " + ex.getId());
        }
    }

    @Test
    public void testCheckIsSameUuid2() throws ServiceException {
        try {
            CheckStrUtil.checkIsSameUuid("123456", "22222");
            fail("should get ServiceException");
        } catch(ServiceException ex) {
            assertEquals(ex.getId(), "overlayvpn.operation.paramter_invalid");
        }
    }

    @Test
    public void testCheckIntRangeFormat() throws ServiceException {
        try {
            CheckStrUtil.checkIntRangeFormat("1-10");
        } catch(ServiceException ex) {
            fail("should not get ServiceException, " + ex.getId());
        }
    }

    @Test
    public void testCheckIntRangeFormat2() throws ServiceException {
        try {
            CheckStrUtil.checkIntRangeFormat("1+++10");
            fail("should get ServiceException");
        } catch(ServiceException ex) {
            assertEquals(ex.getId(), "overlayvpn.operation.paramter_invalid");
        }
    }

    @Test
    public void testValidateNormalizedString() {
        assertTrue(CheckStrUtil.validateNormalizedString(null, null));
    }

    @Test
    public void testValidateNormalizedString2() {
        assertTrue(CheckStrUtil.validateNormalizedString("a", "[a-z]"));
    }

    @Test
    public void testValidateNormalizedString3() {
        assertFalse(CheckStrUtil.validateNormalizedString("+", "[a-z]"));
    }

}
