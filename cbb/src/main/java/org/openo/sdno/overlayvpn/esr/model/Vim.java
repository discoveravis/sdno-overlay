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

package org.openo.sdno.overlayvpn.esr.model;

/**
 * Model Class of ESR VIM.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-9-21
 */
public class Vim extends EsrBaseModel {

    private String vimId;

    private String tenant;

    private String domain;

    /**
     * @return Returns the vimId.
     */
    public String getVimId() {
        return vimId;
    }

    /**
     * @param vimId The vimId to set.
     */
    public void setVimId(String vimId) {
        this.vimId = vimId;
    }

    /**
     * @return Returns the tenant.
     */
    public String getTenant() {
        return tenant;
    }

    /**
     * @param tenant The tenant to set.
     */
    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    /**
     * @return Returns the domain.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain The domain to set.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

}
