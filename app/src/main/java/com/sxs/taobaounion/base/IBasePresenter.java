package com.sxs.taobaounion.base;

/**
 * @Author: a797s
 * @Date: 2020/4/20 13:24
 * @Desc:
 */
public interface IBasePresenter<T> {

    /**
     * 注册UI通知接口
     * @param callback
     */
    void registerViewCallback(T callback);

    /**
     * 取消UI的通知接口
     * @param callback
     */
    void unRegisterViewCallback(T callback);
}
