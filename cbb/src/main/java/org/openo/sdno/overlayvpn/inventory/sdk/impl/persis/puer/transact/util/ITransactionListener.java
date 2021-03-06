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

import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * Interface of transaction listener.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public interface ITransactionListener {

    /**
     * Prepare transaction.<br>
     * 
     * @param context Transaction context
     * @return Prepare transaction result
     * @since SDNO 0.5
     */
    ResultRsp<?> prepare(ITransactionContext context);

    /**
     * Fire transaction.<br>
     * 
     * @param context Transaction context
     * @return Fire transaction result
     * @since SDNO 0.5
     */
    ResultRsp<?> fire(ITransactionContext context);

    /**
     * Commit transaction.<br>
     * 
     * @param context Transaction context
     * @return Commit transaction result
     * @since SDNO 0.5
     */
    ResultRsp<?> commit(ITransactionContext context);

    /**
     * Roll back transaction.<br>
     * 
     * @param context Transaction context
     * @return Roll back transaction result
     * @since SDNO 0.5
     */
    ResultRsp<?> rollback(ITransactionContext context);
}
