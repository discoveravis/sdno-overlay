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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.servicemodel.base.FilterAction;
import org.openo.sdno.overlayvpn.servicemodel.base.PolicyAction;
import org.openo.sdno.overlayvpn.servicemodel.sbi.PolicyRoute;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;

public class VpnPolicyRouteUtilTest {

    @Test
    public void testWaitDoneEmpty() throws ServiceException {
        assertFalse(VpnPolicyRouteUtil.waitDone(null));
    }

    @Test
    public void testProcessPolicyRouteUpdateEmpty() throws ServiceException {
        assertTrue(CollectionUtils.isEmpty(VpnPolicyRouteUtil.processPolicyRouteUpdate(null)));
    }

    @Test
    public void testProcessPolicyRouteUpdate2() throws ServiceException {
        List<PolicyRoute> routes = new ArrayList<>();
        PolicyRoute route = new PolicyRoute();
        routes.add(route);
        assertTrue(CollectionUtils.isEmpty(VpnPolicyRouteUtil.processPolicyRouteUpdate(routes)));
    }

    @Test
    public void testProcessPolicyRouteUpdate3() throws ServiceException {
        List<PolicyRoute> routes = new ArrayList<>();
        PolicyRoute route = new PolicyRoute();
        FilterAction filterAction = new FilterAction();
        PolicyAction policyAction = new PolicyAction();
        policyAction.setPolicy("test");
        filterAction.setAction(policyAction);
        route.setFilterAction(JsonUtils.toJson(filterAction));

        routes.add(route);
        assertTrue(CollectionUtils.isEmpty(VpnPolicyRouteUtil.processPolicyRouteUpdate(routes)));
    }

}
