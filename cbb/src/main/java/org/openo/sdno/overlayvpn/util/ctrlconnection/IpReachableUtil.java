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

package org.openo.sdno.overlayvpn.util.ctrlconnection;

import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.openo.sdno.overlayvpn.consts.ValidationConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of IpAddress Reachable Detect.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public class IpReachableUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpReachableUtil.class);

    /**
     * LAN IP Range(A)
     */
    public static final String PRIVATE_IP_A = "10.0.0.1~10.255.255.254";

    /**
     * LAN IP Range(B)
     */
    public static final String PRIVATE_IP_B = "172.16.0.1~172.31.255.254";

    /**
     * LAN IP Range(C)
     */
    public static final String PRIVATE_IP_C = "192.168.0.1~192.168.255.254";

    private IpReachableUtil() {
    }

    /**
     * ping test of ipAddress.<br>
     * 
     * @param ip ipAddress need to test
     * @return true if this ipAddress is reachable, false otherwise
     * @since SDNO 0.5
     */
    public static boolean ping(String ip) {
        String normalIp = Normalizer.normalize(ip, Form.NFKC);

        // validate ipAddress Format
        if(!normalIp.matches(ValidationConsts.IP_REGEX)) {
            return false;
        }

        boolean result = true;
        try {
            // ping Command in Linux
            String pingCmd = "ping -c 1 -w 2 " + normalIp;
            Process pingProcess = Runtime.getRuntime().exec(pingCmd);
            if(0 != pingProcess.waitFor()) {
                result = false;
            }
        } catch(InterruptedException e) {
            result = false;
            LOGGER.error("Interrupted Exception", e);
        } catch(IOException e) {
            result = false;
            LOGGER.error("IO Exception", e);
        }

        return result;
    }

    /**
     * Check whether ipAddress is Private.<br>
     * 
     * @param ip ipAddress need to check
     * @return true if ipAddress is private,false otherwise
     * @since SDNO 0.5
     */
    public static boolean isPrivateIp(String ip) {
        return isInPrivateIp(ip, PRIVATE_IP_A) || isInPrivateIp(ip, PRIVATE_IP_B) || isInPrivateIp(ip, PRIVATE_IP_C);
    }

    private static boolean isInPrivateIp(String ip, String ipRange) {
        Long ipLong = ipToLong(ip);

        String[] ipAry = ipRange.split("~");
        if(ipAry.length != 2) {
            return false;
        }
        long minIP = ipToLong(ipAry[0]);
        long maxIP = ipToLong(ipAry[1]);
        return ipLong >= minIP && ipLong <= maxIP;
    }

    private static long ipToLong(String strip) {
        long[] ip = new long[4];
        int position1 = strip.indexOf('.');
        int position2 = strip.indexOf(".", position1 + 1);
        int position3 = strip.indexOf(".", position2 + 1);
        ip[0] = Long.parseLong(strip.substring(0, position1));
        ip[1] = Long.parseLong(strip.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strip.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strip.substring(position3 + 1));
        return ip[0] * 256L * 256L * 256L + ip[1] * 256L * 256L + ip[2] * 256L + ip[3];
    }

}
