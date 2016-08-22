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

package org.openo.sdno.overlayvpn.security.tenant;

import org.openo.sdno.overlayvpn.log.inf.IOperObject;
import org.openo.sdno.overlayvpn.model.tenant.TenantMO;

/**
 * Class of Tenant Message.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public class TenantMsg implements IOperObject {

    private TenantMO tenantMO;

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    public TenantMsg() {
        // empty construction
    }

    /**
     * Get Operation name.<br/>
     * 
     * @return
     * @since SDNO 0.5
     */
    @Override
    public String queryOperName() {
        return tenantMO.getName();
    }

    /**
     * Get Operation Detail.<br/>
     * 
     * @return
     * @since SDNO 0.5
     */
    @Override
    public String queryOperDetail() {
        return tenantMO.getName();
    }

    public TenantMO getTenantMO() {
        return tenantMO;
    }

    public void setTenantMO(TenantMO tenantMO) {
        this.tenantMO = tenantMO;
    }
}
