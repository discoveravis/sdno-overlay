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

package org.openo.sdno.overlayvpn.verify.annotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

import org.junit.Test;
import org.openo.sdno.overlayvpn.verify.annotation.AString;
import org.openo.sdno.overlayvpn.verify.annotation.AStringValidator;

/*
 * Junit Test case to test the com.huawei.netmatrix.cbb.overlayvpn.verify.annotation.AStringValidator class
 */

public class AStringValidatorTest {

    @Mocked
    AString mock1;

    /*
     * To test the initialize() method with the scope.
     */
    @Test
    public void testInitializeWithScope() {
        new Expectations() {

            {
                mock1.min();
                result = 0;
                mock1.max();
                result = 255;
                mock1.require();
                result = true;
                mock1.forbiddenChar();
                result = "my test data";
                mock1.scope();
                result = "my,test,data";
            }
        };
        AStringValidator test = new AStringValidator();
        test.initialize(mock1);
        new Verifications() {

            {
                mock1.min();
                mock1.max();
                mock1.require();
                mock1.forbiddenChar();
                mock1.scope();
                times = 2;
            }
        };
    }

    /*
     * To test the initialize() method without setting the scope.
     */
    @Test
    public void testInitialize2WithoutScope() {
        new Expectations() {

            {
                mock1.min();
                result = 0;
                mock1.max();
                result = 255;
                mock1.require();
                result = true;
                mock1.forbiddenChar();
                result = "my test data";
                mock1.scope();
                result = "";
            }
        };
        AStringValidator test = new AStringValidator();
        test.initialize(mock1);
        new Verifications() {

            {
                mock1.min();
                mock1.max();
                mock1.require();
                mock1.forbiddenChar();
                mock1.scope();
                times = 1;
            }
        };
    }

    /*
     * To test the isValid() method by passing null value and setting isRequire as false.
     */
    @Test
    public void testIsValidNull() {
        Integer integer = null;
        AStringValidator test = new AStringValidator();
        assertTrue(test.isValid(integer, null));
    }

    /*
     * To test the isValid() method by passing null value and setting isRequire as true.
     */
    @Test
    public void testIsValidNullIsRequired() {
        new Expectations() {

            {
                mock1.min();
                result = 0;
                mock1.max();
                result = 255;
                mock1.require();
                result = true;
                mock1.forbiddenChar();
                result = "my test data";
                mock1.scope();
                result = "";
            }
        };
        AStringValidator test = new AStringValidator();
        test.initialize(mock1);
        Integer integer = null;
        assertFalse(test.isValid(integer, null));
    }

    /*
     * To test the isValid() method by passing an Integer value.
     */
    @Test
    public void testIsValidInteger() {
        Integer integer = new Integer(1);
        AStringValidator test = new AStringValidator();
        assertTrue(test.isValid(integer, null));

    }

    /*
     * To test the isValid() method by passing a String value.
     */
    @Test
    public void testIsValidString() {
        String stringValue = new String("Test");
        AStringValidator test = new AStringValidator();
        assertTrue(test.isValid(stringValue, null));
    }

    /*
     * To test the isValid() method by passing a an empty list.
     */
    @Test
    public void testIsValidList() {
        List<String> listValue = new ArrayList<String>();
        AStringValidator test = new AStringValidator();
        assertTrue(test.isValid(listValue, null));
    }

    /*
     * To test the isValid() method by passing a List of Integer values.
     */
    @Test
    public void testIsValidListInteger() {
        List<Integer> listValue = new ArrayList<Integer>();
        listValue.add(Integer.valueOf(3));
        AStringValidator test = new AStringValidator();
        assertTrue(test.isValid(listValue, null));
    }

    /*
     * To test the isValid() method by passing a List of String values.
     */
    @Test
    public void testIsValidListString() {
        List<String> listValue = new ArrayList<String>();
        listValue.add("Test");
        AStringValidator test = new AStringValidator();
        assertTrue(test.isValid(listValue, null));
    }

    /*
     * To test the isValid() method by passing the value which is out of the range.
     */
    @Test
    public void testIsValidListStringRange() {
        new Expectations() {

            {
                mock1.min();
                result = 0;
                mock1.max();
                result = 255;
                mock1.require();
                result = true;
                mock1.forbiddenChar();
                result = "my test data";
                mock1.scope();
                result = "1,2,3";
            }
        };

        List<Object> listValue = new ArrayList<Object>();
        listValue.add(String.valueOf("5"));
        AStringValidator test = new AStringValidator();
        test.initialize(mock1);
        assertFalse(test.isValid(listValue, null));
    }

    /*
     * To test the isValid() method by passing the value which is out of the range.
     */
    @Test
    public void testIsValidListIntegerRange() {
        new Expectations() {

            {
                mock1.min();
                result = 1;
                mock1.max();
                result = 5;
                mock1.require();
                result = true;
                mock1.forbiddenChar();
                result = "my test data";
                mock1.scope();
                result = "1,2,3";
            }
        };

        List<Object> listValue = new ArrayList<Object>();
        listValue.add(Integer.valueOf(5));
        AStringValidator test = new AStringValidator();
        test.initialize(mock1);
        assertFalse(test.isValid(listValue, null));
    }

    /*
     * To test the isValid() method by passing Object.
     */
    @Test
    public void testIsValidObject() {
        Object object = new Object();
        AStringValidator test = new AStringValidator();
        assertTrue(test.isValid(object, null));

    }

    /*
     * To test the verifyForbiddenchar() method.
     */
    @Test
    public void testVerifyForbiddencharForbiddenChar() {
        new Expectations() {

            {
                mock1.min();
                result = 1;
                mock1.max();
                result = 5;
                mock1.require();
                result = true;
                mock1.forbiddenChar();
                result = "#";
                mock1.scope();
                result = "";
            }
        };
        AStringValidator test = new AStringValidator();
        test.initialize(mock1);
        assertFalse(test.isValid("12#", null));

    }

}
