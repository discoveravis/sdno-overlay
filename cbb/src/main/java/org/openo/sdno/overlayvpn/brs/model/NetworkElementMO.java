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

package org.openo.sdno.overlayvpn.brs.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Network element model class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-5-5
 */
@MOResType(infoModelName = "managedelement")
public class NetworkElementMO extends BaseMO {

    private static final String MOKEY = "managedElement";

    @AString(require = false, min = 0, max = 255)
    private String version;

    /**
     * Network element's logic ID. Use for matching and the element ID collected from the
     * controller.
     */
    @AString(require = false, min = 0, max = 64)
    private String logicID;

    @AString(require = false, min = 0, max = 36)
    private String phyNeID;

    private List<String> managementDomainID;

    private List<String> controllerID;

    private List<String> siteID;

    /**
     * Product name, such as NE40E, CX600.
     */
    @AString(require = false, min = 0, max = 32)
    private String productName;

    /**
     * Is virtual or not.
     */
    @AString(require = false, scope = "true,false")
    private String isVirtual;

    /**
     * IP address.
     */
    @AString(require = true, min = 0, max = 128)
    private String ipAddress;

    /**
     * NE's source, such as NETWORK_ME(discover from the network element),OS(discover from the
     * controller),NETWORK_EMS(discover from the EMS),USER(Input by user).
     */
    @AString(require = false, scope = "network_me,os,network_ems,user")
    private String source;

    /**
     * Owner. Fill in controller UUID if it's comes from controller, fill in network element UUID if
     * it's comes from network element.
     */
    @AString(require = false, min = 0, max = 36)
    private String owner;

    /**
     * Location info, such as geographical address, logical position(site).
     */
    private String location;

    /**
     * Serial number.
     */
    @AString(require = false, min = 0, max = 32)
    private String serialNumber;

    /**
     * Manufacturer name.
     */
    @AString(require = false, min = 0, max = 32)
    private String manufacturer;

    /**
     * Manufacture date.
     */
    @AString(require = false, min = 0, max = 32)
    private String manufactureDate;

    /**
     * Administration status.
     */
    @AString(require = false, scope = "active,inactive")
    private String adminState;

    /**
     * Operation state.
     */
    @AString(require = false, scope = "up,down,unknown")
    private String operState;

    private String nativeID;

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    public NetworkElementMO() {
        super();
    }

    /**
     * Get version attribute.<br/>
     * 
     * @return version attribute
     * @since SDNO 0.5
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set version attribute.<br/>
     * 
     * @param version String Object
     * @since SDNO 0.5
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Get logicID attribute.<br/>
     * 
     * @return logicID attribute
     * @since SDNO 0.5
     */
    public String getLogicID() {
        return logicID;
    }

    /**
     * Set logicID attribute.<br/>
     * 
     * @param logicID String Object
     * @since SDNO 0.5
     */
    public void setLogicID(String logicID) {
        this.logicID = logicID;
    }

    /**
     * Get phyNeID attribute.<br/>
     * 
     * @return phyNeID attribute.
     * @since SDNO 0.5
     */
    public String getPhyNeID() {
        return phyNeID;
    }

    /**
     * Get phyNeID attribute.<br/>
     * 
     * @param phyNeID String Object
     * @since SDNO 0.5
     */
    public void setPhyNeID(String phyNeID) {
        this.phyNeID = phyNeID;
    }

    /**
     * Get managementDomainID attribute.<br/>
     * 
     * @return managementDomainID attribute
     * @since SDNO 0.5
     */
    public List<String> getManagementDomainID() {
        return managementDomainID;
    }

    /**
     * Set managementDomainID attribute.<br/>
     * 
     * @param managementDomainID String Object
     * @since SDNO 0.5
     */
    public void setManagementDomainID(List<String> managementDomainID) {
        this.managementDomainID = managementDomainID;
    }

    /**
     * Get controllerID attribute.<br/>
     * 
     * @return controllerID attribute
     * @since SDNO 0.5
     */
    public List<String> getControllerID() {
        return controllerID;
    }

    /**
     * Set controllerID attribute.<br/>
     * 
     * @param controllerID List<String> Object
     * @since SDNO 0.5
     */
    public void setControllerID(List<String> controllerID) {
        this.controllerID = controllerID;
    }

    /**
     * Get siteID attribute.<br/>
     * 
     * @return List<String> Object
     * @since SDNO 0.5
     */
    public List<String> getSiteID() {
        return siteID;
    }

    /**
     * Set siteID attribute.<br/>
     * 
     * @param siteID List<String> Object
     * @since SDNO 0.5
     */
    public void setSiteID(List<String> siteID) {
        this.siteID = siteID;
    }

    /**
     * Get productName attribute.<br/>
     * 
     * @return productName attribute
     * @since SDNO 0.5
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Set productName attribute.<br/>
     * 
     * @param productName String Object
     * @since SDNO 0.5
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Get isVirtual attribute.<br/>
     * 
     * @return isVirtual attribute.
     * @since SDNO 0.5
     */
    public String getIsVirtual() {
        return isVirtual;
    }

    /**
     * Set isVirtual attribute.<br/>
     * 
     * @param isVirtual String Object
     * @since SDNO 0.5
     */
    public void setIsVirtual(String isVirtual) {
        this.isVirtual = isVirtual;
    }

    /**
     * Get ipAddress attribute.<br/>
     * 
     * @return ipAddress attribute
     * @since SDNO 0.5
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Set ipAddress attribute.<br/>
     * 
     * @param ipAddress String Object
     * @since SDNO 0.5
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Get source attribute.<br/>
     * 
     * @return source attribute
     * @since SDNO 0.5
     */
    public String getSource() {
        return source;
    }

    /**
     * Set source attribute. <br/>
     * 
     * @param source String Object
     * @since SDNO 0.5
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Get owner attribute.<br/>
     * 
     * @return owner attribute
     * @since SDNO 0.5
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set owner attribute.<br/>
     * 
     * @param owner String Object
     * @since SDNO 0.5
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Get location attribute.<br/>
     * 
     * @return location attribute
     * @since SDNO 0.5
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set location attribute.<br/>
     * 
     * @param location String Object
     * @since SDNO 0.5
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get serialNumber attribute.<br/>
     * 
     * @return serialNumber attribute
     * @since SDNO 0.5
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Set serialNumber attribute.<br/>
     * 
     * @param serialNumber String Object
     * @since SDNO 0.5
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Get manufacturer attribute.<br/>
     * 
     * @return manufacturer attribute
     * @since SDNO 0.5
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Set manufacturer attribute.<br/>
     * 
     * @param manufacturer String Object
     * @since SDNO 0.5
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Get manufactureDate attribute.<br/>
     * 
     * @return manufactureDate attribute.
     * @since SDNO 0.5
     */
    public String getManufactureDate() {
        return manufactureDate;
    }

    /**
     * Set manufactureDate attribute.<br/>
     * 
     * @param manufactureDate manufactureDate attribute
     * @since SDNO 0.5
     */
    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    /**
     * Get adminState attribute.<br/>
     * 
     * @return adminState attribute
     * @since SDNO 0.5
     */
    public String getAdminState() {
        return adminState;
    }

    /**
     * Set adminState attribute.<br/>
     * 
     * @param adminState String Object
     * @since SDNO 0.5
     */
    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    /**
     * Get operState attribute.<br/>
     * 
     * @return operState attribute
     * @since SDNO 0.5
     */
    public String getOperState() {
        return operState;
    }

    /**
     * Set operState attribute.<br/>
     * 
     * @param operState attribute
     * @since SDNO 0.5
     */
    public void setOperState(String operState) {
        this.operState = operState;
    }

    /**
     * Get nativeID attribute.<br/>
     * 
     * @return nativeID attribute
     * @since SDNO 0.5
     */
    public String getNativeID() {
        return nativeID;
    }

    /**
     * Set nativeID attribute.<br/>
     * 
     * @param nativeID String Object
     * @since SDNO 0.5
     */
    public void setNativeID(String nativeID) {
        this.nativeID = nativeID;
    }

    /**
     * Get MOKEY attribute.<br/>
     * 
     * @return MOKEY attribute
     * @since SDNO 0.5
     */
    public static String getMokey() {
        return MOKEY;
    }

    /**
     * Override toJsonBody Function.<br/>
     * 
     * @return toJsonBody
     * @since SDNO 0.5
     */
    @Override
    public String toJsonBody() {
        Map<String, Object> moListMap = new HashMap<String, Object>();
        moListMap.put(MOKEY, this);
        return JsonUtil.toJson(moListMap);
    }

    /**
     * Override toString Function.<br/>
     * 
     * @return toString description
     * @since SDNO 0.5
     */
    @Override
    public String toString() {
        return "NE [id=" + id + ", name=" + name + ", ipAddress=" + ipAddress + ", description=" + description
                + ", version=" + version + ", adminState=" + adminState + ", operState=" + operState + ", longitude="
                + location + ", latitude=" + location + ", source=" + source + ", productName=" + productName
                + ", manufacturer=" + manufacturer + ",logicID=" + logicID + ",phyNeID=" + phyNeID
                + ",managementDomainID=" + managementDomainID + ",controllerID=" + controllerID + ",siteID=" + siteID
                + ",isVirtual=" + isVirtual + ",owner=" + owner + ",serialNumber=" + serialNumber + ",manufactureDate="
                + manufactureDate + "]";
    }
}
