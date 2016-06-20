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

package soo.swallow.base.lang;

/**
 * @author Soo.
 */
public abstract class ExtRunnable<T> implements Runnable {
    private static final String TAG = "ExtRunnable--->";

    private final T params;

    public ExtRunnable(T params) {
        this.params = params;
    }

    public final T getParams() {
        return params;
    }

    @Override
    public final void run() {
        run(params);
    }

    protected abstract void run(T params);
}
