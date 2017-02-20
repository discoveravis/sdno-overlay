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

package org.openo.sdno.overlayvpn.model.v2.overlay;

import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOUUIDField;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Basic model class.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-6
 */
public class BaseModel {

    /**
     * Key id
     */
    @AUuid
    @MOUUIDField
    protected String id;

    public BaseModel() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Interface of Allocate UUID.<br>
     * 
     * @since SDNO 0.5
     */
    public void allocateUuid() {
        if(!UuidUtil.validate(this.id)) {
            setId(UuidUtils.createUuid());
        }
    }

}
