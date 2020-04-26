package com.sxs.taobaounion.base;

import android.app.Application;
import android.content.Context;

/**
 * @Author: a797s
 * @Date: 2020/4/26 16:24
 * @Desc:
 */
public class BaseApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static Context getAppContext() {
        return appContext;
    }
}
