package com.sxs.taobaounion.view;

import com.sxs.taobaounion.base.IBasePresenter;
import com.sxs.taobaounion.model.domain.HomePagerBean;

import java.util.List;

/**
 * @Author: a797s
 * @Date: 2020/4/20 13:11
 * @Desc: 分类pager
 */
public interface ICategoryPagerBallBack extends IBasePresenter {

    /**
     * 数据加载回来
     *
     * @param contents
     */
    void onContentLoaded(List<HomePagerBean.DataBean> contents, int categoryId);

    /**
     * 加载中
     *
     * @param categoryId
     */
    void onLoading(int categoryId);

    /**
     * 网络错误
     *
     * @param categoryId
     */
    void onError(int categoryId);

    /**
     * 数据为空
     *
     * @param categoryId
     */
    void onEmpty(int categoryId);

    /**
     * 网络错误
     *
     * @param categoryId
     */
    void onLoadMoreError(int categoryId);

    /**
     * 没有更多内容
     *
     * @param categoryId
     */
    void onLoadMoreEmpty(int categoryId);

    /**
     * 加载了更多的内容
     *
     * @param contents
     */
    void onLoaderMoreLoaded(List<HomePagerBean.DataBean> contents, int categoryId);

    /**
     * 轮播图内容
     *
     * @param contents
     */
    void onLooperListLoaded(List<HomePagerBean.DataBean> contents, int categoryId);
}
