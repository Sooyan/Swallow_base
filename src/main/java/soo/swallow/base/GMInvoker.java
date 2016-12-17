/*
 * Copyright 2015 Soo [154014022@qq.com | sootracker@gmail.com]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package soo.swallow.base;

import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Soo.
 */
public class GMInvoker<T> {
    private static final String TAG = "GMICompact--->";

    private Class<T> tClass;
    private String methodName;
    private T t;
    private Set<Object> objects;

    public static <T> GMInvoker<T> newInstance(Class<T> tClass) {
        return newInstance(tClass, null);
    }

    public static <T> GMInvoker<T> newInstance(Class<T> tClass, String methodName) {
        ArgsUtils.notNull(tClass, "Type of class");
        return new GMInvoker<T>(tClass, methodName);
    }

    private GMInvoker(Class<T> tClass, String methodName) {
        this.tClass = tClass;
        if (TextUtils.isEmpty(methodName)) {
            this.methodName = "set" + this.tClass.getSimpleName();
        } else {
            this.methodName = methodName;
        }
        this.objects = new HashSet<>();
    }

    public GMInvoker<T> make(Object...objects) {
        List<Object> objectList = Arrays.asList(objects);
        this.objects.addAll(objectList);
        makeInvoke(objectList);
        return this;
    }

    public GMInvoker<T> set(T t) {
        this.t = t;
        makeInvoke(objects);
        return this;
    }

    private void makeInvoke(Collection<Object> objects) {
        if (objects == null || objects.size() == 0) {
            return;
        }
        Iterator<Object> iterator = objects.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            invoke(object, methodName, tClass, t);
        }
    }

    private static <T> void invoke(Object object, String methodName, Class<T> tClass, T t) {
        Class<?> cls = object.getClass();
        try {
            Method method = cls.getMethod(methodName, tClass);
            method.invoke(object, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
