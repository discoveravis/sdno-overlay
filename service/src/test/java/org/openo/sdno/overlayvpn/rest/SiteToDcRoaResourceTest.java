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

package org.openo.sdno.overlayvpn.rest;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDcNbi;
import org.openo.sdno.overlayvpn.service.impl.site2dc.SiteToDCSvcImpl;

public class SiteToDcRoaResourceTest {

    static SiteToDcRoaResource roa = new SiteToDcRoaResource();
    static {
        SiteToDCSvcImpl site = new SiteToDCSvcImpl();
        roa.setService(site);
    }

    @Test(expected = Exception.class)
    public void testCreateException() throws ServiceException {

        SiteToDcRoaResource roa = new SiteToDcRoaResource();
        SiteToDcNbi dc = new SiteToDcNbi();
        dc.setName("test");

        roa.create(null, null, JsonUtil.toJson(dc));
        fail("Exception not occured");
    }

    @Test(expected = Exception.class)
    public void testQueryException() throws ServiceException {
        roa.query(null, null, "12345");
        fail("Exception not occured");
    }

}
