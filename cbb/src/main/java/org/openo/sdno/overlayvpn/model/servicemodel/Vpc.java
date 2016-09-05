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

package org.openo.sdno.overlayvpn.model.servicemodel;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Vpc model.<br/>
 * 
 * @author
 * @version     SDNO 0.5  Aug 24, 2016
 */
public class Vpc {

    @JsonProperty(value = "id")
    @AUuid
    protected String uuid;

    /**
     * SiteToDC DC details: Vpc Name.
     */
    @AString(require = true)
    private String name;

    /**
     * SiteToDC DC details: Vpc Description
     */
    @AString(require = false)
    private String description;

    /**
     * VPC status.
     */
    @AString(require = true)
    private String status = ActionStatus.NONE.getName();

    /**
     * VPC status reason.
     */
    @NONInvField
    @AString(require = false)
    private String statusReason = null;

    /**
     * SiteToDC DC details: Controller Id.
     */
    @AUuid(require = true)
    private String osControllerId;

    /**
     * VPC external Ip address in v4.
     */
    @AString(require = true)
    private String externalIp = null;

    private UnderlayResources attributes = null;

    /**
     * SiteToDC DC details: DC Access subnet.
     */
    @Valid
    @AUuid(require = true)
    @JsonIgnore
    private SubNet subnet;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     */
    public Vpc() {
        subnet = new SubNet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOsControllerId() {
        return osControllerId;
    }

    public void setOsControllerId(String osControllerId) {
        this.osControllerId = osControllerId;
    }

    public SubNet getSubnet() {
        return subnet;
    }

    public void setSubnet(SubNet subnet) {
        this.subnet = subnet;
    }

    public String getExternalIp() {
        return this.externalIp;
    }

    public Vpc setExternalIp(String externalIp) {
        this.externalIp = externalIp;
        return this;
    }

    public UnderlayResources getAttributes() {
        return this.attributes;
    }

    public void setAttributes(UnderlayResources attributes) {
        this.attributes = attributes;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusReason() {
        return this.statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    /**
     * The model of underlay resource.<br/>
     * 
     * @author
     * @version SDNO 0.5 Aug 18, 2016
     */
    @JsonRootName(value = "underlay_resources")
    public class UnderlayResources extends AbstUuidModel {

        /**
         * VPC uuid.
         */
        @AUuid(require = true)
        private String parentId = null;

        /**
         * VPC router uuid.
         */
        @AUuid(require = true)
        private String routerId = null;

        /**
         * Constructor.<br/>
         * 
         * @since SDNO 0.5
         */
        public UnderlayResources() {
            super();
        }

        public String getRouterId() {
            return this.routerId;
        }

        public void setRouterId(String routerId) {
            this.routerId = routerId;
        }

        public String getParentId() {
            return this.parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }
    }

}
