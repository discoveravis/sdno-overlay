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

package org.openo.sdno.overlayvpn.rest;

/**
 * NBI interface for Site to DC use case<br/>
 * 
 * @author
 * @version SDNO 0.5 Jul 18, 2016
 */
public class SiteToDcNbi {

    // Name of Site2DC
    String name;

    // Description of Site2DC
    String description;

    // Site data
    SiteNbi site;

    // VPC data
    VpcNbi vpc;

    SiteToDcNbi() {
        site = new SiteNbi();
        vpc = new VpcNbi();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SiteNbi getSite() {
        return site;
    }

    public void setSite(SiteNbi site) {
        this.site = site;
    }

    public VpcNbi getVpc() {
        return vpc;
    }

    public void setVpc(VpcNbi vpc) {
        this.vpc = vpc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    static class SiteNbi {

        String cidr;

        String thinCpeId;

        String portAndVlan;

        String vCPEId;

        SiteNbi() {

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

        public String getVCPEId() {
            return vCPEId;
        }

        public void setVCPEId(String vCPEId) {
            this.vCPEId = vCPEId;
        }

        public String getPortAndVlan() {
            return portAndVlan;
        }

        public void setPortAndVlan(String portAndVlan) {
            this.portAndVlan = portAndVlan;
        }

    }

    static class VpcNbi {

        String name;

        Subnet site;

        VpcNbi() {
            site = new Subnet();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Subnet getSite() {
            return site;
        }

        public void setSite(Subnet site) {
            this.site = site;
        }
    }

    static class Subnet {

        String name;

        String cidr;

        Subnet() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCidr() {
            return cidr;
        }

        public void setCidr(String cidr) {
            this.cidr = cidr;
        }

    }
}
