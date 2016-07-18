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

import soo.swallow.base.AsyncUtils;

/**
 * @author Soo.
 */
public class Aspector {
    private static final String TAG = "Aspect--->";

    public static <T> T aspect(Class<T> tClass, T delegate, Aspect aspect) {
        return (T) Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
                new Class[]{tClass}, new AspectInvocationHandler(delegate, aspect, false));
    }

    public static <T> T aspectAsync(Class<T> tClass, T delegate, Aspect aspect) {
        return (T) Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
                new Class[]{tClass}, new AspectInvocationHandler(delegate, aspect, true));
    }

    static class AspectInvocationHandler<T> implements InvocationHandler {

        private final T delegate;
        private final Aspect aspect;
        private final boolean isAsync;

        public AspectInvocationHandler(T delegate, Aspect aspect, boolean isAsync) {
            this.delegate = delegate;
            this.aspect = aspect;
            this.isAsync = isAsync;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Utils.isMatch(aspect, method)) {
                doAspect(method, args);
            } else {
                method.invoke(delegate, args);
            }

            return null;
        }

        private void doAspect(Method method, Object...args) throws InvocationTargetException, IllegalAccessException {
            if (isAsync) {
                AsyncUtils.getDefault().postReceiverTask(new InnerReceiverTask<T>(method, delegate, aspect), args);
            } else {
                Object[] data = aspect.aspect(args);
                method.invoke(delegate, data);
            }
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
