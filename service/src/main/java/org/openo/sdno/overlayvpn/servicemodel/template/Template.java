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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.openo.sdno.overlayvpn.composer.ParamConsts;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * The template model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class Template {

    private String name;

    private Map<String, Object> specMap = new HashMap<String, Object>();

    private List<TemplateConnection> connections = new ArrayList<>();

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public Template() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @param templateObject The templateObject contains field to set
     * @since SDNO 0.5
     */
    public Template(TemplateObject templateObject) {
        this.setName(templateObject.getName());
        List<Spec> specList = templateObject.getSpecList();
        if(!CollectionUtils.isEmpty(specList)) {
            for(Spec spec : specList) {
                if(spec.getName().equals(ParamConsts.CONNECTIONS)) {
                    continue;
                } else if(spec.getName().equals(ParamConsts.LOCALCPEINFO)) {
                    specMap.put(spec.getName(), Spec.toSubSpecValueMap(spec));
                } else {
                    specMap.put(spec.getName(), Spec.toValueMap(spec));
                }
            }
        }
        connections.clear();
        if(templateObject.getConnections() != null && templateObject.getConnections().getSpecList() != null
                && !CollectionUtils.isEmpty(templateObject.getConnections().getSpecList())) {
            for(Spec spec : templateObject.getConnections().getSpecList()) {
                connections.add(new TemplateConnection(spec));
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TemplateConnection> getConnections() {
        return connections;
    }

    public void setConnections(List<TemplateConnection> connections) {
        this.connections = connections;
    }

    public String getPolicy(String specName) {
        if(specName.equals(ParamConsts.CONNECTIONS)) {
            return JSONArray.fromObject(this.connections).toString();
        }
        return JSONObject.fromObject(specMap.get(specName)).toString();
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getIpsecPolicy() {
        return (Map<String, String>)specMap.get(ParamConsts.IPSEC_POLICY);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getIkePolicy() {
        return (Map<String, String>)specMap.get(ParamConsts.IKE_POLICY);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getGwPolicy() {
        return (Map<String, String>)specMap.get(ParamConsts.GW_POLICY);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getCloudCpeInfo() {
        return (Map<String, String>)specMap.get(ParamConsts.CLOUDCPEINFO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Map<String, String>> getLocalCpeInfo() {
        return (Map<String, Map<String, String>>)specMap.get(ParamConsts.LOCALCPEINFO);
    }
}
