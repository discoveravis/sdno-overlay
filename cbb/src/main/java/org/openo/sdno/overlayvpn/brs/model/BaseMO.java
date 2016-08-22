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

package org.openo.sdno.overlayvpn.brs.model;

import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Base model object abstract class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-5-5
 */
public abstract class BaseMO {

    protected String id;

    protected String name;

    @AString(require = false, min = 0, max = 255)
    protected String description;

    /**
     * Get id attribute.<br/>
     * 
     * @return id attribute
     * @since SDNO 0.5
     */
    public final String getId() {
        return id;
    }

    /**
     * Set id attribute.<br/>
     * 
     * @param id String Object
     * @since SDNO 0.5
     */
    public final void setId(String id) {
        this.id = id;
    }

    /**
     * Get name attribute.<br/>
     * 
     * @return name attribute
     * @since SDNO 0.5
     */
    public final String getName() {
        return name;
    }

    /**
     * Set name attribute.<br/>
     * 
     * @param name String Object
     * @since SDNO 0.5
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * Get description attribute.<br/>
     * 
     * @return
     * @since SDNO 0.5
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description attribute.<br/>
     * 
     * @param description String Object
     * @since SDNO 0.5
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Transform object info to JSON.<br/>
     * 
     * @return JSON string
     * @since SDNO 0.5
     */
    public abstract String toJsonBody();
}
