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
 * Junit Test case to test the com.huawei.netmatrix.cbb.overlayvpn.verify.annotation.AIntValidator class
 */
public class AIntValidatorTest {

    @Mocked
    AInt mockAInt;

    /*
     * To test the initialize() method.
     */
    @Test
    public void testInitialize() {
        new Expectations() {

            {
                mockAInt.min();
                result = 0x80000000;
                mockAInt.max();
                result = 0x7fffffff;
                mockAInt.require();
                result = true;
            }
        };
        AIntValidator aIntValidator = new AIntValidator();
        aIntValidator.initialize(mockAInt);
        new Verifications() {

            {
                mockAInt.min();
                times = 1;
                mockAInt.max();
                times = 1;
                mockAInt.require();
                times = 1;
            }
        };

    }

    /*
     * To test the isValid() method by passing a valid value as a String.
     */
    @Test
    public void testIsValidWithValidString() {
        AIntValidator aIntValidator = new AIntValidator();
        String stringValue = "178";
        assertTrue(aIntValidator.isValid(stringValue, null));
    }

    /*
     * To test the isValid() method by passing an invalid value as a String.
     */
    @Test
    public void testIsValidWithInValidString() {
        AIntValidator aIntValidator = new AIntValidator();
        String stringValue = "178TEST";
        assertFalse(aIntValidator.isValid(stringValue, null));
    }

    /*
     * To test the isValid() method by passing a value which is out of range.
     */
    @Test
    public void testIsValidWithInValidStringOutOfRange() {
        AIntValidator aIntValidator = new AIntValidator();
        String stringValue = "9333372036854775807";
        assertFalse(aIntValidator.isValid(stringValue, null));
    }

    /*
     * To test the isValid() method by passing a Integer value.
     */
    @Test
    public void testIsValidWithValidInt() {
        AIntValidator aIntValidator = new AIntValidator();
        Integer intValue = 178;
        assertTrue(aIntValidator.isValid(intValue, null));
    }

    /*
     * To test the isValid() method by passing a Integer value which is greater than the specified
     * range.
     */
    @Test
    public void testIsValidWithIntOutOfMaxRange() {
        new Expectations() {

            {
                mockAInt.min();
                result = -255;
                mockAInt.max();
                result = 255;
                mockAInt.require();
                result = true;
            }
        };
        AIntValidator aIntValidator = new AIntValidator();
        aIntValidator.initialize(mockAInt);
        Integer intValue = 355;
        assertFalse(aIntValidator.isValid(intValue, null));
    }

    /*
     * To test the isValid() method by passing a Integer value which is less than the specified
     * range.
     */
    @Test
    public void testIsValidWithIntOutOfMinRange() {
        new Expectations() {

            {
                mockAInt.min();
                result = -255;
                mockAInt.max();
                result = 255;
                mockAInt.require();
                result = true;
            }
        };
        AIntValidator aIntValidator = new AIntValidator();
        aIntValidator.initialize(mockAInt);
        Integer intValue = -355;
        assertFalse(aIntValidator.isValid(intValue, null));
    }
}
