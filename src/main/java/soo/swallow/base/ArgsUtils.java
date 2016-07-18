/*
 * Copyright 2015 Soo [154014022@qq.com | sootracker@gmail.com]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package soo.swallow.base;

/**
 * Created by Soo.
 */
public class ArgsUtils {
    private static final String TAG = "ArgsUtils--->";

    /**Throw NullPointerException if the particular object is null
     * @param object The particular object
     * @see #notNull(Object, String)
     */
    public static void notNull(Object object) {
        notNull(object, null);
    }

    /**Throw NullPointerException if the particular object is null
     * @param object The particular object
     * @param message The message which to be wrapped into NullPointerException
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new NullPointerException(message == null ? "" : message);
        }
    }

    /**Throw IllegalArgumentException if the destination is not instance of particular class type
     * @param object The destination object
     * @param cls The particular class type
     */
    public static void mustInstanceOf(Object object, Class<?> cls) {
        notNull(object);
        notNull(cls);

        if (!cls.isInstance(object)) {
            throw new IllegalArgumentException(object.getClass() + "is not instance of " + cls);
        }
    }
}
