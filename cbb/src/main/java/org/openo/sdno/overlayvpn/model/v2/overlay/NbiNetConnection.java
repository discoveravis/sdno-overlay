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

package org.openo.sdno.overlayvpn.model.v2.overlay;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONSBIField;
import org.openo.sdno.overlayvpn.util.ModelUtils;

/**
 * Network side connection model<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class NbiNetConnection extends NbiBaseConnection {

    private String type;

    private String srcNeId;

    private String destNeId;

    private String srcNeRole;

    private String destNeRole;

    private String connectionId;

    private String srcPortName;

    private String destPortName;

    @NONSBIField
    @JsonIgnore
    private String srcSiteId;

    @NONSBIField
    @JsonIgnore
    private String destSiteId;

    @NONInvField
    @NONSBIField
    @JsonIgnore
    private List<NbiConnectionRelation> parentConnections;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public NbiNetConnection() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @param srcNeId The source neId to set
     * @param srcNeRole The source neRole to set
     * @param destNeId The destination neId to set
     * @param destNeRole The destination neRole to set
     * @since SDNO 0.5
     */
    public NbiNetConnection(String srcNeId, String srcNeRole, String destNeId, String destNeRole) {
        super();
        this.srcNeId = srcNeId;
        this.srcNeRole = srcNeRole;
        this.destNeId = destNeId;
        this.destNeRole = destNeRole;
    }

    /**
     * Build query condition model.<br>
     * 
     * @return The query condition model
     * @throws ServiceException when construct model failed
     * @since SDNO 0.5
     */
    public NbiNetConnection toQueryCondition() throws ServiceException {
        NbiNetConnection cond = ModelUtils.newInstance(this.getClass());
        cond.setSrcNeId(this.getSrcNeId());
        cond.setDestNeId(this.getDestNeId());
        cond.setType(this.getType());
        cond.setSrcPortName(this.getSrcPortName());
        cond.setDestPortName(this.getDestPortName());
        return cond;
    }

    /**
     * Reverse the source and destination.<br>
     * 
     * @return The reversed model
     * @throws ServiceException when construct model failed
     * @since SDNO 0.5
     */
    public NbiNetConnection toReverse() throws ServiceException {
        NbiNetConnection conn = ModelUtils.newInstance(this.getClass());
        ModelUtils.copyAttributes(this, conn);
        String srcNeId = conn.getSrcNeId();
        String srcNeRole = conn.getSrcNeRole();
        String srcPort = conn.getSrcPortName();
        String srcSite = conn.getSrcSiteId();

        conn.setSrcNeId(conn.getDestNeId());
        conn.setSrcNeRole(conn.getDestNeRole());
        conn.setSrcPortName(conn.getDestPortName());
        conn.setSrcSiteId(conn.getDestSiteId());

        conn.setDestNeId(srcNeId);
        conn.setDestNeRole(srcNeRole);
        conn.setDestPortName(srcPort);
        conn.setDestSiteId(srcSite);
        return conn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getSrcPortName() {
        return srcPortName;
    }

    public void setSrcPortName(String srcPortName) {
        this.srcPortName = srcPortName;
    }

    public String getDestPortName() {
        return destPortName;
    }

    public void setDestPortName(String destPortName) {
        this.destPortName = destPortName;
    }

    public String getSrcSiteId() {
        return srcSiteId;
    }

    public void setSrcSiteId(String srcSiteId) {
        this.srcSiteId = srcSiteId;
    }

    public String getDestSiteId() {
        return destSiteId;
    }

    public void setDestSiteId(String destSiteId) {
        this.destSiteId = destSiteId;
    }

    public List<NbiConnectionRelation> getParentConnections() {
        return parentConnections;
    }

    public void setParentConnections(List<NbiConnectionRelation> parentConnections) {
        this.parentConnections = parentConnections;
    }

}
