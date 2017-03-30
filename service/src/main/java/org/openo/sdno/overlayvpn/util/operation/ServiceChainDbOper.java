/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.util.operation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.inventory.sdk.util.InventoryDaoUtil;
import org.openo.sdno.overlayvpn.model.servicechain.ServiceChainSiteToDcRelation;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * ServiceChain service DB operate class.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 August 20, 2016
 */
public class ServiceChainDbOper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceChainDbOper.class);

    private static final String VPN_ID = "vpnId";

    private static final String SERVICE_CHAIN_ID = "uuid";

    private ServiceChainDbOper() {
    }

    /**
     * Insert data to database.<br>
     * 
     * @param data data
     * @return ResultRsp
     * @throws ServiceException when insert data failed
     * @since SDNO 0.5
     */
    public static ResultRsp<ServiceChainSiteToDcRelation> create(ServiceChainSiteToDcRelation data)
            throws ServiceException {

        return new InventoryDaoUtil<ServiceChainSiteToDcRelation>().getInventoryDao().insert(data);
    }

    /**
     * Query ServiceChainSiteToDcRelation data.<br>
     * 
     * @param vpnId VPN id
     * @return data queried out
     * @throws ServiceException when query data failed
     * @since SDNO 0.5
     */
    public static ServiceChainSiteToDcRelation query(String vpnId) throws ServiceException {

        ResultRsp<List<ServiceChainSiteToDcRelation>> queryDbRsp = queryByFilter(vpnId, null, null);
        if(CollectionUtils.isEmpty(queryDbRsp.getData())) {
            LOGGER.warn("query error, vpnId (" + vpnId + ") is not found");
            return null;
        }

        return queryDbRsp.getData().get(0);
    }

    /**
     * It is used to delete the original data. <br>
     * 
     * @param serviceChainId serviceChainId uuid
     * @throws ServiceException When delete failed.
     * @since SDNO 0.5
     */
    public static void delete(String serviceChainId) throws ServiceException {
        ResultRsp<List<ServiceChainSiteToDcRelation>> queryDbRsp =
                queryByFilter(null, serviceChainId, SERVICE_CHAIN_ID);
        if(CollectionUtils.isEmpty(queryDbRsp.getData())) {
            LOGGER.warn("delete error, serviceChainId (" + serviceChainId + ") is not found");
            return;
        }

        ServiceChainSiteToDcRelation reqModelInfo = queryDbRsp.getData().get(0);
        new InventoryDaoUtil<ServiceChainSiteToDcRelation>().getInventoryDao()
                .delete(ServiceChainSiteToDcRelation.class, reqModelInfo.getUuid());
    }

    private static ResultRsp<List<ServiceChainSiteToDcRelation>> queryByFilter(String vpnId, String uuid,
            String queryResultFields) throws ServiceException {
        Map<String, Object> filterMap = new HashMap<String, Object>();
        if(StringUtils.hasLength(vpnId)) {
            filterMap.put(VPN_ID, Arrays.asList(vpnId));
        }

        if(StringUtils.hasLength(uuid)) {
            filterMap.put(SERVICE_CHAIN_ID, Arrays.asList(uuid));
        }

        String filter = JSONObject.fromObject(filterMap).toString();

        return new InventoryDaoUtil<ServiceChainSiteToDcRelation>().getInventoryDao()
                .queryByFilter(ServiceChainSiteToDcRelation.class, filter, queryResultFields);
    }

}
