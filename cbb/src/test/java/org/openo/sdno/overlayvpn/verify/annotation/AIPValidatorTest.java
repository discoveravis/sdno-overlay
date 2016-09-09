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

import static org.junit.Assert.*;

import javax.validation.ConstraintValidatorContext;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

import org.junit.Test;
import org.openo.sdno.overlayvpn.verify.annotation.AIPValidator;
import org.openo.sdno.overlayvpn.verify.annotation.AIp;

/**
 * JUnit Test class for Annotation Validator Class of IpAddress.
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 15-Jun-2016
 */
public class AIPValidatorTest {

    @Mocked
    AIp mockAIp;

    /**
     * Initialize method test by passing mocked AIp object
     * 
     * @since SDNO 0.5
     */

    @Test
    public void testInitialize() {
        new Expectations() {

            {
                mockAIp.require();
                result = true;
                times = 1;
            }
        };
        AIPValidator aIpValidator = new AIPValidator();
        aIpValidator.initialize(mockAIp);
        new Verifications() {

            {
                mockAIp.require();
                times = 1;
            }
        };
    }

    /**
     * isValid method test for Normal cases.
     * 
     * @since SDNO 0.5
     */

    @Test
    public void testIsValidNormal() {
        AIPValidator aIpValidator = new AIPValidator();
        assertTrue(aIpValidator.isValid("10.1.1.1", null));
    }

    /**
     * isValid method test with empty IpAddress value for Abnormal cases.
     * 
     * @since SDNO 0.5
     */

    @Test
    public void testIsValidAbnormal() {
        AIPValidator aIpValidator = new AIPValidator();
        assertTrue(aIpValidator.isValid("", null));
    }

    @Test
    public void testIsValidAbnormalFasle() {
        new Expectations() {

            {
                mockAIp.require();
                result = true;
                times = 1;
            }
        };
        AIPValidator aIpValidator = new AIPValidator();
        aIpValidator.initialize(mockAIp);
        assertFalse(aIpValidator.isValid("", null));
    }
}
