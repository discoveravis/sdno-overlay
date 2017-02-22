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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.servicemodel.enums.RuleAction;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;

/**
 * Policy route rule model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 10, 2017
 */
public class PolicyRule {

    private String policy;

    private IP srcIp;

    private IP desIp;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public PolicyRule() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @param srcIp The source IP to set
     * @param desIp The destination IP to set
     * @param action The RuleAction to set
     * @since SDNO 0.5
     */
    public PolicyRule(IP srcIp, IP desIp, RuleAction action) {
        super();
        this.policy = action != null ? action.getAction() : RuleAction.PERMIT.getAction();
        this.setSrcIp(srcIp);
        this.setDesIp(desIp);
    }

    /**
     * Whether the rule match this rule.<br>
     * 
     * @param rule The PolicyRule
     * @return true when the two rules matched,otherwise is false
     * @throws ServiceException when translate to json failed
     * @since SDNO 0.5
     */
    public boolean match(PolicyRule rule) throws ServiceException {
        if(rule == null) {
            return false;
        }
        return JsonUtils.toJson(this).equals(JsonUtils.toJson(rule));
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public IP getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(IP srcIp) {
        this.srcIp = srcIp;
    }

    public IP getDesIp() {
        return desIp;
    }

    public void setDesIp(IP desIp) {
        this.desIp = desIp;
    }

}
