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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.CommonTransactionContext;
import org.openo.sdno.overlayvpn.inventory.sdk.inf.IInvDAO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationMO;
import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * Class of MO operation context.<br>
 * 
 * @param <T> Inventory Model Class
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class PuerAddMOContext<T> extends CommonTransactionContext {

    private List<T> molist;

    private List<RelationMO> relations;

    private ResultRsp result;

    private IInvDAO<T> dao;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param addmo MO collection
     * @param relation Relation collection
     * @param dao Inventory DAO handler
     */
    public PuerAddMOContext(List<T> addmo, List<RelationMO> relation, IInvDAO<T> dao) {
        super();
        this.molist = addmo;
        this.relations = relation;
        this.dao = dao;
    }

    public List<T> getMo() {
        return molist;
    }

    /**
     * Get MO collection classified by MO type.<br>
     * 
     * @return MO collection
     * @since SDNO 0.5
     */
    public Map<Class, List<T>> getMOMap() {
        Map<Class, List<T>> map = new HashMap<Class, List<T>>();
        for(T mo : molist) {
            if(!map.containsKey(mo.getClass())) {
                List<T> moList = new ArrayList<T>();
                moList.add(mo);
                map.put(mo.getClass(), moList);
            } else {
                map.get(mo.getClass()).add(mo);
            }
        }
        return map;
    }

    public void setMo(List<T> mo) {
        this.molist = mo;
    }

    public ResultRsp getResult() {
        return result;
    }

    public void setResult(ResultRsp result) {
        this.result = result;
    }

    public List<RelationMO> getRelation() {
        return relations;
    }

    public void setRelation(List<RelationMO> relation) {
        this.relations = relation;
    }

    public IInvDAO<T> getDao() {
        return dao;
    }

    public void setDao(IInvDAO<T> dao) {
        this.dao = dao;
    }

}
