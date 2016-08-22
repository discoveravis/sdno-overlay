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

package org.openo.sdno.overlayvpn.model.port;

import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AIp;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of WanSubInterface Model.<br/>
 * <p>
 * This model do not store in database, just collect from adapter
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public class WanSubInterface {

    /**
     * network element id
     */
    @AUuid
    private String neId;

    /**
     * name of WanSubInf
     */
    @AString(require = false, min = 0, max = 255)
    private String name;

    /**
     * VLAN id
     */
    @AInt(min = 1, max = 4094)
    private int ceHighVlan;

    /**
     * VLAN id
     */
    @AInt(min = 1, max = 4094)
    private int ceLowVlan;

    /**
     * IpAddress of WanSubInf(Ipv4)
     */
    @AIp
    private String ipAddress;

    /**
     * IpMask of WanSubInf(Ipv4)
     */
    @AIp
    private String mask;

    /**
     * MacAddress of WanSubInf
     */
    private String mac;

    /**
     * IpAddress of WanSubInf(Ipv6)
     */
    private String ipv6address;

    /**
     * The prefix length of IPV6
     */
    private String prifexlength;

    /**
     * type:for GRE or for VxLan
     */
    private String type;

    @Override
    public String toString() {
        return "WanSubInterface [neId=" + neId + ", name=" + name + ", ceHighVlan=" + ceHighVlan + ", ip=" + ipAddress
                + ", mask=" + mask + ", mac=" + mac + ", ipv6address=" + ipv6address + ", prifexlength=" + prifexlength
                + ", type=" + type + "]";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getCeHighVlan() {
        return ceHighVlan;
    }

    public void setCeHighVlan(int ceHighVlan) {
        this.ceHighVlan = ceHighVlan;
    }

    public int getCeLowVlan() {
        return ceLowVlan;
    }

    public void setCeLowVlan(int ceLowVlan) {
        this.ceLowVlan = ceLowVlan;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpv6address() {
        return ipv6address;
    }

    public void setIpv6address(String ipv6address) {
        this.ipv6address = ipv6address;
    }

    public String getPrifexlength() {
        return prifexlength;
    }

    public void setPrifexlength(String prifexlength) {
        this.prifexlength = prifexlength;
    }
}
