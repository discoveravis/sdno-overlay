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

package org.openo.sdno.overlayvpn.res;

import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOResType;
import org.openo.sdno.overlayvpn.model.uuid.AbstUuidModel;

/**
 * Class of AllocId Model.<br>
 * 
 * @author
 * @version SDNO 0.5 June 5, 2016
 */
@MOResType(infoModelName = "allocidresource")
public class AllocIdResource extends AbstUuidModel {

    private Long idres;

    private String poolname;

    private String userlabel;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public AllocIdResource() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @param idres id resource
     * @param poolname pool name
     * @param userlabel user label
     * @since SDNO 0.5
     */
    public AllocIdResource(Long idres, String poolname, String userlabel) {
        super();
        this.idres = idres;
        this.poolname = poolname;
        this.userlabel = userlabel;
    }

    public Long getIdres() {
        return idres;
    }

    public void setIdres(Long idres) {
        this.idres = idres;
    }

    public String getPoolname() {
        return poolname;
    }

    public void setPoolname(String poolname) {
        this.poolname = poolname;
    }

    public String getUserlabel() {
        return userlabel;
    }

    public void setUserlabel(String userlabel) {
        this.userlabel = userlabel;
    }
}
