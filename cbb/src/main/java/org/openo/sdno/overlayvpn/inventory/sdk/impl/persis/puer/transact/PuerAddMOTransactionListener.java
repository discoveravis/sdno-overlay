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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.ITransactionContext;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.ITransactionListener;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.SimpleTransactionContext;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.SimpleTransactionMgr;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.TransactionListenerAnnotation;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.TransactionOperateType;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for adding transaction listener of MO.<br>
 * 
 * @param <T> Inventory Model Class
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@TransactionListenerAnnotation(contextClass = PuerAddMOContext.class, operateType = TransactionOperateType.CREATE)
public class PuerAddMOTransactionListener<T> implements ITransactionListener {

    private static final int MAX_NUM_PER_PKG_CU = 1000;

    private static final Logger LOGGER = LoggerFactory.getLogger(PuerAddMOTransactionListener.class);

    @Override
    public ResultRsp<T> commit(ITransactionContext context) {
        return ResultRsp.SUCCESS;
    }

    @Override
    public ResultRsp<?> rollback(ITransactionContext context) {
        if(context instanceof PuerAddMOContext) {
            PuerAddMOContext<T> pcontext = (PuerAddMOContext)context;

            Map<Class, List<T>> molist = pcontext.getMOMap();
            ResultRsp result = ResultRsp.SUCCESS;
            Set<Entry<Class, List<T>>> moset = molist.entrySet();
            Iterator<Entry<Class, List<T>>> it = moset.iterator();

            while(it.hasNext()) {
                Entry<Class, List<T>> moEntry = it.next();
                try {
                    result = pcontext.getDao().delete(moEntry.getValue(), moEntry.getKey());
                    pcontext.setResult(result);
                } catch(ServiceException e) {
                    result.setErrorCode(ErrorCode.DB_OPERATION_ERROR);
                    LOGGER.warn("Delete MO to puer failed.", e);
                }
            }

            return result;

        }
        return ResultRsp.SUCCESS;
    }

    @Override
    public ResultRsp<?> fire(ITransactionContext context) {
        if(context instanceof PuerAddMOContext) {
            final PuerAddMOContext<T> pcontext = (PuerAddMOContext<T>)context;
            Collection<AddMOContextData> value = getSimpleContextData(pcontext);
            SimpleTransactionMgr mgr = new SimpleTransactionMgr();

            // For batch calling of transaction interfaces, so need to re-factor the interfaces.
            return mgr.transact(new TransactionListener(pcontext), value);

        }
        return ResultRsp.SUCCESS;
    }

    @Override
    public ResultRsp<T> prepare(ITransactionContext context) {
        return ResultRsp.SUCCESS;
    }

    private Collection<AddMOContextData> getSimpleContextData(PuerAddMOContext context) {
        Map<Class, List<T>> molist = context.getMOMap();
        Set<Entry<Class, List<T>>> moset = molist.entrySet();
        Iterator<Entry<Class, List<T>>> it = moset.iterator();
        Collection<AddMOContextData> allAddMOData = new ArrayList<AddMOContextData>();
        while(it.hasNext()) {
            Entry<Class, List<T>> moEntry = it.next();
            List<T> value = moEntry.getValue();
            int num = value.size();
            int count = (num / MAX_NUM_PER_PKG_CU) + ((num % MAX_NUM_PER_PKG_CU) > 0 ? 1 : 0);
            for(int i = 0; i < count; i++) {
                int toIndex = (i + 1) * MAX_NUM_PER_PKG_CU;
                List<T> subList = value.subList(i * MAX_NUM_PER_PKG_CU, toIndex > num ? num : toIndex);
                allAddMOData.add(new AddMOContextData(subList, moEntry.getKey()));
            }
        }
        return allAddMOData;
    }

    /**
     * Class of MO context data.<br>
     * 
     * @author
     * @version SDNO 0.5 2016-6-6
     */
    class AddMOContextData {

        private List<T> moList;

        private Class moType;

        /**
         * Constructor.<br>
         * 
         * @since SDNO 0.5
         * @param moList MO list
         * @param moType MO type
         */
        public AddMOContextData(List<T> moList, Class moType) {
            this.moList = moList;
            this.moType = moType;
        }

        public List<T> getMoList() {
            return moList;
        }

        public void setMoList(List<T> moList) {
            this.moList = moList;
        }

        public Class getMoType() {
            return moType;
        }

        public void setMoType(Class moType) {
            this.moType = moType;
        }
    }

    private class TransactionListener implements ITransactionListener {

        private PuerAddMOContext puerContext;

        TransactionListener(PuerAddMOContext puerContext) {
            this.puerContext = puerContext;
        }

        @Override
        public ResultRsp<?> prepare(ITransactionContext context) {
            return ResultRsp.SUCCESS;
        }

        @Override
        public ResultRsp<?> fire(ITransactionContext context) {
            if(context instanceof SimpleTransactionContext) {
                try {
                    AddMOContextData data = (AddMOContextData)((SimpleTransactionContext)context).getData();
                    return puerContext.getDao().add(data.getMoList(), data.getMoType());
                } catch(ServiceException e) {
                    LOGGER.error("Add context data failed", e);
                    return ResultRsp.FAILED;
                }
            }
            return ResultRsp.SUCCESS;
        }

        @Override
        public ResultRsp<?> commit(ITransactionContext context) {
            return ResultRsp.SUCCESS;
        }

        @Override
        public ResultRsp<?> rollback(ITransactionContext context) {
            if(context instanceof SimpleTransactionContext) {
                try {
                    AddMOContextData data = (AddMOContextData)((SimpleTransactionContext)context).getData();
                    return puerContext.getDao().delete(data.getMoList(), data.getMoType());
                } catch(ServiceException e) {
                    LOGGER.error("Add context data failed", e);
                    return ResultRsp.FAILED;
                }
            }
            return ResultRsp.SUCCESS;
        }
    }

}
