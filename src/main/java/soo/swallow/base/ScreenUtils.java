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

import android.content.Context;

/**
 * @author Soo.
 */
public class ScreenUtils {
    private static final String TAG = "ScreenUtils--->";

    public static int px2dip(Context context, float value) {
        float density = getDensity(context);
        return (int) (((double) value - 0.5D) / (double) density);
    }

    public static int px2dip(Context context, int value) {
        float density = getDensity(context);
        return (int) (((double) value - 0.5D) / (double) density);
    }

    public static int dip2px(Context context, int value) {
        return dip2px(context, value * 1.0f);
    }

    public static int dip2px(Context context, float value) {
        return (int) (0.5D + (double) (value * getDensity(context)));
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static float getXdpi(Context context) {
        return context.getResources().getDisplayMetrics().xdpi;
    }

    public static float getYdpi(Context context) {
        return context.getResources().getDisplayMetrics().ydpi;
    }
}
