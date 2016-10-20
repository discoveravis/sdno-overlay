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

package org.openo.sdno.overlayvpn.model.servicemodel;

/**
 * NBI interface for Site to DC use case<br>
 * 
 * @author
 * @version SDNO 0.5 July 18, 2016
 */
public class SiteNbi {

    String cidr;

    String thinCpeId;

    String portAndVlan;

    String vCPEId;

    public String getvCPEId() {
        return vCPEId;
    }

    public void setvCPEId(String vCPEId) {
        this.vCPEId = vCPEId;
    }

    public SiteNbi() {

    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public String getThinCpeId() {
        return thinCpeId;
    }

    public void setThinCpeId(String thinCpeId) {
        this.thinCpeId = thinCpeId;
    }

    public String getPortAndVlan() {
        return portAndVlan;
    }

    public void setPortAndVlan(String portAndVlan) {
        this.portAndVlan = portAndVlan;
    }

}
