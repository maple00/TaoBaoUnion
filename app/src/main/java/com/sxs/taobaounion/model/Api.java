package com.sxs.taobaounion.model;

import com.sxs.taobaounion.model.domain.Categories;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @Author: a797s
 * @Date: 2020/4/17 15:02
 * @Desc:
 */
public interface Api {

    @GET("discovery/categories")
    Call<Categories> getCategories();
}
