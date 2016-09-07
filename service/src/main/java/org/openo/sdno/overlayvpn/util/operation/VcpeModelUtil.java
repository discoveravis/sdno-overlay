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

package org.openo.sdno.overlayvpn.util.operation;

import java.util.Arrays;
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.brs.invdao.ControllerDao;
import org.openo.sdno.overlayvpn.brs.invdao.NetworkElementInvDao;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;
import org.openo.sdno.overlayvpn.brs.model.NetworkElementMO;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.model.netmodel.localsite.AdapterDeviceNetwork;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.openo.sdno.overlayvpn.sbi.cpe.NetworkAdapterService;
import org.openo.sdno.overlayvpn.util.consts.CpeConstant;
import org.openo.sdno.overlayvpn.util.exception.ThrowOverlayVpnExcpt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Util class of VcpeModel.<br/>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 Jun 1, 2016
 */
public class VcpeModelUtil {

    private NetworkAdapterService netAdptSvc;

    private static final Logger LOGGER = LoggerFactory.getLogger(VcpeModelUtil.class);

    /**
     * Create AdapterDeviceNetwork<br/>
     * 
     * @param neID networkElement id
     * @param tenantId tenant id
     * @param cIdR CIDR value
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    public void createNetworkOnAc(String neID, String tenantId, String cIdR) throws ServiceException {
        try {

            String ctrlUuid = getControllerIdFromNeId(neID);

            AdapterDeviceNetwork devAdaptNetwork = buildNetworkReq(neID, tenantId, cIdR);

            ResultRsp<List<AdapterDeviceNetwork>> rsp =
                    netAdptSvc.createNetwork(ctrlUuid, neID, Arrays.asList(devAdaptNetwork));
            if(rsp.isSuccess() || rsp.getErrorCode().equals(ErrorCode.OVERLAYVPN_SUCCESS)) {
                List<AdapterDeviceNetwork> networks = rsp.getData();
                if(CollectionUtils.isEmpty(networks)) {
                    LOGGER.warn("AdapterDeviceNetwork is empty");
                    throw new ServiceException("AdapterDeviceNetwork is empty");
                }
                LOGGER.info("create network sunccessfully!");
            } else {
                LOGGER.error("create range failed, rsp code:{0}, content:{1}", rsp.getErrorCode(), rsp.getMessage());
                throw new ServiceException("create range failed");
            }

        } catch(Exception ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Get controller id of network element.<br/>
     * 
     * @param neID networkElement id
     * @return controller id
     * @throws ServiceException when failed
     * @since SDNO 0.5
     */
    private String getControllerIdFromNeId(String neID) throws ServiceException {
        // setp1 : get the device info from the neID...
        NetworkElementInvDao neDao = new NetworkElementInvDao();
        NetworkElementMO tempNe = neDao.query(neID);

        if(null == tempNe) {
            LOGGER.error("ne not exist: " + neID);
            ThrowOverlayVpnExcpt.throwResNotExistAsBadReq(ErrorCode.RESOURCE_NETWORKELEMENT_NOT_EXIST, neID);
        }

        String deviceId = tempNe.getNativeID();
        if(!StringUtils.hasLength(deviceId)) {
            ThrowOverlayVpnExcpt.throwParmaterInvalid("device id", tempNe.getName());
        }

        // step 2 : make a DAO call to get the controller iD for the given device
        String neUuid = tempNe.getId();
        ControllerDao controllerDao = new ControllerDao();
        List<ControllerMO> conList = controllerDao.getControllerByNeId(neUuid);

        if(CollectionUtils.isEmpty(conList)) {
            LOGGER.info("do not find ControllerMO by ne, wite uuid = " + neUuid);
            ThrowOverlayVpnExcpt.throwParmaterInvalid("controller id des not exist", tempNe.getName());
        }

        // step 3: get the controller UUID
        return conList.get(0).getObjectId();
    }

    /**
     * Build AdapterDeviceNetwork instance.<br/>
     * 
     * @param neId network element id
     * @param tenantId tenant id
     * @param cIdR CIDR value
     * @return AdapterDeviceNetwork object created
     * @since SDNO 0.5
     */
    private AdapterDeviceNetwork buildNetworkReq(String neId, String tenantId, String cIdR) {
        String deviceId = neId;
        AdapterDeviceNetwork network = new AdapterDeviceNetwork();
        network.setDeviceId(deviceId);
        // fill name attribute
        network.setName(tenantId);
        // set use attribute
        network.setUse(CpeConstant.LAN_NETWORK_USE_TYPE_TERMINAL);
        // set vlanId attribute
        network.setVlanId(CpeConstant.LAN_NETWORK_ACCESS_VLAN_ID_DEFAULT);
        // set ChangedMode attribute
        network.setIsChangedMode(CpeConstant.LAN_NETWORK_CHANGE_MODE_DEFAULT);
        // set DhcpEnable attribute
        network.setDhcpEnable(CpeConstant.LAN_NETWORK_DHCP_ENABLE_DEFAULT);
        // TODO set IP
        return network;
    }

}
