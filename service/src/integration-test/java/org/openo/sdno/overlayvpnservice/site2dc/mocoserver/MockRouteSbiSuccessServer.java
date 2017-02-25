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
public class MockRouteSbiSuccessServer extends MocoHttpServer {

    private static final String DELETE_NQA_FILE = "src/integration-test/resources/site2dc/moco/route/deletenqa.json";

    private static final String DELETE_POLICY_ROUTE_FILE =
            "src/integration-test/resources/site2dc/moco/route/deletepolicyroute.json";

    private static final String CREATE_ROUTE_FILE =
            "src/integration-test/resources/site2dc/moco/route/createroute.json";

    private static final String DELETE_ROUTE_FILE =
            "src/integration-test/resources/site2dc/moco/route/deleteroute.json";

    private static final String UPDATE_ROUTE_FILE =
            "src/integration-test/resources/site2dc/moco/route/updateroute.json";

    private static final String DEPLOY_ROUTE_FILE =
            "src/integration-test/resources/site2dc/moco/route/deployroute.json";

    private static final String QUERY_ROUTE_FILE = "src/integration-test/resources/site2dc/moco/route/queryroute.json";

    public MockRouteSbiSuccessServer() {
        super(12307);
    }

    @Override
    public void addRequestResponsePairs() {

        // nqa mock
        this.addRequestResponsePair(DELETE_NQA_FILE);

        // policy route mock
        this.addRequestResponsePair(DELETE_POLICY_ROUTE_FILE);

        // static route mock
        this.addRequestResponsePair(CREATE_ROUTE_FILE);

        this.addRequestResponsePair(DELETE_ROUTE_FILE);

        this.addRequestResponsePair(UPDATE_ROUTE_FILE);

        this.addRequestResponsePair(DEPLOY_ROUTE_FILE);

        this.addRequestResponsePair(QUERY_ROUTE_FILE);
    }

}
