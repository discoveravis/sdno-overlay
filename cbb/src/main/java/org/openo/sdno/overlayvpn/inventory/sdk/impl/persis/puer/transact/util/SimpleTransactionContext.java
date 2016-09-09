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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util;

/**
 * Class to store simple transaction context.<br>
 * 
 * @param <T> Data Class
 * @author
 * @version SDNO 0.5 2016-4-25
 */
public class SimpleTransactionContext<T> extends CommonTransactionContext {

    private T data;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param data Context data
     */
    public SimpleTransactionContext(T data) {
        super();
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
