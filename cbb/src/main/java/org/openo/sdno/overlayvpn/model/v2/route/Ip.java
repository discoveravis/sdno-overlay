/*
 * Copyright (c) 2017, Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.model.v2.route;

import org.openo.sdno.overlayvpn.model.v2.uuid.UuidModel;
import org.openo.sdno.overlayvpn.verify.annotation.AInt;
import org.openo.sdno.overlayvpn.verify.annotation.AIp;
import org.openo.sdno.overlayvpn.verify.annotation.AUuid;

/**
 * Ip model.<br/>
 * 
 * @author
 * @version SDNO 0.5 Jan 6, 2017
 */
public class Ip extends UuidModel {

    @AIp
    private String ipv4;

    @AIp
    private String ipv6;

    @AInt(min = 0, max = 32)
    private String ipMask;

    @AInt(min = 0, max = 128)
    private String prefixLength;

    @AUuid
    private String neId;

    @AUuid
    private String deviceId;

    @AUuid
    private String routeId;
}
