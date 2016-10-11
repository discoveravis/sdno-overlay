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

package org.openo.sdno.overlayvpn.inventory.sdk.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MORelationField;
import org.openo.sdno.overlayvpn.inventory.sdk.util.MOModelProcessor;
import org.openo.sdno.util.reflect.JavaEntityUtil;

/**
 * The definition of relative relation about MO. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class RelationMO {

    private final RelationType srcRelation = new RelationType();

    private final RelationType dstRelation = new RelationType();

    /**
     * The type are aggregation, composition and association.
     */
    private String relation;

    private static final String DST_UUID = "dst_uuid";

    private static final String SRC_UUID = "src_uuid";

    private static final String SERVICE_TYPE = "servicetype";

    private static final String DST_TYPE = "dst_type";

    private static final String RELATION_TYPE = "relation";

    private String serviceType;

    public static final String AGGREGATION_TYPE = "aggregation";

    public static final String COMPOSITION_TYPE = "composition";

    public static final String ASSOCIATION_TYPE = "association";

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public RelationMO() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param srcMo The source MO
     * @param dstMo The destination MO
     * @param srcid The source UUID
     * @param dstid The destination UUID
     * @param relation The relation, the type are aggregation, composition and association.
     * @param serviceType The service type
     */
    public RelationMO(Class srcMo, Class dstMo, String srcid, String dstid, String relation, String serviceType)
            throws InnerErrorServiceException {
        this.setSrcType(srcMo);
        this.setSrcUUID(srcid);
        this.setDstType(dstMo);
        this.setDstUUID(dstid);
        this.setRelation(relation);
        this.setServiceType(serviceType);

    }

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param srcMo The source object
     * @param moclass The source class
     * @param field The field
     * @param dstObj The destination object
     */
    public RelationMO(Object srcMo, Class moclass, Field field, Object dstObj) throws InnerErrorServiceException {
        MORelationField relationFieldAno = field.getAnnotation(MORelationField.class);
        this.setSrcType(srcMo.getClass());
        this.setSrcUUID(MOModelProcessor.getUUID(srcMo, moclass));

        this.setDstType(JavaEntityUtil.getFiledType(field));
        if(dstObj != null) {
            this.setDstUUID(MOModelProcessor.getUUID(dstObj, dstObj.getClass()));
        }
        this.setRelation(relationFieldAno.type());

        this.setServiceType(relationFieldAno.serviceType());

    }

    public String getSrcUUID() {
        return this.srcRelation.getUuid();
    }

    public void setSrcUUID(String srcUUID) {
        this.srcRelation.setUuid(srcUUID);
    }

    public String getDstUUID() {
        return this.dstRelation.getUuid();
    }

    public void setDstUUID(String dstUUID) {
        this.dstRelation.setUuid(dstUUID);
    }

    public Class getSrcType() {
        return this.srcRelation.getType();
    }

    public void setSrcType(Class srcType) {
        this.srcRelation.setType(srcType);
    }

    public Class getDstType() {
        return this.dstRelation.getType();
    }

    /**
     * It is used to get resource type of destination. <br>
     * 
     * @return The resource type.
     * @since SDNO 0.5
     */
    public String getDstResType() {
        return MOModelProcessor.getRestType(this.getDstType());
    }

    /**
     * It is used to get resource type of source. <br>
     * 
     * @return The resource type.
     * @since SDNO 0.5
     */
    public String getSrcResType() {
        return MOModelProcessor.getRestType(this.getSrcType());
    }

    public void setDstType(Class dstType) {
        this.dstRelation.setType(dstType);
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     * It is used to build a relation map by relation. <br>
     * 
     * @return The relation map, the format is [{"src_uuid":"s7S3e_1BTgquI8TaDjJccA",
     *         "dst_uuid":"2FET7LKITkab88KTjtDGjQ", "dst_type":"frame", "relation":"composition",
     *         "servicetypt":"protect"}]
     * @since SDNO 0.5
     */
    public Map<String, Object> buildRelationMap() {
        Map<String, Object> oneRelation = new HashMap<String, Object>();
        oneRelation.put(SRC_UUID, this.getSrcUUID());
        oneRelation.put(DST_UUID, this.getDstUUID());
        oneRelation.put(DST_TYPE, this.getDstResType());
        oneRelation.put(RELATION_TYPE, this.getRelation());
        oneRelation.put(SERVICE_TYPE, this.getServiceType());
        return oneRelation;
    }

    /**
     * It is used to get attribute of source relation. <br>
     * 
     * @return The attribute.
     * @since SDNO 0.5
     */
    public String getSrcAttr() {
        return this.srcRelation.getAttr();
    }

    /**
     * It is used to set attribute of source relation. <br>
     * 
     * @param srcAttr
     * @since SDNO 0.5
     */
    public void setSrcAttr(String srcAttr) {
        this.srcRelation.setAttr(srcAttr);
    }

    /**
     * It is used to get attribute of destination relation. <br>
     * 
     * @return
     * @since SDNO 0.5
     */
    public String getDstAttr() {
        return this.dstRelation.getAttr();
    }

    /**
     * It is used to set attribute of destination relation. <br>
     * 
     * @param dstAttr
     * @since SDNO 0.5
     */
    public void setDstAttr(String dstAttr) {
        this.dstRelation.setAttr(dstAttr);
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * It is used to get resource type. <br>
     * 
     * @return It'll return destination resource type if destination UUID is null, it'll return
     *         source resource type if source UUID is null or it'll return null if both source and
     *         destination UUID are existed.
     * @since SDNO 0.5
     */
    public Class getQueryType() {
        if(this.getDstUUID() == null) {
            return this.getDstType();
        }
        if(this.getSrcUUID() == null) {
            return this.getSrcType();
        }
        return null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((dstRelation == null) ? 0 : dstRelation.hashCode());
        result = (prime * result) + ((serviceType == null) ? 0 : serviceType.hashCode());
        result = (prime * result) + ((srcRelation == null) ? 0 : srcRelation.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        RelationMO other = (RelationMO)obj;
        if(dstRelation == null) {
            if(other.dstRelation != null) {
                return false;
            }
        } else if(!dstRelation.equals(other.dstRelation)) {
            return false;
        }
        if(serviceType == null) {
            if(other.serviceType != null) {
                return false;
            }
        } else if(!serviceType.equals(other.serviceType)) {
            return false;
        }
        if(srcRelation == null) {
            if(other.srcRelation != null) {
                return false;
            }
        } else if(!srcRelation.equals(other.srcRelation)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RelationMO [srcRelation=" + srcRelation + ", dstRelation=" + dstRelation + ", relation=" + relation
                + ", serviceType=" + serviceType + "]";
    }

}
