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

import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;

/**
 * The base route model,is the parent model of NetRoute and PolicyRoute.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 10, 2017
 */
public class BaseRoute extends ServiceModel {

    private String srcNeId;

    private String srcNeRole;

    private String connectionId;

    private String type;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public BaseRoute() {
        super();
    }

    public String getSrcNeId() {
        return srcNeId;
    }

    public void setSrcNeId(String srcNeId) {
        this.srcNeId = srcNeId;
    }

    public String getSrcNeRole() {
        return srcNeRole;
    }

    public void setSrcNeRole(String srcNeRole) {
        this.srcNeRole = srcNeRole;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
