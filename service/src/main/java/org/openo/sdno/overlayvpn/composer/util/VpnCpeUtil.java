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

package org.openo.sdno.overlayvpn.composer.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class to get the port name.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 23, 2017
 */
public class VpnCpeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnCpeUtil.class);

    private static NetworkElementInvDao neInvDao = new NetworkElementInvDao();

    private VpnCpeUtil() {
        super();
    }

    /**
     * Get port name.<br>
     * 
     * @param template The template
     * @param neId The neId
     * @param templateKey The template key
     * @return The port name
     * @throws ServiceException when query port name failed
     * @since SDNO 0.5
     */
    public static String getCpeInterface(Template template, String neId, String templateKey) throws ServiceException {
        LOGGER.info("Get cpe interface , neId = " + neId + ",templateKey = " + templateKey);
        if(StringUtils.isEmpty(templateKey) || template == null) {
            return null;
        }
        String interfaceName = null;
        if(templateKey.startsWith(ParamConsts.CLOUDCPEINFO)) {
            String key = StringUtils.substring(templateKey, StringUtils.lastIndexOf(templateKey, '.') + 1);
            interfaceName = template.getCloudCpeInfo().get(key);
        } else if(templateKey.startsWith(ParamConsts.LOCALCPEINFO)) {
            NetworkElementMO managedElement = neInvDao.query(neId);
            if(managedElement != null) {
                String productName = managedElement.getProductName();
                if(StringUtils.isEmpty(productName)) {
                    LOGGER.error("Device type is null,neId=" + neId);
                }
                String key = StringUtils.substring(templateKey, StringUtils.lastIndexOf(templateKey, '.') + 1);
                Map<String, String> interfaceMap = template.getLocalCpeInfo().get(productName);
                if(interfaceMap == null) {
                    LOGGER.error("Device type is not supported in this model,type=" + productName);
                }
                interfaceName = interfaceMap != null ? interfaceMap.get(key) : null;
            }
        } else {
            interfaceName = templateKey;
        }
        LOGGER.info("Get cpe interface, templateKey = " + templateKey + ",interfaceName = " + interfaceName);
        if(StringUtils.isEmpty(interfaceName)) {
            LOGGER.error("Vpncpe.getInterface.error,get cpe interface failed,interfaceName is empty,neId = " + neId
                    + ",templateKey = " + templateKey);
            throw new InnerErrorServiceException(
                    "Vpncpe.getInterface.error,get cpe interface failed,interfaceName is empty,neId = " + neId
                            + ",templateKey = " + templateKey);
        }
        return interfaceName;
    }
}
