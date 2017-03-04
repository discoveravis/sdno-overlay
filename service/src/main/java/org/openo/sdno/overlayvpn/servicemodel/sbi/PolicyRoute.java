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

package org.openo.sdno.overlayvpn.servicemodel.sbi;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONSBIField;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseRoute;
import org.openo.sdno.overlayvpn.servicemodel.base.FilterAction;
import org.openo.sdno.overlayvpn.servicemodel.base.PolicyRule;
import org.openo.sdno.overlayvpn.servicemodel.enums.ActionType;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;

/**
 * The policy route model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
@MOResType(infoModelName = "net_policy_route")
public class PolicyRoute extends BaseRoute {

    private String trafficPolicyName;

    private String interfaceName;

    private String direction;

    private String filterAction;

    @NONInvField
    @NONSBIField
    @JsonIgnore
    private ActionType updateAction;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public PolicyRoute() {
        super();
    }

    /**
     * Add policy rule.<br>
     * 
     * @param rule The PolicyRule to add
     * @throws ServiceException when translating json failed
     * @since SDNO 0.5
     */
    public void addRule(PolicyRule rule) throws ServiceException {
        if(rule == null) {
            return;
        }
        FilterAction filterAction = JsonUtils.fromJson(this.filterAction, FilterAction.class);
        if(filterAction == null) {
            return;
        }
        if(filterAction.getRuleList() == null) {
            filterAction.setRuleList(new ArrayList<PolicyRule>());
        }
        for(PolicyRule baseRule : filterAction.getRuleList()) {
            if(baseRule != null && baseRule.match(rule)) {
                return;
            }
        }
        filterAction.getRuleList().add(0, rule);
        this.filterAction = JsonUtils.toJson(filterAction);
    }

    /**
     * Delete policy rule.<br>
     * 
     * @param rule The PolicyRule to delete
     * @throws ServiceException when translating json failed
     * @since SDNO 0.5
     */
    public void deleteRule(PolicyRule rule) throws ServiceException {
        if(rule == null) {
            return;
        }
        FilterAction filterAction = JsonUtils.fromJson(this.filterAction, FilterAction.class);
        if(filterAction == null) {
            return;
        }
        if(CollectionUtils.isEmpty(filterAction.getRuleList())) {
            return;
        }
        boolean removed = false;
        Iterator<PolicyRule> iterator = filterAction.getRuleList().iterator();
        while(iterator.hasNext()) {
            PolicyRule baseRule = iterator.next();
            if(baseRule != null && baseRule.match(rule)) {
                iterator.remove();
                removed = true;
            }
        }
        if(!removed) {
            return;
        }
        this.filterAction = JsonUtils.toJson(filterAction);
    }

    public String getTrafficPolicyName() {
        return trafficPolicyName;
    }

    public void setTrafficPolicyName(String trafficPolicyName) {
        this.trafficPolicyName = trafficPolicyName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFilterAction() {
        return filterAction;
    }

    public void setFilterAction(String filterAction) {
        this.filterAction = filterAction;
    }

    public ActionType getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(ActionType updateAction) {
        this.updateAction = updateAction;
    }
}
