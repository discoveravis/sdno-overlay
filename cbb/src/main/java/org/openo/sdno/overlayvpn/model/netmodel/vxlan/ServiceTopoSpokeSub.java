/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.model.netmodel.vxlan;

import java.util.List;

import org.openo.sdno.overlayvpn.model.NvString;
import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of ServiceTopoSpokeSub.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class ServiceTopoSpokeSub {

    /**
     * Key Id
     */
    @AString(min = 0, max = 36)
    private String uuid;

    @AString(min = 1, max = 36)
    private String index;

    private List<Tp> hubTpIdList;

    @AString(min = 1, max = 255)
    private String serviceTopoId;

    /**
     * Additional Info
     */
    @AString()
    private List<NvString> addtionalInfo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<Tp> getHubTpIdList() {
        return hubTpIdList;
    }

    public void setHubTpIdList(List<Tp> hubTpIdList) {
        this.hubTpIdList = hubTpIdList;
    }

    public String getServiceTopoId() {
        return serviceTopoId;
    }

    public void setServiceTopoId(String serviceTopoId) {
        this.serviceTopoId = serviceTopoId;
    }

    public List<NvString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(List<NvString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

}
