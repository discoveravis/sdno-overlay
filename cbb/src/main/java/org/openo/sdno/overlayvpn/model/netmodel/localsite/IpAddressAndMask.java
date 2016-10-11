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

package org.openo.sdno.overlayvpn.model.netmodel.localsite;

import org.openo.sdno.overlayvpn.verify.annotation.AIp;

/**
 * Class of IpAddressAndMask.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 6, 2016
 */
public class IpAddressAndMask {

    @AIp
    private String ipAddress;

    @AIp
    private String mask;

    /**
     * @return Returns the ipAddress.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress The ipAddress to set.
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return Returns the mask.
     */
    public String getMask() {
        return mask;
    }

    /**
     * @param mask The mask to set.
     */
    public void setMask(String mask) {
        this.mask = mask;
    }

}
