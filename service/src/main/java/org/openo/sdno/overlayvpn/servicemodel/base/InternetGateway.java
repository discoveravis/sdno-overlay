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

package org.openo.sdno.overlayvpn.servicemodel.base;

import java.util.List;

import org.openo.sdno.overlayvpn.model.v2.overlay.ServiceModel;

/**
 * The Internet gateway model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 10, 2017
 */
public class InternetGateway extends ServiceModel {

    private String siteId;

    private List<String> sourceSubnets;

    private String deployPosition;

    private String upstreamBandwidth;

    private String downstreamBandwidth;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public InternetGateway() {
        super();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public List<String> getSourceSubnets() {
        return sourceSubnets;
    }

    public void setSourceSubnets(List<String> sourceSubnets) {
        this.sourceSubnets = sourceSubnets;
    }

    public String getDeployPosition() {
        return deployPosition;
    }

    public void setDeployPosition(String deployPosition) {
        this.deployPosition = deployPosition;
    }

    public String getUpstreamBandwidth() {
        return upstreamBandwidth;
    }

    public void setUpstreamBandwidth(String upstreamBandwidth) {
        this.upstreamBandwidth = upstreamBandwidth;
    }

    public String getDownstreamBandwidth() {
        return downstreamBandwidth;
    }

    public void setDownstreamBandwidth(String downstreamBandwidth) {
        this.downstreamBandwidth = downstreamBandwidth;
    }

}
