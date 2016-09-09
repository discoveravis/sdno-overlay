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

package org.openo.sdno.overlayvpn.model.netmodel.localsite;

import java.util.List;

import org.openo.sdno.overlayvpn.verify.annotation.AIp;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of Adapter Model of DeviceNetwork.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class AdapterDeviceNetwork {

    private String id;

    private String deviceId;

    /**
     * name
     */
    @AString(require = true, min = 1, max = 32)
    private String name;

    /**
     * usage: terminal and vm
     */
    private String use;

    @AString(min = 1, max = 255)
    private String description;

    /**
     * VLAN ID: 1~4000(terminal)\4001-4032(vm)
     */
    private int vlanId;

    @AIp
    @AString(require = true)
    private String ipAddress;

    @AIp
    @AString(require = true)
    private String maskAddress;

    private boolean isChangedMode;

    /**
     * true if enable DHCP
     */
    private boolean dhcpEnable;

    /**
     * Configuration of DHCP
     */
    private DhcpConfig dhcpConfig;

    SecurityAuth securityAuth;

    /**
     * SubNet
     */
    private List<IpAddressAndMask> subnetList;

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the use.
     */
    public String getUse() {
        return use;
    }

    /**
     * @param use The use to set.
     */
    public void setUse(String use) {
        this.use = use;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the vlanId.
     */
    public int getVlanId() {
        return vlanId;
    }

    /**
     * @param vlanId The vlanId to set.
     */
    public void setVlanId(int vlanId) {
        this.vlanId = vlanId;
    }

    /**
     * @return Returns the ipAddress.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress The ipAddress to set.
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return Returns the maskAddress.
     */
    public String getMaskAddress() {
        return maskAddress;
    }

    /**
     * @param maskAddress The maskAddress to set.
     */
    public void setMaskAddress(String maskAddress) {
        this.maskAddress = maskAddress;
    }

    /**
     * @return Returns the isChangedMode.
     */
    public boolean getIsChangedMode() {
        return isChangedMode;
    }

    /**
     * @param isChangedMode The isChangedMode to set.
     */
    public void setIsChangedMode(boolean isChangedMode) {
        this.isChangedMode = isChangedMode;
    }

    /**
     * @return Returns the dhcpEnable.
     */
    public boolean getDhcpEnable() {
        return dhcpEnable;
    }

    /**
     * @param dhcpEnable The dhcpEnable to set.
     */
    public void setDhcpEnable(boolean dhcpEnable) {
        this.dhcpEnable = dhcpEnable;
    }

    /**
     * @return Returns the dhcpConfig.
     */
    public DhcpConfig getDhcpConfig() {
        return dhcpConfig;
    }

    /**
     * @param dhcpConfig The dhcpConfig to set.
     */
    public void setDhcpConfig(DhcpConfig dhcpConfig) {
        this.dhcpConfig = dhcpConfig;
    }

    /**
     * @return Returns the deviceId.
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId The deviceId to set.
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return Returns the securityAuth.
     */
    public SecurityAuth getSecurityAuth() {
        return securityAuth;
    }

    /**
     * @param securityAuth The securityAuth to set.
     */
    public void setSecurityAuth(SecurityAuth securityAuth) {
        this.securityAuth = securityAuth;
    }

    /**
     * @return Returns the subnetList.
     */
    public List<IpAddressAndMask> getSubnetList() {
        return subnetList;
    }

    /**
     * @param subnetList The subnetList to set.
     */
    public void setSubnetList(List<IpAddressAndMask> subnetList) {
        this.subnetList = subnetList;
    }

}
