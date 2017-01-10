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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.openo.sdno.overlayvpn.model.v2.basemodel.BaseModel;

import io.swagger.annotations.ApiModelProperty;

@XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name = "SbiBaseNetModel", propOrder =
    { "deviceId", "controllerId", "externalId", "nbiNeRouteId"
})


public class SbiRouteNetModel extends BaseModel {
  

  @XmlElement(name="deviceId")
  @ApiModelProperty(example = "null", required = true, value = "device ID")
  private String deviceId = null;

  @XmlElement(name="controllerId")
  @ApiModelProperty(example = "null", value = "controller ID")
  private String controllerId = null;

  @XmlElement(name="externalId")
  @ApiModelProperty(example = "null", value = "external ID")
  private String externalId = null;

  @XmlElement(name="nbiNeRouteId")
  @ApiModelProperty(example = "null", required = true, value = "the UUID of nbi model")
  private String nbiNeRouteId = null;

 /**
   * device ID
   * @return deviceId
  **/
  public String getDeviceId() {
    return deviceId;
  }
  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }
 /**
   * controller ID
   * @return controllerId
  **/
  public String getControllerId() {
    return controllerId;
  }
  public void setControllerId(String controllerId) {
    this.controllerId = controllerId;
  }
 /**
   * external ID
   * @return externalId
  **/
  public String getExternalId() {
    return externalId;
  }
  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }
 /**
   * the UUID of nbi model
   * @return nbiNeRouteId
  **/
  public String getNbiNeRouteId() {
    return nbiNeRouteId;
  }
  public void setNbiNeRouteId(String nbiNeRouteId) {
    this.nbiNeRouteId = nbiNeRouteId;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SbiBaseNetModel {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    controllerId: ").append(toIndentedString(controllerId)).append("\n");
    sb.append("    externalId: ").append(toIndentedString(externalId)).append("\n");
    sb.append("    nbiNeRouteId: ").append(toIndentedString(nbiNeRouteId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private static String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

