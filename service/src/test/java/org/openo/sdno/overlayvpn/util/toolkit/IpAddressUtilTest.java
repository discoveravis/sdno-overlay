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

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.servicemodel.base.IP;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;

public class IpAddressUtilTest {

    @Test
    public void testJsonToIPNull() throws ServiceException {
        List<IP> ipList = IpAddressUtil.jsonToIP(null);
        assertTrue(CollectionUtils.isEmpty(ipList));
    }

    @Test
    public void testJsonToIP() throws ServiceException {
        IP ip = new IP();
        ip.setIpv4("0.0.0.0");
        ip.setIpMask("24");
        List<IP> ipList = IpAddressUtil.jsonToIP(JsonUtils.toJson(Arrays.asList(ip)));
        assertTrue(CollectionUtils.isNotEmpty(ipList));
    }

}
