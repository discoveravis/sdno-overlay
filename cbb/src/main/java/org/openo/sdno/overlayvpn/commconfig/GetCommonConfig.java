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

package org.openo.sdno.overlayvpn.commconfig;

/**
 * Get Common Configuration parameter.<br>
 * 
 * @author
 * @version SDNO 0.5 May 26, 2016
 */
public class GetCommonConfig {

    private static volatile GetCommonConfig instance;

    private GetCommonConfig() {
    }

    /**
     * Get Singleton instance.<br>
     * 
     * @return Singleton instance
     * @since SDNO 0.5
     */
    public static synchronized GetCommonConfig getInstanceOf() {
        if(null == instance) {
            instance = new GetCommonConfig();
        }
        return instance;
    }

    /**
     * Get Value by Key.<br>
     * 
     * @param key key
     * @return common value
     * @since SDNO 0.5
     */
    public String getCommonValueOf(String key) {
        return "";
    }

    /**
     * Get Cloud VPN value by key.<br>
     * 
     * @param key key
     * @return common value
     * @since SDNO 0.5
     */
    public String getCloudVpnValueOf(String key) {
        return "";
    }
}
