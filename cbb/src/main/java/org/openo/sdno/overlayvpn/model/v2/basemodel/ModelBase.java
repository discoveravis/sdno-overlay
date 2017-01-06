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

package org.openo.sdno.overlayvpn.model.v2.basemodel;

import java.util.List;

import javax.validation.Valid;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MORelationField;
import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * The basic model information. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public abstract class ModelBase extends UuidModel {

    @AString(min = 0, max = 255)
    private String tenantId;

    @AString(require = true, min = 1, max = 128)
    private String name;

    @AString(min = 0, max = 256)
    private String description;

    @Valid
    @MORelationField
    private List<NvString> additionalInfo;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public ModelBase() {
        super();
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param name The model name
     */
    public ModelBase(String name) {
        super();
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NvString> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(List<NvString> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * It is used to copy attributes of ModelBase. <br>
     * 
     * @param data The original data.
     * @since SDNO 0.5
     */
    public void copyBasicData(ModelBase data) {
        super.copyBasicData(data);
        this.tenantId = data.getTenantId();
        this.name = data.getName();
        this.description = data.getDescription();
    }
}
