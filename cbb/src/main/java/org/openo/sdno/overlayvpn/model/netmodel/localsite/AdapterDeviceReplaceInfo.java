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

package org.openo.sdno.overlayvpn.model.netmodel.localsite;

import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of Device Replace Info.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class AdapterDeviceReplaceInfo {

    /**
     * old device id
     */
    @AString(require = true)
    private String originalDeviceId;

    /**
     * Replace Initial File or not,default is false
     */
    private boolean replaceInitialFile;

    /**
     * @return Returns the originalDeviceId.
     */
    public String getOriginalDeviceId() {
        return originalDeviceId;
    }

    /**
     * @param originalDeviceId The originalDeviceId to set.
     */
    public void setOriginalDeviceId(String originalDeviceId) {
        this.originalDeviceId = originalDeviceId;
    }

    /**
     * @return Returns the replaceInitialFile.
     */
    public boolean isReplaceInitialFile() {
        return replaceInitialFile;
    }

    /**
     * @param replaceInitialFile The replaceInitialFile to set.
     */
    public void setReplaceInitialFile(boolean replaceInitialFile) {
        this.replaceInitialFile = replaceInitialFile;
    }

}
