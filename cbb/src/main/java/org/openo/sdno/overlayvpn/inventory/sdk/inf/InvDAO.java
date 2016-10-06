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

package org.openo.sdno.overlayvpn.inventory.sdk.inf;

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.model.BatchQueryFileterEntity;
import org.openo.sdno.overlayvpn.inventory.sdk.model.QueryParams;
import org.openo.sdno.overlayvpn.inventory.sdk.model.RelationMO;
import org.openo.sdno.overlayvpn.result.ResultRsp;

/**
 * The interface of inventory. <br>
 * <p>
 * The object of MO must has a empty constructor, and all attributes must have the method of get or
 * set.
 * </p>
 * 
 * @param <T> Inventory Model Class
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public interface InvDAO<T> {

    /**
     * Add relative relation. <br>
     * 
     * @param relationMO The object of RelationMO
     * @return The object of ResultRsp.
     * @throws ServiceException When add relation fail.
     * @since SDNO 0.5
     */
    ResultRsp<T> addRelation(RelationMO relationMO) throws ServiceException;

    /**
     * Delete relative relation. <br>
     * 
     * @param relationMO The object of RelationMO
     * @return The object of ResultRsp.
     * @throws ServiceException When delete relation fail.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    ResultRsp deleteRelation(RelationMO relation) throws ServiceException;

    /**
     * Insert designated object to database. <br>
     * 
     * @param moList The object list that want to insert.
     * @param moType The class type.
     * @return The object of ResultRsp.
     * @throws ServiceException When insert object fail.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    ResultRsp<List<String>> add(List<T> moList, Class moType) throws ServiceException;

    /**
     * Delete from database by uuid. <br>
     * 
     * @param uuid The record uuid that want to delete
     * @param moType The class type
     * @return The object of ResultRsp.
     * @throws ServiceException When delete fail.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    ResultRsp<String> delete(String uuid, Class moType) throws ServiceException;

    /**
     * Delete from database by object list. <br>
     * 
     * @param moList The object list that want to delete
     * @param moType The class type
     * @return The object of ResultRsp.
     * @throws ServiceException When delete fail.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    ResultRsp<List<String>> delete(List<T> moList, Class moType) throws ServiceException;

    /**
     * Batch delete from database by uuid list. <br>
     * 
     * @param uuidList The list of uuid that want to delete
     * @param moType The class type
     * @return The object of ResultRsp.
     * @throws ServiceException When delete fail.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    ResultRsp batchDelete(List<String> uuidList, Class moType) throws ServiceException;

    /**
     * Update database by object list. <br>
     * 
     * @param moList The object list that want to update.
     * @param moType The class type.
     * @return The object of ResultRsp.
     * @throws ServiceException When update fail.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    ResultRsp update(List<T> moList, Class moType) throws ServiceException;

    /**
     * Query database by condition list. <br>
     * 
     * @param condition The condition list
     * @param moType The class type.
     * @return The object of ResultRsp, it'll take with MO list.
     * @throws ServiceException When query fail.
     * @since SDNO 0.5
     */
    @Deprecated
    ResultRsp<List<T>> query(List<T> condition, @SuppressWarnings("rawtypes") Class moType) throws ServiceException;

    /**
     * Query database by queryParams that user defined. <br>
     * 
     * @param moType The class type.
     * @param queryParams The query conditions that user defined
     * @return The object of ResultRsp, it'll take with MO list.
     * @throws ServiceException When query fail.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    ResultRsp<List<T>> query(Class moType, QueryParams queryParams) throws ServiceException;

    /**
     * Query database by uuid. <br>
     * 
     * @param uuid The uuid
     * @param moType The class type
     * @return the object of ResultRsp.
     * @throws ServiceException When query fail.
     * @since SDNO 0.5
     */
    @SuppressWarnings("rawtypes")
    ResultRsp<T> query(String uuid, Class moType) throws ServiceException;

    /**
     * Query database by relation. <br>
     * 
     * @param relation the object of RelationMO.
     * @return The object of ResultRsp, it'll take with MO list.
     * @throws ServiceException When query fail.
     * @since SDNO 0.5
     */
    ResultRsp<List<T>> queryByRelation(RelationMO relation) throws ServiceException;

    /**
     * Query numbers by filter in one table. <br>
     * 
     * @param resType The resource type
     * @param filter The filter
     * @return The numbers that query.
     * @throws ServiceException When query fail.
     * @since SDNO 0.5
     */
    long queryTotalNumber(String resType, String filter) throws ServiceException;

    /**
     * Query database by filterEntity. <br>
     * 
     * @param moType The class type
     * @param filterEntity The query filter
     * @return The object of ResultRsp, it'll take with MO list.
     * @throws ServiceException When query fail.
     * @since SDNO 0.5
     */
    ResultRsp<List<T>> query(Class moType, BatchQueryFileterEntity filterEntity) throws ServiceException;
}
