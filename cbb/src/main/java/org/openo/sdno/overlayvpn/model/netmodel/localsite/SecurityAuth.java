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

package org.openo.sdno.overlayvpn.model.netmodel.localsite;

import org.openo.sdno.overlayvpn.verify.annotation.AString;

/**
 * Class of Security Authentication.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 6, 2016
 */
public class SecurityAuth {

    /**
     * Authentication type
     */
    @AString(require = true, scope = "None,Normal,Creating,Deleting,Updating")
    private String authType;

    /**
     * Template Id
     */
    private String templateId;

    /**
     * @return Returns the authType.
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * @param authType The authType to set.
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    /**
     * @return Returns the templateId.
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId The templateId to set.
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

}
