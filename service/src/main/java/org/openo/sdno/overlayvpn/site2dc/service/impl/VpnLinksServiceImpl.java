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

package org.openo.sdno.overlayvpn.site2dc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.LogicalTernminationPointInvDao;
import org.openo.sdno.overlayvpn.brs.model.LogicalTernminationPointMO;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.composer.util.VpnRelationUtil;
import org.openo.sdno.overlayvpn.frame.dao.MssInvProxy;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryParam;
import org.openo.sdno.overlayvpn.frame.dao.model.BatchQueryResult;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiConnectionRelation;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.servicemodel.base.IP;
import org.openo.sdno.overlayvpn.site2dc.service.inf.VpnLinksService;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The implements of VpnLinksService interface.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 13, 2017
 */
public class VpnLinksServiceImpl implements VpnLinksService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnLinksServiceImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public <T> BatchQueryResult<T> query(HttpServletRequest httpContext, HttpServletResponse httpContextRsp,
            T condition, BatchQueryParam batchQueryParam) throws ServiceException {
        MssInvProxy mss = new MssInvProxy(condition.getClass());
        BatchQueryResult<T> result = mss.query(condition, batchQueryParam);
        List<NbiNetConnection> connectionList = (List<NbiNetConnection>)result.getObjects();
        Map<String, List<NbiConnectionRelation>> parentConnections = getParentConnections(connectionList);
        for(NbiNetConnection netConnection : connectionList) {
            initSrcPortIp(netConnection);
            initDestPortIp(netConnection);
            netConnection.setParentConnections(parentConnections.get(netConnection.getId()));
        }
        return result;
    }

    private Map<String, List<NbiConnectionRelation>> getParentConnections(List<NbiNetConnection> netConnections)
            throws ServiceException {
        Map<String, List<NbiConnectionRelation>> parentConnections = new HashMap<String, List<NbiConnectionRelation>>();
        List<NbiConnectionRelation> connectionRelations = VpnRelationUtil.queryConnectionRelations(netConnections);
        if(CollectionUtils.isEmpty(connectionRelations)) {
            LOGGER.warn("connectionRelations is null.");
            return parentConnections;
        }
        for(NbiConnectionRelation connectionRelation : connectionRelations) {
            String netConnectionId = connectionRelation.getNetConnectionId();
            if(parentConnections.get(netConnectionId) == null) {
                parentConnections.put(netConnectionId, new ArrayList<NbiConnectionRelation>());
            }
            parentConnections.get(netConnectionId).add(connectionRelation);
        }
        return parentConnections;
    }

    private void initSrcPortIp(NbiNetConnection netConnection) throws ServiceException {
        Map<String, String> values = new HashMap<String, String>();
        values.put("srcPortIp", getPortIp(netConnection.getSrcNeId(), netConnection.getSrcPortName(),
                JavaEntityUtil.getFieldValueByName("srcPortIp", netConnection)));
        ModelUtils.setValueMap(netConnection, values);
    }

    private void initDestPortIp(NbiNetConnection netConnection) throws ServiceException {
        Map<String, String> values = new HashMap<String, String>();
        values.put("destPortIp", getPortIp(netConnection.getDestNeId(), netConnection.getDestPortName(),
                JavaEntityUtil.getFieldValueByName("destPortIp", netConnection)));
        ModelUtils.setValueMap(netConnection, values);
    }

    private String getPortIp(String neId, String portName, Object portIp) throws ServiceException {
        if(StringUtils.isEmpty(portName)) {
            LOGGER.warn("Connection portName is null,maybe is Site2Dc,portIp=" + portIp + ",neId=" + neId);
            return convertIp(String.valueOf(portIp));
        }

        LogicalTernminationPointInvDao brsPointDao = new LogicalTernminationPointInvDao();
        Map<String, String> condition = new HashMap<String, String>();
        condition.put(ParamConsts.ME_PARAM, neId);
        condition.put(ParamConsts.NAME_PARAM, portName);
        List<LogicalTernminationPointMO> portList = brsPointDao.query(condition);

        if(CollectionUtils.isNotEmpty(portList)) {
            return portList.get(0).getIpAddress();
        }
        return null;
    }

    private String convertIp(String portIp) throws ServiceException {
        String result = null;
        if(!"null".equals(portIp)) {
            IP ip = JsonUtils.fromJson(portIp, IP.class);
            if(ip != null && ip.getIpv4() != null) {
                result = ip.getIpv4();
            }
        }
        return result;
    }
}
