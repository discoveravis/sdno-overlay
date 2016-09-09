/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.model.tenant;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * Class of tenant Model.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public class TenantMO {

    /**
     * Object UUID
     */
    private String uuid;

    /**
     * tenant name
     */
    @NotNull
    @Max(255)
    private String name;

    /**
     * Tenant id
     */
    @NotNull
    @Max(255)
    private String id;

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param uuid Object UUID
     */
    public TenantMO(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    public TenantMO() {
        // empty construction
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        TenantMO other = (TenantMO)obj;
        if(id == null) {
            if(other.id != null) {
                return false;
            }
        } else if(!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TenantMO [uuid=" + uuid + ", name=" + name + ", id=" + id + "]";
    }

}
