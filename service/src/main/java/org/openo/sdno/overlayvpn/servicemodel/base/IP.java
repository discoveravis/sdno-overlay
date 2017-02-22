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

package org.openo.sdno.overlayvpn.servicemodel.base;

import org.apache.commons.lang.StringUtils;
import org.openo.sdno.overlayvpn.util.toolkit.IpAddressUtil;

/**
 * The IP model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class IP {

    private String ipv4;

    private String ipv6;

    private String ipMask;

    private String prefixLength;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public IP() {
        super();
    }

    /**
     * Build ipv4 from cidr.<br>
     * 
     * @param cidr The cidr as string
     * @return The IP contains IPv4 and IpMask
     * @since SDNO 0.5
     */
    public static IP buildIPv4(String cidr) {
        if(StringUtils.isEmpty(cidr)) {
            return null;
        }
        IP ipv4 = new IP();
        ipv4.setIpv4(IpAddressUtil.getIpFromCidr(cidr));
        ipv4.setIpMask(String.valueOf(IpAddressUtil.maskToPrefix(IpAddressUtil.getMaskFromCidr(cidr))));
        return ipv4;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public String getIpMask() {
        return ipMask;
    }

    public void setIpMask(String ipMask) {
        this.ipMask = ipMask;
    }

    public String getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(String prefixLength) {
        this.prefixLength = prefixLength;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isNotEmpty(ipv4)) {
            sb.append(ipv4);
            sb.append('/');
            sb.append(ipMask);
        } else if(StringUtils.isNotEmpty(ipv6)) {
            sb.append(ipv6);
            sb.append('/');
            sb.append(prefixLength);
        }
        return sb.toString();
    }

}
