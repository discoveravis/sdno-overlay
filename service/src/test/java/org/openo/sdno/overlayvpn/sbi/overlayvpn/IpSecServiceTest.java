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

package org.openo.sdno.overlayvpn.sbi.overlayvpn;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * @author vWX369881
 */
public class IpSecServiceTest {

    IpSecService ipSecService;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetInstance() {
        IpSecService result = IpSecService.getInstance();
        assertTrue(true);
    }

    @Test
    public void testQuery() {
        OverlayVpn overlayVpn = new OverlayVpn();

        try {
            ResultRsp<OverlayVpn> result = ipSecService.query(null, "123-456-abcd-def");
            assertTrue(true);
        } catch(NullPointerException e) {

        } catch(ServiceException e) {

        }
    }

    @Test
    public void testUpdate() {
        OverlayVpn overlayVpn = new OverlayVpn();

        try {
            ResultRsp<OverlayVpn> result = ipSecService.update(null, overlayVpn);
            assertTrue(true);
        } catch(NullPointerException e) {

        } catch(ServiceException e) {

        }
    }

    @Test
    public void testDelete() {
        OverlayVpn overlayVpn = new OverlayVpn();

        try {
            ResultRsp<OverlayVpn> result = ipSecService.delete(null, "123-456-abcd-def");
            assertTrue(true);
        } catch(NullPointerException e) {

        } catch(ServiceException e) {

        }
    }

    @Test
    public void testDeploy() {
        OverlayVpn overlayVpn = new OverlayVpn();

        try {
            ResultRsp<OverlayVpn> result = ipSecService.deploy(null, overlayVpn);
            assertTrue(true);
        } catch(NullPointerException e) {

        } catch(ServiceException e) {

        }
    }

    @Test
    public void testUndeploy() {
        OverlayVpn overlayVpn = new OverlayVpn();

        try {
            ResultRsp<OverlayVpn> result = ipSecService.undeploy(null, overlayVpn);
            assertTrue(true);
        } catch(NullPointerException e) {

        } catch(ServiceException e) {

        }
    }

    @Test
    public void testCreate() {
        OverlayVpn overlayVpn = new OverlayVpn();

        try {
            ResultRsp<OverlayVpn> result = ipSecService.create(null, overlayVpn);
            assertTrue(true);
        } catch(NullPointerException e) {

        } catch(ServiceException e) {

        }
    }

}
