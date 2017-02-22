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

package org.openo.sdno.overlayvpn.composer.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOEditableField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOPrivacyField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONSBIField;
import org.openo.sdno.overlayvpn.model.v2.overlay.BaseModel;
import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;
import org.openo.sdno.overlayvpn.servicemodel.enums.DeployState;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.openo.sdno.util.reflect.ReflectionUtil;

/**
 * The tool class to deal the service model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 22, 2017
 */
public class ModelServiceUtil {

    private ModelServiceUtil() {

    }

    /**
     * Get uuid list of model list<br>
     * 
     * @param models The model list to be got
     * @return The uuid list
     * @since SDNO 0.5
     */
    public static List<String> getUuidList(List<? extends BaseModel> models) {
        List<String> uuids = new ArrayList<>();
        if(CollectionUtils.isEmpty(models)) {
            return uuids;
        }
        for(BaseModel model : models) {
            uuids.add(model.getId());
        }
        return uuids;
    }

    /**
     * Update the deploy state of model in DB.<br>
     * 
     * @param state The deploy state
     * @param models The model list
     * @throws ServiceException When constructing model failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T extends ServiceModel> void updateDeployState(DeployState state, List<T> models)
            throws ServiceException {
        if(CollectionUtils.isEmpty(models)) {
            return;
        }
        Class<T> resType = (Class<T>)models.get(0).getClass();
        List<T> conds = new ArrayList<>();
        for(ServiceModel model : models) {
            T cond = ModelUtils.newInstance(resType);
            if(StringUtils.isNotEmpty(model.getId())) {
                cond.setId(model.getId());
                cond.setDeployStatus(state.getState());
                conds.add(cond);
            }
        }
        new BaseDao<T>().update(conds);
    }

    /**
     * Update the action state of model in DB.<br>
     * 
     * @param state The action state
     * @param model The model
     * @throws ServiceException When constructing model failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T extends ServiceModel> void updateActionState(ActionState state, T model) throws ServiceException {
        if(model == null) {
            return;
        }
        Class<T> resType = (Class<T>)model.getClass();
        List<T> conds = new ArrayList<>();
        T cond = ModelUtils.newInstance(resType);
        if(StringUtils.isNotEmpty(model.getId())) {
            cond.setId(model.getId());
            cond.setActionState(state.getState());
            conds.add(cond);
        }
        new BaseDao<T>().update(conds);
    }

    /**
     * Update the action state of model in DB by uuid.<br>
     * 
     * @param state The action state
     * @param uuid The model uuid
     * @param resType The model resType
     * @throws ServiceException When constructing model failed
     * @since SDNO 0.5
     */
    public static <T extends ServiceModel> void updateActionState(ActionState state, String uuid, Class<T> resType)
            throws ServiceException {
        List<T> conds = new ArrayList<>();
        T cond = ModelUtils.newInstance(resType);
        if(StringUtils.isNotEmpty(uuid)) {
            cond.setId(uuid);
            cond.setActionState(state.getState());
            conds.add(cond);
        }
        new BaseDao<T>().update(conds);
    }

    /**
     * Whether the service is on going.<br>
     * 
     * @param model The service model
     * @return true when the the service is on going,otherwise is false
     * @since SDNO 0.5
     */
    public static <T extends ServiceModel> boolean isOnGoing(T model) {
        ActionState status = ActionState.fromState(model.getActionState());
        if(!status.isDoing()) {
            return false;
        }
        Long createTime = model.getCreatetime() != null ? model.getCreatetime() : 0L;
        Long updateTime = model.getUpdatetime() != null ? model.getUpdatetime() : 0L;
        long currentTime = System.currentTimeMillis() / 1000L;
        long escape = 0L;
        if(ActionState.CREATING == status) {
            escape = currentTime - createTime;
        } else {
            escape = currentTime - updateTime;
        }
        return escape < ParamConsts.MAX_DOING_SECONDS;
    }

    /**
     * Hidden not editable field.<br>
     * 
     * @param model The model to be dealt
     * @since SDNO 0.5
     */
    public static <T extends BaseModel> void hidUneditableField(T model) {
        if(model == null) {
            return;
        }
        List<Field> fields = JavaEntityUtil.getAllField(model.getClass());
        for(Field field : fields) {
            MOEditableField annotation = field.getAnnotation(MOEditableField.class);
            if(annotation == null) {
                ReflectionUtil.setFieldValue(model, field.getName(), null);
            }
        }
    }

    /**
     * Hidden not sbi field.<br>
     * 
     * @param model The model to be dealt
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public static void hidNonSbiField(Object model) {
        if(model == null) {
            return;
        }
        Class<?> resType = model.getClass();
        if(BaseModel.class.isAssignableFrom(resType)) {
            List<Field> fields = JavaEntityUtil.getAllField(resType);
            for(Field field : fields) {
                NONSBIField annotation = field.getAnnotation(NONSBIField.class);
                if(annotation != null) {
                    ReflectionUtil.setFieldValue(model, field.getName(), null);
                    continue;
                }
                Class<?> fieldType = field.getType();
                if(BaseModel.class.isAssignableFrom(fieldType) || Collection.class.isAssignableFrom(fieldType)) {
                    hidNonSbiField(ReflectionUtil.getFieldValue(model, field.getName()));
                }
            }
        } else if(Collection.class.isAssignableFrom(resType)) {
            Collection collection = (Collection)model;
            for(Object field : collection) {
                hidNonSbiField(field);
            }
        }
    }

    /**
     * Deal with the restful response,need to deal with privacy field.<br>
     * 
     * @param obj The response object
     * @return The dealt response
     * @throws ServiceException When deal with privacy field failed
     * @since SDNO 0.5
     */
    public static String processRestResponse(Object obj) throws ServiceException {
        processPrivacy(obj);
        return JsonUtils.toJson(obj);
    }

    /**
     * Construct query Conditions.<br>
     * 
     * @param req The HttpServletRequest object
     * @param resType The resource type
     * @return The query condition object
     * @throws ServiceException when construct object failed
     * @since SDNO 0.5
     */
    public static <T> T constructQueryCondition(HttpServletRequest req, Class<T> resType) throws ServiceException {
        Map<String, String[]> queryMap = req.getParameterMap();
        T cond = ModelUtils.newInstance(resType);
        if(MapUtils.isEmpty(queryMap)) {
            return cond;
        }
        Set<String> attrs = new HashSet<>();
        for(Field field : JavaEntityUtil.getAllField(resType)) {
            attrs.add(field.getName());
        }
        Map<String, String> queryParams = new HashMap<String, String>();
        for(String param : queryMap.keySet()) {
            String[] values = queryMap.get(param);
            if(!attrs.contains(param) || ArrayUtils.isEmpty(values) || values[0] == null) {
                continue;
            }
            queryParams.put(param, values[0]);
        }
        ModelUtils.setValueMap(cond, queryParams);
        return cond;
    }

    /**
     * Initialize uuid of model.<br>
     * 
     * @param models The model to be initialized
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public static void initUuid(Object... models) {
        if(ArrayUtils.isEmpty(models)) {
            return;
        }
        Class<?> resType = models[0].getClass();
        if(!BaseModel.class.isAssignableFrom(resType)) {
            return;
        }
        List<Field> fields = JavaEntityUtil.getAllField(resType);
        for(Object model : models) {
            ((BaseModel)model).allocateUuid();
            for(Field field : fields) {
                Object fieldValue = JavaEntityUtil.getFieldValue(field.getName(), model);
                Class<?> fieldType = field.getType();
                if(fieldValue == null) {
                    continue;
                }
                if(BaseModel.class.isAssignableFrom(fieldType)) {
                    ((BaseModel)fieldValue).allocateUuid();
                } else if(Collection.class.isAssignableFrom(fieldType)) {
                    initUuid(((Collection)fieldValue).toArray());
                } else {
                    continue;
                }
            }
        }
    }

    /**
     * Process the privacy information of the object.<br>
     * 
     * @param object The object need to process
     * @throws ServiceException when translating the object to json or translating the json to
     *             object failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    private static void processPrivacy(Object object) throws ServiceException {
        if(object == null) {
            return;
        }
        Object data = (object instanceof BatchQueryResult) ? ((BatchQueryResult)object).getObjects() : object;
        if(!isPrivacyPossible(data)) {
            return;
        }
        List<BaseModel> datas = new ArrayList<>();
        if(BaseModel.class.isAssignableFrom(data.getClass())) {
            datas.add((BaseModel)data);
        } else {
            Collection list = (Collection)data;
            Iterator iterator = list.iterator();
            while(iterator.hasNext()) {
                datas.add((BaseModel)iterator.next());
            }
        }
        List<Field> fields = JavaEntityUtil.getAllField(datas.get(0).getClass());
        for(BaseModel model : datas) {
            for(Field field : fields) {
                provessPrivacyField(model, field);
            }
        }
    }

    private static void provessPrivacyField(BaseModel model, Field field) throws ServiceException {
        MOPrivacyField anno = field.getAnnotation(MOPrivacyField.class);
        if(anno != null) {
            if("json".equalsIgnoreCase(anno.type())) {
                Object fieldValue = ReflectionUtil.getFieldValue(model, field.getName());
                if(fieldValue == null) {
                    return;
                }
                String json = String.valueOf(fieldValue);
                Object subModel = JsonUtils.fromJson(json, anno.classz());
                processPrivacy(subModel);
                ReflectionUtil.setFieldValue(model, field.getName(), JsonUtils.toJson(subModel));
            } else {
                ReflectionUtil.setFieldValue(model, field.getName(), null);
            }
        } else {
            Object fieldValue = ReflectionUtil.getFieldValue(model, field.getName());
            processPrivacy(fieldValue);
        }
    }

    @SuppressWarnings("rawtypes")
    private static boolean isPrivacyPossible(Object obj) {
        if(obj == null) {
            return false;
        }
        Class<? extends Object> resType = obj.getClass();
        if(BaseModel.class.isAssignableFrom(resType)) {
            return true;
        } else if(Collection.class.isAssignableFrom(resType)) {
            Collection list = (Collection)obj;
            return CollectionUtils.isNotEmpty(list)
                    && BaseModel.class.isAssignableFrom(list.iterator().next().getClass());
        } else {
            return false;
        }
    }
}
