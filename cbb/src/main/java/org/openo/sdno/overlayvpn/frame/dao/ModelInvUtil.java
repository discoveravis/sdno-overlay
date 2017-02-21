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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryFilterEntity;
import org.openo.sdno.overlayvpn.frame.dao.model.InvMapping;
import org.openo.sdno.overlayvpn.frame.dao.model.InvMapping.InvMappingType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOInvField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOJsonField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.springframework.util.CollectionUtils;

/**
 * The tool class of inventory model.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
public class ModelInvUtil {

    private ModelInvUtil() {

    }

    /**
     * Gets the name of the field in the inventory.<br>
     * 
     * @param field The object field
     * @return The name of the field in the inventory
     * @since SDNO 0.5
     */
    public static InvMapping getModelInvMapping(Field field) {
        InvMapping mapping = new InvMapping(field.getName(), field.getName());
        MOInvField fieldAnno = field.getAnnotation(MOInvField.class);
        MOJsonField jsonAnno = field.getAnnotation(MOJsonField.class);
        if(fieldAnno != null && StringUtils.isNotEmpty(fieldAnno.invName())) {
            mapping.setInvName(fieldAnno.invName());
        } else if(jsonAnno != null && jsonAnno.type() != null) {
            Class<?> fieldType = field.getType();
            InvMappingType mappingType = null;
            if(Collection.class.isAssignableFrom(fieldType)) {
                mappingType = InvMappingType.JSON_LIST;
            } else {
                mappingType = InvMappingType.JSON;
            }
            Class<?> resType = jsonAnno.type();
            String invName = StringUtils.isEmpty(jsonAnno.invName()) ? field.getName() : jsonAnno.invName();
            mapping.setInvName(invName);
            mapping.setJsonType(mappingType, resType);
        }
        return mapping;
    }

    /**
     * Generating complex query conditions.<br>
     * 
     * @param models The object list
     * @return The complex query conditions
     * @throws ServiceException when translating object to json failed
     * @since SDNO 0.5
     */
    public static BatchQueryFilterEntity buildComlexFilter(Object model) throws ServiceException {
        Map<String, List<Object>> conditions = buildQueryConditions(model);
        if(MapUtils.isEmpty(conditions)) {
            return new BatchQueryFilterEntity();
        }
        Map<String, Object> entryFilter = new HashMap<String, Object>();
        StringBuffer filterDsc = new StringBuffer();
        Iterator<String> iter = conditions.keySet().iterator();
        while(iter.hasNext()) {
            String key = iter.next().toString();
            List<Object> values = conditions.get(key);
            if(CollectionUtils.isEmpty(values)) {
                continue;
            }
            int num = 1;
            int size = values.size();
            filterDsc.append("(");
            for(Object obj : values) {
                if(size == num) {
                    filterDsc.append(key).append("=':").append(key).append(num).append("'");
                } else {
                    filterDsc.append(key).append("=':").append(key).append(num).append("' or ");
                }
                entryFilter.put(key + num, obj);
                num++;
            }
            filterDsc.append(")");
            if(iter.hasNext()) {
                filterDsc.append(" and ");
            }
        }
        return new BatchQueryFilterEntity(filterDsc.toString(), JsonUtils.toJson(entryFilter));
    }

    /**
     * Generating query conditions for models.<br>
     * 
     * @param models The object list
     * @return The query conditions for models
     * @throws ServiceException when getting modelValue as string failed
     * @since SDNO 0.5
     */
    private static Map<String, List<Object>> buildQueryConditions(Object model) throws ServiceException {
        Map<String, List<Object>> conds = new HashMap<>();
        List<Field> fields = JavaEntityUtil.getAllField(model.getClass());
        for(Field field : fields) {
            NONInvField nonInvAnno = field.getAnnotation(NONInvField.class);
            if(nonInvAnno != null) {
                continue;
            }
            InvMapping invMapping = getModelInvMapping(field);
            Object fieldValue = JavaEntityUtil.getFieldValue(field.getName(), model);
            String invValue = invMapping.getInvValue(fieldValue);
            String invName = invMapping.getInvName();
            if(StringUtils.isEmpty(invValue) || StringUtils.isEmpty(invName)) {
                continue;
            }
            if(!conds.containsKey(invName)) {
                conds.put(invName, new ArrayList<Object>());
            }
            if(!conds.get(invName).contains(invValue)) {
                conds.get(invName).add(invValue);
            }
        }
        return conds;
    }
}
