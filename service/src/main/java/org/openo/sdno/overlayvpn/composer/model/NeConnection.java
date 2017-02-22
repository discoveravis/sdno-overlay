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

package org.openo.sdno.overlayvpn.composer.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnGateway;
import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseSite;
import org.openo.sdno.overlayvpn.servicemodel.enums.CpeRole;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.openo.sdno.overlayvpn.util.ModelUtils;

/**
 * The ne connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 22, 2017
 */
public class NeConnection extends ServiceModel {

    public static final String KEY_VNI = "vni";

    public static final String KEY_REGIONID = "regionId";

    private NbiServiceConnection parentConnection;

    private String connectionId;

    private String connectionType;

    private Template srcTemplate;

    private Template destTemplate;

    private String srcNeId;

    private String destNeId;

    private String srcNeRole;

    private String destNeRole;

    private BaseSite srcSite;

    private BaseSite destSite;

    private NbiVpnGateway srcGateway;

    private NbiVpnGateway destGateway;

    private Map<String, Object> extAttrs = new HashMap<String, Object>();

    public NeConnection() {
        super();
    }

    /**
     * Constructor.<br>
     * 
     * @param connection The service connection data that to be set
     * @since SDNO 0.5
     */
    public NeConnection(NbiServiceConnection connection) {
        super();
        ModelUtils.copyAttributes(connection, this);
        this.setParentConnection(connection);
        this.setConnectionId(connection.getId());
        this.setConnectionType(connection.getClass().getSimpleName());

        if(connection instanceof NbiVpnConnection) {
            NbiVpnConnection vpnConnection = (NbiVpnConnection)connection;
            if(StringUtils.isNotEmpty(vpnConnection.getVni())) {
                this.extAttrs.put(KEY_VNI, vpnConnection.getVni());
            }
        }
    }

    /**
     * Set source information.<br>
     * 
     * @param srcNeId The source ne uuid
     * @param srcNeRole The source ne role
     * @param srcSite The source site
     * @param srcGateway The source vpn gateway
     * @param srcTemplate The source template
     * @since SDNO 0.5
     */
    public void setSrc(String srcNeId, String srcNeRole, BaseSite srcSite, NbiVpnGateway srcGateway,
            Template srcTemplate) {
        this.srcNeId = srcNeId;
        this.srcNeRole = srcNeRole;
        this.srcSite = srcSite;
        this.srcGateway = srcGateway;
        this.srcTemplate = srcTemplate;
        initRegion(srcNeRole, srcGateway);
    }

    /**
     * Set destination information.<br>
     * 
     * @param destNeId The destination ne uuid
     * @param destNeRole The destination ne role
     * @param destSite The destination site
     * @param destGateway The destination vpn gateway
     * @param destTemplate The destination template
     */
    public void setDest(String destNeId, String destNeRole, BaseSite destSite, NbiVpnGateway destGateway,
            Template destTemplate) {
        this.destNeId = destNeId;
        this.destNeRole = destNeRole;
        this.destSite = destSite;
        this.destGateway = destGateway;
        this.destTemplate = destTemplate;
        initRegion(destNeRole, destGateway);
    }

    /**
     * Reverse the data of source and destination.<br>
     * 
     * @since SDNO 0.5
     */
    public void reverse() {
        String tmpNeId = destNeId;
        String tmpNeRole = destNeRole;
        BaseSite tmpSite = destSite;
        NbiVpnGateway tmpGateway = destGateway;
        Template template = destTemplate;
        this.destNeId = this.srcNeId;
        this.destNeRole = this.srcNeRole;
        this.destSite = this.srcSite;
        this.destGateway = this.srcGateway;
        this.destTemplate = this.srcTemplate;
        this.srcNeId = tmpNeId;
        this.srcNeRole = tmpNeRole;
        this.srcSite = tmpSite;
        this.srcGateway = tmpGateway;
        this.srcTemplate = template;
    }

    public NbiServiceConnection getParentConnection() {
        return parentConnection;
    }

    public void setParentConnection(NbiServiceConnection parentConnection) {
        this.parentConnection = parentConnection;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public Template getSrcTemplate() {
        return srcTemplate;
    }

    public void setSrcTemplate(Template srcTemplate) {
        this.srcTemplate = srcTemplate;
    }

    public Template getDestTemplate() {
        return destTemplate;
    }

    public void setDestTemplate(Template destTemplate) {
        this.destTemplate = destTemplate;
    }

    public String getSrcNeId() {
        return srcNeId;
    }

    public void setSrcNeId(String srcNeId) {
        this.srcNeId = srcNeId;
    }

    public String getDestNeId() {
        return destNeId;
    }

    public void setDestNeId(String destNeId) {
        this.destNeId = destNeId;
    }

    public String getSrcNeRole() {
        return srcNeRole;
    }

    public void setSrcNeRole(String srcNeRole) {
        this.srcNeRole = srcNeRole;
    }

    public String getDestNeRole() {
        return destNeRole;
    }

    public void setDestNeRole(String destNeRole) {
        this.destNeRole = destNeRole;
    }

    public BaseSite getSrcSite() {
        return srcSite;
    }

    public void setSrcSite(BaseSite srcSite) {
        this.srcSite = srcSite;
    }

    public BaseSite getDestSite() {
        return destSite;
    }

    public void setDestSite(BaseSite destSite) {
        this.destSite = destSite;
    }

    public NbiVpnGateway getSrcGateway() {
        return srcGateway;
    }

    public void setSrcGateway(NbiVpnGateway srcGateway) {
        this.srcGateway = srcGateway;
    }

    public NbiVpnGateway getDestGateway() {
        return destGateway;
    }

    public void setDestGateway(NbiVpnGateway destGateway) {
        this.destGateway = destGateway;
    }

    public Map<String, Object> getExtAttrs() {
        return extAttrs;
    }

    public void setExtAttrs(Map<String, Object> extAttrs) {
        this.extAttrs = extAttrs;
    }

    private void initRegion(String neRole, NbiVpnGateway gateway) {
        if(CpeRole.VPC.getRole().equalsIgnoreCase(neRole) && StringUtils.isNotEmpty(gateway.getRegionId())) {
            this.extAttrs.put(KEY_REGIONID, gateway.getRegionId());
        }
    }
}
