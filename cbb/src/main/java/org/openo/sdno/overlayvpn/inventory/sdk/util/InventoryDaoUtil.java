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

package org.openo.sdno.overlayvpn.inventory.sdk.util;

import org.openo.sdno.overlayvpn.dao.common.InventoryDao;
import org.openo.sdno.overlayvpn.util.OverlayvpnContextHelper;

/**
 * Utility class of InventoryDao.<br>
 * <p>
 * </p>
 * 
 * @param <T> template
 * @author
 * @version SDNO 0.5 July 27, 2016
 */
public class InventoryDaoUtil<T> {

    private static final String INVENTORYDAO_BEAN_NAME = "inventoryDao";

    /**
     * Get inventory DAO bean object.<br>
     * 
     * @return inventory DAO object
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public InventoryDao<T> getInventoryDao() {
        return (InventoryDao<T>)OverlayvpnContextHelper.getBean(INVENTORYDAO_BEAN_NAME);
    }
}
