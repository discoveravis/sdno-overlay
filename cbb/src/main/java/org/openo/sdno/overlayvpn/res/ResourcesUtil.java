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

package org.openo.sdno.overlayvpn.res;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class of Resource Operation.<br/>
 * 
 * @author
 * @version SDNO 0.5 Jun 3, 2016
 */
public class ResourcesUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesUtil.class);

    private static AllocIdResourceInvDao resourceDao = new AllocIdResourceInvDao();

    private ResourcesUtil() {
    }

    /**
     * Allocate LongID resource.<br/>
     * 
     * @param poolname resource pool name
     * @param label user label
     * @param reqNumber number of LongIds need to allocate
     * @param min minimum of LongID Range
     * @param max maximum of LongID Range
     * @return LongID allocated
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public static List<Long> requestGloabelValue(String poolname, String label, int reqNumber, Long min, Long max)
            throws ServiceException {

        if(reqNumber < 1) {
            LOGGER.error("reqNumber is wrong!!");
            throw new ServiceException("reqNumber is wrong");
        }

        ResultRsp<List<AllocIdResource>> allocIdResRsp = resourceDao.batchQuery(poolname, null);
        if(!allocIdResRsp.isSuccess()) {
            LOGGER.error("Query Alloc Id Resource Failed!!");
            throw new ServiceException("Query Alloc Id Resource Failed");
        }

        List<Long> allocResList =
                new ArrayList<Long>(CollectionUtils.collect(allocIdResRsp.getData(), new Transformer() {

                    @Override
                    public Object transform(Object arg0) {
                        return ((AllocIdResource)arg0).getIdres();
                    }
                }));

        if((max - min + 1 - allocResList.size()) < reqNumber) {
            LOGGER.error("The resource is not enough!!");
            throw new ServiceException("The resource is not enough");
        }

        List<Long> idList = new ArrayList<Long>();
        List<AllocIdResource> resourceList = new ArrayList<AllocIdResource>();

        for(Long resourceIndex = min; resourceIndex < max; resourceIndex++) {
            if(!allocResList.contains(resourceIndex)) {
                idList.add(resourceIndex);
                resourceList.add(new AllocIdResource((long)resourceIndex, poolname, label));
            }
            if(reqNumber == idList.size()) {
                break;
            }
        }

        resourceDao.batchInsert(resourceList);

        return idList;
    }

    /**
     * Free LongID resource.<br/>
     * 
     * @param poolname pool name
     * @param label user label
     * @param idList list of LongId need to free
     * @return result of free operation
     * @since SDNO 0.5
     */
    public static ResultRsp<String> freeGlobalValueList(String poolname, String label, List<Long> idList)
            throws ServiceException {

        ResultRsp<List<AllocIdResource>> queryResult = resourceDao.batchQuery(poolname, idList);
        if(!queryResult.isSuccess()) {
            LOGGER.error("Query Alloc Id Resource Failed!!");
            throw new ServiceException("Query Alloc Id Resource Failed");
        }

        resourceDao.batchDelete(queryResult.getData());

        return new ResultRsp<String>(ErrorCode.OVERLAYVPN_SUCCESS);
    }

}
