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

package org.openo.sdno.overlayvpn.model;

import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;

/**
 * It is used to save port and VLAN. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class PortAndVlanUsed extends AbstUuidModel {

    private String epdId;

    private String neId;

    private String portId;

    private String type;

    private String vlan;

    public String getEpdId() {
        return epdId;
    }

    public void setEpdId(String epdId) {
        this.epdId = epdId;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }
}
