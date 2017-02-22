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

package org.openo.sdno.overlayvpn.composer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.SysEnvironment;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateObject;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.overlayvpn.util.toolkit.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * The manager of template,save the template as map<templateName,template>.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 20, 2017
 */
public class TemplateManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateManager.class);

    private static TemplateManager instance = null;

    private Map<String, Template> templateMap = new HashMap<String, Template>();

    /**
     * The path that saved the template file.
     */
    private static final String FILEPATH = SysEnvironment.getAppRoot() + File.separator + "template";

    private static final String JSON = ".json";

    private TemplateManager() {
        super();
        getMapFromFileReader();
    }

    /**
     * Read template by singleton pattern.<br>
     * 
     * @return The template manager contains all template
     * @since SDNO 0.5
     */
    public static TemplateManager getInstance() {
        if(instance == null) {
            instance = new TemplateManager();
        }
        return instance;
    }

    /**
     * Get template map.<br>
     * 
     * @return The template map
     * @since SDNO 0.5
     */
    public Map<String, Template> getTemplateMap() {
        return templateMap;
    }

    /**
     * Get template by templateName<br>
     * 
     * @param templateName The template name
     * @return The needed template
     * @since SDNO 0.5
     */
    public Template getTemplate(String templateName) {
        return templateMap.get(templateName);
    }

    /**
     * Get the policy in template by templateName and policyName.<br>
     * 
     * @param templateName The template name
     * @param policyName The policy name
     * @return The needed policy in template
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public String getTemplateSpec(String templateName, String policyName) throws ServiceException {
        Template template = getTemplate(templateName);
        if(template == null) {
            LOGGER.warn("The template is null");
            return null;
        }
        LOGGER.info("The template is not null,the template is :" + JsonUtils.toJson(template));
        return template.getPolicy(policyName);
    }

    private void getMapFromFileReader() {
        List<TemplateObject> list = null;
        try {
            list = readAllTemplateFile();
        } catch(ServiceException e) {
            LOGGER.warn("Read TemplateObject list error:" + e);
        }

        if(CollectionUtils.isNotEmpty(list)) {
            for(TemplateObject templateObject : list) {
                String templateName = templateObject.getName();
                templateMap.put(templateName, new Template(templateObject));
            }
        }
    }

    private List<TemplateObject> readAllTemplateFile() throws ServiceException {
        List<TemplateObject> templateObjectList = new ArrayList<>();

        if(!(StringUtils.hasLength(FILEPATH))) {
            return templateObjectList;
        }
        File[] files = FileUtils.getFiles(FILEPATH, JSON);
        if(files.length > 0) {
            for(File file : files) {
                TemplateObject templateObject = JsonUtil.fromJson(FileUtils.readFile(file), TemplateObject.class);
                templateObjectList.add(templateObject);
            }
        }
        LOGGER.info("The template object list is :" + JsonUtils.toJson(templateObjectList));
        return templateObjectList;
    }

}
