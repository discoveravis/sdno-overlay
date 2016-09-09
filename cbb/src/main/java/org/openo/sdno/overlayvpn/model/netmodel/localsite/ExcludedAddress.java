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
 * Class of Excluded IpAddress.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class ExcludedAddress {

    /**
     * Start IpAddress
     */
    @AIp
    private String startIpAddress;

    /**
     * End IpAddress
     */
    @AIp
    private String endIpAddress;

    /**
     * @return Returns the startIpAddress.
     */
    public String getStartIpAddress() {
        return startIpAddress;
    }

    /**
     * @param startIpAddress The startIpAddress to set.
     */
    public void setStartIpAddress(String startIpAddress) {
        this.startIpAddress = startIpAddress;
    }

    /**
     * @return Returns the endIpAddress.
     */
    public String getEndIpAddress() {
        return endIpAddress;
    }

    /**
     * @param endIpAddress The endIpAddress to set.
     */
    public void setEndIpAddress(String endIpAddress) {
        this.endIpAddress = endIpAddress;
    }

}
