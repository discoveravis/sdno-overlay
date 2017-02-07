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

package org.openo.sdno.overlayvpn.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class of model convert.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
public class ModelUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModelUtils.class);

    private ModelUtils() {

    }

    /**
     * Construct a new object.<br>
     * 
     * @param type The resource type
     * @param uuid The uuid of new object
     * @return The new object
     * @throws ServiceException when class has no default constructor
     * @since SDNO 0.5
     */
    public static <T extends UuidModel> T newModel(Class<T> type, String uuid) throws ServiceException {
        try {
            T instance = type.newInstance();
            instance.setUuid(uuid);
            return instance;
        } catch(InstantiationException | IllegalAccessException e) {
            throw new InnerErrorServiceException(
                    "model.init.error,class has no default constructor :" + type.getName());
        }
    }

    /**
     * Get generic type of field.<br>
     * 
     * @param field The object field
     * @return The generic type
     * @since SDNO 0.5
     */
    public static Class<?> getGenericType(Field field) {
        if(field == null) {
            return null;
        }
        Class<?> type = field.getType();
        if(type.isPrimitive() || !Collection.class.isAssignableFrom(type)) {
            return null;
        }
        Type gtype = field.getGenericType();
        if(gtype == null) {
            return null;
        }
        if(gtype instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType)gtype;
            Type[] actualTypeArguments = ptype.getActualTypeArguments();
            if(ArrayUtils.isNotEmpty(actualTypeArguments)) {
                return (Class<?>)actualTypeArguments[0];
            }
        }
        return null;
    }

    /**
     * Initialization instance.<br>
     * 
     * @param type The resource type
     * @return The initialized instance
     * @throws ServiceException when class has no default constructor
     * @since SDNO 0.5
     */
    public static <T> T newInstance(Class<T> type) throws ServiceException {
        try {
            return type.newInstance();
        } catch(InstantiationException | IllegalAccessException e) {
            LOGGER.error("Model.init.error,class has no default constructor :" + type.getName());
            throw new InnerErrorServiceException(
                    "Model.init.error,class has no default constructor :" + type.getName());
        }
    }

    /**
     * Set the value of object that the value map contains the object field.<br>
     * 
     * @param obj The object to set
     * @param values The value map that contain all information
     * @since SDNO 0.5
     */
    public static void setValueMap(Object obj, Map<String, ? extends Object> values) {
        if(obj == null || MapUtils.isEmpty(values)) {
            return;
        }
        List<Field> fields = JavaEntityUtil.getAllField(obj.getClass());
        for(Field field : fields) {
            if(values.containsKey(field.getName())) {
                Object value = values.get(field.getName());
                if(value != null) {
                    JavaEntityUtil.setFieldValue(field, obj, value);
                }
            }
        }
    }

    /**
     * Copy data from source to destination.<br>
     * 
     * @param src The source object
     * @param dst The destination object
     * @since SDNO 0.5
     */
    public static void copyAttributes(Object src, Object dst) {
        if(src == null || dst == null) {
            return;
        }
        Method[] srcMethods = src.getClass().getMethods();
        if(srcMethods == null || srcMethods.length == 0) {
            return;
        }
        for(Method srcMethod : srcMethods) {
            String srcMethodName = srcMethod.getName();
            String dstMethodName = null;
            if(srcMethodName.startsWith("get")) {
                dstMethodName = "set" + srcMethodName.substring(3);
            } else if(srcMethodName.startsWith("is")) {
                dstMethodName = "set" + srcMethodName.substring(2);
            } else {
                continue;
            }
            Class<?> returnType = srcMethod.getReturnType();
            Method dstMethod = null;
            try {
                dstMethod = dst.getClass().getMethod(dstMethodName, returnType);
                Object result = srcMethod.invoke(src);
                dstMethod.invoke(dst, result);
            } catch(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                continue;
            }
        }
    }

    /**
     * Using the source object data to fill the empty field in destination object.<br>
     * 
     * @param src The source object list
     * @param dest The destination object list
     * @return The filled result list
     * @throws ServiceException when construct object failed
     * @since SDNO 0.5
     */
    public static <T extends UuidModel> List<T> fillAttribute(List<T> src, List<T> dest) throws ServiceException {
        List<T> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(src) || CollectionUtils.isEmpty(dest)) {
            return result;
        }
        Map<String, T> srcMap = modelsToMap(src);
        Map<String, T> destMap = modelsToMap(dest);
        for(String key : srcMap.keySet()) {
            if(!destMap.containsKey(key)) {
                continue;
            }
            result.add(fillAttribute(srcMap.get(key), destMap.get(key)));
        }
        return result;
    }

    private static <T extends UuidModel> Map<String, T> modelsToMap(List<T> datas) {
        Map<String, T> dataMap = new HashMap<>();
        if(CollectionUtils.isEmpty(datas)) {
            return dataMap;
        }
        for(T data : datas) {
            dataMap.put(data.getUuid(), data);
        }
        return dataMap;
    }

    @SuppressWarnings("unchecked")
    private static <T extends UuidModel> T fillAttribute(T src, T dest) throws ServiceException {
        Class<? extends UuidModel> resType = src.getClass();
        T data = (T)newInstance(resType);
        data.setUuid(src.getUuid());
        List<Field> fields = JavaEntityUtil.getAllField(resType);
        for(Field field : fields) {
            Object srcValue = JavaEntityUtil.getFieldValue(field.getName(), src);
            Object destValue = JavaEntityUtil.getFieldValue(field.getName(), dest);
            if(srcValue != null && !"null".equals(srcValue) && destValue == null) {
                JavaEntityUtil.setFieldValue(field, data, srcValue);
                JavaEntityUtil.setFieldValue(field, dest, srcValue);
            }
        }
        return data;
    }
}
