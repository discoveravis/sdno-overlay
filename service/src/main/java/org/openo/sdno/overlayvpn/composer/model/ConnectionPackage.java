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

import java.util.ArrayList;
import java.util.List;

import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiServiceConnection;

/**
 * The connection package model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 22, 2017
 */
public class ConnectionPackage {

    private String type;

    private NbiServiceConnection parentConnection;

    private List<NbiNetConnection> connections = new ArrayList<>();

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public ConnectionPackage() {
        super();
    }

    /**
     * Constructor.<br>
     * 
     * @param type The connection type to be set
     * @since SDNO 0.5
     */
    public ConnectionPackage(String type) {
        super();
        this.type = type;
        this.connections = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public NbiServiceConnection getParentConnection() {
        return parentConnection;
    }

    public void setParentConnection(NbiServiceConnection parentConnection) {
        this.parentConnection = parentConnection;
    }

    public List<NbiNetConnection> getConnections() {
        return connections;
    }

    public void setConnections(List<NbiNetConnection> connections) {
        this.connections = connections;
    }

}
