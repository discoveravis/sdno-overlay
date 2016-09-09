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

package org.openo.sdno.overlayvpn.brs.model;

import java.util.HashMap;
import java.util.Map;

import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Logical termination points model class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-5-5
 */
@MOResType(infoModelName = "terminationpoint")
public class LogicalTernminationPointMO extends BaseMO {

    private static final String MOKEY = "logicalTerminationPoint";

    /**
     * Network element UUID.
     */
    @AString(require = true, min = 0, max = 36)
    private String meID;

    /**
     * Type.
     */
    @AString(require = true, scope = "ETH,POS,Trunk")
    private String logicalType;

    /**
     * Layer speed rate, such as LR_Ethernet, LR_Fast_Ethernet, LR_Gigabit_Ethernet,
     * LR_PHYSICAL_ELECTRICAL, LR_PHYSICAL_OPTICAL.
     */
    @AString(require = false, min = 0, max = 32)
    private String layerRate;

    /**
     * Is edge point or not.
     */
    @AString(require = false, scope = "true,false")
    private String isEdgePoint;

    /**
     * Port index. A unique index of network element port collected form equipment. Do not use
     * unstable MibIndex, for example the index changes when the host restart.
     */
    @AString(require = false, min = 0, max = 255)
    private String portIndex;

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
     * IP address. If the port has multiple address, record main address.
     */
    @AString(require = false, min = 0, max = 128)
    private String ipAddress;

    /**
     * Port flow's direction. Value: D_NA: Unknown, D_BIDIRECTIONAL: Carries Traffic
     * Bi-directionally(transmit and receive), D_SOURCE: Carries Sources
     * Traffic(transmit), D_SINK: Carries Sinks Traffic(receive).
     */
    @AString(require = false, scope = "D_NA,D_BIDIRECTIONAL,D_SOURCE,D_SINK")
    private String direction;

    /**
     * Physical bandwidth without the unit, the default is bps.
     */
    @AString(require = false, min = 0, max = 64)
    private String phyBW;

    /**
     * IP address mask.
     */
    @AString(require = false, min = 0, max = 64)
    private String ipMask;

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
     * Mac address
     */
    private String macAddress;

    /**
     * PE device need to be given to tenant
     */
    private String tenantID;

    /**
     * usage state: "unused" when is not used and "used" when is used
     */
    private String usageState;

    /**
     * layer rate.
     */
    private String containedLayers;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public LogicalTernminationPointMO() {
        super();
    }

    /**
     * Get meID attribute.<br>
     * 
     * @return meID attribute
     * @since SDNO 0.5
     */
    public String getMeID() {
        return meID;
    }

    /**
     * Set meID attribute.<br>
     * 
     * @param meID String Object
     * @since SDNO 0.5
     */
    public void setMeID(String meID) {
        this.meID = meID;
    }

    /**
     * Get logicalType attribute.<br>
     * 
     * @return logicalType attribute
     * @since SDNO 0.5
     */
    public String getLogicalType() {
        return logicalType;
    }

    /**
     * Set logicalType attribute.<br>
     * 
     * @param logicalType String Object
     * @since SDNO 0.5
     */
    public void setLogicalType(String logicalType) {
        this.logicalType = logicalType;
    }

    /**
     * Get layerRate attribute.<br>
     * 
     * @return layerRate attribute
     * @since SDNO 0.5
     */
    public String getLayerRate() {
        return layerRate;
    }

    /**
     * Set layerRate attribute.<br>
     * 
     * @param layerRate String Object
     * @since SDNO 0.5
     */
    public void setLayerRate(String layerRate) {
        this.layerRate = layerRate;
    }

    /**
     * Get isEdgePoint attribute.<br>
     * 
     * @return isEdgePoint attribute
     * @since SDNO 0.5
     */
    public String getIsEdgePoint() {
        return isEdgePoint;
    }

    /**
     * Set isEdgePoint attribute.<br>
     * 
     * @param isEdgePoint String Object
     * @since SDNO 0.5
     */
    public void setIsEdgePoint(String isEdgePoint) {
        this.isEdgePoint = isEdgePoint;
    }

    /**
     * Get portIndex attribute.<br>
     * 
     * @return portIndex attribute
     * @since SDNO 0.5
     */
    public String getPortIndex() {
        return portIndex;
    }

    /**
     * Set portIndex attribute.<br>
     * 
     * @param portIndex String Object
     * @since SDNO 0.5
     */
    public void setPortIndex(String portIndex) {
        this.portIndex = portIndex;
    }

    /**
     * Get source attribute.<br>
     * 
     * @return source attribute
     * @since SDNO 0.5
     */
    public String getSource() {
        return source;
    }

    /**
     * Set source attribute.<br>
     * 
     * @param source String Object
     * @since SDNO 0.5
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Get owner attribute.<br>
     * 
     * @return owner attribute
     * @since SDNO 0.5
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set owner attribute.<br>
     * 
     * @param owner String Object
     * @since SDNO 0.5
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Get ipAddress attribute.<br>
     * 
     * @return ipAddress attribute
     * @since SDNO 0.5
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Set ipAddress attribute.<br>
     * 
     * @param ipAddress String Object
     * @since SDNO 0.5
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Get direction attribute.<br>
     * 
     * @return direction attribute
     * @since SDNO 0.5
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Set direction attribute.<br>
     * 
     * @param direction String Object
     * @since SDNO 0.5
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Get phyBW attribute.<br>
     * 
     * @return phyBW attribute
     * @since SDNO 0.5
     */
    public String getPhyBW() {
        return phyBW;
    }

    /**
     * Set phyBW attribute.<br>
     * 
     * @param phyBW String Object
     * @since SDNO 0.5
     */
    public void setPhyBW(String phyBW) {
        this.phyBW = phyBW;
    }

    /**
     * Get ipMask attribute.<br>
     * 
     * @return ipMask attribute
     * @since SDNO 0.5
     */
    public String getIpMask() {
        return ipMask;
    }

    /**
     * Set ipMask attribute.<br>
     * 
     * @param ipMask String Object
     * @since SDNO 0.5
     */
    public void setIpMask(String ipMask) {
        this.ipMask = ipMask;
    }

    /**
     * Get adminState attribute.<br>
     * 
     * @return adminState attribute
     * @since SDNO 0.5
     */
    public String getAdminState() {
        return adminState;
    }

    /**
     * Set adminState attribute.<br>
     * 
     * @param adminState String Object
     * @since SDNO 0.5
     */
    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }

    /**
     * Get operState attribute.<br>
     * 
     * @return operState attribute
     * @since SDNO 0.5
     */
    public String getOperState() {
        return operState;
    }

    /**
     * Set operState attribute.<br>
     * 
     * @param operState String Object
     * @since SDNO 0.5
     */
    public void setOperState(String operState) {
        this.operState = operState;
    }

    /**
     * Get MOKEY attribute.<br>
     * 
     * @return MOKEY attribute
     * @since SDNO 0.5
     */
    public static String getMokey() {
        return MOKEY;
    }

    /**
     * Get nativeID attribute.<br>
     * 
     * @return nativeID attribute
     * @since SDNO 0.5
     */
    public String getNativeID() {
        return nativeID;
    }

    /**
     * Set nativeID attribute.<br>
     * 
     * @param nativeID String Object
     * @since SDNO 0.5
     */
    public void setNativeID(String nativeID) {
        this.nativeID = nativeID;
    }

    /**
     * Get macAddress attribute.<br>
     * 
     * @return macAddress attribute
     * @since SDNO 0.5
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Set macAddress attribute.<br>
     * 
     * @param macAddress String Object
     * @since SDNO 0.5
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Get tenantID attribute.<br>
     * 
     * @return tenantID attribute
     * @since SDNO 0.5
     */
    public String getTenantID() {
        return tenantID;
    }

    /**
     * Set tenantID attribute.<br>
     * 
     * @param tenantID String Object
     * @since SDNO 0.5
     */
    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    /**
     * Get usageState attribute.<br>
     * 
     * @return usageState attribute
     * @since SDNO 0.5
     */
    public String getUsageState() {
        return usageState;
    }

    /**
     * Set usageState attribute.<br>
     * 
     * @param usageState String Object
     * @since SDNO 0.5
     */
    public void setUsageState(String usageState) {
        this.usageState = usageState;
    }

    /**
     * Get containedLayers attribute.<br>
     * 
     * @return containedLayers attribute
     * @since SDNO 0.5
     */
    public String getContainedLayers() {
        return containedLayers;
    }

    /**
     * Set containedLayers attribute.<br>
     * 
     * @param containedLayers String Object
     * @since SDNO 0.5
     */
    public void setContainedLayers(String containedLayers) {
        this.containedLayers = containedLayers;
    }

    /**
     * Override toJsonBody Function.<br>
     * 
     * @return toJsonBody Content
     * @since SDNO 0.5
     */
    @Override
    public String toJsonBody() {
        Map<String, Object> moListMap = new HashMap<String, Object>();
        moListMap.put(MOKEY, this);
        return JsonUtil.toJson(moListMap);
    }

    /**
     * Override toString Function.<br>
     * 
     * @return toString description
     * @since SDNO 0.5
     */
    @Override
    public String toString() {
        return "LTP [id=" + id + ", name=" + name + ", description=" + description + ", meID=" + meID + ", layerRate="
                + layerRate + ", ipAddress=" + ipAddress + ", portIndex=" + portIndex + ", owner=" + owner
                + ", adminState=" + adminState + ", operState=" + operState + ",logicalType=" + logicalType
                + ",isEdgePoint=" + isEdgePoint + ", source=" + source + ",direction=" + direction + ",phyBW=" + phyBW
                + ",ipMask=" + ipMask + "]";
    }
}
