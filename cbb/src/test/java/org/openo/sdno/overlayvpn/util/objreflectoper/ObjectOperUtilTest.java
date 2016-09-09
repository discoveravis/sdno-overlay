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

package org.openo.sdno.overlayvpn.util.objreflectoper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openo.sdno.overlayvpn.model.servicemodel.Gateway;
import org.openo.sdno.overlayvpn.util.objreflectoper.ObjectOperUtil;
import org.openo.sdno.util.reflect.JavaEntityUtil;

/**
 * ObjectOperUtil test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class ObjectOperUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testFillNull() {
        Gateway tmp = new Gateway();
        tmp.setIpAddress("1.1.1.1");
        tmp.setNeId("neid");
        tmp.setDeviceId("deviceId");

        ObjectOperUtil.fillNull(tmp);
        assertNull(tmp.getIpAddress());
        assertNull(tmp.getNeId());
        assertNull(tmp.getDeviceId());
    }

    @Test
    public void testSetValueByMethod() {
        Gateway tmp = new Gateway();
        tmp.setIpAddress("1.1.1.1");
        tmp.setNeId("neid");
        tmp.setDeviceId("deviceId");
        List<Field> allField = JavaEntityUtil.getAllField(tmp.getClass());
        for(Field tempField : allField) {
            if(tempField.getName().equals("ipAddress")) {
                ObjectOperUtil.setValueByMethod(tmp, tempField, "setIpAddress", "0.0.0.0");
            }
        }

        assertEquals("0.0.0.0", tmp.getIpAddress());
    }

    @Test
    public void testSetValueByMethod2() {
        Gateway tmp = new Gateway();
        tmp.setNeId("neid");
        tmp.setDeviceId("deviceId");
        List<Field> allField = JavaEntityUtil.getAllField(tmp.getClass());
        for(Field tempField : allField) {
            if(tempField.getName().equals("ipAddress")) {
                ObjectOperUtil.setValueByMethod(tmp, tempField, "setIpAddress", "0.0.0.0");
            }
        }

        assertEquals("0.0.0.0", tmp.getIpAddress());
    }

    @Test
    public void testGetSetMethodByAttr() {
        assertEquals("setIpAddress", ObjectOperUtil.getSetMethodByAttr("ipAddress"));
    }

    @Test
    public void testGetSetMethodByAttr2() {
        assertNull(ObjectOperUtil.getSetMethodByAttr(null));
    }

    @Test
    public void testGetGetMethodByAttr() {
        assertEquals("getIpAddress", ObjectOperUtil.getGetMethodByAttr("ipAddress"));
    }

    @Test
    public void testGetGetMethodByAttr2() {
        assertNull(ObjectOperUtil.getGetMethodByAttr(null));
    }

}
