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

package org.openo.sdno.overlayvpn.site2dc.sbi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.servicemodel.base.IP;
import org.openo.sdno.overlayvpn.servicemodel.enums.ActionType;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetIpsecConnection;
import org.openo.sdno.overlayvpn.site2dc.sbi.inf.SbiService;
import org.openo.sdno.overlayvpn.util.toolkit.IpAddressUtil;
import org.springframework.stereotype.Service;

/**
 * The SBI service of ipsec.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 17, 2017
 */
@Service("ipsecSbiService")
public class IpsecSbiService extends AbstractSbiService<NetIpsecConnection> implements SbiService<NetIpsecConnection> {

    private static final String SBI_URL = "/openoapi/sdnoipsec/v1/ipsec/ipsec-connections";

    @Override
    public void create(List<NetIpsecConnection> connectionList) throws ServiceException {
        dao.insert(connectionList);
        doCreate(SBI_URL, NetIpsecConnection.class, connectionList);
    }

    @Override
    public void delete(String uuid) throws ServiceException {
        doDelete(SBI_URL, uuid, NetIpsecConnection.class);
        dao.delete(uuid, NetIpsecConnection.class);
    }

    @Override
    public void update(List<NetIpsecConnection> mos) throws ServiceException {
        List<NetIpsecConnection> updates = processIpsecUpdate(mos);
        dao.update(updates);
        doUpdate(SBI_URL, NetIpsecConnection.class, updates);
    }

    @Override
    public void deploy(List<String> uuidList) throws ServiceException {
        doDeploy(SBI_URL, NetIpsecConnection.class, uuidList);
    }

    @Override
    public void undeploy(List<String> uuidList) throws ServiceException {
        doUndeploy(SBI_URL, NetIpsecConnection.class, uuidList);
    }

    @Override
    public List<NetIpsecConnection> queryRemote(Set<String> uuids) throws ServiceException {
        return doQuery(SBI_URL, NetIpsecConnection.class, uuids);
    }

    /**
     * Build the ipsec list that need to be updated.<br>
     * 
     * @param ipsecConnections The all ipsec connection list
     * @return The ipsec list that need to be updated
     * @throws ServiceException When build ipsec failed
     * @since SDNO 0.5
     */
    private List<NetIpsecConnection> processIpsecUpdate(List<NetIpsecConnection> ipsecConnections)
            throws ServiceException {
        List<NetIpsecConnection> updates = new ArrayList<>();
        if(CollectionUtils.isEmpty(ipsecConnections)) {
            return updates;
        }
        for(NetIpsecConnection ipsecConnection : ipsecConnections) {
            NetIpsecConnection update = processIpsecUpdate(ipsecConnection);
            if(update != null) {
                updates.add(update);
            }
        }
        return updates;
    }

    private static NetIpsecConnection processIpsecUpdate(NetIpsecConnection ipsecConnection) throws ServiceException {
        ActionType updateAction = ipsecConnection.getUpdateAction();
        if(updateAction == null) {
            return ipsecConnection;
        }
        List<IP> sourceSubnets = IpAddressUtil.jsonToIP(ipsecConnection.getSourceLanCidrs());
        List<IP> destSubnets = IpAddressUtil.jsonToIP(ipsecConnection.getDestLanCidrs());
        NetIpsecConnection dbIpsecConnection =
                new BaseDao<NetIpsecConnection>().query(ipsecConnection.getId(), NetIpsecConnection.class);
        if(dbIpsecConnection == null) {
            return null;
        }
        for(IP ip : sourceSubnets) {
            if(ActionType.CREATE == updateAction) {
                dbIpsecConnection.addSubnet(ip, true);
            } else if(ActionType.DELETE == updateAction) {
                dbIpsecConnection.removeSubnet(ip, true);
            }
        }
        for(IP ip : destSubnets) {
            if(ActionType.CREATE == updateAction) {
                dbIpsecConnection.addSubnet(ip, false);
            } else if(ActionType.DELETE == updateAction) {
                dbIpsecConnection.removeSubnet(ip, false);
            }
        }
        dbIpsecConnection.setActionState(null);
        return dbIpsecConnection;
    }

}
