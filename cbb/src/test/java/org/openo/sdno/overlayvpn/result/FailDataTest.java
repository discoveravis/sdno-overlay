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

package org.openo.sdno.overlayvpn.result;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;

public class FailDataTest {

    @Test
    public void testFailDataInstance() {
        Gateway gw = new Gateway();
        gw.setName("gateway123");
        FailData<Gateway> failData = new FailData<Gateway>("errorcode123", "errormessage123", gw);

        String errorcode = failData.getErrcode();
        assertEquals("errorcode123", errorcode);

        String errorMsg = failData.getErrmsg();
        assertEquals("errormessage123", errorMsg);

        String name = failData.getData().getName();
        assertEquals("gateway123", name);

    }

}
