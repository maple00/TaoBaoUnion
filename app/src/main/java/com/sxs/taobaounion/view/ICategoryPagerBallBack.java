package com.sxs.taobaounion.view;

import com.sxs.taobaounion.base.IBaseCallBack;
import com.sxs.taobaounion.model.domain.HomePagerBean;

import java.util.List;

/**
 * @Author: a797s
 * @Date: 2020/4/20 13:11
 * @Desc: 分类pager
 */
public interface ICategoryPagerBallBack extends IBaseCallBack {

    /**
     * 数据加载回来
     *
     * @param contents
     */
    void onContentLoaded(List<HomePagerBean.DataBean> contents);

    int getCategoryId();

    /**
     * 网络错误
     *
     */
    void onLoadMoreError();

    /**
     * 没有更多内容
     *
     */
    void onLoadMoreEmpty();

    /**
     * 加载了更多的内容
     *
     * @param contents
     */
    void onLoaderMoreLoaded(List<HomePagerBean.DataBean> contents);

    /**
     * 轮播图内容
     *
     * @param contents
     */
    void onLooperListLoaded(List<HomePagerBean.DataBean> contents);
}
