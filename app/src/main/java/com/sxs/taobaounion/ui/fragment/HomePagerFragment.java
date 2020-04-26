package com.sxs.taobaounion.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.sxs.taobaounion.R;
import com.sxs.taobaounion.base.BaseFragment;
import com.sxs.taobaounion.model.domain.Categories;
import com.sxs.taobaounion.model.domain.HomePagerBean;
import com.sxs.taobaounion.presenter.ICategoriesPresenter;
import com.sxs.taobaounion.presenter.impl.CategoryPagePresenterImpl;
import com.sxs.taobaounion.ui.adapter.HomePagerContainerAdapter;
import com.sxs.taobaounion.ui.adapter.LooperPagerAdapter;
import com.sxs.taobaounion.ui.widget.TBNestedScrollView;
import com.sxs.taobaounion.utils.Contants;
import com.sxs.taobaounion.utils.LogUtils;
import com.sxs.taobaounion.utils.ToastUtil;
import com.sxs.taobaounion.view.ICategoryPagerBallBack;

import java.util.List;

import butterknife.BindView;

/**
 * @Author: a797s
 * @Date: 2020/4/17 15:48
 * @Desc:
 */
public final class HomePagerFragment extends BaseFragment implements ICategoryPagerBallBack {

    private ICategoriesPresenter mCategoriesPresenter;
    private int mMaterialId;

    @BindView(R.id.ll_pager_parent)
    public LinearLayout pagerParent;
    @BindView(R.id.rl_home_pager_container_list)
    public RecyclerView mContainerList;
    @BindView(R.id.vp_looper_pager)
    public ViewPager looperPager;

    @BindView(R.id.tv_pager_title)
    public TextView pagerTitle;

    @BindView(R.id.ll_looper_point)
    public LinearLayout looperPoint;

    @BindView(R.id.tbsv_scroll_view)
    public TBNestedScrollView pagerScrollView;

    @BindView(R.id.ll_top_pager)
    public LinearLayout topPager;

    @BindView(R.id.trl_home_pager_refresh)
    public TwinklingRefreshLayout pagerRefresh;

    private HomePagerContainerAdapter mContentAdapter;
    private LooperPagerAdapter mLooperPagerAdapter;

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
        // 布局管理器
        mContainerList.setLayoutManager(new LinearLayoutManager(getContext()));
        mContainerList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = 8;
                outRect.top = 8;
            }
        });
        // 创建适配器
        mContentAdapter = new HomePagerContainerAdapter();
        // 适配器
        mContainerList.setAdapter(mContentAdapter);
        // setUpState(State.SUCCESS);
        // 创建轮播图适配器
        mLooperPagerAdapter = new LooperPagerAdapter();
        // 设置适配器
        looperPager.setAdapter(mLooperPagerAdapter);
        // 设置refresh相关内容
        pagerRefresh.setEnableRefresh(false);
        pagerRefresh.setEnableLoadmore(true);
    }

    @Override
    protected void initListener(View rootView) {
        // list高度动态计算
        pagerParent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 动态设置top高度
                int topPagerMeasuredHeight = topPager.getMeasuredHeight();
                pagerScrollView.setHeadHeight(topPagerMeasuredHeight);
                //
                int measuredHeight = pagerParent.getMeasuredHeight();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mContainerList.getLayoutParams();
                layoutParams.height = measuredHeight;
                mContainerList.setLayoutParams(layoutParams);
                if (measuredHeight != 0){
                    pagerParent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        // 轮播图
        looperPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 滑动监听
            }

            @Override
            public void onPageSelected(int position) {
                // 数据监听
                if (mLooperPagerAdapter.getDataSize() == 0) {
                    return;
                }
                int targetPosition = position % mLooperPagerAdapter.getDataSize();
                // 切换指示器
                updateLooperIndicator(targetPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 加载监听
        pagerRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                LogUtils.d(this, "触发了加载更多");
                // 去加载更多的内容
                if (mCategoriesPresenter != null) {
                    mCategoriesPresenter.loadMore(mMaterialId);
                }
            }
        });
    }

    /**
     * 切换指示器
     *
     * @param position
     */
    private void updateLooperIndicator(int position) {
        LogUtils.d(this, "point ---- >" + position);
        for (int i = 0; i < looperPoint.getChildCount(); i++) {
            View point = looperPoint.getChildAt(i);
            if (i == position) {
                point.setBackgroundResource(R.drawable.shape_looper_point_selected);
            } else {
                point.setBackgroundResource(R.drawable.shape_looper_point_normal);
            }
        }
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
        mMaterialId = bundle.getInt(Contants.KEY_HOME_PAGER_MATERIAL_ID);
        // TODO:加载数据：
        if (mCategoriesPresenter != null) {
            mCategoriesPresenter.getContentByCategoryId(mMaterialId);
        }
        LogUtils.d(this, title);
        LogUtils.d(this, mMaterialId + "");
        if (title != null) {
            pagerTitle.setText(title);
        }
    }

    @Override
    public void onContentLoaded(List<HomePagerBean.DataBean> contents) {
        // 数据列表加载
        // TODO: 更新UI
        // LogUtils.d(this, "categoryId -== " + categoryId);
        mContentAdapter.setData(contents);
        setUpState(State.SUCCESS);
    }

    @Override
    public int getCategoryId() {
        return mMaterialId;
    }

    @Override
    public void OnLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void OnError() {
        // 网络错误
        setUpState(State.ERROR);
    }

    @Override
    public void OnEmpty() {
        setUpState(State.EMPTY);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtil.showToast("网络异常，请稍后重试");
        if (pagerRefresh != null) {
            pagerRefresh.finishLoadmore();
        }
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.showToast("没有更多的商品");
        if (pagerRefresh != null) {
            pagerRefresh.finishLoadmore();
        }
    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerBean.DataBean> contents) {
        LogUtils.d(this, "load more ---- >" + contents);
        mContentAdapter.addData(contents);
        if (pagerRefresh != null) {
            pagerRefresh.finishLoadmore();
        }
        ToastUtil.showToast("加载了" + (contents == null ? 0 : contents.size()) + "条数据");
    }

    @Override
    public void onLooperListLoaded(List<HomePagerBean.DataBean> contents) {
        LogUtils.d(this, "looper list -- > " + contents.size());
        mLooperPagerAdapter.setData(contents);
        // 中间点%数据的size不一定为0，所以显示的轮播图不一定是第一张图片
        int dx = (Integer.MAX_VALUE / 2) % contents.size();
        int targetPos = (Integer.MAX_VALUE / 2) - dx;
        // 设置轮播图中间点
        looperPager.setCurrentItem(targetPos);
        looperPoint.removeAllViews();
        // 添加轮播点
        for (int i = 0; i < contents.size(); i++) {
            // LogUtils.d(this, "content image url === >" + contents.get(i).getPict_url());
            View pointView = new View(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 20);
            layoutParams.leftMargin = 8;
            layoutParams.rightMargin = 8;
            pointView.setLayoutParams(layoutParams);
            if (i == 0) {
                pointView.setBackgroundResource(R.drawable.shape_looper_point_selected);
            } else {
                pointView.setBackgroundResource(R.drawable.shape_looper_point_normal);
            }
            looperPoint.addView(pointView);
        }
    }

    @Override
    protected void release() {
        if (mCategoriesPresenter != null) {
            mCategoriesPresenter.unRegisterViewCallback(this);
        }
    }
}
