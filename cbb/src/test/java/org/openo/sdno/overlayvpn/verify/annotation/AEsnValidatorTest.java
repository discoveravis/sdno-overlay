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

package org.openo.sdno.overlayvpn.verify.annotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/*
 * Junit Test case to test the com.huawei.netmatrix.cbb.overlayvpn.verify.annotation.AEsnValidator class
 */
public class AEsnValidatorTest {

    @Mocked
    AEsn mockAEsn;

    /*
     * To test the initialize() method.
     */
    @Test
    public void testInitialize() {
        new Expectations() {

            {
                mockAEsn.require();
                result = true;
            }
        };
        AEsnValidator aEsnValidator = new AEsnValidator();
        aEsnValidator.initialize(mockAEsn);
        new Verifications() {

            {
                mockAEsn.require();
                times = 1;
            }
        };
    }

    /*
     * To test the isValid() method by passing a valid value.
     */
    @Test
    public void testIsValidWithValidValue() {
        String esnValue = "ESN10000000000000000";
        AEsnValidator aEsnValidator = new AEsnValidator();
        assertTrue(aEsnValidator.isValid(esnValue, null));
    }

    /*
     * To test the isValid() method by passing an invalid value.
     */
    @Test
    public void testIsValidWithInvalidValue() {
        String esnValue = "ESN100000000000";
        AEsnValidator aEsnValidator = new AEsnValidator();
        assertFalse(aEsnValidator.isValid(esnValue, null));
    }

    /*
     * To test the isValid() method by passing an empty String.
     */
    @Test
    public void testIsValidWithEmptyString() {
        String esnValue = "";
        AEsnValidator aEsnValidator = new AEsnValidator();
        assertTrue(aEsnValidator.isValid(esnValue, null));
    }

    /*
     * To test the isValid() method by passing an empty String and setting the value for
     * 'isRequired' as true.
     */
    @Test
    public void testIsValidWithEmptyStringWithIsRequired() {
        new Expectations() {

            {
                mockAEsn.require();
                result = true;
            }
        };
        AEsnValidator aEsnValidator = new AEsnValidator();
        aEsnValidator.initialize(mockAEsn);
        assertFalse(aEsnValidator.isValid(null, null));

    }

}
