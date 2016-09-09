/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.model.servicechain;

import java.util.List;

import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;

/**
 * ServiceChainPath model class.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 19, 2016
 */
public class ServiceChainPath extends AbstUuidModel {

    private String name;

    private String description;

    private String sfcName;

    private String sfcId;

    private String popId;

    private String scfNeId;

    private boolean symmetric;

    private String transportType;

    private List<ServicePathHop> servicePathHop;

    private List<ServiceClassifer> classifiers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSfcName() {
        return sfcName;
    }

    public void setSfcName(String sfcName) {
        this.sfcName = sfcName;
    }

    public String getSfcId() {
        return sfcId;
    }

    public void setSfcId(String sfcId) {
        this.sfcId = sfcId;
    }

    public String getScfNeId() {
        return scfNeId;
    }

    public void setScfNeId(String scfNeId) {
        this.scfNeId = scfNeId;
    }

    public List<ServiceClassifer> getClassifiers() {
        return classifiers;
    }

    public void setClassifiers(List<ServiceClassifer> classifiers) {
        this.classifiers = classifiers;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public List<ServicePathHop> getServicePathHop() {
        return servicePathHop;
    }

    public void setServicePathHop(List<ServicePathHop> servicePathHop) {
        this.servicePathHop = servicePathHop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPopId() {
        return popId;
    }

    public void setPopId(String popId) {
        this.popId = popId;
    }

    public boolean getSymmetric() {
        return symmetric;
    }

    public void setSymmetric(boolean symmetric) {
        this.symmetric = symmetric;
    }

}
