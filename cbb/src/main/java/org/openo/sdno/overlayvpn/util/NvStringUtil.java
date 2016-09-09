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

package org.openo.sdno.overlayvpn.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.model.ModelBase;
import org.openo.sdno.overlayvpn.model.NvString;

/**
 * Class of Nv String Operation.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public class NvStringUtil {

    private NvStringUtil() {
    }

    /**
     * Modify NV Value.<br>
     * 
     * @param nvList List of NvString
     * @since SDNO 0.5
     */
    public static void toggleMdfNvValue(List<NvString> nvList) {
        if(CollectionUtils.isEmpty(nvList)) {
            return;
        }
        NvString nv = new NvString();
        nv.setName(CommConst.MODIFY_MASK);
        int index = nvList.indexOf(nv);
        if(-1 == index) {
            return;
        }
        nv = nvList.get(index);
        if(CommConst.MODIFY_MASK_ADD.equals(nv.getValue())) {
            nv.setValue(CommConst.MODIFY_MASK_DELETE);
        } else if(CommConst.MODIFY_MASK_DELETE.equals(nv.getValue())) {
            nv.setValue(CommConst.MODIFY_MASK_ADD);
        }
    }

    /**
     * Add NvString to Model.<br>
     * 
     * @param nv NvString Object
     * @param model Model Object
     * @since SDNO 0.5
     */
    public static void addNvString4Model(NvString nv, ModelBase model) {
        if(null == model.getAdditionalInfo()) {
            model.setAdditionalInfo(new ArrayList<NvString>());
        }

        int index = model.getAdditionalInfo().indexOf(nv);
        if(-1 != index) {
            model.getAdditionalInfo().remove(index);
        }

        nv.setFirstParentUuid(model.getUuid());
        model.getAdditionalInfo().add(nv);
    }

}
