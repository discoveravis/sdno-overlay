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

/**
 * JUnit Test class for Annotation Validator Class of IpMask.
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 15-Jun-2016
 */

public class AIpMaskValidatorTest {

    @Mocked
    AIpMask mockAIpMask;

    /**
     * Initialize method test by passing mocked IpMask object
     * 
     * @since SDNO 0.5
     */

    @Test
    public void testInitialize() {
        new Expectations() {

            {
                mockAIpMask.require();
                result = true;
                times = 1;
            }
        };
        AIpMaskValidator aIpMaskValidator = new AIpMaskValidator();
        aIpMaskValidator.initialize(mockAIpMask);
        new Verifications() {

            {
                mockAIpMask.require();
                times = 1;
            }
        };
    }

    /**
     * isValid method test with different IpMask values for Normal cases.
     * 
     * @since SDNO 0.5
     */

    @Test
    public void testIsValidNormalTrue() {
        AIpMaskValidator aIpMaskValidator = new AIpMaskValidator();
        assertTrue(aIpMaskValidator.isValid("0.0.0.0/0", null));

    }

    @Test
    public void testIsValidNormal() {
        AIpMaskValidator aIpMaskValidator = new AIpMaskValidator();
        assertFalse(aIpMaskValidator.isValid("10.1.1.1", null));
    }

    /**
     * isValid method test with empty IpMask value for Abnormal cases.
     * 
     * @since SDNO 0.5
     */

    @Test
    public void testIsValidAbnormal() {
        AIpMaskValidator aIpMaskValidator = new AIpMaskValidator();
        assertTrue(aIpMaskValidator.isValid("", null));
    }

    @Test
    public void testIsValidAbnormalFasle() {
        new Expectations() {

            {
                mockAIpMask.require();
                result = true;
                times = 1;
            }
        };
        AIpMaskValidator aIpMaskValidator = new AIpMaskValidator();
        aIpMaskValidator.initialize(mockAIpMask);
        assertFalse(aIpMaskValidator.isValid("", null));
    }

}
