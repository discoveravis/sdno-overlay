/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy;

import javax.validation.Valid;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.ModelBase;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Base Class of Mapping Policy.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public class BaseMappingPolicy extends ModelBase {

    /**
     * type
     */
    @AString(require = true, scope = "ipsec,gre,vxlan,gre_over_ipsec,vxlan_over_ipsec")
    private String type;

    /**
     * id of iKePolicy
     */
    @AUuid(require = false)
    protected String ikePolicyId;

    /**
     * id of ipsecPolicy
     */
    @AUuid(require = false)
    protected String ipsecPolicyId;

    /**
     * IkePolicy Object
     */
    @Valid
    @NONInvField
    protected IkePolicy ikePolicy;

    /**
     * IpSecPolicy Object
     */
    @Valid
    @NONInvField
    protected IpSecPolicy ipSecPolicy;

    /**
     * Copy basic attribute to this object.<br/>
     * 
     * @param mappingPolicy other mappingPolicy Object
     * @since SDNO 0.5
     */
    public void copyBasicData(BaseMappingPolicy mappingPolicy) {
        super.copyBasicData(mappingPolicy);
        this.type = mappingPolicy.getType();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIkePolicyId() {
        return ikePolicyId;
    }

    public void setIkePolicyId(String ikePolicyId) {
        this.ikePolicyId = ikePolicyId;
    }

    public String getIpsecPolicyId() {
        return ipsecPolicyId;
    }

    public void setIpsecPolicyId(String ipsecPolicyId) {
        this.ipsecPolicyId = ipsecPolicyId;
    }

    public IkePolicy getIkePolicy() {
        return this.ikePolicy;
    }

    public void setIkePolicy(IkePolicy ikePolicy) {
        this.ikePolicy = ikePolicy;
    }

    public IpSecPolicy getIpSecPolicy() {
        return this.ipSecPolicy;
    }

    public void setIpSecPolicy(IpSecPolicy ipSecPolicy) {
        this.ipSecPolicy = ipSecPolicy;
    }

}
