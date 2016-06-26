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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.view.View.OnClickListener;
/**
 * @author Soo
 */
public final class CallbackCompact<T> {
    
    private static Map<Class<?>, String> mapping = new HashMap<Class<?>, String>();
    
    static {
        mapping.put(OnClickListener.class, "setOnClickListener");
    }

    public static <T> CallbackCompact<T> wrapView(Class<T> classT, View...views) {
        return new CallbackCompact<T>(classT, views);
    }
    
    private Class<T> classT;
    private View[] views;
    
    private CallbackCompact(Class<T> classT, View...views){
        this.classT = classT;
        this.views = views;
    }
    
    public void addView(View...views) {
        if (views == null) {
            return;
        }
        if (this.views == null) {
            this.views = views;
        } else {
            int oldLength = this.views.length;
            int newLength = views.length;
            
            View[] newViews = new View[oldLength + newLength];
            
            System.arraycopy(this.views, 0, newViews, 0, oldLength);
            System.arraycopy(views, newLength, newViews, oldLength, newLength);
            this.views = newViews;
        }
    }
    
    public void setCallback(T callback) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    makeProperInvoke(view, callback, classT);
                }
            }
        }
    }
    
    private static <T> void makeProperInvoke(View view, T callback, Class<T> classT) {
        String methodName = mapping.get(classT);
        if (methodName == null) {
            methodName = "set" + classT.getSimpleName();
        }
        Class<?> classV = view.getClass();
        try {
            Method method = classV.getMethod(methodName, classT);
            method.invoke(view, callback);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
