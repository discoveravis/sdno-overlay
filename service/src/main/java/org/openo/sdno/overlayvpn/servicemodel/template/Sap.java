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

/**
 * The sap model,represent the two sides of connection.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class Sap {

    private String name;

    private String type;

    private String interFace;

    private String ingress;

    private String role;

    private String adminStatus;

    private String staticRoute;

    private String nqa;

    private String ip;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public Sap() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @param spec The spec contains field to set
     * @since SDNO 0.5
     */
    public Sap(Spec spec) {
        this.name = spec.getName();
        for(TemplateField field : spec.getFieldList()) {
            if(field.getName().equals("type")) {
                this.type = field.getValue();
                continue;
            }
            if(field.getName().equals("role")) {
                this.role = field.getValue();
                continue;
            }
            if(field.getName().equals("staticRoute")) {
                this.staticRoute = field.getValue();
                continue;
            }
            if(field.getName().equals("interFace")) {
                this.interFace = field.getValue();
                continue;
            }
            if(field.getName().equals("ingress")) {
                this.ingress = field.getValue();
                continue;
            }
            if(field.getName().equals("nqa")) {
                this.nqa = field.getValue();
                continue;
            }
            if(field.getName().equals("ip")) {
                this.ip = field.getValue();
                continue;
            }
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

    public String getInterFace() {
        return interFace;
    }

    public void setInterFace(String interFace) {
        this.interFace = interFace;
    }

    public String getIngress() {
        return ingress;
    }

    public void setIngress(String ingress) {
        this.ingress = ingress;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getStaticRoute() {
        return staticRoute;
    }

    public void setStaticRoute(String staticRoute) {
        this.staticRoute = staticRoute;
    }

    public String getNqa() {
        return nqa;
    }

    public void setNqa(String nqa) {
        this.nqa = nqa;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
