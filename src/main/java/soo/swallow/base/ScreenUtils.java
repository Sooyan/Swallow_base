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

    private static ScreenUtils screenTools;

    private Context context;

    public static ScreenUtils touch(Context context) {
        ArgsUtils.notNull(context, "Context");
        return new ScreenUtils(context);
    }

    private ScreenUtils(Context context) {
        this.context = context.getApplicationContext();
    }

    public int dip2px(float f) {
        return (int) (0.5D + (double) (f * getDensity(context)));
    }

    public int dip2px(int i) {
        return (int) (0.5D + (double) (getDensity(context) * (float) i));
    }

    public int get480Height(int i) {
        return (i * getScreenWidth()) / 480;
    }

    public float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public int getScal() {
        return (100 * getScreenWidth()) / 480;
    }

    public int getScreenDensityDpi() {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public int getScreenHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public int getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    public float getXdpi() {
        return context.getResources().getDisplayMetrics().xdpi;
    }

    public float getYdpi() {
        return context.getResources().getDisplayMetrics().ydpi;
    }

    public int px2dip(float f) {
        float f1 = getDensity(context);
        return (int) (((double) f - 0.5D) / (double) f1);
    }

    public int px2dip(int i) {
        float f = getDensity(context);
        return (int) (((double) i - 0.5D) / (double) f);
    }
}
