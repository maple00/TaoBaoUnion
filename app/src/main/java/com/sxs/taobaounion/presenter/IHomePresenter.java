package com.sxs.taobaounion.presenter;

import com.sxs.taobaounion.base.IBasePresenter;
import com.sxs.taobaounion.view.IHomeCallback;

/**
 * @Author: a797s
 * @Date: 2020/4/17 14:30
 * @Desc:
 */
public interface IHomePresenter extends IBasePresenter<IHomeCallback> {

    /**
     * 获取商品分类
     */
    void getCategories();

}
