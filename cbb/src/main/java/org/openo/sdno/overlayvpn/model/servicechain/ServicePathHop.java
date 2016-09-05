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

package org.openo.sdno.overlayvpn.model.servicechain;

/**
 * ServicePathHop model class.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Aug 19, 2016
 */
public class ServicePathHop {

    private Integer hopNumber;

    private String sfiId;

    private String sfgId;

    public Integer getHopNumber() {
        return hopNumber;
    }

    public void setHopNumber(Integer hopNumber) {
        this.hopNumber = hopNumber;
    }

    public String getSfiId() {
        return sfiId;
    }

    public void setSfiId(String sfiId) {
        this.sfiId = sfiId;
    }

    public String getSfgId() {
        return sfgId;
    }

    public void setSfgId(String sfgId) {
        this.sfgId = sfgId;
    }
}
