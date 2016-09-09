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

package org.openo.sdno.overlayvpn.result;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.errorcode.ErrorCodeInfo;
import org.springframework.util.StringUtils;

/**
 * Class of Result Response.<br>
 * 
 * @param <T> User Data Class
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
@SuppressWarnings("serial")
public class ResultRsp<T> implements Serializable {

    /**
     * Success ResultRsp
     */
    @SuppressWarnings("rawtypes")
    public static final ResultRsp SUCCESS = new ResultRsp(ErrorCode.OVERLAYVPN_SUCCESS);

    /**
     * Fail ResultRsp
     */
    @SuppressWarnings("rawtypes")
    public static final ResultRsp FAILED = new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);

    protected String errorCode = ErrorCode.OVERLAYVPN_SUCCESS;

    protected T data;

    /**
     * Description Argument
     */
    protected String descArg = "";

    /**
     * Reason Argument
     */
    protected String reasonArg = "";

    /**
     * Detail Argument
     */
    protected String detailArg = "";

    /**
     * Advice Argument
     */
    protected String adviceArg = "";

    /**
     * HttpCode of response
     */
    protected int httpCode = HttpCode.RESPOND_OK;

    /**
     * Error message
     */
    protected String message = "";

    private List<ErrorCodeInfo> smallErrorCodeList;

    private List<T> successed = null;

    private List<FailData<T>> fail = null;

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    public ResultRsp() {
        super();
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param errorCode error code
     */
    public ResultRsp(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param errorCode error code
     * @param descArg description argument
     * @param reasonArg reason argument
     * @param detailArg detail argument
     * @param adviceArg advice argument
     */
    public ResultRsp(String errorCode, String descArg, String reasonArg, String detailArg, String adviceArg) {
        super();
        this.errorCode = errorCode;
        this.descArg = descArg;
        this.reasonArg = reasonArg;
        this.detailArg = detailArg;
        this.adviceArg = adviceArg;
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param errorCode error code
     * @param data user data
     */
    public ResultRsp(String errorCode, T data) {
        super();
        this.errorCode = errorCode;
        this.data = data;
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param errorCode error code
     * @param excptArgs ExceptionArgs Object
     */
    public ResultRsp(String errorCode, ExceptionArgs excptArgs) {
        super();
        this.errorCode = errorCode;
        if(null != excptArgs) {
            this.descArg = getFisrtDataFromArry(excptArgs.getDescArgs());
            this.reasonArg = getFisrtDataFromArry(excptArgs.getReasonArgs());
            this.detailArg = getFisrtDataFromArry(excptArgs.getDetailArgs());
            this.adviceArg = getFisrtDataFromArry(excptArgs.getAdviceArgs());
        }
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param excpt ServiceException Object
     */
    public ResultRsp(ServiceException excpt) {
        this(excpt.getId(), excpt.getExceptionArgs());
        this.message = excpt.getMessage();
        this.httpCode = excpt.getHttpCode();
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param result ResultRsp Object
     */
    public ResultRsp(ResultRsp<?> result) {
        if(null != result) {
            this.errorCode = result.getErrorCode();
            this.descArg = result.getDescArg();
            this.reasonArg = result.getReasonArg();
            this.detailArg = result.getDetailArg();
            this.adviceArg = result.getAdviceArg();
            this.smallErrorCodeList = result.smallErrorCodeList;
        }
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param result ResultRsp Object
     * @param data user data
     */
    public ResultRsp(ResultRsp<?> result, T data) {
        this(result);
        this.data = data;
    }

    /**
     * Copy ResultRsp to this Object.<br>
     * 
     * @param result other ResultRsp Object
     * @since SDNO 0.5
     */
    public void copyInfo(ResultRsp<?> result) {
        if(null != result) {
            this.errorCode = result.getErrorCode();
            this.httpCode = result.getHttpCode();
            this.message = result.getMessage();
            this.descArg = result.getDescArg();
            this.reasonArg = result.getReasonArg();
            this.detailArg = result.getDetailArg();
            this.adviceArg = result.getAdviceArg();
            this.smallErrorCodeList = result.smallErrorCodeList;
        }
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    public String getAdviceArg() {
        return adviceArg;
    }

    public void setAdviceArg(String adviceArg) {
        this.adviceArg = adviceArg;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Check whether Result Response is Success.<br>
     * 
     * @return true if Result Response is Success
     * @since SDNO 0.5
     */
    @JsonIgnore
    public boolean isSuccess() {
        return ErrorCode.OVERLAYVPN_SUCCESS.equals(errorCode);
    }

    /**
     * Build Exception Arguments.<br>
     * 
     * @return exception arguments built
     * @since SDNO 0.5
     */
    public ExceptionArgs buidlExceptionArgs() {
        if(StringUtils.hasLength(message)) {
            while(message.endsWith(" ")) {
                message = message.substring(0, message.length() - 1);
            }
            if(StringUtils.hasLength(message)) {
                if(!message.endsWith(".") && !message.endsWith(",") && !message.endsWith("!")) {
                    message += ". ";
                } else {
                    message += " ";
                }
            }
        }
        String[] descArray = {descArg};
        String[] reasonArray = {reasonArg};
        String[] detailArray = {message + detailArg};
        String[] adviceArray = {message + adviceArg};
        return new ExceptionArgs(descArray, reasonArray, detailArray, adviceArray);
    }

    /**
     * Get First Error Message.<br>
     * 
     * @param dataArry list of Error Message
     * @return first error message
     * @since SDNO 0.5
     */
    private String getFisrtDataFromArry(String[] dataArry) {
        if((null != dataArry) && (dataArry.length > 0)) {
            return dataArry[0];
        }
        return null;
    }

    /**
     * Override toString function.<br>
     * 
     * @return description of ResultRep Object
     * @since SDNO 0.5
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(512);
        sb.append("errorcode = [").append(errorCode);
        sb.append("], descArg = [").append(descArg);
        sb.append("], reasonArg = [").append(reasonArg);
        sb.append("], detailArg = [").append(detailArg);
        sb.append("], adviceArg = [").append(adviceArg);
        sb.append("], message = [").append(message).append(']');
        return sb.toString();
    }

    /**
     * Check whether ResultRsp is valid.<br>
     * 
     * @return true if ResultRsp is success result and contains data.
     * @since SDNO 0.5
     */
    @JsonIgnore
    public boolean isValid() {
        return isSuccess() && this.getData() != null;
    }

    public List<ErrorCodeInfo> getSmallErrorCodeList() {
        return smallErrorCodeList;
    }

    public void setSmallErrorCodeList(List<ErrorCodeInfo> smallErrorCodeList) {
        this.smallErrorCodeList = smallErrorCodeList;
    }

    public List<T> getSuccessed() {
        return successed;
    }

    public void setSuccessed(List<T> successed) {
        this.successed = successed;
    }

    public List<FailData<T>> getFail() {
        return fail;
    }

    public void setFail(List<FailData<T>> fail) {
        this.fail = fail;
    }
}
