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

package org.openo.sdno.overlayvpn.util.operation;

import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDc;
import org.openo.sdno.overlayvpn.model.servicemodel.SubNet;
import org.openo.sdno.overlayvpn.model.servicemodel.Vpc;
import org.openo.sdno.overlayvpn.model.servicemodel.VpcSubNetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class of VPC and subnet.<br>
 * 
 * @author
 * @version SDNO 0.5 August 24, 2016
 */
public class VpcSubnetUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpcSubnetUtil.class);

    private VpcSubnetUtil() {
    }

    /**
     * Build VpcSubNetMapping object.<br>
     * 
     * @param siteToDc Site to DC model
     * @return VpcSubNetMapping object
     * @since SDNO 0.5
     */
    public static VpcSubNetMapping buildVpcSubnetMapping(SiteToDc siteToDc) {

        Vpc vpc = siteToDc.getVpc();
        SubNet subnet = vpc.getSubnet();
        VpcSubNetMapping vpcSubNetMapping = new VpcSubNetMapping();
        vpcSubNetMapping.allocateUuid();
        vpcSubNetMapping.setSitetodcId(siteToDc.getUuid());
        vpcSubNetMapping.setSubnetId(subnet.getUuid());
        vpcSubNetMapping.setSubnetName(subnet.getName());
        vpcSubNetMapping.setSubnetDescription(subnet.getDescription());
        vpcSubNetMapping.setSubnetCidr(subnet.getCidr());
        vpcSubNetMapping.setVpcId(vpc.getUuid());
        vpcSubNetMapping.setVpcName(vpc.getName());
        vpcSubNetMapping.setVpcDescription(vpc.getDescription());
        vpcSubNetMapping.setStatus(vpc.getStatus());
        vpcSubNetMapping.setOsControllerId(vpc.getOsControllerId());
        vpcSubNetMapping.setExternalIp(vpc.getExternalIp());
        vpcSubNetMapping.setVni(subnet.getVni());
        if(null != vpc.getAttributes()) {
            vpcSubNetMapping.setUnderResId(vpc.getAttributes().getUuid());
            vpcSubNetMapping.setUnderResParentId(vpc.getAttributes().getParentId());
            vpcSubNetMapping.setUnderResRouterId(vpc.getAttributes().getRouterId());
        }

        return vpcSubNetMapping;
    }

    public static void setSiteToDcByVpcMapping(SiteToDc site2Dc, VpcSubNetMapping vpcSubNetMapping) {
        Vpc vpc = site2Dc.getVpc();
        vpc.setUuid(vpcSubNetMapping.getVpcId());
        vpc.setName(vpcSubNetMapping.getVpcName());
        vpc.setDescription(vpcSubNetMapping.getVpcDescription());
        vpc.setStatus(vpcSubNetMapping.getStatus());
        vpc.setOsControllerId(vpcSubNetMapping.getOsControllerId());
        vpc.setExternalIp(vpcSubNetMapping.getExternalIp());

        SubNet subnet = vpc.getSubnet();
        subnet.setUuid(vpcSubNetMapping.getSubnetId());
        subnet.setName(vpcSubNetMapping.getSubnetName());
        subnet.setDescription(vpcSubNetMapping.getSubnetDescription());
        subnet.setCidr(vpcSubNetMapping.getSubnetCidr());
        subnet.setVni(vpcSubNetMapping.getVni());
        subnet.setVpcId(vpcSubNetMapping.getVpcId());
    }
}
