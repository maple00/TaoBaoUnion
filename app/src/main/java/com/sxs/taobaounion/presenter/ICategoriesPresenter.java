package com.sxs.taobaounion.presenter;

import com.sxs.taobaounion.base.IBasePresenter;
import com.sxs.taobaounion.view.ICategoryPagerBallBack;

/**
 * @Author: a797s
 * @Date: 2020/4/20 13:09
 * @Desc: 分类id
 */
public interface ICategoriesPresenter extends IBasePresenter<ICategoryPagerBallBack> {

    /**
     * 根据分类id获取内容
     */
    void getContentByCategoryId(int categoryId);

    void loadMore(int materialId);

    void reload();
}
