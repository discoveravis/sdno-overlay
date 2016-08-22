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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact;

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.ITransactionContext;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.ITransactionListener;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.SimpleTransactionContext;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.SimpleTransactionMgr;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.TransactionListenerAnnotation;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.TransactionOperateType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationMO;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for adding transaction listener of relations.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@TransactionListenerAnnotation(contextClass = PuerAddMOContext.class, operateType = TransactionOperateType.CREATE)
public class PuerAddRelationTransactionListener implements ITransactionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PuerAddRelationTransactionListener.class);

    @Override
    public ResultRsp prepare(ITransactionContext context) {
        return ResultRsp.SUCCESS;
    }

    @Override
    public ResultRsp fire(ITransactionContext context) {
        if(context instanceof PuerAddMOContext) {
            final PuerAddMOContext pcontext = (PuerAddMOContext)context;
            List<RelationMO> relationMO = pcontext.getRelation();
            SimpleTransactionMgr mgr = new SimpleTransactionMgr();
            return mgr.transact(new TransactionListener(pcontext), relationMO);
        }
        return ResultRsp.SUCCESS;
    }

    @Override
    public ResultRsp commit(ITransactionContext context) {
        return ResultRsp.SUCCESS;
    }

    @Override
    public ResultRsp rollback(ITransactionContext context) {
        if(context instanceof PuerAddMOContext) {
            PuerAddMOContext<RelationMO> pcontext = (PuerAddMOContext)context;
            List<RelationMO> relationMO = pcontext.getRelation();

            ResultRsp result = ResultRsp.SUCCESS;

            for(RelationMO relation : relationMO) {
                try {
                    result = pcontext.getDao().deleteRelation(relation);
                } catch(ServiceException e) {
                    result.setErrorCode(ErrorCode.DB_OPERATION_ERROR);
                    LOGGER.warn("Delete relation to puer failed.", e);
                }
            }

            return result;

        }
        return ResultRsp.SUCCESS;
    }

    /**
     * Transaction listener implementation.<br/>
     * 
     * @author
     * @version SDNO 0.5 Jun 8, 2016
     */
    private class TransactionListener implements ITransactionListener {

        private PuerAddMOContext pureContext;

        TransactionListener(PuerAddMOContext pureContext) {
            this.pureContext = pureContext;
        }

        @Override
        public ResultRsp<?> commit(ITransactionContext context) {
            return ResultRsp.SUCCESS;
        }

        @Override
        public ResultRsp<?> fire(ITransactionContext context) {
            if(context instanceof SimpleTransactionContext) {
                try {
                    return pureContext.getDao().addRelation((RelationMO)((SimpleTransactionContext)context).getData());
                } catch(ServiceException e) {
                    LOGGER.error("Add Relation throws ServiceException", e);
                    return ResultRsp.FAILED;
                }
            }
            return ResultRsp.SUCCESS;
        }

        @Override
        public ResultRsp<?> prepare(ITransactionContext context) {
            return ResultRsp.SUCCESS;
        }

        @Override
        public ResultRsp<?> rollback(ITransactionContext context) {
            if(context instanceof SimpleTransactionContext) {
                try {
                    return pureContext.getDao().deleteRelation(
                            (RelationMO)((SimpleTransactionContext)context).getData());
                } catch(ServiceException e) {
                    LOGGER.error("Delete Relation throws ServiceException", e);
                    return ResultRsp.FAILED;
                }
            }
            return ResultRsp.SUCCESS;
        }

    }

}
