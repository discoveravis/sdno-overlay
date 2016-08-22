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

package org.openo.sdno.overlayvpn.inventory.sdk.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The definition of relative relation about puer MO. <br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class RelationPuerMO {

    @JsonProperty(value = "src_uuid")
    private String srcUuid;

    @JsonProperty(value = "dst_uuid")
    private String dstUuid;

    @JsonProperty(value = "dst_type")
    private String dstType;

    private String relation;

    private String servicetype;

    /**
     * Constructor<br/>
     * <p>
     * </p>
     * 
     * @since SDNO 0.5
     */
    public RelationPuerMO() {
        // empty construction
    }

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     * @param srcUuid The source uuid
     * @param dstUuid The destination uuid
     * @param dstType The destination type
     * @param relation The relation
     * @param servicetype The service type
     */
    private RelationPuerMO(String srcUuid, String dstUuid, String dstType, String relation, String servicetype) {
        this.srcUuid = srcUuid;
        this.dstUuid = dstUuid;
        this.dstType = dstType;
        this.relation = relation;
        this.servicetype = servicetype;
    }

    public String getSrcUuid() {
        return srcUuid;
    }

    @JsonProperty(value = "src_id")
    public void setSrcUuid(String srcuuid) {
        this.srcUuid = srcuuid;
    }

    public String getDstUuid() {
        return dstUuid;
    }

    @JsonProperty(value = "dst_id")
    public void setDstUuid(String dstuuid) {
        this.dstUuid = dstuuid;
    }

    public String getDstType() {
        return dstType;
    }

    public void setDstType(String dsttype) {
        this.dstType = dsttype;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    /**
     * It is used to convert from RelationMO to RelationPuerMO. <br/>
     * 
     * @param relationList The RelationMO list
     * @return The RelationPuerMO list
     * @since SDNO 0.5
     */
    public static List<RelationPuerMO> convert(List<RelationMO> relationList) {
        List<RelationPuerMO> relationPuerMOList = new ArrayList<RelationPuerMO>();

        for(RelationMO mo : relationList) {
            relationPuerMOList.add(new RelationPuerMO(mo.getSrcUUID(), mo.getDstUUID(), mo.getDstResType(), mo
                    .getRelation(), mo.getServiceType()));
        }

        return relationPuerMOList;
    }
}
