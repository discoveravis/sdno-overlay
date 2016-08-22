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

package org.openo.sdno.overlayvpn.brs.model;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.ssl.EncryptionUtil;

/**
 * Model class of communication parameter for controller.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-16
 */
public final class CommParamMO extends BaseMO {

    private static final String MOKEY = "commparam";

    private String objectId;

    private String protocol;

    private String hostName;

    private String port;

    /**
     * User name and password related info.
     */
    private String commParams;

    /**
     * Get authentication info.<br/>
     * 
     * @return Authentication info
     * @since SDNO 0.5
     */
    @JsonIgnore
    public AuthInfo getAuthInfo() {
        char[] tempCommParams = EncryptionUtil.decode(this.commParams.toCharArray());
        AuthInfo authInfo = JsonUtil.fromJson(new String(tempCommParams), AuthInfo.class);
        EncryptionUtil.clear(tempCommParams);
        return authInfo;
    }

    /**
     * Set authentication info.<br/>
     * 
     * @return Authentication info
     * @since SDNO 0.5
     */
    @JsonIgnore
    public void setAuthInfo(AuthInfo info) {
        char[] encodedAuthInfo = EncryptionUtil.encode(JsonUtil.toJson(info).toCharArray());
        this.setCommParams(new String(encodedAuthInfo));
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCommParams() {
        return commParams;
    }

    public void setCommParams(String commParams) {
        this.commParams = commParams;
    }

    @Override
    public String toJsonBody() {
        Map<String, Object> moListMap = new HashMap<String, Object>();
        moListMap.put(MOKEY, this);
        return JsonUtil.toJson(moListMap);
    }

}
