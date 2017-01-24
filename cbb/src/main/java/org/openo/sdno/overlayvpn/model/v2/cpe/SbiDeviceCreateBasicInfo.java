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

/**
 * Class of SbiDevice Create Model.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-4
 */
public class SbiDeviceCreateBasicInfo extends UuidModel {

    /**
     * Name
     */
    @AString(min = 0, max = 64)
    private String name;

    /**
     * Esn
     */
    @AString(require = true, min = 20, max = 20)
    private String esn;

    /**
     * Name of organization
     */
    @AString(min = 0, max = 128)
    private String orgnizationName;

    /**
     * Description
     */
    @AString(min = 0, max = 255)
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

    public String getOrgnizationName() {
        return orgnizationName;
    }

    public void setOrgnizationName(String orgnizationName) {
        this.orgnizationName = orgnizationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (esn == null ? 0 : esn.hashCode());
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

        SbiDeviceInfo other = (SbiDeviceInfo)obj;
        if(esn == null) {
            if(other.getEsn() != null) {
                return false;
            }
        } else if(!esn.equals(other.getEsn())) {
            return false;
        }

        return true;
    }

}
