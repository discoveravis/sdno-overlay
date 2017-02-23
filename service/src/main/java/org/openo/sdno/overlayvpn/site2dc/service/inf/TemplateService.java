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

package org.openo.sdno.overlayvpn.site2dc.service.inf;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.service.IService;

/**
 * The interface to query template,extends IService.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public interface TemplateService extends IService {

    /**
     * Query template policy through template name and policy name.<br>
     * 
     * @param templateName The template name
     * @param policyName The policy name
     * @return The policy in template queried by templateName and policyName
     * @since SDNO 0.5
     */
    String getTemplateSpec(String templateName, String policyName) throws ServiceException;
}
