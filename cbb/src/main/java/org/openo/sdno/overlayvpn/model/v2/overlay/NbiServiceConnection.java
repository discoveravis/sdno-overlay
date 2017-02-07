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

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;

/**
 * The service connection model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class NbiServiceConnection extends NbiBaseConnection {

    private String technologys;

    @NONInvField
    private List<NbiNetConnection> netConnections;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public NbiServiceConnection() {
        super();
    }

    public String getTechnologys() {
        return technologys;
    }

    public void setTechnologys(String technologys) {
        this.technologys = technologys;
    }

    public List<NbiNetConnection> getNetConnections() {
        return netConnections;
    }

    public void setNetConnections(List<NbiNetConnection> netConnections) {
        this.netConnections = netConnections;
    }
}
