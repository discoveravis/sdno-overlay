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

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;

public class ModelServiceUtilTest {

    @Test
    public void testGetUuidListEmpty() throws ServiceException {
        assertTrue(CollectionUtils.isEmpty(ModelServiceUtil.getUuidList(null)));
    }

    @Test
    public void testUpdateDeployStateEmpty() throws ServiceException {
        ModelServiceUtil.updateDeployState(null, null);
    }

    @Test
    public void testUpdateActionStateEmpty() throws ServiceException {
        ModelServiceUtil.updateActionState(null, null);
    }

    @Test
    public void testIsOnGoingCreating() throws ServiceException {
        ServiceModel model = new ServiceModel();
        model.setActionState("creating");
        model.setCreatetime(System.currentTimeMillis() / 1000L);
        assertTrue(ModelServiceUtil.isOnGoing(model));
    }

    @Test
    public void testIsOnGoingUpdating() throws ServiceException {
        ServiceModel model = new ServiceModel();
        model.setActionState("updating");
        model.setUpdatetime(System.currentTimeMillis() / 1000L);
        assertTrue(ModelServiceUtil.isOnGoing(model));
    }

    @Test
    public void testHidUneditableFieldEmpty() throws ServiceException {
        ModelServiceUtil.hidUneditableField(null);
    }

    @Test
    public void testHidNonSbiFieldEmpty() throws ServiceException {
        ModelServiceUtil.hidNonSbiField(null);
    }

    @Test
    public void testInitUuidEmpty() throws ServiceException {
        ModelServiceUtil.initUuid(null);
    }

}
