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

package org.openo.sdno.overlayvpn.inventory.sdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOExtendField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOInvField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOLikeFilterField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MORelationField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOUUIDField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.util.reflect.ClassFieldManager;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class of model processor. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-7
 */
public class MOModelProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MOModelProcessor.class);

    private MOModelProcessor() {

    }

    /**
     * It is used to build filter map by annotation in class. <br/>
     * 
     * @param value The map of query value
     * @param motype The class type
     * @return The filter map.
     * @since SDNO 0.5
     */
    public static Map<String, Object> buildFilterMap(Map<String, Object> value, Class motype) {
        List<Field> fields = JavaEntityUtil.getAllField(motype);
        for(Field curField : fields) {
            if(value.containsKey(curField.getName())) {

                if(Modifier.isStatic(curField.getModifiers())) {
                    value.remove(curField.getName());
                }
                MOInvField invFieldAnn = curField.getAnnotation(MOInvField.class);
                NONInvField nonInvFieldAnn = curField.getAnnotation(NONInvField.class);
                MORelationField relationFieldAnn = curField.getAnnotation(MORelationField.class);
                MOExtendField extendFieldAnn = curField.getAnnotation(MOExtendField.class);

                if(invFieldAnn != null) {
                    Object fValue = value.get(curField.getName());
                    value.remove(curField.getName());
                    if(fValue != null) {

                        MOLikeFilterField likeFieldAnn = curField.getAnnotation(MOLikeFilterField.class);

                        if(likeFieldAnn == null) {
                            Collection inValue = new ArrayList();
                            inValue.add(fValue);
                            value.put(invFieldAnn.invName(), inValue);
                        } else {
                            value.put(invFieldAnn.invName(), fValue);
                        }
                    }
                } else if((nonInvFieldAnn != null) || (extendFieldAnn != null) || (relationFieldAnn != null)) {
                    value.remove(curField.getName());
                } else {
                    Object fValue = value.get(curField.getName());
                    if(fValue != null) {
                        MOLikeFilterField likeFieldAnn = curField.getAnnotation(MOLikeFilterField.class);
                        if(likeFieldAnn == null) {
                            Collection inValue = new ArrayList();
                            inValue.add(fValue);
                            value.put(curField.getName(), inValue);
                        }
                    }

                }
            }
        }
        return value;
    }

    /**
     * It processes by annotation in class. <br/>
     * 
     * @param value The value
     * @param motype The class type
     * @return The value after process.
     * @since SDNO 0.5
     */
    public static Map<String, Object> process(Map<String, Object> value, Class motype) {
        List<Field> fields = JavaEntityUtil.getAllField(motype);
        for(Field field : fields) {
            if(value.containsKey(field.getName())) {
                if(Modifier.isStatic(field.getModifiers())) {
                    value.remove(field.getName());
                }
                MOInvField invFieldAnn = field.getAnnotation(MOInvField.class);
                NONInvField nonInvFieldAnn = field.getAnnotation(NONInvField.class);
                MORelationField relationFieldAnn = field.getAnnotation(MORelationField.class);
                MOExtendField extendFieldAnn = field.getAnnotation(MOExtendField.class);

                if(invFieldAnn != null) {
                    Object fValue = value.get(field.getName());
                    value.remove(field.getName());
                    value.put(invFieldAnn.invName(), fValue);
                } else if((nonInvFieldAnn != null) || (relationFieldAnn != null)) {
                    value.remove(field.getName());
                } else if(extendFieldAnn != null) {
                    Object fValue = value.get(field.getName());
                    value.remove(field.getName());
                    if(fValue instanceof Map) {
                        Map extendAttr = (Map)fValue;

                        Set ketset = extendAttr.keySet();
                        Iterator it = ketset.iterator();
                        while(it.hasNext()) {
                            Object extendAttrKey = it.next();

                            if(!value.containsKey(extendAttrKey)) {
                                value.put(extendAttrKey.toString(), extendAttr.get(extendAttrKey));
                            }
                        }
                    }

                }
            }
        }
        return value;
    }

    /**
     * It is used to get field name in class. <br/>
     * 
     * @param fieldName The field name that want to get
     * @param motype The class type
     * @return The field name, it'll return directly if the input field name is the same as the
     *         field name in class, it'll return annotation if the input field name is the same as
     *         the annotation of input field name in class, or it'll return null.
     * @since SDNO 0.5
     */
    public static Field getMOFieldName(String fieldName, Class motype) {
        List<Field> fields = ClassFieldManager.getInstance().getAllField(motype);
        return getField(fieldName, fields);
    }

    private static Field getField(String fieldName, List<Field> fields) {
        if(fields == null) {
            LOGGER.error("getField fields is null,return null.");
            return null;
        }

        for(Field field : fields) {
            if(field.getName().equals(fieldName)) {
                return field;
            }
            MOInvField invFieldAnn = field.getAnnotation(MOInvField.class);
            if((invFieldAnn != null) && invFieldAnn.invName().equals(fieldName)) {
                return field;
            }
        }

        return null;
    }

    /**
     * It is used to get uuid. <br/>
     * 
     * @param obj The object
     * @param motype The class type
     * @return The uuid value.
     * @throws InnerErrorServiceException When something wrong.
     * @since SDNO 0.5
     */
    public static String getUUID(Object obj, Class motype) throws InnerErrorServiceException {
        List<Field> fields = JavaEntityUtil.getAllField(motype);
        for(Field field : fields) {
            if(field.getName().equalsIgnoreCase(InvConst.UUID_STR)) {
                return getFieldValueByName(obj, field);
            }
            MOUUIDField uuidFieldAnn = field.getAnnotation(MOUUIDField.class);
            if(uuidFieldAnn != null) {
                return getFieldValueByName(obj, field);
            }
        }

        return null;
    }

    private static String getFieldValueByName(Object obj, Field field) throws InnerErrorServiceException {
        Object fieldValueByName = JavaEntityUtil.getFieldValueByName(field.getName(), obj);
        if(fieldValueByName != null) {
            return fieldValueByName.toString();
        } else {
            LOGGER.error("getFieldValueByName fieldValueByName is null,return null.");
            return null;
        }
    }

    private static String buildRestType(Class moType) {
        String resttype = moType.getName().replaceAll("\\.", "_");
        return resttype.toLowerCase(Locale.getDefault());
    }

    /**
     * It is used to get resource type. <br/>
     * 
     * @param motype The class type
     * @return The resource type.
     * @since SDNO 0.5
     */
    public static String getRestType(Class motype) {
        if(motype.getAnnotation(MOResType.class) != null) {
            MOResType restypeAn = (MOResType)motype.getAnnotation(MOResType.class);
            if(restypeAn.infoModelName() != null) {
                return restypeAn.infoModelName();
            }
        }
        return buildRestType(motype);
    }

    /**
     * It is used to get relation field in class. <br/>
     * 
     * @param motype The class type
     * @return The relation field list.
     * @since SDNO 0.5
     */
    public static List<Field> getRelationField(Class motype) {
        List<Field> relationFieldList = new ArrayList<Field>();
        List<Field> allfield = JavaEntityUtil.getAllField(motype);
        for(Field field : allfield) {
            MORelationField relationField = field.getAnnotation(MORelationField.class);
            if(relationField != null) {
                relationFieldList.add(field);
            }
        }
        return relationFieldList;
    }

    /**
     * It is used to check the field is composition field or not. <br/>
     * 
     * @param field The field that want to check
     * @return true if the field is composition field.
     * @since SDNO 0.5
     */
    public static boolean isCompositionRelationField(Field field) {
        MORelationField relationField = field.getAnnotation(MORelationField.class);
        return (relationField != null) && ("composition").equals(relationField.type());

    }

    /**
     * It is used to get all composition fields. <br/>
     * 
     * @param motype the class type
     * @return The list of composition field.
     * @since SDNO 0.5
     */
    public static List<Field> getCompositionRelationField(Class motype) {
        List<Field> relationFieldList = new ArrayList<Field>();
        List<Field> allfield = JavaEntityUtil.getAllField(motype);
        for(Field field : allfield) {
            if(isCompositionRelationField(field)) {
                relationFieldList.add(field);
            }
        }
        return relationFieldList;
    }
}
