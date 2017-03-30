/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.frame.dao;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryFilterEntity;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;

public class ModelInvUtilTest {

    @Test
    public void testBuildComlexFilter() throws ServiceException {
        List<String> ports = new ArrayList<>();
        ports.add("test_port");
        NbiVpnGateway model = new NbiVpnGateway();
        model.setId("test_uuid");
        model.setPorts(ports);
        BatchQueryFilterEntity result = ModelInvUtil.buildComlexFilter(model);
        assertFalse(result.getFilterDsc().isEmpty());
        assertFalse(result.getFilterData().isEmpty());
    }

}
