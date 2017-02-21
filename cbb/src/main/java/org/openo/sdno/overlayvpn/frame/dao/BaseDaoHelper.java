/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.frame.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MORelatedField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOSubField;
import org.openo.sdno.overlayvpn.model.v2.overlay.BaseModel;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.openo.sdno.util.reflect.ReflectionUtil;

/**
 * The tool class of base dao.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 3, 2017
 */
public class BaseDaoHelper {

    private BaseDaoHelper() {

    }

    /**
     * Whether support deep querying.<br>
     * 
     * @param field The field of object
     * @return true when field has sub model or relation model annotation
     * @since SDNO 0.5
     */
    public static boolean isSupportDeepQuery(Field field) {
        return getSubModeAnno(field) != null || getRelationModeAnno(field) != null;
    }

    /**
     * Get attr of model annotation.<br>
     * 
     * @param field The field of object
     * @return The attr of model annotation
     * @since SDNO 0.5
     */
    public static String getAnnoSubAttr(Field field) {
        MOSubField subAnno = getSubModeAnno(field);
        return subAnno != null ? subAnno.attr() : null;
    }

    /**
     * Get sub model annotation of the field.<br>
     * 
     * @param field The field of object
     * @return The sub model annotation
     * @since SDNO 0.5
     */
    public static MOSubField getSubModeAnno(Field field) {
        return field.getAnnotation(MOSubField.class);
    }

    /**
     * Get relation model annotation of the field.<br>
     * 
     * @param field The field of object
     * @return The relation model annotation
     * @since SDNO 0.5
     */
    public static MORelatedField getRelationModeAnno(Field field) {
        return field.getAnnotation(MORelatedField.class);
    }

    /**
     * Get the type of the sub model.<br>
     * 
     * @param field The field of object
     * @return The type of the sub model
     * @since SDNO 0.5
     */
    public static Class<?> getSubModelType(Field field) {
        Class<?> attrType = getAttrType(field);
        if(BaseModel.class.isAssignableFrom(attrType)) {
            return attrType;
        }
        Class<?> subType = getAnnoSubType(field);
        if(subType == null || !BaseModel.class.isAssignableFrom(subType)) {
            return null;
        }
        return subType;
    }

    /**
     * Get type of the field and returns the generic type if it is List.<br>
     * 
     * @param field The field of object
     * @return The type of the field
     * @since SDNO 0.5
     */
    public static Class<?> getAttrType(Field field) {
        return isListType(field) ? ModelUtils.getGenericType(field) : field.getType();
    }

    /**
     * Whether the field is a list.<br>
     * 
     * @param field The field of object
     * @return true when the field is a list,otherwise is false
     * @since SDNO 0.5
     */
    public static boolean isListType(Field field) {
        return List.class.isAssignableFrom(field.getType());
    }

    /**
     * Build the querying condition.<br>
     * 
     * @param field The field of object
     * @param subType The sub type
     * @param models The uuid model list
     * @return The result uuid model list
     * @throws ServiceException when construct model failed
     * @since SDNO 0.5
     */
    public static List<BaseModel> buildQueryCond(Field field, Class<?> subType, BaseModel... models)
            throws ServiceException {
        List<BaseModel> conds = new ArrayList<>();
        MOSubField subAnno = getSubModeAnno(field);
        MORelatedField relationAnno = getRelationModeAnno(field);
        for(BaseModel model : models) {
            BaseModel cond = (BaseModel)ModelUtils.newInstance(subType);
            if(subAnno != null) {
                ReflectionUtil.setFieldValue(cond, subAnno.parentId(), model.getId());
            } else if(relationAnno != null) {
                String relationKey = relationAnno.relationId();
                Object relationId = ReflectionUtil.getFieldValue(model, relationKey);
                if(relationId != null) {
                    cond.setId(String.valueOf(relationId));
                }
            }
            conds.add(cond);
        }
        return conds;
    }

    /**
     * Query sub model.<br>
     * 
     * @param conds The query condition
     * @return The queried result list
     * @throws ServiceException when querying failed
     * @since SDNO 0.5
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends BaseModel> List<T> querySubModel(T cond) throws ServiceException {
        if(cond == null) {
            return new ArrayList<>();
        }
        Class<? extends BaseModel> resType = cond.getClass();
        BatchQueryResult queryResult = new MssInvProxy(resType).query(cond, null);
        return queryResult.getObjects();
    }

    private static Class<?> getAnnoSubType(Field field) {
        MOSubField subAnno = getSubModeAnno(field);
        return subAnno != null ? subAnno.type() : null;
    }
}
