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

package org.openo.sdno.overlayvpn.util.exception;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.HttpCode;
import org.openo.sdno.framework.container.util.SysEnvironment;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.result.SvcExcptUtil;
import org.openo.sdno.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Throw some OverlayVpn Exceptions.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public class ThrowOverlayVpnExcpt {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThrowOverlayVpnExcpt.class);

    private ThrowOverlayVpnExcpt() {

    }

    /**
     * Check weather ResultRsp is success and throws Exception.<br>
     * 
     * @param result ResultRsp need to check
     * @throws ServiceException ServiceException throws if result is not success
     * @since SDNO 0.5
     */
    public static void checkRspThrowException(ResultRsp<?> result) throws ServiceException {
        if(result == null) {
            LOGGER.error("operation failed! ErrorCode = " + ErrorCode.OVERLAYVPN_FAILED);
            throw new ServiceException(ErrorCode.OVERLAYVPN_FAILED, HttpCode.ERR_FAILED);
        }
        if(!result.isSuccess()) {
            LOGGER.error("operation not success");
            SvcExcptUtil.throwSvcExptionByResultRsp(result);
        }
    }

    /**
     * Throw BadReq Exception.<br>
     * 
     * @param resName resource name
     * @param resDesc resource description
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwResNotExistAsBadReq(String resName, String resDesc) throws ServiceException {
        String packName = "";
        if(!StringUtils.hasLength(resName)) {
            packName = "[Name]";
        } else {
            packName = "[" + resName + "]";
        }

        // make error message
        LOGGER.error("resource not exist, " + packName + resDesc);
        String desc = ResourceUtil.getMessage("resource.not_exist.desc");
        String message = packName + resDesc + " " + ResourceUtil.getMessage("resource.not_exist.reason");
        String advice = ResourceUtil.getMessage("resource.not_exist.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_RESOURCE_NOT_EXIST, desc, message, message,
                advice);
    }

    /**
     * Throw NotFound Exception.<br>
     * 
     * @param resName resource name
     * @param resDesc resource description
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwResNotExistAsNotFound(String resName, String resDesc) throws ServiceException {
        String packName = "";
        if(!StringUtils.hasLength(resName)) {
            packName = "[Name]";
        } else {
            packName = "[" + resName + "]";
        }
        // make error message
        LOGGER.error("resource not exist, " + packName + resDesc);
        String desc = ResourceUtil.getMessage("resource.not_exist.desc");
        String message = packName + resDesc + " " + ResourceUtil.getMessage("resource.not_exist.reason");
        String advice = ResourceUtil.getMessage("resource.not_exist.advice");
        SvcExcptUtil.throwNotFoundSvcExptionWithInfo(ErrorCode.OVERLAYVPN_RESOURCE_NOT_EXIST, desc, message, message,
                advice);
    }

    /**
     * Throw Tenant id invalid Exception.<br>
     * 
     * @param exptTenantId exception tenant id
     * @param realTenantId real tenant id
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwTenantIdInvalid(String exptTenantId, String realTenantId) throws ServiceException {
        if(exptTenantId.equals(realTenantId)) {
            return;
        }

        // make error message
        LOGGER.error("tenantIds are not the same, expt = " + exptTenantId + ", real = " + realTenantId);
        String desc = ResourceUtil.getMessage("tenantid.invalid.desc");
        String message = ResourceUtil.getMessage("tenantid.invalid.reason");
        String advice = ResourceUtil.getMessage("tenantid.invalid.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_TENANT_INVALID, desc, message, message, advice);
    }

    /**
     * Throw Tenant id missing Exception.<br>
     * 
     * @param exptTenantId exception tenant id
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwTenantIdMissing(String exptTenantId) throws ServiceException {
        // make error message
        LOGGER.error("tenantIds data missing in operation, expt = " + exptTenantId);
        String desc = ResourceUtil.getMessage("tenantid.data.missing.desc");
        String message = ResourceUtil.getMessage("tenantid.data.missing.reason");
        String advice = ResourceUtil.getMessage("tenantid.data.missing.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_TENANT_INVALID, desc, message, message, advice);
    }

    /**
     * Throw Parameter invalid Exception.<br>
     * 
     * @param paraName parameter name
     * @param parmaterPath parameter path
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwParmaterInvalid(String paraName, String parmaterPath) throws ServiceException {
        String packName = "";
        if(!StringUtils.hasLength(paraName)) {
            packName = "[Name]";
        } else {
            packName = "[" + paraName + "]";
        }

        String desc = ResourceUtil.getMessage("parameter.invalid.desc");
        String message = packName + parmaterPath + " " + ResourceUtil.getMessage("parameter.invalid.reason");
        String advice = ResourceUtil.getMessage("parameter.invalid.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                advice);
    }

    /**
     * Throw Update Epg invalid Exception.<br>
     * 
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwUpdateEpgInvalid() throws ServiceException {
        String desc = ResourceUtil.getMessage("epg.update.paramoutrange.desc");
        String message = ResourceUtil.getMessage("epg.update.paramoutrange.reason");
        String advice = ResourceUtil.getMessage("epg.update.paramoutrange.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                advice);
    }

    /**
     * Throw Having MappingPolicy Exception.<br>
     * mappingPolicyId should not have mappingPolicyId when Gre is used
     * 
     * @param connectionId connection id
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwHavingPolicyIdAsParmaterInvalid(String connectionId) throws ServiceException {
        String desc = ResourceUtil.getMessage("gre.mappingpolicy.having.desc");
        String message =
                ResourceUtil.getMessage("gre.mappingpolicy.having.reason").replace("{connectionId}", connectionId);
        String advice = ResourceUtil.getMessage("gre.mappingpolicy.having.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                advice);
    }

    /**
     * Throw Having MappingPolicy Exception.<br>
     * mappingPolicyId should have if ipsec、vxlan、gre_over_ipsec are used
     * 
     * @param connectionId connection id
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwNotHavingPolicyIdAsParmaterInvalid(String connectionId) throws ServiceException {
        String desc = ResourceUtil.getMessage("connection.mappingpolicy.nohaving.desc");
        String message = ResourceUtil.getMessage("connection.mappingpolicy.nohaving.reason").replace("{connectionId}",
                connectionId);
        String advice = ResourceUtil.getMessage("connection.mappingpolicy.nohaving.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                advice);
    }

    /**
     * Throw Port Conflict Exception.<br>
     * 
     * @param neUuid network element id
     * @param portUuid port id
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwPortConflict(String neUuid, String portUuid) throws ServiceException {
        String desc = ResourceUtil.getMessage("svc.port.conflict.desc");
        String reason = ResourceUtil.getMessage("svc.port.conflict.reason");
        String detail = reason + " " + ResourceUtil.getMessage("svc.port.conflict").replace("{neuuid}", neUuid)
                .replace("{portuuid}", portUuid);
        String advice = ResourceUtil.getMessage("svc.port.conflict.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, reason, detail,
                advice);
    }

    /**
     * Throw VLAN Conflict Exception.<br>
     * 
     * @param neUuid network element id
     * @param portUuid port id
     * @param vlan vlan value
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwVlanConflict(String neUuid, String portUuid, String vlan) throws ServiceException {
        String desc = ResourceUtil.getMessage("svc.vlan.conflict.desc");
        String reason = ResourceUtil.getMessage("svc.vlan.conflict.reason");
        String detail = reason + " " + ResourceUtil.getMessage("svc.vlan.conflict").replace("{neuuid}", neUuid)
                .replace("{portuuid}", portUuid).replace("{vlan}", vlan);
        String advice = ResourceUtil.getMessage("svc.vlan.conflict.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, reason, detail,
                advice);
    }

    /**
     * Throw TP already exist Exception.<br>
     * 
     * @param neUuid network element id
     * @param portUuid port id
     * @param tpUuid TP id
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwTpAlreadyExists(String neUuid, String portUuid, String tpUuid) throws ServiceException {
        String desc = ResourceUtil.getMessage("svc.tp.already_exist.desc");
        String reason = ResourceUtil.getMessage("svc.tp.already_exist.reason");
        String detail = reason + " " + ResourceUtil.getMessage("svc.tp.already_exist").replace("{neuuid}", neUuid)
                .replace("{portuuid}", portUuid).replace("{tpuuid}", tpUuid);
        String advice = ResourceUtil.getMessage("svc.tp.already_exist.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, reason, detail,
                advice);
    }

    /**
     * Throw Resource is used Exception.<br>
     * 
     * @param resource resource id
     * @param user user id
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwResourceIsInUsedExpt(String resource, String user) throws ServiceException {

        String desc = ResourceUtil.getMessage("resource.in.using.desc");

        String message = ResourceUtil.getMessage("resource.in.using.reason").replace("{resourceid}", resource)
                .replace("{userid}", user);
        String advice = ResourceUtil.getMessage("resource.in.using.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                advice);
    }

    /**
     * Throw id Description Exception.<br>
     * 
     * @param id key id
     * @param desc Description
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwExceptionWithIdDesc(String id, String desc) throws ServiceException {
        SvcExcptUtil.throwInnerErrSvcExptionWithInfo(id, desc, null, null, null);
    }

    /**
     * Throw OverlayVpn Data Missing Exception.<br>
     * 
     * @param resource resource name
     * @param resDesc resource description
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwOverlayVpnDataMissExpt(String resource, String resDesc) throws ServiceException {
        String packName = "";
        if(!StringUtils.hasLength(resource)) {
            packName = "[Name]";
        } else {
            packName = "[" + resource + "]";
        }
        ResultRsp<String> retRsp = new ResultRsp<>(ErrorCode.OVERLAYVPNSVC_CONN_NOT_COMPLETE);
        retRsp.setDescArg(ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.desc"));
        if(CommConst.LANGUAGE_ZH_CN.equals(SysEnvironment.getLanguage())) {
            String msg = packName + ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.reason");
            retRsp.setReasonArg(msg);
            retRsp.setDetailArg(msg);
            retRsp.setDetailArg(ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.advice") + "OverlayVpnService");
        } else {
            String msg = packName + " " + ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.reason");
            retRsp.setReasonArg(msg);
            retRsp.setDetailArg(msg);
            retRsp.setDetailArg(ResourceUtil.getMessage("cloudvpnsvc.data.not_complete.advice") + " OverlayVpnService");
        }
        SvcExcptUtil.throwSvcExptionByResultRsp(retRsp);
    }

    /**
     * Throw WanSubInf Query Failed Exception.<br>
     * 
     * @param neId network element id
     * @param type type
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwQueryWanSubInfExpt(String neId, String type) throws ServiceException {
        String desc = ResourceUtil.getMessage("vpn.subinterface.query_exception.desc");
        String message = ResourceUtil.getMessage("vpn.subinterface.query_exception.reason").replace("{neId}", neId)
                .replace("{type}", type);
        String advice = ResourceUtil.getMessage("vpn.subinterface.query_exception.advice").replace("{type}", type);
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                advice);
    }

    /**
     * Throw epg belongs to different connection Exception.<br>
     * 
     * @param connectionId1 connection1 id
     * @param connectionId2 connection2 id
     * @throws ServiceException ServiceException throws
     * @since SDNO 0.5
     */
    public static void throwConnectionIdDisaccord(String connectionId1, String connectionId2) throws ServiceException {
        String desc = ResourceUtil.getMessage("compositevpn.batchcreateepg.dis_accord.desc");
        String message = ResourceUtil.getMessage("compositevpn.batchcreateepg.dis_accord.reason")
                .replace("{connectionId1}", connectionId1).replace("{connectionId2}", connectionId2);
        String advice = ResourceUtil.getMessage("compositevpn.batchcreateepg.dis_accord.advice");
        SvcExcptUtil.throwBadReqSvcExptionWithInfo(ErrorCode.OVERLAYVPN_PARAMETER_INVALID, desc, message, message,
                advice);
    }

}
