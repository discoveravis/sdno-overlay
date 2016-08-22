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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.DBErrorServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.addservice.GetAddRelationProcessor;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.PuerAddMOContext;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.PuerAddMOTransactionListener;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.PuerAddRelationTransactionListener;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.TransactionMgr;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.TransactionOperateType;
import org.openo.sdno.overlayvpn.inventory.sdk.inf.IInvDAO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationMO;
import org.openo.sdno.overlayvpn.inventory.sdk.util.MOModelProcessor;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation class for inventory service.<br/>
 * 
 * @param <T> Inventory Model Class
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class PuerInvServicesImpl<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PuerInvServicesImpl.class);

    @Autowired
    private IInvDAO<T> dao;

    static {
        // Init transaction listener.
        TransactionMgr.registerListener("com.huawei.netmatrix.cbb.overlayvpn", PuerAddMOTransactionListener.class);
        TransactionMgr.registerListener("com.huawei.netmatrix.cbb.overlayvpn",
                PuerAddRelationTransactionListener.class);

    }

    /**
     * Add MO data.<br/>
     * 
     * @param moList MO list
     * @return Adding result
     * @throws ServiceException if the transaction is failed.
     * @since SDNO 0.5
     */
    public ResultRsp add(List<T> moList) throws ServiceException {
        PuerAddMOContext context = getMOContext(moList);
        ResultRsp result = TransactionMgr.prepare(context);
        if(result.isSuccess()) {
            result = TransactionMgr.fire(context);
            if(!result.isSuccess()) {
                LOGGER.error("add moList fire is failed and rollback.");
                TransactionMgr.rollback(context);
                throw new DBErrorServiceException();
            }
        } else {
            LOGGER.error("add moList prepare is failed.");
            throw new DBErrorServiceException();
        }
        return result;
    }

    private PuerAddMOContext getMOContext(List<T> moList) throws InnerErrorServiceException {
        List allAddMOList = new ArrayList();
        List<RelationMO> allAddRelationMO = new ArrayList<RelationMO>();
        for(T mo : moList) {
            // Add all MO objects into the list.
            allAddMOList.add(mo);
            this.getChildMO(mo, allAddMOList);

            // Add all relations into the list.
            GetAddRelationProcessor.getRelation(mo, allAddRelationMO);
        }
        PuerAddMOContext context = new PuerAddMOContext(allAddMOList, allAddRelationMO, dao);
        context.setOperateType(TransactionOperateType.CREATE);
        return context;
    }

    private void getChildMO(Object mo, List<Object> allAddMOList) {
        Class moclass = mo.getClass();
        List<Field> cRelationField = MOModelProcessor.getCompositionRelationField(moclass);

        if(!cRelationField.isEmpty()) {
            for(Field field : cRelationField) {
                Object relationObject = JavaEntityUtil.getFieldValue(field.getName(), mo);

                if(relationObject instanceof Collection) {
                    allAddMOList.addAll((Collection)relationObject);
                    Iterator it = ((Collection)relationObject).iterator();
                    while(it.hasNext()) {
                        // Recursive to get child MO objects.
                        this.getChildMO(it.next(), allAddMOList);
                    }
                } else {
                    if(relationObject != null) {
                        allAddMOList.add(relationObject);
                        // Recursive to get child MO objects.
                        this.getChildMO(relationObject, allAddMOList);
                    } else {
                        LOGGER.warn("PuerInvServicesImpl.getChildMO to get relation object value is null for "
                                + field.getName() + " in " + moclass.getName() + ".");
                    }
                }

            }
        }
    }

}
