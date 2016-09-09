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

package org.openo.sdno.overlayvpn.errorcode;

/**
 * ErrorCode Info Class.<br>
 * 
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
public class ErrorCodeInfo {

    private String errorCode;

    private String description;

    private String objectId;

    /**
     * Fail Object name
     */
    private String objectName;

    /**
     * Fail Object
     */
    private Object object;

    /**
     * Description Argument
     */
    private String descArg = "";

    /**
     * Reason Argument
     */
    private String reasonArg = "";

    /**
     * Detail Description Argument
     */
    private String detailArg = "";

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public ErrorCodeInfo() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param errorCode error code
     */
    public ErrorCodeInfo(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param errorCode error code
     * @param obj fail Object
     */
    public ErrorCodeInfo(String errorCode, Object obj) {
        super();
        this.errorCode = errorCode;
        this.object = obj;
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param errorCode error code
     * @param detailArg detail description
     * @param objectName Object name
     */
    public ErrorCodeInfo(String errorCode, String detailArg, String objectName) {
        super();
        this.errorCode = errorCode;
        this.detailArg = detailArg;
        this.objectName = objectName;
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param errorCode error code
     * @param descArg detail description
     * @param objectId Object id
     * @param objectName Object name
     */
    public ErrorCodeInfo(String errorCode, String descArg, String objectId, String objectName) {
        super();
        this.errorCode = errorCode;
        this.objectId = objectId;
        this.objectName = objectName;
        this.descArg = descArg;
    }

    /**
     * Get description attribute.<br>
     * 
     * @return description attribute
     * @since SDNO 0.5
     */
    public String getDescrption() {
        return description;
    }

    /**
     * Set description attribute.<br>
     * 
     * @param descrption String Object
     * @since SDNO 0.5
     */
    public void setDescrption(String descrption) {
        this.description = descrption;
    }

    /**
     * Get objectId attribute.<br>
     * 
     * @return objectId attribute
     * @since SDNO 0.5
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * Set objectId attribute.<br>
     * 
     * @param objectId String Object
     * @since SDNO 0.5
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * Set objectName attribute.<br>
     * 
     * @param objectName String Object
     * @since SDNO 0.5
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * Get objectName attribute.<br>
     * 
     * @return objectName attribute
     * @since SDNO 0.5
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * Get errorCode attribute.<br>
     * 
     * @return errorCode attribute
     * @since SDNO 0.5
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Set errorCode attribute.<br>
     * 
     * @param errorCode String Object
     * @since SDNO 0.5
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Get object attribute.<br>
     * 
     * @return object attribute
     * @since SDNO 0.5
     */
    public Object getObject() {
        return object;
    }

    /**
     * Set object attribute.<br>
     * 
     * @param object Object
     * @since SDNO 0.5
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * Set descArg attribute.<br>
     * 
     * @param descArg String Object
     * @since SDNO 0.5
     */
    public void setDescArg(String descArg) {
        this.descArg = descArg;
    }

    /**
     * Get descArg attribute.<br>
     * 
     * @return descArg attribute
     * @since SDNO 0.5
     */
    public String getDescArg() {
        return descArg;
    }

    /**
     * Set reasonArg attribute.<br>
     * 
     * @param reasonArg String Object
     * @since SDNO 0.5
     */
    public void setReasonArg(String reasonArg) {
        this.reasonArg = reasonArg;
    }

    /**
     * Get reasonArg attribute.<br>
     * 
     * @return reasonArg attribute
     * @since SDNO 0.5
     */
    public String getReasonArg() {
        return reasonArg;
    }

    /**
     * Set detailArg attribute.<br>
     * 
     * @param detailArg String Object
     * @since SDNO 0.5
     */
    public void setDetailArg(String detailArg) {
        this.detailArg = detailArg;
    }

    /**
     * Get detailArg attribute.<br>
     * 
     * @return detailArg attribute
     * @since SDNO 0.5
     */
    public String getDetailArg() {
        return detailArg;
    }

}
