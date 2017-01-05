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
@XmlType(name = "ResultResponse", propOrder = {"errorCode", "descArg", "reasonArg", "detailArg", "adviceArg",
                "httpCode", "message", "smallErrorCodeList"})

@XmlRootElement(name = "ResultResponse")
@ApiModel(description = "Result response model")
public class ResultResponse {

    @XmlElement(name = "errorCode")
    @ApiModelProperty(example = "null", required = true, value = "result error code")
    private String errorCode = null;

    @XmlElement(name = "descArg")
    @ApiModelProperty(example = "null", value = "The description args")
    private String descArg = null;

    @XmlElement(name = "reasonArg")
    @ApiModelProperty(example = "null", value = "The reason args")
    private String reasonArg = null;

    @XmlElement(name = "detailArg")
    @ApiModelProperty(example = "null", value = "The detail args")
    private String detailArg = null;

    @XmlElement(name = "adviceArg")
    @ApiModelProperty(example = "null", value = "The advice args")
    private String adviceArg = null;

    @XmlElement(name = "httpCode")
    @ApiModelProperty(example = "null", required = true, value = "The http status code")
    private Integer httpCode = null;

    @XmlElement(name = "message")
    @ApiModelProperty(example = "null", value = "the exception message word")
    private String message = null;

    @XmlElement(name = "smallErrorCodeList")
    @ApiModelProperty(example = "null", value = "")
    private ErrorCodeInfo smallErrorCodeList = null;

    /**
     * result error code
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

    /**
     * The advice args
     * 
     * @return adviceArg
     **/
    public String getAdviceArg() {
        return adviceArg;
    }

    public void setAdviceArg(String adviceArg) {
        this.adviceArg = adviceArg;
    }

    /**
     * The http status code
     * 
     * @return httpCode
     **/
    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * the exception message word
     * 
     * @return message
     **/
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get smallErrorCodeList
     * 
     * @return smallErrorCodeList
     **/
    public ErrorCodeInfo getSmallErrorCodeList() {
        return smallErrorCodeList;
    }

    public void setSmallErrorCodeList(ErrorCodeInfo smallErrorCodeList) {
        this.smallErrorCodeList = smallErrorCodeList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResultResponse {\n");

        sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
        sb.append("    descArg: ").append(toIndentedString(descArg)).append("\n");
        sb.append("    reasonArg: ").append(toIndentedString(reasonArg)).append("\n");
        sb.append("    detailArg: ").append(toIndentedString(detailArg)).append("\n");
        sb.append("    adviceArg: ").append(toIndentedString(adviceArg)).append("\n");
        sb.append("    httpCode: ").append(toIndentedString(httpCode)).append("\n");
        sb.append("    message: ").append(toIndentedString(message)).append("\n");
        sb.append("    smallErrorCodeList: ").append(toIndentedString(smallErrorCodeList)).append("\n");
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
