package com.sxs.taobaounion.utils;

import android.widget.Toast;

import com.sxs.taobaounion.base.BaseApplication;

/**
 * @Author: a797s
 * @Date: 2020/4/26 16:23
 * @Desc:
 */
public final class ToastUtil {

    private static Toast sToast;

    public static void showToast(String tips) {
        if (sToast == null) {
            sToast = Toast.makeText(BaseApplication.getAppContext(), tips, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(tips);
        }
        sToast.show();
    }
}
