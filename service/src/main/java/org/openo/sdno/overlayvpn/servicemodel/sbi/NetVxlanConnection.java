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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;

/**
 * The vxlan connection model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
@MOResType(infoModelName = "net_vxlanconnection")
public class NetVxlanConnection extends NbiNetConnection {

    private String vni;

    private String portVlanList;

    private String srcVbdifIp;

    private String destVbdifIp;

    @NONInvField
    private String srcPortIp;

    @NONInvField
    private String destPortIp;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public NetVxlanConnection() {
        super();
    }

    @Override
    public NbiNetConnection toQueryCondition() throws ServiceException {
        NbiNetConnection netConnection = super.toQueryCondition();
        ((NetVxlanConnection)netConnection).setVni(vni);
        return netConnection;
    }

    public String getVni() {
        return vni;
    }

    public void setVni(String vni) {
        this.vni = vni;
    }

    public String getPortVlanList() {
        return portVlanList;
    }

    public void setPortVlanList(String portVlanList) {
        this.portVlanList = portVlanList;
    }

    public String getSrcVbdifIp() {
        return srcVbdifIp;
    }

    public void setSrcVbdifIp(String srcVbdifIp) {
        this.srcVbdifIp = srcVbdifIp;
    }

    public String getDestVbdifIp() {
        return destVbdifIp;
    }

    public void setDestVbdifIp(String destVbdifIp) {
        this.destVbdifIp = destVbdifIp;
    }

    public String getSrcPortIp() {
        return srcPortIp;
    }

    public void setSrcPortIp(String srcPortIp) {
        this.srcPortIp = srcPortIp;
    }

    public String getDestPortIp() {
        return destPortIp;
    }

    public void setDestPortIp(String destPortIp) {
        this.destPortIp = destPortIp;
    }

}
