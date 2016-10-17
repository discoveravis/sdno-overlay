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

import org.codehaus.jackson.annotate.JsonProperty;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Subnet model.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 24, 2016
 */
public class SubNet {

    @JsonProperty(value = "id")
    @AUuid
    protected String uuid;

    private String name;

    private String description;

    private String cidr;

    private Integer vni;

    private String vpcId;

    private SubNet.UnderlayResources attributes = null;

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Integer getVni() {
        return vni;
    }

    public void setVni(Integer vni) {
        this.vni = vni;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SubNet.UnderlayResources getAttributes() {
        return this.attributes;
    }

    public SubNet setAttributes(SubNet.UnderlayResources attributes) {
        this.attributes = attributes;
        return this;
    }

    public static class UnderlayResources {

        @JsonProperty(value = "id")
        @AUuid
        protected String uuid;

        /**
         * SubNet uuid
         */
        @AUuid(require = true)
        private String parentId = null;

        /**
         * SubNet network uuid.
         */
        @AUuid(require = true)
        private String networkId = null;

        /**
         * SubNet uuid.
         */
        @AUuid(require = true)
        private String subnetId = null;

        public String getUuid() {
            return uuid;
        }

        public SubNet.UnderlayResources setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public String getNetworkId() {
            return this.networkId;
        }

        public SubNet.UnderlayResources setNetworkId(String networkId) {
            this.networkId = networkId;
            return this;
        }

        public String getSubnetId() {
            return this.subnetId;
        }

        public SubNet.UnderlayResources setSubnetId(String subnetId) {
            this.subnetId = subnetId;
            return this;
        }

        public String getParentId() {
            return this.parentId;
        }

        public SubNet.UnderlayResources setParentId(String parentId) {
            this.parentId = parentId;
            return this;
        }
    }
}
