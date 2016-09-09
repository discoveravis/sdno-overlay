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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of Object Operation.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public class ObjectOperUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectOperUtil.class);

    private ObjectOperUtil() {
    }

    /**
     * Set value of all field to null.<br>
     * 
     * @param object Object need to fill NULL
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public static void fillNull(Object object) {
        if(null == object) {
            return;
        }

        Class moType = object.getClass();
        List<Field> allField = JavaEntityUtil.getAllField(moType);
        for(Field tempField : allField) {
            // Get all fields
            String setMethodName = getSetMethodByAttr(tempField.getName());
            if(null == setMethodName) {
                continue;
            }
            // Set value of all fields to null
            setValueByMethod(object, tempField, setMethodName, null);
        }
    }

    /**
     * Set filed Value by setterName.<br>
     * 
     * @param object Object need to set value
     * @param field Object field need to set value
     * @param setterName setter method name
     * @param setValue value to set
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public static void setValueByMethod(Object object, Field field, String setterName, Object setValue) {
        Object oriFieldValue = JavaEntityUtil.getFieldValue(field.getName(), object);
        if(null == oriFieldValue && null == setValue) {
            return;
        }

        Class fieldClass = null;
        if(null != oriFieldValue) {
            fieldClass = oriFieldValue.getClass();
        } else {
            fieldClass = setValue.getClass();
        }

        try {
            Method method = object.getClass().getMethod(setterName, fieldClass);
            method.invoke(object, new Object[] {setValue});
        } catch(SecurityException securityException) {
            LOGGER.error("GetMethod throws SecurityException!", securityException);
        } catch(NoSuchMethodException noSuchMethodException) {
            LOGGER.error("GetMethod throws NoSuchMethodException!", noSuchMethodException);
        } catch(IllegalArgumentException illegalArgumentException) {
            LOGGER.error("GetMethod throws IllegalArgumentException!", illegalArgumentException);
        } catch(IllegalAccessException illegalAccessException) {
            LOGGER.error("GetMethod throws IllegalAccessException!", illegalAccessException);
        } catch(InvocationTargetException invocationTargetExceptione) {
            LOGGER.error("GetMethod throws InvocationTargetException! ", invocationTargetExceptione);
        }
    }

    /**
     * Get Setter Method name of Attribute.<br>
     * 
     * @param attrName attribute name
     * @return Setter Method name
     * @since SDNO 0.5
     */
    public static String getSetMethodByAttr(String attrName) {
        if(null == attrName) {
            return null;
        }
        return "set" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
    }

    /**
     * Get Getter Method name of Attribute.<br>
     * 
     * @param attrName attribute name
     * @return Getter Method name
     * @since SDNO 0.5
     */
    public static String getGetMethodByAttr(String attrName) {
        if(null == attrName) {
            return null;
        }
        return "get" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1);
    }
}
