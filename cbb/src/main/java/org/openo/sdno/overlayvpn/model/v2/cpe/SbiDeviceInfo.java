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

/**
 * Class of Configuration Site NetworkElement Model.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-4
 */
public class SbiDeviceInfo extends SbiDeviceCreateBasicInfo {

    /**
     * Device id
     */
    private String id;

    /**
     * Show tenant or not
     */
    private String showTenant;

    /**
     * Service Ip
     */
    private String serviceIp;

    /**
     * Device type
     */
    private String neType;

    /**
     * Device version
     */
    private String version;

    /**
     * Device status
     */
    private String status;

    /**
     * Device longitude
     */
    private Double gisLon;

    /**
     * Device latitude
     */
    private Double gisLat;

    /**
     * Device vendor
     */
    private String vendor;

    /**
     * Tenant id
     */
    private String tenantId;

    /**
     * Tenant name
     */
    private String tenantName;

    /**
     * Organization id
     */
    private String orgnizationId;

    /**
     * Device creator
     */
    private String creator;

    /**
     * Device create time
     */
    private String createTime;

    /**
     * Device register time
     */
    private String registerTime;

    /**
     * Device modifier
     */
    private String modifier;

    /**
     * Device modifyTime
     */
    private String modifyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShowTenant() {
        return showTenant;
    }

    public void setShowTenant(String showTenant) {
        this.showTenant = showTenant;
    }

    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    public String getNeType() {
        return neType;
    }

    public void setNeType(String neType) {
        this.neType = neType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getGisLon() {
        return gisLon;
    }

    public void setGisLon(Double gisLon) {
        this.gisLon = gisLon;
    }

    public Double getGisLat() {
        return gisLat;
    }

    public void setGisLat(Double gisLat) {
        this.gisLat = gisLat;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getOrgnizationId() {
        return orgnizationId;
    }

    public void setOrgnizationId(String orgnizationId) {
        this.orgnizationId = orgnizationId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

}
