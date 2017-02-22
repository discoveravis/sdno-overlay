/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.composer.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.composer.ParamConsts;
import org.openo.sdno.overlayvpn.frame.dao.BaseDao;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseRoute;
import org.openo.sdno.overlayvpn.servicemodel.sbi.NetRoute;
import org.openo.sdno.overlayvpn.servicemodel.sbi.PolicyRoute;
import org.openo.sdno.overlayvpn.util.ModelUtils;

/**
 * The tool class to deal with route.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 12, 2017
 */
public class VpnRouteUtil {

    private static BaseDao<NetRoute> routeDao = new BaseDao<NetRoute>();

    private VpnRouteUtil() {
        super();
    }

    /**
     * Process the static route ,remove the route that is in DB.<br>
     * 
     * @param routeList The route list
     * @return The route list that has removed all the route in DB
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public static List<NetRoute> processRoute(List<NetRoute> routeList) throws ServiceException {
        List<NetRoute> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(routeList)) {
            return result;
        }
        List<NetRoute> queryDatas = new ArrayList<>();
        for(NetRoute route : routeList) {
            NetRoute cond = new NetRoute();
            cond.setSrcNeId(route.getSrcNeId());
            queryDatas.addAll(routeDao.query(cond));
        }
        Set<String> routeKeys = new HashSet<String>();
        for(NetRoute route : queryDatas) {
            routeKeys.add(route.toRouteKey());
        }
        for(NetRoute route : routeList) {
            if(routeKeys.contains(route.toRouteKey())) {
                continue;
            }
            result.add(route);
        }
        return result;
    }

    /**
     * Query the route that need to delete.<br>
     * 
     * @param connectionId The connection uuid
     * @return The queried route that need to delete
     * @throws ServiceException when queried failed
     * @since SDNO 0.5
     */
    public static List<BaseRoute> queryDeleteRoute(String connectionId) throws ServiceException {
        List<BaseRoute> routes = new ArrayList<>();

        NetRoute staticCond = new NetRoute();
        staticCond.setConnectionId(connectionId);
        routes.addAll(processSharedRoutes(routeDao.query(staticCond)));

        PolicyRoute policyCond = new PolicyRoute();
        policyCond.setConnectionId(connectionId);
        routes.addAll(processSharedRoutes(new BaseDao<PolicyRoute>().query(policyCond)));

        return routes;
    }

    @SuppressWarnings("unchecked")
    private static <T extends BaseRoute> List<T> processSharedRoutes(List<T> routes) throws ServiceException {
        List<T> result = new ArrayList<>();
        if(CollectionUtils.isEmpty(routes)) {
            return result;
        }
        List<String> uuidList = ModelServiceUtil.getUuidList(routes);
        for(T route : routes) {
            String description = route.getDescription();
            if(StringUtils.isEmpty(description) || !description.startsWith(ParamConsts.BASIC_ROUTE)) {
                result.add(route);
                continue;
            }
            T cond = (T)ModelUtils.newInstance(route.getClass());
            cond.setSrcNeId(route.getSrcNeId());
            List<T> dbDatas = new BaseDao<T>().query(cond);
            boolean shared = false;
            for(T dbRoute : dbDatas) {
                if(uuidList.contains(dbRoute.getId())) {
                    continue;
                }
                shared = true;
                if(dbRoute.getTenantId().equals(route.getTenantId())
                        && !dbRoute.getConnectionId().equals(route.getConnectionId())) {
                    route.setConnectionId(dbRoute.getConnectionId());
                    new BaseDao<T>().update(Arrays.asList(route));
                    break;
                }
            }
            if(!shared) {
                result.add(route);
            }
        }
        return result;
    }
}
