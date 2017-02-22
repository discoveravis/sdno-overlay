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

package org.openo.sdno.overlayvpn.servicemodel.base;

/**
 * The portVlan model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 11, 2017
 */
public class PortVlan {

    private String neId;

    private String port;

    private String vlan;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public PortVlan() {
        super();
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("neId=");
        sb.append(neId);
        sb.append(',');
        sb.append("port=");
        sb.append(port);
        sb.append(',');
        sb.append("vlan=");
        sb.append(vlan);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !PortVlan.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        return this.toString().equals(obj.toString());
    }

}
