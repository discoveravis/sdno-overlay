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

package org.openo.sdno.overlayvpn.model.netmodel.vxlan;

/**
 * Class of NeVtep Model.<br/>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class NeVtep {

    /**
     * NetWorkElement Device Id
     */
    private String neId;

    /**
     * EndPoint IpAddress
     */
    private String vtepIp;

    public NeVtep() {
        // empty constructor
    }

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param neId NetWorkElement Device Id
     * @param vtepIp EndPoint IpAddress
     */
    public NeVtep(String neId, String vtepIp) {
        this.neId = neId;
        this.vtepIp = vtepIp;
    }

    /**
     * @return Returns the neId.
     */
    public String getNeId() {
        return neId;
    }

    /**
     * @param neId The neId to set.
     */
    public void setNeId(String neId) {
        this.neId = neId;
    }

    /**
     * @return Returns the vtepIp.
     */
    public String getVtepIp() {
        return vtepIp;
    }

    /**
     * @param vtepIp The vtepIp to set.
     */
    public void setVtepIp(String vtepIp) {
        this.vtepIp = vtepIp;
    }
}
