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

import java.util.ArrayList;
import java.util.Collection;

import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * This class only solves a simple type of transaction model, using one listener to process a series
 * of data, and requiring the listener is defined as the transaction of create type.<br>
 * 
 * @param <T> Data Class
 * @author
 * @version SDNO 0.5 2016-4-13
 */
public class SimpleTransactionMgr<T> {

    /**
     * Handling simple transactions. If there is a failure, all data should roll back.<br>
     * 
     * @param listener Transaction listener
     * @param transactData Collection of transaction data
     * @return Transaction success or failure
     * @since SDNO 0.5
     */
    public ResultRsp transact(ITransactionListener listener, Collection<T> transactData) {
        Collection<T> sucessData = new ArrayList<T>();
        for(T tdata : transactData) {
            SimpleTransactionContext context = new SimpleTransactionContext(tdata);
            context.setOperateType(TransactionOperateType.CREATE);
            ResultRsp result = listener.prepare(context);
            if(result.isSuccess()) {
                result = listener.fire(context);
                if(!result.isSuccess()) {
                    // There is a failure, need to roll back all.
                    rollbackDataCollection(listener, sucessData);
                    return result;
                } else {
                    sucessData.add(tdata);
                }
            }

        }
        return ResultRsp.SUCCESS;
    }

    private void rollbackDataCollection(ITransactionListener listener, Collection<T> sucessData) {
        for(T tdata : sucessData) {
            SimpleTransactionContext context = new SimpleTransactionContext(tdata);
            context.setOperateType(TransactionOperateType.CREATE);
            listener.rollback(context);
        }
    }
}
