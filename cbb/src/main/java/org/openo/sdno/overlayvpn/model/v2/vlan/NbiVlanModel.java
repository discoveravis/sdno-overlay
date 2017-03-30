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

package org.openo.sdno.overlayvpn.model.v2.vlan;

import java.util.List;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseServiceModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Model class of Site VLAN.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-4
 */
@MOResType(infoModelName = "localsite_vlanmodel")
public class NbiVlanModel extends BaseServiceModel {

    /**
     * Site Uuid
     */
    @AUuid
    private String siteId;

    /**
     * Vlan Id
     */
    @AInt(min = 1, max = 4094)
    private Integer vlanId;

    /**
     * List of port Uuid
     */
    @NONInvField
    private List<String> ports;

    /**
     * List of port name
     */
    @NONInvField
    private List<String> portNames;

    @NONInvField
    private long createtime;

    @NONInvField
    private long updatetime;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public Integer getVlanId() {
        return vlanId;
    }

    public void setVlanId(Integer vlanId) {
        this.vlanId = vlanId;
    }

    public List<String> getPorts() {
        return ports;
    }

    public void setPorts(List<String> portList) {
        this.ports = portList;
    }

    public List<String> getPortNames() {
        return portNames;
    }

    public void setPortNames(List<String> portNames) {
        this.portNames = portNames;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
        return "VlanModel [siteId=" + siteId + ",vlanId=" + vlanId + ",portNames=" + portNames + "]";
    }
}
