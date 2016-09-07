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
import org.openo.sdno.overlayvpn.model.ipsec.IpSecPolicy;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.service.inf.overlayvpn.IIpsecPolicy;
import org.openo.sdno.overlayvpn.util.check.CheckSecurityPolicyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * IpsecPolicy service implementation.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 7, 2016
 */
@Service
public class IpsecPolicySvcImpl implements IIpsecPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpsecPolicySvcImpl.class);

    /**
     * Create IpSecPolicy.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param ipSecPolicy IpSecPolicy need to create
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<IpSecPolicy> create(HttpServletRequest req, HttpServletResponse resp, IpSecPolicy ipSecPolicy)
            throws ServiceException {
        return new InventoryDao<IpSecPolicy>().insert(ipSecPolicy);
    }

    /**
     * Query Single IpSecPolicy.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param ipsecPolicyUuid IpSecPolicy id
     * @param tenantId tenant id
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<IpSecPolicy> query(HttpServletRequest req, HttpServletResponse resp, String ipsecPolicyUuid,
            String tenantId) throws ServiceException {
        ResultRsp<IpSecPolicy> queryResult =
                new InventoryDao<IpSecPolicy>().query(IpSecPolicy.class, ipsecPolicyUuid, tenantId);
        ResultRsp<IpSecPolicy> retResult = new ResultRsp<>(queryResult);
        if(queryResult.isSuccess()) {
            retResult.setData(queryResult.getData());
        } else {
            LOGGER.error("failed to query ipsec policy: " + queryResult.toString());
        }
        return retResult;
    }

    /**
     * Batch Query IpSecPolicy.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param tenantId tenant id
     * @param filter filter condition
     * @return IpSecPolicy queried out
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<List<IpSecPolicy>> batchQuery(HttpServletRequest req, HttpServletResponse resp, String tenantId,
            String filter) throws ServiceException {
        return new InventoryDao<IpSecPolicy>().batchQuery(IpSecPolicy.class, filter);
    }

    /**
     * Update IpsecPolicy, need to check first.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param newIpsecPolicy old IpsecPolicy
     * @param oldIpsecPolicy new IpsecPolicy
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<IpSecPolicy> update(HttpServletRequest req, HttpServletResponse resp, IpSecPolicy newIpsecPolicy,
            IpSecPolicy oldIpsecPolicy) throws ServiceException {
        // Check whether IpsecPolicy is used
        CheckSecurityPolicyUtil.checkIpsecPolicyIsUsed(oldIpsecPolicy);
        return new InventoryDao<IpSecPolicy>().update(newIpsecPolicy, null);
    }

    /**
     * Delete IpSecPolicy, need to check first.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param ipSecPolicy IpSecPolicy need to delete
     * @return operation result
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<String> delete(HttpServletRequest req, HttpServletResponse resp, IpSecPolicy ipSecPolicy)
            throws ServiceException {
        // Check whether IpsecPolicy is used
        CheckSecurityPolicyUtil.checkIpsecPolicyIsUsed(ipSecPolicy);

        return new ResultRsp<>(new InventoryDao<IpSecPolicy>().delete(IpSecPolicy.class, ipSecPolicy.getUuid()));
    }

    /**
     * Deploy IpSecPolicy.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param ipSecPolicy IpSecPolicy need to deploy
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<IpSecPolicy> deploy(HttpServletRequest req, HttpServletResponse resp, IpSecPolicy ipSecPolicy)
            throws ServiceException {
        return new ResultRsp<>();
    }

    /**
     * UnDeploy IpSecPolicy.<br/>
     * 
     * @param req HttpServletRequest Object
     * @param resp HttpServletResponse Object
     * @param ipSecPolicy IpSecPolicy need to UnDeploy
     * @return operation result wrapped with the response object
     * @throws ServiceException throws serviceException if operate failed
     * @since SDNO 0.5
     */
    @Override
    public ResultRsp<IpSecPolicy> undeploy(HttpServletRequest req, HttpServletResponse resp, IpSecPolicy ipSecPolicy)
            throws ServiceException {
        return new ResultRsp<>();
    }

}
