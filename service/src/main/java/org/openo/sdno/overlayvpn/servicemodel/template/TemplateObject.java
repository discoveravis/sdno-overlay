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

package org.openo.sdno.overlayvpn.servicemodel.template;

import java.util.List;

import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.springframework.util.CollectionUtils;

/**
 * The template object model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class TemplateObject extends BaseMo {

    private List<Spec> specList;

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    /**
     * Get spec by connection name.<br>
     * 
     * @return The spec object
     * @since SDNO 0.5
     */
    public Spec getConnections() {
        return getSpecByName(ParamConsts.CONNECTIONS);
    }

    private Spec getSpecByName(String name) {
        if(CollectionUtils.isEmpty(specList)) {
            return null;
        }
        for(Spec spec : specList) {
            if(spec.getName().equals(name)) {
                return spec;
            }
        }
        return null;
    }

}
