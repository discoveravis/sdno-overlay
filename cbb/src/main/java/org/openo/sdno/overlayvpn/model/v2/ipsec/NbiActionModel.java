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

package org.openo.sdno.overlayvpn.model.v2.ipsec;

import java.util.List;

/**
 * Nbi Action Model.<br/>
 * 
 * @author
 * @version SDNO 0.5 Jan 16, 2017
 */
public class NbiActionModel {

    private List<String> deploy;

    private List<String> undeploy;

    /**
     * @return Returns the deploy.
     */
    public List<String> getDeploy() {
        return deploy;
    }

    /**
     * @param deploy The deploy to set.
     */
    public void setDeploy(List<String> deploy) {
        this.deploy = deploy;
    }

    /**
     * @return Returns the undeploy.
     */
    public List<String> getUndeploy() {
        return undeploy;
    }

    /**
     * @param undeploy The undeploy to set.
     */
    public void setUndeploy(List<String> undeploy) {
        this.undeploy = undeploy;
    }

}
