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

package org.openo.sdno.overlayvpn.util.check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.PortAndVlanUsed;
import org.openo.sdno.overlayvpn.model.common.enums.EndpointType;

/**
 * Port and VLAN Data Verify Class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 1, 2016
 */
public class CheckPortAndVlanUtil {

    private static final String POINTID_PARAM = "pointId";

    private static final String TYPE_PARAM = "type";

    private CheckPortAndVlanUtil() {
    }

    /**
     * Check weather port and VLAN data is invalid.<br>
     * 
     * @param portUuids list of Port id
     * @param portToVlanMap port to VLAN Map
     * @throws ServiceException ServiceException throws when data is invalid
     * @since SDNO 0.5
     */
    public static void check(List<String> portUuids, Map<String, String> portToVlanMap) throws ServiceException {
        // TODO verify VLAN
        Map<String, List<String>> filter = new HashMap<String, List<String>>();
        filter.put(POINTID_PARAM, portUuids);
        filter.put(TYPE_PARAM, Arrays.asList(EndpointType.PORT_VLAN.getName()));

        List<PortAndVlanUsed> portVlanList = new InventoryDao<PortAndVlanUsed>()
                .batchQuery(PortAndVlanUsed.class, JsonUtil.toJson(filter)).getData();

        List<String> vlanList = new ArrayList<String>();
        for(Map.Entry<String, String> iter : portToVlanMap.entrySet()) {
            for(PortAndVlanUsed vlanUsed : portVlanList) {
                if(iter.getKey().equals(vlanUsed.getPortId())) {
                    // add VLAN to list
                    vlanList.add(vlanUsed.getVlan());
                }
            }

            if(CollectionUtils.isNotEmpty(vlanList)) {
                // error message
                checkVlan(iter.getValue(), vlanList);
            }
        }
    }

    /**
     * Check weather VLAN data is valid.<br>
     * 
     * @param targetVlans target VLAN's
     * @param vlansLst list of VLAN
     * @return true if VLAN in the range, false otherwise
     * @since SDNO 0.5
     */
    private static boolean checkVlan(Object targetVlans, List<String> vlansLst) {
        if(CollectionUtils.isEmpty(vlansLst) || (null == targetVlans) || (!(targetVlans instanceof String))) {
            return false;
        }

        // target VLAN range ["1-2","3"]
        String[] targetVlanRanges = String.valueOf(targetVlans).split(",");
        for(String vlans : vlansLst) {
            // destination VLAN range ["1-2","3"]
            List<String> srcVlanRanges = Arrays.asList(vlans.split(","));
            Collections.sort(srcVlanRanges, new VlanRangeComparator());

            for(String targetVlanRange : targetVlanRanges) {
                if(Collections.binarySearch(srcVlanRanges, targetVlanRange, new VlanRangeComparator()) >= 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
