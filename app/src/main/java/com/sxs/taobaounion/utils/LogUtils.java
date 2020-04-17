package com.sxs.taobaounion.utils;

import android.util.Log;

/**
 * @Author: a797s
 * @Date: 2020/4/17 11:58
 * @Desc: 日志管理
 */
public class LogUtils {

    private static int currentLev = 4;
    private static final int DEBUG_LEV = 4;
    private static final int INFO_LEV = 3;
    private static final int WARNING_LEV = 2;
    private static final int ERROR_LEV = 1;

    public static void d(Object object, String log) {
        if (currentLev >= DEBUG_LEV) {
            Log.d(object.getClass().getSimpleName(), log);
        }
    }

    public static void i(Object object, String log) {
        if (currentLev >= INFO_LEV) {
            Log.d(object.getClass().getSimpleName(), log);
        }
    }

    public static void w(Object object, String log) {
        if (currentLev >= WARNING_LEV) {
            Log.d(object.getClass().getSimpleName(), log);
        }
    }

    public static void e(Object object, String log) {
        if (currentLev >= ERROR_LEV) {
            Log.d(object.getClass().getSimpleName(), log);
        }
    }
}
