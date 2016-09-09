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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.ITransactionContext;
import org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util.TransactionMgr;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.result.ResultRsp;

import mockit.Mock;
import mockit.MockUp;

public class PuerInvServicesImplTest {

    @Test(expected = Exception.class)
    public void testAddException() throws ServiceException {

        PuerInvServicesImpl<Gateway> servImpl = new PuerInvServicesImpl<Gateway>();
        List<Gateway> moList = new ArrayList<Gateway>();
        Gateway gway = new Gateway();
        gway.setUuid("uuid");
        moList.add(gway);
        servImpl.add(moList);
        // assertTrue(resp.isSuccess());
    }

    @Test
    public void testAddNormal() throws ServiceException {
        new MockUp<TransactionMgr>() {

            @Mock
            public ResultRsp fire(ITransactionContext context) {
                ResultRsp resp = new ResultRsp();
                return resp.SUCCESS;
            }

        };
        PuerInvServicesImpl<Gateway> servImpl = new PuerInvServicesImpl<Gateway>();
        List<Gateway> moList = new ArrayList<Gateway>();
        Gateway gway = new Gateway();
        gway.setUuid("uuid");
        moList.add(gway);
        ResultRsp resp = servImpl.add(moList);
        assertTrue(resp.isSuccess());
    }

}
