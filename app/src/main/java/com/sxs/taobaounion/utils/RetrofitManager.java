package com.sxs.taobaounion.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: a797s
 * @Date: 2020/4/17 14:57
 * @Desc: 网络编程Retrofit
 */
public final class RetrofitManager {

    private static final RetrofitManager ourInstance = new RetrofitManager();
    private final Retrofit mRetrofit;

    public static RetrofitManager getInstance(){
        return ourInstance;
    }

    private RetrofitManager(){
        // 创建retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Contants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }
}
