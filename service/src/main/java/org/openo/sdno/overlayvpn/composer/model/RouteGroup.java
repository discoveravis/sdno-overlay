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

package org.openo.sdno.overlayvpn.composer.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openo.sdno.overlayvpn.servicemodel.base.BaseRoute;

/**
 * The route group model.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 22, 2017
 */
public class RouteGroup {

    private List<BaseRoute> create = new ArrayList<>();

    private List<BaseRoute> update = new ArrayList<>();

    private List<BaseRoute> delete = new ArrayList<>();

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public RouteGroup() {
        super();
    }

    public List<BaseRoute> getCreate() {
        return create;
    }

    public void setCreate(List<BaseRoute> create) {
        this.create = create;
    }

    public List<BaseRoute> getUpdate() {
        return update;
    }

    public void setUpdate(List<BaseRoute> update) {
        this.update = update;
    }

    public List<BaseRoute> getDelete() {
        return delete;
    }

    public void setDelete(List<BaseRoute> delete) {
        this.delete = delete;
    }

    /**
     * Get the routes that the destination type is assignable from the route in route list.<br>
     * 
     * @param type The destination type
     * @param routeList The route list
     * @return The route list that meeting the conditions
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public static <T extends BaseRoute> List<T> getRoutes(Class<T> type, List<BaseRoute> routeList) {
        List<T> list = new ArrayList<T>();
        if(CollectionUtils.isEmpty(routeList) || type == null) {
            return list;
        }
        for(BaseRoute route : routeList) {
            if(type.isAssignableFrom(route.getClass())) {
                list.add((T)route);
            }
        }
        return list;
    }
}
