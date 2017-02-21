/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.overlayvpn.inventory.sdk.impl.persis.puer.transact.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openo.sdno.overlayvpn.errorcode.ErrorCode;
import org.openo.sdno.overlayvpn.result.ResultRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for transaction context operating.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
class TransactionContextOperate {

    Class<?> contextClass;

    TransactionOperateType operateType;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param contextClass Context class
     * @param operateType Operation type
     */
    TransactionContextOperate(Class<?> contextClass, TransactionOperateType operateType) {
        this.contextClass = contextClass;
        this.operateType = operateType;
    }

    @Override
    public int hashCode() {
        return this.contextClass.hashCode() * this.operateType.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof TransactionContextOperate) {
            TransactionContextOperate operate = (TransactionContextOperate)o;
            if(!this.contextClass.equals(operate.contextClass)) {
                return false;
            } else {
                return this.operateType.equals(operate.operateType);
            }
        }
        return false;
    }

}

/**
 * Transaction management class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class TransactionMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMgr.class);

    private static Map<TransactionContextOperate, List<Class<?>>> listenerMap =
            new HashMap<TransactionContextOperate, List<Class<?>>>();

    private static Map<String, List<Class<?>>> bundleListenerMap = new HashMap<String, List<Class<?>>>();

    private TransactionMgr() {

    }

    /**
     * Register transaction listener.<br>
     * 
     * @param bundle Bundle key of listener map
     * @param listenerClass Listener class
     * @since SDNO 0.5
     */
    public static void registerListener(String bundle, Class<?> listenerClass) {
        if(isITransactionListener(listenerClass)) {
            Annotation anno = listenerClass.getAnnotation(TransactionListenerAnnotation.class);
            if(anno != null) {
                Class<?> contextClass = ((TransactionListenerAnnotation)anno).contextClass();
                insertBundleMap(bundle, listenerClass);
                TransactionOperateType operateType = ((TransactionListenerAnnotation)anno).operateType();
                registerListener(bundle, contextClass, operateType, listenerClass);
            }
        }
    }

    private static void registerListener(String bundle, Class<?> contextClass, TransactionOperateType operateType,
            Class<?> listenerClass) {
        synchronized(listenerMap) {
            TransactionContextOperate key = new TransactionContextOperate(contextClass, operateType);
            if(listenerMap.containsKey(key)) {
                listenerMap.get(key).add(listenerClass);
            } else {
                List<Class<?>> listenerlist = new ArrayList<Class<?>>();
                listenerlist.add(listenerClass);
                listenerMap.put(key, listenerlist);
            }
        }
    }

    /**
     * Unregister transaction listener.<br>
     * 
     * @param bundle Bundle key of listener map
     * @since SDNO 0.5
     */
    public static void unregisterListener(String bundle) {
        List<Class<?>> listenerList = getBundleListener(bundle);
        synchronized(listenerMap) {
            Iterator<Entry<TransactionContextOperate, List<Class<?>>>> it = listenerMap.entrySet().iterator();
            while(it.hasNext()) {
                Entry<TransactionContextOperate, List<Class<?>>> entry = it.next();
                entry.getValue().removeAll(listenerList);
                if(entry.getValue().isEmpty()) {
                    it.remove();
                }
            }
        }
        removeBudleMap(bundle);
    }

    /**
     * Prepare transaction.<br>
     * 
     * @param context Transaction context
     * @return Prepare transaction result
     * @since SDNO 0.5
     */
    public static ResultRsp<?> prepare(ITransactionContext context) {
        List<ITransactionListener> successList = new ArrayList<ITransactionListener>();
        ResultRsp<?> result = ResultRsp.SUCCESS;
        try {
            createContextListener(context);
            for(ITransactionListener listener : getContextListener(context)) {
                result = listener.prepare(context);
                if(!result.isSuccess()) {
                    LOGGER.error(String.format("Listener %s failed in prepare, error no:%s",
                            listener.getClass().getName(), result.getErrorCode()));
                    return result;
                } else {
                    successList.add(listener);
                }
            }
            return ResultRsp.SUCCESS;
        } catch(Exception e) {
            LOGGER.warn("Failed to fire tranction.", e);
            result = new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
        } finally {
            if(!result.isSuccess()) {
                rollback(context, successList);
            }
        }
        return result;
    }

    /**
     * Fire transaction.<br>
     * 
     * @param context Transaction context
     * @return Fire transaction result
     * @since SDNO 0.5
     */
    public static ResultRsp<?> fire(ITransactionContext context) {
        try {
            for(ITransactionListener listener : getContextListener(context)) {
                ResultRsp<?> result = listener.fire(context);
                if(!result.isSuccess()) {
                    LOGGER.error(String.format("Listener %s failed in fire, error no:%s", listener.getClass().getName(),
                            result.getErrorCode()));
                    return result;
                }
            }
            return ResultRsp.SUCCESS;
        } catch(Exception e) {
            LOGGER.warn("Failed to fire tranction.", e);
        }
        return new ResultRsp(ErrorCode.OVERLAYVPN_FAILED);
    }

    /**
     * Roll back transaction.<br>
     * 
     * @param context Transaction context
     * @return Roll back transaction result
     * @since SDNO 0.5
     */
    public static ResultRsp<?> rollback(ITransactionContext context) {
        return rollback(context, getContextListener(context));
    }

    /**
     * Roll back transaction.<br>
     * 
     * @param context Transaction context
     * @param successList List of transaction listeners
     * @return Roll back transaction result
     * @since SDNO 0.5
     */
    public static ResultRsp<?> rollback(ITransactionContext context, List<ITransactionListener> successList) {
        for(ITransactionListener listener : successList) {
            ResultRsp<?> result = listener.rollback(context);
            if(!result.isSuccess()) {
                LOGGER.warn(String.format("Listener %s failed in rollback, error no:%s", listener.getClass().getName(),
                        result.getErrorCode()));
            }
        }
        return ResultRsp.SUCCESS;
    }

    /**
     * Commit transaction.<br>
     * 
     * @param context Transaction context
     * @return Commit transaction result
     * @since SDNO 0.5
     */
    public static ResultRsp<?> commit(ITransactionContext context) {
        for(ITransactionListener listener : getContextListener(context)) {
            ResultRsp<?> result = listener.commit(context);
            if(!result.isSuccess()) {
                LOGGER.warn(String.format("Listener %s failed in commit, error no:%s", listener.getClass().getName(),
                        result.getErrorCode()));
            }
        }
        return ResultRsp.SUCCESS;
    }

    /**
     * Get listener of transaction context.<br>
     * 
     * @param context Transaction context
     * @return Collection of transaction listeners
     * @since SDNO 0.5
     */
    static List<ITransactionListener> getContextListener(ITransactionContext context) {
        if(context instanceof CommonTransactionContext) {
            return ((CommonTransactionContext)context).listener;
        } else {
            return new ArrayList<ITransactionListener>();
        }
    }

    /**
     * Create listener for transaction context.<br>
     * 
     * @param context Transaction context
     * @since SDNO 0.5
     */
    static void createContextListener(ITransactionContext context) {
        synchronized(listenerMap) {
            if(context instanceof CommonTransactionContext) {
                CommonTransactionContext common = (CommonTransactionContext)context;
                TransactionContextOperate operate =
                        new TransactionContextOperate(context.getClass(), context.getOperateType());
                if(listenerMap.containsKey(operate)) {
                    for(Class<?> listenerClass : listenerMap.get(operate)) {
                        try {
                            common.listener.add((ITransactionListener)listenerClass.newInstance());
                        } catch(InstantiationException e) {
                            LOGGER.warn("Failed instance transaction listener for " + listenerClass.getName(), e);
                        } catch(IllegalAccessException e) {
                            LOGGER.warn("Failed instance transaction listener for " + listenerClass.getName(), e);
                        }
                    }
                }
            } else {
                LOGGER.warn(String.format("Context %s not inherit from CommonTransactionContext",
                        context.getClass().getName()));
            }
        }
    }

    private static boolean isITransactionListener(Class<?> cls) {
        for(Class<?> cl : cls.getInterfaces()) {
            if(cl.getName().equals(ITransactionListener.class.getName())) {
                return true;
            }
        }
        return false;
    }

    private static List<Class<?>> getBundleListener(String bundle) {
        synchronized(bundleListenerMap) {
            return bundleListenerMap.get(bundle);
        }
    }

    private static void insertBundleMap(String bundle, Class<?> listenerClass) {
        synchronized(bundleListenerMap) {
            List<Class<?>> listenerlist = bundleListenerMap.get(bundle);
            if(listenerlist == null) {
                List<Class<?>> list = new ArrayList<Class<?>>();
                list.add(listenerClass);
                bundleListenerMap.put(bundle, list);
            } else {
                listenerlist.add(listenerClass);
            }
        }
    }

    private static void removeBudleMap(String bundle) {
        synchronized(bundleListenerMap) {
            List<Class<?>> listenerlist = bundleListenerMap.get(bundle);
            if(listenerlist != null) {
                bundleListenerMap.remove(bundle);
            }
        }
    }
}
