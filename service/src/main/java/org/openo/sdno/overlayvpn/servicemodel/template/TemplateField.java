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

/**
 * The template field model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class TemplateField extends BaseMo {

    private String type;

    private String value;

    private String valueRange;

    private String serviceID;

    private String minCardinality;

    private String maxCardinality;

    private String charOrder;

    private String encrypt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueRange() {
        return valueRange;
    }

    public void setValueRange(String valueRange) {
        this.valueRange = valueRange;
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

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }
}
