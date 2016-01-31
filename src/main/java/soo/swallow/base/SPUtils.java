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
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by Soo.
 */
public class SPUtils {

    public static SharedPreferences getSharedPreferences(Context context, String name, int mod) {
        return null;
    }

    public static SharedPreferences getSharedPreferences(Context context, File dirFile, String name, int mod) {
        ArgsUtils.notNull(dirFile, "The directory of preference is null");
        if (!dirFile.isDirectory()) {
            throw new IllegalArgumentException("The file is not directory");
        }
        if (!FileUtils.createDir(dirFile, false)) {
//            TODO
        }
        try {
            Field mBaseField = ContextWrapper.class.getDeclaredField("mBase");
            mBaseField.setAccessible(true);
            Object mBaseObj = mBaseField.get(context);
            Field mPreferencesField = mBaseObj.getClass().getField("mPreferencesDir");
            mPreferencesField.setAccessible(true);

            Object originDirFile = mPreferencesField.get(mBaseObj);
            mPreferencesField.set(mBaseObj, dirFile);
            SharedPreferences sharedPreferences = context.getSharedPreferences(name, mod);
            mPreferencesField.set(mBaseObj, originDirFile);

            return sharedPreferences;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
