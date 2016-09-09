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

package org.openo.sdno.overlayvpn.util.dbresultprocess;

import org.openo.sdno.framework.container.util.SysEnvironment;
import org.openo.sdno.overlayvpn.consts.CommConst;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.resource.ResourceUtil;

/**
 * Fill ResultRsp of Database Operation Result.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 2, 2016
 */
public class FillDbResultRsp {

    private FillDbResultRsp() {
    }

    /**
     * Fill in Query Failed ResultRsp.<br>
     * 
     * @param rsp ResultRsp Object need to fill in
     * @param msg error message
     * @since SDNO 0.5
     */
    public static void fillQueryResultRsp(ResultRsp<?> rsp, String msg) {

        if(null == rsp) {
            return;
        }

        fillResultRsp(rsp, msg, "database.operation.query.fail");
    }

    /**
     * Fill in Insert Failed ResultRsp.<br>
     * 
     * @param rsp ResultRsp Object need to fill in
     * @param msg error message
     * @since SDNO 0.5
     */
    public static void fillInsertResultRsp(ResultRsp<?> rsp, String msg) {

        if(null == rsp) {
            return;
        }

        fillResultRsp(rsp, msg, "database.operation.insert.fail");
    }

    /**
     * Fill in Delete Failed Operation.<br>
     * 
     * @param rsp ResultRsp Object need to fill in
     * @param msg error message
     * @since SDNO 0.5
     */
    public static void fillDeleteResultRsp(ResultRsp<?> rsp, String msg) {

        if(null == rsp) {
            return;
        }

        fillResultRsp(rsp, msg, "database.operation.delete.fail");
    }

    /**
     * Fill in Update Failed Operation.<br>
     * 
     * @param rsp ResultRsp Object need to fill in
     * @param msg error message
     * @since SDNO 0.5
     */
    public static void fillUpdateResultRsp(ResultRsp<?> rsp, String msg) {

        if(null == rsp) {
            return;
        }

        fillResultRsp(rsp, msg, "database.operation.update.fail");
    }

    private static void fillResultRsp(ResultRsp<?> rsp, String msg, String operationMsg) {

        String errMsg = (null == msg) ? "" : msg;

        // Need to fill both Chinese and English Error Message
        rsp.setDescArg(ResourceUtil.getMessage("database.operation.description"));

        if(CommConst.LANGUAGE_ZH_CN.equals(SysEnvironment.getLanguage())) {
            errMsg += ResourceUtil.getMessage(operationMsg) + ResourceUtil.getMessage("database.operation.reason");
        } else {
            errMsg +=
                    " " + ResourceUtil.getMessage(operationMsg) + " "
                            + ResourceUtil.getMessage("database.operation.reason");
        }
        rsp.setReasonArg(errMsg);
        rsp.setDetailArg(errMsg);
        rsp.setAdviceArg(ResourceUtil.getMessage("database.operation.advice"));
    }

}
