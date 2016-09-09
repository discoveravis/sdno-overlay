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

package org.openo.sdno.overlayvpn.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.model.common.enums.ModifyMaskType;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.EndpointGroup;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.springframework.util.StringUtils;

/**
 * Class Filter Data Operation.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public class FilterDataUtil {

    private FilterDataUtil() {
    }

    /**
     * Build Filter Data by Object.<br>
     * 
     * @param name attribute name
     * @param value attribute value
     * @return Filter data in JSON format
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public static String getFilterData(String name, Object value) {
        if(!StringUtils.hasLength(name) || (null == value)) {
            return "";
        }

        if((value instanceof String) && (!StringUtils.hasLength((String)value))) {
            return "";
        }

        if((value instanceof List<?>) && (CollectionUtils.isEmpty((List)value))) {
            return "";
        }

        Map<String, Object> filterMap = new HashMap<String, Object>();

        filterMap.put(name, value);

        return JsonUtil.toJson(filterMap);
    }

    /**
     * Build Filter Data.<br>
     * 
     * @param overlayVpn OverlayVpn Object
     * @param tenantId tenant id
     * @param modifyMaskType mask type
     * @return Filter data in JSON format
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public static String getLocalNeFilterFromOverlayVpn(OverlayVpn overlayVpn, String tenantId,
            ModifyMaskType modifyMaskType) {
        List<String> filterConnectionIds =
                new ArrayList<String>(CollectionUtils.collect(overlayVpn.getVpnConnections(), new Transformer() {

                    @Override
                    public Object transform(Object arg0) {
                        return ((Connection)arg0).getUuid();
                    }
                }));

        List<String> filterDeviceIds = new ArrayList<String>();

        for(Connection connection : overlayVpn.getVpnConnections()) {
            for(EndpointGroup epg : connection.getEndpointGroups()) {
                if(modifyMaskType.getName().equals(epg.getModifyMask())) {
                    filterDeviceIds.add(epg.getDeviceId());
                }
            }
        }

        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("tenantId", Arrays.asList(tenantId));
        filterMap.put("connectionServiceId", filterConnectionIds);
        if(CollectionUtils.isNotEmpty(filterDeviceIds)) {
            filterMap.put("neId", filterDeviceIds);
        }

        return JsonUtil.toJson(filterMap);
    }

    /**
     * Build Filter Data.<br>
     * 
     * @param overlayVpn OverlayVpn Object
     * @param tenantId tenant id
     * @param modifyMaskType mask type
     * @return Filter data in JSON format
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public static String getPeerNeFilterFromOverlayVpn(OverlayVpn overlayVpn, String tenantId,
            ModifyMaskType modifyMaskType) {
        List<String> filterDeviceIds = new ArrayList<String>();

        for(Connection connection : overlayVpn.getVpnConnections()) {
            for(EndpointGroup epg : connection.getEndpointGroups()) {
                if(modifyMaskType.getName().equals(epg.getModifyMask())) {
                    filterDeviceIds.add(epg.getDeviceId());
                }
            }
        }

        if(CollectionUtils.isEmpty(filterDeviceIds)) {
            return null;
        }

        List<String> filterConnectionIds =
                new ArrayList<String>(CollectionUtils.collect(overlayVpn.getVpnConnections(), new Transformer() {

                    @Override
                    public Object transform(Object arg0) {
                        return ((Connection)arg0).getUuid();
                    }
                }));

        Map<String, Object> filterMap = new HashMap<String, Object>();
        filterMap.put("tenantId", Arrays.asList(tenantId));
        filterMap.put("connectionServiceId", filterConnectionIds);
        filterMap.put("peerNeId", filterDeviceIds);

        return JsonUtil.toJson(filterMap);
    }

}
