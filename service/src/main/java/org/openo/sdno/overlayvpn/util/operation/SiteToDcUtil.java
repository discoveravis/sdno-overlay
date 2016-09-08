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

package org.openo.sdno.overlayvpn.util.operation;

import java.util.Arrays;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDc;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.openo.sdno.util.ip.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Util class of SiteToDc.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public class SiteToDcUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteToDcUtil.class);

    private SiteToDcUtil() {
    }

    /**
     * Build new SiteToDc based on old SiteToDc and new data.<br/>
     * 
     * @param quriedSiteToDC old SiteToDc
     * @param inputJsonStr new data in JSON format
     * @return new SiteToDc Object
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public static ResultRsp<SiteToDc> buildNewVpnByOldAndInputData(String inputJsonStr) throws ServiceException {
        Map<String, String> updateDataMap = null;
        try {
            updateDataMap = JsonUtil.fromJson(inputJsonStr, new TypeReference<Map<String, String>>() {});
        } catch(IllegalArgumentException e) {
            LOGGER.error("update data formate error, inputJsonStr = " + inputJsonStr, e);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("SiteToDc", "Update data");
            return null;
        }

        if(null == updateDataMap) {
            LOGGER.error("update data formate error, updateDataMap is NULL");
            ThrowOverlayVpnExcpt.throwParmaterInvalid("SiteToDc", "Update data");
        }

        SiteToDc siteToDc = new SiteToDc("");

        String name = updateDataMap.get("name");
        if(StringUtils.hasLength(name)) {
            if(name.length() > CommConst.NAME_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("name", name);
            }
            siteToDc.setName(name);
        }

        String description = updateDataMap.get("description");
        if(StringUtils.hasLength(description)) {
            if(description.length() > CommConst.DESCRIPTION_MAX_LENGTH) {
                ThrowOverlayVpnExcpt.throwParmaterInvalid("description", description);
            }
            siteToDc.setDescription(description);
        }

        return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS, siteToDc);
    }

    /**
     * <br/>
     * 
     * @param cidr
     * @return
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public static String[] getGatewayIpAndMaskFromCidr(String cidr) throws ServiceException {

        // Validate CIDR
        if(!IpUtils.isValidCidr(cidr)) {
            LOGGER.error("Invalid CIDR");
            ThrowOverlayVpnExcpt.throwParmaterInvalid("CIDR", cidr);
        }

        // Split IP Address and VLAN information
        String[] ipandprefix = cidr.split("/");

        if((null == ipandprefix[0]) || (2 != ipandprefix.length)) {
            LOGGER.error("CIDR do not match ***/*** format");
            ThrowOverlayVpnExcpt.throwParmaterInvalid("CIDRs", cidr);
        }
        // Validate IP
        if(!IpUtils.isValidAddress(ipandprefix[0])) {
            LOGGER.error("IP format err");
            ThrowOverlayVpnExcpt.throwParmaterInvalid("IP Address", cidr);
        }

        String[] ipandmask = Arrays.copyOf(ipandprefix, ipandprefix[1].length());

        int prefix = Integer.parseInt(ipandprefix[1]);
        if(prefix > 32 || prefix < 0) {
            LOGGER.error("Prefix format err");
            ThrowOverlayVpnExcpt.throwParmaterInvalid("CIDR Prefix", String.valueOf(prefix));
        }
        ipandmask[1] = IpUtils.prefixToMask(prefix);

        return ipandmask;
    }

}
