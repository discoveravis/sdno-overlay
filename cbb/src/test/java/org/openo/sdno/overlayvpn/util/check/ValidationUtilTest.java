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

package org.openo.sdno.overlayvpn.util.check;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;

import org.junit.Before;
import org.junit.Test;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.overlayvpn.model.servicemodel.OverlayVpn;
import org.openo.sdno.resource.ResourceUtil;

import mockit.Mock;
import mockit.MockUp;

/**
 * ValidationUtil test class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-15
 */
public class ValidationUtilTest {

    @Before
    public void setUp() throws Exception {
        new MockUp<ValidatorFactory>() {

            @Mock
            public Validator getValidator() {
                return new PriValidator();
            }
        };

        new MockUp<Validation>() {

            @Mock
            public ValidatorFactory buildDefaultValidatorFactory() {
                return new PriValidatorFactory();
            }
        };

        new MockUp<ResourceUtil>() {

            @Mock
            public String getMessage(String str) {
                return "ValidationUtilTest Message";
            }
        };
    }

    @Test
    public void testValidateModel() {
        try {
            ValidationUtil.validateModel(null);
        } catch(ServiceException e) {
            assertEquals(e.getMessage(), "validator model is null.");
        }
    }

    @Test
    public void testValidateModel2() {

        try {
            ValidationUtil.validateModel(new OverlayVpn());
        } catch(ServiceException e) {
            assertEquals(e.getId(), "overlayvpn.operation.paramter_invalid");
        }
    }

    @Test
    public void testValidateModel3() {
        List<OverlayVpn> list = new ArrayList<OverlayVpn>();
        list.add(new OverlayVpn("001"));
        list.add(new OverlayVpn("002"));
        try {
            ValidationUtil.validateModel(list);
        } catch(ServiceException e) {
            assertEquals(e.getId(), "overlayvpn.operation.paramter_invalid");
        }
    }

    private class PriValidator implements Validator {

        @Override
        public <T> Set<ConstraintViolation<T>> validate(T paramT, Class<?>... paramArrayOfClass) {
            PriConstraintViolation pcv = new PriConstraintViolation();
            Set<ConstraintViolation<T>> set = new HashSet<ConstraintViolation<T>>();
            set.add(pcv);
            return set;
        }

        @Override
        public <T> Set<ConstraintViolation<T>> validateProperty(T paramT, String paramString,
                Class<?>... paramArrayOfClass) {

            return null;
        }

        @Override
        public <T> Set<ConstraintViolation<T>> validateValue(Class<T> paramClass, String paramString,
                Object paramObject, Class<?>... paramArrayOfClass) {

            return null;
        }

        @Override
        public BeanDescriptor getConstraintsForClass(Class<?> paramClass) {

            return null;
        }

        @Override
        public <T> T unwrap(Class<T> paramClass) {

            return null;
        }

        @Override
        public ExecutableValidator forExecutables() {

            return null;
        }

    }

    private class PriValidatorFactory implements ValidatorFactory {

        @Override
        public Validator getValidator() {
            return new PriValidator();
        }

        @Override
        public ValidatorContext usingContext() {

            return null;
        }

        @Override
        public MessageInterpolator getMessageInterpolator() {

            return null;
        }

        @Override
        public TraversableResolver getTraversableResolver() {

            return null;
        }

        @Override
        public ConstraintValidatorFactory getConstraintValidatorFactory() {

            return null;
        }

        @Override
        public <T> T unwrap(Class<T> paramClass) {

            return null;
        }

        @Override
        public void close() {

        }

        @Override
        public ParameterNameProvider getParameterNameProvider() {

            return null;
        }
    }

    private class PriConstraintViolation implements ConstraintViolation {

        @Override
        public String getMessage() {

            return "PriConstraintViolation";
        }

        @Override
        public String getMessageTemplate() {

            return null;
        }

        @Override
        public Object getRootBean() {

            return null;
        }

        @Override
        public Class getRootBeanClass() {

            return null;
        }

        @Override
        public Object getLeafBean() {

            return null;
        }

        @Override
        public Path getPropertyPath() {

            return new PriPath();
        }

        @Override
        public Object getInvalidValue() {

            return null;
        }

        @Override
        public ConstraintDescriptor getConstraintDescriptor() {

            return null;
        }

        @Override
        public Object[] getExecutableParameters() {

            return null;
        }

        @Override
        public Object getExecutableReturnValue() {

            return null;
        }

        @Override
        public Object unwrap(Class arg0) {

            return null;
        }

    }

    private class PriPath implements Path {

        @Override
        public Iterator<Node> iterator() {
            // TODO Auto-generated method stub
            return null;
        }

    }
}
