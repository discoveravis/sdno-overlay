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

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.BaseServiceModel;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyRole;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of EndpointGroup Model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_endpointgroup")
public class EndpointGroup extends BaseServiceModel {

    /**
     * type
     */
    @AString(require = true, scope = "subnet,cidr,network,router,port,port-and-vlan,vni,cidr_with_l2access,vpc")
    private String type;

    /**
     * EndPoints are different for different types.
     * when type is CIDR, the format likes "ip/mask" , used by GRE
     * when type is network, this value is network id
     * when type is router, this value is router id
     */
    @AString(require = true, min = 1, max = 255)
    private String endpoints;

    /**
     * list of EndPoint
     */
    @NONInvField
    private List<String> endpointList;

    /**
     * Map of Port nativeId to VLAN
     */
    @NONInvField
    @JsonIgnore
    private Map<String, List<String>> portNativeIdToVlanMap;

    /**
     * Site Type
     */
    @NONInvField
    @JsonIgnore
    private String siteType;

    /**
     * id of related gateway
     */
    @AUuid(require = false)
    private String gatewayId;

    /**
     * id of network element
     */
    @AUuid(require = true)
    private String neId;

    /**
     * id of network element device
     */
    @JsonIgnore
    @AString(min = 1, max = 255)
    private String deviceId;

    /**
     * topology role of this EPG
     */
    @AString(scope = "none,hub,spoke")
    private String topologyRole = TopologyRole.NONE.getName();

    /**
     * id of related connection
     */
    @AUuid(require = true)
    private String connectionId;

    /**
     * id of related QosPolicy
     */
    @AUuid(require = false)
    private String qosPolicyId;

    /**
     * Gateway Data
     */
    @Valid
    @NONInvField
    private Gateway gateway;

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    public EndpointGroup() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param uuid UUid
     * @param tenantId tenant id
     */
    public EndpointGroup(String uuid, String tenantId) {
        super();
        this.setUuid(uuid);
        this.setTenantId(tenantId);
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param uuid UUid
     */
    public EndpointGroup(String uuid) {
        super();
        this.setUuid(uuid);
    }

    /**
     * Copy basic data from other Object.<br>
     * 
     * @param srcEpg source EndpointGroup Object
     * @since SDNO 0.5
     */
    public void copyBasicData(EndpointGroup srcEpg) {
        super.copyBasicData(srcEpg);
        this.type = srcEpg.getType();
        this.gatewayId = srcEpg.getGatewayId();
        this.topologyRole = srcEpg.getTopologyRole();
        this.connectionId = srcEpg.getConnectionId();
        this.qosPolicyId = srcEpg.getQosPolicyId();
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(String endpoints) {
        this.endpoints = endpoints;
    }

    public List<String> getEndpointList() {
        return endpointList;
    }

    public void setEndpointList(List<String> endpointList) {
        this.endpointList = endpointList;
    }

    public Map<String, List<String>> getPortNativeIdToVlanMap() {
        return portNativeIdToVlanMap;
    }

    public void setPortNativeIdToVlanMap(Map<String, List<String>> portNativeIdToVlanMap) {
        this.portNativeIdToVlanMap = portNativeIdToVlanMap;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTopologyRole() {
        return topologyRole;
    }

    public void setTopologyRole(String topologyRole) {
        this.topologyRole = topologyRole;
    }

    public String getQosPolicyId() {
        return qosPolicyId;
    }

    public void setQosPolicyId(String qosPolicyId) {
        this.qosPolicyId = qosPolicyId;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }
}
