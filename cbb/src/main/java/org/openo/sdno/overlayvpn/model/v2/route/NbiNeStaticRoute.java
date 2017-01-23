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

package org.openo.sdno.overlayvpn.model.v2.route;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.model.v2.basemodel.NbiBaseServiceModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;
import org.springframework.util.StringUtils;

@MOResType(infoModelName = "nbinestaticroute")
public class NbiNeStaticRoute extends NbiBaseServiceModel {

    @AString(require = true, scope = "static")
    private String type = null;

    @AString(max = 255)
    private String destIp = null;

    @AString(scope = "true,false")
    private String enableDhcp = null;

    @AString(max = 255)
    private String nextHop = null;

    @AString(max = 255)
    private String outInterfaceName = null;

    @AInt(min = 0, max = 255)
    private String priority = null;

    @AUuid
    private String nqa = null;

    @NONInvField
    @JsonIgnore
    @Valid
    private Ip destIpData;

    @NONInvField
    @JsonIgnore
    @Valid
    private Ip nextHopData;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     */
    public NbiNeStaticRoute() {
        super();
    }

    /**
     * the scope is static
     * 
     * @return type
     **/
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * destinantion IP address
     * 
     * @return destIp
     **/
    public String getDestIp() {
        return destIp;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }

    /**
     * the scope is true,false
     * 
     * @return enableDhcp
     **/
    public String getEnableDhcp() {
        return enableDhcp;
    }

    public void setEnableDhcp(String enableDhcp) {
        this.enableDhcp = enableDhcp;
    }

    /**
     * next hop IP address
     * 
     * @return nextHop
     **/
    public String getNextHop() {
        return nextHop;
    }

    public void setNextHop(String nextHop) {
        this.nextHop = nextHop;
    }

    /**
     * outbound interface name
     * 
     * @return outInterfaceName
     **/
    public String getOutInterfaceName() {
        return outInterfaceName;
    }

    public void setOutInterfaceName(String outInterfaceName) {
        this.outInterfaceName = outInterfaceName;
    }

    /**
     * the priority of the route
     * 
     * @return priority
     **/
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * network quality analyzer
     * 
     * @return nqa
     **/
    public String getNqa() {
        return nqa;
    }

    public void setNqa(String nqa) {
        this.nqa = nqa;
    }

    public Ip getDestIpData() {
        return destIpData;
    }

    public void setDestIpData(Ip destIpData) {
        this.destIpData = destIpData;
    }

    public Ip getNextHopData() {
        return nextHopData;
    }

    public void setNextHopData(Ip nextHopData) {
        this.nextHopData = nextHopData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiNeStaticRoute {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    destIp: ").append(toIndentedString(destIp)).append("\n");
        sb.append("    enableDhcp: ").append(toIndentedString(enableDhcp)).append("\n");
        sb.append("    nextHop: ").append(toIndentedString(nextHop)).append("\n");
        sb.append("    outInterfaceName: ").append(toIndentedString(outInterfaceName)).append("\n");
        sb.append("    priority: ").append(toIndentedString(priority)).append("\n");
        sb.append("    nqa: ").append(toIndentedString(nqa)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(Object o) {
        if(o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public void transferJsonData() {
        if(StringUtils.hasLength(destIp)) {
            setDestIpData(JsonUtil.fromJson(destIp, Ip.class));
        }

        if(StringUtils.hasLength(nextHop)) {
            setNextHopData(JsonUtil.fromJson(nextHop, Ip.class));
        }
    }
}
