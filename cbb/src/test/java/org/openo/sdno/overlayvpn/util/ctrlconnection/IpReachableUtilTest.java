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

package org.openo.sdno.overlayvpn.util.ctrlconnection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.overlayvpn.util.ctrlconnection.IpReachableUtil;

/**
 * IpReachableUtil test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class IpReachableUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testPing() {
        new MockUp<Runtime>() {

            @Mock
            public Process exec(String command) throws IOException {

                return new PriProcess();
            }

        };

        assertTrue(IpReachableUtil.ping("1.1.1.1"));
    }

    @Test
    public void testPing2() {
        new MockUp<Runtime>() {

            @Mock
            public Process exec(String command) throws IOException {

                return new PriProcess();
            }

        };

        assertFalse(IpReachableUtil.ping("1.1.1.1+++"));
    }

    @Test
    public void testPing3() {
        new MockUp<Runtime>() {

            @Mock
            public Process exec(String command) throws IOException {

                return new PriProcess();
            }

        };

        new MockUp<PriProcess>() {

            @Mock
            public int waitFor() throws InterruptedException {

                return 1;
            }

        };

        assertFalse(IpReachableUtil.ping("1.1.1.1"));
    }

    @Test
    public void testIsPrivateIp() {
        assertTrue(IpReachableUtil.isPrivateIp("10.0.0.1"));
    }

    @Test
    public void testIsPrivateIp2() {
        assertTrue(IpReachableUtil.isPrivateIp("172.16.0.1"));
    }

    @Test
    public void testIsPrivateIp3() {
        assertTrue(IpReachableUtil.isPrivateIp("192.168.0.1"));
    }

    @Test
    public void testIsPrivateIp4() {
        assertFalse(IpReachableUtil.isPrivateIp("192.100.0.1"));
    }

    private class PriProcess extends Process {

        @Override
        public OutputStream getOutputStream() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public InputStream getInputStream() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public InputStream getErrorStream() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int waitFor() throws InterruptedException {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int exitValue() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void destroy() {
            // TODO Auto-generated method stub

        }

    }
}
