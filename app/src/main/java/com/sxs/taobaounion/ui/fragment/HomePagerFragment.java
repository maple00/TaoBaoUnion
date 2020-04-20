package com.sxs.taobaounion.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.sxs.taobaounion.R;
import com.sxs.taobaounion.base.BaseFragment;
import com.sxs.taobaounion.model.domain.Categories;
import com.sxs.taobaounion.model.domain.HomePagerBean;
import com.sxs.taobaounion.presenter.ICategoriesPresenter;
import com.sxs.taobaounion.presenter.impl.CategoryPagePresenterImpl;
import com.sxs.taobaounion.utils.Contants;
import com.sxs.taobaounion.utils.LogUtils;
import com.sxs.taobaounion.view.ICategoryPagerBallBack;

import java.util.List;

/**
 * @Author: a797s
 * @Date: 2020/4/17 15:48
 * @Desc:
 */
public final class HomePagerFragment extends BaseFragment implements ICategoryPagerBallBack {

    private ICategoriesPresenter mCategoriesPresenter;

    public static HomePagerFragment newInstance(Categories.DataBean catagory) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Contants.KEY_HOME_PAGER_TITLE, catagory.getTitle());
        bundle.putInt(Contants.KEY_HOME_PAGER_MATERIAL_ID, catagory.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
    }

    @Override
    protected void initPresenter() {
        mCategoriesPresenter = CategoryPagePresenterImpl.getInstance();
        mCategoriesPresenter.registerViewCallback(this);
    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        String title = bundle.getString(Contants.KEY_HOME_PAGER_TITLE);
        int materialId = bundle.getInt(Contants.KEY_HOME_PAGER_MATERIAL_ID);
        // TODO:加载数据：
        if (mCategoriesPresenter != null) {
            mCategoriesPresenter.getContentByCategoryId(materialId);
        }
        LogUtils.d(this, title);
        LogUtils.d(this, materialId + "");
    }

    @Override
    public void onContentLoaded(List<HomePagerBean.DataBean> contents, int categoryId) {
        // 数据列表加载
        // TODO: 更新UI
        LogUtils.d(this, "categoryId -== " + categoryId);
        setUpState(State.SUCCESS);
    }

    @Override
    public void onLoading(int categoryId) {
        setUpState(State.LOADING);
    }

    @Override
    public void onError(int categoryId) {
        // 网络错误
        setUpState(State.ERROR);
    }

    @Override
    public void onEmpty(int categoryId) {
        setUpState(State.EMPTY);
    }

    @Override
    public void onLoadMoreError(int categoryId) {

    }

    @Override
    public void onLoadMoreEmpty(int categoryId) {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerBean.DataBean> contents, int categoryId) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerBean.DataBean> contents, int categoryId) {

    }

    @Override
    public void registerViewCallback(Object callback) {

    }

    @Override
    public void unRegisterViewCallback(Object callback) {

    }

    @Override
    protected void release() {
        if (mCategoriesPresenter != null) {
            mCategoriesPresenter.unRegisterViewCallback(this);
        }
    }
}
