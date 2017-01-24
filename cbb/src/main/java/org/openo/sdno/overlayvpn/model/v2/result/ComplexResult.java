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

package org.openo.sdno.overlayvpn.model.v2.result;

import java.util.List;

/**
 * Class of Complex Result Data.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-9
 */
public class ComplexResult<T> {

    private long total;

    private int curIndex;

    private int pageCapacity;

    private List<T> data;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public ComplexResult() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @param total total counts of result data
     * @param curIndex current data index
     * @param pageCapacity page capacity
     * @since SDNO 0.5
     */
    public ComplexResult(long total, int curIndex, int pageCapacity) {
        super();
        this.total = total;
        this.curIndex = curIndex;
        this.pageCapacity = pageCapacity;
    }

    /**
     * Constructor<br>
     * 
     * @param result other result data
     * @param data list of data
     * @since SDNO 0.5
     */
    public ComplexResult(ComplexResult<?> result, List<T> data) {
        super();
        this.total = result.total;
        this.curIndex = result.curIndex;
        this.pageCapacity = result.pageCapacity;
        this.data = data;
    }

    /**
     * Constructor<br>
     * 
     * @param total total counts of result data
     * @param curIndex current data index
     * @param pageCapacity page capacity
     * @param data list of data
     * @since SDNO 0.5
     */
    public ComplexResult(long total, int curIndex, int pageCapacity, List<T> data) {
        super();
        this.total = total;
        this.curIndex = curIndex;
        this.pageCapacity = pageCapacity;
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCurIndex() {
        return curIndex;
    }

    public void setCurIndex(int curIndex) {
        this.curIndex = curIndex;
    }

    public int getPageCapacity() {
        return pageCapacity;
    }

    public void setPageCapacity(int pageCapacity) {
        this.pageCapacity = pageCapacity;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
