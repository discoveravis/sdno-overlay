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
 * Class of Tp.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class Tp {

    /**
     * Key Id
     */
    @AString(min = 1, max = 36)
    private String uuid;

    /**
     * Controller Id
     */
    @AString(require = true, min = 1, max = 255)
    private String controllerId;

    /**
     * Controller Device id
     */
    @AString(min = 1, max = 255)
    private String externalId;

    /**
     * Network Element Id
     */
    @AString(min = 1, max = 255)
    private String neId;

    @AString(min = 1, max = 255)
    private String userLabel;

    /**
     * VxLan Service Id
     */
    @AString(min = 1, max = 255)
    private String vxlanServiceId;

    @AString(min = 1, max = 255)
    private String evpnE2EServiceId;

    @AString(min = 1, max = 255)
    private String evpnNeServiceId;

    @AString(min = 1, max = 255)
    private String l3vpnE2EServiceId;

    @AString(min = 1, max = 255)
    private String l3vpnNeServiceId;

    @AString(scope = "L2,L3")
    private String accessType;

    /**
     * QOS Id
     */
    @AString(min = 1, max = 255)
    private String qosId;

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

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getUserLabel() {
        return userLabel;
    }

    public void setUserLabel(String userLabel) {
        this.userLabel = userLabel;
    }

    public String getVxlanServiceId() {
        return vxlanServiceId;
    }

    public void setVxlanServiceId(String vxlanServiceId) {
        this.vxlanServiceId = vxlanServiceId;
    }

    public String getEvpnE2EServiceId() {
        return evpnE2EServiceId;
    }

    public void setEvpnE2EServiceId(String evpnE2EServiceId) {
        this.evpnE2EServiceId = evpnE2EServiceId;
    }

    public String getEvpnNeServiceId() {
        return evpnNeServiceId;
    }

    public void setEvpnNeServiceId(String evpnNeServiceId) {
        this.evpnNeServiceId = evpnNeServiceId;
    }

    public String getL3vpnE2EServiceId() {
        return l3vpnE2EServiceId;
    }

    public void setL3vpnE2EServiceId(String l3vpnE2EServiceId) {
        this.l3vpnE2EServiceId = l3vpnE2EServiceId;
    }

    public String getL3vpnNeServiceId() {
        return l3vpnNeServiceId;
    }

    public void setL3vpnNeServiceId(String l3vpnNeServiceId) {
        this.l3vpnNeServiceId = l3vpnNeServiceId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getQosId() {
        return qosId;
    }

    public void setQosId(String qosId) {
        this.qosId = qosId;
    }

    public List<NvString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(List<NvString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

}
