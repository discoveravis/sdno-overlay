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

package org.openo.sdno.overlayvpn.model.netmodel.vxlan;

import org.openo.sdno.overlayvpn.model.ModelBase;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of VxLan Model Base.<br>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class VxlanModelBase extends ModelBase {

    /**
     * source: "User" means created by user,Discovery means collected from controller
     */
    @AString(require = true, scope = "USER,DISCOVERY")
    private String source;

    /**
     * State of Action:Normal/Creating/Deleting/Updating
     */
    @AString(require = true, scope = "NORMAL,CREATING,DELETING,UPDATING")
    private String actionState;

    /**
     * Administrative Status of VPN
     */
    @AString(require = true, scope = "NORMAL,CREATING,DELETING,UPDATING")
    private String adminStatus;

    /**
     * Running Status of VPN:ACTIVE, DOWN, ERROR
     */
    @AString(scope = "ACTIVE,DOWN,ERROR")
    private String runningStatus;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getActionState() {
        return actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(String runningStatus) {
        this.runningStatus = runningStatus;
    }
}
