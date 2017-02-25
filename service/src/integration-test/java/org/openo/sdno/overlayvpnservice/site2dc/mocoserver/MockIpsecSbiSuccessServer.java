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

package org.openo.sdno.overlayvpnservice.site2dc.mocoserver;

import org.openo.sdno.testframework.moco.MocoHttpServer;

/**
 * SbiAdapterSuccessServer class for success test cases. <br>
 * 
 * @author
 * @version SDNO 0.5 June 16, 2016
 */
public class MockIpsecSbiSuccessServer extends MocoHttpServer {

    private static final String CREATE_IPSEC_FILE =
            "src/integration-test/resources/site2dc/moco/ipsec/createipsec.json";

    private static final String DELETE_IPSEC_FILE =
            "src/integration-test/resources/site2dc/moco/ipsec/deleteipsec.json";

    private static final String UPDATE_IPSEC_FILE =
            "src/integration-test/resources/site2dc/moco/ipsec/updateipsec.json";

    private static final String DEPLOY_IPSEC_FILE =
            "src/integration-test/resources/site2dc/moco/ipsec/deployipsec.json";

    private static final String QUERY_IPSEC_FILE = "src/integration-test/resources/site2dc/moco/ipsec/queryipsec.json";

    public MockIpsecSbiSuccessServer() {
        super(12306);
    }

    @Override
    public void addRequestResponsePairs() {

        this.addRequestResponsePair(CREATE_IPSEC_FILE);

        this.addRequestResponsePair(DELETE_IPSEC_FILE);

        this.addRequestResponsePair(UPDATE_IPSEC_FILE);

        this.addRequestResponsePair(DEPLOY_IPSEC_FILE);

        this.addRequestResponsePair(QUERY_IPSEC_FILE);
    }
}
