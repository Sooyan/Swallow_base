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

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.WeakHashMap;

import soo.swallow.base.exception.ReasonException;

/**The SharedPreferences tools kit
 * Created by Soo.
 */
public final class SPUtils {

    private static Map<String, SharedPreferences> spReceiver = new WeakHashMap<String, SharedPreferences>();

    /**Get instance of SharedPreferences by particular directory and name.
     * @param context getSharedPreferences by context when the directory file is null
     * @param directoryFile The directory which is the parent of xml file
     * @param name The name of xml file
     * @param mod The permission of xml file for read or write
     * @return The instance of SharedPreferences
     * @throws ReasonException
     * @throws IllegalArgumentException
     */
    public static SharedPreferences getSharedPreferences(Context context, File directoryFile, String name, int mod) throws ReasonException {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Name must is not empty");
        }
        File tempFile = context.getDir("shared_prefs", Context.MODE_PRIVATE);
        if (directoryFile == null || directoryFile.equals(tempFile)) {
            return context.getSharedPreferences(name, mod);
        }
        if (!directoryFile.isDirectory()) {
            throw new IllegalArgumentException("directoryFile is not directory file");
        }
        if (!name.endsWith(".xml")) {
            name += ".xml";
        }
        File spFile = FileUtils.createFile(directoryFile, name, false);

        if (spFile == null || !spFile.exists()) {
            throw new RuntimeException("Can not create target file[" + name + "]which parent is[" + directoryFile + "]");
        }
        synchronized (spReceiver) {
            String spKey = spFile.getAbsolutePath();
            SharedPreferences instance = spReceiver.get(spKey);
            if (instance == null) {
                try {
                    Class<?> implCls = Class.forName("android.app.SharedPreferencesImpl");
                    Constructor<?> cons = implCls.getDeclaredConstructor(File.class, int.class);
                    cons.setAccessible(true);
                    instance = (SharedPreferences)cons.newInstance(spFile, mod);
                    spReceiver.put(spKey, instance);
                } catch (ClassNotFoundException e) {
                    throw new ReasonException("Failed, because:" + e.getMessage());
                } catch (NoSuchMethodException e) {
                    throw new ReasonException("Failed, because:" + e.getMessage());
                } catch (InstantiationException e) {
                    throw new ReasonException("Failed, because:" + e.getMessage());
                } catch (IllegalAccessException e) {
                    throw new ReasonException("Failed, because:" + e.getMessage());
                } catch (IllegalArgumentException e) {
                    throw new ReasonException("Failed, because:" + e.getMessage());
                } catch (InvocationTargetException e) {
                    throw new ReasonException("Failed, because:" + e.getMessage());
                }
            }
            return instance;
        }
    }

}
