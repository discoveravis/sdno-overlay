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

package org.openo.sdno.overlayvpn.sbi.cpe;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.resthelper.RestfulProxy;
import org.openo.sdno.overlayvpn.model.netmodel.localsite.AdapterDeviceNetwork;

import mockit.Mock;
import mockit.MockUp;

public class NetworkAdapterServiceTest {

    NetworkAdapterService nwAdaptServ = new NetworkAdapterService();

    @Test
    public void testCreateNetworkFailureOnSucc() {
        new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse post(String url, RestfulParametes restParametes) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(HttpCode.RESPOND_OK);
                return resp;
            }
        };
        String ctrlUuid = "";
        String deviceid = "";
        List<AdapterDeviceNetwork> networkList = new ArrayList<AdapterDeviceNetwork>();
        try {
            nwAdaptServ.createNetwork(ctrlUuid, deviceid, networkList);
            fail("Should not be here");
        } catch(Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCreateNetworkFailureOnInvalidParam() {
        MockUp<RestfulProxy> mocitTo = new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse post(String url, RestfulParametes restParametes) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(HttpCode.NOT_FOUND);
                return resp;
            }
        };
        String ctrlUuid = "";
        String deviceid = "";
        List<AdapterDeviceNetwork> networkList = new ArrayList<AdapterDeviceNetwork>();
        try {
            nwAdaptServ.createNetwork(ctrlUuid, deviceid, networkList);
            fail("Should not be here");
        } catch(ServiceException e) {
            assertTrue(true);
        }
        mocitTo.tearDown();
    }

    @Test
    public void testCreateNetworkFailureOnnullResp() {
        MockUp<RestfulProxy> mocitTo = new MockUp<RestfulProxy>() {

            @Mock
            public RestfulResponse post(String url, RestfulParametes restParametes) {
                RestfulResponse resp = new RestfulResponse();
                resp.setStatus(HttpCode.NOT_FOUND);
                return null;
            }
        };
        String ctrlUuid = "";
        String deviceid = "";
        List<AdapterDeviceNetwork> networkList = new ArrayList<AdapterDeviceNetwork>();
        try {
            nwAdaptServ.createNetwork(ctrlUuid, deviceid, networkList);
            fail("Should not be here");
        } catch(ServiceException e) {
            assertTrue(true);
        }

        mocitTo.tearDown();
    }
}
