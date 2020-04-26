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
    private Integer mMCurrentPage;

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
        //
        Integer targetPage = pageInfo.get(categoryId);
        if (targetPage == null) {        // 如果传入的页码为空，则自动装箱
            targetPage = DEFAULT_PAGE;
            pageInfo.put(categoryId, targetPage);
        }
        // 根据分类id去加载内容
        Call<HomePagerBean> task = createTask(categoryId, targetPage);
        task.enqueue(new Callback<HomePagerBean>() {
            @Override
            public void onResponse(Call<HomePagerBean> call, Response<HomePagerBean> response) {
                int code = response.code();
                LogUtils.d(this, "code --> " + code);
                if (code == HttpsURLConnection.HTTP_OK) {
                    HomePagerBean homePagerBean = response.body();
                    LogUtils.d(this, "pageContent -- >" + homePagerBean);
                    // 把数据给到UI更新
                    handleHomePageContentResult(homePagerBean, categoryId);
                } else {
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

    private Call<HomePagerBean> createTask(int categoryId, Integer targetPage) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPage);
        LogUtils.d(this, "home -- page === " + homePagerUrl);
        return api.getHomePageContent(homePagerUrl);
    }

    @Override
    public void loadMore(int materialId) {
        // 加载更多的数据
        //1、拿到当前页面
        mMCurrentPage = pageInfo.get(materialId);
        if (mMCurrentPage == null) {
            mMCurrentPage = 1;
        }
        //2、页码++
        mMCurrentPage++;
        //3、加载数据
        Call<HomePagerBean> task = createTask(materialId, mMCurrentPage);
        //4、处理数据结果
        task.enqueue(new Callback<HomePagerBean>() {
            @Override
            public void onResponse(Call<HomePagerBean> call, Response<HomePagerBean> response) {
                int code = response.code();
                if (code == HttpsURLConnection.HTTP_OK){
                    HomePagerBean result = response.body();
                    handleLoadMoreResult(result, materialId);
                }else {
                    handleLoadMoreError(materialId);
                }
            }

            @Override
            public void onFailure(Call<HomePagerBean> call, Throwable t) {
                // 请求失败
                LogUtils.d(this, t.toString());
                handleLoadMoreError(materialId);
            }
        });
    }

    private void handleLoadMoreResult(HomePagerBean result, int categoryId) {
        for (ICategoryPagerBallBack callback : callbacks) {
            if (callback.getCategoryId() == categoryId){
                if (result == null || result.getData().size() == 0){
                    callback.onLoadMoreEmpty();
                }else {
                    callback.onLoaderMoreLoaded(result.getData());
                }
            }
        }
    }

    private void handleLoadMoreError(int categoryId) {
        for (ICategoryPagerBallBack callback : callbacks) {
            if (callback.getCategoryId() == categoryId){
                callback.onLoadMoreError();
            }
        }
    }

    private void handleNetWorkError(int categoryId) {
        mMCurrentPage--;
        pageInfo.put(categoryId, mMCurrentPage);
        for (ICategoryPagerBallBack callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                callback.OnError();
            }
        }
    }

    private void handleHomePageContentResult(HomePagerBean pagerBean, int categoryId) {
        // 通知UI层更新数据
        List<HomePagerBean.DataBean> data = pagerBean.getData();
        for (ICategoryPagerBallBack callback : callbacks) {
            if (callback.getCategoryId() == categoryId) {
                if (pagerBean == null || pagerBean.getData().size() == 0) {
                    callback.OnEmpty();
                } else {
                    List<HomePagerBean.DataBean> looperData = data.subList(data.size() - 5, data.size());
                    callback.onLooperListLoaded(looperData);
                    callback.onContentLoaded(pagerBean.getData());
                }
            }
        }
    }


    @Override
    public void reload() {

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
