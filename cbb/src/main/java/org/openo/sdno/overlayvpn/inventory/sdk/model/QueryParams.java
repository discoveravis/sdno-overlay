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

import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.sdno.overlayvpn.security.authentication.TokenDataHolder;

/**
 * The definition of query parameters. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class QueryParams {

    /**
     * The filter for query, it's not mandatory.
     */
    private final String filter;

    /**
     * The attributes that want to get, it's not mandatory. It means get all attributes when it sets
     * null or empty character string or character '*'.
     */
    private final String attr;

    /**
     * The attribute that want to sort, it's not mandatory.
     */
    private final String sort;

    /**
     * The sort type, default is ascending. Please input "-" if want sort by descending.
     */
    private final String sortType;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     * @param filter The filter value
     * @param attr The attributes that want to get
     * @param sort The attribute that want to sort
     * @param sortType The sort type
     */
    public QueryParams(String filter, String attr, String sort, String sortType) {
        this.filter = filter;
        this.attr = attr;
        this.sort = sort;
        this.sortType = sortType;
    }

    public String getFilter() {
        return filter;
    }

    public String getSortType() {
        return sortType;
    }

    public String getAttr() {
        return attr;
    }

    public String getSort() {
        return sort;
    }

    /**
     * It is used to convert from QueryParams to RestfulParametes. <br>
     * 
     * @return The object of RestfulParametes.
     * @since SDNO 0.5
     */
    public RestfulParametes convertRestfulParametes() {
        RestfulParametes restParametes = new RestfulParametes();
        // It is used to authentication.
        restParametes.putHttpContextHeader("X-Auth-Token", TokenDataHolder.getToken());
        String queryAttr = ((attr == null) || attr.isEmpty() || "*".equals(attr)) ? "all" : attr;
        restParametes.put("attr", queryAttr);
        restParametes.put("filter", filter);
        if((sort != null) && !sort.isEmpty()) {
            restParametes.put("sort", sort);
        }
        if((sortType != null) && !sortType.isEmpty()) {
            restParametes.put("sorttype", sortType);
        }
        return restParametes;
    }

    @Override
    public String toString() {
        return "QueryParams{" + "filter='" + filter + '\'' + ", attr='" + attr + '\'' + ", sort='" + sort + '\''
                + ", sortType='" + sortType + '\'' + '}';
    }
}
