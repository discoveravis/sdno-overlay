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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.mutiltask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.sbi.overlayvpn.IpSecService;
import org.openo.sdno.overlayvpn.sbi.overlayvpn.VxLANService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of OverlayVpn UnDeploy Task.<br>
 * 
 * @author
 * @version SDNO 0.5 June 7, 2016
 */
public class UndeployCloudVpnTask extends AbstCloudVpnTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(UndeployCloudVpnTask.class);

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param vpnTechnology Technology type
     * @param overlayVpn OverlayVpn need to Undeploy
     */
    public UndeployCloudVpnTask(HttpServletRequest req, HttpServletResponse resp, String vpnTechnology,
            OverlayVpn overlayVpn) {
        super(req, resp, vpnTechnology, overlayVpn);
    }

    /**
     * UnDeploy OverlayVpn.<br>
     * 
     * @since SDNO 0.5
     */
    @Override
    public void doTask() {

        this.isFinish = false;

        LOGGER.info("UndeployCloudVpnTask vpn:" + this.overlayVpn.getUuid() + ", tec: " + this.vpnTechnology);

        createNewOverlayVpnInstance();

        try {
            if(CommConst.VPN_TEC_VXLAN.equals(this.vpnTechnology)) {
                for(Connection connection : this.overlayVpn.getVpnConnections()) {
                    this.execuRet = VxLANService.getInstance().delete(httpRequest, connection.getUuid());
                }
            } else if(CommConst.VPN_TEC_IPSEC.equals(this.vpnTechnology)) {
                for(Connection connection : this.overlayVpn.getVpnConnections()) {
                    this.execuRet = IpSecService.getInstance().delete(httpRequest, connection.getUuid());
                }
            } else {
                LOGGER.error("vpn technology invalied. tec: " + this.vpnTechnology);
                this.execuRet = new ResultRsp<>(ErrorCode.OVERLAYVPN_FAILED);
            }
        } catch(ServiceException unDeployException) {
            LOGGER.error("UndeployCloudVpnTask exception: ", unDeployException);
            this.execuRet = new ResultRsp<>(ErrorCode.OVERLAYVPN_FAILED);
        }

        isFinish = true;
    }

    @Override
    public String getTaskName() {
        return "Overlayvpn service undeploy CloudVpn Task. uuid: " + this.overlayVpn.getUuid() + ", tec: "
                + this.vpnTechnology;
    }

}
