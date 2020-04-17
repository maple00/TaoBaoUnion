package com.sxs.taobaounion.presenter.impl;

import com.sxs.taobaounion.model.Api;
import com.sxs.taobaounion.model.domain.Categories;
import com.sxs.taobaounion.presenter.IHomePresenter;
import com.sxs.taobaounion.utils.LogUtils;
import com.sxs.taobaounion.utils.RetrofitManager;
import com.sxs.taobaounion.view.IHomeCallback;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @Author: a797s
 * @Date: 2020/4/17 14:37
 * @Desc:
 */
public final class HomePresenterImpl implements IHomePresenter {

    private IHomeCallback mCallback = null;

    @Override
    public void getCategories() {
        if (mCallback != null) {
           // mCallback.OnLoading();
        }
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        Call<Categories> task = api.getCategories();
        task.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                // 数据结果
                int code = response.code();
                LogUtils.d(HomePresenterImpl.this, "result code --- >" + code);
                if (code == HttpsURLConnection.HTTP_OK) {
                    Categories categories = response.body();
                    if (mCallback != null) {
                        if (categories == null || categories.getData().size() == 0) {
                           // mCallback.OnEmpty();
                        } else {
                        }
                        LogUtils.d(HomePresenterImpl.this, categories.toString());
                        mCallback.onCategoriesLoaded(categories);
                    }
                } else {
                    // 请求失败
                    LogUtils.i(HomePresenterImpl.this, "请求失败");
                    if (mCallback != null) {
                       // mCallback.OnError();
                    }
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                // 加载失败
                LogUtils.i(HomePresenterImpl.this, "请求失败" + t);
                if (mCallback != null) {
                    //mCallback.OnError();
                }
            }
        });
    }

    @Override
    public void registerCallback(IHomeCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void unRegisterCallback(IHomeCallback callback) {
        mCallback = null;
    }
}
