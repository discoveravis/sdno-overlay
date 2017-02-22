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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.servicemodel.base.FilterAction;
import org.openo.sdno.overlayvpn.servicemodel.base.PolicyRule;
import org.openo.sdno.overlayvpn.servicemodel.enums.ActionType;
import org.openo.sdno.overlayvpn.servicemodel.sbi.PolicyRoute;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class to deal with policy route.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 12, 2017
 */
public class VpnPolicyRouteUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnPolicyRouteUtil.class);

    private static final Integer RETRY_INTEVAL = 1000;

    private static final Integer RETRY_TIME = 60;

    private static BaseDao<PolicyRoute> policyDao = new BaseDao<PolicyRoute>();

    private VpnPolicyRouteUtil() {
        super();
    }

    /**
     * Process the policy route list that to be updated.<br>
     * 
     * @param routes The policy route list to be check
     * @return The policy route list that to be updated
     * @throws ServiceException when build failed
     * @since SDNO 0.5
     */
    public static List<PolicyRoute> processPolicyRouteUpdate(List<PolicyRoute> routes) throws ServiceException {
        List<PolicyRoute> updates = new ArrayList<>();
        if(CollectionUtils.isEmpty(routes)) {
            return updates;
        }
        for(PolicyRoute route : routes) {
            PolicyRoute update = processPolicyRouteUpdate(route);
            if(update != null) {
                updates.add(update);
            }
        }
        return updates;
    }

    /**
     * Wait the policy finish the on going status.<br>
     * 
     * @param uuids The policy route uuid list
     * @return true when the policy route exist and is not on going,otherwise is false
     * @throws ServiceException when query policy route fialed
     * @since SDNO 0.5
     */
    public static boolean waitDone(List<String> uuids) throws ServiceException {
        if(CollectionUtils.isEmpty(uuids)) {
            return false;
        }
        for(int i = 0; i < RETRY_TIME; i++) {
            List<PolicyRoute> queryDatas = policyDao.query(uuids, PolicyRoute.class);
            if(CollectionUtils.isEmpty(queryDatas)) {
                return false;
            }
            boolean onGoing = false;
            for(PolicyRoute queryData : queryDatas) {
                if(ModelServiceUtil.isOnGoing(queryData)) {
                    onGoing = true;
                    break;
                }
            }
            if(onGoing) {
                waitNext();
            } else {
                return true;
            }
        }
        return true;
    }

    /**
     * Wait function,the maximum time to wait in milliseconds is 1000.<br>
     * 
     * @since SDNO 0.5
     */
    private static void waitNext() {
        Object lockObject = new Object();
        try {
            synchronized(lockObject) {
                boolean isRun = true;
                while(isRun) {
                    lockObject.wait(RETRY_INTEVAL);
                    isRun = false;
                }
            }
        } catch(InterruptedException e) {
            LOGGER.warn("Sleep interrupted " + e.toString());
        }
    }

    private static PolicyRoute processPolicyRouteUpdate(PolicyRoute route) throws ServiceException {
        ActionType updateAction = route.getUpdateAction();
        if(updateAction == null) {
            return route;
        }
        FilterAction filterAction = JsonUtils.fromJson(route.getFilterAction(), FilterAction.class);
        if(filterAction == null || CollectionUtils.isEmpty(filterAction.getRuleList())) {
            return null;
        }
        PolicyRoute dbRoute = policyDao.query(route.getId(), PolicyRoute.class);
        if(dbRoute == null) {
            return null;
        }
        for(PolicyRule rule : filterAction.getRuleList()) {
            if(ActionType.CREATE == updateAction) {
                dbRoute.addRule(rule);
            } else if(ActionType.DELETE == updateAction) {
                dbRoute.deleteRule(rule);
            }
        }
        dbRoute.setActionState(null);
        return dbRoute;
    }
}
