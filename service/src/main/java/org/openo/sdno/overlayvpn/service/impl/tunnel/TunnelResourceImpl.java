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

package org.openo.sdno.overlayvpn.service.impl.tunnel;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.tunnel.Tunnel;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.connection.ConnectionSvcImpl;
import org.openo.sdno.overlayvpn.service.inf.tunnel.ITunnelService;

/**
 * Tunnel service implementation.<br/>
 * <p>
 * </p>
 *
 * @author
 * @version SDNO 0.5 Jun 7, 2016
 */
public class TunnelResourceImpl implements ITunnelService {

    /**
     * ConnectionSvcImpl instance
     */
    @Resource
    private ConnectionSvcImpl connectionSvcImpl;

    /**
     * Query Tunnel.<br/>
     * This Function is used to upload OverlayVpn Network Resource
     *
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param overlayVpnUuidList List of OverlayVpn Key Id
     * @param tenantIdFromToken tenant id
     * @return List of Tunnel Queried out
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<List<Tunnel>> query(HttpServletRequest req, HttpServletResponse resp,
            List<String> overlayVpnUuidList, String tenantIdFromToken) throws ServiceException {

        return null;
    }

    public void setConnectionSvcImpl(ConnectionSvcImpl connectionSvcImpl) {
        this.connectionSvcImpl = connectionSvcImpl;
    }
}
