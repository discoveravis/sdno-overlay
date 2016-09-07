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

package org.openo.sdno.overlayvpn.util.exception;

import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.resource.ResourceUtil;

/**
 * Define General ErrorResultRsp when filling OverlayVpn.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public class FillOverlayVpnResRsp {

    private FillOverlayVpnResRsp() {

    }

    /**
     * Common Configuration not exist ResultRsp.<br/>
     * 
     * @param cfgkey Configuration key
     * @return ResultRsp Object
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public static ResultRsp commConfigNotExist(String cfgkey) {
        String msg = ResourceUtil.getMessage("compositevpn.commcfg.not_exist.reason").replace("{param}", cfgkey);
        return new ResultRsp(ErrorCode.COMMON_CONFIG_NOT_EXIST,
                ResourceUtil.getMessage("compositevpn.commcfg.not_exist.desc"), msg, msg, ResourceUtil.getMessage(
                        "compositevpn.commcfg.not_exist.advice").replace("{param}", cfgkey));
    }

    /**
     * Inventory Resource not exist ResultRsp.<br/>
     * 
     * @param resName resource name
     * @param resDesc resource description
     * @return ResultRsp Object
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public static ResultRsp inventoryResNotExist(String resName, String resDesc) {
        String msg =
                ResourceUtil.getMessage("compositevpn.resource.not_exist.reason").replace("{resname}", resName)
                        .replace("{resdesc}", resDesc);

        return new ResultRsp(ErrorCode.OVERLAYVPN_RESOURCE_NOT_EXIST,
                ResourceUtil.getMessage("compositevpn.resource.not_exist.desc"), msg, msg,
                ResourceUtil.getMessage("compositevpn.resource.not_exist.advice"));

    }

    /**
     * Resource allocate Failed ResultRsp.<br/>
     * 
     * @param resType resource type
     * @param num index of error message
     * @return ResultRsp Object
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    public static ResultRsp allocResFailed(String resType, int num) {
        String msg =
                ResourceUtil.getMessage("cloudvpn.resource.alloc_failed.reason").replace("{restype}", resType)
                        .replace("{num}", String.valueOf(num));

        return new ResultRsp(ErrorCode.RESOURCE_ALLOC_FAILED,
                ResourceUtil.getMessage("cloudvpn.resource.alloc_failed.desc"), msg, msg,
                ResourceUtil.getMessage("compositevpn.resource.not_exist.advice"));
    }

}
