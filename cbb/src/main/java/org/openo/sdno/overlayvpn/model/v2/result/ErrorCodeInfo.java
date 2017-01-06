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

package org.openo.sdno.overlayvpn.model.v2.result;

/**
 * Class of Error Code Info.<br>
 * 
 * @author
 * @version SDNO 0.5 2017-1-6
 */
public class ErrorCodeInfo {

    /**
     * Error code
     */
    private String errorCode;

    /**
     * Description
     */
    private String description;

    /**
     * Object Id
     */
    private String objectId;

    /**
     * Object Name
     */
    private String objectName;

    /**
     * Object
     */
    private Object object;

    /**
     * Description Argument
     */
    private String descArg = null;

    /**
     * Reason Argument
     */
    private String reasonArg = null;

    /**
     * Detail Argument
     */
    private String detailArg = null;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getDescArg() {
        return descArg;
    }

    public void setDescArg(String descArg) {
        this.descArg = descArg;
    }

    public String getReasonArg() {
        return reasonArg;
    }

    public void setReasonArg(String reasonArg) {
        this.reasonArg = reasonArg;
    }

    public String getDetailArg() {
        return detailArg;
    }

    public void setDetailArg(String detailArg) {
        this.detailArg = detailArg;
    }

}
