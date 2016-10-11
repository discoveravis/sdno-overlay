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

package org.openo.sdno.overlayvpn.util.objreflectoper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;
import org.openo.sdno.util.reflect.JavaEntityUtil;

/**
 * Class of Object UUid Allocation.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 2, 2016
 */
public class UuidAllocUtil {

    private UuidAllocUtil() {
    }

    /**
     * Allocate UUid for all AbstUuidModel Objects Recursively.<br>
     * 
     * @param object Object need to allocate UUid
     * @since SDNO 0.5
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void allocUuid(Object object) {
        if(null == object) {
            return;
        }

        if(Collection.class.isAssignableFrom(object.getClass())) {
            for(Object childObj : (Collection<Object>)object) {
                allocUuid(childObj);
            }
        }

        Class moType = object.getClass();
        // if this class is subclass of AbstUuidModel
        if(AbstUuidModel.class.isAssignableFrom(moType)) {
            ((AbstUuidModel)object).allocateUuid();
        }

        // allocate UUid for all non-basic type fields
        List<Field> cRelationField = getRelationField(moType);
        for(Field field : cRelationField) {
            Object relationObject = JavaEntityUtil.getFieldValue(field.getName(), object);
            if(null == relationObject) {
                continue;
            }

            if(Collection.class.isAssignableFrom(field.getType())) {
                for(Object childObj : (Collection<Object>)relationObject) {
                    allocUuid(childObj);
                }
            } else {
                allocUuid(relationObject);
            }
        }
    }

    /**
     * Check whether UUid of Object is valid.<br>
     * 
     * @param object Object need to allocate UUid
     * @throws ServiceException when ServiceException occurs
     * @since SDNO 0.5
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void checkUuid(Object object) throws ServiceException {
        if(null == object) {
            return;
        }

        if(Collection.class.isAssignableFrom(object.getClass())) {
            for(Object childObj : (Collection<Object>)object) {
                checkUuid(childObj);
            }
        }

        Class moType = object.getClass();
        // if this class is subclass of AbstUuidModel
        if(AbstUuidModel.class.isAssignableFrom(moType)) {
            ((AbstUuidModel)object).checkUuid();
        }

        // Allocate UUid for fields
        List<Field> cRelationField = getRelationField(moType);
        for(Field field : cRelationField) {
            Object relationObject = JavaEntityUtil.getFieldValue(field.getName(), object);
            if(null == relationObject) {
                continue;
            }

            if(Collection.class.isAssignableFrom(field.getType())) {
                for(Object childObj : (Collection<Object>)relationObject) {
                    checkUuid(childObj);
                }
            } else {
                checkUuid(relationObject);
            }
        }
    }

    /**
     * Get all Non-basic Type fields.<br>
     * 
     * @param motype class type of Object
     * @return all fields of non-basic type
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    private static List<Field> getRelationField(Class motype) {
        List<Field> relationFieldList = new ArrayList<Field>();
        List<Field> allfield = JavaEntityUtil.getAllField(motype);
        for(Field field : allfield) {
            Class<?> type = field.getType();
            if(type.isAssignableFrom(Number.class) || type.isAssignableFrom(Character.class)
                    || type.isAssignableFrom(String.class) || type.isAssignableFrom(Boolean.class)
                    || type.isPrimitive() || type.isAssignableFrom(Integer.class)) {
                continue;
            }
            relationFieldList.add(field);
        }
        return relationFieldList;
    }

}
