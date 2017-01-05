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

package org.openo.sdno.overlayvpn.model.v2.basemodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorCodeInfo", propOrder = {"errorCode", "description", "objectId", "objectName", "object", "descArg",
                "reasonArg", "detailArg"})

@XmlRootElement(name = "ErrorCodeInfo")
@ApiModel(description = "Error code information model")
public class ErrorCodeInfo {

    @XmlElement(name = "errorCode")
    @ApiModelProperty(example = "null", value = "error code")
    private String errorCode = null;

    @XmlElement(name = "description")
    @ApiModelProperty(example = "null", value = "error description")
    private String description = null;

    @XmlElement(name = "objectId")
    @ApiModelProperty(example = "null", value = "object Id")
    private String objectId = null;

    @XmlElement(name = "objectName")
    @ApiModelProperty(example = "null", value = "object Name")
    private String objectName = null;

    @XmlElement(name = "object")
    @ApiModelProperty(example = "null", value = "object")
    private Object object = null;

    @XmlElement(name = "descArg")
    @ApiModelProperty(example = "null", value = "The description args")
    private String descArg = null;

    @XmlElement(name = "reasonArg")
    @ApiModelProperty(example = "null", value = "The reason args")
    private String reasonArg = null;

    @XmlElement(name = "detailArg")
    @ApiModelProperty(example = "null", value = "The detail args")
    private String detailArg = null;

    /**
     * error code
     * 
     * @return errorCode
     **/
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * error description
     * 
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * object Id
     * 
     * @return objectId
     **/
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * object Name
     * 
     * @return objectName
     **/
    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * object
     * 
     * @return object
     **/
    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * The description args
     * 
     * @return descArg
     **/
    public String getDescArg() {
        return descArg;
    }

    public void setDescArg(String descArg) {
        this.descArg = descArg;
    }

    /**
     * The reason args
     * 
     * @return reasonArg
     **/
    public String getReasonArg() {
        return reasonArg;
    }

    public void setReasonArg(String reasonArg) {
        this.reasonArg = reasonArg;
    }

    /**
     * The detail args
     * 
     * @return detailArg
     **/
    public String getDetailArg() {
        return detailArg;
    }

    public void setDetailArg(String detailArg) {
        this.detailArg = detailArg;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ErrorCodeInfo {\n");

        sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    objectId: ").append(toIndentedString(objectId)).append("\n");
        sb.append("    objectName: ").append(toIndentedString(objectName)).append("\n");
        sb.append("    object: ").append(toIndentedString(object)).append("\n");
        sb.append("    descArg: ").append(toIndentedString(descArg)).append("\n");
        sb.append("    reasonArg: ").append(toIndentedString(reasonArg)).append("\n");
        sb.append("    detailArg: ").append(toIndentedString(detailArg)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(Object o) {
        if(o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
