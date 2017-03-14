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

package org.openo.sdno.overlayvpn.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;

public class ModelUtilsTest {

    @Test
    public void testNewModel() throws ServiceException {
        assertEquals("test_uuid", ModelUtils.newModel(ServiceModel.class, "test_uuid").getId());
    }

    @Test
    public void testNewInstance() throws ServiceException {
        assertTrue(ModelUtils.newInstance(ServiceModel.class) != null);
    }

    @Test
    public void testCopyAttributes() throws ServiceException {
        ServiceModel destModel = new ServiceModel();
        ServiceModel srcModel = new ServiceModel();
        srcModel.setId("test_uuid");
        srcModel.setActionState("normal");
        srcModel.setActiveStatus("active");
        srcModel.setCreatetime(0L);
        srcModel.setDeployStatus("deploy");
        srcModel.setDescription("Description");
        srcModel.setName("name");
        srcModel.setRunningStatus("up");
        srcModel.setTenantId("tenantId");
        srcModel.setUpdatetime(0L);
        ModelUtils.copyAttributes(srcModel, destModel);
        assertEquals(destModel.getActionState(), srcModel.getActionState());
        assertEquals(destModel.getActiveStatus(), srcModel.getActiveStatus());
        assertEquals(destModel.getCreatetime(), srcModel.getCreatetime());
        assertEquals(destModel.getDeployStatus(), srcModel.getDeployStatus());
        assertEquals(destModel.getDescription(), srcModel.getDescription());
        assertEquals(destModel.getId(), srcModel.getId());
        assertEquals(destModel.getName(), srcModel.getName());
        assertEquals(destModel.getRunningStatus(), srcModel.getRunningStatus());
        assertEquals(destModel.getTenantId(), srcModel.getTenantId());
        assertEquals(destModel.getUpdatetime(), srcModel.getUpdatetime());
    }

}
