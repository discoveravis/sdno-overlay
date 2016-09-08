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

package org.openo.sdno.overlayvpnservice.test;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpnservice.test.mocoserver.OverlayVpnSuccessServer;
import org.openo.sdno.testframework.testmanager.TestManager;

public class OverlayVpnFailTest extends TestManager {

    private OverlayVpnSuccessServer overlayVpnAdapterServer = new OverlayVpnSuccessServer();

    @Before
    public void setup() throws ServiceException {
        overlayVpnAdapterServer.start();
    }

    @After
    public void tearDown() {
        overlayVpnAdapterServer.stop();
    }

    @Test
    public void CreateVxlanFailTest1() throws ServiceException {
        execTestCase(new File("src/main/resources/createvxlanfail1.json"));
    }

    @Test
    public void CreateVxlanFailTest2() throws ServiceException {
        execTestCase(new File("src/main/resources/createvxlanfail1.json"));
    }

}
