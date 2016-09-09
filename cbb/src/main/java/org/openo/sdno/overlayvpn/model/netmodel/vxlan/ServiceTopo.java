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

package org.openo.sdno.overlayvpn.model.netmodel.vxlan;

import java.util.List;

import org.openo.sdno.overlayvpn.model.NvString;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of ServiceTopo.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class ServiceTopo {

    /**
     * Key Id
     */
    @AString(min = 1, max = 36)
    private String uuid;

    /**
     * Tenant Id
     */
    @AString(min = 1, max = 255)
    private String tenantId;

    /**
     * Topology type:HUBSPOKEN,FULLMESH
     */
    @AString(scope = "HUBSPOKEN,FULLMESH")
    private String topoType;

    private List<Tp> hubTpIdList;

    /**
     * hub-spoken
     */
    @AString(scope = "TRUE,FALSE")
    private String splitHorizon;

    /**
     * VPN Service Id
     */
    @AString(min = 1, max = 255)
    private String vpnServiceId;

    /**
     * Additional Info
     */
    @AString()
    private List<NvString> addtionalInfo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTopoType() {
        return topoType;
    }

    public void setTopoType(String topoType) {
        this.topoType = topoType;
    }

    public List<Tp> getHubTpIdList() {
        return hubTpIdList;
    }

    public void setHubTpIdList(List<Tp> hubTpIdList) {
        this.hubTpIdList = hubTpIdList;
    }

    public String getSplitHorizon() {
        return splitHorizon;
    }

    public void setSplitHorizon(String splitHorizon) {
        this.splitHorizon = splitHorizon;
    }

    public String getVpnServiceId() {
        return vpnServiceId;
    }

    public void setVpnServiceId(String vpnServiceId) {
        this.vpnServiceId = vpnServiceId;
    }

    public List<NvString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(List<NvString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

}
