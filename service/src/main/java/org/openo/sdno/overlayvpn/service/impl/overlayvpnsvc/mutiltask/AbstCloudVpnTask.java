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

import org.openo.sdno.framework.base.threadpool.Task;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * Abstract class of OverlayVpnTask.<br>
 * 
 * @author
 * @version SDNO 0.5 June 7, 2016
 */
public abstract class AbstCloudVpnTask extends Task {

    /**
     * HttpServlet Request
     */
    protected HttpServletRequest httpRequest;

    /**
     * HttpServlet Response
     */
    protected HttpServletResponse httpResponse;

    /**
     * VPN technology type
     */
    protected String vpnTechnology;

    /**
     * Overlay VPN
     */
    protected OverlayVpn overlayVpn;

    /**
     * Is task execution finished
     */
    protected boolean isFinish = false;

    /**
     * Result of the task execution
     */
    protected ResultRsp<OverlayVpn> execuRet = new ResultRsp<>(ErrorCode.OVERLAYVPN_SUCCESS);

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public AbstCloudVpnTask() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param vpnTechnology - VPN Technology
     * @param overlayVpn - Corresponding overlay VPN object
     */
    public AbstCloudVpnTask(HttpServletRequest req, HttpServletResponse resp, String vpnTechnology,
            OverlayVpn overlayVpn) {
        super();
        this.httpRequest = req;
        this.httpResponse = resp;
        this.vpnTechnology = vpnTechnology;
        this.overlayVpn = overlayVpn;
    }

    /**
     * Create a new OverlayVpn Instance.<br>
     * 
     * @since SDNO 0.5
     */
    protected void createNewOverlayVpnInstance() {
        String overlayVpnJson = JsonUtil.toJson(this.overlayVpn);
        this.overlayVpn = JsonUtil.fromJson(overlayVpnJson, OverlayVpn.class);
    }

    public String getVpnTechnology() {
        return vpnTechnology;
    }

    public void setVpnTechnology(String vpnTechnology) {
        this.vpnTechnology = vpnTechnology;
    }

    public OverlayVpn getOverlayVpn() {
        return overlayVpn;
    }

    public void setOverlayVpn(OverlayVpn overlayVpn) {
        this.overlayVpn = overlayVpn;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public ResultRsp<OverlayVpn> getExecuRet() {
        return execuRet;
    }

    public void setExecuRet(ResultRsp<OverlayVpn> execuRet) {
        this.execuRet = execuRet;
    }

    public HttpServletResponse getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(HttpServletResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public HttpServletRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

}
