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

package org.openo.sdno.overlayvpn.service.impl.overlayvpnsvc.vpcsubnet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.servicemodel.SiteToDc;
import org.openo.sdno.overlayvpn.model.servicemodel.SubNet;
import org.openo.sdno.overlayvpn.model.servicemodel.Vpc;
import org.openo.sdno.overlayvpn.model.servicemodel.VpcSubNetMapping;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.sbi.overlayvpn.VpcService;
import org.openo.sdno.overlayvpn.sbi.overlayvpn.VpcSubnetService;
import org.openo.sdno.overlayvpn.util.check.CheckStrUtil;
import org.openo.sdno.overlayvpn.util.operation.VpcSubnetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Vpc subnet model implementation.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 24, 2016
 */
public class VpcSubnetImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(VpcSubnetImpl.class);

    @Autowired
    private InventoryDao inventoryDao;

    /**
     * Create Vpc and Subnet.<br>
     * 
     * @param req HttpServletRequest object
     * @param resp HttpServletResponse Object
     * @param siteToDc SiteToDc object
     * @return Vpc Object created
     * @throws ServiceException throws when create failed
     * @since SDNO 0.5
     */
    public Vpc create(HttpServletRequest req, HttpServletResponse resp, SiteToDc siteToDc) throws ServiceException {

        // Create UUID
        siteToDc.getVpc().setUuid(UuidUtils.createUuid());
        siteToDc.getVpc().getSubnet().setUuid(UuidUtils.createUuid());

        // Insert DB
        VpcSubNetMapping vpcSubNetMapping = VpcSubnetUtil.buildVpcSubnetMapping(siteToDc);
        inventoryDao.insert(vpcSubNetMapping);

        ResultRsp<Vpc> vpcResultRsp = VpcService.getInstance().create(req, siteToDc.getVpc());
        if(!vpcResultRsp.isSuccess() || null == vpcResultRsp.getData()) {
            LOGGER.error("Create VPC Service return error!!");
            throw new ServiceException("Create VPC Service failed!!");
        }

        Vpc createdVpc = vpcResultRsp.getData();
        siteToDc.getVpc().getSubnet().setVpcId(createdVpc.getUuid());

        ResultRsp<SubNet> subnetResultRsp = VpcSubnetService.getInstance().create(req, siteToDc.getVpc().getSubnet());
        if(!subnetResultRsp.isSuccess() || null == subnetResultRsp.getData()) {
            LOGGER.error("Create Subnet Service return Error!!");
            throw new ServiceException("Create Subnet Service failed!!");
        }

        SubNet createdSubnet = subnetResultRsp.getData();
        createdVpc.setSubnet(createdSubnet);
        return createdVpc;
    }

    /**
     * Delete Vpc and Subnet.<br>
     * 
     * @param req HttpServletRequest object
     * @param resp HttpServletResponse Object
     * @param siteToDcId SiteToDc UUID
     * @return operation result
     * @throws ServiceException throws when delete failed
     * @since SDNO 0.5
     */
    public ResultRsp<String> delete(HttpServletRequest req, HttpServletResponse resp, String siteToDcId)
            throws ServiceException {

        CheckStrUtil.checkUuidStr(siteToDcId);

        VpcSubNetMapping vpcSubNetMapping = query(req, resp, siteToDcId);
        if(null == vpcSubNetMapping) {
            LOGGER.info("Vpc and subnet not exist.");
            return new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS, "Vpc and subnet not exist");
        }
        CheckStrUtil.checkUuidStr(vpcSubNetMapping.getSubnetId());
        CheckStrUtil.checkUuidStr(vpcSubNetMapping.getVpcId());

        ResultRsp<SubNet> subnetResultRsp = VpcSubnetService.getInstance().delete(req, vpcSubNetMapping.getSubnetId());
        if(!subnetResultRsp.isSuccess()) {
            LOGGER.error("Delete Subnet Service return Error!!");
            throw new ServiceException("Delete Subnet Service failed!!");
        }

        ResultRsp<Vpc> vpcResultRsp = VpcService.getInstance().delete(req, vpcSubNetMapping.getVpcId());
        if(!vpcResultRsp.isSuccess()) {
            LOGGER.error("Delete VPC Service return error!!");
            throw new ServiceException("Delete VPC Service failed!!");
        }

        inventoryDao.delete(VpcSubNetMapping.class, vpcSubNetMapping.getUuid());

        return new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS, "Delete vpc and subnet success");
    }

    /**
     * Query Vpc and Subnet.<br>
     * 
     * @param req HttpServletRequest object
     * @param resp HttpServletResponse Object
     * @param siteToDcId SiteToDc UUID
     * @return VpcSubNetMapping object
     * @throws ServiceException throws when query failed
     * @since SDNO 0.5
     */
    public VpcSubNetMapping query(HttpServletRequest req, HttpServletResponse resp, String siteToDcId)
            throws ServiceException {

        CheckStrUtil.checkUuidStr(siteToDcId);

        Map<String, List<String>> filterMap = new HashMap<String, List<String>>();
        filterMap.put("sitetodcId", Arrays.asList(siteToDcId));

        ResultRsp<List<VpcSubNetMapping>> resultList =
                inventoryDao.queryByFilter(VpcSubNetMapping.class, JsonUtil.toJson(filterMap), null);
        return resultList.getData().get(0);
    }
}
