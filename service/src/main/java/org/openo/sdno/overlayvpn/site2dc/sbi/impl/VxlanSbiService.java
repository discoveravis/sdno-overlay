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

import java.util.List;
import java.util.Set;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetVxlanConnection;
import org.openo.sdno.overlayvpn.site2dc.sbi.inf.SbiService;
import org.springframework.stereotype.Service;

/**
 * The SBI service of vxlan.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 17, 2017
 */
@Service("vxlanSbiService")
public class VxlanSbiService extends AbstractSbiService<NetVxlanConnection> implements SbiService<NetVxlanConnection> {

    private static final String SBI_URL = "/openoapi/sdnovxlan/v1/vxlan/tunnel";

    @Override
    public void create(List<NetVxlanConnection> connectionList) throws ServiceException {
        dao.insert(connectionList);
        doCreate(SBI_URL, NetVxlanConnection.class, connectionList);
    }

    @Override
    public void delete(String uuid) throws ServiceException {
        doDelete(SBI_URL, uuid, NetVxlanConnection.class);
        dao.delete(uuid, NetVxlanConnection.class);

    }

    @Override
    public void update(List<NetVxlanConnection> mos) throws ServiceException {
        doUpdate(SBI_URL, NetVxlanConnection.class, mos);
        dao.update(mos);
    }

    @Override
    public void deploy(List<String> uuidList) throws ServiceException {
        doDeploy(SBI_URL, NetVxlanConnection.class, uuidList);
    }

    @Override
    public void undeploy(List<String> uuidList) throws ServiceException {
        doUndeploy(SBI_URL, NetVxlanConnection.class, uuidList);
    }

    @Override
    public List<NetVxlanConnection> queryRemote(Set<String> uuids) throws ServiceException {
        return doQuery(SBI_URL, NetVxlanConnection.class, uuids);
    }

}