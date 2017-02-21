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

package org.openo.sdno.overlayvpn.util.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

/**
 * The tool class of json.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 3, 2017
 */
public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {

    }

    /**
     * Get value from json body through the key.<br>
     * 
     * @param jsonBody The json body
     * @param key The key
     * @return The value of the key in json
     * @throws ServiceException when the jsonBody is invalid json format
     * @since SDNO 0.5
     */
    public static String getJsonByKey(String jsonBody, String key) throws ServiceException {
        if(StringUtils.isEmpty(jsonBody) || StringUtils.isEmpty(key)) {
            return null;
        }
        if(StringUtils.startsWith(jsonBody, "{")) {
            JSONObject jobj = toJsonObject(jsonBody);
            return jobj.containsKey(key) ? jobj.getString(key) : null;
        } else if(StringUtils.startsWith(jsonBody, "[")) {
            JSONArray jarray = toJsonArray(jsonBody);
            if(jarray == null || jarray.isEmpty()) {
                return null;
            }
            JSONObject jobj = jarray.getJSONObject(0);
            return jobj.containsKey(key) ? jobj.getString(key) : null;
        } else {
            return null;
        }
    }

    /**
     * Translate the object to json.<br>
     * 
     * @param obj The object to be translated
     * @return The result of translating
     * @throws ServiceException when parser object to json error
     * @since SDNO 0.5
     */
    public static String toJson(Object obj) throws ServiceException {
        if(obj == null) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(Inclusion.NON_NULL);
            return mapper.writeValueAsString(obj);
        } catch(IOException ex) {
            LOGGER.error("Parser obj to json error,obj=" + obj, ex);
            throw new InnerErrorServiceException("Json.parse.error,Parser obj to json error,obj=" + obj);
        }
    }

    /**
     * Parse json to a simple object list.<br>
     * 
     * @param jsonBody The json body
     * @param classz The object class type
     * @return The simple object list
     * @throws ServiceException when translating failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> parseJson2SimpleList(String jsonBody, Class<T> classz) throws ServiceException {
        List<T> list = new ArrayList<>();
        if(StringUtils.isEmpty(jsonBody)) {
            return list;
        }
        JSONArray jarray = toJsonArray(jsonBody);
        for(Object obj : jarray) {
            if(isJsonNull(obj)) {
                continue;
            }
            if(String.class.isAssignableFrom(classz)) {
                list.add((T)obj.toString());
            } else {
                list.add(fromJson(toJson(parseJson2SimpleMap(obj.toString())), classz));
            }
        }
        return list;
    }

    /**
     * Parse json to a map list.<br>
     * 
     * @param jsonStr The json body
     * @return The map list
     * @throws ServiceException when translating failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> parseJson2MapList(String jsonStr) throws ServiceException {
        List<Map<String, Object>> list = new ArrayList<>();
        if(StringUtils.isEmpty(jsonStr)) {
            return list;
        }
        Iterator<JSONObject> it = toJsonArray(jsonStr).iterator();
        while(it.hasNext()) {
            list.add(parseJson2SimpleMap(it.next().toString()));
        }
        return list;
    }

    /**
     * Parse json to a simple object map.<br>
     * 
     * @param jsonStr The json body
     * @return The simple object map
     * @throws ServiceException when translating failed
     * @since SDNO 0.5
     */
    public static Map<String, Object> parseJson2SimpleMap(String jsonStr) throws ServiceException {
        if(StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        JSONObject jsonObject = toJsonObject(jsonStr);
        for(Object jsonKey : jsonObject.keySet()) {
            if(isJsonNull(jsonKey)) {
                continue;
            }
            Object jobj = jsonObject.get(jsonKey);
            if(isJsonNull(jobj)) {
                continue;
            }
            result.put(jsonKey.toString(), jobj.toString());
        }
        return result;
    }

    /**
     * Translate json to object.<br>
     * 
     * @param jsonStr The json body
     * @param objClass The object class type
     * @return The object
     * @throws ServiceException when translating failed
     * @since SDNO 0.5
     */
    public static <T> T fromJson(String jsonStr, Class<T> objClass) throws ServiceException {
        if(StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(jsonStr, objClass);
        } catch(IOException ex) {
            LOGGER.error("Parser json to object error,json=" + jsonStr + ", expect class =" + objClass, ex);
            throw new InnerErrorServiceException(
                    "Json.parse.error,Parser json to object error,json=" + jsonStr + ", expect class =" + objClass);
        }
    }

    /**
     * Translate json to object.<br>
     * 
     * @param jsonStr The json body
     * @param typeRef The object class type
     * @return The object
     * @throws ServiceException when translating failed
     * @since SDNO 0.5
     */
    public static <T> T fromJson(String jsonStr, TypeReference<T> typeRef) throws ServiceException {
        if(StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.readValue(jsonStr, typeRef);
        } catch(IOException ex) {
            LOGGER.error("Parser json to object error,json=" + jsonStr + ",expect class =" + typeRef.getType(), ex);
            throw new InnerErrorServiceException("Json.parse.error,Parser json to object error,json=" + jsonStr
                    + ",expect class =" + typeRef.getType());
        }
    }

    private static JSONObject toJsonObject(String jsonBody) throws ServiceException {
        try {
            return JSONObject.fromObject(jsonBody);
        } catch(JSONException ex) {
            LOGGER.error("Parse json object failed:", ex);
            throw new InnerErrorServiceException("Json.parse.error,parse json object failed:" + ex.getMessage());
        }
    }

    private static JSONArray toJsonArray(String jsonBody) throws ServiceException {
        try {
            return JSONArray.fromObject(jsonBody);
        } catch(JSONException ex) {
            LOGGER.error("Parse json array failed:", ex);
            throw new InnerErrorServiceException("Json.parse.error,parse json array failed:" + ex.getMessage());
        }
    }

    private static boolean isJsonNull(Object obj) {
        return obj == null || StringUtils.isEmpty(obj.toString()) || JSONNull.class.isAssignableFrom(obj.getClass());
    }
}
