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

package org.openo.sdno.overlayvpn.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOInvField;
import org.openo.sdno.overlayvpn.model.common.enums.ActionStatus;
import org.openo.sdno.overlayvpn.model.common.enums.AdminStatus;
import org.openo.sdno.overlayvpn.model.common.enums.OperStatus;
import org.openo.sdno.overlayvpn.model.common.enums.SourceType;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * The basic service information. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public abstract class BaseServiceModel extends ModelBase {

    /**
     * The operation status.
     */
    @AString(scope = "none,up,down,partially down")
    private String operStatus = OperStatus.NONE.getName();

    /**
     * The admin status.
     */
    @AString(require = true, scope = "none,active,inactive,partially inactive")
    private String adminStatus = AdminStatus.NONE.getName();

    /**
     * Action State
     */
    @AString(scope = "Normal,Creating,Deleting,Updating,Create_Excepion,Delete_Exception,Update_Exception")
    private String actionState = ActionStatus.NORMAL.getName();

    /**
     * create time
     */
    @MOInvField(invName = "createModelTime")
    @AString(min = 0, max = 255)
    private String createTime;

    /**
     * source of BaseNetModel
     */
    @JsonIgnore
    @AString(scope = "None,User,Discovery")
    private String source = SourceType.NONE.getName();

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
    }

    public String getActionState() {
        return actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * It is used to copy attributes of BaseServiceModel. <br>
     * 
     * @param data The original data.
     * @since SDNO 0.5
     */
    public void copyBasicData(BaseServiceModel data) {
        super.copyBasicData(data);
        this.adminStatus = data.getAdminStatus();
        this.operStatus = data.getOperStatus();
        this.actionState = data.getActionState();
        this.source = data.getSource();
        this.createTime = data.getCreateTime();
    }

}
