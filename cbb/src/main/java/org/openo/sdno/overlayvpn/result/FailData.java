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

/**
 * Class of Fail Data.<br>
 * 
 * @param <T> User Data Class
 * @author
 * @version SDNO 0.5 June 3, 2016
 */
public class FailData<T> {

    private String errcode;

    private String errmsg;

    /**
     * user data
     */
    private T data;

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    public FailData() {
        super();
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param errcode error code
     * @param errmsg error message
     * @param data user data
     */
    public FailData(String errcode, String errmsg, T data) {
        super();
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.data = data;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * Override toString function<br>
     * 
     * @return description of FailData Object
     * @since SDNO 0.5
     */
    @Override
    public String toString() {
        return "FailData [errcode=" + errcode + ", errmsg=" + errmsg + "]";
    }

}
