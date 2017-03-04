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

package org.openo.sdno.overlayvpn.servicemodel.sbi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOPrivacyField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONInvField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.NONSBIField;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.servicemodel.base.IP;
import org.openo.sdno.overlayvpn.servicemodel.base.IkePolicy;
import org.openo.sdno.overlayvpn.servicemodel.enums.ActionType;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;

/**
 * The ipsec connection model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
@MOResType(infoModelName = "net_ipsecconnection")
public class NetIpsecConnection extends NbiNetConnection {

    private String workType;

    private String nqa;

    @MOPrivacyField(type = "json", classz = IkePolicy.class)
    private String ikePolicy;

    private String ipsecPolicy;

    private String srcIsTemplateType;

    private String destIsTemplateType;

    private String ruleSrcPortName;

    private String ruleDestPortName;

    private String sourceLanCidrs;

    private String destLanCidrs;

    @NONInvField
    @NONSBIField
    @JsonIgnore
    private ActionType updateAction;

    private String regionId;

    @NONSBIField
    private String srcPortIp;

    @NONSBIField
    private String destPortIp;

    private String protectionPolicy;

    private String qosPreClassify;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public NetIpsecConnection() {
        super();
    }

    @Override
    public NbiNetConnection toReverse() throws ServiceException {
        NbiNetConnection connection = super.toReverse();
        NetIpsecConnection ipsec = (NetIpsecConnection)connection;

        String srcIsTemplateType = ipsec.getSrcIsTemplateType();
        String ruleSrcPortName = ipsec.getRuleSrcPortName();
        String sourceLanCidrs = ipsec.getSourceLanCidrs();

        ipsec.setSrcIsTemplateType(ipsec.getDestIsTemplateType());
        ipsec.setRuleSrcPortName(ipsec.getRuleDestPortName());
        ipsec.setSourceLanCidrs(ipsec.getDestLanCidrs());

        ipsec.setDestIsTemplateType(srcIsTemplateType);
        ipsec.setRuleDestPortName(ruleSrcPortName);
        ipsec.setDestLanCidrs(sourceLanCidrs);
        return ipsec;
    }

    @Override
    public NbiNetConnection toQueryCondition() throws ServiceException {
        NbiNetConnection netConnection = super.toQueryCondition();
        ((NetIpsecConnection)netConnection).setRegionId(regionId);
        return netConnection;
    }

    /**
     * Add subnet.<br>
     * 
     * @param subnet The subnet to set
     * @param src Whether is source
     * @throws ServiceException when translating json failed
     * @since SDNO 0.5
     */
    public void addSubnet(IP subnet, boolean src) throws ServiceException {
        if(subnet == null) {
            return;
        }
        String lanCidrs = src ? this.sourceLanCidrs : this.destLanCidrs;
        List<IP> lanSubnets = StringUtils.isNotEmpty(lanCidrs)
                ? JsonUtils.fromJson(lanCidrs, new TypeReference<List<IP>>() {}) : new ArrayList<IP>();
        for(IP ip : lanSubnets) {
            if(subnet.toString().equals(ip.toString())) {
                return;
            }
        }
        lanSubnets.add(subnet);
        String updateLanCidrs = JsonUtils.toJson(lanSubnets);
        if(src) {
            setSourceLanCidrs(updateLanCidrs);
        } else {
            setDestLanCidrs(updateLanCidrs);
        }
    }

    /**
     * Remove subnet.<br>
     * 
     * @param subnet The subnet to be removed
     * @param src Whether is source
     * @throws ServiceException when translating json failed
     * @since SDNO 0.5
     */
    public void removeSubnet(IP subnet, boolean src) throws ServiceException {
        if(subnet == null) {
            return;
        }
        String lanCidrs = src ? this.sourceLanCidrs : this.destLanCidrs;
        List<IP> lanSubnets = StringUtils.isNotEmpty(lanCidrs)
                ? JsonUtils.fromJson(lanCidrs, new TypeReference<List<IP>>() {}) : new ArrayList<IP>();
        if(CollectionUtils.isEmpty(lanSubnets)) {
            return;
        }
        boolean removed = false;
        Iterator<IP> iterator = lanSubnets.iterator();
        while(iterator.hasNext()) {
            IP ip = iterator.next();
            if(ip.toString().equals(subnet.toString())) {
                iterator.remove();
                removed = true;
            }
        }
        if(!removed) {
            return;
        }
        String updateLanCidrs = JsonUtils.toJson(lanSubnets);
        if(src) {
            setSourceLanCidrs(updateLanCidrs);
        } else {
            setDestLanCidrs(updateLanCidrs);
        }
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getNqa() {
        return nqa;
    }

    public void setNqa(String nqa) {
        this.nqa = nqa;
    }

    public String getIkePolicy() {
        return ikePolicy;
    }

    public void setIkePolicy(String ikePolicy) {
        this.ikePolicy = ikePolicy;
    }

    public String getIpsecPolicy() {
        return ipsecPolicy;
    }

    public void setIpsecPolicy(String ipsecPolicy) {
        this.ipsecPolicy = ipsecPolicy;
    }

    public String getSrcIsTemplateType() {
        return srcIsTemplateType;
    }

    public void setSrcIsTemplateType(String srcIsTemplateType) {
        this.srcIsTemplateType = srcIsTemplateType;
    }

    public String getDestIsTemplateType() {
        return destIsTemplateType;
    }

    public void setDestIsTemplateType(String destIsTemplateType) {
        this.destIsTemplateType = destIsTemplateType;
    }

    public String getRuleSrcPortName() {
        return ruleSrcPortName;
    }

    public void setRuleSrcPortName(String ruleSrcPortName) {
        this.ruleSrcPortName = ruleSrcPortName;
    }

    public String getRuleDestPortName() {
        return ruleDestPortName;
    }

    public void setRuleDestPortName(String ruleDestPortName) {
        this.ruleDestPortName = ruleDestPortName;
    }

    public String getSourceLanCidrs() {
        return sourceLanCidrs;
    }

    public void setSourceLanCidrs(String sourceLanCidrs) {
        this.sourceLanCidrs = sourceLanCidrs;
    }

    public String getDestLanCidrs() {
        return destLanCidrs;
    }

    public void setDestLanCidrs(String destLanCidrs) {
        this.destLanCidrs = destLanCidrs;
    }

    public ActionType getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(ActionType updateAction) {
        this.updateAction = updateAction;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getSrcPortIp() {
        return srcPortIp;
    }

    public void setSrcPortIp(String srcPortIp) {
        this.srcPortIp = srcPortIp;
    }

    public String getDestPortIp() {
        return destPortIp;
    }

    public void setDestPortIp(String destPortIp) {
        this.destPortIp = destPortIp;
    }

    public String getProtectionPolicy() {
        return protectionPolicy;
    }

    public void setProtectionPolicy(String protectionPolicy) {
        this.protectionPolicy = protectionPolicy;
    }

    public String getQosPreClassify() {
        return qosPreClassify;
    }

    public void setQosPreClassify(String qosPreClassify) {
        this.qosPreClassify = qosPreClassify;
    }
}
