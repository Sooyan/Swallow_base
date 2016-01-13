package soo.swallow.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

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

}
