/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.model.netmodel.servicechain;

import java.util.List;

import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;

/**
 * NetServiceChainPath model class.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 19, 2016
 */
public class NetServiceChainPath extends AbstUuidModel {

    private String name;

    private String description;

    private boolean symmetric;

    private String transportType;

    private String scfNeId;

    private List<NetServicePathHop> servicePathHop;

    private List<NetServiceClassifer> classifiers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSymmetric() {
        return symmetric;
    }

    public void setSymmetric(boolean symmetric) {
        this.symmetric = symmetric;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getScfNeId() {
        return scfNeId;
    }

    public void setScfNeId(String scfNeId) {
        this.scfNeId = scfNeId;
    }

    public List<NetServicePathHop> getServicePathHop() {
        return servicePathHop;
    }

    public void setServicePathHop(List<NetServicePathHop> servicePathHop) {
        this.servicePathHop = servicePathHop;
    }

    public List<NetServiceClassifer> getClassifiers() {
        return classifiers;
    }

    public void setClassifiers(List<NetServiceClassifer> classifiers) {
        this.classifiers = classifiers;
    }

}
