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

package org.openo.sdno.overlayvpn.servicemodel.base;

import java.util.List;

/**
 * The filter rule of policy route.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 10, 2017
 */
public class FilterAction {

    private List<PolicyRule> ruleList;

    private PolicyAction action;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public FilterAction() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @param ruleList PolicyRule list to set
     * @param action PolicyAction to set
     * @since SDNO 0.5
     */
    public FilterAction(List<PolicyRule> ruleList, PolicyAction action) {
        super();
        this.ruleList = ruleList;
        this.action = action;
    }

    public List<PolicyRule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<PolicyRule> ruleList) {
        this.ruleList = ruleList;
    }

    public PolicyAction getAction() {
        return action;
    }

    public void setAction(PolicyAction action) {
        this.action = action;
    }

}
