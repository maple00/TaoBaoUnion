package com.sxs.taobaounion.view;

import com.sxs.taobaounion.base.IBaseCallBack;
import com.sxs.taobaounion.model.domain.Categories;

/**
 * @Author: a797s
 * @Date: 2020/4/17 14:33
 * @Desc: 数据回调连接
 */
public interface IHomeCallback extends IBaseCallBack{

    /**
     * 获取商品分类
     *
     * @param categories
     */
    void onCategoriesLoaded(Categories categories);

}
