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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.DBErrorServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOExtendField;
import org.openo.sdno.overlayvpn.inventory.sdk.util.MOModelProcessor;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.result.InvRsp;
import org.openo.sdno.util.reflect.ClassFieldManager;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Puer Inventory DAO tool Class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class PuerInvDAOUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PuerInvDAOUtil.class);

    private PuerInvDAOUtil() {

    }

    /**
     * Build query condition.<br/>
     * 
     * @param condition Collection of MO objects
     * @param moType MO type
     * @return Collection of query condition
     * @throws ServiceException if get attributes from MO objects failed.
     * @since SDNO 0.5
     */
    public static <T> Map<String, Object> buildQueryCondition(List<T> condition, Class moType) throws ServiceException {
        Map<String, Object> condMap = new HashMap<String, Object>();
        for(T mo : condition) {

            // All non-empty fields of the incoming object as the filter conditions for the query.
            Map<String, Object> fieldValue = JavaEntityUtil.getFiledValues(mo);

            fieldValue = MOModelProcessor.buildFilterMap(fieldValue, moType);
            Set<String> ketSet = fieldValue.keySet();
            Iterator<String> it = ketSet.iterator();
            while(it.hasNext()) {
                String key = it.next();
                Object value = fieldValue.get(key);
                if(value != null) {
                    // If the key exists, insert the value. If not exists, insert key and value.
                    if(condMap.containsKey(key)) {
                        buildCondition(condMap, key, value);
                    } else {
                        condMap.put(key, value);
                    }
                }
            }
        }
        return condMap;
    }

    private static <T> void buildCondition(Map<String, Object> condMap, String key, Object value) {
        Object condvalue = condMap.get(key);
        if(condvalue instanceof Collection) {

            if(!((Collection)condvalue).contains(value)) {
                // If the value is collection, need to use addAll method.
                if(value instanceof Collection) {
                    ((Collection)condvalue).addAll((Collection)value);
                } else {
                    ((Collection)condvalue).add(value);
                }
            }
        } else {
            List conValueList = new ArrayList();
            conValueList.add(value);
            conValueList.add(condvalue);
            condMap.put(key, conValueList);
        }
    }

    /**
     * Build MO object by MO type and attributes collection.<br/>
     * 
     * @param moType MO type
     * @param moMap Collection of attributes.
     * @return MO object
     * @throws InstantiationException if get MO instance failed.
     * @throws IllegalAccessException if get MO instance failed.
     * @since SDNO 0.5
     */
    public static <T> T buildMO(Class moType, Map<String, Object> moMap) throws InstantiationException,
            IllegalAccessException {
        T mo = (T)moType.newInstance();

        Set<String> ketSet = moMap.keySet();
        Iterator<String> it = ketSet.iterator();
        List<String> extendAttrName = new ArrayList<String>();
        while(it.hasNext()) {
            String key = it.next();

            Field field = MOModelProcessor.getMOFieldName(key, moType);

            // If the field exists in the class, update the field value. If not exists, it may be
            // extension attribute.
            if(field != null) {
                JavaEntityUtil.setFieldValue(field, mo, moMap.get(key));
            } else {
                extendAttrName.add(key);
            }
        }
        processExtendsAttr(mo, moType, moMap, extendAttrName);
        return mo;
    }

    /**
     * Translate inventory response to result response.<br/>
     * 
     * @param rsp Inventory response
     * @return Result response
     * @throws DBErrorServiceException if the inventory response is null or the status of inventory
     *             response is not success.
     * @since SDNO 0.5
     */
    public static ResultRsp tranlateInvRsp(InvRsp rsp) throws DBErrorServiceException {
        ResultRsp result = ResultRsp.SUCCESS;
        if((rsp == null) || !rsp.isSucess()) {
            // All the errors returned by puer inventory, are considered to be the failure of the
            // database operation.
            LOGGER.error("tranlateInvRsp DB error");
            throw new DBErrorServiceException();
        }
        return result;
    }

    /**
     * Build query result by the return data.<br/>
     * 
     * @param moType MO type
     * @param rsp Results of query operation
     * @return Query result in result response structure
     * @throws ServiceException if build MO object failed.
     * @since SDNO 0.5
     */
    public static <T> ResultRsp<List<T>> buildQueryResult(Class moType, List<Map<String, Object>> rsp)
            throws ServiceException {
        ResultRsp<List<T>> result = ResultRsp.SUCCESS;
        if(result.isSuccess()) {
            List<T> moList = new ArrayList<T>();
            if(rsp != null) {
                try {
                    for(Map<String, Object> moMap : rsp) {
                        moList.add((T)buildMO(moType, moMap));
                    }
                } catch(InstantiationException e) {
                    LOGGER.error(moType.getName()
                            + " has not empty param constructor method, so failed to query from puer inv.", e);
                    throw new InnerErrorServiceException(moType.getName()
                            + " has not empty param constructor method, so failed to query from puer inv.");
                } catch(IllegalAccessException e) {
                    LOGGER.error(moType.getName() + " create new instance failed.", e);
                    throw new InnerErrorServiceException(moType.getName() + " create new instance failed.");
                }
            } else {
                LOGGER.info("Get empty data from puer inv.");
            }
            result.setData(moList);
        }
        return result;
    }

    protected static <T> List<Object> buildMoMap(List<T> moList, Class moType) throws InnerErrorServiceException {
        List<Object> listMapValue = new ArrayList<Object>();
        for(T mo : moList) {
            if(moType.isAssignableFrom(mo.getClass()) || mo.getClass().isAssignableFrom(moType)) {
                listMapValue.add(MOModelProcessor.process(JavaEntityUtil.getFiledValues(mo), moType));
            } else {
                LOGGER.error("Input mo type[" + mo.getClass().getName() + "] is not assigned from " + moType.getName());
                throw new InnerErrorServiceException("Input mo type[" + mo.getClass().getName()
                        + "] is not assigned from " + moType.getName());
            }
        }
        return listMapValue;
    }

    protected static String getAttr(List<String> attr) {
        StringBuilder sb = new StringBuilder();
        for(String str : attr) {
            if(sb.length() == 0) {
                sb.append(str);
            } else {
                sb.append(", ").append(str);
            }
        }
        return sb.toString();
    }

    private static <T> void processExtendsAttr(T mo, Class moType, Map<String, Object> moMap,
            List<String> extendAttrName) {
        List<Field> fields = ClassFieldManager.getInstance().getAllField(moType);
        for(Field field : fields) {
            MOExtendField extendFieldAnn = field.getAnnotation(MOExtendField.class);
            if(extendFieldAnn != null) {
                Object extendValue = JavaEntityUtil.getFieldValue(field.getName(), mo);
                if(extendValue instanceof Map) {
                    ((Map)extendValue).clear();
                    ((Map)extendValue).putAll(moMap);
                    return;
                } else {
                    LOGGER.debug("Invalid mo extend field because the value is not map:" + field.getName());
                }
            }
        }
    }
}
