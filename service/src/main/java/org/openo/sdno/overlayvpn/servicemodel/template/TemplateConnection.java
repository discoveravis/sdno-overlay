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

package org.openo.sdno.overlayvpn.servicemodel.template;

import java.util.ArrayList;
import java.util.List;

/**
 * The connection model in template.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class TemplateConnection {

    private static final String TECHNOLOGY = "technology";

    private static final String TOPOLOGY = "topology";

    private static final String ROUTE = "route";

    private static final String PROTECT = "protect";

    private static final String WORKTYPE = "worktype";

    private static final String QOSPRECLASSIFY = "qosPreClassify";

    private static final String ACCESSTYPE = "accessType";

    private String name;

    private String type;

    private String technology;

    private String topology;

    private String route;

    private String adminStatus;

    private String protect;

    private String workType;

    private String qosPreClassify;

    private String accessType;

    private List<Sap> saps;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public TemplateConnection() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @param spec The spec contains field to set
     * @since SDNO 0.5
     */
    public TemplateConnection(Spec spec) {
        this.name = spec.getName();
        this.type = spec.getType();
        for(TemplateField field : spec.getFieldList()) {
            if(field.getName().equals(TECHNOLOGY)) {
                this.setTechnology(field.getValue());
                continue;
            }
            if(field.getName().equals(TOPOLOGY)) {
                this.setTopology(field.getValue());
                continue;
            }
            if(field.getName().equals(ROUTE)) {
                this.setRoute(field.getValue());
                continue;
            }
            if(field.getName().equals(PROTECT)) {
                this.setProtect(field.getValue());
                continue;
            }
            if(field.getName().equals(WORKTYPE)) {
                this.setWorkType(field.getValue());
                continue;
            }
            if(field.getName().equals(QOSPRECLASSIFY)) {
                this.setQosPreClassify(field.getValue());
                continue;
            }
            if(field.getName().equals(ACCESSTYPE)) {
                this.setAccessType(field.getValue());
                continue;
            }
        }
        this.saps = new ArrayList<>();
        for(Spec sap : spec.getSpecList().get(0).getSpecList()) {
            saps.add(new Sap(sap));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTopology() {
        return topology;
    }

    public void setTopology(String topology) {
        this.topology = topology;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getProtect() {
        return protect;
    }

    public void setProtect(String protect) {
        this.protect = protect;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getQosPreClassify() {
        return qosPreClassify;
    }

    public void setQosPreClassify(String qosPreClassify) {
        this.qosPreClassify = qosPreClassify;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public List<Sap> getSaps() {
        return saps;
    }

    public void setSaps(List<Sap> saps) {
        this.saps = saps;
    }
}
