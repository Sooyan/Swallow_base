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

import android.os.Handler;
import android.os.Looper;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**A simple helper which can make sync or async very convenient
 * @author Soo
 */
public final class AsyncUtils {

    private static Map<Looper, AsyncUtils> pool = new WeakHashMap<Looper, AsyncUtils>();

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 3;

    private final ThreadFactory sThreadFactory = new ThreadFactory() {

        private final AtomicInteger count = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncUtils #" + count.getAndIncrement());
        }
    };

    private final Handler handler;
    private final Executor executor;

    /**Get the default instance with looper which belong to current thread
     * @return
     */
    public static AsyncUtils getDefault() {
        return getInstance(Looper.myLooper());
    }

    /**Get the main instance with looper which belong to main thread(it is ui thread)
     * @return
     */
    public static AsyncUtils getMainInstance() {
        return getInstance(Looper.getMainLooper());
    }

    /**Get the instance belong to special looper
     * @param looper
     * @return
     */
    public static AsyncUtils getInstance(Looper looper) {
        if (looper == null) {
            throw new NullPointerException("Looper must is not null");
        }
        synchronized (pool) {
            AsyncUtils instance = pool.get(looper);
            if (instance == null) {
                instance = new AsyncUtils(looper);
                pool.put(looper, instance);
            }
            return instance;
        }
    }

    AsyncUtils(Looper looper) {
        handler = new Handler(looper);
        executor = new ThreadPoolExecutor(CORE_POOL_SIZE,
                                          MAXIMUM_POOL_SIZE,
                                          KEEP_ALIVE,
                                          TimeUnit.SECONDS,
                                          new LinkedBlockingQueue<Runnable>(),
                                          sThreadFactory);
    }

    /**Execute a runnable in thread pool
     * @param runnable The runnable will be executed
     */
    public void execute(Runnable runnable) {
        if (runnable != null) executor.execute(runnable);
    }

    /**Post a runnable
     * @param runnable The runnable will be posted
     * @see #postDelay(Runnable, long)
     */
    public void post(Runnable runnable) {
        postDelay(runnable, 0);
    }

    /**Post a runnable to run after delay millis
     * @param runnable The runnable will be posted
     * @param delayMillis The delay time
     */
    public void postDelay(Runnable runnable, long delayMillis) {
        if (runnable != null) handler.postDelayed(runnable, delayMillis);
    }

    /**Post data(Object) to receiver
     * @param data The data what you want to post
     * @param receiver The data receiver
     * @param <T> The type of data
     * @see #postObject(Object, Receiver)
     * @see soo.swallow.base.AsyncUtils.Receiver
     */
    public <T> void postObject(T data, Receiver<T> receiver) {
        postObjectDelay(data, receiver, 0);
    }

    /**Post data(Object) to receiver after delay millis
     * @param data The data what you want to post
     * @param receiver The data receiver
     * @param delayMillis The delay time
     * @param <T> The type of data
     * @see soo.swallow.base.AsyncUtils.Receiver
     */
    public <T> void postObjectDelay(T data, Receiver<T> receiver, long delayMillis) {
        postDelay(new InnerReceiverRunnable<T>(data, receiver), 0);
    }

    /**Call onDo-method of ReceiverTask in async-thread
     * and post the result to onReceived-method of ReceiverTask in sync-thread
     * @param receiverTask The target
     * @param param The params will be used in onDo-method of ReceiverTask
     * @param <Param> The params
     * @param <Result> The value after onDo-method executed
     * @see #postObjectDelay(Object, Receiver, long)
     */
    public <Param, Result> void postReceiverTask(ReceiverTask<Param, Result> receiverTask,
                                                 Param param) {
        postReceiverTaskDelay(receiverTask, param, 0);
    }

    /**Call onDo-method of ReceiverTask in async-thread after delay millis
     * and post the result to onReceived-method of ReceiverTask in sync-thread
     * @param receiverTask The target
     * @param param The params will be used in onDo-method of ReceiverTask
     * @param delayMillis The delay time
     * @param <Param> The params
     * @param <Result> The value after onDo-method executed
     */
    public <Param, Result> void postReceiverTaskDelay(ReceiverTask<Param, Result> receiverTask,
                                                      Param param, long delayMillis) {
        if (receiverTask == null) {
            return;
        }
        execute(new InnerReceiverTaskRunnable<Param, Result>(this, receiverTask, param, delayMillis));
    }

    static class InnerReceiverRunnable<T> implements Runnable {

        final T data;
        final Receiver<T> receiver;

        public InnerReceiverRunnable(T data, Receiver<T> receiver) {
            this.data = data;
            this.receiver = receiver;
        }

        @Override
        public void run() {
            if (receiver != null) {
                receiver.onReceived(data);
            }
        }
    }

    static class InnerReceiverTaskRunnable<Param, Result> implements Runnable {

        final AsyncUtils asyncUtils;
        final ReceiverTask<Param, Result> receiverTask;
        final Param param;
        final long delayMillis;

        public InnerReceiverTaskRunnable(AsyncUtils asyncUtils, ReceiverTask<Param, Result> receiverTask,
                                         Param param, long delayMillis) {
            this.asyncUtils = asyncUtils;
            this.receiverTask = receiverTask;
            this.param = param;
            this.delayMillis = delayMillis;
        }

        @Override
        public void run() {
            if (receiverTask != null) {
                Result result = receiverTask.onDo(param);
                asyncUtils.postObjectDelay(result, receiverTask, delayMillis);
            }
        }
    }

    /**The data will be received by receiver
     * @param <T> The type of data
     */
    public interface Receiver<T> {
        void onReceived(T data);
    }

    /**This is task that onDo-method will be executed in async-thread,
     * and onReceived-method will be executed in sync-thread
     * @param <Param> The type of param
     * @param <Result> The type of result
     */
    public interface ReceiverTask<Param, Result> extends Receiver<Result> {
        Result onDo(Param param);
    }

}
