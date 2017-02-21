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

/**
 * The enumeration class of restful action.<br>
 * 
 * @author
 * @version SDNO 0.5 Feb 4, 2017
 */
public enum RestAction {
    GET("get"), POST("post"), PUT("put"), DELETE("delete"), PATCH("patch");

    private String action;

    private RestAction(String action) {
        this.setAction(action);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
