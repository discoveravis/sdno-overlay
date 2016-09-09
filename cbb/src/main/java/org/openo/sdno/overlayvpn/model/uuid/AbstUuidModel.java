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

package org.openo.sdno.overlayvpn.model.uuid;

import org.codehaus.jackson.annotate.JsonProperty;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOInvField;
import org.openo.sdno.overlayvpn.util.check.UuidUtil;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Abstract Model of UUID.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public abstract class AbstUuidModel implements IAllocateUuid {

    /**
     * Key id
     */
    @MOInvField(invName = "id")
    @JsonProperty(value = "id")
    @AUuid
    protected String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Copy UUid of Other Object to this object.<br>
     * 
     * @param data other AbstUuidModel Object
     * @since SDNO 0.5
     */
    public void copyBasicData(AbstUuidModel data) {
        this.uuid = data.getUuid();
    }

    /**
     * Override allocateUuid function.<br>
     * 
     * @since SDNO 0.5
     */
    @Override
    public void allocateUuid() {
        if(!UuidUtil.validate(uuid)) {
            setUuid(UuidUtils.createUuid());
        }
    }

    /**
     * <br>
     * 
     * @throws ServiceException
     * @since SDNO 0.5
     */
    @Override
    public void checkUuid() throws ServiceException {
        UuidUtils.checkUuid(uuid);
    }

    /**
     * Override HashCode function.<br>
     * 
     * @return hashCode of this Object
     * @since SDNO 0.5
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    /**
     * Override Equals function.<br>
     * 
     * @param obj Other object
     * @return true if this Object equals to the other
     * @since SDNO 0.5
     */
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

        AbstUuidModel other = (AbstUuidModel)obj;
        if(uuid == null) {
            if(other.uuid != null) {
                return false;
            }
        } else if(!uuid.equals(other.uuid)) {
            return false;
        }

        return true;
    }
}
