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

package org.openo.sdno.overlayvpn.model.v2.vxlan;

/**
 * Model class of VxLAN tunnel status.<br>
 * 
 * @author
 * @version SDNO 0.5 June 6, 2016
 */
public class SbiNeVxlanTunnelStatus {

    /**
     * Device Id
     */
    private String deviceId;

    /**
     * Local IpAddress
     */
    private String localIp;

    /**
     * Peer IpAddress
     */
    private String peerIp;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public String getPeerIp() {
        return peerIp;
    }

    public void setPeerIp(String peerIp) {
        this.peerIp = peerIp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SbiNeVxlanTunnelStatus {\n");

        sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
        sb.append("    localIp: ").append(toIndentedString(localIp)).append("\n");
        sb.append("    peerIp: ").append(toIndentedString(peerIp)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String toIndentedString(Object o) {
        if(o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
