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

import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of OverlayVpn Create Task.<br>
 * 
 * @author
 * @version SDNO 0.5 June 7, 2016
 */
public class CreateCloudVpnTask extends AbstCloudVpnTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateCloudVpnTask.class);

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param vpnTechnology Technology type
     * @param overlayVpn OverlayVpn need to create
     */
    public CreateCloudVpnTask(HttpServletRequest req, HttpServletResponse resp, String vpnTechnology,
            OverlayVpn overlayVpn) {
        super(req, resp, vpnTechnology, overlayVpn);
    }

    /**
     * Create OverlayVpn.<br>
     * 
     * @since SDNO 0.5
     */
    @Override
    public void doTask() {

        this.isFinish = false;

        LOGGER.info("CreateCloudVpnTask OverlayVpn:" + this.overlayVpn.getUuid() + ", tec: " + this.vpnTechnology);

        this.isFinish = true;
    }

    @Override
    public String getTaskName() {
        return "Overlayvpn service create OverlayVpn Task. uuid: " + this.overlayVpn.getUuid() + ", tec: "
                + this.vpnTechnology;
    }

}
