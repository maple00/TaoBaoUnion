package com.sxs.taobaounion.presenter.impl;

import com.sxs.taobaounion.model.Api;
import com.sxs.taobaounion.model.domain.HomePagerBean;
import com.sxs.taobaounion.presenter.ICategoriesPresenter;
import com.sxs.taobaounion.utils.LogUtils;
import com.sxs.taobaounion.utils.RetrofitManager;
import com.sxs.taobaounion.utils.UrlUtils;
import com.sxs.taobaounion.view.ICategoryPagerBallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @Author: a797s
 * @Date: 2020/4/20 13:33
 * @Desc:
 */
public class CategoryPagePresenterImpl implements ICategoriesPresenter {

    private Map<Integer, Integer> pageInfo = new HashMap<>();

    public static final int DEFAULT_PAGE = 1;

    public CategoryPagePresenterImpl() {

    }

    private static ICategoriesPresenter sInstance = null;

    public static ICategoriesPresenter getInstance() {
        if (sInstance == null) {
            sInstance = new CategoryPagePresenterImpl();
        }
        return sInstance;
    }

    @Override
    public void getContentByCategoryId(int categoryId) {
        // 根据分类id去加载内容
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        //
        Integer targetPage = pageInfo.get(categoryId);
        if (targetPage == null) {        // 如果传入的页码为空，则自动装箱
            targetPage = DEFAULT_PAGE;
            pageInfo.put(categoryId, targetPage);
        }

        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPage);
        LogUtils.d(this, "home -- page === " + homePagerUrl);
        Call<HomePagerBean> task = api.getHomePageContent(homePagerUrl);
        task.enqueue(new Callback<HomePagerBean>() {
            @Override
            public void onResponse(Call<HomePagerBean> call, Response<HomePagerBean> response) {
                int code = response.code();
                LogUtils.d(this, "code --> " + code);
                if (code == HttpsURLConnection.HTTP_OK){
                    HomePagerBean homePagerBean = response.body();
                    LogUtils.d(this, "pageContent -- >" + homePagerBean);
                    // 把数据给到UI更新
                    handleHomePageContentResult(homePagerBean, categoryId);
                }else {
                    // TODO:
                    handleNetWorkError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerBean> call, Throwable t) {
                LogUtils.d(this, "exception --> " + t.toString());
            }
        });
    }

    private void handleNetWorkError(int categoryId) {
        for (ICategoryPagerBallBack callback : callbacks) {
            callback.onError(categoryId);
        }
    }

    private void handleHomePageContentResult(HomePagerBean pagerBean, int categoryId) {
        // 通知UI层更新数据
        for (ICategoryPagerBallBack callback : callbacks) {
            if (pagerBean == null || pagerBean.getData().size() == 0) {
                callback.onEmpty(categoryId);
            }else {
                callback.onContentLoaded(pagerBean.getData(), categoryId);
            }
        }
    }

    @Override
    public void loadMore(int categoryId) {

    }

    @Override
    public void reload(int categoryId) {

    }

    private List<ICategoryPagerBallBack> callbacks = new ArrayList<>();

    @Override
    public void registerViewCallback(ICategoryPagerBallBack callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallback(ICategoryPagerBallBack callback) {
        callbacks.remove(callback);
    }
}
