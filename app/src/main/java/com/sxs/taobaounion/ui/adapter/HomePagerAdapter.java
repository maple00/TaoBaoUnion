package com.sxs.taobaounion.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sxs.taobaounion.model.domain.Categories;
import com.sxs.taobaounion.ui.fragment.HomePagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: a797s
 * @Date: 2020/4/17 15:37
 * @Desc: 首页pagerAdapter
 */
public final class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Categories.DataBean> mCategoryList = new ArrayList<>();

    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoryList.get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new HomePagerFragment();
    }

    @Override
    public int getCount() {
        return mCategoryList == null ? 0 : mCategoryList.size();
    }

    public void setCategories(Categories categories) {
        mCategoryList.clear();
        List<Categories.DataBean> data = categories.getData();
        this.mCategoryList.addAll(data);
        notifyDataSetChanged();
    }
}
