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

package org.openo.sdno.overlayvpn.model.v2.basemodel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseServiceModel", propOrder = {"adminStatus", "operStatus", "actionState", "createTime"})

public class BaseServiceModel extends ModelBase {

    @XmlElement(name = "adminStatus")
    @ApiModelProperty(example = "null", required = true, value = "the scope is none,active,inactive,partially inactive")
    private String adminStatus = null;

    @XmlElement(name = "operStatus")
    @ApiModelProperty(example = "null", value = "the scope is none,up,down,partially down")
    private String operStatus = null;

    @XmlElement(name = "actionState")
    @ApiModelProperty(example = "null", value = "the scope is Normal,Creating,Deleting,Updating,Checking,Create_Exception, Update_Exception,Delete_Exception,Check_Exception")
    private String actionState = null;

    @XmlElement(name = "createTime")
    @ApiModelProperty(example = "null", value = "create time")
    private String createTime = null;

    /**
     * the scope is none,active,inactive,partially inactive
     * 
     * @return adminStatus
     **/
    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    /**
     * the scope is none,up,down,partially down
     * 
     * @return operStatus
     **/
    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
    }

    /**
     * the scope is Normal,Creating,Deleting,Updating,Checking,Create_Exception,
     * Update_Exception,Delete_Exception,Check_Exception
     * 
     * @return actionState
     **/
    public String getActionState() {
        return actionState;
    }

    public void setActionState(String actionState) {
        this.actionState = actionState;
    }

    /**
     * create time
     * 
     * @return createTime
     **/
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BaseServiceModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    adminStatus: ").append(toIndentedString(adminStatus)).append("\n");
        sb.append("    operStatus: ").append(toIndentedString(operStatus)).append("\n");
        sb.append("    actionState: ").append(toIndentedString(actionState)).append("\n");
        sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
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
}
