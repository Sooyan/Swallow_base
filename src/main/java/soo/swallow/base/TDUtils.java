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

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Soo.
 */
public class TDUtils {
    private static final String TAG = "TDUtils--->";

    public static int getTimePercent(long startTime, long tagTime, long endTime) {
        if (tagTime >= endTime) {
            return 100;
        }
        if (startTime >= endTime) {
            return 0;
        }
        if (startTime >= tagTime) {
            return 0;
        }

        BigDecimal left = BigDecimal.valueOf(tagTime - startTime);
        BigDecimal right = BigDecimal.valueOf(endTime - startTime);

        BigDecimal p = left.divide(right, 3, BigDecimal.ROUND_HALF_EVEN);
        float pf = p.floatValue() * 100;

        return (int) pf;
    }

    public static int[] getInterval(long leftTime, long rightTime) {
        if (leftTime > rightTime) {
            throw new IllegalArgumentException("The right time must super than left time");
        }
        Calendar startC = Calendar.getInstance();
        startC.setTimeInMillis(leftTime);

        Calendar tagC = Calendar.getInstance();
        tagC.setTimeInMillis(rightTime);

        int startY = startC.get(Calendar.YEAR);
        int startM = startC.get(Calendar.MONTH) + 1;
        int startD = startC.get(Calendar.DAY_OF_MONTH);
        int startH = startC.get(Calendar.HOUR_OF_DAY);
        int startF = startC.get(Calendar.MINUTE);
        int startS = startC.get(Calendar.SECOND);

        int tagY = tagC.get(Calendar.YEAR);
        int tagM = tagC.get(Calendar.MONTH) + 1;
        int tagD = tagC.get(Calendar.DAY_OF_MONTH);
        int tagH = tagC.get(Calendar.HOUR_OF_DAY);
        int tagF = tagC.get(Calendar.MINUTE);
        int tagS = tagC.get(Calendar.SECOND);

        int dS = tagS - startS;
        if (dS < 0) {
            tagF--;
            dS += 60;
        }

        int dF = tagF - startF;
        if (dF < 0) {
            tagH--;
            dF += 60;
        }

        int dH = tagH - startH;
        if (dH < 0) {
            tagD--;
            dH += 24;
        }

        int dD = tagD - startD;
        if (dD < 0) {
            tagM--;
            tagC.add(Calendar.MONTH, -1);
            dD += tagC.getActualMaximum(Calendar.DAY_OF_MONTH);
            tagC.add(Calendar.MONTH, 1);
        }

        int dM = tagM - startM;
        if (dM < 0) {
            tagY--;
            dM += tagC.getActualMaximum(Calendar.MONTH);
        }

        int dY = tagY - startY;

        if (dY < 0) {
            throw new IllegalArgumentException("Time is error");
        }

        return new int[]{dY, dM, dD, dH, dF, dS};
    }
}
