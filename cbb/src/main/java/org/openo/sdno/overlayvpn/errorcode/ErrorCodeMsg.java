/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.errorcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Error Code Message Class.<br/>
 * 
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
public class ErrorCodeMsg {

    /**
     * Big Error Code
     */
    private ErrorCodeInfo tatalErrorCode = new ErrorCodeInfo(ErrorCode.OVERLAYVPN_SUCCESS);

    /**
     * Small Error Code
     */
    private List<ErrorCodeInfo> smallErrorCodeList;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     */
    public ErrorCodeMsg() {
        super();
    }

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param retCode error code
     */
    public ErrorCodeMsg(String retCode) {
        tatalErrorCode = new ErrorCodeInfo(retCode);
        smallErrorCodeList = new ArrayList<ErrorCodeInfo>();
    }

    /**
     * Get tatalErrorCode attribute.<br/>
     * 
     * @return tatalErrorCode attribute
     * @since SDNO 0.5
     */
    public ErrorCodeInfo getTatalErrorCode() {
        return tatalErrorCode;
    }

    /**
     * Set tatalErrorCode attribute.<br/>
     * 
     * @param tatalErrorCode ErrorCodeInfo Object
     * @since SDNO 0.5
     */
    public void setTatalErrorCode(ErrorCodeInfo tatalErrorCode) {
        this.tatalErrorCode = tatalErrorCode;
    }

    /**
     * Get smallErrorCodeList attribute.<br/>
     * 
     * @return smallErrorCodeList attribute
     * @since SDNO 0.5
     */
    public List<ErrorCodeInfo> getSmallErrorCodeList() {
        return smallErrorCodeList;
    }

    /**
     * Set smallErrorCodeList attribute.<br/>
     * 
     * @param smallErrorCodeList List<ErrorCodeInfo> Object
     * @since SDNO 0.5
     */
    public void setSmallErrorCodeList(List<ErrorCodeInfo> smallErrorCodeList) {
        this.smallErrorCodeList = smallErrorCodeList;
    }

}
