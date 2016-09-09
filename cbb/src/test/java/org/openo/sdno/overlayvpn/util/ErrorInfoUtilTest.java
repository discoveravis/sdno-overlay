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

package org.openo.sdno.overlayvpn.util;

import static org.junit.Assert.assertEquals;
import mockit.Mock;
import mockit.MockUp;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.overlayvpn.util.ErrorInfoUtil;
import org.openo.sdno.overlayvpn.verify.annotation.AErrorInfo;
import org.openo.sdno.resource.ResourceUtil;

/**
 * ErrorInfoUtil test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-13
 */
public class ErrorInfoUtilTest {

    @Before
    public void setUp() throws Exception {
        new MockUp<ResourceUtil>() {

            @Mock
            public String getMessage(String str) {
                return "Test_ConvertErrorMessage";
            }
        };
    }

    @Test
    public void testConvertErrorMessage() {
        String[] errInfo = {"Test error info."};
        String result = ErrorInfoUtil.convertErrorMessage(TestErrorInfoUtil.class, errInfo);
        assertEquals("Test_ConvertErrorMessage", result);
    }

    @Test
    public void testConvertErrorMessage2() {
        String[] errInfo = {};
        String result = ErrorInfoUtil.convertErrorMessage(TestErrorInfoUtil.class, errInfo);
        assertEquals("Test_ConvertErrorMessage", result);
    }

    @Test
    public void testConvertErrorMessage3() {
        String[] errInfo = {"errInfo: Just test."};
        String result = ErrorInfoUtil.convertErrorMessage(TestErrorInfoUtil.class, errInfo);
        assertEquals("Test_ConvertErrorMessage", result);
    }

    @Test
    public void testConvertErrorMessage4() {
        String[] errInfo = {"errInfo: Just test."};
        String result = ErrorInfoUtil.convertErrorMessage(AnotherTestErrorInfo.class, errInfo);
        assertEquals("Test_ConvertErrorMessage Test_ConvertErrorMessage", result);
    }

    private class TestErrorInfoUtil {

        @AErrorInfo
        private String errInfo;

        public TestErrorInfoUtil() {
            errInfo = "default error info.";
        }

        /**
         * @return Returns the errInfo.
         */
        public String getErrInfo() {
            return errInfo;
        }

        /**
         * @param errInfo The errInfo to set.
         */
        public void setErrInfo(String errInfo) {
            this.errInfo = errInfo;
        }
    }

    private class AnotherTestErrorInfo {

        @AErrorInfo(info = "info")
        private String errInfo;

        public AnotherTestErrorInfo() {
            errInfo = "default error info.";
        }

        /**
         * @return Returns the errInfo.
         */
        public String getErrInfo() {
            return errInfo;
        }

        /**
         * @param errInfo The errInfo to set.
         */
        public void setErrInfo(String errInfo) {
            this.errInfo = errInfo;
        }
    }
}
