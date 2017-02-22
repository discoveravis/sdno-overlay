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

import org.openo.sdno.overlayvpn.model.v2.overlay.NbiConnectionRelation;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;

/**
 * Connection group.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
public class ConnectionGroup {

    /**
     * Connection type,is VpnConnection or InternalConnection.
     */
    private String connectionType;

    /**
     * The connection list to be created.
     */
    private List<NbiNetConnection> create = new ArrayList<>();

    /**
     * The connection list to be updated.
     */
    private List<NbiNetConnection> update = new ArrayList<>();

    /**
     * The connection list to be deleted.
     */
    private List<NbiNetConnection> delete = new ArrayList<>();

    private List<NbiConnectionRelation> relation = new ArrayList<>();

    private List<NeConnection> neConnections = new ArrayList<>();

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public ConnectionGroup() {
        super();
    }

    /**
     * Add a group's all connection to destination group.<br>
     * 
     * @param group The source group to be added
     * @since SDNO 0.5
     */
    public void append(ConnectionGroup group) {
        create.addAll(group.getCreate());
        update.addAll(group.getUpdate());
        delete.addAll(group.getDelete());
        relation.addAll(group.getRelation());
        neConnections.addAll(group.getNeConnections());
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public List<NbiNetConnection> getCreate() {
        return create;
    }

    public void setCreate(List<NbiNetConnection> create) {
        this.create = create;
    }

    public List<NbiNetConnection> getUpdate() {
        return update;
    }

    public void setUpdate(List<NbiNetConnection> update) {
        this.update = update;
    }

    public List<NbiNetConnection> getDelete() {
        return delete;
    }

    public void setDelete(List<NbiNetConnection> delete) {
        this.delete = delete;
    }

    public List<NbiConnectionRelation> getRelation() {
        return relation;
    }

    public void setRelation(List<NbiConnectionRelation> relation) {
        this.relation = relation;
    }

    public List<NeConnection> getNeConnections() {
        return neConnections;
    }

    public void setNeConnections(List<NeConnection> neConnections) {
        this.neConnections = neConnections;
    }
}
