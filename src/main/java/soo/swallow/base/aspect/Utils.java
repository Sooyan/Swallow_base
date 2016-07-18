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

import java.lang.reflect.Method;

/**
 * @author Soo.
 */
public class Utils {
    private static final String TAG = "Utils--->";

    public static boolean isMatch(Aspect aspect, Method method) {
        String aspectName = getAspectName(aspect);
        Class<?>[] aspectParameters = getAspectParameters(aspect);
        String methodName = method.getName();
        Class<?>[] methodParameters = method.getParameterTypes();
        if (methodName.equals(aspectName)) {
            int aspectLength = aspectParameters.length;
            int methodLength = methodParameters.length;
            if (aspectLength == methodLength) {
                for (int i = 0; i < aspectLength; i++) {
                    Class<?> aspectClass = aspectParameters[i];
                    Class<?> methodClass = methodParameters[i];
                    if (!aspectClass.getName().equals(methodClass.getName())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static String getAspectName(Aspect aspect) {
        String name = null;
        try {
            Method method = aspect.getClass().getMethod("aspect");
            Mn mn = method.getAnnotation(Mn.class);
            name = mn.value();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public static Class<?>[] getAspectParameters(Aspect aspect) {
        Class<?>[] parameters = null;
        try {
            Method method = aspect.getClass().getMethod("aspect");
            Mn mn = method.getAnnotation(Mn.class);
            parameters = mn.parameters();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parameters;
    }

}
