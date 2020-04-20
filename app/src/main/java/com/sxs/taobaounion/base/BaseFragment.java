package com.sxs.taobaounion.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sxs.taobaounion.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Author: a797s
 * @Date: 2020/4/17 11:06
 * @Desc: fragment基类
 */
public abstract class BaseFragment extends Fragment {

    protected final String TAG = getClass().getSimpleName();
    private View mLoadingView;
    private View mSuccessView;
    private View mErrorView;
    private View mEmptyView;


    private State currentState = State.NONE;
    private Unbinder mBind;

    public enum State {
        NONE, LOADING, SUCCESS, ERROR, EMPTY
    }

    private FrameLayout mBaseContainer;

    /**
     * 点击网络重试
     */
    @OnClick(R.id.ll_network_error_tips)
    public void retry(View view) {
        switch (view.getId()) {
            case R.id.ll_network_error_tips:
                // LogUtils.d(this, "on retry...");
                onRetryOnClick();
                break;
        }
    }


    /**
     * 如果子类fragment需要知道 网络错误之后的点击，覆盖方法即可
     */
    protected void onRetryOnClick() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = LoadRootView(inflater, container);
        mBaseContainer = rootView.findViewById(R.id.fl_state_container);
        loadStateView(inflater, container);
        // ViewBind.inject(this, rootView);
        mBind = ButterKnife.bind(this, rootView);
        initView(rootView);
        initPresenter();
        loadData();
        return rootView;
    }

    protected View LoadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_base_state, container, false);
    }

    protected abstract int getLayoutId();

    protected void initView(View rootView) {
        // 初始化控件
    }

    protected void release() {
        // 释放资源
    }

    protected void initPresenter() {
        // 创建presenter
    }

    protected void loadData() {
        // 加载数据
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null) {
            mBind.unbind();
        }
        release();
    }

    /**
     * 加载各种状态的View
     *
     * @param inflater
     * @param container
     */
    private void loadStateView(LayoutInflater inflater, ViewGroup container) {
        // 成功的view、
        mSuccessView = loadSuccessView(inflater, container);
        mBaseContainer.addView(mSuccessView);
        // LoadingView
        mLoadingView = loadingView(inflater, container);
        mBaseContainer.addView(mLoadingView);
        // errorView
        mErrorView = loadErrorView(inflater, container);
        mBaseContainer.addView(mErrorView);
        // emptyView
        mEmptyView = loadEmptyView(inflater, container);
        mBaseContainer.addView(mEmptyView);
        setUpState(State.NONE);
    }

    /**
     * 供子类调用的状态页面
     *
     * @param state
     */
    public void setUpState(State state) {
        this.currentState = state;
        mSuccessView.setVisibility(currentState == State.SUCCESS ? View.VISIBLE : View.GONE);
        mLoadingView.setVisibility(currentState == State.LOADING ? View.VISIBLE : View.GONE);
        mErrorView.setVisibility(currentState == State.ERROR ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility(currentState == State.EMPTY ? View.VISIBLE : View.GONE);
    }

    /**
     * Success View
     *
     * @param inflater
     * @param container
     * @return
     */
    protected View loadSuccessView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    /**
     * 加载中view、
     *
     * @param inflater
     * @param container
     */
    protected View loadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_loading_view, container, false);
    }

    /**
     * Error View
     *
     * @param inflater
     * @param container
     * @return
     */
    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_error_view, container, false);
    }

    /**
     * Empty View
     *
     * @param inflater
     * @param container
     * @return
     */
    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_empty_view, container, false);
    }
}
