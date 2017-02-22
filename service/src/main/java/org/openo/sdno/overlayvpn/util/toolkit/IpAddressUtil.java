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

package org.openo.sdno.overlayvpn.util.toolkit;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class of IP address.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 3, 2017
 */
public class IpAddressUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpAddressUtil.class);

    private IpAddressUtil() {

    }

    /**
     * Get IP from cidr.<br>
     * 
     * @param cidr The cidr as string
     * @return The ip as string
     * @since SDNO 0.5
     */
    public static String getIpFromCidr(String cidr) {
        if(isValidIpAddr(cidr)) {
            return cidr;
        }
        if(!isValidCidr(cidr)) {
            return null;
        }
        return StringUtils.split(cidr, '/')[0];
    }

    /**
     * Get mask from cidr.<br>
     * 
     * @param cidr The cidr as string
     * @return The mask as string
     * @since SDNO 0.5
     */
    public static String getMaskFromCidr(String cidr) {
        if(!isValidCidr(cidr)) {
            return null;
        }
        return prefixToMask(Integer.valueOf(StringUtils.split(cidr, '/')[1]));
    }

    /**
     * Convert mask of IP to prefix.<br>
     * 
     * @param mask The IP mask as string
     * @return The prefix as integer
     * @since SDNO 0.5
     */
    public static int maskToPrefix(String mask) {
        if(!isValidIpAddr(mask)) {
            return 0;
        }
        if("0.0.0.0".equals(mask)) {
            return 0;
        }
        if("255.255.255.255".equals(mask)) {
            return 32;
        }
        long ipValue = ipToLong(mask);
        for(int i = 0; i < 32; i++) {
            if(((ipValue >> (31 - i)) & 1) == 0) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Validate the IP address.<br>
     * 
     * @param ipAddr The ip address as string
     * @return true when the ip address is legal,otherwise is false
     * @since SDNO 0.5
     */
    public static boolean isValidIpAddr(String ipAddr) {
        String regex =
                "^(25[0-5]|2[0-4]\\d?|1\\d{2}|[1-9]\\d?|0)\\.(25[0-5]|2[0-4]\\d?|1\\d{2}|[1-9]\\d?|0)\\.(25[0-5]|2[0-4]\\d?|1\\d{2}|[1-9]\\d?|0)\\.(25[0-5]|2[0-4]\\d?|1\\d{2}|[1-9]\\d?|0)$";
        if(StringUtils.isEmpty(ipAddr)) {
            LOGGER.warn("Invalid ip address,is empty.");
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Normalizer.normalize(ipAddr, Form.NFKC));

        boolean match = matcher.matches();
        if(!match) {
            LOGGER.warn("Invalid ip address :" + ipAddr);
        }
        return match;
    }

    /**
     * Validate the CIDR.<br>
     * 
     * @param cidr The CIDR as string
     * @return true when the CIDR is legal,otherwise is false
     * @since SDNO 0.5
     */
    private static boolean isValidCidr(String cidr) {
        if(StringUtils.isEmpty(cidr)) {
            return false;
        }
        String[] split = StringUtils.split(cidr, '/');
        if(split == null || split.length != 2) {
            return false;
        }
        String ip = split[0];
        String maskStr = split[1];
        if(!isValidIpAddr(ip)) {
            return false;
        }
        if(!NumberUtils.isDigits(maskStr) || maskStr.length() > 3) {
            return false;
        }

        int mask = Integer.valueOf(maskStr);
        if(mask < 0 || mask > 32) {
            return false;
        }
        return true;
    }

    private static String prefixToMask(int prefix) {
        if(prefix < 0 || prefix > 32) {
            return null;
        } else if(prefix == 0) {
            return "0.0.0.0";
        } else if(prefix == 32) {
            return "255.255.255.255";
        } else {
            char[] chars = new char[32];
            Arrays.fill(chars, '0');
            for(int i = 0; i < prefix; i++) {
                chars[i] = '1';
            }
            long ipValue = Long.parseLong(String.valueOf(chars), 2);
            return longToIp(ipValue);
        }
    }

    private static String longToIp(long ipAddr) {
        if(ipAddr < 0 || ipAddr > 4294967295L) {
            return null;
        }
        StringBuffer sb = new StringBuffer();

        sb.append(String.valueOf(ipAddr >>> 24));
        sb.append('.');

        sb.append(String.valueOf((ipAddr & 0x00FFFFFF) >>> 16));
        sb.append('.');

        sb.append(String.valueOf((ipAddr & 0x0000FFFF) >>> 8));
        sb.append('.');

        sb.append(String.valueOf(ipAddr & 0x000000FF));
        return sb.toString();
    }

    private static long ipToLong(String ipAddr) {
        if(!isValidIpAddr(ipAddr)) {
            return 0;
        }
        String ipAddress = ipAddr.trim();
        String[] ipArray = StringUtils.split(ipAddress, '.');
        long[] ip = new long[4];

        ip[0] = Long.parseLong(ipArray[0]);
        ip[1] = Long.parseLong(ipArray[1]);
        ip[2] = Long.parseLong(ipArray[2]);
        ip[3] = Long.parseLong(ipArray[3]);

        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }
}
