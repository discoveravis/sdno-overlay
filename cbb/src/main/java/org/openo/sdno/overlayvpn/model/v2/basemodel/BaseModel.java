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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseModel", propOrder = {"tenantId", "name", "description", "additionalInfo", "deployStatus",
                "actionState", "activeStatus", "runningStatus"})

@ApiModel(description = "base model")
public class BaseModel extends UuidModel {

    @XmlElement(name = "tenantId")
    @ApiModelProperty(example = "null", value = "")
    private String tenantId = null;

    @XmlElement(name = "name")
    @ApiModelProperty(example = "null", value = "")
    private String name = null;

    @XmlElement(name = "description")
    @ApiModelProperty(example = "null", value = "")
    private String description = null;

    @XmlElement(name = "additionalInfo")
    @ApiModelProperty(example = "null", value = "")
    private String additionalInfo = null;

    @XmlElement(name = "deployStatus")
    @ApiModelProperty(example = "null", required = true, value = "deploy status(deploy,undeploy)")
    private String deployStatus = null;

    @XmlElement(name = "actionState")
    @ApiModelProperty(example = "null", value = "action state(none,normal,creating,create_exception,deleting,delete_exception,updating,update_exception,deploying,deploy_exception)")
    private String actionState = null;

    @XmlElement(name = "activeStatus")
    @ApiModelProperty(example = "null", value = "active status(none,active,inactive,partially_inactive)")
    private String activeStatus = null;

    @XmlElement(name = "runningStatus")
    @ApiModelProperty(example = "null", value = "running status(none,up,down,partially_down)")
    private String runningStatus = null;

    /**
     * Get tenantId
     * 
     * @return tenantId
     **/
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Get name
     * 
     * @return name
     **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get description
     * 
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get additionalInfo
     * 
     * @return additionalInfo
     **/
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * deploy status(deploy,undeploy)
     * 
     * @return deployStatus
     **/
    public String getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(String deployStatus) {
        this.deployStatus = deployStatus;
    }

    /**
     * action
     * state(none,normal,creating,create_exception,deleting,delete_exception,updating,update_exception,deploying,deploy_exception)
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
     * active status(none,active,inactive,partially_inactive)
     * 
     * @return activeStatus
     **/
    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    /**
     * running status(none,up,down,partially_down)
     * 
     * @return runningStatus
     **/
    public String getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(String runningStatus) {
        this.runningStatus = runningStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BaseModel {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    tenantId: ").append(toIndentedString(tenantId)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    additionalInfo: ").append(toIndentedString(additionalInfo)).append("\n");
        sb.append("    deployStatus: ").append(toIndentedString(deployStatus)).append("\n");
        sb.append("    actionState: ").append(toIndentedString(actionState)).append("\n");
        sb.append("    activeStatus: ").append(toIndentedString(activeStatus)).append("\n");
        sb.append("    runningStatus: ").append(toIndentedString(runningStatus)).append("\n");
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
