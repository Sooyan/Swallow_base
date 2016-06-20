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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.LinkedBlockingQueue;

import soo.swallow.base.Log;

/**
 * Created by Soo.
 */
public class Processor implements Runnable {

    private Thread innerThread;
    private LinkedBlockingQueue<Processor.Task> taskQueue;
    private boolean isRunning;

    private Processor next;

    public static Processor newProcessor(int steps) {
        Processor head = null;
        Processor temp = null;
        for (int i = 0; i < steps; i++) {
            Processor p = new Processor();
            if (temp == null) {
                temp = p;
                head = temp;
            } else {
                temp.next = p;
                temp = p;
            }
        }
        return head;
    }

    private Processor() {
        this.taskQueue = new LinkedBlockingQueue<Task>();
    }

    public synchronized void process(Task task) {
        if (task == null) {
            throw new NullPointerException("task is null");
        }
        try {
            taskQueue.put(task);
            prepare();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void prepare() {
        if (!isRunning) {
            isRunning = true;
            innerThread = new Thread(this);
            innerThread.start();
        }
    }

    public synchronized void cut() {
        if (next != null) {
            next.cut();
        }
        isRunning = false;
        if (innerThread != null) {
            innerThread.interrupt();
        }
    }

    @Override
    public void run() {
        while (isRunning && !taskQueue.isEmpty()) {
            try {
                Task task = taskQueue.take();
                if (task.isIntercepte) {
                    continue;
                }
                task.onDo();
                if (next != null && !task.isIntercepte) {
                    next.process(task);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                if (!isRunning) {
                    break;
                }
            }
        }
        isRunning = false;
    }

    public static abstract class Task {

        private int step;
        private boolean isIntercepte;

        private void onDo() {
            step++;
            try {
                Method method = this.getClass().getDeclaredMethod("do_" + step);
                method.setAccessible(true);
                method.invoke(this);
                Log.d("invod:" + method.getName());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        public int getStep() {
            return step;
        }

        public void intercepte() {
            this.isIntercepte = true;
        }
    }
}
