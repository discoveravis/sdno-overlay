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

package org.openo.sdno.overlayvpn.servicemodel.enums;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;

/**
 * Tunnel template name.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 10, 2017
 */
public enum Reliability {
    SINGLEFIXEDNETWORK("singleFixedNetwork"), DUALFIXEDNETWORK("dualFixedNetwork"),
    FIXEDANDWIRELESSNETWORK("fixedAndWirelessNetwork"), ETHERNETNETWORK("EthernetNetwork"), VDSLNETWORK("VDSLNetwork"),
    GSHDSLNETWORK("GSHDSLNetwork"), ETHERNETANDLTENETWORK("EthernetAndLTENetwork"),
    ETHERNETANDETHERNETNETWORK("EthernetAndEthernetNetwork"), ETHERNETANDVDSLNETWORK("EthernetAndVDSLNetwork"),
    VDSLANDLTENETWORK("VDSLAndLTENetwork");

    private String reliability;

    private Reliability(String reliability) {
        this.reliability = reliability;
    }

    /**
     * Get Reliability by type.<br>
     * 
     * @param type The reliability type
     * @return The Reliability
     * @throws ServiceException when reliability type invalid
     * @since SDNO 0.5
     */
    public static Reliability getReliability(String type) throws ServiceException {
        for(Reliability reliability : Reliability.values()) {
            if(reliability.name().equalsIgnoreCase(type) || reliability.getReliability().equalsIgnoreCase(type)) {
                return reliability;
            }
        }
        throw new InnerErrorServiceException("reliability.get.error,reliability type invalid");
    }

    public String getReliability() {
        return reliability;
    }

    /**
     * Build the template name.<br>
     * 
     * @param templatePrefix The template name prefix
     * @return The template name
     * @since SDNO 0.5
     */
    public String getTemplate(String templatePrefix) {
        return new StringBuilder(templatePrefix).append('_').append(reliability).toString();
    }

}
