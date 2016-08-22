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

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.openo.sdno.overlayvpn.brs.model.SiteMO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.BaseServiceModel;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Site To DC model object for the NBI Rest interface.<br/>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_sitetodc")
public class SiteToDc extends BaseServiceModel {

    /**
     * SiteToDC overlay VPN instance name.
     */
    @Valid
    private String name;

    /**
     * Site Object
     */
    private Site site;

    /**
     * Vpc Object
     */
    private Vpc vpc;

    /**
     * Constructor<br/>
     * <p>
     * Empty constructor
     * </p>
     * 
     * @since SDNO 0.5
     */
    public SiteToDc() {
        super();
    }

    /**
     * Constructor<br/>
     * <p>
     * Constructor with UUid
     * </p>
     * 
     * @since SDNO 0.5
     * @param uuid
     */
    public SiteToDc(String uuid) {
        super();
        this.setUuid(uuid);
        site = new Site();
        vpc = new Vpc();
    }

    /**
     * Constructor<br/>
     * <p>
     * Constructor with Tenant Id & UUid
     * </p>
     * 
     * @since SDNO 0.5
     * @param uuid SiteToDc UUid
     * @param tenantId tenant id
     */
    public SiteToDc(String uuid, String tenantId) {
        super();
        this.setUuid(uuid);
        this.setTenantId(tenantId);
        site = new Site();
        vpc = new Vpc();
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    /**
     * Copy Basic data from other Site2DC.<br/>
     * 
     * @param data Site2DC Object
     * @since SDNO 0.5
     */
    public void copyBasicData(SiteToDc data) {
        super.copyBasicData(data);
        this.setSite(data.getSite());
        this.setVpc(data.getVpc());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Vpc getVpc() {
        return vpc;
    }

    public void setVpc(Vpc vpc) {
        this.vpc = vpc;
    }

    /**
     * Class of Site NBI Model.<br/>
     * 
     * @author
     * @version SDNO 0.5 Jun 3, 2016
     */
    public class Site extends SiteMO {

        /**
         * SiteToDC Site details: Site Type.
         */
        @Valid
        @AString(require = true, scope = "cidr,port,port-and-vlan")
        private String siteType;

        /**
         * SiteToDC Site details: Site Address.
         */
        @Valid
        @AString(require = true)
        private String siteTypeAddress;

        /**
         * SiteToDC Site details: Site ThinCPE as VxLAN GW
         */
        @Valid
        @AUuid(require = true)
        private String siteThinCPE;

        /**
         * SiteToDC Site details: Port Vlan mapping of site.
         */
        @Valid
        @AString(require = true)
        private String portVlan;

        /**
         * SiteToDC Site details: Site ThinCPE if exists at edge.
         */
        @Valid
        @AUuid(require = false)
        private String sitevCPE;

        /**
         * SiteToDC Site details: Site gateway if known already.
         */
        @Valid
        @AUuid(require = false)
        private String siteGatewayId;

        /**
         * Port and Vlans are separated by '/' .
         */
        @Valid
        @AUuid(require = true)
        private String portAndVlan;

        public String getSiteTypeAddress() {
            return siteTypeAddress;
        }

        public void setSiteTypeAddress(String siteTypeAddress) {
            this.siteTypeAddress = siteTypeAddress;
        }

        public String getSiteThinCPE() {
            return siteThinCPE;
        }

        public void setSiteThinCPE(String siteThinCPE) {
            this.siteThinCPE = siteThinCPE;
        }

        public String getSitevCPE() {
            return sitevCPE;
        }

        public void setSitevCPE(String sitevCPE) {
            this.sitevCPE = sitevCPE;
        }

        public String getSiteGatewayId() {
            return siteGatewayId;
        }

        public void setSiteGatewayId(String siteGatewayId) {
            this.siteGatewayId = siteGatewayId;
        }

        public String getSiteType() {
            return siteType;
        }

        public void setSiteType(String siteType) {
            this.siteType = siteType;
        }

        public String getPortAndVlan() {
            return portAndVlan;
        }

        public void setPortAndVlan(String portAndVlan) {
            this.portAndVlan = portAndVlan;
        }
    }

    /**
     * Class of SubNet Model.<br/>
     * 
     * @author
     * @version SDNO 0.5 28-May-2016
     */
    public class SubNet {

        @JsonProperty(value = "id")
        @AUuid
        protected String uuid;

        private String name;

        private String description;

        private String cidr;

        private String vpcId;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVpcId() {
            return vpcId;
        }

        public void setVpcId(String vpcId) {
            this.vpcId = vpcId;
        }
    }

    /**
     * Class of VPC Model.<br/>
     * 
     * @author
     * @version SDNO 0.5 28-May-2016
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
        private SubNet subnet;

        Vpc() {
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
}
