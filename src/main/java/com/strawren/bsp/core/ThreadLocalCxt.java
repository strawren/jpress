/**
 * Copyright : http://www.strawren.com , since 2013
 * Project : jpess
 * $Id$
 * $Revision$
 * Last Changed by ZhouXushun at 2011-8-4 下午04:53:49
 * $URL$
 *
 * Change Log
 * Author Change Date Comments
 * -------------------------------------------------------------
 * ZhouXushun 2011-8-4 Initailized
 */

package com.strawren.bsp.core;

import java.util.HashMap;

/**
 * ThreadLocal上下文
 * 用于存储一些需要在一个线程里面用到的变量
 * 比如remoteIp, username, password
 */
public class ThreadLocalCxt {

    private static final ThreadLocal<ThreadLocalCxt> localContext = new ThreadLocal<ThreadLocalCxt>();

    private int count;
    private final HashMap<String, Object> values = new HashMap<String, Object>();

    private ThreadLocalCxt() {

    }

    /**
     * Sets the request serviceContext.
     *
     *
     */
    public static synchronized ThreadLocalCxt begin() {
        ThreadLocalCxt context = localContext.get();
        if (context == null) {
            context = new ThreadLocalCxt();
            localContext.set(context);
        }
        context.count++;
        return context;
    }

    /**
     * Returns the service request.
     */
    public static ThreadLocalCxt getContext() {
        return localContext.get();
    }

    /**
     * Add a object.
     */
    public void addValue(String key, Object value) {
        values.put(key, value);
    }

    /**
     * Gets a object.
     */
    public Object getValue(String key) {
        return values.get(key);
    }

    /**
     * Gets a header from the context.
     */
    public static Object getContextValue(String header) {
        ThreadLocalCxt context = localContext.get();

        if (context != null) {
            return context.getValue(header);
        }
        return null;
    }

    /**
     * Cleanup at the end of a request.
     */
    public static synchronized void end() {
        ThreadLocalCxt context = localContext.get();

        if (context != null && --context.count == 0) {
            context.values.clear();
            localContext.remove();
        }
    }
}
