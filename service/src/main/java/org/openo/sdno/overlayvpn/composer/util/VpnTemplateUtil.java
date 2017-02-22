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

package org.openo.sdno.overlayvpn.composer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.composer.TemplateManager;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiNetConnection;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpn;
import org.openo.sdno.overlayvpn.model.v2.overlay.NbiVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseSite;
import org.openo.sdno.overlayvpn.servicemodel.base.VpnSite;
import org.openo.sdno.overlayvpn.servicemodel.db.InternalVpnConnection;
import org.openo.sdno.overlayvpn.servicemodel.enums.Reliability;
import org.openo.sdno.overlayvpn.servicemodel.template.Sap;
import org.openo.sdno.overlayvpn.servicemodel.template.Template;
import org.openo.sdno.overlayvpn.servicemodel.template.TemplateConnection;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class to deal with template model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 23, 2017
 */
public class VpnTemplateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpnTemplateUtil.class);

    private VpnTemplateUtil() {
        super();
    }

    /**
     * Get template list through vpn connection and site.<br>
     * 
     * @param connection The vpn connection
     * @param aEndSite The a end site
     * @param zEndSite The z end site
     * @return The template list
     * @throws ServiceException when the both site is not vpn site or reliability type invalid or
     *             query vpn failed
     * @since SDNO 0.5
     */
    public static Template[] getTemplate(NbiVpnConnection connection, BaseSite aEndSite, BaseSite zEndSite)
            throws ServiceException {
        if((!(aEndSite instanceof VpnSite)) && (!(zEndSite instanceof VpnSite))) {
            LOGGER.error("Connection.getTemplate.error,both site is not vpn site,model invalid.");
            throw new InnerErrorServiceException(
                    "Connection.getTemplate.error,both site is not vpn site,model invalid.");
        }
        String aEndModel = (aEndSite instanceof VpnSite) ? ((VpnSite)aEndSite).getReliability()
                : ((VpnSite)zEndSite).getReliability();
        String zEndModel = (zEndSite instanceof VpnSite) ? ((VpnSite)zEndSite).getReliability()
                : ((VpnSite)aEndSite).getReliability();
        Reliability aEndReliability = Reliability.getReliability(aEndModel);
        Reliability zEndReliability = Reliability.getReliability(zEndModel);

        NbiVpn vpn = new BaseDao<NbiVpn>().query(connection.getVpnId(), NbiVpn.class);
        String descriptor = vpn.getVpnDescriptor();
        Template[] templates = new Template[2];
        templates[0] = TemplateManager.getInstance().getTemplate(aEndReliability.getTemplate(descriptor));
        templates[1] = TemplateManager.getInstance().getTemplate(zEndReliability.getTemplate(descriptor));
        return templates;
    }

    /**
     * Get template through internal vpn connection.<br>
     * 
     * @param connection The internal vpn connection
     * @return The template
     * @since SDNO 0.5
     */
    public static Template getTemplate(InternalVpnConnection connection) {
        return TemplateManager.getInstance().getTemplate(connection.getVpnTemplateName());
    }

    /**
     * Get connection template through the template name and connection.<br>
     * 
     * @param templateName The template name
     * @param netConnection The vpn connection
     * @return The connection template
     * @throws ServiceException when no connection template defined for netConnection or template is
     *             null
     * @since SDNO 0.5
     */
    public static TemplateConnection getConnectionTemplate(String templateName, NbiNetConnection netConnection)
            throws ServiceException {
        List<TemplateConnection> templateConnections = getConnectionTemplate(templateName, netConnection.getSrcNeRole(),
                netConnection.getDestNeRole(), netConnection.getType());
        if(CollectionUtils.isNotEmpty(templateConnections)) {
            return templateConnections.get(0);
        }
        LOGGER.error("VpnTemplate.get.error,no connection template defined for netConnection :" + netConnection.getId()
                + " in template " + templateName);
        throw new InnerErrorServiceException("VpnTemplate.get.error,no connection template defined for netConnection :"
                + netConnection.getId() + " in template " + templateName);
    }

    /**
     * Get connection template through the template name,source neRole,destination neRole and
     * technology.<br>
     * 
     * @param templateName The template name
     * @param srcRole The source neRole
     * @param destRole The destination neRole
     * @param technology The technology
     * @return The connection template
     * @throws ServiceException when template is null
     * @since SDNO 0.5
     */
    public static List<TemplateConnection> getConnectionTemplate(String templateName, String srcRole, String destRole,
            String technology) throws ServiceException {
        Template template = TemplateManager.getInstance().getTemplate(templateName);
        if(template == null) {
            LOGGER.error("VpnTemplate.getConnection.error,template is null.templateName : " + templateName);
            throw new InnerErrorServiceException(
                    "VpnTemplate.getConnection.error,template is null.templateName : " + templateName);
        }
        List<TemplateConnection> templateConnections = template.getConnections();
        LOGGER.info("The template connection list is :" + JsonUtils.toJson(templateConnections));

        List<TemplateConnection> result = new ArrayList<>();
        for(TemplateConnection templateConnection : templateConnections) {
            List<Sap> saps = templateConnection.getSaps();
            LOGGER.info("The template connection sap list is :" + JsonUtils.toJson(saps));

            if(CollectionUtils.isEmpty(saps) || saps.size() != 2 || (StringUtils.isNotEmpty(technology)
                    && !technology.equalsIgnoreCase(templateConnection.getTechnology()))) {
                continue;
            }
            if(srcRole.equalsIgnoreCase(saps.get(0).getType()) && destRole.equalsIgnoreCase(saps.get(1).getType())) {
                result.add(templateConnection);
            } else if(srcRole.equalsIgnoreCase(saps.get(1).getType())
                    && destRole.equalsIgnoreCase(saps.get(0).getType())) {
                Sap sap = saps.get(1);
                saps.set(1, saps.get(0));
                saps.set(0, sap);
                result.add(templateConnection);
            }
        }
        return result;
    }

    /**
     * Parse the template object.<br>
     * 
     * @param templateKey The template key
     * @return The map of template value
     * @since SDNO 0.5
     */
    public static Map<String, String> parseTemplateObj(String templateKey) {
        Map<String, String> data = new HashMap<String, String>();
        if(StringUtils.isEmpty(templateKey)) {
            return data;
        }
        String regx = "[^\\[,]+=[^\\],]+";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(templateKey);
        while(matcher.find()) {
            String subStr = matcher.group();
            int idx = StringUtils.indexOf(subStr, '=');
            data.put(StringUtils.substring(subStr, 0, idx), StringUtils.substring(subStr, idx + 1));
        }
        return data;
    }
}
