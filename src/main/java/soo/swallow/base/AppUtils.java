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

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**The kit of many general tools
 * Created by Soo.
 */
public class AppUtils {
    private static final String TAG = "AppUtils--->";

    /**Check whether the sdcard is available
     * @return True if the sdcard is available, otherwise return false
     */
    public static boolean sdcardIsAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**Check whether the app has been granted the permission of external storage
     * @param context The context of your app
     * @return True if app has been granted the permission of external storage, otherwise return false
     */
    public static boolean isGrantedExternalStoragePermission(Context context) {
        return isGrantedPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE");
    }

    /**Check whether the app has been granted permission
     * @param context The context of your app
     * @param permission The string of permission which you want to check
     * @return True if the permission has been granted, otherwise return false
     */
    public static boolean isGrantedPermission(Context context, String permission) {
        if (TextUtils.isEmpty(permission)) {
            return false;
        }
        String packageName = context.getPackageName();
        return PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(permission, packageName);
    }

    /**
     * Check whether the current invoking is in main-thread
     */
    public static void mustInMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("Must be in main-thread");
        }
    }

    public static boolean networkEnable(Context context) {
        return true;
    }

    public static boolean shouldShowRequestPermissionRationale(Context context, String permission) {
        if (context instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);
        }
        PackageManager packageManager = context.getPackageManager();
        Class<?> cls = packageManager.getClass();
        try {
            Method method = cls.getDeclaredMethod("shouldShowRequestPermissionRationale", String.class);
            method.setAccessible(true);
            Object result = method.invoke(packageManager, permission);
            return (boolean) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
