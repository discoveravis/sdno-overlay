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

package org.openo.sdno.overlayvpnservice.site2dc.util;

import java.io.File;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.sdno.overlayvpn.brs.model.PopMO;
import org.openo.sdno.overlayvpn.util.json.JsonUtils;
import org.openo.sdno.testframework.restclient.HttpRestClient;
import org.openo.sdno.testframework.util.file.FileUtils;

public class PopUtil {

    private static final String POP_BODY_FILE = "src/integration-test/resources/site2dc/pop/pop.json";

    private static final HttpRestClient restClient = new HttpRestClient();

    private PopUtil() {

    }

    public static void addPop() throws ServiceException {
        String content = FileUtils.readFromJson(new File(POP_BODY_FILE));
        PopMO pop = JsonUtils.fromJson(content, PopMO.class);

        RestfulParametes restParams = new RestfulParametes();
        restParams.putHttpContextHeader("Content-Type", "application/json");
        restParams.setRawData(pop.toJsonBody());

        restClient.post("/openoapi/sdnobrs/v1/pops", restParams);
    }

    public static void deletePop() throws ServiceException {
        RestfulParametes restParams = new RestfulParametes();
        restParams.putHttpContextHeader("Content-Type", "application/json");
        restClient.delete("/openoapi/sdnobrs/v1/pops/test_uuid", restParams);
    }
}
