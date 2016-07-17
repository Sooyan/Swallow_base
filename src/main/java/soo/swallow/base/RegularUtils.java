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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Soo.
 */
public class RegularUtils {
    private static final String TAG = "RegularUtils--->";

    public static boolean isMobile(String str) {
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isOkPwd(String str) {
        Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
