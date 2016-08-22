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

package org.openo.sdno.overlayvpn.model;

import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class of NvString, It is used to save the type, endpoints and uuid of EndpointGroup. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@MOResType(infoModelName = "overlayvpn_nvstring")
public class NvString extends AbstUuidModel implements Cloneable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NvString.class);

    @AString(require = true, min = 1, max = 36)
    private String name;

    @AString(min = 0, max = 255)
    private String value;

    @AString(min = 0, max = 36)
    private String firstParentUuid;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     */
    public NvString() {
        super();
        uuid = null;
        name = null;
        value = null;
        firstParentUuid = null;
    }

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param tempName The value name
     * @param tempValue The value
     */
    public NvString(String tempName, String tempValue) {
        super();
        name = tempName;
        value = tempValue;
    }

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param tempName The value name
     * @param tempValue The value
     * @param parentUuid The uuid of parent
     */
    public NvString(String tempName, String tempValue, String parentUuid) {
        super();
        name = tempName;
        value = tempValue;
        firstParentUuid = parentUuid;
    }

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param tempUuid The uuid of current data
     * @param tempName The value name
     * @param tempValue The value
     * @param parentUuid The uuid of parent
     */
    public NvString(String tempUuid, String tempName, String tempValue, String parentUuid) {
        super();
        uuid = tempUuid;
        name = tempName;
        value = tempValue;
        firstParentUuid = parentUuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch(CloneNotSupportedException e) {
            LOGGER.error("Clone not support", e);
            return new Object();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(null == obj) {
            return false;
        }

        if(this == obj) {
            return true;
        }

        if(getClass() != obj.getClass()) {
            return false;
        }

        NvString other = (NvString)obj;
        if(name == null) {
            if(other.name != null) {
                return false;
            }
        } else if(!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public void allocateUuid() {
        if(!UuidUtil.validate(uuid)) {
            setUuid(UuidUtils.createUuid());
        }
    }

    public String getFirstParentUuid() {
        return firstParentUuid;
    }

    public void setFirstParentUuid(String firstParentUuid) {
        this.firstParentUuid = firstParentUuid;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

}
