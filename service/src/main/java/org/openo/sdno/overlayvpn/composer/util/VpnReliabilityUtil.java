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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.exception.ParameterServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.LogicalTernminationPointInvDao;
import org.openo.sdno.overlayvpn.brs.model.LogicalTernminationPointMO;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.composer.model.NeConnection;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.frame.dao.model.ActionState;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetIpsecConnection;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetNqa;
import org.openo.sdno.overlayvpn.servicemodel.template.Sap;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateConnection;
import org.openo.sdno.overlayvpn.site2dc.sbi.impl.NqaSbiService;
import org.openo.sdno.overlayvpn.util.ModelUtils;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.overlayvpn.util.toolkit.IpAddressUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class to deal with reliability model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 11, 2017
 */
public class VpnReliabilityUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnReliabilityUtil.class);

    private static BaseDao<NetNqa> nqaDao = new BaseDao<NetNqa>();

    private static BaseDao<NetIpsecConnection> ipsecDao = new BaseDao<NetIpsecConnection>();

    private static LogicalTernminationPointInvDao portInvDao = new LogicalTernminationPointInvDao();

    private static NqaSbiService nqaSbiService = new NqaSbiService();

    private VpnReliabilityUtil() {
        super();
    }

    /**
     * Create nqa.<br>
     * 
     * @param netConnection The ipsec connection
     * @param neConnection The ne connection
     * @param connectionTemplate The connection template
     * @return The nqa uuid
     * @throws ServiceException when queried nqa operationStatus is exception or call sbi interface
     *             to create nqa failed
     * @since SDNO 0.5
     */
    public static String createNqa(NetIpsecConnection netConnection, NeConnection neConnection,
            TemplateConnection connectionTemplate) throws ServiceException {
        NetNqa nqa = generateNqa(netConnection, neConnection, connectionTemplate);
        if(nqa == null) {
            return null;
        }
        NetNqa cond = new NetNqa();
        cond.setNeId(nqa.getNeId());
        cond.setDstIp(nqa.getDstIp());
        List<NetNqa> dbDatas = nqaDao.query(cond);
        if(CollectionUtils.isNotEmpty(dbDatas)) {
            LOGGER.warn("Nqa has been exist,data={}", JsonUtils.toJson(dbDatas));
            NetNqa dbData = dbDatas.get(0);
            ActionState state = ActionState.fromState(dbData.getActionState());
            if(state.isException()) {
                LOGGER.warn("Nqa has been exist , but exception,data={}", JsonUtils.toJson(dbDatas));
                throw new InnerErrorServiceException(
                        "Nqa has been exist , but exception,data=" + JsonUtils.toJson(dbDatas));
            }
            return dbData.getId();
        }
        nqaSbiService.create(Arrays.asList(nqa));
        return nqa.getId();
    }

    /**
     * Delete nqa through connection uuid.<br>
     * 
     * @param vpnId The connection uuid
     * @throws ServiceException when query nqa failed or call sbi interface to delete nqa failed
     * @since SDNO 0.5
     */
    public static void deleteNqa(String vpnId) throws ServiceException {
        if(StringUtils.isEmpty(vpnId)) {
            return;
        }
        NetNqa cond = new NetNqa();
        cond.setVpnConnectionId(vpnId);
        List<NetNqa> dbNqas = nqaDao.query(cond);
        LOGGER.info("Query nqa success,the result is :" + JsonUtils.toJson(dbNqas));
        removeNqa(dbNqas);
    }

    /**
     * Batch delete nqa through connection uuid list.<br>
     * 
     * @param netConnectionIds The connection uuid list
     * @throws ServiceException when when query nqa failed or call sbi interface to delete nqa
     *             failed
     * @since SDNO 0.5
     */
    public static void deleteNqa(List<String> netConnectionIds) throws ServiceException {
        if(CollectionUtils.isEmpty(netConnectionIds)) {
            return;
        }
        List<NetNqa> dbNqas = new ArrayList<>();
        for(String connectionId : netConnectionIds) {
            NetNqa cond = new NetNqa();
            cond.setConnectionId(connectionId);
            dbNqas.addAll(nqaDao.query(cond));
        }
        removeNqa(dbNqas);
    }

    private static NetNqa generateNqa(NetIpsecConnection netConnection, NeConnection neConnection,
            TemplateConnection connectionTemplate) throws ServiceException {
        List<Sap> saps = connectionTemplate.getSaps();
        Sap srcSap = saps.get(0);
        Sap dstSap = saps.get(1);
        if(StringUtils.isEmpty(srcSap.getNqa()) && StringUtils.isEmpty(dstSap.getNqa())) {
            LOGGER.info("Nqa is not configured in template,connectionName={}", connectionTemplate.getName());
            return null;
        }
        NbiNetConnection connection = netConnection;
        String nqaTemplate = srcSap.getNqa();
        Template template = neConnection.getSrcTemplate();
        if(StringUtils.isEmpty(srcSap.getNqa())) {
            connection = netConnection.toReverse();
            nqaTemplate = dstSap.getNqa();
            template = neConnection.getDestTemplate();
        }

        Map<String, String> nqaParams = VpnTemplateUtil.parseTemplateObj(nqaTemplate);
        String dstIp = getNqaIp(connection, template, nqaParams.get(ParamConsts.KEY_DSTIP));
        if(!IpAddressUtil.isValidIpAddr(dstIp)) {
            LOGGER.error("Build nqa failed,dstIp is invalid,dstIp=" + dstIp);
            throw new ParameterServiceException("Build nqa failed,dstIp is invalid,dstIp=" + dstIp);
        }

        String srcIp = getNqaIp(connection, template, nqaParams.get(ParamConsts.KEY_SRCIP));
        String srcPort = getNqaPort(connection, template, nqaParams.get(ParamConsts.KEY_SRCPORT));
        nqaParams.remove(ParamConsts.KEY_SRCIP);
        nqaParams.remove(ParamConsts.KEY_DSTIP);
        nqaParams.remove(ParamConsts.KEY_SRCPORT);
        NetNqa nqa = new NetNqa(connection, srcIp, dstIp, srcPort);
        ModelUtils.setValueMap(nqa, nqaParams);
        return nqa;
    }

    private static void removeNqa(List<NetNqa> dbNqas) throws ServiceException {
        LOGGER.info("Start remove nqa.");
        if(CollectionUtils.isEmpty(dbNqas)) {
            LOGGER.info("The nqa is empty.");
            return;
        }
        List<String> nqaIds = ModelServiceUtil.getUuidList(dbNqas);
        List<NetIpsecConnection> ipsecList = new ArrayList<>();
        for(String nqaId : nqaIds) {
            NetIpsecConnection cond = new NetIpsecConnection();
            cond.setNqa(nqaId);
            ipsecList.addAll(ipsecDao.query(cond));
        }
        LOGGER.info("Query ipsec success,the result is :" + JsonUtils.toJson(ipsecList));

        boolean inUsed = false;
        for(NetNqa nqa : dbNqas) {
            inUsed = false;
            for(NetIpsecConnection ipsec : ipsecList) {
                if(nqa.getId().equals(ipsec.getNqa())) {
                    inUsed = true;
                    break;
                }
            }
            if(!inUsed) {
                LOGGER.info("Call sbi interface to delete nqa.");
                nqaSbiService.undeploy(Arrays.asList(nqa.getId()));
                nqaSbiService.delete(nqa.getId());
            }
        }
    }

    private static String getNqaIp(NbiNetConnection connection, Template template, String ipTemplate)
            throws ServiceException {
        if(StringUtils.isEmpty(ipTemplate)) {
            return null;
        }
        String nqaIp = null;
        String[] splits = StringUtils.split(ipTemplate, ':');
        if(validateNqaIp(ipTemplate, ParamConsts.REGX_IP_IP)) {
            nqaIp = splits[1];
        } else if(validateNqaIp(ipTemplate, ParamConsts.REGX_IP_INTERFACE)) {
            String neId =
                    ParamConsts.TYPE_REMOTE.equals(splits[1]) ? connection.getDestNeId() : connection.getSrcNeId();
            String portName = VpnCpeUtil.getCpeInterface(template, neId, splits[2]);
            nqaIp = queryPortIp(neId, portName);
        }
        return nqaIp;
    }

    private static String getNqaPort(NbiNetConnection connection, Template template, String portTemplate)
            throws ServiceException {
        if(StringUtils.isEmpty(portTemplate)) {
            return null;
        }
        return VpnCpeUtil.getCpeInterface(template, connection.getSrcNeId(), portTemplate);
    }

    private static boolean validateNqaIp(String ipTemplate, String regx) {
        if(StringUtils.isEmpty(ipTemplate)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(ipTemplate);
        return matcher.matches();
    }

    private static String queryPortIp(String neId, String portName) throws ServiceException {
        Map<String, String> condition = new HashMap<String, String>();
        condition.put(ParamConsts.ME_PARAM, neId);
        List<LogicalTernminationPointMO> points = portInvDao.query(condition);
        if(CollectionUtils.isEmpty(points)) {
            return null;
        }
        for(LogicalTernminationPointMO point : points) {
            if(portName.equals(point.getName())) {
                return point.getIpAddress();
            }
        }
        return null;
    }
}
