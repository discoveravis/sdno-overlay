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
@XmlType(name = "NbiNqa", propOrder = {"neId", "neRole", "srcIp", "srcPortName", "dstIp", "dstPortName", "testType",
                "frequency", "probeCount", "timeout", "ttl", "tos", "interval"})

public class NbiNqa extends BaseModel {

    @XmlElement(name = "neId")
    @ApiModelProperty(example = "null", required = true, value = "NE Id")
    private String neId = null;

    @XmlElement(name = "neRole")
    @ApiModelProperty(example = "null", value = "NE role")
    private String neRole = null;

    @XmlElement(name = "srcIp")
    @ApiModelProperty(example = "null", value = "source IP address")
    private String srcIp = null;

    @XmlElement(name = "srcPortName")
    @ApiModelProperty(example = "null", value = "source port name")
    private String srcPortName = null;

    @XmlElement(name = "dstIp")
    @ApiModelProperty(example = "null", required = true, value = "destination IP address")
    private String dstIp = null;

    @XmlElement(name = "dstPortName")
    @ApiModelProperty(example = "null", value = "destination port name")
    private String dstPortName = null;

    @XmlElement(name = "testType")
    @ApiModelProperty(example = "null", value = "test type, now only support \"ping\"")
    private String testType = null;

    @XmlElement(name = "frequency")
    @ApiModelProperty(example = "null", value = "frequency, the range is 1 to 604800")
    private String frequency = null;

    @XmlElement(name = "probeCount")
    @ApiModelProperty(example = "null", value = "probe count, the range is 1 to 15")
    private String probeCount = null;

    @XmlElement(name = "timeout")
    @ApiModelProperty(example = "null", value = "timeout, the range is 1 to 60")
    private String timeout = null;

    @XmlElement(name = "ttl")
    @ApiModelProperty(example = "null", value = "time to live, the range is 1 to 255")
    private String ttl = null;

    @XmlElement(name = "tos")
    @ApiModelProperty(example = "null", value = "type of service, the range is 1 to 255")
    private String tos = null;

    @XmlElement(name = "interval")
    @ApiModelProperty(example = "null", value = "interval")
    private String interval = null;

    /**
     * NE Id
     * 
     * @return neId
     **/
    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    /**
     * NE role
     * 
     * @return neRole
     **/
    public String getNeRole() {
        return neRole;
    }

    public void setNeRole(String neRole) {
        this.neRole = neRole;
    }

    /**
     * source IP address
     * 
     * @return srcIp
     **/
    public String getSrcIp() {
        return srcIp;
    }

    public void setSrcIp(String srcIp) {
        this.srcIp = srcIp;
    }

    /**
     * source port name
     * 
     * @return srcPortName
     **/
    public String getSrcPortName() {
        return srcPortName;
    }

    public void setSrcPortName(String srcPortName) {
        this.srcPortName = srcPortName;
    }

    /**
     * destination IP address
     * 
     * @return dstIp
     **/
    public String getDstIp() {
        return dstIp;
    }

    public void setDstIp(String dstIp) {
        this.dstIp = dstIp;
    }

    /**
     * destination port name
     * 
     * @return dstPortName
     **/
    public String getDstPortName() {
        return dstPortName;
    }

    public void setDstPortName(String dstPortName) {
        this.dstPortName = dstPortName;
    }

    /**
     * test type, now only support \"ping\"
     * 
     * @return testType
     **/
    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    /**
     * frequency, the range is 1 to 604800
     * 
     * @return frequency
     **/
    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    /**
     * probe count, the range is 1 to 15
     * 
     * @return probeCount
     **/
    public String getProbeCount() {
        return probeCount;
    }

    public void setProbeCount(String probeCount) {
        this.probeCount = probeCount;
    }

    /**
     * timeout, the range is 1 to 60
     * 
     * @return timeout
     **/
    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    /**
     * time to live, the range is 1 to 255
     * 
     * @return ttl
     **/
    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    /**
     * type of service, the range is 1 to 255
     * 
     * @return tos
     **/
    public String getTos() {
        return tos;
    }

    public void setTos(String tos) {
        this.tos = tos;
    }

    /**
     * interval
     * 
     * @return interval
     **/
    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class NbiNqa {\n");
        sb.append("    ").append(toIndentedString(super.toString())).append("\n");
        sb.append("    neId: ").append(toIndentedString(neId)).append("\n");
        sb.append("    neRole: ").append(toIndentedString(neRole)).append("\n");
        sb.append("    srcIp: ").append(toIndentedString(srcIp)).append("\n");
        sb.append("    srcPortName: ").append(toIndentedString(srcPortName)).append("\n");
        sb.append("    dstIp: ").append(toIndentedString(dstIp)).append("\n");
        sb.append("    dstPortName: ").append(toIndentedString(dstPortName)).append("\n");
        sb.append("    testType: ").append(toIndentedString(testType)).append("\n");
        sb.append("    frequency: ").append(toIndentedString(frequency)).append("\n");
        sb.append("    probeCount: ").append(toIndentedString(probeCount)).append("\n");
        sb.append("    timeout: ").append(toIndentedString(timeout)).append("\n");
        sb.append("    ttl: ").append(toIndentedString(ttl)).append("\n");
        sb.append("    tos: ").append(toIndentedString(tos)).append("\n");
        sb.append("    interval: ").append(toIndentedString(interval)).append("\n");
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
