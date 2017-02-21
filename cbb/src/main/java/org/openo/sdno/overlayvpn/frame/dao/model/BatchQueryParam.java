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

package org.openo.sdno.overlayvpn.frame.dao.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.overlayvpn.frame.dao.constant.MssConsts;

/**
 * The batch query parameters.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
public class BatchQueryParam {

    private int pagesize;

    private int pagenum;

    private String fields;

    private String sort;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public BatchQueryParam() {
        super();
    }

    /**
     * Constructor.<br>
     * 
     * @param httpContext The HttpServletRequest object
     * @throws ServiceException when number format exception
     * @since SDNO 0.5
     */
    public BatchQueryParam(HttpServletRequest httpContext) throws ServiceException {
        Map<String, String[]> paramMap = httpContext.getParameterMap();
        if(paramMap.containsKey("sort")) {
            String sort = httpContext.getParameter("sort");
            this.setSort(Arrays.asList(sort));
        }
        if(paramMap.containsKey("pagenum")) {
            this.setPagenum(Integer.parseInt(httpContext.getParameter("pagenum")));
        }
        if(paramMap.containsKey("pagesize")) {
            this.setPagenum(Integer.parseInt(httpContext.getParameter("pagesize")));
        }
    }

    /**
     * To set restful parameters.<br>
     * 
     * @param restParam The restful parameters to set
     * @return The RestfulParametes object
     * @since SDNO 0.5
     */
    public RestfulParametes toRestParam(RestfulParametes restParam) {
        RestfulParametes params;
        if(restParam == null) {
            params = new RestfulParametes();
        } else {
            params = restParam;
        }
        if(pagesize <= 0 || pagesize > 1000) {
            pagesize = MssConsts.MAX_PAGE_SIZE;
        }
        if(pagenum < 0) {
            pagenum = 0;
        }
        if(StringUtils.isNotEmpty(fields)) {
            params.put("fields", fields);
        }
        if(StringUtils.isNotEmpty(sort)) {
            params.put("sort", sort);
        }
        params.put("pagesize", String.valueOf(pagesize));
        params.put("pagenum", String.valueOf(pagenum));
        return params;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * Set sort list as string.<br>
     * 
     * @param sortAttrs The sort list
     * @throws ServiceException when translating sort list to json failed
     * @since SDNO 0.5
     */
    @JsonIgnore
    private void setSort(List<String> sortAttrs) throws ServiceException {
        if(CollectionUtils.isNotEmpty(sortAttrs)) {
            this.sort = JsonUtil.toJson(sortAttrs);
        }
    }
}
