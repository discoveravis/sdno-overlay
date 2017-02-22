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

package org.openo.sdno.overlayvpn.servicemodel.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * The spec model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class Spec extends BaseMo {

    private String type;

    private String serviceID;

    private String minCardinality;

    private String maxCardinality;

    private String charOrder;

    private List<TemplateField> fieldList;

    private List<Spec> specList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getMinCardinality() {
        return minCardinality;
    }

    public void setMinCardinality(String minCardinality) {
        this.minCardinality = minCardinality;
    }

    public String getMaxCardinality() {
        return maxCardinality;
    }

    public void setMaxCardinality(String maxCardinality) {
        this.maxCardinality = maxCardinality;
    }

    public String getCharOrder() {
        return charOrder;
    }

    public void setCharOrder(String charOrder) {
        this.charOrder = charOrder;
    }

    public List<TemplateField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<TemplateField> fieldList) {
        this.fieldList = fieldList;
    }

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    /**
     * Translate spec value to map.<br>
     * 
     * @param spec The spec model
     * @return The translated spec as map
     * @since SDNO 0.5
     */
    public static Map<String, String> toValueMap(Spec spec) {
        Map<String, String> valueMap = new HashMap<String, String>();
        if(spec == null || CollectionUtils.isEmpty(spec.getFieldList())) {
            return valueMap;
        }
        for(TemplateField field : spec.getFieldList()) {
            if(StringUtils.isNotEmpty(field.getName())) {
                valueMap.put(field.getName(), field.getValue());
            }
        }
        return valueMap;
    }

    /**
     * Translate spec value to map.<br>
     * 
     * @param spec The spec model
     * @return The translated spec as map
     * @since SDNO 0.5
     */
    public static Map<String, Map<String, String>> toSubSpecValueMap(Spec spec) {
        Map<String, Map<String, String>> valueMap = new HashMap<String, Map<String, String>>();
        if(spec == null || CollectionUtils.isEmpty(spec.getSpecList())) {
            return valueMap;
        }
        for(Spec sub : spec.getSpecList()) {
            valueMap.put(sub.getName(), Spec.toValueMap(sub));
        }
        return valueMap;
    }
}
