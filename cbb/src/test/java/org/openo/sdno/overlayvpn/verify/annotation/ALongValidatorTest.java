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
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

import org.junit.Test;
import org.openo.sdno.overlayvpn.verify.annotation.ALong;
import org.openo.sdno.overlayvpn.verify.annotation.ALongValidator;

/*
 * Junit Test case to test the com.huawei.netmatrix.cbb.overlayvpn.verify.annotation.ALongValidator class
 */
public class ALongValidatorTest {

    @Mocked
    ALong mockALong;

    /*
     * To test the initialize() method.
     */
    @Test
    public void testInitialize() {
        new Expectations() {

            {
                mockALong.min();
                result = 0x8000000000000000L;
                mockALong.max();
                result = 0x7fffffffffffffffL;
                mockALong.require();
                result = true;
            }
        };
        ALongValidator aLongValidator = new ALongValidator();
        aLongValidator.initialize(mockALong);
        new Verifications() {

            {
                mockALong.min();
                times = 1;
                mockALong.max();
                times = 1;
                mockALong.require();
                times = 1;
            }
        };
    }

    /*
     * To test the isValid() method by passing the value as a String.
     */
    @Test
    public void testIsValidWithValidString() {
        ALongValidator aLongValidator = new ALongValidator();
        String stringValue = "17865467876";
        assertTrue(aLongValidator.isValid(stringValue, null));
    }

    /*
     * To test the isValid() method by passing an invalid value as a String.
     */
    @Test
    public void testIsValidWithInValidString() {
        ALongValidator aLongValidator = new ALongValidator();
        String stringValue = "17865467876TEST";
        assertFalse(aLongValidator.isValid(stringValue, null));
    }

    /*
     * To test the isValid() method by passing a out of range value as a String.
     */
    @Test
    public void testIsValidWithInValidStringOutOfRange() {
        ALongValidator aLongValidator = new ALongValidator();
        String stringValue = "9333372036854775807";
        assertFalse(aLongValidator.isValid(stringValue, null));
    }

    /*
     * To test the isValid() method by passing a Long value.
     */
    @Test
    public void testIsValidWithValidLong() {
        ALongValidator aLongValidator = new ALongValidator();
        Long longValue = 17865467876L;
        assertTrue(aLongValidator.isValid(longValue, null));
    }

    /*
     * To test the isValid() method by passing a Long value which is greater than the specified
     * range.
     */
    @Test
    public void testIsValidWithLongOutOfMaxRange() {
        new Expectations() {

            {
                mockALong.min();
                result = -255L;
                mockALong.max();
                result = 255L;
                mockALong.require();
                result = true;
            }
        };
        ALongValidator aLongValidator = new ALongValidator();
        aLongValidator.initialize(mockALong);
        Long longValue = 355L;
        assertFalse(aLongValidator.isValid(longValue, null));
    }

    /*
     * To test the isValid() method by passing a Long value which is less than specified range.
     */
    @Test
    public void testIsValidWithLongOutOfMinRange() {
        new Expectations() {

            {
                mockALong.min();
                result = -255L;
                mockALong.max();
                result = 255L;
                mockALong.require();
                result = true;
            }
        };
        ALongValidator aLongValidator = new ALongValidator();
        aLongValidator.initialize(mockALong);
        Long longValue = -355L;
        assertFalse(aLongValidator.isValid(longValue, null));
    }

}
