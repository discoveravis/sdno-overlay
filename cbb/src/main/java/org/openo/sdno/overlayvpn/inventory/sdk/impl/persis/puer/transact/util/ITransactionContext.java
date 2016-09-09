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
 * Interface of transaction context.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public interface ITransactionContext {

    /**
     * Get the operation type of the corresponded context.<br>
     * 
     * @return Operation type
     * @since SDNO 0.5
     */
    TransactionOperateType getOperateType();

    /**
     * Add context to child context.<br>
     * 
     * @param name Context name
     * @param context Context value
     * @since SDNO 0.5
     */
    void addContext(String name, ITransactionContext context);

    /**
     * Get the context of the specified name.<br>
     * 
     * @param name Context name
     * @return Context
     * @since SDNO 0.5
     */
    ITransactionContext getContext(String name);

}
