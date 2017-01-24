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

package org.openo.sdno.overlayvpn.model.v2.cpe;

import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Model class of LocalCpe.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-4
 */
public class NbiLocalCpeModel extends UuidModel {

    @AString(min = 0, max = 128)
    private String name;

    @AString(min = 0, max = 36)
    private String esn;

    @AString(min = 0, max = 36)
    private String oldEsn;

    @AUuid
    private String tenantId;

    @AUuid
    private String controllerId;

    @AUuid
    private String siteId;

    @AString(min = 0, max = 128)
    private String localCpeType;

    @AString
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEsn() {
        return esn;
    }

    public void setEsn(String esn) {
        this.esn = esn;
    }

    public String getOldEsn() {
        return oldEsn;
    }

    public void setOldEsn(String oldEsn) {
        this.oldEsn = oldEsn;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getLocalCpeType() {
        return localCpeType;
    }

    public void setLocalCpeType(String localCpeType) {
        this.localCpeType = localCpeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
