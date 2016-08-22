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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.addservice;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationMO;
import org.openo.sdno.overlayvpn.inventory.sdk.util.MOModelProcessor;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of Relation Operation.<br/>
 * 
 * @author
 * @version SDNO 0.5 Jun 7, 2016
 */
public class GetAddRelationProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetAddRelationProcessor.class);

    private final Object mo;

    private final List<RelationMO> allAddRelationMO;

    private final Class moclass;

    private final Field field;

    private final Object relationObject;

    /**
     * Constructor.<br/>
     * 
     * @since SDNO 0.5
     * @param mo MO object
     * @param allAddRelationMO Collection of relation MO objects
     * @param moclass MO class
     * @param field Field
     * @param relationObject Relation object
     */
    public GetAddRelationProcessor(Object mo, List<RelationMO> allAddRelationMO, Class moclass, Field field,
            Object relationObject) {
        this.mo = mo;
        this.allAddRelationMO = allAddRelationMO;
        this.moclass = moclass;
        this.field = field;
        this.relationObject = relationObject;
    }

    /**
     * Recursive get all relations.<br/>
     * 
     * @param mo MO object
     * @param allAddRelationMO Collection of relation MO objects
     * @throws InnerErrorServiceException if get relations of collection failed.
     * @since SDNO 0.5
     */
    public static void getRelation(Object mo, List<RelationMO> allAddRelationMO) throws InnerErrorServiceException {
        Class moclass = mo.getClass();
        List<Field> cRelationField = MOModelProcessor.getRelationField(moclass);

        if(!cRelationField.isEmpty()) {
            for(Field field : cRelationField) {
                Object relationObject = JavaEntityUtil.getFieldValue(field.getName(), mo);
                GetAddRelationProcessor parameter =
                        new GetAddRelationProcessor(mo, allAddRelationMO, moclass, field, relationObject);

                if(relationObject instanceof Collection) {
                    parameter.getAddRelationnForCollection();
                } else {
                    parameter.getAddRelationForObject();
                }

            }
        }
    }

    public List<RelationMO> getAllAddRelationMO() {
        return allAddRelationMO;
    }

    public Object getRelationObject() {
        return relationObject;
    }

    @Override
    public String toString() {
        return "GetAddRelationForParameter [moclass=" + moclass + ", field=" + field + "]";
    }

    private RelationMO buildRelationMO(Object obj) throws InnerErrorServiceException {
        return new RelationMO(mo, moclass, field, obj);
    }

    private void addRelation(Object obj) throws InnerErrorServiceException {
        this.allAddRelationMO.add(buildRelationMO(obj));
    }

    private boolean isCompositionRelationField() {
        return MOModelProcessor.isCompositionRelationField(field);
    }

    /**
     * Get relations of collection object.<br/>
     * 
     * @throws InnerErrorServiceException if add relation failed.
     * @since SDNO 0.5
     */
    private void getAddRelationnForCollection() throws InnerErrorServiceException {
        Collection relationObjList = (Collection)this.getRelationObject();
        for(Object reObj : relationObjList) {
            this.addRelation(reObj);

            if(this.isCompositionRelationField()) {
                GetAddRelationProcessor.getRelation(reObj, this.getAllAddRelationMO());
            }
        }
    }

    /**
     * Get relations of ordenary object.<br/>
     * 
     * @throws InnerErrorServiceException if add relation failed.
     * @since SDNO 0.5
     */
    private void getAddRelationForObject() throws InnerErrorServiceException {
        Object reObject = this.getRelationObject();
        if(reObject != null) {
            this.addRelation(reObject);

            if(this.isCompositionRelationField()) {
                GetAddRelationProcessor.getRelation(reObject, this.getAllAddRelationMO());
            }
        } else {
            LOGGER.info("PuerInvServicesImpl.getChildMO to get relation object value is null for " + this.toString()
                    + ".");
        }
    }

}
