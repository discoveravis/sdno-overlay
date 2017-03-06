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

package org.openo.sdno.overlayvpn.model.v2.vxlan;

import java.util.List;

/**
 * VxLAN action model class.<br>
 * 
 * @author
 * @version SDNO 0.5 June 6, 2016
 */
public class ActionModel {

    /**
     * Uuid of deployed VxLAN list
     */
    private List<String> deploy;

    /**
     * Uuid of undeployed VxLAN list
     */
    private List<String> undeploy;

    public List<String> getDeploy() {
        return deploy;
    }

    public void setDeploy(List<String> deploy) {
        this.deploy = deploy;
    }

    public List<String> getUndeploy() {
        return undeploy;
    }

    public void setUndeploy(List<String> undeploy) {
        this.undeploy = undeploy;
    }
}
