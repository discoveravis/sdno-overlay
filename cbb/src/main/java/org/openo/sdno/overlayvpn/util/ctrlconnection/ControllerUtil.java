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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.ControllerDao;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of Controller Reachable Detect.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 2, 2016
 */
public class ControllerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerUtil.class);

    private ControllerUtil() {
    }

    /**
     * Test whether Controllers of all network Elements are reachable.<br>
     * 
     * @param neUuids List of network id
     * @return success if controllers are reachable,fail otherwise
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public static ResultRsp<Map<String, ControllerMO>> testCtrlConnection(List<String> neUuids) throws ServiceException {
        ResultRsp<Map<String, ControllerMO>> retResult = queryControllerMoMapByNeUuids(neUuids);
        if(!retResult.isSuccess() || MapUtils.isEmpty(retResult.getData())) {
            return retResult;
        }

        return retResult;
    }

    /**
     * Get network element to Controller Map.<br>
     * 
     * @param neUuidList list of id
     * @return network element to Controller Map.
     * @since SDNO 0.5
     */
    public static ResultRsp<Map<String, ControllerMO>> queryControllerMoMapByNeUuids(List<String> neUuidList) {
        if(CollectionUtils.isEmpty(neUuidList)) {
            return new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);
        }

        Map<String, ControllerMO> map = new HashMap<String, ControllerMO>();
        ControllerDao invDao = new ControllerDao();

        try {
            for(String neUuid : neUuidList) {
                // query controller by network element id
                List<ControllerMO> controllerList = invDao.getControllerByNeId(neUuid);

                if(CollectionUtils.isEmpty(controllerList)) {
                    LOGGER.info("do not find ControllerMO by ne, wite uuid = " + neUuid);
                    map.put(neUuid, null);
                } else {
                    map.put(neUuid, controllerList.get(0));
                }
            }

            return new ResultRsp<Map<String, ControllerMO>>(ErrorCode.OVERLAYVPN_SUCCESS, map);
        } catch(ServiceException e) {
            LOGGER.error("database exception: ", e);
            return new ResultRsp<Map<String, ControllerMO>>(ErrorCode.OVERLAYVPN_DATABASE_QUERY_FAILED);
        }
    }
}
