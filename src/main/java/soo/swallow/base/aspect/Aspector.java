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


    public static <T> T aspect(Class<?> interfaceClass, T interfaceDelegate, Aspect aspect) {
        ArgsUtils.notNull(interfaceClass, "Interface Class");
        ArgsUtils.notNull(interfaceDelegate, "Delegate of class");
        ArgsUtils.notNull(aspect, "Aspect");
        if (!interfaceClass.isInterface()) {
            throw new IllegalArgumentException("Class of aspect must is interface");
        }
        if (!isInstanceOfType(interfaceDelegate.getClass(), interfaceClass)) {
            throw new IllegalArgumentException("Delegate must is instance of " + interfaceClass.getName());
        }
        try {
            Method aspectMethod = aspect.getClass().getMethod("aspect", Object[].class);
            AspectMode aspectMode = aspectMethod.getAnnotation(AspectMode.class);
            return (T) Proxy.newProxyInstance(interfaceDelegate.getClass().getClassLoader(),
                    new Class[]{interfaceClass}, new AspectInvocationHandler2<>(interfaceDelegate, aspect, aspectMode));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to aspect", e);
        }
    }

    private static boolean isInstanceOfType(Class<?> destClass, Class<?> typeClass) {
        if (destClass == null || typeClass == null) {
            return false;
        }
        if (destClass.equals(typeClass)) {
            return true;
        }
        if (destClass.equals(Object.class)) {
            return false;
        }
        Class<?> [] interfaces = destClass.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> cls : interfaces) {
                return isInstanceOfType(cls, typeClass);
            }
        }
        return isInstanceOfType(destClass.getSuperclass(), typeClass);
    }

    static class AspectInvocationHandler2<T> implements InvocationHandler {

        final T delegate;
        final Aspect aspect;
        final AspectMode aspectMode;

        AspectInvocationHandler2(T delegate, Aspect aspect, AspectMode aspectMode) {
            this.delegate = delegate;
            this.aspect = aspect;
            this.aspectMode = aspectMode;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isMatch(method, args)) {
                InnerReceiverTask<T> task = new InnerReceiverTask<>(method, delegate, aspect);
                boolean isAsync = aspectMode.async();
                if (isAsync) {
                    AsyncUtils.getDefault().postReceiverTask(task, args);
                    return args;
                } else {
                    try {
                        task.onDo(args);
                    } finally {}
                }
            }
            return method.invoke(delegate, args);
        }

        private boolean isMatch(Method method, Object[] args) {
            String methodName = method.getName();
            String aspectName = aspectMode.method();
            if (!methodName.contains(aspectName)) {
                return false;
            }
            Class<?>[] aspectParametersTypes = aspectMode.types();
            if (aspectParametersTypes.length != args.length) {
                return false;
            }
            int length = aspectParametersTypes.length;
            for (int i = 0; i < length; i++) {
                Object obj = args[i];
                Class<?> objClass = obj.getClass();
                Class<?> parametersClass = aspectParametersTypes[i];
                if (!objClass.equals(parametersClass)) {
                    return false;
                }
            }
            return true;
        }
    }

    static class InnerReceiverTask<T> implements AsyncUtils.ReceiverTask<Object[], Void> {

        final Method method;
        final T delegate;
        final Aspect aspect;

        Object[] args;

        InnerReceiverTask(Method method, T delegate, Aspect aspect) {
            this.method = method;
            this.delegate = delegate;
            this.aspect = aspect;
        }

        @Override
        public Void onDo(Object[] objects) {
            args = objects;
            try {
                aspect.aspect(args);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        }

        @Override
        public void onReceived(Void data) {
            try {
                method.invoke(delegate, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
