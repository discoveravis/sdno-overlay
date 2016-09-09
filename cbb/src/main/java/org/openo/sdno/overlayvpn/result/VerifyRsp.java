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

/**
 * Class of Verification Response.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
@SuppressWarnings("serial")
public class VerifyRsp implements Serializable {

    /**
     * id
     */
    protected String uuid;

    /**
     * return code
     */
    protected int retcode;

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    public VerifyRsp() {
        super();
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param retcode return code
     */
    public VerifyRsp(int retcode) {
        super();
        this.retcode = retcode;
    }

    /**
     * Constructor<br>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     * @param retcode return code
     * @param uuid key id
     */
    public VerifyRsp(int retcode, String uuid) {
        super();
        this.retcode = retcode;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }
}
