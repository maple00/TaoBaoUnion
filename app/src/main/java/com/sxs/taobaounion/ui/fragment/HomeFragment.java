package com.sxs.taobaounion.ui.fragment;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sxs.taobaounion.R;
import com.sxs.taobaounion.base.BaseFragment;
import com.sxs.taobaounion.model.domain.Categories;
import com.sxs.taobaounion.presenter.IHomePresenter;
import com.sxs.taobaounion.presenter.impl.HomePresenterImpl;
import com.sxs.taobaounion.ui.adapter.HomePagerAdapter;
import com.sxs.taobaounion.utils.LogUtils;
import com.sxs.taobaounion.view.IHomeCallback;
import com.sxs.tools.annotation.ViewInject;

/**
 * @Author: a797s
 * @Date: 2020/4/17 10:51
 * @Desc: 首页
 */
public class HomeFragment extends BaseFragment implements IHomeCallback {

    @ViewInject(R.id.tl_indicator)
    private TabLayout mIndicator;
    @ViewInject(R.id.vp_pager)
    private ViewPager mHomePager;

    private IHomePresenter mHomePresenter;
    private HomePagerAdapter mHomePagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPresenter() {
        // 创建presenter
        mHomePresenter = new HomePresenterImpl();
        mHomePresenter.registerCallback(this);
    }

    @Override
    protected void initView(View rootView) {
        mIndicator.setupWithViewPager(mHomePager);
        // 给ViewPager设置适配器
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        mHomePager.setAdapter(mHomePagerAdapter);
    }

    @Override
    protected void loadData() {
        // setUpState(State.LOADING);
        // 加载数据
        mHomePresenter.getCategories();
    }

    @Override
    public void onCategoriesLoaded(Categories categories) {
        LogUtils.d(this, "111111111");
        // 加载数据回调
        if (categories != null){            // 防止数据还没有回来就取消了View
            mHomePagerAdapter.setCategories(categories);
        }
    }

//    @Override
//    public void OnLoading() {
//        setUpState(State.LOADING);
//    }
//
//    @Override
//    public void OnError() {
//        setUpState(State.ERROR);
//    }
//
//    @Override
//    public void OnEmpty() {
//        setUpState(State.EMPTY);
//    }

    @Override
    protected void release() {
        if (mHomePresenter != null) {
            mHomePresenter.unRegisterCallback(this);
        }
    }
}
