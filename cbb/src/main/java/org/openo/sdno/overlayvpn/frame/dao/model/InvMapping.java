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

package org.openo.sdno.overlayvpn.frame.dao.model;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;

/**
 * The inventory mapping model.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
public class InvMapping {

    private String attrName;

    private String invName;

    private InvMappingType mappingType;

    private Class<?> resType;

    /**
     * The inventory mapping type.<br>
     * 
     * @author
     * @version SDNO 0.5 Feb 4, 2017
     */
    public enum InvMappingType {
        STRING, JSON, JSON_LIST;
    }

    /**
     * Constructor.<br>
     * 
     * @param attrName The attrName to set
     * @param invName The invName to set
     * @since SDNO 0.5
     */
    public InvMapping(String attrName, String invName) {
        super();
        this.attrName = attrName;
        this.invName = invName;
        this.mappingType = InvMappingType.STRING;
    }

    /**
     * Set json type.<br>
     * 
     * @param mappingType The mappingType to set
     * @param resType The resType to set
     * @since SDNO 0.5
     */
    public void setJsonType(InvMappingType mappingType, Class<?> resType) {
        this.mappingType = mappingType == null ? InvMappingType.STRING : mappingType;
        this.resType = resType;
    }

    /**
     * Get modelValue as string.<br>
     * 
     * @param modelValue The modelValue to process
     * @return The modelValue as string
     * @throws ServiceException when translating object to json failed
     * @since SDNO 0.5
     */
    public String getInvValue(Object modelValue) throws ServiceException {
        if(modelValue == null || "".equals(modelValue)) {
            return null;
        }
        return InvMappingType.STRING == mappingType ? String.valueOf(modelValue) : JsonUtils.toJson(modelValue);
    }

    /**
     * Get invValue as object through the mappingType.<br>
     * 
     * @param invValue The invValue to process
     * @return The invValue as object
     * @throws ServiceException when translating json to object failed
     * @since SDNO 0.5
     */
    public Object getModelValue(Object invValue) throws ServiceException {
        if(invValue == null || "".equals(invValue)) {
            return null;
        }
        String dbValue = String.valueOf(invValue);
        Object modelValue = null;
        switch(mappingType) {
            case STRING:
                modelValue = dbValue;
                break;
            case JSON:
                modelValue = JsonUtils.fromJson(dbValue, resType);
                break;
            case JSON_LIST:
                modelValue = JsonUtils.parseJson2SimpleList(dbValue, resType);
                break;
            default:
                modelValue = null;
                break;
        }
        return modelValue;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }

    public InvMappingType getMappingType() {
        return mappingType;
    }

    public void setMappingType(InvMappingType mappingType) {
        this.mappingType = mappingType;
    }

    public Class<?> getResType() {
        return resType;
    }

    public void setResType(Class<?> resType) {
        this.resType = resType;
    }

}
