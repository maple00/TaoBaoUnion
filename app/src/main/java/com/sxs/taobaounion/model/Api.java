package com.sxs.taobaounion.model;

import com.sxs.taobaounion.model.domain.Categories;
import com.sxs.taobaounion.model.domain.HomePagerBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @Author: a797s
 * @Date: 2020/4/17 15:02
 * @Desc:
 */
public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();

    @GET
    Call<HomePagerBean> getHomePageContent(@Url String url);
}
