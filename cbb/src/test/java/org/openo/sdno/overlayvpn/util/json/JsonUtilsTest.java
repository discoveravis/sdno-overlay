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

package org.openo.sdno.overlayvpn.util.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.v2.basemodel.NvString;

public class JsonUtilsTest {

    @Test
    public void testGetJsonByKeyObject() throws ServiceException {
        NvString object = new NvString("test_id", "test_name", "test_value", "test_parent");
        String name = JsonUtils.getJsonByKey(JsonUtils.toJson(object), "name");
        assertEquals(name, object.getName());
    }

    @Test
    public void testGetJsonByKeyList() throws ServiceException {
        List<NvString> objectList = new ArrayList<>();
        NvString object = new NvString("test_name", "test_value", "test_parent");
        objectList.add(object);
        String name = JsonUtils.getJsonByKey(JsonUtils.toJson(objectList), "name");
        assertEquals(name, object.getName());
    }

    @Test
    public void testGetJsonByKeyEmpty() throws ServiceException {
        List<NvString> objectList = new ArrayList<>();
        NvString object = new NvString("test_name", "test_value");
        String name = JsonUtils.getJsonByKey(JsonUtils.toJson(objectList), "name");
        assertFalse(object.equals(name));
    }

    @Test
    public void testGetJsonByKeyString() throws ServiceException {
        String json = "String";
        String name = JsonUtils.getJsonByKey(json, "name");
        assertTrue(name == null);
    }

    @Test
    public void testParseJson2SimpleList() throws ServiceException {
        List<NvString> objectList = new ArrayList<>();
        NvString object = new NvString();
        object.allocateUuid();
        object.setName("test");
        object.setValue("test_value");
        object.setFirstParentUuid("test_parentId");
        objectList.add(object);
        List<NvString> result = JsonUtils.parseJson2SimpleList(JsonUtils.toJson(objectList), NvString.class);
        assertEquals(result.get(0), object);
    }

    @Test
    public void testFromJson() throws ServiceException {
        NvString object = new NvString();
        object.allocateUuid();
        object.setName("test");
        object.setValue("test_value");
        object.setFirstParentUuid("test_parentId");
        NvString result = JsonUtils.fromJson(JsonUtils.toJson(object), NvString.class);
        assertEquals(result, object);
    }

    @Test
    public void testFromJsonList() throws ServiceException {
        List<NvString> objectList = new ArrayList<>();
        NvString object = new NvString();
        object.allocateUuid();
        object.setName("test");
        object.setValue("test_value");
        object.setFirstParentUuid("test_parentId");
        objectList.add(object);
        List<NvString> result =
                JsonUtils.fromJson(JsonUtils.toJson(objectList), new TypeReference<List<NvString>>() {});
        assertEquals(result.get(0), object);
    }

    @Test
    public void testParseJson2MapList() throws ServiceException {
        List<Map<String, NvString>> objectList = new ArrayList<>();
        Map<String, NvString> objectMap = new HashMap<>();
        NvString object = new NvString();
        object.allocateUuid();
        object.setName("test");
        object.setValue("test_value");
        object.setFirstParentUuid("test_parentId");
        objectMap.put("nvString", object);
        objectList.add(objectMap);
        List<Map<String, Object>> result = JsonUtils.parseJson2MapList(JsonUtils.toJson(objectList));
        assertTrue(CollectionUtils.isNotEmpty(result));
    }

}
