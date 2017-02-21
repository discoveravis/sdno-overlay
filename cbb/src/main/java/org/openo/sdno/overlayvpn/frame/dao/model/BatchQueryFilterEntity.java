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

package org.openo.sdno.overlayvpn.frame.dao.model;

/**
 * Batch query filter entity.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
public class BatchQueryFilterEntity {

    private String filterDsc;

    private String filterData;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public BatchQueryFilterEntity() {
        this.filterDsc = "";
        this.filterData = "";
    }

    /**
     * Constructor.<br>
     * 
     * @param filterDsc The filterDsc to set
     * @param filterData The filterData to set
     * @since SDNO 0.5
     */
    public BatchQueryFilterEntity(String filterDsc, String filterData) {
        this.filterDsc = filterDsc;
        this.filterData = filterData;
    }

    public String getFilterDsc() {
        return filterDsc;
    }

    public void setFilterDsc(String filterDsc) {
        this.filterDsc = filterDsc;
    }

    public String getFilterData() {
        return filterData;
    }

    public void setFilterData(String filterData) {
        this.filterData = filterData;
    }
}
