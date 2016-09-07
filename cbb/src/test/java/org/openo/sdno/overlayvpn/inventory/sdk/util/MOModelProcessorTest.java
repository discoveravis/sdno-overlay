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

package org.openo.sdno.overlayvpn.inventory.sdk.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.overlayvpn.brs.model.ControllerMO;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOExtendField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MORelationField;
import org.openo.sdno.overlayvpn.inventory.sdk.model.annotation.MOUUIDField;
import org.openo.sdno.overlayvpn.inventory.sdk.util.MOModelProcessor;
import org.openo.sdno.overlayvpn.model.servicemodel.Connection;
import org.openo.sdno.overlayvpn.model.tenant.TenantMO;
import org.openo.sdno.util.reflect.JavaEntityUtil;

/**
 * MOModelProcessor test class.<br/>
 * 
 * @author
 * @version SDNO 0.5 2016-6-12
 */
public class MOModelProcessorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testBuildFilterMap1() throws InnerErrorServiceException {
        TenantMO mo = new TenantMO();
        mo.setId("MoId");
        mo.setUuid("123456");
        mo.setName("MoName");
        Map<String, Object> fieldValue = JavaEntityUtil.getFiledValues(mo);
        Map<String, Object> result = MOModelProcessor.buildFilterMap(fieldValue, mo.getClass());

        assertEquals("123456", ((ArrayList<?>)result.get("uuid")).get(0));
        assertEquals("MoId", ((ArrayList<?>)result.get("id")).get(0));
        assertEquals("MoName", ((ArrayList<?>)result.get("name")).get(0));

    }

    @Test
    public void testBuildFilterMap2() throws InnerErrorServiceException {
        Connection mo = new Connection();
        mo.setCompositeVpnId("CompositeVpnId");

        Map<String, Object> fieldValue = JavaEntityUtil.getFiledValues(mo);
        Map<String, Object> result = MOModelProcessor.buildFilterMap(fieldValue, mo.getClass());

        assertEquals("CompositeVpnId", ((ArrayList<?>)result.get("overlayVpnId")).get(0));

    }

    @Test
    public void testBuildFilterMap3() throws InnerErrorServiceException {
        Connection mo = new Connection();
        ArrayList<String> lst = new ArrayList<String>();
        lst.add("epgIds");
        mo.setEpgIds(lst);

        Map<String, Object> fieldValue = JavaEntityUtil.getFiledValues(mo);
        Map<String, Object> result = MOModelProcessor.buildFilterMap(fieldValue, mo.getClass());

        assertFalse(result.containsValue("epgIds"));
    }

    @Test
    public void testProcess1() throws InnerErrorServiceException {
        TenantMO mo = new TenantMO();
        mo.setId("MoId");
        mo.setUuid("123456");
        mo.setName("MoName");
        Map<String, Object> fieldValue = JavaEntityUtil.getFiledValues(mo);
        Map<String, Object> result = MOModelProcessor.process(fieldValue, mo.getClass());

        assertEquals("123456", result.get("uuid"));
        assertEquals("MoId", result.get("id"));
        assertEquals("MoName", result.get("name"));
    }

    @Test
    public void testProcess2() throws InnerErrorServiceException {
        Connection mo = new Connection();
        mo.setCompositeVpnId("CompositeVpnId");

        Map<String, Object> fieldValue = JavaEntityUtil.getFiledValues(mo);
        Map<String, Object> result = MOModelProcessor.process(fieldValue, mo.getClass());

        assertEquals("CompositeVpnId", result.get("overlayVpnId"));
    }

    @Test
    public void testProcess3() throws InnerErrorServiceException {
        Connection mo = new Connection();
        ArrayList<String> lst = new ArrayList<String>();
        lst.add("epgIds");
        mo.setEpgIds(lst);

        Map<String, Object> fieldValue = JavaEntityUtil.getFiledValues(mo);
        Map<String, Object> result = MOModelProcessor.process(fieldValue, mo.getClass());

        assertFalse(result.containsValue("epgIds"));
    }

    @Test
    public void testProcess4() throws InnerErrorServiceException {
        TestMoModelPro mo = new TestMoModelPro();
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "value01");
        map.put("2", "value02");
        mo.setMapValue(map);

        Map<String, Object> fieldValue = JavaEntityUtil.getFiledValues(mo);
        Map<String, Object> result = MOModelProcessor.process(fieldValue, mo.getClass());

        assertFalse(result.containsValue("value"));
        assertEquals("value01", result.get("1"));
        assertEquals("value02", result.get("2"));
    }

    @Test
    public void testGetMOFieldName() throws InnerErrorServiceException {
        TenantMO mo = new TenantMO();
        mo.setId("MoId");
        mo.setUuid("123456");
        Field result = MOModelProcessor.getMOFieldName("uuid", mo.getClass());
        assertEquals("uuid", result.getName());
    }

    @Test
    public void testGetMOFieldName2() throws InnerErrorServiceException {
        TenantMO mo = new TenantMO();
        mo.setId("MoId");
        mo.setUuid("123456");
        Field nullResult = MOModelProcessor.getMOFieldName("nullResult", mo.getClass());
        assertNull(nullResult);
    }

    @Test
    public void testGetMOFieldName3() throws InnerErrorServiceException {
        Connection mo = new Connection();
        mo.setCompositeVpnId("CompositeVpnId");

        Field result = MOModelProcessor.getMOFieldName("overlayVpnId", mo.getClass());
        assertEquals("compositeVpnId", result.getName());
    }

    @Test
    public void testGetUUID() throws InnerErrorServiceException {
        TenantMO mo = new TenantMO();
        mo.setUuid("123456");
        String result = MOModelProcessor.getUUID(mo, mo.getClass());
        assertEquals("123456", result);
    }

    @Test
    public void testGetUUID2() throws InnerErrorServiceException {
        TestUuid mo = new TestUuid();

        String result = MOModelProcessor.getUUID(mo, mo.getClass());
        assertEquals("0", result);
    }

    @Test
    public void testGetRestType() {
        ControllerMO mo = new ControllerMO();
        String result = MOModelProcessor.getRestType(mo.getClass());
        assertEquals("controller", result);
    }

    @Test
    public void testGetRestType2() {
        TenantMO mo = new TenantMO();
        String result = MOModelProcessor.getRestType(mo.getClass());
        assertEquals("org_openo_sdno_overlayvpn_model_tenant_tenantmo", result);
    }

    @Test
    public void testGetRelationField() {
        TestMoModelPro mo = new TestMoModelPro();
        List<Field> result = MOModelProcessor.getRelationField(mo.getClass());

        assertEquals("listValue", result.get(0).getName());
    }

    @Test
    public void testIsCompositionRelationField() {
        TestMoModelPro mo = new TestMoModelPro();
        List<Field> fields = JavaEntityUtil.getAllField(mo.getClass());

        assertFalse(MOModelProcessor.isCompositionRelationField(fields.get(0)));
        assertTrue(MOModelProcessor.isCompositionRelationField(fields.get(2)));
    }

    @Test
    public void testGetCompositionRelationField() {
        TestMoModelPro mo = new TestMoModelPro();
        List<Field> result = MOModelProcessor.getCompositionRelationField(mo.getClass());

        assertEquals("listValue", result.get(0).getName());
    }

    private class TestUuid {

        @MOUUIDField
        private String id;

        public TestUuid() {
            id = "0";
        }
    }

    private class TestMoModelPro {

        @MOExtendField
        private int value;

        @MOExtendField
        private Map<String, String> mapValue;

        @MORelationField
        private List<String> listValue;

        /**
         * Constructor.<br/>
         * 
         * @since SDNO 0.5
         */
        public TestMoModelPro() {
            value = 0;

            listValue = new ArrayList<String>();
        }

        /**
         * @return Returns the value.
         */
        @SuppressWarnings("unused")
        public int getValue() {
            return value;
        }

        /**
         * @param value The value to set.
         */
        @SuppressWarnings("unused")
        public void setValue(int value) {
            this.value = value;
        }

        /**
         * @return Returns the mapValue.
         */
        @SuppressWarnings("unused")
        public Map<String, String> getMapValue() {
            return mapValue;
        }

        /**
         * @param mapValue The mapValue to set.
         */
        public void setMapValue(Map<String, String> mapValue) {
            this.mapValue = mapValue;
        }

        /**
         * @return Returns the listValue.
         */
        public List<String> getListValue() {
            return listValue;
        }

        /**
         * @param listValue The listValue to set.
         */
        public void setListValue(List<String> listValue) {
            this.listValue = listValue;
        }

    }
}
