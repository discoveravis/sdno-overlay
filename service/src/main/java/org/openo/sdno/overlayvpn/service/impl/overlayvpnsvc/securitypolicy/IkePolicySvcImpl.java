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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.securitypolicy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.model.ipsec.IkePolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.inf.overlayvpn.IIkepolicy;
import org.openo.sdno.overlayvpn.util.check.CheckSecurityPolicyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * IkePolicy service implementation.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 7, 2016
 */
@Service
public class IkePolicySvcImpl implements IIkepolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(IkePolicySvcImpl.class);

    /**
     * Create IkePolicy.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param ikePolicy IkePolicy Object
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<IkePolicy> create(HttpServletRequest req, HttpServletResponse resp, IkePolicy ikePolicy)
            throws ServiceException {
        // Insert data to database
        return new InventoryDao<IkePolicy>().insert(ikePolicy);
    }

    /**
     * Query single IkePolicy.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param ikePolicyUuid key id of IkePolicy
     * @param tenantId tenant id
     * @return IkePolicy Queried out
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<IkePolicy> query(HttpServletRequest req, HttpServletResponse resp, String ikePolicyUuid,
            String tenantId) throws ServiceException {
        ResultRsp<IkePolicy> queryResult =
                new InventoryDao<IkePolicy>().query(IkePolicy.class, ikePolicyUuid, tenantId);
        ResultRsp<IkePolicy> retResult = new ResultRsp<>(queryResult);
        if(queryResult.isSuccess()) {
            retResult.setData(queryResult.getData());
        } else {
            LOGGER.error("failed to query ike policy: " + queryResult.toString());
        }
        return retResult;
    }

    /**
     * Batch Query IkePolicy.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param tenantId tenant id
     * @param filter filter condition
     * @return IkePolicy Queried out
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<List<IkePolicy>> batchQuery(HttpServletRequest req, HttpServletResponse resp, String tenantId,
            String filter) throws ServiceException {
        return new InventoryDao<IkePolicy>().batchQuery(IkePolicy.class, filter);
    }

    /**
     * Update IkePolicy,need to verify first.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param newIkePolicy old IkePolicy
     * @param oldIkePolicy new IkePolicy
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<IkePolicy> update(HttpServletRequest req, HttpServletResponse resp, IkePolicy newIkePolicy,
            IkePolicy oldIkePolicy) throws ServiceException {
        // check whether IkePolicy is used
        CheckSecurityPolicyUtil.checkIkePolicyIsUsed(oldIkePolicy);

        return new InventoryDao<IkePolicy>().update(newIkePolicy, null);
    }

    /**
     * Delete IkePolicy, need to check first.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param ikePolicy
     * @return operation result
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<String> delete(HttpServletRequest req, HttpServletResponse resp, IkePolicy ikePolicy)
            throws ServiceException {
        // check whether IkePolicy is used
        CheckSecurityPolicyUtil.checkIkePolicyIsUsed(ikePolicy);

        return new ResultRsp<>(new InventoryDao<IkePolicy>().delete(IkePolicy.class, ikePolicy.getUuid()));
    }

    /**
     * Deploy IkePolicy.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param ikePolicy IkePolicy need to deploy
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<IkePolicy> deploy(HttpServletRequest req, HttpServletResponse resp, IkePolicy ikePolicy)
            throws ServiceException {
        return new ResultRsp<>();
    }

    /**
     * UnDeploy IkePolicy.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param ikePolicy IkePolicy need to UnDeploy
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<IkePolicy> undeploy(HttpServletRequest req, HttpServletResponse resp, IkePolicy ikePolicy)
            throws ServiceException {
        return new ResultRsp<>();
    }

}
