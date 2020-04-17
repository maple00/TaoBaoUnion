package com.sxs.taobaounion.presenter;

import com.sxs.taobaounion.view.IHomeCallback;

/**
 * @Author: a797s
 * @Date: 2020/4/17 14:30
 * @Desc:
 */
public interface IHomePresenter {

    /**
     * 获取商品分类
     */
    void getCategories();

    /**
     * 注册UI通知接口
     * @param callback
     */
    void registerCallback(IHomeCallback callback);

    /**
     * 取消UI的通知接口
     * @param callback
     */
    void unRegisterCallback(IHomeCallback callback);
}
