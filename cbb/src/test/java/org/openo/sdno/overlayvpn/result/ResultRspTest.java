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

package org.openo.sdno.overlayvpn.result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ExceptionArgs;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;

public class ResultRspTest {

    @Test
    public void testResultRspInstance() {
        ResultRsp<Gateway> resRsp = new ResultRsp<Gateway>();

        ResultRsp resRsp1 = new ResultRsp("10");
        assertEquals("10", resRsp1.getErrorCode());

        ResultRsp resRsp2 = new ResultRsp("10", "descArg", "reasonArg", "detailArg", "adviceArg");
        assertEquals("10", resRsp2.getErrorCode());
        assertEquals("descArg", resRsp2.getDescArg());
        assertEquals("reasonArg", resRsp2.getReasonArg());
        assertEquals("detailArg", resRsp2.getDetailArg());
        assertEquals("adviceArg", resRsp2.getAdviceArg());

        Gateway gw = new Gateway();
        gw.setNeId("10");
        ResultRsp resRsp3 = new ResultRsp("10", gw);
        assertEquals("10", resRsp3.getErrorCode());
        Gateway gw1 = new Gateway();
        gw1 = (Gateway)resRsp3.getData();
        assertEquals("10", gw1.getNeId());

        ResultRsp resRsp4 = new ResultRsp();
        ExceptionArgs excptArgs = new ExceptionArgs();
        excptArgs = resRsp4.buidlExceptionArgs();

        ResultRsp resRsp5 = new ResultRsp();
        resRsp5.setErrorCode("10");
        assertEquals("10", resRsp5.getErrorCode());

        resRsp5.setHttpCode(200);
        assertEquals(200, resRsp5.getHttpCode());

        resRsp5.setMessage("Message");
        assertEquals("Message", resRsp5.getMessage());
        excptArgs = resRsp5.buidlExceptionArgs();

        resRsp5.setMessage("Message ");
        assertEquals("Message ", resRsp5.getMessage());
        excptArgs = resRsp5.buidlExceptionArgs();

        resRsp5.setMessage("Message!");
        assertEquals("Message!", resRsp5.getMessage());
        excptArgs = resRsp5.buidlExceptionArgs();

        resRsp5.setMessage("Message,");
        assertEquals("Message,", resRsp5.getMessage());
        excptArgs = resRsp5.buidlExceptionArgs();

        resRsp5.setMessage("Message.");
        assertEquals("Message.", resRsp5.getMessage());
        excptArgs = resRsp5.buidlExceptionArgs();

        assertFalse(resRsp5.isValid());

        resRsp5.setData(null);
        assertFalse(resRsp5.isValid());

        resRsp5.setErrorCode("overlayvpn.operation.success");
        assertFalse(resRsp5.isValid());

        resRsp5.setData(null);
        assertFalse(resRsp5.isValid());
    }

}
