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
public class SiteToDcNbi {

    // Name of Site2DC
    String name;

    // Description of Site2DC
    String description;

    // Site data
    SiteNbi site;

    // VPC data
    VpcNbi vpc;

    // service function path data
    SfpNbi sfp;

    public SiteToDcNbi() {
        site = new SiteNbi();
        vpc = new VpcNbi();
        sfp = new SfpNbi();
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

    public SfpNbi getSfp() {
        return sfp;
    }

    public void setSfp(SfpNbi sfp) {
        this.sfp = sfp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
