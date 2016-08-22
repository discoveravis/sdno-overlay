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

package org.openo.sdno.overlayvpn.model.servicemodel;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Null;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOInvField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.BaseServiceModel;
import org.openo.sdno.overlayvpn.model.common.enums.topo.TopologyType;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.GreMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.IpsecMappingPolicy;
import org.openo.sdno.overlayvpn.model.servicemodel.mappingpolicy.VxlanMappingPolicy;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Class of OverlayVpn Connection Model.<br/>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
@MOResType(infoModelName = "overlayvpn_tenant_connection")
public class Connection extends BaseServiceModel {

    /**
     * topology of EndpointGroups
     * hub_spoke:spoke connected to hub,and spoke can connect to another spoke through hub
     * hub_spoke_disjoint:spoke connected to hub,and spoke can connect to another spoke
     */
    @AString(require = true, scope = "point_to_point,full_mesh,hub_spoke")
    private String topology = TopologyType.NONE.getName();

    /**
     * TechnologyType of this connection
     */
    @AString(require = true, scope = "ipsec,gre,vxlan,gre_over_ipsec,vxlan_over_ipsec")
    private String technology;

    /**
     * id of OverlayVpn this connection belongs to
     */
    @MOInvField(invName = "overlayVpnId")
    @AUuid(require = true)
    private String compositeVpnId;

    /**
     * id of MappingPolicy
     */
    @AUuid(require = false)
    private String mappingPolicyId;

    /**
     * List of EndpointGroup Id
     */
    @Null
    @NONInvField
    private List<String> epgIds;

    /**
     * List of EndpointGroup
     */
    @Valid
    @NONInvField
    private List<EndpointGroup> endpointGroups;

    /**
     * MappingPolicy of GRE
     */
    @Valid
    @NONInvField
    private GreMappingPolicy greMappingPolicy;

    /**
     * MappingPolicy of IpSec
     */
    @Valid
    @NONInvField
    private IpsecMappingPolicy ipsecMappingPolicy;

    /**
     * MappingPolicy of Vxlan
     */
    @Valid
    @NONInvField
    private VxlanMappingPolicy vxlanMappingPolicy;

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    public Connection() {
        super();
    }

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param connUuid
     */
    public Connection(String connUuid) {
        super();
        this.setUuid(connUuid);
    }

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param connUuid connection UUid
     * @param tenantId tenant id
     */
    public Connection(String connUuid, String tenantId) {
        super();
        this.setUuid(connUuid);
        this.setTenantId(tenantId);
    }

    public String getCompositeVpnId() {
        return compositeVpnId;
    }

    public void setCompositeVpnId(String compositeVpnId) {
        this.compositeVpnId = compositeVpnId;
    }

    public String getTopology() {
        return topology;
    }

    public void setTopology(String topology) {
        this.topology = topology;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getMappingPolicyId() {
        return mappingPolicyId;
    }

    public void setMappingPolicyId(String mappingPolicyId) {
        this.mappingPolicyId = mappingPolicyId;
    }

    public List<String> getEpgIds() {
        return epgIds;
    }

    public void setEpgIds(List<String> epgIds) {
        this.epgIds = epgIds;
    }

    public List<EndpointGroup> getEndpointGroups() {
        return endpointGroups;
    }

    public void setEndpointGroups(List<EndpointGroup> endpointGroups) {
        this.endpointGroups = endpointGroups;
    }

    public GreMappingPolicy getGreMappingPolicy() {
        return greMappingPolicy;
    }

    public void setGreMappingPolicy(GreMappingPolicy greMappingPolicy) {
        this.greMappingPolicy = greMappingPolicy;
    }

    public IpsecMappingPolicy getIpsecMappingPolicy() {
        return ipsecMappingPolicy;
    }

    public void setIpsecMappingPolicy(IpsecMappingPolicy ipsecMappingPolicy) {
        this.ipsecMappingPolicy = ipsecMappingPolicy;
    }

    public VxlanMappingPolicy getVxlanMappingPolicy() {
        return vxlanMappingPolicy;
    }

    public void setVxlanMappingPolicy(VxlanMappingPolicy vxlanMappingPolicy) {
        this.vxlanMappingPolicy = vxlanMappingPolicy;
    }

    /**
     * Copy Basic attribute from other Object.<br/>
     * 
     * @param data other connection data
     * @since SDNO 0.5
     */
    public void copyBasicData(Connection data) {
        super.copyBasicData(data);
        this.compositeVpnId = data.getCompositeVpnId();
        this.topology = data.getTopology();
        this.technology = data.getTechnology();
        this.mappingPolicyId = data.getMappingPolicyId();
    }

}
