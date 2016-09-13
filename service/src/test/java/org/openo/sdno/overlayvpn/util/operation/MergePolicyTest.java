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

package org.openo.sdno.overlayvpn.util.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.common.enums.PfsType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.AuthAlgorithmType;
import org.openo.sdno.overlayvpn.model.common.enums.ipsec.EncryptionAlgorithmType;
import org.openo.sdno.overlayvpn.model.ipsec.SecurityPolicy;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;

public class MergePolicyTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testMergeSecurityPolicy() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", "1");
        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();

        try {

            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
        } catch(ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception");
        }

    }

    @Test(expected = ServiceException.class)
    public void testMergeSecurityPolicyException() throws ServiceException {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("pfs", "WrongValue");
        updateNameValueMap.put("lifetime", "1");
        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();

        MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
        fail("Exception");
    }

    @Test
    public void testbuildQueryFilterMap() {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;
        try {

            Map<String, List<String>> result = MergePolicy.buildQueryFilterMap("abc-def-0123-456", "name",
                    "description", AuthAlgorithmType.SHA1.getName(), EncryptionAlgorithmType.AES128.getName(),
                    PfsType.GROUP14.getName(), "1");
            assertEquals((result.get("name")).get(0), "name");
            assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");
        } catch(ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            fail("Exception");
        }

    }

    @Test(expected = ServiceException.class)
    public void testbuildQueryFilterMap_fail_forTenantID() throws ServiceException {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;

        Map<String, List<String>> result = MergePolicy.buildQueryFilterMap(null,
                "name111111222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222",
                "description", AuthAlgorithmType.SHA1.getName(), EncryptionAlgorithmType.AES128.getName(),
                PfsType.GROUP14.getName(), "1");
        assertEquals((result.get("name")).get(0), "name");
        assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");

    }

    @Test(expected = ServiceException.class)
    public void testbuildQueryFilterMap_fail_forName() throws ServiceException {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;

        Map<String, List<String>> result = MergePolicy.buildQueryFilterMap("abc-def-0123-456",
                "name111111222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222",
                "description", AuthAlgorithmType.SHA1.getName(), EncryptionAlgorithmType.AES128.getName(),
                PfsType.GROUP14.getName(), "1");
        assertEquals((result.get("name")).get(0), "name");
        assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");

    }

    @Test
    public void testbuildQueryFilterMap_fail_forName_null() throws ServiceException {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;

        Map<String, List<String>> result = MergePolicy.buildQueryFilterMap("abc-def-0123-456", null, "description",
                AuthAlgorithmType.SHA1.getName(), EncryptionAlgorithmType.AES128.getName(), PfsType.GROUP14.getName(),
                "1");
        assertTrue(result != null);

    }

    @Test(expected = ServiceException.class)
    public void testbuildQueryFilterMap_fail_forDescription() throws ServiceException {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;

        Map<String, List<String>> result = MergePolicy.buildQueryFilterMap("abc-def-0123-456", "name",
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
                AuthAlgorithmType.SHA1.getName(), EncryptionAlgorithmType.AES128.getName(), PfsType.GROUP14.getName(),
                "1");
        assertEquals((result.get("name")).get(0), "name");
        assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");

    }

    @Test
    public void testbuildQueryFilterMap_fail_forDescription_null() {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;
        try {

            Map<String, List<String>> result =
                    MergePolicy.buildQueryFilterMap("abc-def-0123-456", "name", null, AuthAlgorithmType.SHA1.getName(),
                            EncryptionAlgorithmType.AES128.getName(), PfsType.GROUP14.getName(), "1");
            assertEquals((result.get("name")).get(0), "name");
            assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");
        } catch(ServiceException e) {
            fail("Exception");
        }

    }

    @Test(expected = ServiceException.class)
    public void testbuildQueryFilterMapForAuthAlgo() throws ServiceException {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;

        Map<String, List<String>> result = MergePolicy.buildQueryFilterMap("abc-def-0123-456", "name", "description",
                "xyz", EncryptionAlgorithmType.AES128.getName(), PfsType.GROUP14.getName(), "1");
        assertEquals((result.get("name")).get(0), "name");
        assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");
    }

    @Test(expected = ServiceException.class)
    public void testbuildQueryFilterMapForEncryption() throws ServiceException {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;

        Map<String, List<String>> result = MergePolicy.buildQueryFilterMap("abc-def-0123-456", "name", "description",
                AuthAlgorithmType.SHA1.getName(), "wrongvalue", PfsType.GROUP14.getName(), "1");
        assertEquals((result.get("name")).get(0), "name");
        assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");
    }

    @Test
    public void testbuildQueryFilterMapForEncryption_null() {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;
        try {

            Map<String, List<String>> result = MergePolicy.buildQueryFilterMap("abc-def-0123-456", "name",
                    "description", AuthAlgorithmType.SHA1.getName(), null, PfsType.GROUP14.getName(), "1");
            assertEquals((result.get("name")).get(0), "name");
            assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");
        } catch(ServiceException e) {
            fail("Exception");
        }

    }

    @Test(expected = ServiceException.class)
    public void testbuildQueryFilterMapForEncryption_forpfs() throws ServiceException {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;

        Map<String, List<String>> result = MergePolicy.buildQueryFilterMap("abc-def-0123-456", "name", "description",
                AuthAlgorithmType.SHA1.getName(), EncryptionAlgorithmType.AES128.getName(), "wrongvalue", "1");
        assertEquals((result.get("name")).get(0), "name");
        assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");
    }

    @Test
    public void testbuildQueryFilterMapForEncryption_forpfs_null() throws ServiceException {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;

        Map<String, List<String>> result = MergePolicy.buildQueryFilterMap("abc-def-0123-456", "name", "description",
                AuthAlgorithmType.SHA1.getName(), EncryptionAlgorithmType.AES128.getName(), null, "1");
        assertEquals((result.get("name")).get(0), "name");
        assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");
    }

    @Test
    public void testbuildQueryFilterMapFail_forlifetime() throws ServiceException {
        Map<String, String> updateNameValueMap = null;
        SecurityPolicy mergedSecurityPolicy = null;

        Map<String, List<String>> result = MergePolicy.buildQueryFilterMap("abc-def-0123-456", "name", "description",
                AuthAlgorithmType.SHA1.getName(), EncryptionAlgorithmType.AES128.getName(), PfsType.GROUP14.getName(),
                null);
        assertEquals((result.get("name")).get(0), "name");
        assertEquals((result.get("tenantId")).get(0), "abc-def-0123-456");
    }

    @Test
    public void testMergeSecurityPolicyInvalid() {
        try {
            Map<String, String> updateNameValueMap = new HashMap<String, String>();
            updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
            updateNameValueMap.put("lifetim", "1");
            SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();
            String pfs = null;
            ThrowOverlayVpnExcpt.throwParmaterInvalid("pfs", pfs);
            fail("Validation is not done");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMergeSecurityPolicy_fail_invalid() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", "1");
        updateNameValueMap.put("authAlgorithm", "xyz");
        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();
        try {
            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
            fail("Validation is not done");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMergeSecurityPolicy_fail_null() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", "1");
        updateNameValueMap.put("authAlgorithm", null);
        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();
        try {
            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Exception");
        }

    }

    @Test
    public void testMergeSecurityPolicy_encryptionfail_invalid() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", "1");
        updateNameValueMap.put("authAlgorithm", AuthAlgorithmType.SHA1.getName());
        updateNameValueMap.put("encryptionAlgorithm", "xyz");
        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();
        try {

            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
            fail("Exception");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMergeSecurityPolicy_encryptionfail_null() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", "1");
        updateNameValueMap.put("authAlgorithm", AuthAlgorithmType.SHA1.getName());
        updateNameValueMap.put("encryptionAlgorithm", null);
        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();
        try {
            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Exception");
        }
    }

    @Test
    public void testMergeSecurityPolicy_name_invalid() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("name",
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", "1");
        updateNameValueMap.put("authAlgorithm", AuthAlgorithmType.SHA1.getName());
        updateNameValueMap.put("encryptionAlgorithm", null);
        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();
        try {
            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
            fail("Exception");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMergeSecurityPolicy_name_null() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("name", null);
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", "1");
        updateNameValueMap.put("authAlgorithm", AuthAlgorithmType.SHA1.getName());
        updateNameValueMap.put("encryptionAlgorithm", null);
        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();

        try {

            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Exception");
        }

    }

    @Test
    public void testMergeSecurityPolicy_description_invalid() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("name", "name");
        updateNameValueMap.put("description",
                "111111111111111111111111212331111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111222222222");
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", "1");
        updateNameValueMap.put("authAlgorithm", AuthAlgorithmType.SHA1.getName());
        updateNameValueMap.put("encryptionAlgorithm", null);
        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();

        try {

            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
            fail("Exception");
        } catch(ServiceException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testMergeSecurityPolicy_LifeTime_null() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("name", "name");
        updateNameValueMap.put("description", null);
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", null);
        updateNameValueMap.put("authAlgorithm", AuthAlgorithmType.SHA1.getName());
        updateNameValueMap.put("encryptionAlgorithm", EncryptionAlgorithmType.AES128.getName());

        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();

        try {

            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
            assertTrue(true);
        } catch(ServiceException e) {
            fail("Exception");
        }
    }

    @Test
    public void testMergeSecurityPolicy_LifeTime_invalid() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("name", "name");
        updateNameValueMap.put("description", null);
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", "xyz");
        updateNameValueMap.put("authAlgorithm", AuthAlgorithmType.SHA1.getName());
        updateNameValueMap.put("encryptionAlgorithm", EncryptionAlgorithmType.AES128.getName());

        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();

        try {

            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
            fail("Exception");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMergeSecurityPolicy_LifeTime_negative() {
        Map<String, String> updateNameValueMap = new HashMap<String, String>();
        updateNameValueMap.put("name", "name");
        updateNameValueMap.put("description", null);
        updateNameValueMap.put("pfs", PfsType.GROUP14.getName());
        updateNameValueMap.put("lifetime", "-1");
        updateNameValueMap.put("authAlgorithm", AuthAlgorithmType.SHA1.getName());
        updateNameValueMap.put("encryptionAlgorithm", EncryptionAlgorithmType.AES128.getName());

        SecurityPolicy mergedSecurityPolicy = new SecurityPolicy();

        try {

            MergePolicy.mergeSecurityPolicy(mergedSecurityPolicy, updateNameValueMap);
            fail("Exception");
        } catch(ServiceException e) {
            assertTrue(true);
        }
    }

}
