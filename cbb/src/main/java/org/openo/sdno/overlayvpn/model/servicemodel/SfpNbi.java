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

package org.openo.sdno.overlayvpn.model.servicemodel;

import java.util.ArrayList;
import java.util.List;

import org.openo.sdno.overlayvpn.model.servicechain.ServicePathHop;

/**
 * NBI interface for Site to DC use case<br>
 * 
 * @author
 * @version SDNO 0.5 July 18, 2016
 */

public class SfpNbi {

    String scfNeId;

    List<ServicePathHop> servicePathHops;

    public SfpNbi() {
        servicePathHops = new ArrayList<ServicePathHop>();
    }

    public String getScfNeId() {
        return scfNeId;
    }

    public void setScfNeId(String scfNeId) {
        this.scfNeId = scfNeId;
    }

    public List<ServicePathHop> getServicePathHops() {
        return servicePathHops;
    }

    public void setServicePathHops(List<ServicePathHop> servicePathHops) {
        this.servicePathHops = servicePathHops;
    }
}
