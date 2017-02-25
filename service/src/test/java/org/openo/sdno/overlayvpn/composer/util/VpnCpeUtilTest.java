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

package org.openo.sdno.overlayvpn.composer.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.servicemodel.template.Spec;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateObject;

public class VpnCpeUtilTest {

    @Test
    public void testGetCpeInterfaceNull() throws ServiceException {
        assertTrue(VpnCpeUtil.getCpeInterface(null, null, null) == null);
    }

    @Test
    public void testGetCpeInterface() throws ServiceException {
        Template template = new Template();
        assertTrue("test_key".equals(VpnCpeUtil.getCpeInterface(template, null, "test_key")));
    }

    @Test(expected = ServiceException.class)
    public void testGetCpeInterfaceException() throws ServiceException {
        TemplateObject templateObject = new TemplateObject();

        List<Spec> specList = new ArrayList<>();
        Spec spec = new Spec();
        spec.setName(ParamConsts.CLOUDCPEINFO);
        specList.add(spec);

        templateObject.setSpecList(specList);

        Template template = new Template(templateObject);

        VpnCpeUtil.getCpeInterface(template, null, ParamConsts.CLOUDCPEINFO);
    }

}
