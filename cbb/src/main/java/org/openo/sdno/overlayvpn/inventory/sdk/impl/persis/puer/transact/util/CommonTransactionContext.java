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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class of common transaction context.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public abstract class CommonTransactionContext implements ITransactionContext {

    TransactionOperateType operateType;

    Map<String, ITransactionContext> childContext = null;

    List<ITransactionListener> listener = new ArrayList<ITransactionListener>();

    @Override
    public TransactionOperateType getOperateType() {
        return operateType;
    }

    public void setOperateType(TransactionOperateType operateType) {
        this.operateType = operateType;
    }

    @Override
    public void addContext(String name, ITransactionContext context) {

        synchronized(TransactionMgr.class) {
            if(childContext == null) {
                childContext = new HashMap<String, ITransactionContext>();
            }

        }
        childContext.put(name, context);
    }

    @Override
    public ITransactionContext getContext(String name) {
        if(childContext != null) {
            return childContext.get(name);
        } else {
            return null;
        }
    }

}
