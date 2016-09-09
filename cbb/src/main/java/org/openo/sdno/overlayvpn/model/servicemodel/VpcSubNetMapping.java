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

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Vpc model info and site to DC id mapping.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 24, 2016
 */
@MOResType(infoModelName = "vpcsubnetmapping")
public class VpcSubNetMapping extends AbstUuidModel {

    @AUuid(require = true)
    private String sitetodcId;

    @AUuid(require = true)
    private String vpcId;

    @AString(require = true)
    private String vpcName;

    @AString(require = false)
    private String vpcDescription;

    @AString(require = true)
    private String status = ActionStatus.NONE.getName();

    @AUuid(require = true)
    private String osControllerId;

    @AString(require = true)
    private String externalIp = null;

    @AUuid(require = true)
    private String underResId;

    @AUuid(require = true)
    private String underResParentId = null;

    @AUuid(require = true)
    private String underResRouterId = null;

    @AUuid(require = true)
    private String subnetId;

    @AString(require = true)
    private String subnetName;

    @AString(require = false)
    private String subnetDescription;

    @AString(require = false)
    private String subnetCidr;

    @AString(require = false)
    private Integer vni;

    public String getSitetodcId() {
        return sitetodcId;
    }

    public void setSitetodcId(String sitetodcId) {
        this.sitetodcId = sitetodcId;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public String getVpcName() {
        return vpcName;
    }

    public void setVpcName(String vpcName) {
        this.vpcName = vpcName;
    }

    public String getVpcDescription() {
        return vpcDescription;
    }

    public void setVpcDescription(String vpcDescription) {
        this.vpcDescription = vpcDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOsControllerId() {
        return osControllerId;
    }

    public void setOsControllerId(String osControllerId) {
        this.osControllerId = osControllerId;
    }

    public String getExternalIp() {
        return externalIp;
    }

    public void setExternalIp(String externalIp) {
        this.externalIp = externalIp;
    }

    public String getUnderResId() {
        return underResId;
    }

    public void setUnderResId(String underResId) {
        this.underResId = underResId;
    }

    public String getUnderResParentId() {
        return underResParentId;
    }

    public void setUnderResParentId(String underResParentId) {
        this.underResParentId = underResParentId;
    }

    public String getUnderResRouterId() {
        return underResRouterId;
    }

    public void setUnderResRouterId(String underResRouterId) {
        this.underResRouterId = underResRouterId;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getSubnetName() {
        return subnetName;
    }

    public void setSubnetName(String subnetName) {
        this.subnetName = subnetName;
    }

    public String getSubnetDescription() {
        return subnetDescription;
    }

    public void setSubnetDescription(String subnetDescription) {
        this.subnetDescription = subnetDescription;
    }

    public String getSubnetCidr() {
        return subnetCidr;
    }

    public void setSubnetCidr(String subnetCidr) {
        this.subnetCidr = subnetCidr;
    }

    public Integer getVni() {
        return vni;
    }

    public void setVni(Integer vni) {
        this.vni = vni;
    }

}
