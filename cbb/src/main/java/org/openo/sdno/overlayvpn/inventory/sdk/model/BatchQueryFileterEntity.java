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

package org.openo.sdno.overlayvpn.inventory.sdk.model;

/**
 * The definition of batch query filter. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class BatchQueryFileterEntity {

    private String filterDsc;

    private String filterData;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public BatchQueryFileterEntity() {
        this.filterDsc = "";

        this.filterData = "";
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
