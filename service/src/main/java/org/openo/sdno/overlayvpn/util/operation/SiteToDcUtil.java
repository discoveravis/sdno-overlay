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

package org.openo.sdno.overlayvpn.util.operation;

import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDc;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Utility class of SiteToDc.<br>
 * 
 * @author
 * @version SDNO 0.5 June 1, 2016
 */
public class SiteToDcUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SiteToDcUtil.class);

    private SiteToDcUtil() {
    }

    /**
     * Build new SiteToDc based on old SiteToDc and new data.<br>
     * 
     * @param quriedSiteToDC old SiteToDc
     * @param inputJsonStr new data in JSON format
     * @return new SiteToDc Object
     * @throws ServiceException when build OverlayVpn data failed
     * @since SDNO 0.5
     */
    public static ResultRsp<SiteToDc> buildNewVpnByOldAndInputData(String inputJsonStr) throws ServiceException {
        Map<String, String> updateDataMap = OperationUtil.getUpdateDataMap(inputJsonStr);

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
}
