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

package org.openo.sdno.overlayvpn.brs.model;

import java.util.HashMap;
import java.util.Map;

import org.openo.sdno.framework.container.util.JsonUtil;

/**
 * Model class of pop data.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-01-10
 */
public class PopMO extends BaseMO {

    private static final String MOKEY = "pop";

    /**
     * Pop location
     */
    private String location;

    /**
     * Pop area
     */
    private String area;

    /**
     * Controller Id
     */
    private String controllerID;

    /**
     * Ip Address of Dc Gateway
     */
    private String dcGW;

    /**
     * Ip Address of Internet Gateway
     */
    private String internetGW;

    /**
     * Ip Address of Site Gateway
     */
    private String siteGW;

    /**
     * Managed Ip Adress of Pop
     */
    private String managedGW;

    /**
     * Description
     */
    private String description;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getControllerID() {
        return controllerID;
    }

    public void setControllerID(String controllerID) {
        this.controllerID = controllerID;
    }

    public String getDcGW() {
        return dcGW;
    }

    public void setDcGW(String dcGW) {
        this.dcGW = dcGW;
    }

    public String getInternetGW() {
        return internetGW;
    }

    public void setInternetGW(String internetGW) {
        this.internetGW = internetGW;
    }

    public String getSiteGW() {
        return siteGW;
    }

    public void setSiteGW(String siteGW) {
        this.siteGW = siteGW;
    }

    public String getManagedGW() {
        return managedGW;
    }

    public void setManagedGW(String managedGW) {
        this.managedGW = managedGW;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public static String getMokey() {
        return MOKEY;
    }

    @Override
    public String toJsonBody() {
        Map<String, Object> moListMap = new HashMap<String, Object>();
        moListMap.put(MOKEY, this);
        return JsonUtil.toJson(moListMap);
    }

}
