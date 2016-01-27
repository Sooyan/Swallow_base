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

import android.text.TextUtils;

import java.util.HashSet;
import java.util.Set;

/**Help to testing and coding app
 * Created by Soo.
 */
public final class Log {

    private static final String TAG = "--->";
    private static Logger logger;

    /**Init log with logger
     * @param logger The wrapper for android.util.Log
     * @see soo.swallow.base.Log.Logger
     */
    public static void init(Logger logger) {
        if (Log.logger == null) {
            Log.logger = logger;
        }
    }

    private Log(){/**empty*/}

    /**
     * Send a VERBOSE log msg.
     * @param msg The message you would like logged by default tag.
     */
    public static void v(String msg) {
        if (logger == null) {
            android.util.Log.v(TAG, msg);
        } else {
            logger.v(msg);
        }
    }

    /**Send a VERBOSE log msg which will be identity by specific tag
     * @param tag Use tag for identify the msg which you would like to log
     * @param msg The message you would like logged
     */
    public static void v(String tag, String msg) {
        if (logger == null) {
            android.util.Log.v(tag, msg);
        } else {
            logger.v(tag, msg);
        }
    }

    /**Send a VERBOSE log msg and exception which will be identity by specific tag
     * @param tag Use tag for identify the msg which you would like to log
     * @param msg The message you would like logged
     * @param throwable The exception you would like logged
     */
    public static void v(String tag, String msg, Throwable throwable) {
        if (logger == null) {
            android.util.Log.v(tag, msg, throwable);
        } else {
            logger.v(tag, msg, throwable);
        }
    }

    /**
     * Send a DEBUG log msg.
     * @param msg The message you would like logged by default tag.
     */
    public static void d(String msg) {
        if (logger == null) {
            android.util.Log.d(TAG, msg);
        } else {
            logger.d(msg);
        }
    }

    /**Send a DEBUG log msg which will be identity by specific tag
     * @param tag Use tag for identify the msg which you would like to log
     * @param msg The message you would like logged
     */
    public static void d(String tag, String msg) {
        if (logger == null) {
            android.util.Log.d(tag, msg);
        } else {
            logger.d(tag, msg);
        }
    }

    /**Send a DEBUG log msg and exception which will be identity by specific tag
     * @param tag Use tag for identify the msg which you would like to log
     * @param msg The message you would like logged
     * @param throwable The exception you would like logged
     */
    public static void d(String tag, String msg, Throwable throwable) {
        if (logger == null) {
            android.util.Log.d(tag, msg, throwable);
        } else {
            logger.d(tag, msg, throwable);
        }
    }

    /**
     * Send a INFO log msg.
     * @param msg The message you would like logged by default tag.
     */
    public static void i(String msg) {
        if (logger == null) {
            android.util.Log.i(TAG, msg);
        } else {
            logger.i(msg);
        }
    }

    /**Send a INFO log msg which will be identity by specific tag
     * @param tag Use tag for identify the msg which you would like to log
     * @param msg The message you would like logged
     */
    public static void i(String tag, String msg) {
        if (logger == null) {
            android.util.Log.i(tag, msg);
        } else {
            logger.i(tag, msg);
        }
    }

    /**Send a INFO log msg and exception which will be identity by specific tag
     * @param tag Use tag for identify the msg which you would like to log
     * @param msg The message you would like logged
     * @param throwable The exception you would like logged
     */
    public static void i(String tag, String msg, Throwable throwable) {
        if (logger == null) {
            android.util.Log.i(tag, msg, throwable);
        } else {
            logger.i(tag, msg, throwable);
        }
    }

    /**
     * Send a WARM log msg.
     * @param msg The message you would like logged by default tag.
     */
    public static void w(String msg) {
        if (logger == null) {
            android.util.Log.w(TAG, msg);
        } else {
            logger.w(msg);
        }
    }

    /**Send a WARM log msg which will be identity by specific tag
     * @param tag Use tag for identify the msg which you would like to log
     * @param msg The message you would like logged
     */
    public static void w(String tag, String msg) {
        if (logger == null) {
            android.util.Log.w(tag, msg);
        } else {
            logger.w(tag, msg);
        }
    }

    /**Send a WARM log msg and exception which will be identity by specific tag
     * @param tag Use tag for identify the msg which you would like to log
     * @param msg The message you would like logged
     * @param throwable The exception you would like logged
     */
    public static void w(String tag, String msg, Throwable throwable) {
        if (logger == null) {
            android.util.Log.w(tag, msg, throwable);
        } else {
            logger.w(tag, msg, throwable);
        }
    }

    /**
     * Send a ERROR log msg.
     * @param msg The message you would like logged by default tag.
     */
    public static void e(String msg) {
        if (logger == null) {
            android.util.Log.e(TAG, msg);
        } else {
            logger.e(msg);
        }
    }

    /**Send a ERROR log msg which will be identity by specific tag
     * @param tag Use tag for identify the msg which you would like to log
     * @param msg The message you would like logged
     */
    public static void e(String tag, String msg) {
        if (logger == null) {
            android.util.Log.e(tag, msg);
        } else {
            logger.e(tag, msg);
        }
    }

    /**Send a ERROR log msg and exception which will be identity by specific tag
     * @param tag Use tag for identify the msg which you would like to log
     * @param msg The message you would like logged
     * @param throwable The exception you would like logged
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (logger == null) {
            android.util.Log.e(tag, msg, throwable);
        } else {
            logger.e(tag, msg, throwable);
        }
    }

    /**
     * The core
     */
    public final static class Logger {

        private Builder builder;

        public Logger(Builder builder) {
            this.builder = builder;
        }

        public void v(String msg) {
            v(builder.defaultTag, msg);
        }

        public void v(String tag, String msg) {
            v(tag, msg, null);
        }

        public void v(String tag, String msg, Throwable throwable) {
            println(android.util.Log.VERBOSE, tag, msg, throwable);
        }

        public void d(String msg) {
            d(builder.defaultTag, msg);
        }

        public void d(String tag, String msg) {
            d(tag, msg, null);
        }

        public void d(String tag, String msg, Throwable throwable) {
            println(android.util.Log.DEBUG, tag, msg, throwable);
        }

        public void i(String msg) {
            i(builder.defaultTag, msg);
        }

        public void i(String tag, String msg) {
            i(tag, msg, null);
        }

        public void i(String tag, String msg, Throwable throwable) {
            println(android.util.Log.INFO, tag, msg, throwable);
        }

        public void w(String msg) {
            w(builder.defaultTag, msg);
        }

        public void w(String tag, String msg) {
            w(tag, msg, null);
        }

        public void w(String tag, String msg, Throwable throwable) {
            println(android.util.Log.WARN, tag, msg, throwable);
        }

        public void e(String msg) {
            e(builder.defaultTag, msg);
        }

        public void e(String tag, String msg) {
            e(tag, msg, null);
        }

        public void e(String tag, String msg, Throwable throwable) {
            println(android.util.Log.ERROR, tag, msg, throwable);
        }

        private void println(int priority, String tag, String msg, Throwable throwable) {
            if (builder.logAble && shouldLogWithPriority(priority)
                    && shouldLogWithTag(tag) && shouldLogWithClass()) {
                msg += throwable == null ? "" : ("\n" + android.util.Log.getStackTraceString(throwable));
                android.util.Log.println(priority, tag, msg);
            }
        }

        /**Judge logger is enable or not with priority
         * @return True is enable, otherwise return false;
         */
        private boolean shouldLogWithPriority(int priority) {
            switch (priority) {
                case android.util.Log.VERBOSE: return builder.vable;
                case android.util.Log.DEBUG: return builder.dable;
                case android.util.Log.INFO: return builder.iable;
                case android.util.Log.WARN: return builder.wable;
                case android.util.Log.ERROR: return builder.eable;
            }
            return true;
        }

        /**Judge logger is enable or not which tag of msg has been added into set
         * @return True is enable, otherwise return false;
         */
        private boolean shouldLogWithTag(String tag) {
            if (TextUtils.isEmpty(tag) || builder.tagSet.isEmpty()
                    || builder.tagSet.contains(tag)) {
                return true;
            }
            return false;
        }

        /**Judge logger is enable or not which name of caller class has been added into set
         * @return True is enable, otherwise return false;
         */
        private boolean shouldLogWithClass() {
            if (builder.clsSet.isEmpty()) {
                return true;
            }
            StackTraceElement[] stes = java.lang.Thread.currentThread().getStackTrace();
            try {
                String callerClassName = stes[8].getClassName();
                return builder.clsSet.contains(callerClassName);
            } catch (Exception e) {

            }
            return false;
        }

        /**
         * The logger builder
         */
        public final static class Builder {

            private String defaultTag;
            private boolean logAble = true;
            private boolean vable = true, dable = true, iable = true, wable = true, eable = true;
            private Set<String> tagSet = new HashSet<String>();
            private Set<String> clsSet = new HashSet<String>();

            public Builder() {

            }

            /**Set the default tag
             * @param tag The default tag
             * @return The instance of builder
             */
            public Builder setDefaultTag(String tag) {
                this.defaultTag = tag;
                return this;
            }

            /**Set the log enable or not
             * @param logAble
             * @return The instance of builder
             */
            public Builder setlogAble(boolean logAble) {
                this.logAble = logAble;
                return this;
            }

            /**Set the priority enable or not
             * @param vable The VERBOSE is enable or not
             * @param dable The DEBUG is enable or not
             * @param iable The INFO is enable or not
             * @param wable The WARM is enable or not
             * @param eable The ERROR is enable or not
             * @return The instance of builder
             */
            public Builder setLogLevelable(boolean vable, boolean dable,
                                           boolean iable, boolean wable, boolean eable) {
                this.vable = vable;
                this.dable = dable;
                this.iable = iable;
                this.wable = wable;
                this.eable = eable;
                return this;
            }

            /**Set a specific tag, logger will not log except the specific tag. Call this mathod will
             * clean tags before
             * @param tag Which tag will be logged only
             * @return The instance of builder
             */
            public Builder setLogOnlyByTag(String tag) {
                tagSet.clear();
                tagSet.add(tag);
                return this;
            }

            /**Add a specific tag, logger will log msg which identify by tags
             * @param tag Which tag will be logged only
             * @return The instance of builder
             */
            public Builder addLogOnlyByTag(String tag) {
                tagSet.add(tag);
                return this;
            }

            /**Set a specific class, logger will not log in other classes except this specific class.
             * Call this method will clean class before
             * @param cls The target class
             * @return The instance of builder
             */
            public Builder setLogOnlyByClass(Class<?> cls) {
                clsSet.clear();
                clsSet.add(cls.getName());
                return this;
            }

            /**Add a specific class, logger will not log in other classes except the specific classes
             * which have been added
             * @param cls The target class
             * @return The instance of builder
             */
            public Builder addLogOnlyByClass(Class<?> cls) {
                clsSet.add(cls.getName());
                return this;
            }

            /**Build a instance of logger
             * @return The instance of logger
             */
            public Logger build() {
                defaultTag = TextUtils.isEmpty(defaultTag) ? Log.TAG : defaultTag;
                return new Logger(this);
            }
        }
    }

}
