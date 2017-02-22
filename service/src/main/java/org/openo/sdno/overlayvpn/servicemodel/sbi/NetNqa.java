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

package org.openo.sdno.overlayvpn.servicemodel.sbi;

import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOInvField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONSBIField;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;

/**
 * The nqa model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
@MOResType(infoModelName = "net_nqa")
public class NetNqa extends ServiceModel {

    private String neId;

    private String neRole;

    @NONSBIField
    private String connectionId;

    @NONSBIField
    private String connectionType;

    @NONSBIField
    private String vpnConnectionId;

    private String testType;

    private String dstIp;

    private String dstPortName;

    private String srcIp;

    private String srcPortName;

    private String frequency;

    private String probeCount;

    private String timeout;

    @MOInvField(invName = "nqainterval")
    private String interval;

    private String ttl;

    private String tos;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public NetNqa() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @param connection The connection contains field to set
     * @param srcIP The source IP to set
     * @param dstIP The destination IP to set
     * @param srcPort The source port to set
     * @since SDNO 0.5
     */
    public NetNqa(NbiNetConnection connection, String srcIP, String dstIP, String srcPort) {
        super();
        setId(UuidUtils.createUuid());
        this.srcIp = srcIP;
        this.dstIp = dstIP;
        this.srcPortName = srcPort;
        this.testType = "ping";
        this.setDeployStatus(DeployState.DEPLOY.getState());
        if(connection != null) {
            this.connectionId = connection.getId();
            this.connectionType = connection.getType();
            this.vpnConnectionId = connection.getConnectionId();
            this.neId = connection.getSrcNeId();
            this.neRole = connection.getSrcNeRole();
            this.setTenantId(connection.getTenantId());
        }
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getNeRole() {
        return neRole;
    }

    public void setNeRole(String neRole) {
        this.neRole = neRole;
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

    public String getVpnConnectionId() {
        return vpnConnectionId;
    }

    public void setVpnConnectionId(String vpnConnectionId) {
        this.vpnConnectionId = vpnConnectionId;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    public String getDstPortName() {
        return dstPortName;
    }

    public void setDstPortName(String dstPortName) {
        this.dstPortName = dstPortName;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    public String getSrcPortName() {
        return srcPortName;
    }

    public void setSrcPortName(String srcPortName) {
        this.srcPortName = srcPortName;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getProbeCount() {
        return probeCount;
    }

    public void setProbeCount(String probeCount) {
        this.probeCount = probeCount;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getTos() {
        return tos;
    }

    public void setTos(String tos) {
        this.tos = tos;
    }

}
