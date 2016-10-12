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

package org.openo.sdno.overlayvpn.security.interceptor;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.sdno.overlayvpn.security.authentication.HttpContext;
import org.openo.sdno.overlayvpn.security.authentication.TokenDataHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Intercepter of Restful Request.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 June 3, 2016
 */
public class RestfulProxyInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestfulProxyInterceptor.class);

    /**
     * Intercepter before.<br>
     * 
     * @param jp Join Point
     * @throws Throwable exception throws
     * @since SDNO 0.5
     */
    public void before(JoinPoint jp) throws ServiceException {
        LOGGER.debug("RestfulProxyInterceptor.before->enter before advice");
        // Get Method need to intercept
        MethodSignature signature = (MethodSignature)jp.getSignature();
        Method method = signature.getMethod();
        // parameter of intercepter
        Object[] args = jp.getArgs();

        RestfulParametes arg = this.getRestfulParametesArg(method, args);
        if(null == arg) {
            LOGGER.debug("RestfulProxyInterceptor.before->RestfulParametes arg is null");
        } else if(this.isTokenExisted(arg)) {
            LOGGER.debug("RestfulProxyInterceptor.before->token already exists");
        } else {
            // add token
            this.addToken2HttpHeader(arg);
            LOGGER.debug(
                    "RestfulProxyInterceptor.before->add token to header successfully, orignal url request is: {0}, method is:{1}",
                    new Object[] {TokenDataHolder.getRequestURI(), TokenDataHolder.getRequestMethod()});
        }
    }

    /**
     * Build Restful Parameters With Arguments.<br>
     * 
     * @param method Method Object
     * @param args Arguments List
     * @return Restful Parameters Constructed
     * @since SDNO 0.5
     */
    private RestfulParametes getRestfulParametesArg(Method method, Object[] args) {
        int i = 0;
        for(Class<?> type : method.getParameterTypes()) {
            if(type.equals(RestfulParametes.class)) {
                Object arg = args[i];
                if(arg instanceof RestfulParametes) {
                    return (RestfulParametes)arg;
                }
                break;
            }
            i++;
        }

        return null;
    }

    /**
     * Check Whether RestfulParametes contains token.<br>
     * 
     * @param restfulParametes Restful Parameter
     * @return true if RestfulParametes contains token, false otherwise
     * @since SDNO 0.5
     */
    private boolean isTokenExisted(RestfulParametes restfulParametes) {
        return StringUtils.hasLength(restfulParametes.getHttpContextHeader(HttpContext.X_AUTH_TOKEN));
    }

    /**
     * Add Token to HTTP Header.<br>
     * 
     * @param restfulParametes Restful Parameters
     * @since SDNO 0.5
     */
    private void addToken2HttpHeader(RestfulParametes restfulParametes) {
        String token = TokenDataHolder.getToken();
        restfulParametes.putHttpContextHeader(HttpContext.X_AUTH_TOKEN, token);
    }
}
