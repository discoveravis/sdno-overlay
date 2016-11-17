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

package org.openo.sdno.overlayvpn.model.localsite;

import java.util.List;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.BaseServiceModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Model class of Site VLAN.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-11-16
 */
@MOResType(infoModelName = "localsite_vlanmodel")
public class VlanModel extends BaseServiceModel {

    /**
     * Site Uuid
     */
    @AUuid
    private String siteId;

    /**
     * Vlan Id
     */
    @AInt(min = 1, max = 4094)
    private int vlanId;

    /**
     * List of port Uuid
     */
    @NONInvField
    private List<String> portList;

    /**
     * List of port name
     */
    @NONInvField
    private List<String> portNames;

    @NONInvField
    @AString
    private String createtime;

    @NONInvField
    @AString
    private String updatetime;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getVlanId() {
        return vlanId;
    }

    public void setVlanId(int vlanId) {
        this.vlanId = vlanId;
    }

    public List<String> getPortList() {
        return portList;
    }

    public void setPortList(List<String> portList) {
        this.portList = portList;
    }

    public List<String> getPortNames() {
        return portNames;
    }

    public void setPortNames(List<String> portNames) {
        this.portNames = portNames;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "VlanModel [siteId=" + siteId + ",vlanId=" + vlanId + ",portNames=" + portNames + "]";
    }
}
