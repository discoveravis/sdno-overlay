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

package org.openo.sdno.overlayvpn.verify.annotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/*
 * Junit Test case to test the com.huawei.netmatrix.cbb.overlayvpn.verify.annotation.AUuidValidator class
 */
public class AUuidValidatorTest {

    @Mocked
    AUuid mockAUuid;

    /*
     * To test the initialize() method.
     */
    @Test
    public void testInitialize() {
        new Expectations() {

            {
                mockAUuid.require();
                result = true;
            }
        };
        AUuidValidator auuidValidator = new AUuidValidator();
        auuidValidator.initialize(mockAUuid);
        new Verifications() {

            {
                mockAUuid.require();
            }
        };
    }

    /*
     * To test the isValid() method by passing an object of the type 'Object'.
     */
    @Test
    public void testIsValidObject() {
        Object object = new Object();
        AUuidValidator auuidValidator = new AUuidValidator();
        assertTrue(auuidValidator.isValid(object, null));
    }

    /*
     * To test the isValid() method by passing an object of the type String.
     */
    /*
     * @Test
     * public void testIsValidWithString() {
     * String stringValue = new String("Test");
     * AUuidValidator auuidValidator = new AUuidValidator();
     * assertTrue(auuidValidator.isValid(stringValue, null));
     * }
     */

    /*
     * To test the isValid() method by passing an object of the type String with empty value.
     */
    @Test
    public void testIsValidWithEmptyString() {
        String stringValue = new String("");
        AUuidValidator auuidValidator = new AUuidValidator();
        assertTrue(auuidValidator.isValid(stringValue, null));
    }

    /*
     * To test the isValid() method by passing null.
     */
    @Test
    public void testIsValidWithNull() {
        AUuidValidator auuidValidator = new AUuidValidator();
        assertTrue(auuidValidator.isValid(null, null));
    }

    /*
     * To test the isValid() method by passing null and setting the attribute 'isRequire' to true.
     */
    @Test
    public void testIsValidWithNullWithIsRequired() {
        new Expectations() {

            {
                mockAUuid.require();
                result = true;
            }
        };
        AUuidValidator auuidValidator = new AUuidValidator();
        auuidValidator.initialize(mockAUuid);
        assertFalse(auuidValidator.isValid(null, null));
    }

    /*
     * To test the isValid() method by passing an empty String and setting the attribute 'isRequire'
     * to true.
     */
    @Test
    public void testIsValidEmptyStringWithIsRequired() {
        new Expectations() {

            {
                mockAUuid.require();
                result = true;
            }
        };
        AUuidValidator auuidValidator = new AUuidValidator();
        auuidValidator.initialize(mockAUuid);
        String stringValue = new String("");
        assertFalse(auuidValidator.isValid(stringValue, null));
    }

}
