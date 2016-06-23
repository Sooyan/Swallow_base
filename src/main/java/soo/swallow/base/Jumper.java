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

import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * @author Soo
 */
public class Jumper {

    public static final String JUMPER_ANIMATION_BACK_IN = "Jumper_animation_back_in";
    public static final String JUMPER_ANIMATION_BACK_OUT = "Jumper_animation_back_out";

    private Intent intent = null;

    private int aheadInAnimation;
    private int aheadOutAnimation;

    private int backInAnimation;
    private int backOutAnimation;

    private Jumper() {
        this.intent = new Intent();
    }

    public static Jumper newJumper() {
        // TODO
        return new Jumper();
    }

    public Jumper putInt(String key, int value) {
        intent.putExtra(key, value);
        return this;
    }

    public Jumper putLong(String key, long value) {
        intent.putExtra(key, value);
        return this;
    }

    public Jumper putString(String key, String value) {
        intent.putExtra(key, value);
        return this;
    }

    public Jumper putSerializable(String key, Serializable value) {
        intent.putExtra(key, value);
        return this;
    }

    public Jumper putBoolean(String key, boolean value) {
        intent.putExtra(key, value);
        return this;
    }

    public Jumper setAheadInAnimation(int animation) {
        this.aheadInAnimation = animation;
        return this;
    }

    public Jumper setAheadOutAnimation(int animation) {
        this.aheadOutAnimation = animation;
        return this;
    }

    public Jumper setBackInAnimation(int animation) {
        this.backInAnimation = animation;
        return this;
    }

    public Jumper setBackOutAnimation(int animation) {
        this.backOutAnimation = animation;
        return this;
    }

    public Jumper setIntentFlag(int flag) {
        intent.setFlags(flag);
        return this;
    }

    public Jumper addIntentFlag(int flag) {
        intent.addFlags(flag);
        return this;
    }
    
    public void jump(Context packageContext, Class<?> cls) {
        intent.setClass(packageContext, cls);
        intent.putExtra(JUMPER_ANIMATION_BACK_IN, backInAnimation);
        intent.putExtra(JUMPER_ANIMATION_BACK_OUT, backOutAnimation);
        packageContext.startActivity(intent);
        if (packageContext instanceof Activity) {
            ((Activity) packageContext).overridePendingTransition(aheadInAnimation, aheadOutAnimation);
        }
    }
    
    public void jumpForResult(Context packageContext, Class<?> cls, int code) {
        intent.setClass(packageContext, cls);
        intent.putExtra(JUMPER_ANIMATION_BACK_IN, backInAnimation);
        intent.putExtra(JUMPER_ANIMATION_BACK_OUT, backOutAnimation);
        if (packageContext instanceof Activity) {
            ((Activity) packageContext).startActivityForResult(intent, code);
            ((Activity) packageContext).overridePendingTransition(aheadInAnimation, aheadOutAnimation);
        }
    }
    
    public void jump(Activity activity, Class<?> cls) {
        intent.setClass(activity, cls);
        intent.putExtra(JUMPER_ANIMATION_BACK_IN, backInAnimation);
        intent.putExtra(JUMPER_ANIMATION_BACK_OUT, backOutAnimation);
        activity.startActivity(intent);
        activity.overridePendingTransition(aheadInAnimation, aheadOutAnimation);
    }
    
    public void jumpForResult(Activity activity, Class<?> cls, int code) {
        intent.setClass(activity, cls);
        intent.putExtra(JUMPER_ANIMATION_BACK_IN, backInAnimation);
        intent.putExtra(JUMPER_ANIMATION_BACK_OUT, backOutAnimation);
        activity.startActivityForResult(intent, code);
        activity.overridePendingTransition(aheadInAnimation, aheadOutAnimation);
    }
    
    public void jump(Fragment fragment, Class<?> cls) {
        intent.setClass(fragment.getActivity(), cls);
        intent.putExtra(JUMPER_ANIMATION_BACK_IN, backInAnimation);
        intent.putExtra(JUMPER_ANIMATION_BACK_OUT, backOutAnimation);
        fragment.startActivity(intent);
        fragment.getActivity().overridePendingTransition(aheadInAnimation, aheadOutAnimation);
    }
    
    public void jumpForResult(Fragment fragment, Class<?> cls, int code) {
        intent.setClass(fragment.getActivity(), cls);
        intent.putExtra(JUMPER_ANIMATION_BACK_IN, backInAnimation);
        intent.putExtra(JUMPER_ANIMATION_BACK_OUT, backOutAnimation);
        fragment.startActivityForResult(intent, code);
        fragment.getActivity().overridePendingTransition(aheadInAnimation, aheadOutAnimation);
    }
    
    public void jump(Object obj, Class<?> cls) {
        if (obj instanceof Context) {
            if (obj instanceof Activity) {
                jump((Activity) obj, cls);
                return;
            }
            jump((Context) obj, cls);
        } else if (obj instanceof Fragment) {
            jump((Fragment) obj, cls);
        } else {
            throw new IllegalArgumentException("This obj(" + obj.getClass().getName() + ") can`t support jumping");
        }
    }
    
    public void jumpForResult(Object obj, Class<?> cls, int requestCode) {
        if (obj instanceof Context) {
            if (obj instanceof Activity) {
                jumpForResult((Activity) obj, cls, requestCode);
                return;
            }
            jumpForResult((Context) obj, cls, requestCode);
        } else if (obj instanceof Fragment) {
            jumpForResult((Fragment) obj, cls, requestCode);
        } else {
            throw new IllegalArgumentException("This obj(" + obj.getClass().getName() + ") can`t support jumping");
        }
    }
    
    public void clear() {
        // TODO
    }

}
