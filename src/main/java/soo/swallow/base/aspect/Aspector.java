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

package soo.swallow.base.aspect;

import android.text.TextUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import soo.swallow.base.ArgsUtils;
import soo.swallow.base.AsyncUtils;

/**
 * @author Soo.
 */
public class Aspector {
    private static final String TAG = "Aspect--->";

    public static <T> T aspect(Class<?> tClass, T delegate, Aspect aspect) {
        ArgsUtils.notNull(tClass, "Type");
        ArgsUtils.notNull(delegate, "Object of class");
        if (!tClass.isInterface()) {
            throw new IllegalArgumentException("Class of aspect must is interface");
        }
        try {
            Method aspectMethod = aspect.getClass().getMethod("aspect", Object[].class);
            AspectMode aspectMode = aspectMethod.getAnnotation(AspectMode.class);
            String name = aspectMode.method();
            if (TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException("The name of Aspect-Method must is not empty");
            }
            Class<?>[] parameters = aspectMode.types();
            Method destMethod = delegate.getClass().getMethod(name, parameters);
            boolean isAsync = aspectMode.async();
            if (isAsync && destMethod.getReturnType().equals(void.class)) {
                throw new IllegalArgumentException("Aspect async must have no return value");
            }

            return (T) Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
                    new Class[]{tClass}, new AspectInvocationHandler<>(delegate, aspect, parameters, isAsync));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to aspect", e);
        }
    }

    static class AspectInvocationHandler<T> implements InvocationHandler {

        private final T delegate;
        private final Aspect aspect;
        private final Class<?>[] parameterTypes;
        private final boolean isAsync;

        public AspectInvocationHandler(T delegate, Aspect aspect, Class<?>[] parameterTypes, boolean isAsync) {
            this.delegate = delegate;
            this.aspect = aspect;
            this.parameterTypes = parameterTypes;
            this.isAsync = isAsync;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(this, args);
            }

            Object[] newArgs = args;
            if (isMatch(parameterTypes, args)) {
                if (isAsync) {
                    AsyncUtils.getDefault().postReceiverTask(new InnerReceiverTask<>(method, delegate, aspect), newArgs);
                    return null;
                } else {
                    newArgs = aspect.aspect(newArgs);
                }
            }

            return method.invoke(delegate, newArgs);
        }

        static boolean isMatch(Class<?>[] parameterTypes, Object[] args) {
            return isSame(parameterTypes, getArgTypes(args));
        }

        static Class<?>[] getArgTypes(Object[] args) {
            int length = args.length;
            Class<?>[] types = new Class<?>[length];
            for (int i = 0; i < length; i++) {
                Object obj = args[i];
                types[i] = obj.getClass();
            }
            return types;
        }

        static boolean isSame(Class<?>[] types1, Class<?>[] types2) {
            if (types1.length != types2.length) {
                return false;
            }
            int length = types1.length;
            for (int i = 0; i < length; i++) {
                if (!types1[i].equals(types2[i])) {
                    return false;
                }
            }
            return true;
        }
    }

    static class InnerReceiverTask<T> implements AsyncUtils.ReceiverTask<Object[], Object[]> {

        final Method method;
        final T delegate;
        final Aspect aspect;

        InnerReceiverTask(Method method, T delegate, Aspect aspect) {
            this.method = method;
            this.delegate = delegate;
            this.aspect = aspect;
        }

        @Override
        public Object[] onDo(Object[] objects) {
            return aspect.aspect(objects);
        }

        @Override
        public void onReceived(Object[] data) {
            try {
                method.invoke(delegate, data);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
