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

import org.openo.sdno.overlayvpn.servicemodel.enums.ConnectionType;
import org.openo.sdno.overlayvpn.servicemodel.enums.CpeRole;

/**
 * The topology of the connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
public class ConnectionTopology {

    /**
     * connection type
     */
    private ConnectionType type;

    private CpeRole srcRole;

    private CpeRole destRole;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public ConnectionTopology() {
        super();
    }

    public ConnectionType getType() {
        return type;
    }

    public void setType(ConnectionType type) {
        this.type = type;
    }

    public CpeRole getSrcRole() {
        return srcRole;
    }

    public void setSrcRole(CpeRole srcRole) {
        this.srcRole = srcRole;
    }

    public CpeRole getDestRole() {
        return destRole;
    }

    public void setDestRole(CpeRole destRole) {
        this.destRole = destRole;
    }
}
